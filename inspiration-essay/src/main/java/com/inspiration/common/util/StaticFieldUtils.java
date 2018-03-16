package com.inspiration.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class StaticFieldUtils {
	public static String getClassStaticFieldString(Class<?> reflextClass)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		StringBuilder sb = new StringBuilder();
		Field[] fields = reflextClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String name = fields[i].getName();
			if ((!"logger".equalsIgnoreCase(name)) && (!"LOG".equalsIgnoreCase(name))) {
				Field temp = reflextClass.getDeclaredField(name);
				temp.setAccessible(true);
				Object value = temp.get(reflextClass);
				sb.append(CommonUtils.LINE_SEP);
				sb.append("    ").append(name).append(" = ").append(value);
			}
		}
		return sb.toString();
	}

	public static Map<String, Object> getClassStaticFields(Class<?> reflextClass)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Field[] fields = reflextClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String name = fields[i].getName();
			Field temp = reflextClass.getDeclaredField(name);
			temp.setAccessible(true);
			Object value = temp.get(reflextClass);
			retMap.put(name, value);
		}
		return retMap;
	}
}
