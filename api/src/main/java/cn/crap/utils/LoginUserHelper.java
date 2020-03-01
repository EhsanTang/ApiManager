package cn.crap.utils;

import cn.crap.dto.LoginInfoDto;
import cn.crap.enu.MyError;
import cn.crap.framework.MyException;
import cn.crap.framework.SpringContextHolder;
import cn.crap.framework.ThreadContext;
import cn.crap.model.ProjectPO;
import cn.crap.service.tool.UserCache;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author Ehsan
 * @date 17/12/30 15:59
 */
public class LoginUserHelper implements IConst{
    /**
     * 如果未登录，则返回指定的错误码
     * @return
     */
    public static LoginInfoDto getUser(MyError error) throws MyException{
        LoginInfoDto loginInfoDto = tryGetUser();
        if (loginInfoDto == null){
            throw new MyException(error == null ? MyError.E000021 : error);
        }
        return loginInfoDto;
    }

    /**
     * 如果未登录，则返回错误码
     * @return
     * @throws MyException
     */
    public static LoginInfoDto getUser() throws MyException{
       return getUser(null);
    }

    /**
     * 如果登录了，则返回用户信息，否则返回null
     * @return
     */
    public static LoginInfoDto tryGetUser(){
        UserCache userCache = SpringContextHolder.getBean("userCache", UserCache.class);
        String uid = MyCookie.getCookie(C_COOKIE_USERID, false);
        String token = MyCookie.getCookie(IConst.COOKIE_TOKEN);
        LoginInfoDto user = userCache.get(uid);
        if (user == null || MyString.isEmpty(token) || MyString.isEmpty(uid) || !Aes.desEncrypt(token).equals(uid)) {
            return null;
        }
        return user;
    }

    public static String getSecretName(LoginInfoDto loginInfoDto){
        if (loginInfoDto == null){
            return null;
        }
        String name = Optional.ofNullable(loginInfoDto.getTrueName()).orElse(loginInfoDto.getUserName());
        if (MyString.isEmpty(name)) {
            return null;
        }
        int length = name.length();
        return name.substring(0, 1) + "**" + (length > 3 ? name.substring(3, length) : "");
    }

    public static String getName(LoginInfoDto loginInfoDto){
        if (loginInfoDto == null){
            return null;
        }
        return Optional.ofNullable(loginInfoDto.getTrueName()).orElse(loginInfoDto.getUserName());
    }

    /**
     * 检查登录用户是否有用需要的authPassport
     * @param authPassport
     * @return
     * @throws MyException
     */
    public static boolean checkAuthPassport(String authPassport) throws MyException {
        LoginInfoDto user = LoginUserHelper.getUser(MyError.E000003);
        String authority = user.getAuthStr();
        if( authority != null && (","+authority).indexOf(","+ C_SUPER +",")>=0){
            return true;//超级管理员
        }

        // 管理员修改自己的资料
        if(authPassport.equals(IConst.C_AUTH_USER)){
            // 如果session中的管理员id和参数中的id一致
            String modifyUserId  = ThreadContext.request().getParameter("id");
            if( MyString.isEquals(  user.getId(), modifyUserId)  ){
                return true;
            }
        }

        if(authority.indexOf(","+authPassport+",")>=0){
            return true;
        }
        return false;
    }

    /**
     * 判断是否是超级管理员
     * @return
     * @throws MyException
     */
    public static boolean isSuperAdmin() throws MyException {
        LoginInfoDto user = tryGetUser();
        if( user != null && (","+user.getAuthStr()).indexOf(","+ C_SUPER +",")>=0){
            return true;
        }
        return false;
    }

    /**
     * 判断是否是超级管理员或项目拥有者
     * @return
     * @throws MyException
     */
    public static boolean isAdminOrProjectOwner(ProjectPO project) throws MyException {
        Assert.notNull(project);
        LoginInfoDto user = LoginUserHelper.getUser(MyError.E000003);
        if( user != null && (","+user.getAuthStr()).indexOf(","+ C_SUPER +",")>=0){
            return true;
        }

        if (project.getUserId() != null && project.getUserId().equals(user.getId())){
            return true;
        }
        return false;
    }


}
