package cn.crap.service;

import cn.crap.adapter.Adapter;
import cn.crap.adapter.ArticleAdapter;
import cn.crap.dao.custom.CustomArticleDao;
import cn.crap.dao.mybatis.ArticleDao;
import cn.crap.dto.SearchDto;
import cn.crap.enu.ArticleStatus;
import cn.crap.enu.CanDeleteEnum;
import cn.crap.enu.LogType;
import cn.crap.enu.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.Article;
import cn.crap.model.ArticleCriteria;
import cn.crap.model.ArticleWithBLOBs;
import cn.crap.model.Log;
import cn.crap.query.ArticleQuery;
import cn.crap.service.tool.ModuleCache;
import cn.crap.service.tool.ProjectCache;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class ArticleService extends BaseService<ArticleWithBLOBs, ArticleDao> implements ILuceneService, IConst {

    private ArticleDao articleDao;
    @Autowired
    private CustomArticleDao customArticleMapper;
    @Resource(name = "projectCache")
    protected ProjectCache projectCache;
    @Resource(name = "moduleCache")
    protected ModuleCache moduleCache;
    @Autowired
    private LogService logService;

    @Resource
    public void ArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
        super.setBaseDao(articleDao, TableId.ARTICLE);
    }

    /**
     * 新增
     * @param model
     * @return
     */
    public boolean insert(ArticleWithBLOBs model) throws MyException{
        if (model == null) {
            return false;
        }
        if (model.getCanDelete() == null) {
            model.setCanDelete(CanDeleteEnum.CAN.getCanDelete());
        }
        if (model.getMarkdown() == null) {
            model.setMarkdown("");
        }
        if (model.getContent() == null){
            model.setContent("");
        }
        if (model.getStatus() == null) {
            model.setStatus(ArticleStatus.COMMON.getStatus());
        }
        if (!ArticleStatus.PAGE.getStatus().equals(model.getStatus())) {
            model.setMkey(null);
        }
        if (model.getSequence() == null){
            List<Article>  models = this.query(new ArticleQuery().setPageSize(1).setProjectId(model.getProjectId()));
            if (models.size() > 0){
                model.setSequence(models.get(0).getSequence() + 1);
            }else{
                model.setSequence(0);
            }
        }
        return super.insert(model);
    }

    /**
     * 查询文章
     * @param query
     * @return
     * @throws MyException
     */
    public List<Article> query(ArticleQuery query) throws MyException {
        Assert.notNull(query);
        Page page = new Page(query);
        ArticleCriteria example = getArticleCriteria(query);
        if (page.getSize() != ALL_PAGE_SIZE){
            example.setLimitStart(page.getStart());
            example.setMaxResults(page.getSize());
        }
        example.setOrderByClause(query.getSort() == null ? TableField.SORT.SEQUENCE_DESC : query.getSort());
        return articleDao.selectByExample(example);
    }

    /**
     * 查询文章数量
     * @param query
     * @return
     * @throws MyException
     */
    public int count(ArticleQuery query) throws MyException {
        Assert.notNull(query);

        ArticleCriteria example = getArticleCriteria(query);
        return articleDao.countByExample(example);
    }

    private ArticleCriteria getArticleCriteria(ArticleQuery query) throws MyException {
        ArticleCriteria example = new ArticleCriteria();
        ArticleCriteria.Criteria criteria = example.createCriteria();
        if (query.getName() != null) {
            criteria.andNameLike("%" + query.getName() + "%");
        }
        if (query.getStatus() != null) {
            criteria.andStatusEqualTo(query.getStatus());
        }
        if (query.getProjectId() != null) {
            criteria.andProjectIdEqualTo(query.getProjectId());
        }
        if (query.getModuleId() != null) {
            criteria.andModuleIdEqualTo(query.getModuleId());
        }
        if (query.getType() != null) {
            criteria.andTypeEqualTo(query.getType());
        }
        if (MyString.isNotEmpty(query.getCategory())) {
            criteria.andCategoryEqualTo(query.getCategory());
        }
        if (query.getKey() != null) {
            criteria.andMkeyEqualTo(query.getKey());
        }

        return example;
    }
    


    public List<String> queryTop10RecommendCategory(){
        return customArticleMapper.queryTop10RecommendCategory();
    }


    /**
     * update article and add update log
     *
     * @param model
     * @param modelName
     * @param remark
     */
    public void update(ArticleWithBLOBs model, String modelName, String remark) throws MyException{
        Article dbModel = getById(model.getId());
        if (MyString.isEmpty(remark)) {
            remark = model.getName();
        }

        Log log = Adapter.getLog(dbModel.getId(), modelName, remark, LogType.UPDATE, dbModel.getClass(), dbModel);
        logService.insert(log);

        super.update(model);
    }

    /**
     * 更新属性
     * @param id
     * @param attributeKey
     * @param attributeValue
     * @throws MyException
     */
    public void updateAttribute(String id, String attributeKey, String attributeValue) throws MyException{
        Assert.notNull(id);
        Assert.notNull(attributeKey);
        if (MyString.isEmpty(attributeKey)) {
            return;
        }
        Article dbModel = getById(id);
        Map<String, String> attributeMap = AttributeUtils.getAttributeMap(dbModel.getAttributes());
        attributeMap.put(attributeKey, attributeValue);

        ArticleWithBLOBs model = new ArticleWithBLOBs();
        model.setId(id);
        model.setAttributes(AttributeUtils.getAttributeStr(attributeMap));

        super.update(model);
    }

    /**
     * 删除属性值
     * @param id
     * @param attributeKey
     * @throws MyException
     */
    public void deleteAttribute(String id, String attributeKey) throws MyException{
        Assert.notNull(id);
        Assert.notNull(attributeKey);
        if (MyString.isEmpty(attributeKey)) {
            return;
        }
        Article dbModel = getById(id);
        Map<String, String> attributeMap = AttributeUtils.getAttributeMap(dbModel.getAttributes());
        attributeMap.remove(attributeKey);

        ArticleWithBLOBs model = new ArticleWithBLOBs();
        model.setId(id);
        model.setAttributes(AttributeUtils.getAttributeStr(attributeMap));
        super.update(model);
    }


    public void delete(String id, String modelName, String remark) throws MyException{
        Assert.notNull(id);
        Article dbModel = getById(id);
        if (MyString.isEmpty(remark)) {
            remark = dbModel.getName();
        }
        super.delete(id);

        Log log = Adapter.getLog(dbModel.getId(), modelName, remark, LogType.DELTET, dbModel.getClass(), dbModel);
        logService.insert(log);
    }

    public List<SearchDto> getAll() {
        return ArticleAdapter.getSearchDto(articleDao.selectByExampleWithBLOBs(new ArticleCriteria()));
    }

    @Override
    public String getLuceneType() {
        return "文章&数据库表";
    }

    @Override
    public List<SearchDto> getAllByProjectId(String projectId) {
        ArticleCriteria example = new ArticleCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);
        return ArticleAdapter.getSearchDto(articleDao.selectByExampleWithBLOBs(example));
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

    public List<Article> queryTop100Page(){
        ArticleCriteria articleCriteria = new ArticleCriteria();
        articleCriteria.createCriteria().andStatusEqualTo(ArticleStatus.PAGE.getStatus()).andMkeyIsNotNull();
        articleCriteria.setMaxResults(100);
        articleCriteria.setLimitStart(0);
        return articleDao.selectByExample(articleCriteria);
    }
}
