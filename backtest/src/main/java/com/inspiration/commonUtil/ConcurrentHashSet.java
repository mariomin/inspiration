package com.inspiration.commonUtil;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentHashSet<E> extends AbstractSet<E> implements Serializable {
	private static final long serialVersionUID = 1L;
	protected final Map<E, Boolean> map = new ConcurrentHashMap<E, Boolean>();
	
	@Override
	public boolean add(E o) {
		return null == ((ConcurrentMap<E, Boolean>) map).putIfAbsent(o, Boolean.TRUE);
	}
	
	@Override
	public Iterator<E> iterator() {
		return map.keySet().iterator();
	}
	
	@Override
	public int size() {
		return map.size();
	}
}
