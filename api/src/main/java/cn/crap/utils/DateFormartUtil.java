package cn.crap.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 根据输入的字符串，日期，日期格式返回相应的日期字符串
 * @author TangTao
 *
 */
public class DateFormartUtil {
	//yyyyMMddHHmmSS
	//S毫秒
	//s秒
	public static String YYYY_MM_DD = "yyyy-MM-dd";
	public static String DDHH="ddHH";
	public static String YYYY_MM_DD_HH_mm_ss="yyyy-MM-dd HH:mm:ss";
	public static String YYYY_MM_DD_HH_mm="yyyy-MM-dd HH:mm";
	public static String YYYYMMDDHHmmss="yyyyMMddHHmmss";
	public static String HHmmss="HHmmss";

	public static boolean isWeekend(String date){
		SimpleDateFormat dateFormat1 = new SimpleDateFormat(YYYY_MM_DD);
		Date myDate1=null;
		try {
			myDate1 = dateFormat1.parse(date);
		} catch (ParseException e) {
			return false;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(myDate1);
		if(Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK)||Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)){
			return true;
		}else
			return false;
		
	}

	public static String getDateByFormat()
	{
		return getDateByFormat(new Date(),YYYY_MM_DD);
	}
	public static String getDateByFormatAddOneHour()
	{
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, 1);
		return getDateByFormat(c.getTime(),"DDHH");
	}
	public static String getDateByFormatAddOneDay(String date)
	{
		SimpleDateFormat dateFormat1 = new SimpleDateFormat(YYYY_MM_DD);
		Date myDate1=null;
		try {
			myDate1 = dateFormat1.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(myDate1);
		c.add(Calendar.DAY_OF_MONTH, 1);
		return getDateByFormat(c.getTime(),YYYY_MM_DD);
	}
	public static long getCurrentTimeMillis(String date,String format)
	{
		SimpleDateFormat dateFormat1 = new SimpleDateFormat(format);
		Date myDate1=null;
		try {
			myDate1 = dateFormat1.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(myDate1);
		
		return c.getTimeInMillis();
	}
	public static long getCurrentTimeMillis(String date)
	{
		SimpleDateFormat dateFormat1 = new SimpleDateFormat(YYYY_MM_DD_HH_mm_ss);
		Date myDate1=null;
		try {
			myDate1 = dateFormat1.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(myDate1);
		
		return c.getTimeInMillis();
	}
	
	public static String getDateByFormat(String date,String fromat)
	{
		SimpleDateFormat dateFormat1 = new SimpleDateFormat(YYYY_MM_DD_HH_mm_ss);
		Date myDate1=null;
		try {
			myDate1 = dateFormat1.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getDateByFormat(myDate1,fromat);
	}

	public static Date getByFormat(String date,String fromat)
	{
		if (MyString.isEmpty(date)){
			return new Date();
		}
		SimpleDateFormat dateFormat1 = new SimpleDateFormat(fromat);
		Date myDate1= new Date();
		try {
			myDate1 = dateFormat1.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myDate1;
	}

	public static String getDateByFormat(String date,String inFormat,String outFormat)
	{
		SimpleDateFormat dateInFormat = new SimpleDateFormat(inFormat);
		Date inDate=null;
		try {
			inDate = dateInFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return getDateByFormat(inDate,outFormat);
	}

	
	public static String getDateByFormatDecOneDay(String date)
	{
		SimpleDateFormat dateFormat1 = new SimpleDateFormat(YYYY_MM_DD);
		Date myDate1=null;
		try {
			myDate1 = dateFormat1.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(myDate1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return getDateByFormat(c.getTime(),YYYY_MM_DD);
	}
	public static String getChinaDate(String date){
		date=date.substring(5,date.length());
		date=date.replace("-", "月");
		date=date+"日";
		return date;
	}
	public static String getDateByTimeMillis(String str,String sformat){
		try{
		  SimpleDateFormat format = new SimpleDateFormat(sformat);
	      Long time=Long.valueOf(str);
	      String d = format.format(time);
	      return d;
		}catch(Exception e){
			return "";
		}
	}
	public static String getDateByFormat(String daf){
		return getDateByFormat(new Date(),daf);
	}
	public static String getDateByFormat(SimpleDateFormat daf){
		return   getDateByFormat(new Date(),daf);
	}
	public static String getDateByFormat(Date date,String daf){
		SimpleDateFormat dafe=new SimpleDateFormat(daf);
		return getDateByFormat(date,dafe);
	}
	public static String getDateByFormat(Date date,SimpleDateFormat daf){
		String dateStr=daf.format(date);
		return  dateStr;
	}
	public static String getDateByTimeMillis(Long str){
		if (str == null){
			return "";
		}
		return getDateByTimeMillis(str.toString(),YYYY_MM_DD_HH_mm);
	}
}
