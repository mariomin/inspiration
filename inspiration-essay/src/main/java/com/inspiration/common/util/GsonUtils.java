package com.inspiration.common.util;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 说明:gson操作方法集合
 */
public class GsonUtils {

	private static final Gson GSON = new Gson();

	private static final Gson FORMAR_GSON = new GsonBuilder().setPrettyPrinting().create();
	
	public static final String DATA_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static Gson getGsonInstance() {
		return new GsonBuilder().setDateFormat(DATA_PATTERN).create();
	}

	public static Gson getGsonFormator() {
		return GSON;
	}

	public static String toFormatJson(Object obj) {
		return FORMAR_GSON.toJson(obj);
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		if (null == obj) {
			return null;
		}
		return GSON.toJson(obj);
	}

	public static Object fromJson(String str, Type type) {
		return GSON.fromJson(str, type);
	}
}
