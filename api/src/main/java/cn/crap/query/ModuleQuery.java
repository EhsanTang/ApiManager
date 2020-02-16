package cn.crap.query;

import cn.crap.framework.MyException;
import cn.crap.utils.SafetyUtil;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class ModuleQuery extends BaseQuery<ModuleQuery>{
    private String name;
    private String userId;
    private String uniKey;


    @Override
    public ModuleQuery getQuery(){
        return this;
    }

    public String getName() throws MyException{
        SafetyUtil.checkSqlParam(name);
        return name;
    }

    public ModuleQuery setName(String name) {
        this.name = name;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public ModuleQuery setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUniKey() {
        return uniKey;
    }

    public ModuleQuery setUniKey(String uniKey) {
        this.uniKey = uniKey;
        return this;
    }
}
