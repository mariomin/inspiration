package com.inspiration.commonUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckChinese {
	/**
	 * 判断字符中是否包含中文
	 * @param str
	 * @return
	 */
	 public static boolean isContainChinese(String str) {

	        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
	        Matcher m = p.matcher(str);
	        if (m.find()) {
	            return true;
	        }
	        return false;
	  }
}
