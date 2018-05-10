package cn.crap.utils;

import cn.crap.dto.CrumbDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.enumer.MyError;
import cn.crap.framework.MyException;
import cn.crap.framework.SpringContextHolder;
import cn.crap.framework.ThreadContext;
import cn.crap.service.tool.StringCache;
import cn.crap.service.tool.UserCache;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Tools {
	public static void staticize(String html, String filePath) throws MyException, IOException{
		if(html == null){
			throw new MyException(MyError.E000045);
		}
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8");
		try{
			fw.write(html);
			fw.flush();
		}catch(Exception e){
			e.printStackTrace();
			throw new MyException(MyError.E000001, e.getMessage());
		}finally{
			fw.close();
		}
	}
	
	public static void createZip(String sourcePath, String zipPath) throws Exception {
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(zipPath);
			zos = new ZipOutputStream(fos);
			writeZip(new File(sourcePath), "", zos);
		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			if (zos != null) {
				zos.close();
			}
			if( fos != null ){
				fos.close();
			}
		}
	}
  
	private static void writeZip(File file, String parentPath, ZipOutputStream zos) throws Exception {
		if (file.exists()) {
			if (file.isDirectory()) {// 处理文件夹
				parentPath += file.getName() + File.separator;
				File[] files = file.listFiles();
				if (files.length != 0) {
					for (File f : files) {
						writeZip(f, parentPath, zos);
					}
				} else {
					zos.putNextEntry(new ZipEntry(parentPath));
				}
			} else {
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(file);
					ZipEntry ze = new ZipEntry(parentPath + file.getName());
					zos.putNextEntry(ze);
					byte[] content = new byte[1024];
					int len;
					while ((len = fis.read(content)) != -1) {
						zos.write(content, 0, len);
						zos.flush();
					}
				} catch (Exception e) {
					throw e;
				} finally {
					if (fis != null) {
						fis.close();
					}
				}
			}
		}
	}
      
	public static String readFile(String filePath) throws Exception{
		  FileInputStream fis = null;
		  InputStreamReader isr = null;
		  BufferedReader br = null;
		  try{
			  fis = new FileInputStream(filePath);   
			  isr = new InputStreamReader(fis, "UTF-8");   
			  br =new BufferedReader(isr);   
			  String line = null;   
			  StringBuilder sb = new StringBuilder();
			  while ((line = br.readLine()) != null) {  
				  sb.append(line + (line.equals("\r\n") ? "":"\r\n") );
			  }   
			  return sb.toString();
		  }catch(Exception e){
			  throw e;
		  }finally{
			 if(fis != null){
				 fis.close();
			 }
			 if(isr != null){
				 isr.close();
			 }
			 if(br != null){
				 br.close();
			 }
		  }
	}
	public static void copyFile(String source, String dest)
	        throws IOException {    
	    InputStream input = null;    
	    OutputStream output = null;    
	    try {
	           input = new FileInputStream(source);
	           output = new FileOutputStream(dest);        
	           byte[] buf = new byte[1024];        
	           int bytesRead;        
	           while ((bytesRead = input.read(buf)) > 0) {
	               output.write(buf, 0, bytesRead);
	           }
	    } catch(Exception e){
	    	e.printStackTrace();
	    }finally {
	    	if(input != null)
	    		input.close();
	    	if(output != null)
	    		output.close();
	    }
	}
	
	public static void getHrefFromText(String html, List<String> filePaths){
		Pattern pattern = Pattern.compile("href=\"(.*)\"",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(html);
		while(matcher.find()){
		    String foundURL = matcher.group(1);
		    if (foundURL.startsWith("http")){
		    	if( !filePaths.contains(foundURL) ){
		    		filePaths.add(foundURL);
		    	}
		    }
		}
		pattern = Pattern.compile("src=\"(.*)\"",Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(html);
		while(matcher.find()){
		    String foundURL = matcher.group(1);
		    if (foundURL.startsWith("http")){
		    	if( !filePaths.contains(foundURL) ){
		    		filePaths.add(foundURL);
		    	}
		    }
		}
	}
	
	/** 
     * 通过递归调用删除一个文件夹及下面的所有文件 
     * @param filePath
     */  
    public static void deleteFile(String filePath){  
    	File file = new File(filePath);
    	if( !file.exists()){
    		return;
    	}
        if(file.isFile()){//表示该文件不是文件夹  
            file.delete();  
        }else{  
            //首先得到当前的路径  
            String[] childFilePaths = file.list();  
            for(String childFilePath : childFilePaths){  
                deleteFile(file.getAbsolutePath()+"/"+childFilePath);  
            }  
            file.delete();  
        }  
    } 
    // 创建文件夹
    public static void createFile(String filePath){  
    	File file = new File(filePath);
    	if( !file.exists()){
    		file.mkdirs();
    	}
    }  
	/**
	 * 构造查询的id
	 * @param ids
	 * @return
	 */
	public static List<String> getIdsFromField(String ids) {
		if (MyString.isEmpty(ids)){
			return Collections.emptyList();
		}
		return Arrays.asList(ids.split(","));
	}
	
	// 获取图形验证码
	public static String getImgCode() throws MyException{
		StringCache stringCache = SpringContextHolder.getBean("stringCache", StringCache.class);
		String timesStr = stringCache.get(IConst.CACHE_IMGCODE_TIMES + MyCookie.getCookie(IConst.COOKIE_UUID, false));
		int times = 0;
		if(timesStr != null){
			times = Integer.parseInt(timesStr.toString()) + 1;
		}
		if(times > 3){
			throw new MyException(MyError.E000011);
		}
		stringCache.add(IConst.CACHE_IMGCODE_TIMES + MyCookie.getCookie(IConst.COOKIE_UUID, false), times + "");
		String imgCode = stringCache.get(IConst.CACHE_IMGCODE + MyCookie.getCookie(IConst.COOKIE_UUID, false));
		return imgCode == null? System.currentTimeMillis()+"" : imgCode.toString();
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
	
	public static Map<String, String> getStrMap(String... params) {
		if (params.length == 0 || params.length % 2 != 0) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
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
				
				else if (keys[1].equals(IConst.NULL)) {
					hql.append(keys[0] + " =null and ");
					removes.add(key);
				} 
				
				else if (keys[1].equals(IConst.NOT_NULL)) {
					hql.append(keys[0] + "!=null and ");
					removes.add(key);
				} 
				
				else if (keys[1].equals(IConst.BLANK)) {
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
	public static String getServicePath(){
		return ThreadContext.request().getSession().getServletContext().getRealPath("/")+"/";
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
	
	public static boolean isEmail(String email) {
		if (null == email || "".equals(email))
			return false;
		// Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
		Pattern p = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	public static boolean isSuperAdmin(String role) {
		if( MyString.isEmpty(role) ){
			return false;
		}
		
		if( ( ","+ role + ",").indexOf(","+ IConst.C_SUPER +",") >= 0){
			return true;
		}
		return false;
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
		if(inputStr == null){
			return "";
		}
		inputStr=inputStr.replaceAll("<[a-zA-Z|//]+[1-9]?[^><]*>", "");
		inputStr=inputStr.replaceAll("&nbsp;", "");
		inputStr=inputStr.replaceAll("<", "&lt;");
        inputStr=inputStr.replaceAll(">", "&gt;");
		return inputStr;
	}
	
	public static String subString(String str, int length, String suffix){
		if( MyString.isEmpty( str ) ){
			return "";
		}
		
		if( MyString.isEmpty(suffix) ){
			suffix = "...";
		}
		
		if(str.length() > length){
			return str.substring(0, length) + suffix;
		}
		
		return str;
	}
	
	public static boolean checkUserName(String userName){
		String regex = "^[0-9A-Za-z-_\\.]{5,20}$";		
		return userName.matches(regex);
	}
	public static boolean checkEmail(String email){
	        boolean flag = false;
	        try{
	                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	                Pattern regex = Pattern.compile(check);
	                Matcher matcher = regex.matcher(email);
	                flag = matcher.matches();
	            }catch(Exception e){
	                flag = false;
	            }
	        return flag;
	 }

	 public static String getAvatar(){
		Random random = new Random();
		return "resources/avatar/avatar" + random.nextInt(10) +".jpg";
	 }
	/**
	 * 处理用户名，不能包含@，不能=admin
	 * @param name
	 * @return
	 */
	public static String handleUserName(String name){
		if(MyString.isEmpty(name)){
			return "";
		}
		name = name.replaceAll("@", "");
		if (name.equals("admin")){
			name = "ca_" + name;
		}
		return name;
	}
	// 相同ID，不同用户在数据库存储的id与浏览器中存储的不一致，解决项目导出给其他人id一致的问题
		public static String handleId(LoginInfoDto user, String id){
			if(MyString.isEmpty(id)){
				return null;
			}
			
			id = id + "-" + MD5.encrytMD5(user.getId(), "").substring(0, 5);
			return id;
		}
		public static String unhandleId(String id){
			if(MyString.isEmpty(id)){
				return null;
			}
			id = id.substring(0, id.lastIndexOf("-"));
			return id;
		}
}
