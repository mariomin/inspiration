package com.inspiration.commonUtil;

public class StringUtil {
	
	public static boolean checkDecimalLength(String decimal, int length) {
		if ((decimal == null) || (length < 0)) return false;
		
		String[] items = decimal.split(RegularExpressionUtil.dot);
		if ((items != null) && (items.length == 2)) return items[1].length() <= length;
		
		return false;
	}
	
	public static boolean isBlankString(final CharSequence cs) {
		int strLen;
		if ((cs == null) || ((strLen = cs.length()) == 0)) return true;
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) return false;
		}
		return true;
	}
	
	public static boolean isNotBlankString(final CharSequence cs) {
		return !isBlankString(cs);
	}
	
	public static String trimToEmpty(String str) {
		return str == null ? "" : str.trim();
	}
	
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}
	
	public static String join(Object[] array, char separator) {
		if (array == null) return null;
		int arraySize = array.length;
		int bufSize = (arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0].toString().length()) + 1) * arraySize);
		StringBuffer buf = new StringBuffer(bufSize);
		
		for (int i = 0; i < arraySize; i++) {
			if (i > 0) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}
}
