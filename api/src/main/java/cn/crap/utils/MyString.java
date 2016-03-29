package cn.crap.utils;

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
	public static boolean isEmpty(String tagValue)
	{
		if(tagValue == null||tagValue.trim().equals("")||tagValue.trim().equals("null"))
			return true;
		else
			return false;
	}
}
