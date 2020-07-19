package cn.crap.utils;

public class MyInteger {
    public static int getInt(Integer value, int defaultValue){
        if (value == null){
            return defaultValue;
        }
        return value;
    }

    public static int getInt(String value, int defValue){
        if (value == null){
            return defValue;
        }
        try{
            return Integer.parseInt(value);
        }catch (Exception e){
            e.printStackTrace();
            return defValue;
        }
    }

    public static int getInt(String valueStr, int defValue, int min, int max){
        if (valueStr == null){
            return defValue;
        }

        try{
            int value =  Integer.parseInt(valueStr);
            if (value < min){
                return min;
            }

            if (value > max){
                return max;
            }

            return value;
        }catch (Exception e){
            e.printStackTrace();
            return defValue;
        }
    }
}
