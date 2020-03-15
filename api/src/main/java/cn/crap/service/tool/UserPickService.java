package cn.crap.service.tool;

import cn.crap.adapter.ProjectMetaAdapter;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.PickDto;
import cn.crap.dto.ProjectMetaDTO;
import cn.crap.enu.*;
import cn.crap.framework.MyException;
import cn.crap.model.Error;
import cn.crap.model.*;
import cn.crap.query.*;
import cn.crap.service.*;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 采用责任链模式
 * 下拉选择框
 * @author Ehsan
 */
@Service("userPickService")
public class UserPickService implements IPickService{
    @Autowired
    private ErrorService errorService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private SettingCache settingCache;
    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private ModuleService moduleService;
    @Resource(name = "adminPickService")
    private IPickService adminPickService;
    @Resource
    private ProjectMetaService projectMetaService;

    @Override
    public List<PickDto> getPickList(String code, String key) throws MyException {
        PickCode pickCode = PickCode.getByCode(code);
        if (pickCode == null) {
            throw new MyException(MyError.E000065, "code 有误");
        }

        LoginInfoDto user = LoginUserHelper.getUser();
        List<PickDto> picks = new ArrayList<>();
        PickDto pick = null;

        switch (pickCode) {
            case ERROR_CODE:
                if (StringUtils.isEmpty(key)) {
                    throw new MyException(MyError.E000065, "key（项目ID）不能为空");
                }

                for (Error error : errorService.query(new ErrorQuery().setProjectId(key).setPageSize(settingCache.getInt(SettingEnum.MAX_ERROR)))) {
                    pick = new PickDto(error.getErrorCode(), error.getErrorCode() + "--" + error.getErrorMsg());
                    picks.add(pick);
                }
                return picks;
            case MENU_ICON:
                pick = new PickDto("icon_null", IConst.NULL, "不使用图标");
                picks.add(pick);
                for (IconfontCode iconfontCode : IconfontCode.values()) {
                    pick = new PickDto(iconfontCode.name(), "<i class=\"iconfont fw200\">" + iconfontCode.getValue() + "</i>", iconfontCode.getName());
                    picks.add(pick);
                }
                return picks;

            case CATEGORY:
                int i = 0;
                List<String> categories = moduleService.queryCategoryByModuleId(key);
                for (String category : categories) {
                    i++;
                    pick = new PickDto("category_" + i, category, category);
                    picks.add(pick);
                }
                return picks;

            /**
             * 我创建的项目的模块
             * 拷贝接口时使用
             */
            case MY_MODULE:
                for (ProjectPO p : projectService.query(user.getId(), false, null, new Page(100, 1))) {
                    pick = new PickDto(IConst.C_SEPARATOR, p.getName());
                    picks.add(pick);
                    List<ModulePO> moduleList = moduleService.select(new ModuleQuery().setProjectId(p.getId()).setPageSize(100));
                    if (CollectionUtils.isEmpty(moduleList)){
                        pick = new PickDto(System.currentTimeMillis() + Tools.getChar(20), null,"项目下尚未创建模块");
                        picks.add(pick);
                        continue;
                    }
                    for (ModulePO m : moduleList) {
                        pick = new PickDto(m.getId(), m.getName());
                        picks.add(pick);
                    }
                }
                return picks;
            case PROJECT_ENV:
                List<ProjectMetaPO> envList = projectMetaService.select(new ProjectMetaQuery().setType(ProjectMetaType.ENV.getType()).setProjectId(key));
                for (ProjectMetaPO envPO : envList) {
                    ProjectMetaDTO envDTO = ProjectMetaAdapter.getDto(envPO, null);
                    pick = new PickDto(envDTO.getId(), envDTO.getProjectId(), envDTO.getName());
                    picks.add(pick);
                }
                return picks;
            case PROJECT_MODULES:
                if (MyString.isEmpty(key)) {
                    throw new MyException(MyError.E000065, "key（项目ID）不能为空");
                }
                for (ModulePO m : moduleService.select(new ModuleQuery().setProjectId(key).setPageSize(100))) {
                    pick = new PickDto(m.getId(), m.getName());
                    picks.add(pick);
                }
                return picks;

            case PROJECT_USER:
            case EXECUTOR:
            case TRACER:
            case TESTER:
                if (MyString.isEmpty(key)) {
                    throw new MyException(MyError.E000065, "key（项目ID）不能为空");
                }
                UserPO creator = userService.get(projectService.get(key).getUserId());
                pick = new PickDto(creator.getId(), MyString.isEmpty(creator.getTrueName()) ? creator.getUserName() : creator.getTrueName());
                picks.add(pick);

                // TODO 项目允许的最大成员数，项目成员中需要更新用户真实姓名
                for (ProjectUserPO m : projectUserService.select(new ProjectUserQuery().setProjectId(key))) {
                    UserPO projectUser = userService.get(m.getUserId());
                    if (projectUser == null || projectUser.getId().equals(creator.getId())){
                        continue;
                    }
                    pick = new PickDto(projectUser.getId(), MyString.isEmpty(projectUser.getTrueName()) ? projectUser.getUserName() : projectUser.getTrueName());
                    picks.add(pick);
                }


                return picks;
            case USER:
                if (MyString.isEmpty(key) || key.trim().length() < 6) {
                    throw new MyException(MyError.E000065, "输入的搜索长度必须大于5");
                }
                key = "%" + key + "%";
                Set<String> userIds = new TreeSet<>();


                UserQuery userQuery = new UserQuery().setOrEmail(key).setOrUserName(key).setOrTrueName(key).setPageSize(20);
                for (UserPO u : userService.select(userQuery)) {
                    if (!userIds.contains(u.getId())) {
                        pick = new PickDto(u.getId(), u.getUserName());
                        picks.add(pick);
                        userIds.add(u.getId());
                    }
                }
                return picks;
        }

        return adminPickService.getPickList(code, key);
    }


}
