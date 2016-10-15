package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.IArticleDao;
import cn.crap.model.Article;

@Repository("articleDao")
public class ArticleDao extends BaseDao<Article> implements IArticleDao {
}