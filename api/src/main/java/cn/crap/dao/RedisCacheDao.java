package cn.crap.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.crap.inter.dao.ICacheDao;
import cn.crap.springbeans.Config;
import cn.crap.utils.SerializeUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Repository("redisCacheDao")
public class RedisCacheDao implements ICacheDao{
		@Autowired
		private Config config;
		private static JedisPool pool;
		
		@Autowired
		public void init(){
			if(pool != null)
				return;
			JedisPoolConfig poolConfig = new JedisPoolConfig();
			// 最大连接数
			poolConfig.setMaxTotal(config.getRedisPoolSize());
			// 最大空闲数
			poolConfig.setMaxIdle(10);
			// 最大等待时间
			poolConfig.setMaxWaitMillis(1000);
			// 连接池获取Redis
			if(config.getRedisPwd().trim().equals("")){
				pool = new JedisPool(poolConfig, config.getRedisIp(), config.getRedisPort(), 10000);// 10000:读取数据超时时间
			}else{
				pool = new JedisPool(poolConfig, config.getRedisIp(), config.getRedisPort(), 10000, config.getRedisPwd());// 连接池密码
			}
		}


		@Override
		public Object getObj(String key) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				byte[] obj = jedis.get((config.getRedisKeyPrefix() + key).getBytes());
				if (obj == null)
					return null;
				return SerializeUtil.unserialize(obj);
			} catch (Exception e) {
				throw e;
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}

		@Override
		public boolean setStr(String key, String value, int expireTime) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				if(expireTime > 0){
					jedis.setex((config.getRedisKeyPrefix() + key), expireTime, value);
				}else{
					jedis.set((config.getRedisKeyPrefix() + key), value);
				}
				
				return true;
			} catch (Exception e) {
				throw e;
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}

		@Override
		public String getStr(String key) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.get((config.getRedisKeyPrefix() + key));
			} catch (Exception e) {
				throw e;
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}

		@Override
		public boolean setObj(String key, Object value, int expireTime) {
			if(value == null)
				return false;
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				if(expireTime > 0){
					jedis.setex((config.getRedisKeyPrefix() + key).getBytes(), expireTime, SerializeUtil.serialize(value));
				}else{
					jedis.set((config.getRedisKeyPrefix() + key).getBytes(), SerializeUtil.serialize(value));
				}
				return true;
			} catch (Exception e) {
				throw e;
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}

		@Override
		public boolean delStr(String key) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.del((config.getRedisKeyPrefix() + key)) > 0;
			} catch (Exception e) {
				throw e;
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}

		@Override
		public boolean delObj(String key) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.del((config.getRedisKeyPrefix() + key).getBytes()) > 0;
			} catch (Exception e) {
				throw e;
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}

		@Override
		public boolean delObj(String key, String field) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.hdel((config.getRedisKeyPrefix() + key).getBytes(), field.getBytes()) > 0;
			} catch (Exception e) {
				throw e;
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}

		@Override
		public boolean setObj(String key, String field, Object value, int expireTime) {
			if(value == null)
				return false;
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				jedis.hset((config.getRedisKeyPrefix() + key).getBytes(), field.getBytes(), SerializeUtil.serialize(value));
				if(expireTime > 0){
					jedis.expire((config.getRedisKeyPrefix() + key).getBytes(), expireTime);
				}
				return true;
			} catch (Exception e) {
				throw e;
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}

		@Override
		public Object getObj(String key, String field) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				byte[] obj = jedis.hget((config.getRedisKeyPrefix() + key).getBytes(), field.getBytes());
				if (obj == null)
					return null;
				return SerializeUtil.unserialize(obj);
			} catch (Exception e) {
				throw e;
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}

		@Override
		public boolean flushDB() {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				jedis.flushDB();
				return true;
			} catch (Exception e) {
				throw e;
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}

	}
