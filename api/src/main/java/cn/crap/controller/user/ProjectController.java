package cn.crap.controller.user;

import cn.crap.adapter.ProjectAdapter;
import cn.crap.beans.Config;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.ProjectDto;
import cn.crap.enu.*;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.Project;
import cn.crap.query.*;
import cn.crap.service.*;
import cn.crap.utils.LoginUserHelper;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/project")
public class ProjectController extends BaseController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ISearchService luceneService;
    @Autowired
    private ErrorService errorService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private InterfaceService interfaceService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SourceService sourceService;

    @RequestMapping("/list.do")
    @ResponseBody
    @AuthPassport
    public JsonResult list(@ModelAttribute ProjectQuery query,
                           @RequestParam(defaultValue = "3") Integer projectShowType) throws MyException {
        Page page = new Page(query);
        LoginInfoDto user = LoginUserHelper.getUser();
        String userId = user.getId();
        List<Project> models;

        // 我创建 & 加入的项目
        if (ProjectShowType.CREATE_JOIN.getType() == projectShowType) {
            page.setAllRow(projectService.count(userId, false, query.getName()));
            models = projectService.query(userId, false, query.getName(), page);
        }

        // 我加入的项目
        else if (ProjectShowType.JOIN.getType() == projectShowType) {
            page.setAllRow(projectService.count(userId, true, query.getName()));
            models = projectService.query(userId, true, query.getName(), page);
        }

        // 管理员查看所有项目
        else if(ProjectShowType.ALL.getType() == projectShowType && user.getType() == UserType.ADMIN.getType()){
            page.setAllRow(projectService.count(query));
            models = projectService.query(query);
        }

        // 我创建的项目
        else {
            query.setUserId(userId);
            page.setAllRow(projectService.count(query));
            models = projectService.query(query);
        }

        return new JsonResult().page(page).data(ProjectAdapter.getDto(models, userService));
    }

    @RequestMapping("/detail.do")
    @ResponseBody
    @AuthPassport
    public JsonResult detail(String id) throws MyException {
        if (MyString.isNotEmpty(id)) {
            Project model = projectCache.get(id);
            checkPermission(model, READ);
            ProjectDto dto = ProjectAdapter.getDto(model, null);
            dto.setInviteUrl(projectService.getInviteUrl(dto));
            return new JsonResult(1, dto);
        }

        Project model = new Project();
        model.setType(ProjectType.PRIVATE.getByteType());
        model.setStatus(ProjectStatus.COMMON.getStatus());
        model.setLuceneSearch(CommonEnum.FALSE.getByteValue());
        return new JsonResult(1, ProjectAdapter.getDto(model, null));
    }

    @RequestMapping("/moreInfo.do")
    @ResponseBody
    @AuthPassport
    public JsonResult moreInfo(String id) throws MyException {
        throwExceptionWhenIsNull(id, "项目ID不能为空");
        Map<String, Integer> projectInfo = new HashMap<>();
        projectInfo.put("moduleNum", moduleService.count(new ModuleQuery().setProjectId(id)));
        projectInfo.put("projectUserNum", projectUserService.count(new ProjectUserQuery().setProjectId(id)));
        projectInfo.put("errorNum", errorService.count(new ErrorQuery().setProjectId(id)));
        projectInfo.put("interfaceNum", interfaceService.count(new InterfaceQuery().setProjectId(id)));
        projectInfo.put("articleNum", articleService.count(new ArticleQuery().setProjectId(id).setType(ArticleType.ARTICLE.name())));
        projectInfo.put("dictionaryNum", articleService.count(new ArticleQuery().setProjectId(id).setType(ArticleType.DICTIONARY.name())));
        projectInfo.put("sourceNum", sourceService.count(new SourceQuery().setProjectId(id)));

        return new JsonResult().data(projectInfo);
    }


    @RequestMapping("/addOrUpdate.do")
    @ResponseBody
    public JsonResult addOrUpdate(@ModelAttribute ProjectDto project) throws Exception {
        LoginInfoDto user = LoginUserHelper.getUser();
        String userId = user.getId();
        String projectId = project.getId();

        checkCrapDebug(userId, projectId);

        // 私有项目不能建立索引
        if (project.getType() == ProjectType.PRIVATE.getType()) {
            project.setLuceneSearch(LuceneSearchType.No.getByteValue());
        }

        // 修改
        if (!MyString.isEmpty(projectId)) {
            Project dbProject = projectCache.get(projectId);
            checkPermission(dbProject);

            // 普通用户不能推荐项目，将项目类型修改为原有类型
            if (LoginUserHelper.getUser().getType() == UserType.USER.getType()) {
                project.setStatus(null);
            }
            projectService.update(ProjectAdapter.getModel(project), true);

            // 需要重建索引
            projectCache.del(projectId);
            if (!project.getType().equals(dbProject.getType())
                    || !project.getLuceneSearch().equals(dbProject.getLuceneSearch())) {
                luceneService.rebuildByProjectId(projectId);
            }
        }
        // 新增
        else {
            int totalProjectNum = projectService.count(new ProjectQuery().setUserId(userId));
            Integer maxProject = settingCache.getInteger(SettingEnum.MAX_PROJECT);
            if (totalProjectNum > maxProject) {
                throw new MyException(MyError.E000068, maxProject + "");
            }

            Project model = ProjectAdapter.getModel(project);
            model.setUserId(userId);
            model.setPassword(project.getPassword());
            // 普通用户不能推荐项目
            if (LoginUserHelper.getUser().getType() == UserType.USER.getType()) {
                project.setStatus(Byte.valueOf(ProjectStatus.COMMON.getStatus() + ""));
            }
            projectService.insert(model);
        }

        // 清楚缓存
        projectCache.del(projectId);

        // 刷新用户权限 将用户信息存入缓存
        userCache.add(userId, new LoginInfoDto(userService.getById(userId), roleService, projectService, projectUserService));
        return new JsonResult(1, project);
    }


    @RequestMapping("/delete.do")
    @ResponseBody
    public JsonResult delete(@ModelAttribute Project project) throws Exception {
        // 系统数据，不允许删除
        if (project.getId().equals("web")) {
            throw new MyException(MyError.E000009);
        }
        checkCrapDebug(LoginUserHelper.getUser().getId(), project.getId());

        Project model = projectCache.get(project.getId());
        checkPermission(model);


        // 只有子模块数量为0，才允许删除项目
        if (moduleService.count(new ModuleQuery().setProjectId(model.getId())) > 0) {
            throw new MyException(MyError.E000023);
        }

        // 只有错误码数量为0，才允许删除项目
        if (errorService.count(new ErrorQuery().setProjectId(model.getId())) > 0) {
            throw new MyException(MyError.E000033);
        }

        // 只有项目成员数量为0，才允许删除项目
        if (projectUserService.count(new ProjectUserQuery().setProjectId(model.getId())) > 0) {
            throw new MyException(MyError.E000038);
        }

        projectCache.del(project.getId());
        projectService.delete(project.getId());
        return new JsonResult(1, null);
    }

    @RequestMapping("/changeSequence.do")
    @ResponseBody
    @AuthPassport
    public JsonResult changeSequence(@RequestParam String id, @RequestParam String changeId) throws MyException {
        Project change = projectCache.get(changeId);
        Project model = projectCache.get(id);

        checkPermission(change);
        checkPermission(model);

        int modelSequence = model.getSequence();
        model.setSequence(change.getSequence());
        change.setSequence(modelSequence);

        projectService.update(model);
        projectService.update(change);

        return new JsonResult(1, null);
    }

    @ResponseBody
    @RequestMapping("/rebuildIndex.do")
    @AuthPassport
    public JsonResult rebuildIndex(@RequestParam String projectId) throws Exception {
        Project model = projectCache.get(projectId);
        checkPermission(model);
        return new JsonResult(1, luceneService.rebuildByProjectId(projectId));
    }
}
