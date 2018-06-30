package cn.crap.dao.mybatis;

import cn.crap.model.Article;
import cn.crap.model.ArticleCriteria;
import cn.crap.model.ArticleWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao extends BaseDao<ArticleWithBLOBs>{
    int countByExample(ArticleCriteria example);

    int deleteByExample(ArticleCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(ArticleWithBLOBs record);

    int insertSelective(ArticleWithBLOBs record);

    List<ArticleWithBLOBs> selectByExampleWithBLOBs(ArticleCriteria example);

    List<Article> selectByExample(ArticleCriteria example);

    ArticleWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ArticleWithBLOBs record, @Param("example") ArticleCriteria example);

    int updateByExampleWithBLOBs(@Param("record") ArticleWithBLOBs record, @Param("example") ArticleCriteria example);

    int updateByExample(@Param("record") Article record, @Param("example") ArticleCriteria example);

    int updateByPrimaryKeySelective(ArticleWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ArticleWithBLOBs record);

    int updateByPrimaryKey(Article record);
}