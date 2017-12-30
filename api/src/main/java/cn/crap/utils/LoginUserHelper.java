package cn.crap.utils;

import cn.crap.dto.LoginInfoDto;
import cn.crap.framework.MyException;
import cn.crap.framework.SpringContextHolder;
import cn.crap.service.tool.UserCache;

/**
 * @author Ehsan
 * @date 17/12/30 15:59
 */
public class LoginUserHelper implements IConst, IErrorCode{
    /**
     * if not login, throw exception
     * @return
     */
    public static LoginInfoDto getUser(String errorCode) throws MyException{
        LoginInfoDto loginInfoDto = tryGetUser();
        if (loginInfoDto == null){
            throw new MyException(MyString.isEmpty(errorCode) ? E000064 : errorCode);
        }
        return loginInfoDto;
    }

    public static LoginInfoDto getUser() throws MyException{
       return getUser(null);
    }

    /**
     * if login, then return loginInfo
     * else return null
     * @return
     */
    public static LoginInfoDto tryGetUser(){
        UserCache userCache = SpringContextHolder.getBean("userCache", UserCache.class);
        String uId = MyCookie.getCookie(C_COOKIE_USERID, false);
        return userCache.get(uId);
    }

    /**
     * 检查登陆用户是否有用需要的authPassport
     * @param authPassport
     * @return
     * @throws MyException
     */
    public static boolean checkAuthPassport(String authPassport) throws MyException {
        LoginInfoDto user = LoginUserHelper.getUser(E000003);
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
}
