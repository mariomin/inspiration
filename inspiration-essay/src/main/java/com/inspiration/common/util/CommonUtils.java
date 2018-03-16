package com.inspiration.common.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonUtils {
	public static final String LINE_SEP = System.getProperty("line.separator");

	public static double getDouble(String value, double defValue) {
		if ((value == null) || (value.length() < 1)) {
			return defValue;
		}
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
		}
		return defValue;
	}

	public static boolean getBoolean(String value, boolean defValue) {
		if ((value == null) || (value.length() < 1)) {
			return defValue;
		}
		try {
			return Boolean.parseBoolean(value);
		} catch (Exception e) {
		}
		return defValue;
	}

	public static int getInt(String value, int defalut) {
		if ((value == null) || (value.length() < 1)) {
			return defalut;
		}
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
		}
		return defalut;
	}

	public static long getLong(String value, long defalut) {
		if ((value == null) || (value.length() < 1)) {
			return defalut;
		}
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
		}
		return defalut;
	}

	public static boolean isNull(String str, int minLength) {
		if ((str == null) || (str.isEmpty())) {
			return true;
		}
		if ((str.equalsIgnoreCase("null")) || (str.equals("")) || (str.length() < minLength)) {
			return true;
		}
		return false;
	}

	public static String urlDecode(String encodeStr) {
		if ((encodeStr == null) || (encodeStr.length() < 1)) {
			return encodeStr;
		}
		try {
			return URLDecoder.decode(encodeStr, "utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

	public static String urlEncode(String param) {
		if ((param == null) || (param.length() < 1)) {
			return param;
		}
		String retValue = null;
		try {
			retValue = URLEncoder.encode(param, "utf-8");
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
		}
		return retValue;
	}

	public static String stream2String(InputStream in, int bufferSize, String charsetName) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[bufferSize];
		int count = -1;
		while ((count = in.read(data, 0, bufferSize)) != -1) {
			outStream.write(data, 0, count);
		}
		data = null;
		return new String(outStream.toByteArray(), charsetName);
	}

	public static String getString(Object ob, String def) {
		if (ob == null) {
			return def;
		}
		String value = ob.toString();
		if ((value == null) || (value.isEmpty()) || (value.length() < 1)) {
			return def;
		}
		return value.trim();
	}

	public static double formatDouble(double fValue, int numbers) {
		BigDecimal b = new BigDecimal(fValue);
		double f1 = b.setScale(numbers, 4).doubleValue();
		return f1;
	}

	public static String trim(String str) {
		if (str == null) {
			return str;
		}
		return str.trim();
	}

	public static String getMD5AsHex(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			return null;
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = ((byte) charArray[i]);
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = md5Bytes[i] & 0xFF;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static String getMD5AsHex(byte[] charArray) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");

		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = md5Bytes[i] & 0xFF;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}
