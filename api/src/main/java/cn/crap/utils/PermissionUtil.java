package cn.crap.utils;

import cn.crap.adapter.ProjectUserAdapter;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.ProjectUserDto;
import cn.crap.enu.MyError;
import cn.crap.framework.MyException;
import cn.crap.model.Project;

/**
 * @author Ehsan
 * @date 2018/10/6 12:34
 */
public class PermissionUtil implements IConst, IAuthCode{
    /**
     * 用户页面权限检查
     *
     * @param project
     * @throws MyException
     */
    public static void checkPermission(Project project, int type) throws MyException {
        if (project == null || project.getId() == null){
            throw new MyException(MyError.E000022, "项目不能为空");
        }
        LoginInfoDto user = LoginUserHelper.getUser(MyError.E000021);
        /**
         * 最高管理员修改项目
         * the supper admin can do anything
         */
        if (("," + user.getRoleId()).indexOf("," + C_SUPER + ",") >= 0) {
            return;
        }

        /**
         * 修改自己的项目
         * myself project
         */
        if (user.getId().equals(project.getUserId())) {
            return;
        }

        /**
         * 只有项目创建者才能查看
         */
        if (type == MY_DATE) {
            throw new MyException(MyError.E000022);
        }

        // 项目成员
        ProjectUserDto puDto = ProjectUserAdapter.getDto(user.getProjects().get(project.getId()), null);
        if (puDto == null) {
            throw new MyException(MyError.E000022);
        }

        /**
         * 登录用户为项目成员即可查看
         */
        if (type == READ) {
            return;
        }

        if (type < puDto.getProjectAuth().length - 1 && puDto.getProjectAuth()[type]) {
            return;
        }

        throw new MyException(MyError.E000022);
    }
}
