package cn.crap.utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class MyString {
	public static boolean isEquals(String tagValue,String value)
	{
		if(isEmpty(tagValue) || isEmpty(value))
			return false;
		else if(tagValue.equals(value))
			return true;
		else
			return false;
	}
	/**
	 * 判断对象是否为空
	 * Object = null
	 * String = "","null","undefined"
	 * List size=0
	 * @param object
	 * @return
	 */
	public static boolean isEmpty(Object object)
	{
		if(object instanceof String){
			if(object == null||object.toString().trim().equals("")||object.toString().trim().equalsIgnoreCase("null")||object.toString().equals("undefined"))
				return true;
		}else if(object instanceof List<?>){
			if(object == null ||((List<?>)object).size()==0)
				return true;
		}else if(object == null){
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(Object object){
		return !isEmpty(object);
	}

	public static boolean notEquals(String value, String targetValue){
		return !equals(value, targetValue);
	}

	public static boolean equals(String value, String targetValue){
		if (value == null && targetValue == null){
			return true;
		}
		if (value == null || targetValue == null){
			return false;
		}
		return value.equals(targetValue);
	}

	// 从request中获取值
	public static String getValueFromRequest(HttpServletRequest request, String name){
		return getValueFromRequest(request, name, "");
	}
	
	public static String getValueFromRequest(HttpServletRequest request, String name, String defValue){
		if( isEmpty(request.getParameter(name)) ){
			return defValue;
		}else{
			return request.getParameter(name).toString();
		}
	}
}
