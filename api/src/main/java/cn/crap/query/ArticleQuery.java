package cn.crap.query;

import cn.crap.framework.MyException;
import cn.crap.utils.SafetyUtil;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class ArticleQuery extends BaseQuery<ArticleQuery>{
    private String projectId;
    private String name;
    private String moduleId;
    private String type;
    private String category;
    private String key;

    @Override
    public ArticleQuery getQuery(){
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public ArticleQuery setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getName() throws MyException {
        SafetyUtil.checkSqlParam(name);
        return name;
    }

    public ArticleQuery setName(String name) {
        this.name = name;
        return this;
    }

    public String getModuleId() {
        return moduleId;
    }

    public ArticleQuery setModuleId(String moduleId) {
        this.moduleId = moduleId;
        return this;
    }

    public String getType() {
        return type;
    }

    public ArticleQuery setType(String type) {
        this.type = type;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ArticleQuery setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getKey() {
        return key;
    }

    public ArticleQuery setKey(String key) {
        this.key = key;
        return this;
    }
}
