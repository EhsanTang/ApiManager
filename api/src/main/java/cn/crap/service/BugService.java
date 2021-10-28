package cn.crap.service;

import cn.crap.adapter.BugAdapter;
import cn.crap.dao.mybatis.BugDao;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.SearchDto;
import cn.crap.enu.*;
import cn.crap.framework.MyException;
import cn.crap.model.BugLogPO;
import cn.crap.model.BugPO;
import cn.crap.model.ModulePO;
import cn.crap.model.UserPO;
import cn.crap.query.BugQuery;
import cn.crap.service.tool.ModuleCache;
import cn.crap.service.tool.ProjectCache;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;


/**
 * 暂时不支持搜索
 * ILuceneService
 */
@Service
public class BugService extends NewBaseService<BugPO, BugQuery> implements ILuceneService, IConst {
    private BugDao bugDao;

    @Autowired
    private ModuleCache moduleCache;

    @Autowired
    private ProjectCache projectCache;

    @Autowired
    private UserService userService;

    @Resource
    public void bugDao(BugDao bugDao) {
        this.bugDao = bugDao;
        super.setBaseDao(bugDao, TableId.BUG);
    }

    /**
     * 新增
     * @param bug
     * @return
     */
    @Override
    public boolean insert(BugPO bug) throws MyException{
        Assert.notNull(bug);
        Assert.notNull(bug.getProjectId());
        if (bug == null) {
            return false;
        }

        if (bug.getContent() == null){
            bug.setContent("");
        }
        LoginInfoDto user = LoginUserHelper.getUser();
        bug.setCreator(user.getId());
        bug.setCreatorStr(MyString.isEmpty(user.getTrueName()) ? user.getUserName() : user.getTrueName());
        bug.setStatus(BugStatus.NEW.getByteValue());
        return super.insert(bug);
    }

    @Override
    public List<SearchDto> selectOrderById(String projectId, String id, int pageSize){
        Assert.isTrue(pageSize > 0 && pageSize <= 1000);
        BugQuery bugQuery = new BugQuery().setProjectId(projectId).setPageSize(pageSize).setIdGreatThen(id).setSort(TableField.SORT.ID_ASC);
        return BugAdapter.getSearchDto(bugDao.select(bugQuery));
    }

