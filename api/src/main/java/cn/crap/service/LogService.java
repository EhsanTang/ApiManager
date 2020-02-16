package cn.crap.service;

import cn.crap.dao.mybatis.LogDao;
import cn.crap.enu.MyError;
import cn.crap.enu.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.*;
import cn.crap.query.LogQuery;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Ehsan
 */
@Service
public class LogService extends BaseService<Log, LogDao> {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private InterfaceService interfaceService;
    @Autowired
    private SourceService sourceService;
    @Autowired
    private ProjectService projectService;
    private LogDao logDao;

    @Resource
    public void LogDao(LogDao logDao) {
        this.logDao = logDao;
        super.setBaseDao(logDao, TableId.LOG);
    }
    
    /**
     * 查询日志
     * @param query
     * @return
     * @throws MyException
     */
    public List<Log> query(LogQuery query) throws MyException {
        Assert.notNull(query);

        Page page = new Page(query);
        LogCriteria example = getLogCriteria(query);
        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(query.getSort() == null ? TableField.SORT.CREATE_TIME_DES : query.getSort());

        return logDao.selectByExample(example);
    }

    /**
     * 查询日志数量
     * @param query
     * @return
     * @throws MyException
     */
    public int count(LogQuery query) throws MyException {
        Assert.notNull(query);

        LogCriteria example = getLogCriteria(query);
        return logDao.countByExample(example);
    }

    private LogCriteria getLogCriteria(LogQuery query) throws MyException {
        LogCriteria example = new LogCriteria();
        LogCriteria.Criteria criteria = example.createCriteria();
        if (query.getIdenty() != null){
            criteria.andIdentyEqualTo(query.getIdenty());
        }
        if (query.getModelName() != null){
            criteria.andModelNameEqualTo(query.getModelName());
        }
        return example;
    }

    /**
     * recover by log
     * 通过日志恢复
     *
     * @param log
     * @throws MyException
     */
    public void recover(Log log) throws MyException {
        log = getById(log.getId());
        switch (log.getModelClass().toUpperCase()) {
            case "INTERFACEWITHBLOBS"://恢复接口
            case "INTERFACE"://恢复接口
                JSONObject json = JSONObject.fromObject(log.getContent());
                InterfaceWithBLOBs inter = (InterfaceWithBLOBs) JSONObject.toBean(json, InterfaceWithBLOBs.class);
                checkModule(inter.getModuleId());
                checkLog(inter.getProjectId());
                interfaceService.update(inter);
                break;
            case "ARTICLEWITHBLOBS":// 恢复文档
            case "ARTICLE":// 恢复文档
                json = JSONObject.fromObject(log.getContent());
                ArticleWithBLOBs article = (ArticleWithBLOBs) JSONObject.toBean(json, ArticleWithBLOBs.class);
                checkModule(article.getModuleId());
                checkLog(article.getProjectId());

                // key有唯一约束，不置为null会报错
                if (MyString.isEmpty(article.getMkey())) {
                    article.setMkey(null);
                }
                articleService.update(article);
                break;
            case "MODULEWITHBLOBS"://恢复模块
            case "MODULE"://恢复模块
                json = JSONObject.fromObject(log.getContent());
                ModulePO module = (ModulePO) JSONObject.toBean(json, ModulePO.class);
                checkLog(module.getProjectId());
                moduleService.update(module);
                break;
            case "PROJECTWITHBLOBS":
            case "PROJECT"://恢复日志
                json = JSONObject.fromObject(log.getContent());
                ProjectPO project = (ProjectPO) JSONObject.toBean(json, Log.class);
                projectService.update(project);
                break;
            case "SOURCEWITHBLOBS":
            case "SOURCE"://恢复文件
                json = JSONObject.fromObject(log.getContent());
                Source source = (Source) JSONObject.toBean(json, Source.class);
                checkModule(source.getModuleId());
                checkLog(source.getProjectId());
                sourceService.update(source);
                break;         
        }
    }

    public String getProjectIdByLog(Log log){
            log = getById(log.getId());
            switch (log.getModelClass().toUpperCase()) {
                case "INTERFACEWITHBLOBS"://恢复接口
                case "INTERFACE"://恢复接口
                    JSONObject json = JSONObject.fromObject(log.getContent());
                    InterfaceWithBLOBs inter = (InterfaceWithBLOBs) JSONObject.toBean(json, InterfaceWithBLOBs.class);
                    return inter.getProjectId();
                case "ARTICLEWITHBLOBS":// 恢复文档
                case "ARTICLE":// 恢复文档
                    json = JSONObject.fromObject(log.getContent());
                    ArticleWithBLOBs article = (ArticleWithBLOBs) JSONObject.toBean(json, ArticleWithBLOBs.class);
                    return article.getProjectId();
                case "MODULEWITHBLOBS"://恢复模块
                case "MODULE"://恢复模块
                    json = JSONObject.fromObject(log.getContent());
                    ModulePO module = (ModulePO) JSONObject.toBean(json, ModulePO.class);
                    return module.getProjectId();
                case "PROJECTWITHBLOBS":
                case "PROJECT"://恢复日志
                    json = JSONObject.fromObject(log.getContent());
                    Log project = (Log) JSONObject.toBean(json, Log.class);
                    return project.getId();
                case "SOURCEWITHBLOBS":
                case "SOURCE"://恢复文件
                    json = JSONObject.fromObject(log.getContent());
                    Source source = (Source) JSONObject.toBean(json, Source.class);
                    return source.getProjectId();
            }
            return null;
    }

    private void checkModule(String moduleId) throws MyException {
        if (MyString.isEmpty(moduleId)){
            return;
        }
        Assert.notNull(moduleId);
        ModulePO module = moduleService.get(moduleId);
        if (module == null) {
            throw new MyException(MyError.E000048);
        }
    }

    private void checkLog(String projectId) throws MyException {
        Assert.notNull(projectId);
        ProjectPO project = projectService.get(projectId);
        if (project == null) {
            throw new MyException(MyError.E000049);
        }
    }
}
