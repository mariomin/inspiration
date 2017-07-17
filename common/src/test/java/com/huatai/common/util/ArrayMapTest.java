package com.huatai.common.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ArrayMapTest {
	@Test
	public void testPut() {
		ArrayMap<String, String> am = new ArrayMap<String, String>();
		am.put("3", "apple");
		am.put("2", "orange");
		am.put("1", "mango");
		assertTrue(am.get("3").equals("apple"));
		assertTrue(am.get("2").equals("orange"));
		assertTrue(am.get("1").equals("mango"));
		assertTrue(am.get(0).equals("apple"));
		assertTrue(am.get(1).equals("orange"));
		assertTrue(am.get(2).equals("mango"));
		am.put("2", "watermelon");
		assertTrue(am.get("2").equals("watermelon"));
		assertTrue(am.get(1).equals("watermelon"));
		assertTrue(am.size() == 3);
	}
	
	@Test
	public void testRemove() {
		ArrayMap<String, String> am = new ArrayMap<String, String>();
		am.put("3", "apple");
		am.put("2", "orange");
		am.put("1", "mango");
		am.remove("2");
		assertTrue(am.get("3").equals("apple"));
		assertTrue(am.get("2") == null);
		assertTrue(am.get("1").equals("mango"));
		assertTrue(am.get(0).equals("apple"));
		assertTrue(am.get(1).equals("mango"));
		assertTrue(am.size() == 2);
		
	}
	
	@Test
	public void testMisc() {
		ArrayMap<String, String> am = new ArrayMap<String, String>();
		am.put("3", "apple");
		am.put("2", "orange");
		am.put("1", "mango");
		am.remove("2");
		assertTrue(am.size() == 2);
		assertTrue(am.toArray().size() == 2);
		assertTrue(am.toMap().size() == 2);
		am.put("2", "watermelon");
		assertTrue(am.get("3").equals("apple"));
		assertTrue(am.get("2").equals("watermelon"));
		assertTrue(am.get("1").equals("mango"));
		assertTrue(am.get(0).equals("apple"));
		assertTrue(am.get(1).equals("mango"));
		assertTrue(am.get(2).equals("watermelon"));
		assertTrue(am.size() == 3);
		am.clear();
		assertTrue(am.isEmpty());
	}
}