    public BugPO getChangeBugPO(String id, String type, String value, BugLogPO bugLogPO, BugPO dbBug) throws Exception{
        BugPO bug = new BugPO();
        bug.setId(id);
        if (PickCode.BUG_STATUS.getCode().equals(type)){
            BugStatus bugStatus = Optional.ofNullable(BugStatus.getByValue(value)).orElseThrow(() -> new MyException(MyError.E000065, "状态有误"));
            bug.setStatus(bugStatus.getByteValue());

            bugLogPO.setType(BugLogType.STATUS.getByteType());
            bugLogPO.setOriginalValue(BugStatus.getNameByValue(dbBug.getStatus()));
            bugLogPO.setNewValue(BugStatus.getNameByValue(bugStatus.getByteValue()));
        } else if (PickCode.SEVERITY.getCode().equals(type)){
            BugSeverity bugSeverity = Optional.ofNullable(BugSeverity.getByValue(value)).orElseThrow(() -> new MyException(MyError.E000065, "严重程度有误"));
            bug.setSeverity(bugSeverity.getByteValue());

            bugLogPO.setType(BugLogType.SEVERITY.getByteType());
            bugLogPO.setOriginalValue(BugSeverity.getNameByValue(dbBug.getSeverity()));
            bugLogPO.setNewValue(BugSeverity.getNameByValue(bugSeverity.getByteValue()));
        } else if (PickCode.BUG_TYPE.getCode().equals(type)){
            BugType bugType = Optional.ofNullable(BugType.getByValue(value)).orElseThrow(() -> new MyException(MyError.E000065, "跟踪类型有误"));
            bug.setType(bugType.getByteValue());

            bugLogPO.setType(BugLogType.TYPE.getByteType());
            bugLogPO.setOriginalValue(BugType.getNameByValue(dbBug.getType()));
            bugLogPO.setNewValue(BugType.getNameByValue(bugType.getByteValue()));
        } else if (PickCode.PRIORITY.getCode().equals(type)){
            BugPriority bugPriority = Optional.ofNullable(BugPriority.getByValue(value)).orElseThrow(() -> new MyException(MyError.E000065, "优先级有误"));
            bug.setPriority(bugPriority.getByteValue());

            bugLogPO.setType(BugLogType.PRIORITY.getByteType());
            bugLogPO.setOriginalValue(BugPriority.getNameByValue(dbBug.getPriority()));
            bugLogPO.setNewValue(BugPriority.getNameByValue(bugPriority.getByteValue()));
        } else if (PickCode.MY_MODULE.getCode().equals(type) || PickCode.PROJECT_MODULES.getCode().equals(type)){
            ModulePO module = moduleCache.get(value);
            Optional.ofNullable(module.getId()).orElseThrow(() -> new MyException(MyError.E000065, "模块有误"));
            bug.setModuleId(module.getId());
            bug.setProjectId(module.getProjectId());
            PermissionUtil.checkPermission(projectCache.get(module.getProjectId()), ProjectPermissionEnum.READ);

            ModulePO originalModule = moduleCache.get(dbBug.getModuleId());
            bugLogPO.setType(BugLogType.MODULE.getByteType());
            bugLogPO.setOriginalValue(originalModule.getName());
            bugLogPO.setNewValue(module.getName());
        } else if (PickCode.EXECUTOR.getCode().equals(type)){
            // TODO 用户是否是项目成员
            UserPO user = userService.get(value);
            Optional.ofNullable(user).orElseThrow(() -> new MyException(MyError.E000065, "用户有误"));
            bug.setExecutor(user.getId());
            bug.setExecutorStr(MyString.isEmpty(user.getTrueName()) ? user.getUserName() : user.getTrueName());

            bugLogPO.setType(BugLogType.EXECUTOR.getByteType());
            bugLogPO.setSenior(dbBug.getExecutor());
            bugLogPO.setOriginalValue(dbBug.getExecutorStr());
            bugLogPO.setJunior(bug.getExecutor());
            bugLogPO.setNewValue(bug.getExecutorStr());
        } else if (PickCode.TESTER.getCode().equals(type)){
            // TODO 用户是否是项目成员
            UserPO user = userService.get(value);
            Optional.ofNullable(user).orElseThrow(() -> new MyException(MyError.E000065, "用户有误"));
            bug.setTester(user.getId());
            bug.setTesterStr(MyString.isEmpty(user.getTrueName()) ? user.getUserName() : user.getTrueName());

            bugLogPO.setType(BugLogType.TESTER.getByteType());
            bugLogPO.setSenior(dbBug.getTester());
            bugLogPO.setOriginalValue(dbBug.getTesterStr());
            bugLogPO.setJunior(bug.getTester());
            bugLogPO.setNewValue(bug.getTesterStr());
        } else if (PickCode.TRACER.getCode().equals(type)){
            // TODO 用户是否是项目成员
            UserPO user = userService.get(value);
            Optional.ofNullable(user).orElseThrow(() -> new MyException(MyError.E000065, "用户有误"));
            bug.setTracer(user.getId());
            bug.setTracerStr(MyString.isEmpty(user.getTrueName()) ? user.getUserName() : user.getTrueName());

            bugLogPO.setType(BugLogType.TRACER.getByteType());
            bugLogPO.setSenior(dbBug.getTracer());
            bugLogPO.setOriginalValue(dbBug.getTracerStr());
            bugLogPO.setJunior(bug.getTracer());
            bugLogPO.setNewValue(bug.getTracerStr());
        } else if ("NAME".equalsIgnoreCase(type)){
            bug.setName(value);

            bugLogPO.setType(BugLogType.TITLE.getByteType());
            bugLogPO.setOriginalValue(dbBug.getName());
            bugLogPO.setNewValue(bug.getName());
        } else if ("CONTENT".equalsIgnoreCase(type)){
            bug.setContent(value);

            bugLogPO.setType(BugLogType.CONTENT.getByteType());
            String content = Optional.ofNullable(dbBug.getContent()).orElse("");
            content = Tools.removeHtml(content);
            bugLogPO.setOriginalValue(content.length() > 30 ? content.substring(0, 30) + "..." : content);

            String newContent = Optional.ofNullable(bug.getContent()).orElse("");
            newContent = Tools.removeHtml(newContent);
            bugLogPO.setNewValue(Tools.removeHtml(newContent.length() > 30 ? newContent.substring(0, 30) + "..." : newContent));
        } else {
            throw new MyException(MyError.E000065, "不识别的类型：" + type);
        }
        return bug;
    }
}
