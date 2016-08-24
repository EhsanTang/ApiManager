package cn.crap.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.crap.dto.CrumbDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.framework.MyException;
import cn.crap.framework.SpringContextHolder;
import cn.crap.inter.service.ICacheService;
import cn.crap.service.CacheService;


public class Tools {
	
	/**
	 * 构造查询的id
	 * @param roleName
	 * @return
	 */
	public static List<String> getIdsFromField(String ids) {
		return Arrays.asList(ids.split(","));
	}
	
	// 获取图形验证码
	public static String getImgCode(HttpServletRequest request) throws MyException{
		ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
		String timesStr = cacheService.getStr(Const.CACHE_IMGCODE_TIMES + MyCookie.getCookie(Const.COOKIE_UUID, false, request));
		int times = 0;
		if(timesStr != null){
			times = Integer.parseInt(timesStr.toString()) + 1;
		}
		if(times > 3){
			throw new MyException("000011");
		}
		cacheService.setStr(Const.CACHE_IMGCODE_TIMES + MyCookie.getCookie(Const.COOKIE_UUID, false, request), times + "", 10 * 60);
		String imgCode = cacheService.getStr(Const.CACHE_IMGCODE + MyCookie.getCookie(Const.COOKIE_UUID, false, request));
		return imgCode == null? System.currentTimeMillis()+"" : imgCode.toString();
	}
	/**
	 * 查询是否拥有权限
	 */
	public static boolean hasAuth(String authPassport, String moduleId) throws MyException {
		return hasAuth(authPassport, moduleId, Tools.getRequest());
	}
	public static boolean hasAuth(String authPassport,
			String moduleId, HttpServletRequest request) throws MyException {
		ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
		LoginInfoDto user = Tools.getUser();
		if(user == null ){
			throw new MyException("000003");
		}
		
		String authority = user.getAuthStr();
		if( user != null && (","+user.getRoleId()).indexOf(","+Const.SUPER+",")>=0){
			return true;//超级管理员
		}
		
		// 修改自己创建的模块
		if(!MyString.isEmpty(moduleId) && cacheService.getModule(moduleId).getUserId().equals(user.getId())){
			return true;
		}
		
		// 管理员修改自己的资料
		if(authPassport.equals("USER") && request != null){
			// 如果session中的管理员id和参数中的id一致
			if( MyString.isEquals(  user.getId(),  MyString.getValueFromRequest(request, "id", "-1")  )  ){
				return true;
			}
		}
		
		// 普通用户没有其他访问的权限
		if(Tools.getUser().getType() != 100){
			throw new MyException("000003");
		}
		
		String needAuth = authPassport.replace(Const.MODULEID, moduleId);
		if(authority.indexOf(","+needAuth+",")>=0){
			return true;
		}
		throw new MyException("000003");
	}
	
	/**********************模块访问密码***************************/
	public static void canVisitModule(String modulePassword,String password, String visitCode, HttpServletRequest request) throws MyException{
		ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
		String temPwd = cacheService.getStr(Const.CACHE_TEMP_PWD + MyCookie.getCookie(Const.COOKIE_UUID, false, request));
		if(!MyString.isEmpty(modulePassword)){
			if(!MyString.isEmpty(temPwd)&&temPwd.toString().equals(modulePassword)){
				return;
			}
			if(MyString.isEmpty(password)||!password.equals(modulePassword)){
				throw new MyException("000007");
			}
			if(cacheService.getSetting(Const.SETTING_VISITCODE).getValue().equals("true")){
				Object imgCode = getImgCode(request);
				if(MyString.isEmpty(visitCode)||imgCode==null||!visitCode.equals(imgCode.toString())){
					throw new MyException("000007");
				}
			}
			cacheService.setStr(Const.CACHE_TEMP_PWD + MyCookie.getCookie(Const.COOKIE_UUID, false, request), password, 10 * 60);
		}
	}
	/**
	 * 构造查询Map集合
	 * @param params 不定数量参数 格式(key1,value1,key2,value2....)
	 * @return
	 */
	public static Map<String, Object> getMap(Object... params) {
		if (params.length == 0 || params.length % 2 != 0) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < params.length; i = i + 2) {
			if (!MyString.isEmpty(params[i + 1]))
				map.put(params[i].toString(), params[i + 1]);
		}
		return map;

	}
	
	/**
	 * 构造导航条
	 */
	public static List<CrumbDto> getCrumbs(String... params) {
		 List<CrumbDto> crumbDtos = new ArrayList<CrumbDto>();
		if (params.length == 0 || params.length % 2 != 0) {
			return crumbDtos;
		}
		for (int i = 0; i < params.length; i = i + 2) {
			if (!MyString.isEmpty(params[i + 1])){
				CrumbDto crumb = new CrumbDto(params[i], params[i + 1]);
				crumbDtos.add(crumb);
			}
		}
		return crumbDtos;

	}
	//查询需要添加过滤器status>-1
	public static String getHql(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		if (map == null || map.size() == 0) {
			return " where status>-1 ";
		}
		hql.append(" where ");
		if (!map.containsKey("status")) {
			hql.append(" status>-1 and ");
		}
		List<String> removes = new ArrayList<String>();
		for (Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			if (key.indexOf("|") > 0) {
				String[] keys = key.split("\\|");
				keys[0] = keys[0].replaceAll("\\.", "_");
				
				if (keys[1].equals("in")) {
					hql.append(keys[0] + " in (:" + keys[0]+"_in) and ");
				} 
				
				else if (keys[1].equals(Const.NULL)) {
					hql.append(keys[0] + " =null and ");
					removes.add(key);
				} 
				
				else if (keys[1].equals(Const.NOT_NULL)) {
					hql.append(keys[0] + "!=null and ");
					removes.add(key);
				} 
				
				else if (keys[1].equals(Const.BLANK)) {
					hql.append(keys[0] + " ='' and ");
					removes.add(key);
				} else if (keys[1].equals("like")) {
					hql.append(keys[0] + " like :" + keys[0] + " and ");
				} else {
					hql.append(keys[0] + " " + keys[1] + ":"+ keys[0]+ " and ");
				}
			} else
				hql.append(key + "=:" + key.replaceAll("\\.", "_") + " and ");
		}
		if (map.size() > 0) {
			hql.replace(hql.lastIndexOf("and"), hql.length(), "");
		}
		for (String remove : removes)
			map.remove(remove);
		return hql.toString();
	}

	public static void setPage(Query query, Page pageBean) {
		if (pageBean != null) {
			query.setFirstResult(pageBean.getStart()).setMaxResults(
					pageBean.getSize());
		}
	}

	// 根据map设置参数
	public static void setQuery(Map<String, Object> map, Query query) {
		if (map == null)
			return;
		for (Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			String operator = "";
			if (key.indexOf("|") > 0) {
				key = entry.getKey().split("\\|")[0];
				operator = entry.getKey().split("\\|")[1];
			}
			Object value = entry.getValue();
			key = key.replaceAll("\\.", "_");
			if (operator.toUpperCase().equals("LIKE")) {
				query.setString(key, "%" + value.toString() +"%");
			} else if (value instanceof Integer) {
				query.setInteger(key, Integer.parseInt(value.toString()));
			} else if (value instanceof String) {
				query.setString(key, value.toString());
			} else if (value instanceof Byte) {
				query.setByte(key, Byte.valueOf(value.toString()));
			}else if(value instanceof List){
				query.setParameterList(key+"_in",(List<?>) value); 
			} else {
				query.setParameter(key, value);
			}
		}
	}
