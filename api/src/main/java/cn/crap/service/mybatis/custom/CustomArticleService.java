package cn.crap.service.mybatis.custom;

import cn.crap.adapter.ArticleAdapter;
import cn.crap.dao.mybatis.ArticleMapper;
import cn.crap.dao.mybatis.custom.CustomArticleMapper;
import cn.crap.dto.SearchDto;
import cn.crap.enumeration.LogType;
import cn.crap.model.mybatis.*;
import cn.crap.model.mybatis.Module;
import cn.crap.model.mybatis.ArticleCriteria;
import cn.crap.service.ILuceneService;
import cn.crap.service.imp.tool.CacheService;
import cn.crap.service.mybatis.imp.MybatisLogService;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class CustomArticleService implements ILuceneService{
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
        ArticleCriteria example = new ArticleCriteria();
        ArticleCriteria.Criteria criteria = example.createCriteria().andModuleIdEqualTo(moduleId);
        if (!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(type)){
            criteria.andTypeEqualTo(type);
        }
        if (!StringUtils.isEmpty(category)){
            criteria.andCategoryEqualTo(category);
        }
        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        return mapper.selectByExample(example);
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

    public List<SearchDto> getAll() {
        return ArticleAdapter.getSearchDto(cacheService, mapper.selectByExampleWithBLOBs(new ArticleCriteria()));
    }

    @Override
    public String getLuceneType() {
        return "文章&数据字典";
    }

    @Override
    public List<SearchDto> getAllByProjectId(String projectId) {
        ArticleCriteria example = new ArticleCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);
        return  ArticleAdapter.getSearchDto(cacheService, mapper.selectByExampleWithBLOBs(example));
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

    public List<String> queryTop20Category(String moduleId, String type){
        return customArticleMapper.queryTop20Category(moduleId, type);
    }

    public void updateClickById(String id){
        Assert.notNull(id);
        customArticleMapper.updateClickById(id);
    }

    public ArticleWithBLOBs selectByKey(String key){
        ArticleCriteria example = new ArticleCriteria();
        example.createCriteria().andMkeyEqualTo(key);
        List<ArticleWithBLOBs> models = mapper.selectByExampleWithBLOBs(example);
        return  models.size() > 0 ? models.get(0) : null;
    }
}
