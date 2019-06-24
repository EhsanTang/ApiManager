package cn.crap.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MyString {

    public static String getStr(String value) {
        if (value == null) {
            return "";
        }
        if (isEmpty(value)) {
            return "";
        }
        return value;
    }

    public static boolean getBoolean(String value, boolean defValue) {
        if (value == null) {
            return defValue;
        }

        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            e.printStackTrace();
            return defValue;
        }
    }

    public static boolean isEquals(String tagValue, String value) {
        if (isEmpty(tagValue) || isEmpty(value))
            return false;
        else return tagValue.equals(value);
    }

    /**
     * 判断对象是否为空
     * Object = null
     * String = "","null","undefined"
     * List size=0
     *
     * @param object
     * @return
     */
    public static boolean isEmpty(Object object) {
        if (object instanceof String) {
            return object == null || object.toString().trim().equals("") || object.toString().trim().equalsIgnoreCase("null") || object.toString().equals("undefined");
        } else if (object instanceof List<?>) {
            return object == null || ((List<?>) object).size() == 0;
        } else return object == null;
    }

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    public static boolean notEquals(String value, String targetValue) {
        return !equals(value, targetValue);
    }

    public static boolean equals(String value, String targetValue) {
        if (value == null && targetValue == null) {
            return true;
        }
        if (value == null || targetValue == null) {
            return false;
        }
        return value.equals(targetValue);
    }

    // 从request中获取值
    public static String getValueFromRequest(HttpServletRequest request, String name) {
        return getValueFromRequest(request, name, "");
    }

    public static String getValueFromRequest(HttpServletRequest request, String name, String defValue) {
        if (isEmpty(request.getParameter(name))) {
            return defValue;
        } else {
            return request.getParameter(name);
        }
    }
}
