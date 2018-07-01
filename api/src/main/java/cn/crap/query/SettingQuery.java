package cn.crap.query;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class SettingQuery extends BaseQuery<SettingQuery>{
    private String key;
    private String remark;

    @Override
    public SettingQuery getQuery(){
        return this;
    }

    public String getKey() {
        return key;
    }

    public SettingQuery setKey(String key) {
        this.key = key;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public SettingQuery setRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
