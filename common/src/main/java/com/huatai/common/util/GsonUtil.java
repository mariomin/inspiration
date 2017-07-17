package com.huatai.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
	public static final String DATA_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static Gson getGsonInstance() {
		return new GsonBuilder().setDateFormat(DATA_PATTERN).create();
	}
}
