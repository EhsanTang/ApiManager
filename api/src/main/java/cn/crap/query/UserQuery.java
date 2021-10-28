package cn.crap.query;

import lombok.Getter;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class UserQuery extends BaseQuery<UserQuery>{
    @Getter
    private String thirdlyId;
    @Getter
    private String equalEmail;
    @Getter
    private Integer loginType;
    @Getter
    private String equalUserName;
    @Getter
    private String email;
    @Getter
    private String trueName;
    @Getter
    private String userName;
    @Getter
    private String notEqualId;
    @Getter
    private String orEmail;
    @Getter
    private String orUserName;
    @Getter
    private String orTrueName;

    @Override
    public UserQuery getQuery(){
        return this;
    }

    public UserQuery setThirdlyId(String thirdlyId) {
        this.thirdlyId = thirdlyId;
        return this;
    }

    public UserQuery setNotEqualId(String notEqualId) {
        this.notEqualId = notEqualId;
        return this;
    }

    public UserQuery setOrEmail(String orEmail) {
        this.orEmail = orEmail;
        return this;
    }

    public UserQuery setOrTrueName(String orTrueName) {
        this.orTrueName = orTrueName;
        return this;
    }

    public UserQuery setOrUserName(String orUserName) {
        this.orUserName = orUserName;
        return this;
    }


    public UserQuery setEqualEmail(String equalEmail) {
        this.equalEmail = equalEmail;
        return this;
    }


    public UserQuery setLoginType(Integer loginType) {
        this.loginType = loginType;
        return this;
    }


    public UserQuery setEqualUserName(String equalUserName) {
        this.equalUserName = equalUserName;
        return this;
    }


    public UserQuery setEmail(String email) {
        this.email = email;
        return this;
    }


    public UserQuery setTrueName(String trueName) {
        this.trueName = trueName;
        return this;
    }


    public UserQuery setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
