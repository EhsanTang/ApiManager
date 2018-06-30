package cn.crap.query;

import cn.crap.framework.MyException;
import cn.crap.utils.SafetyUtil;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class ProjectQuery extends BaseQuery<ProjectQuery>{
    private String name;
    private String userId;

    @Override
    public ProjectQuery getQuery(){
        return this;
    }

    public String getName() throws MyException{
        SafetyUtil.checkSqlParam(name);
        return name;
    }

    public ProjectQuery setName(String name) {
        this.name = name;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public ProjectQuery setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
