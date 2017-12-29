package cn.crap.utils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crap.framework.ThreadContext;
import org.apache.commons.codec.binary.Base64;


public class MyCookie {
	
	public static void deleteCookie(String key){
		Cookie[] allCookie= ThreadContext.request().getCookies();
		if(allCookie!=null&&allCookie.length!=0)
		 {
		     for(int i=0;i<allCookie.length;i++)
		     {
		          String keyname= allCookie[i].getName();
		          if(key.equals(keyname))
		          {
		        	  allCookie[i].setValue(null);
		        	  allCookie[i].setMaxAge(0);
		        	  allCookie[i].setPath("/");
		        	  ThreadContext.response().addCookie(allCookie[i]);
		          }
		      }
		 }
	}
	public static void addCookie(String key,String value, int time){
		addCookie(key,value,false, time);
	}
	public static void addCookie(String key,String value){
		addCookie(key,value,false,60*60*24*7);
	}
	public static void addCookie(String key,String value,boolean jiaMi){
		addCookie(key,value,jiaMi,60*60*24*7);
	}

	/**
	 * @param key
	 * @param value
	 * @param jiaMi
	 * @param time 单位 s
	 */
	public static void addCookie(String key,String value,boolean jiaMi, int time){
		if( MyString.isEmpty(key) || MyString.isEmpty(value)){
			return;
		}
		if(jiaMi){
			value = Aes.encrypt(value);
		}else{
			value =new String(new Base64().encode(value.getBytes()));
		}
		Cookie myCookie=new Cookie(key,value);
		myCookie.setMaxAge(time);
		// 多个路径下可以共享cookie
		myCookie.setPath("/");
		ThreadContext.response().addCookie(myCookie);
	}
	public static String getCookie(String key){
		return getCookie(key,false);
	}
	public static String getCookie(String key,boolean jiaMi){
		Cookie allCookie[]= ThreadContext.request().getCookies();
		if(allCookie!=null&&allCookie.length!=0)
		 {
		     for(int i=0;i<allCookie.length;i++)
		     {
		          String keyname= allCookie[i].getName();
		          if((key).equals(keyname))
		          {
					  if(allCookie[i].getValue()==null) {
						  return "";
					  }else if(jiaMi) {
						  return Aes.desEncrypt(allCookie[i].getValue());
					  }else {
						  return new String(new Base64().decode(allCookie[i].getValue()));
					  }
		          }
		         
		      }
		 }
		return "";
	}
}
