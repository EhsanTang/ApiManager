package cn.crap.query;

import java.util.List;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class DebugQuery extends BaseQuery<DebugQuery>{
    private String userId;

    private List<String> moduleIds;

    @Override
    public DebugQuery getQuery(){
        return this;
    }

    public List<String> getModuleIds() {
        return moduleIds;
    }

    public DebugQuery setModuleIds(List<String> moduleIds) {
        this.moduleIds = moduleIds;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public DebugQuery setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
