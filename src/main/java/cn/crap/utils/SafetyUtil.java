package cn.crap.utils;

import cn.crap.enu.MyError;
import cn.crap.framework.MyException;

/**
 * 安全校验代码
 *
 * @author Ehsan
 * @date 2018/6/30 14:42
 */
public class SafetyUtil {
    private static final String illegalWord = "'|exec|insert|delete|update|*|master|truncate|declare|;|,";
    private static final String[] illegalWords = illegalWord.split("\\|");

    public static void checkSqlParam(String param) throws MyException {
        if (param == null) {
            return;
        }
        for (String str : illegalWords) {
            if (param.indexOf(str) >= 0) {
                throw new MyException(MyError.E000070, "参数包含非法字符:" + str);
            }
        }
    }
}
