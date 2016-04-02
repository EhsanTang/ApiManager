package cn.crap.utils;

import java.util.List;

public class MyString {
	public static boolean isEquals(String tagValue,String value)
	{
		if(isEmpty(tagValue))
			return false;
		else if(tagValue.equals(value))
			return true;
		else
			return false;
	}
	public static boolean isEmpty(Object object)
	{
		if(object instanceof String){
			if(object == null||object.toString().trim().equals("")||object.toString().trim().equals("null")||object.toString().equals("undefined"))
				return true;
		}else if(object instanceof List<?>){
			if(object == null ||((List<?>)object).size()==0)
				return true;
		}else if(object == null){
			return true;
		}
		return false;
	}
}
