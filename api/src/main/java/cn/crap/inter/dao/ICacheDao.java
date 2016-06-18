package cn.crap.inter.dao;

public interface ICacheDao {

	Object getObj(String key);

	boolean setStr(String key, String value, int expireTime);

	String getStr(String key);

	boolean setObj(String key, Object value, int expireTime);
	
	boolean delStr(String key);
	
	boolean delObj(String key);

	boolean delObj(String key, String field);

	boolean setObj(String key, String field, Object value, int expireTime);

	Object getObj(String key, String field);

}