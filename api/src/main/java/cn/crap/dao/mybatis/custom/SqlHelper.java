package cn.crap.dao.mybatis.custom;

public class SqlHelper {
    public static String checkParams(String param){
        param = param.replaceAll("'", "");
        param = param.replaceAll("\\(", "");
        param = param.replaceAll("\\)", "");
        param = param.replaceAll("drop", "");
        param = param.replaceAll("delete", "");
        param = param.replaceAll("update", "");
        param = param.replaceAll("insert", "");
        param = param.replaceAll("exec", "");
        return param;
    }
}
