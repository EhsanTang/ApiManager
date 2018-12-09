package cn.crap.service.tool;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.PickDto;
import cn.crap.enu.IconfontCode;
import cn.crap.enu.MyError;
import cn.crap.enu.PickCode;
import cn.crap.enu.SettingEnum;
import cn.crap.framework.MyException;
import cn.crap.model.Error;
import cn.crap.model.*;
import cn.crap.query.ErrorQuery;
import cn.crap.query.ModuleQuery;
import cn.crap.service.*;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

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
    private ModuleService moduleService;
    @Resource(name = "adminPickService")
    private IPickService adminPickService;

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

                for (Error error : errorService.query(new ErrorQuery().setProjectId(key).setPageSize(settingCache.getInteger(SettingEnum.MAX_ERROR)))) {
                    pick = new PickDto(error.getErrorCode(), error.getErrorCode() + "--" + error.getErrorMsg());
                    picks.add(pick);
                }
                return picks;
            case MENU_ICON:
                pick = new PickDto("icon_null", IConst.NULL, "不使用图标");
                picks.add(pick);
                for (IconfontCode iconfontCode : IconfontCode.values()) {
                    pick = new PickDto(iconfontCode.name(), "<i class=\"iconfont\">" + iconfontCode.getValue() + "</i>", iconfontCode.getName());
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
                for (Project p : projectService.query(user.getId(), false, null, new Page(100, 1))) {
                    pick = new PickDto(IConst.C_SEPARATOR, p.getName());
                    picks.add(pick);
                    List<Module> moduleList = moduleService.query(new ModuleQuery().setProjectId(p.getId()).setPageSize(100));
                    if (CollectionUtils.isEmpty(moduleList)){
                        pick = new PickDto(System.currentTimeMillis() + Tools.getChar(20), null,"项目下尚未创建模块");
                        picks.add(pick);
                        continue;
                    }
                    for (Module m : moduleList) {
                        pick = new PickDto(m.getId(), m.getName());
                        picks.add(pick);
                    }
                }
                return picks;

            case PROJECT_MODULES:
                if (MyString.isEmpty(key)) {
                    throw new MyException(MyError.E000065, "key（项目ID）不能为空");
                }
                for (Module m : moduleService.query(new ModuleQuery().setProjectId(key).setPageSize(100))) {
                    pick = new PickDto(m.getId(), m.getName());
                    picks.add(pick);
                }
                return picks;


            case USER:
                if (MyString.isEmpty(key) || key.trim().length() < 6) {
                    throw new MyException(MyError.E000065, "输入的搜索长度必须大于5");
                }
                key = "%" + key + "%";
                Set<String> userIds = new TreeSet<>();

                UserCriteria userExample = new UserCriteria();
                userExample.createCriteria().andEmailLike(key);

                userExample.or(userExample.createCriteria().andUserNameLike(key));
                userExample.or(userExample.createCriteria().andTrueNameLike(key));

                userExample.setMaxResults(20);

                for (User u : userService.selectByExample(userExample)) {
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
