package cn.crap.ability;

import cn.crap.adapter.ProjectUserAdapter;
import cn.crap.dto.LoginInfoDto;
import cn.crap.enu.ProjectUserType;
import cn.crap.model.ProjectPO;
import cn.crap.model.ProjectUserPO;
import cn.crap.service.ProjectService;
import cn.crap.service.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 项目能力
 * @author Ehsan
 * @date 2020/2/29 23:11
 */
@Service
public class ProjectAbility {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectUserService projectUserService;

    /**
     * 添加项目:需要将自己设置为项目用户，查询的时候可以不需要关联查询
     * @param project
     * @return
     * @throws Exception
     */
    public boolean addProject(ProjectPO project, LoginInfoDto user) throws Exception{
        if (projectService.insert(project)) {
            ProjectUserPO projectUser = ProjectUserAdapter.getInitProjectUserPO(project, user);
            projectUser.setType(ProjectUserType.CREATOR.getByteType());
            projectUser.setSequence(project.getSequence());
            projectUser.setProjectName(project.getName());
            return projectUserService.insert(projectUser);
        }
        return false;
    }

}
