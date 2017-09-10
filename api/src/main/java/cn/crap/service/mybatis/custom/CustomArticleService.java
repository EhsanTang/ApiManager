package cn.crap.service.mybatis.custom;

import cn.crap.dao.mybatis.ArticleMapper;
import cn.crap.dao.mybatis.ArticleMapper;
import cn.crap.dao.mybatis.custom.CustomArticleMapper;
import cn.crap.enumeration.LogType;
import cn.crap.framework.SpringContextHolder;
import cn.crap.model.Log;
import cn.crap.model.Module;
import cn.crap.model.mybatis.Project;
import cn.crap.model.mybatis.ArticleCriteria;
import cn.crap.model.mybatis.Article;
import cn.crap.model.mybatis.ArticleCriteria;
import cn.crap.model.mybatis.ArticleWithBLOBs;
import cn.crap.service.ICacheService;
import cn.crap.service.imp.tool.CacheService;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import net.sf.json.JSONObject;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

@Service
public class CustomArticleService {
    @Autowired
    private ArticleMapper mapper;
    @Autowired
    private CustomArticleMapper customArticleMapper;
    @Autowired
    private CacheService cacheService;

    public int countByProjectId(String moduleId, String name, String type, String category) {
        Assert.notNull(moduleId, "moduleId can't be null");
        ArticleCriteria example = new ArticleCriteria();
        ArticleCriteria.Criteria criteria = example.createCriteria().andModuleIdEqualTo(moduleId);
        if (name != null) {
            criteria.andNameLike("%" + name + "%");
        }
        if (type != null) {
            criteria.andTypeLike("%" + type + "%");
        }
        if (category != null) {
            criteria.andCategoryLike("%" + category + "%");
        }
        return mapper.countByExample(example);
    }

    public List<Article> queryArticle(String moduleId, String name, String type, String category, Page page) {
        Assert.notNull(moduleId, "moduleId can't be null");
        return customArticleMapper.queryArticle(moduleId, name, type, category, page);
    }

    public Project getProject(String moduleId) {
        if (!MyString.isEmpty(moduleId)) {
            Module module = cacheService.getModule(moduleId);
            if (module != null){
                return cacheService.getProject(module.getProjectId());
            }
        }
        return new Project();
    }

    public String getProjectId(String moduleId) {
        if (!MyString.isEmpty(moduleId)) {
            Module module = cacheService.getModule(moduleId);
            if (module != null)
                return module.getProjectId();
        }
        return "";
    }

    public String getModuleName(String moduleId){
        if(!MyString.isEmpty(moduleId)){
            return getModule(moduleId).getName();
        }
        return "";
    }

    private Module getModule(String moduleId){
        if(!MyString.isEmpty(moduleId)){
            Module module = cacheService.getModule(moduleId);
            if(module!=null) {
                return module;
            }
        }
        return new Module();
    }


    /**
     * update article and add update log
     * @param model
     * @param modelName
     * @param remark
     */
    public void update(ArticleWithBLOBs model, String modelName, String remark) {
        Article dbModel = mapper.selectByPrimaryKey(model.getId());
        if(MyString.isEmpty(remark)) {
            remark = model.getName();
        }
        Log log = new Log(modelName, remark, LogType.UPDATE.name(), JSONObject.fromObject(dbModel).toString(),
                model.getClass().getSimpleName(), model.getId());
       // logDao.save(log); TODO
        mapper.updateByPrimaryKeyWithBLOBs(model);
    }

    public void delete(String id, String modelName, String remark){
        Assert.notNull(id);
        Article dbModel = mapper.selectByPrimaryKey(id);
        if(MyString.isEmpty(remark)) {
            remark = dbModel.getName();
        }
        Log log = new Log(modelName, remark, LogType.DELTET.name(), JSONObject.fromObject(dbModel).toString(),
                dbModel.getClass().getSimpleName(), dbModel.getId());
       // logDao.save(log);
        mapper.deleteByPrimaryKey(dbModel.getId());
    }
}
