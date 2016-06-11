package com.flyfox.util.ehcache;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * EHCache
 */
public class EhCache {

	private net.sf.ehcache.Cache cache;

	public EhCache(Cache cache) {
		this.cache = cache;
	}

	@SuppressWarnings("rawtypes")
	public List keys() {
		return this.cache.getKeys();
	}

	public Object get(Object key)  {
			if (key == null)
				return null;
			else {
				Element element = cache.get(key);
				if (element != null)
					return element.getObjectValue();
			}
			return null;
	}

	public void update(Object key, Object value) {
		put(key, value);
	}

	public void put(Object key, Object value) {
		Element element = new Element(key, value);
		cache.put(element);

	}

	public void evict(Object key) {
		cache.remove(key);
	}

	public void evict(List<?> keys) {
		cache.removeAll(keys);
	}

	public void clear() {
		cache.removeAll();
	}

	public void destroy() {
		cache.getCacheManager().removeCache(cache.getName());
	}

}