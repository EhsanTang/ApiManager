package cn.crap.query;

import cn.crap.framework.MyException;
import cn.crap.utils.SafetyUtil;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class SourceQuery extends BaseQuery<SourceQuery>{
    private String name;
    private String projectId;
    private String moduleId;

    @Override
    public SourceQuery getQuery(){
        return this;
    }

    public String getName() throws MyException{
        SafetyUtil.checkSqlParam(name);
        return name;
    }

    public SourceQuery setName(String name) {
        this.name = name;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public SourceQuery setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getModuleId() {
        return moduleId;
    }

    public SourceQuery setModuleId(String moduleId) {
        this.moduleId = moduleId;
        return this;
    }
}
