package cn.crap.query;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class CommentQuery extends BaseQuery<CommentQuery>{
    private String targetId;
    private String type;

    @Override
    public CommentQuery getQuery(){
        return this;
    }

    public String getTargetId() {
        return targetId;
    }

    public CommentQuery setTargetId(String targetId) {
        this.targetId = targetId;
        return this;
    }

    public CommentQuery setType(String type) {
        this.type = type;
        return this;
    }

    public String getType(){
        return type;
    }
}
