package com.inspiration.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 时间转换工具类
 * 
 * @author Administrator
 *
 */
public class DateUtil {

	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 将Long型时间毫秒数转换成时间字符串形式
	 * 
	 * @param timeMillis
	 * @param formatType
	 * @return
	 */
	public static String transTimeMillisToDate(Long timeMillis, String formatType) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatType);
		Date date = new Date(timeMillis);
		return simpleDateFormat.format(date);
	}

	/**
	 * 将字符串时间格式转换成Date
	 * 
	 * @param timeStr
	 * @param formatType
	 * @return
	 */
	public static Date tranferTimeStrToDate(String timeStr, String formatType) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatType);
		Date date = null;
		try {
			date = simpleDateFormat.parse(timeStr);
		} catch (ParseException e) {
			logger.error("将字符串格式转换成date失败！");

		}
		return date;
	}


}
