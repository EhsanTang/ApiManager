package cn.crap.service;

import cn.crap.dao.mybatis.BugDao;
import cn.crap.enu.*;
import cn.crap.framework.MyException;
import cn.crap.model.BugPO;
import cn.crap.model.Module;
import cn.crap.query.BugQuery;
import cn.crap.service.tool.ModuleCache;
import cn.crap.service.tool.ProjectCache;
import cn.crap.utils.IAuthCode;
import cn.crap.utils.IConst;
import cn.crap.utils.LoginUserHelper;
import cn.crap.utils.PermissionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Optional;


@Service
public class BugService extends NewBaseService<BugPO, BugQuery> implements IConst {
    private BugDao bugDao;

    @Autowired
    private ModuleCache moduleCache;

    @Autowired
    private ProjectCache projectCache;

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
    public boolean insert(BugPO bug) throws Exception{
        Assert.notNull(bug);
        Assert.notNull(bug.getProjectId());
        if (bug == null) {
            return false;
        }

        if (bug.getContent() == null){
            bug.setContent("");
        }
        bug.setCreator(LoginUserHelper.getUser().getId());
        bug.setStatus(BugStatus.NEW.getByteValue());
        bug.setSequence(getMaxSequence(bug, new BugQuery()));
        return super.insert(bug);
    }

    public BugPO getChangeBugPO(String id, String type, String value) throws Exception{
        BugPO bug = new BugPO();
        bug.setId(id);
        if (PickCode.BUG_STATUS.getCode().equals(type)){
            BugStatus bugStatus = Optional.ofNullable(BugStatus.getByValue(value)).orElseThrow(() -> new MyException(MyError.E000065, "状态有误"));
            bug.setStatus(bugStatus.getByteValue());
        } else if (PickCode.SEVERITY.getCode().equals(type)){
            BugSeverity bugSeverity = Optional.ofNullable(BugSeverity.getByValue(value)).orElseThrow(() -> new MyException(MyError.E000065, "严重程度有误"));
            bug.setSeverity(bugSeverity.getByteValue());
        } else if (PickCode.TRACE_TYPE.getCode().equals(type)){
            BugType bugType = Optional.ofNullable(BugType.getByValue(value)).orElseThrow(() -> new MyException(MyError.E000065, "跟踪类型有误"));
            bug.setTrace(bugType.getByteValue());
        } else if (PickCode.PRIORITY.getCode().equals(type)){
            BugPriority bugPriority = Optional.ofNullable(BugPriority.getByValue(value)).orElseThrow(() -> new MyException(MyError.E000065, "优先级有误"));
            bug.setPriority(bugPriority.getByteValue());
        } else if (PickCode.MY_MODULE.getCode().equals(type)){
            Module module = moduleCache.get(value);
            Optional.ofNullable(module.getId()).orElseThrow(() -> new MyException(MyError.E000065, "模块有误"));
            bug.setModuleId(module.getId());
            bug.setProjectId(module.getProjectId());
            PermissionUtil.checkPermission(projectCache.get(module.getProjectId()), IAuthCode.READ);
        } else if ("NAME".equalsIgnoreCase(type)){
            bug.setName(value);
        } else if ("CONTENT".equalsIgnoreCase(type)){
            bug.setContent(value);
        } else {
            throw new MyException(MyError.E000065, "不识别的类型：" + type);
        }
        return bug;
    }

}
