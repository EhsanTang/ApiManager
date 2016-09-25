package cn.crap.inter.dao;

public interface ICacheDao {

	/**
	 * 获取一级对象换攒
	 * @param key
	 * @return
	 */
	Object getObj(String key);

	/**
	 * 设置一级字符缓存
	 * @param key
	 * @param value
	 * @param expireTime 单位s
	 * @return
	 */
	boolean setStr(String key, String value, int expireTime);

	/**
	 * 获取异常字符缓存
	 * @param key
	 * @return
	 */
	String getStr(String key);

	/**
	 * 设置一级对象缓存
	 * @param key
	 * @param value
	 * @param expireTime 单位s
	 * @return
	 */
	boolean setObj(String key, Object value, int expireTime);
	
	/**
	 * 删除字符串缓存
	 * @param key
	 * @return
	 */
	boolean delStr(String key);
	
	/**
	 * 删除对象缓存：一级，及一级下面的二级缓存都将被删除
	 * @param key
	 * @return
	 */
	boolean delObj(String key);

	/**
	 * 删除对象二级缓存
	 * @param key
	 * @param field
	 * @return
	 */
	boolean delObj(String key, String field);

	/**
	 * 添加二级对象缓存
	 * @param key
	 * @param field
	 * @param value
	 * @param expireTime 单位s
	 * @return
	 */
	boolean setObj(String key, String field, Object value, int expireTime);

	/**
	 * 根据key、field获取二级对象缓存
	 * @param key
	 * @param field
	 * @return
	 */
	Object getObj(String key, String field);
	/**
	 * 清空缓存
	 * @return
	 */
	boolean flushDB();

}