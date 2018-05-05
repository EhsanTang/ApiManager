package cn.crap.model.mybatis;

import java.io.Serializable;

public class RoleWithBLOBs extends Role implements Serializable {
    private String auth;

    private String authName;

    private static final long serialVersionUID = 1L;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth == null ? null : auth.trim();
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName == null ? null : authName.trim();
    }
}