package cn.crap.query;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class ProjectMetaQuery extends BaseQuery<ProjectMetaQuery>{
    private String type;

    @Override
    public ProjectMetaQuery getQuery(){
        return this;
    }

    public String getType() {
        return type;
    }

    public ProjectMetaQuery setType(String type) {
        this.type = type;
        return this;
    }
}
