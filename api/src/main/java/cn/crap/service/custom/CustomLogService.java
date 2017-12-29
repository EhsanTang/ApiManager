package cn.crap.service.custom;

import cn.crap.dao.mybatis.InterfaceDao;
import cn.crap.dao.mybatis.LogDao;
import cn.crap.dao.mybatis.SourceDao;
import cn.crap.enumer.LogType;
import cn.crap.framework.MyException;
import cn.crap.model.mybatis.*;
import cn.crap.service.imp.MybatisArticleService;
import cn.crap.service.imp.MybatisModuleService;
import cn.crap.service.imp.MybatisProjectService;
import cn.crap.utils.MyString;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomLogService{

    @Autowired
    private MybatisArticleService articleService;
    @Autowired
    private MybatisProjectService projectService;
    @Autowired
    private MybatisModuleService moduleService;
    @Autowired
    private LogDao logMapper;
    @Autowired
    private InterfaceDao interfaceMapper;
    @Autowired
    private SourceDao sourceMapper;

    public boolean saveLog(String modelName, String content, String remark, LogType logType, Class clazz){
        Log log = new Log();
        log.setModelName(modelName);
        log.setRemark(remark);
        log.setType(logType.name());
        log.setContent(content);
        log.setModelClass(clazz.getSimpleName());

        logMapper.insert(log);
        return true;
    }

    public void recover(Log log) throws MyException{
        log = logMapper.selectByPrimaryKey(log.getId());
        switch(log.getModelClass().toUpperCase()){

            case "INTERFACE"://恢复接口
                JSONObject json = JSONObject.fromObject(log.getContent());
                InterfaceWithBLOBs inter = (InterfaceWithBLOBs) JSONObject.toBean(json,Interface.class);
                checkModuleAndProject(inter.getModuleId());
                interfaceMapper.updateByPrimaryKeyWithBLOBs(inter);
                break;

            case "ARTICLE":// 恢复文章
                json = JSONObject.fromObject(log.getContent());
                ArticleWithBLOBs article = (ArticleWithBLOBs) JSONObject.toBean(json,ArticleWithBLOBs.class);
                checkModuleAndProject(article.getModuleId());
                // key有唯一约束，不置为null会报错
                if( MyString.isEmpty(article.getMkey())){
                    article.setMkey(null);
                }
                articleService.update(article);
                break;

            case "MODULE"://恢复模块
                json = JSONObject.fromObject(log.getContent());
                Module module = (Module) JSONObject.toBean(json,Module.class);

                // 查看项目是否存在
                if(  MyString.isEmpty( projectService.selectByPrimaryKey(module.getProjectId()).getId() ) ){
                    throw new MyException("000049");
                }

                // 模块不允许恢复修改操作
                if( !log.getType().equals(LogType.DELTET.name()) ){
                    throw new MyException("000050");
                }

                moduleService.update(module);
                break;

            case "PROJECT"://恢复项目
                json = JSONObject.fromObject(log.getContent());
                Project project = (Project) JSONObject.toBean(json,Project.class);
                projectService.update(project);
                break;

            case "SOURCE"://恢复资源
                json = JSONObject.fromObject(log.getContent());
                Source source = (Source) JSONObject.toBean(json,Source.class);
                checkModuleAndProject(source.getModuleId());
                sourceMapper.updateByPrimaryKey(source);
                break;

        }
    }

    private void checkModuleAndProject(String moduleId) throws MyException {
        // 查看模块是否存在
        Module module = moduleService.selectByPrimaryKey(moduleId);
        if( MyString.isEmpty(module.getId()) ){
            throw new MyException("000048");
        }

        // 查看项目是否存在
        if(  MyString.isEmpty( projectService.selectByPrimaryKey(module.getProjectId()).getId() ) ){
            throw new MyException("000049");
        }
    }
}
