package cn.crap.service.custom;

import cn.crap.enumer.LogType;
import cn.crap.enumer.MyError;
import cn.crap.framework.MyException;
import cn.crap.model.mybatis.*;
import cn.crap.service.mybatis.*;
import cn.crap.utils.MyString;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Ehsan
 */
@Service
public class CustomLogService{

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private InterfaceService interfaceService;
    @Autowired
    private SourceService sourceService;
    @Autowired
    private LogService logService;

    /**
     * recover by log
     * 通过日志恢复
     *
     * @param log
     * @throws MyException
     */
    public void recover(Log log) throws MyException {
        log = logService.getById(log.getId());
        switch (log.getModelClass().toUpperCase()) {
            case "INTERFACEWITHBLOBS"://恢复接口
            case "INTERFACE"://恢复接口
                JSONObject json = JSONObject.fromObject(log.getContent());
                InterfaceWithBLOBs inter = (InterfaceWithBLOBs) JSONObject.toBean(json, InterfaceWithBLOBs.class);
                checkModule(inter.getModuleId());
                checkProject(inter.getProjectId());
                interfaceService.update(inter);
                break;
            case "ARTICLEWITHBLOBS":// 恢复文章
            case "ARTICLE":// 恢复文章
                json = JSONObject.fromObject(log.getContent());
                ArticleWithBLOBs article = (ArticleWithBLOBs) JSONObject.toBean(json, ArticleWithBLOBs.class);
                checkModule(article.getModuleId());
                checkProject(article.getProjectId());

                // key有唯一约束，不置为null会报错
                if (MyString.isEmpty(article.getMkey())) {
                    article.setMkey(null);
                }
                articleService.update(article);
                break;
            case "MODULEWITHBLOBS"://恢复模块
            case "MODULE"://恢复模块
                json = JSONObject.fromObject(log.getContent());
                Module module = (Module) JSONObject.toBean(json, Module.class);
                checkProject(module.getProjectId());

                // 模块不允许恢复修改操作，需要关联修改接口数据
                if (!log.getType().equals(LogType.DELTET.name())) {
                    throw new MyException(MyError.E000050);
                }
                moduleService.update(module);
                break;
            case "PROJECTWITHBLOBS":
            case "PROJECT"://恢复项目
                json = JSONObject.fromObject(log.getContent());
                Project project = (Project) JSONObject.toBean(json, Project.class);
                projectService.update(project);
                break;
            case "SOURCEWITHBLOBS":
            case "SOURCE"://恢复资源
                json = JSONObject.fromObject(log.getContent());
                Source source = (Source) JSONObject.toBean(json, Source.class);
                checkModule(source.getModuleId());
                checkProject(source.getProjectId());
                sourceService.update(source);
                break;         
        }
    }

    private void checkModule(String moduleId) throws MyException {
        Assert.notNull(moduleId);
        Module module = moduleService.getById(moduleId);
        if (module == null) {
            throw new MyException(MyError.E000048);
        }
    }

    private void checkProject(String projectId) throws MyException {
        Assert.notNull(projectId);
        Project project = projectService.getById(projectId);
        if (project == null) {
            throw new MyException(MyError.E000049);
        }
    }
}
