package com.jflyfox.util.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.jflyfox.jfinal.component.redis.JedisClient;
import com.jflyfox.util.Config;
import com.jflyfox.util.StrUtils;
import com.jflyfox.util.cache.impl.MemoryCache;
import com.jflyfox.util.cache.impl.MemorySerializeCache;
import com.jflyfox.util.serializable.FSTSerializer;
import com.jflyfox.util.serializable.SerializerManage;

public class RedisCache implements Cache {

	public static void main(String[] args) {
		// 初始化Cache为fst序列化
		SerializerManage.add("fst", new FSTSerializer());
		
		// 设置序列化工具
		String defaultKey = Config.getStr("CACHE.SERIALIZER.DEFAULT");
		defaultKey = StrUtils.isEmpty(defaultKey) ? "java" : defaultKey;
		SerializerManage.setDefaultKey(defaultKey);

		
		// 设置缓存
		CacheManager.setCache(new ICacheManager() {

			public Cache getCache() {
				String cacheName = Config.getStr("CACHE.NAME");
				cacheName = StrUtils.isEmpty(cacheName) ? "MemorySerializeCache" : cacheName; 
				
				if ("MemorySerializeCache".equals(cacheName)) {
					return new MemorySerializeCache();
				} else if ("MemoryCache".equals(cacheName)) {
					return new MemoryCache();
				}  else if ("RedisCache".equals(cacheName)) {
					return new RedisCache();
				} else {
					throw new RuntimeException("####init cache error!");
				}
			}
		});
		
		System.out.println(11);
		CacheManager.get("testaaa").add("testaaa", "1");
		System.out.println(CacheManager.get("testaaa").get("testaaa"));
		CacheManager.get("testaaa").remove("testaaa");
		System.out.println(CacheManager.get("testaaa").get("testaaa"));
	}
	protected String name;
	protected JedisClient client = null;

	public RedisCache() {
		client = JedisClient.getInstance();
	}

	public String name() {
		return name;
	}

	public RedisCache name(String name) {
		this.name = name;
		return this;
	}

	public RedisCache add(String key, Object value) {
		client.hsetObj(this.name, key, value);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) client.hgetObj(this.name, key);
	}

	public Object remove(String key) {
		client.hdel(this.name, key);
		return null;
	}

	public void clear() {
		client.hdelObjAll(this.name);
	}
	
	public int size() {
		return (int) client.hlenObj(this.name);
	}

	public Set<String> keys() {
		Map<String, Object> map = client.hgetAllObj(this.name);
		if (map.size() == 0) {
			return null;
		}
		
		return map.keySet();
	}

	public <T> Collection<T> values() {
		Map<String, T> map = client.hgetAllObj(this.name);
		if (map.size() == 0) {
			return null;
		}

		return map.values();
	}

}
