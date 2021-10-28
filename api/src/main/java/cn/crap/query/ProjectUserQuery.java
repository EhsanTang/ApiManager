package cn.crap.query;

import lombok.Getter;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class ProjectUserQuery extends BaseQuery<ProjectUserQuery>{
    @Getter
    private String userId;

    @Getter
    private Byte type;

    @Getter
    private String projectUniKey;

    @Override
    public ProjectUserQuery getQuery(){
        return this;
    }


    public ProjectUserQuery setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public ProjectUserQuery setType(Byte type) {
        this.type = type;
        return this;
    }

    public ProjectUserQuery setProjectUniKey(String projectUniKey) {
        this.projectUniKey = projectUniKey;
        return this;
    }
}
