package cn.crap.service.mybatis.custom;

import cn.crap.adapter.ArticleAdapter;
import cn.crap.dao.mybatis.ArticleMapper;
import cn.crap.dao.mybatis.ArticleMapper;
import cn.crap.dao.mybatis.custom.CustomArticleMapper;
import cn.crap.dto.ArticleDto;
import cn.crap.enumeration.LogType;
import cn.crap.framework.SpringContextHolder;
import cn.crap.model.mybatis.*;
import cn.crap.model.mybatis.Module;
import cn.crap.model.mybatis.ArticleCriteria;
import cn.crap.service.ICacheService;
import cn.crap.service.ILuceneService;
import cn.crap.service.imp.tool.CacheService;
import cn.crap.service.mybatis.imp.MybatisLogService;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import cn.crap.utils.Tools;
import net.sf.json.JSONObject;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.util.Collections;
import java.util.List;


@Service
public class CustomArticleService implements ILuceneService<ArticleDto> {
    @Autowired
    private ArticleMapper mapper;
    @Autowired
    private CustomArticleMapper customArticleMapper;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private MybatisLogService logService;

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
// TODO 提取代码
            Log log = new Log();
            log.setModelName(modelName);
            log.setRemark(remark);
            log.setType(LogType.UPDATE.name());
            log.setContent(JSONObject.fromObject(dbModel).toString());
            log.setModelClass(dbModel.getClass().getSimpleName());

        logService.insert(log);
        mapper.updateByPrimaryKeyWithBLOBs(model);
    }

    public void delete(String id, String modelName, String remark){
        Assert.notNull(id);
        Article dbModel = mapper.selectByPrimaryKey(id);
        if(MyString.isEmpty(remark)) {
            remark = dbModel.getName();
        }
        Log log = new Log();
        log.setModelName(modelName);
        log.setRemark(remark);
        log.setType(LogType.DELTET.name());
        log.setContent(JSONObject.fromObject(dbModel).toString());
        log.setModelClass(dbModel.getClass().getSimpleName());

       logService.insert(log);
        mapper.deleteByPrimaryKey(dbModel.getId());
    }

    public List<ArticleDto> getAll() {
        return ArticleAdapter.getDto(mapper.selectByExample(new ArticleCriteria()));
    }

    @Override
    public String getLuceneType() {
        return "文章&数据字典";
    }

    @Override
    public List<ArticleDto> getAllByProjectId(String projectId) {
        ArticleCriteria example = new ArticleCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);
        return  ArticleAdapter.getDto(mapper.selectByExample(example));
    }

    public Integer countByModuleIdAndType(String moduleId, String type){
        Assert.notNull(moduleId);
        Assert.notNull(type);
        ArticleCriteria example = new ArticleCriteria();
        example.createCriteria().andModuleIdEqualTo(moduleId).andTypeEqualTo(type);
        return mapper.countByExample(example);
    }

    public List<Article> queryByModuleIdAndType(String moduleId, String type){
        Assert.notNull(moduleId);
        Assert.notNull(type);
        ArticleCriteria example = new ArticleCriteria();
        example.createCriteria().andModuleIdEqualTo(moduleId).andTypeEqualTo(type);
        return mapper.selectByExample(example);
    }

    public List<String> queryArticleCatetoryByModuleIdAndType(String moduleId, String type){
        return customArticleMapper.queryArticleCatetoryByModuleIdAndType(moduleId, type);
    }

    public void updateClickById(String id){
        Assert.notNull(id);
        customArticleMapper.updateClickById(id);
    }
}
