package cn.crap.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class UserPO extends BasePO {
    private String userName;

    private String password;

    private String trueName;

    private String roleId;

    private String roleName;

    private String auth;

    private String authName;

    @Getter
    @Setter
    private Byte status;

    @Getter
    @Setter
    private Byte type;

    private String email;

    private String avatarUrl;

    @Getter
    @Setter
    private Integer loginType;

    private String thirdlyId;

    private String passwordSalt;

    @Getter
    @Setter
    private Date loginTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName == null ? null : trueName.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    public String getThirdlyId() {
        return thirdlyId;
    }

    public void setThirdlyId(String thirdlyId) {
        this.thirdlyId = thirdlyId == null ? null : thirdlyId.trim();
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt == null ? null : passwordSalt.trim();
    }
}