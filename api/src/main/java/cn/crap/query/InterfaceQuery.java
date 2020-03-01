package cn.crap.query;

import cn.crap.framework.MyException;
import cn.crap.utils.SafetyUtil;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class InterfaceQuery extends BaseQuery<InterfaceQuery>{
    private String fullUrl;
    private String exceptId;
    private String interfaceName;
    private String equalFullUrl;
    private String exceptVersion;
    private String equalInterfaceName;
    private String uniKey;

    @Override
    public InterfaceQuery getQuery(){
        return this;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public InterfaceQuery setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
        return this;
    }

    public String getExceptId() {
        return exceptId;
    }

    public InterfaceQuery setExceptId(String exceptId) {
        this.exceptId = exceptId;
        return this;
    }

    public String getInterfaceName() throws MyException{
        SafetyUtil.checkSqlParam(interfaceName);
        return interfaceName;
    }

    public InterfaceQuery setInterfaceName(String interfaceName) {
        this.interfaceName = (interfaceName == null ? null : interfaceName.trim());
        return this;
    }

    public String getEqualFullUrl() {
        return equalFullUrl;
    }

    public InterfaceQuery setEqualFullUrl(String equalFullUrl) {
        this.equalFullUrl = equalFullUrl;
        return this;
    }

    public String getExceptVersion() {
        return exceptVersion;
    }

    public InterfaceQuery setExceptVersion(String exceptVersion) {
        this.exceptVersion = exceptVersion;
        return this;
    }

    public String getEqualInterfaceName() {
        return equalInterfaceName;
    }

    public InterfaceQuery setEqualInterfaceName(String equalInterfaceName) {
        this.equalInterfaceName = equalInterfaceName;
        return this;
    }

    public String getUniKey() {
        return uniKey;
    }

    public InterfaceQuery setUniKey(String uniKey) {
        this.uniKey = uniKey;
        return this;
    }
}
