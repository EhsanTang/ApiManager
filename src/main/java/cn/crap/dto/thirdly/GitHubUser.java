package cn.crap.dto.thirdly;

import java.io.Serializable;

public class GitHubUser implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String login;// 用户登录名
    private String id;// 用户唯一编码
    private String avatar_url;// 用户头像
    private String email;
    private String name;// 用户昵称

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}