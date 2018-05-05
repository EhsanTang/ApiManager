package cn.crap.utils;

public class MyInteger {
    public static int getInt(Integer value, int defaultValue){
        if (value == null){
            return defaultValue;
        }
        return value;
    }
}
