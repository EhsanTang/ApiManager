package cn.crap.utils;

public class TableField {

    public final static String ID = "id";
    public final static String CREATE_TIME = "createTime";
    public final static String SEQUENCE = "sequence";

    public static class SORT {
        public final static String ID_ASC = "id asc";
        public final static String SEQUENCE_DESC = "sequence desc";
        public final static String ERROR_CODE_ASC = "errorCode asc";
        public final static String CREATE_TIME_DES = "createTime desc";
        public final static String TIMES_DESC = "times desc";

    }

    public static class USER {
        public final static String USER_TYPE = "userType"; // userType 只记录用户账号初始化来源
        public final static String EMAIL = "email";
        public final static String USER_NAME = "userName";
        public final static String LOGIN_TYPE = "loginTYpe";
        public final static String THIRDLY_ID = "thirdlyId"; // 第三方账号ID = 第三方前缀 + 唯一标识
        public final static String PASSWORD = "password"; // MD5 加密（旧数据），MD5+盐（新数据）
    }
}
