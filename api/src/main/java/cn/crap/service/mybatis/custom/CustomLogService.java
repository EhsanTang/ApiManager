package cn.crap.service.mybatis.custom;

import cn.crap.adapter.ArticleAdapter;
import cn.crap.dao.imp.InterfaceDao;
import cn.crap.dao.mybatis.ArticleMapper;
import cn.crap.dao.mybatis.LogMapper;
import cn.crap.dao.mybatis.custom.CustomArticleMapper;
import cn.crap.dto.ArticleDto;
import cn.crap.enumeration.LogType;
import cn.crap.framework.MyException;
import cn.crap.model.Interface;
import cn.crap.model.mybatis.Log;
import cn.crap.model.Module;
import cn.crap.model.Source;
import cn.crap.model.mybatis.Article;
import cn.crap.model.mybatis.ArticleCriteria;
import cn.crap.model.mybatis.ArticleWithBLOBs;
import cn.crap.model.mybatis.Project;
import cn.crap.service.ILuceneService;
import cn.crap.service.imp.table.ModuleService;
import cn.crap.service.imp.table.SourceService;
import cn.crap.service.imp.tool.CacheService;
import cn.crap.service.mybatis.imp.MybatisArticleService;
import cn.crap.service.mybatis.imp.MybatisProjectService;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CustomLogService{

    @Autowired
    private InterfaceDao interfaceDao;
    @Autowired
    private MybatisArticleService articleService;
    @Autowired
    private MybatisProjectService projectService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private SourceService sourceService;
    @Autowired
    private LogMapper logMapper;

    public void recover(Log log) throws MyException{
        log = logMapper.selectByPrimaryKey(log.getId());
        switch(log.getModelClass().toUpperCase()){

            case "INTERFACE"://恢复接口
                JSONObject json = JSONObject.fromObject(log.getContent());
                Interface inter = (Interface) JSONObject.toBean(json,Interface.class);
                checkModuleAndProject(inter.getModuleId());
                interfaceDao.update(inter);
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
                sourceService.update(source);
                break;

        }
    }

    private void checkModuleAndProject(String moduleId) throws MyException {
        // 查看模块是否存在
        Module module = moduleService.get(moduleId);
        if( MyString.isEmpty(module.getId()) ){
            throw new MyException("000048");
        }

        // 查看项目是否存在
        if(  MyString.isEmpty( projectService.selectByPrimaryKey(module.getProjectId()).getId() ) ){
            throw new MyException("000049");
        }
    }
}
