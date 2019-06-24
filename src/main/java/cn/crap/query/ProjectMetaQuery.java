package cn.crap.query;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class ProjectMetaQuery extends BaseQuery<ProjectMetaQuery> {
    private Byte type;

    @Override
    public ProjectMetaQuery getQuery() {
        return this;
    }

    public Byte getType() {
        return type;
    }

    public ProjectMetaQuery setType(Byte type) {
        this.type = type;
        return this;
    }
}
