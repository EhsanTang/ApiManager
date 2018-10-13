package cn.crap.utils.table;

/**
 * 根据数据库表，生成各种数据
 * @author Ehsan
 * @date 2018/10/12 22:22
 */
public abstract class BaseTableUtil {

    public abstract boolean canHandle(String code);

    public abstract String hundle(String code, String data);

}
