package cn.crap.service.custom;

import cn.crap.adapter.Adapter;
import cn.crap.adapter.ArticleAdapter;
import cn.crap.dao.mybatis.ArticleDao;
import cn.crap.dao.custom.CustomArticleDao;
import cn.crap.dto.SearchDto;
import cn.crap.enumer.LogType;
import cn.crap.model.mybatis.*;
import cn.crap.model.mybatis.Module;
import cn.crap.model.mybatis.ArticleCriteria;
import cn.crap.service.ILuceneService;
import cn.crap.service.tool.ModuleCache;
import cn.crap.service.tool.ProjectCache;
import cn.crap.service.mybatis.LogService;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CustomArticleService implements ILuceneService {
    @Autowired
    private ArticleDao dao;
    @Autowired
    private CustomArticleDao customArticleMapper;
    @Resource(name = "projectCache")
    protected ProjectCache projectCache;
    @Resource(name = "moduleCache")
    protected ModuleCache moduleCache;
    @Autowired
    private LogService logService;

    public List<String> queryTop10RecommendCategory(){
        return customArticleMapper.queryTop10RecommendCategory();
    }

    public int countByModuleId(String moduleId, String name, String type, String category, Byte status) {
        ArticleCriteria example = new ArticleCriteria();
        ArticleCriteria.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(moduleId)){
            criteria.andModuleIdEqualTo(moduleId);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(type)) {
            criteria.andTypeEqualTo(type);
        }
        if (!StringUtils.isEmpty(category)) {
            criteria.andCategoryEqualTo(category);
        }
        if (status != null){
            criteria.andStatusEqualTo(status);
        }

        return dao.countByExample(example);
    }

    public List<Article> queryArticle(String moduleId, String name, String type, String category, Byte status, Page page) {
        ArticleCriteria example = new ArticleCriteria();
        ArticleCriteria.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(moduleId)){
            criteria.andModuleIdEqualTo(moduleId);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(type)) {
            criteria.andTypeEqualTo(type);
        }
        if (!StringUtils.isEmpty(category)) {
            criteria.andCategoryEqualTo(category);
        }
        if (status != null){
            criteria.andStatusEqualTo(status);
        }
        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
        return dao.selectByExample(example);
    }

    /**
     * update article and add update log
     *
     * @param model
     * @param modelName
     * @param remark
     */
    public void update(ArticleWithBLOBs model, String modelName, String remark) {
        Article dbModel = dao.selectByPrimaryKey(model.getId());
        if (MyString.isEmpty(remark)) {
            remark = model.getName();
        }

        Log log = Adapter.getLog(dbModel.getId(), modelName, remark, LogType.UPDATE, dbModel.getClass(), dbModel);
        logService.insert(log);

        dao.updateByPrimaryKeySelective(model);
    }

    public void delete(String id, String modelName, String remark) {
        Assert.notNull(id);
        Article dbModel = dao.selectByPrimaryKey(id);
        if (MyString.isEmpty(remark)) {
            remark = dbModel.getName();
        }
        Log log = Adapter.getLog(dbModel.getId(), modelName, remark, LogType.DELTET, dbModel.getClass(), dbModel);
        logService.insert(log);

        dao.deleteByPrimaryKey(dbModel.getId());
    }

    public List<SearchDto> getAll() {
        return ArticleAdapter.getSearchDto(dao.selectByExampleWithBLOBs(new ArticleCriteria()));
    }

    @Override
    public String getLuceneType() {
        return "文章&数据字典";
    }

    @Override
    public List<SearchDto> getAllByProjectId(String projectId) {
        ArticleCriteria example = new ArticleCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);
        return ArticleAdapter.getSearchDto(dao.selectByExampleWithBLOBs(example));
    }

    public Integer countByModuleIdAndType(String moduleId, String type) {
        Assert.notNull(moduleId);
        Assert.notNull(type);
        ArticleCriteria example = new ArticleCriteria();
        example.createCriteria().andModuleIdEqualTo(moduleId).andTypeEqualTo(type);
        return dao.countByExample(example);
    }

    public List<Article> queryByModuleIdAndType(String moduleId, String type) {
        Assert.notNull(moduleId);
        Assert.notNull(type);
        ArticleCriteria example = new ArticleCriteria();
        example.createCriteria().andModuleIdEqualTo(moduleId).andTypeEqualTo(type);
        return dao.selectByExample(example);
    }

    /**
     * 跟新点击量
     *
     * @param id
     */
    public void updateClickById(String id) {
        Assert.notNull(id);
        customArticleMapper.updateClickById(id);
    }

    /**
     * 将非PAGE类型的article key修改为null
     * @param id
     */
    public void updateTypeToNullById(String id){
        Assert.notNull(id);
        customArticleMapper.updateTypeToNullById(id);
    }

    public ArticleWithBLOBs selectByKey(String key) {
        ArticleCriteria example = new ArticleCriteria();
        example.createCriteria().andMkeyEqualTo(key);
        List<ArticleWithBLOBs> models = dao.selectByExampleWithBLOBs(example);
        return models.size() > 0 ? models.get(0) : null;
    }
}
