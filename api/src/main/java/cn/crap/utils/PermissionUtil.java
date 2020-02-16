package cn.crap.utils;

import cn.crap.adapter.ProjectUserAdapter;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.ProjectUserDto;
import cn.crap.enu.MyError;
import cn.crap.enu.ProjectPermissionEnum;
import cn.crap.enu.UserType;
import cn.crap.framework.MyException;
import cn.crap.model.ProjectPO;
import cn.crap.model.ProjectUserPO;
import cn.crap.query.ProjectUserQuery;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @author Ehsan
 * @date 2018/10/6 12:34
 */
public class PermissionUtil implements IConst{
    /**
     * 用户页面权限检查
     *
     * @param project
     * @throws MyException
     */
    public static void checkPermission(ProjectPO project, ProjectPermissionEnum needPermission) throws MyException {
        if (project == null || project.getId() == null){
            throw new MyException(MyError.E000022, "项目有误，没有查询到该项目");
        }
        LoginInfoDto user = LoginUserHelper.getUser(MyError.E000021);
        /**
         * 最高管理员修改项目
         * the supper admin can do anything
         */
        if (Tools.isSuperAdmin(user.getAuthStr())) {
            return;
        }

        /**
         * 拥有项目权限的管理员可以操作用户项目
         */
        String authority = user.getAuthStr();
        if(user.getType() == UserType.ADMIN.getType() && authority != null && (","+authority).indexOf(","+ C_AUTH_PROJECT +",")>=0){
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
        if (needPermission == ProjectPermissionEnum.MY_DATE) {
            throw new MyException(MyError.E000022, needPermission.getDesc());
        }

        // 项目成员
        List<ProjectUserPO> projectUserPOList = ServiceFactory.getInstance().getProjectUserService().select(
                new ProjectUserQuery().setProjectId(project.getId()).setUserId(user.getId()));

        if (CollectionUtils.isEmpty(projectUserPOList)) {
            throw new MyException(MyError.E000022, needPermission.getDesc());
        }

        /**
         * 登录用户为项目成员即可查看
         */
        if (needPermission == ProjectPermissionEnum.READ) {
            return;
        }

        ProjectUserDto dto = ProjectUserAdapter.getDto(projectUserPOList.get(0), null);
        if (dto.getCrShowPermissionSet().contains(needPermission.getValue())) {
            return;
        }

        throw new MyException(MyError.E000022, needPermission.getDesc());
    }

    public static Set<String> getSet(String permissionsStr){
        if (MyString.isEmpty(permissionsStr)){
            return Sets.newHashSet();
        }

        Set<String> attributeSet = Sets.newHashSet();
        Splitter.on(",").omitEmptyStrings().split(StringEscapeUtils.unescapeHtml(permissionsStr))
                .forEach(permissionStr-> attributeSet.add(permissionStr));
        return attributeSet;
    }
}
