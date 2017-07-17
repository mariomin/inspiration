package com.huatai.common.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class DualMapTest {
	@Test
	public void test() {
		DualMap<Integer, String> map = new DualMap<Integer, String>();
		map.put(1, "A");
		map.put(2, "B");
		assert (map.get(1).equals("A"));
		assert (map.get(2).equals("B"));
		assert (map.getKeyByValue("A").equals(1));
		assert (map.getKeyByValue("B").equals(2));
	}
	
	@Test
	public void testDupKey() {
		DualMap<Integer, String> map = new DualMap<Integer, String>();
		map.put(1, "A");
		map.put(1, "B");
		assert (map.get(1).equals("B"));
		assert (map.getKeyByValue("A") == null);
		assert (map.getKeyByValue("B").equals(1));
	}
	
	@Test
	public void testDupValue() {
		DualMap<Integer, String> map = new DualMap<Integer, String>();
		map.put(1, "A");
		map.put(2, "A");
		assert (map.get(1) == null);
		assert (map.get(2).equals("A"));
		assert (map.getKeyByValue("A").equals(2));
	}
	
	@Test
	public void testDupKeyAndValue() {
		DualMap<Integer, String> map = new DualMap<Integer, String>();
		map.put(1, "A");
		map.put(1, "A");
		assert (map.get(1).equals("A"));
		assert (map.getKeyByValue("A").equals(1));
	}
	
	@Test
	public void testPutAll() {
		Map<Integer, String> m = new HashMap<Integer, String>();
		m.put(1, "A");
		m.put(2, "B");
		m.put(3, "A");
		
		DualMap<Integer, String> map = new DualMap<Integer, String>();
		map.putAll(m);
		assert (map.get(1) == null);
		assert (map.getKeyByValue("A").equals(3));
		assert (map.get(3).equals("A"));
		
		assert (map.get(2).equals("B"));
		assert (map.getKeyByValue("B").equals(2));
	}
	
	@Test
	public void testRemove() {
		Map<Integer, String> m = new HashMap<Integer, String>();
		m.put(1, "A");
		m.put(2, "B");
		m.put(3, "A");
		
		DualMap<Integer, String> map = new DualMap<Integer, String>();
		map.putAll(m);
		
		assert (map.remove(1) == null);
		assert (map.removeKeyByValue("A").equals(3));
		map.put(3, "A");
		assert (map.remove(3).equals("A"));
	}
	
}
