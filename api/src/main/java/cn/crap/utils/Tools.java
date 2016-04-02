package cn.crap.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;

import cn.crap.framework.BiyaoBizException;


public class Tools {
	/**
	 * 构造查询的id
	 * @param roleName
	 * @return
	 */
	public static List<String> getIdsFromField(String ids) {
		return Arrays.asList(ids.split(","));
	}
	/**
	 * 查询是否拥有权限
	 */
	public static boolean hasAuth(String authPassport, HttpSession session,
			String moduleId) throws BiyaoBizException {
		String authority = session.getAttribute(Const.SESSION_ADMIN_AUTH).toString();
		String roleIds = session.getAttribute(Const.SESSION_ADMIN_ROLEIDS).toString();
		if((","+roleIds).indexOf(","+Const.SUPER+",")>=0){
			return true;//超级管理员
		}
		String needAuth = authPassport.replace(Const.MODULEID, moduleId);
		if(authority.indexOf(","+needAuth+",")>=0){
			return true;
		}else{
			throw new BiyaoBizException("000003");
		}
	}
	/**********************构造查询语句****************************/
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
			Object value = entry.getValue();
			if (key.indexOf("|") > 0) {
				String[] keys = key.split("\\|");
				keys[0] = keys[0].replaceAll("\\.", "_");
				if (keys[1].equals("in")) {
					hql.append(keys[0] + " in (:" + keys[0].replaceAll("\\.", "_") + ") and ");
				} else if (keys[1].equals(Const.NULL)) {
					hql.append(keys[0] + " =null and ");
					removes.add(key);
				} else if (keys[1].equals(Const.BLANK)) {
					hql.append(keys[0] + " ='' and ");
					removes.add(key);
				} else if (keys[1].equals("like")) {
					if (!MyString.isEmpty(value.toString()))
						hql.append(keys[0] + " like '%" + value.toString()
								+ "%' and ");
					removes.add(key);
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
			if (key.indexOf("|") > 0) {
				key = key.split("\\|")[0];
			}
			Object value = entry.getValue();
			key = key.replaceAll("\\.", "_");
			if (value instanceof Integer) {
				query.setInteger(key, Integer.parseInt(value.toString()));
			} else if (value instanceof String) {
				query.setString(key, value.toString());
			}else if(value instanceof List){
				query.setParameterList(key,(List<?>) value); 
			} else {
				query.setParameter(key, value);
			}
		}
	}
	public static String getConf(String key, String fileName) throws Exception{
		return getConf(key,fileName,null);
	}
	public static String getConf(String key, String fileName, String def) throws Exception{
		if(fileName == null)
			fileName = "/jdbc.properties";
		InputStream in = Tools.class.getResourceAsStream(fileName);
		Properties prop = new Properties();
		try {
			prop.load(in);
			return prop.getProperty(key).trim();
		} catch (Exception e) {
			System.out.println("配置有误，params config have error,"+key+" not exist.");
			if(def!=null){
				return def;
			}else{
				e.printStackTrace();
				throw new Exception("配置有误，"+key+"不存在");
			}
		}
	}
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
	private static HashMap<String,String> settings= new HashMap<String,String>();
}
