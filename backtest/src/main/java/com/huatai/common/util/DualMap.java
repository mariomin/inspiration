package com.huatai.common.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * The dual map allows key/value to retrieve value/key easily with the
 * enforcement of BOTH KEY AND VALUE ARE UNIQUE
 *
 * @param <T>
 *            key
 * @param <U>
 *            value
 */
public class DualMap<T, U> {
	private final Map<T, U> map1 = new HashMap<T, U>();
	private final Map<U, T> map2 = new HashMap<U, T>();
	
	public DualMap() {
		super();
	}
	
	public DualMap(Map<? extends T, ? extends U> map) {
		putAll(map);
	}
	
	public synchronized U put(T t, U u) {
		// first check whether value already exists
		T t2 = map2.get(u);
		if (t2 != null) {
			map1.remove(t2);
		}
		
		// then checck whether key already exists for map2
		U u2 = map1.get(t);
		if (u2 != null) {
			map2.remove(u2);
		}
		
		U result = map1.put(t, u);
		map2.put(u, t);
		return result;
	}
	
	public synchronized U remove(T t) {
		// first check whether value already exists
		U u = map1.remove(t);
		
		if (u != null) {
			map2.remove(u);
		}
		
		return u;
	}
	
	public synchronized T removeKeyByValue(U u) {
		// first check whether value already exists
		T t = map2.remove(u);
		
		if (t != null) {
			map1.remove(t);
		}
		
		return t;
	}
	
	public synchronized U get(T t) {
		return map1.get(t);
	}
	
	public synchronized T getKeyByValue(U u) {
		return map2.get(u);
	}
	
	public synchronized boolean containsKey(T t) {
		return map1.containsKey(t);
	}
	
	public synchronized boolean containsValue(U u) {
		return map1.containsValue(u);
	}
	
	public synchronized Set<Entry<T, U>> entrySet() {
		return map1.entrySet();
	}
	
	public synchronized Collection<U> values() {
		return map1.values();
	}
	
	public synchronized Set<T> keySet() {
		return map1.keySet();
	}
	
	public synchronized void putAll(Map<? extends T, ? extends U> map) {
		for (java.util.Map.Entry<? extends T, ? extends U> entry : map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}
	
	public synchronized void clear() {
		map1.clear();
		map2.clear();
	}
}
