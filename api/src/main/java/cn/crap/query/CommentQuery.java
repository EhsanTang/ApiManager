package cn.crap.query;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class CommentQuery extends BaseQuery<CommentQuery>{
    private String articleId;
    @Override
    public CommentQuery getQuery(){
        return this;
    }

    public String getArticleId() {
        return articleId;
    }

    public CommentQuery setArticleId(String articleId) {
        this.articleId = articleId;
        return this;
    }
}
