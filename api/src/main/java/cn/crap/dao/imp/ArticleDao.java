package cn.crap.dao.imp;

import cn.crap.dao.IArticleDao;
import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.model.Article;

@Repository("articleDao")
public class ArticleDao extends BaseDao<Article> implements IArticleDao {
}