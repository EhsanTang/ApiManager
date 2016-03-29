package cn.crap.utils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;


public class MyCookie {
	
	public static void deleteCookie(String key, HttpServletRequest request, HttpServletResponse response){
		Cookie[] allCookie= request.getCookies();
		
		if(allCookie!=null&&allCookie.length!=0)
		 {
		     for(int i=0;i<allCookie.length;i++)
		     {
		          String keyname= allCookie[i].getName();
		          if(key.equals(keyname))
		          {
		        	  allCookie[i].setValue(null);
		        	  allCookie[i].setMaxAge(0);
		        	  response.addCookie(allCookie[i]);
		          }
		         
		      }
		 }
	}
	public static void addCookie(String key,String value, HttpServletResponse response){
		addCookie(key,value,false, response);
	}
	public static void addCookie(String key,String value,boolean jiami, HttpServletResponse response){
		if(jiami) 
			value =new String(new Base64().encode(value.getBytes()));
		Cookie myCookie=new Cookie(key,value);
		myCookie.setMaxAge(60*60*24*7);
		response.addCookie(myCookie);
	}
	public static String getCookie(String key,HttpServletRequest request, HttpServletResponse response){
		return getCookie(key,false,request,response);
	}
	public static String getCookie(String key,boolean jiami, HttpServletRequest request, HttpServletResponse response){
		Cookie allCookie[]= request.getCookies();

		if(allCookie!=null&&allCookie.length!=0)
		 {
		     for(int i=0;i<allCookie.length;i++)
		     {
		          String keyname= allCookie[i].getName();
		          if((key).equals(keyname))
		          {
					  if(allCookie[i].getValue()==null)
						  return "";
					  else if(jiami)
						  return new String(new Base64().decode(allCookie[i].getValue()));
					  else
						  return allCookie[i].getValue();
		          }
		         
		      }
		 }
		return "";
	}
	public static Long getIdFromCookie(String idName,HttpServletRequest request, HttpServletResponse response){
		try{
		String params = MyCookie.getCookie("params", request, response);
			for(String param:params.split("&")){
				if(param.split("=")[0].equals(idName)){
					return Long.parseLong(param.split("=")[1]);
				}
			}
		}catch(Exception e){
			return null;
		}
		return null;
	}
}
