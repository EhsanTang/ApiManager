package cn.crap.service.tool;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.PickDto;
import cn.crap.enumer.IconfontCode;
import cn.crap.enumer.MyError;
import cn.crap.enumer.PickCode;
import cn.crap.framework.MyException;
import cn.crap.model.mybatis.Error;
import cn.crap.model.mybatis.*;
import cn.crap.service.IPickService;
import cn.crap.service.custom.CustomErrorService;
import cn.crap.service.custom.CustomModuleService;
import cn.crap.service.custom.CustomProjectService;
import cn.crap.service.mybatis.UserService;
import cn.crap.utils.IConst;
import cn.crap.utils.LoginUserHelper;
import cn.crap.utils.MyString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private CustomErrorService customErrorService;
    @Autowired
    private CustomProjectService customProjectService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomModuleService customModuleService;
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

                for (Error error : customErrorService.queryByProjectId(key, null, null, null)) {
                    pick = new PickDto(error.getErrorCode(), error.getErrorCode() + "--" + error.getErrorMsg());
                    picks.add(pick);
                }
                return picks;
            case MENU_ICON:
                for (IconfontCode iconfontCode : IconfontCode.values()) {
                    pick = new PickDto(iconfontCode.name(), "<i class=\"iconfont\">" + iconfontCode.getValue() + "</i>", iconfontCode.getName());
                    picks.add(pick);
                }
                return picks;

            case CATEGORY:
                int i = 0;
                List<String> categories = customModuleService.queryCategoryByModuleId(key);
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
                for (Project p : customProjectService.queryMyProjectByUserId(user.getId())) {
                    pick = new PickDto(IConst.SEPARATOR, p.getName());
                    picks.add(pick);

                    for (Module m : customModuleService.queryByProjectId(p.getId())) {
                        pick = new PickDto(m.getId(), m.getName());
                        picks.add(pick);
                    }
                }
                return picks;

            case PROJECT_MODULES:
                if (MyString.isEmpty(key)) {
                    throw new MyException(MyError.E000065, "key（项目ID）不能为空");
                }
                for (Module m : customModuleService.queryByProjectId(key)) {
                    pick = new PickDto(m.getId(), m.getName());
                    picks.add(pick);
                }
                return picks;


            case USER:
                if (MyString.isEmpty(key) || key.trim().length() < 4) {
                    throw new MyException(MyError.E000065, "输入的搜索长度必须大于3");
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
