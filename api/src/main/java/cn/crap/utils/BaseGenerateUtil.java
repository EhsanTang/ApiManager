package cn.crap.utils;

/**
 * @author Ehsan
 * @date 2018/12/31 15:43
 */
public abstract class BaseGenerateUtil {
    public abstract boolean canHanle(String type);

    /**
     * 数据库表
     * @return
     */
    public abstract String hanle();
}
