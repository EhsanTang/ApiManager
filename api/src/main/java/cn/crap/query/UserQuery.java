package cn.crap.query;

/**
 * @author Ehsan
 * @date 2018/6/30 14:17
 */
public class UserQuery extends BaseQuery<UserQuery>{
    private String thirdlyId;
    private String equalEmail;
    private Integer loginType;
    private String equalUserName;
    private String email;
    private String trueName;
    private String userName;

    @Override
    public UserQuery getQuery(){
        return this;
    }

    public String getThirdlyId() {
        return thirdlyId;
    }

    public UserQuery setThirdlyId(String thirdlyId) {
        this.thirdlyId = thirdlyId;
        return this;
    }

    public String getEqualEmail() {
        return equalEmail;
    }

    public UserQuery setEqualEmail(String equalEmail) {
        this.equalEmail = equalEmail;
        return this;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public UserQuery setLoginType(Integer loginType) {
        this.loginType = loginType;
        return this;
    }

    public String getEqualUserName() {
        return equalUserName;
    }

    public UserQuery setEqualUserName(String equalUserName) {
        this.equalUserName = equalUserName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserQuery setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTrueName() {
        return trueName;
    }

    public UserQuery setTrueName(String trueName) {
        this.trueName = trueName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserQuery setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
