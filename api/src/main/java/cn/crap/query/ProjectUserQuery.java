package cn.crap.query;

import cn.crap.framework.MyException;
import cn.crap.utils.SafetyUtil;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class ProjectUserQuery extends BaseQuery<ProjectUserQuery>{
    private String userId;

    @Override
    public ProjectUserQuery getQuery(){
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public ProjectUserQuery setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
