package cn.crap.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.hibernate.Query;

import cn.crap.framework.BiyaoBizException;


public class Tools {
	/**
	 * 构造查询的id
	 * @param roleName
	 * @return
	 */
	public static String getIdsFromField(String ids) {
		ids = ids.trim();
		if (!ids.startsWith(","))
			ids = "," + ids;
		if (!ids.endsWith(","))
			ids = ids + ",";
		ids = ids.replace(",", "','");
		ids = ids.substring(2, ids.length() - 2);
		return ids;
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
			if (params[i + 1] != null&& !params[i + 1].toString().trim().equals("")&& !params[i + 1].toString().trim().equals("null"))
				map.put(params[i].toString(), params[i + 1]);
		}
		return map;

	}
	public static String getHql(Map<String, Object> map) {
		StringBuffer hql = new StringBuffer();
		if (map == null || map.size() == 0) {
			return " where 1=1 ";
		}
		if (map.size() > 0) {
			hql.append(" where ");
		}
		List<String> removes = new ArrayList<String>();
		for (Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value == null || value.toString().trim().equals("")
					|| value.toString().trim().equals("null")) {
				removes.add(key);
				continue;
			}
			if (key.indexOf("|") > 0) {
				String[] keys = key.split("\\|");
				if (keys[1].equals("in")) {
					hql.append(keys[0] + " in (" + value.toString() + ") and ");
					removes.add(key);
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
					hql.append(keys[0] + " " + keys[1] + ":"
							+ keys[0].replaceAll("\\.", "_") + " and ");
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
	private static HashMap<String,String> settings= new HashMap<String,String>();
	public static String getConf(String key) throws Exception{
		String value = settings.get(key);
		if(value==null){
			value = getConf(key,null);
			settings.put(key, value);
		}
		return value;
	}
}