//	public static String getConf(String key, String fileName) throws Exception{
//		return getConf(key,fileName,null);
//	}
//	public static String getConf(String key, String fileName, String def) throws Exception{
//		if(fileName == null)
//			fileName = "/jdbc.properties";
//		InputStream in = Tools.class.getResourceAsStream(fileName);
//		Properties prop = new Properties();
//		try {
//			prop.load(in);
//			return prop.getProperty(key).trim();
//		} catch (Exception e) {
//			System.out.println("配置有误，params config have error,"+key+" not exist.");
//			if(def!=null){
//				return def;
//			}else{
//				e.printStackTrace();
//				throw new Exception("配置有误，"+key+"不存在");
//			}
//		}
//	}
	public static String getServicePath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/")+"/";
	}
	public static String getChar(int num){
		String md="123456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ789abcd";
		Random random = new Random();
		String temp="";
		for(int i=0;i<num;i++){
			temp=temp+md.charAt(random.nextInt(50));
		}
		return temp;
	}
	public static HttpServletResponse getResponse(){
		HttpServletResponse response=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();;
		response.setContentType( "text/html" );
		response.setCharacterEncoding( "UTF-8" );
		return response;
	}
	public static HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
	}
	public static ServletContext getServletContext(){
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
        return webApplicationContext.getServletContext(); 
	}
	public static String readStream(InputStream inStream, String encoding)
			throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return new String(outSteam.toByteArray(), encoding);
	}

	public static String removeHtml(String inputStr){
		inputStr=inputStr.replaceAll("<[a-zA-Z|//]+[1-9]?[^><]*>", "");
		inputStr=inputStr.replaceAll("&nbsp;", "");
		StringBuffer temp=new StringBuffer();
		String str="[a-z]*[A-Z]*[0-9]*[\u4E00-\u9FA5]*[Ⅰ|,|。|，|.|：|(|)|（|）|:|/]*";
		Pattern pattern = Pattern.compile(str,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		while (matcher.find())
		{
			temp.append(matcher.group());
		}
		return temp.toString();
	}
	/**
	 * 获取用户登录信息
	 * @return
	 */
	public static LoginInfoDto getUser(){
		ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
		return (LoginInfoDto) cacheService.getObj(Const.CACHE_USER + MyCookie.getCookie(Const.COOKIE_TOKEN, false, Tools.getRequest()));
	}
	/**
	 * 判断模块id是否合法：top，0，privateModule则不合法，不允许查看所有项目
	 * @param moduleId
	 */
	public static boolean moduleIdIsLegal(String moduleId){
		return !(MyString.isEmpty(moduleId) || moduleId.equals(Const.ADMIN_MODULE) || moduleId.equals(Const.TOP_MODULE) || moduleId.equals(Const.PRIVATE_MODULE));
	}
	
	
}
