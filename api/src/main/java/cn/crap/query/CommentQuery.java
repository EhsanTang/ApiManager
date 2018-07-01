package cn.crap.query;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class CommentQuery extends BaseQuery<CommentQuery>{
    private String articelId;
    @Override
    public CommentQuery getQuery(){
        return this;
    }

    public String getArticelId() {
        return articelId;
    }

    public CommentQuery setArticelId(String articelId) {
        this.articelId = articelId;
        return this;
    }
}
