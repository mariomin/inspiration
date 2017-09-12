package com.inspiration.commonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ArrayMap implements both the functions of array and hash map
 * @param <T>
 *            key type
 * @param <U>
 *            value type
 */
public class ArrayMap<T, U> {
	protected Map<T, U> map = new HashMap<T, U>();
	protected Map<T, Integer> index = new HashMap<T, Integer>();
	protected List<U> array = new ArrayList<U>();
	
	public synchronized U put(T t, U u) {
		U result = map.put(t, u);
		if (null != result) {
			Integer pos = index.get(t);
			array.set(pos, u);
		} else {
			Integer pos = array.size();
			array.add(u);
			index.put(t, pos);
		}
		return result;
	}
	
	// removing is slow for this data structure
	// we speed up sequential access at the cost of
	// removal operation
	public synchronized U remove(T t) {
		U result = map.remove(t);
		if (null != result) {
			Integer pos = index.remove(t);
			array.remove(pos.intValue());
			// need to rebuild the index map
			for (T key : map.keySet()) {
				Integer i = index.get(key);
				if (i > pos) {
					index.put(key, i - 1);
				}
			}
		}
		return result;
	}
	
	public synchronized int indexOf(T t) {
		return index.get(t);
	}
	
	public synchronized U get(T t) {
		return map.get(t);
	}
	
	public synchronized U get(int i) {
		return array.get(i);
	}
	
	public synchronized List<U> toArray() {
		return array;
	}
	
	public synchronized Map<T, U> toMap() {
		return map;
	}
	
	public synchronized int size() {
		return array.size();
	}
	
	public synchronized boolean isEmpty() {
		return array.size() == 0;
	}
	
	public synchronized void clear() {
		map.clear();
		array.clear();
		index.clear();
	}
}
