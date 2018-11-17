package cn.crap.utils;

import cn.crap.dto.LoginInfoDto;
import cn.crap.enu.MyError;
import cn.crap.framework.MyException;
import cn.crap.framework.SpringContextHolder;
import cn.crap.model.Project;
import cn.crap.service.tool.UserCache;
import org.springframework.util.Assert;

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

    /**
     * 检查登录用户是否有用需要的authPassport
     * @param authPassport
     * @return
     * @throws MyException
     */
    public static boolean checkAuthPassport(String authPassport) throws MyException {
        LoginInfoDto user = LoginUserHelper.getUser(MyError.E000003);
        String authority = user.getAuthStr();
        if( user != null && (","+user.getRoleId()).indexOf(","+ C_SUPER +",")>=0){
            return true;//超级管理员
        }

        // 管理员修改自己的资料
        if(authPassport.equals("USER")){
            // 如果session中的管理员id和参数中的id一致
            if( MyString.isEquals(  user.getId(),  user.getId() )  ){
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
        if( user != null && (","+user.getRoleId()).indexOf(","+ C_SUPER +",")>=0){
            return true;
        }
        return false;
    }

    /**
     * 判断是否是超级管理员或项目拥有者
     * @return
     * @throws MyException
     */
    public static boolean isAdminOrProjectOwner(Project project) throws MyException {
        Assert.notNull(project);
        LoginInfoDto user = LoginUserHelper.getUser(MyError.E000003);
        String authority = user.getAuthStr();
        if( user != null && (","+user.getRoleId()).indexOf(","+ C_SUPER +",")>=0){
            return true;
        }

        if (project.getUserId() != null && project.getUserId().equals(user.getId())){
            return true;
        }
        return false;
    }


}
