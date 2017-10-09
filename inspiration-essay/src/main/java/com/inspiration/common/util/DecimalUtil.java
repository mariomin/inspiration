package com.inspiration.common.util;

public class DecimalUtil {
	private static final int scale = 7;
	private static final double roundingFactor = Math.pow(10, scale);
	private static final double EPSILON = 1.0 / roundingFactor;

	public static boolean equal(double x, double y) {
		return Math.abs(x - y) < EPSILON;
	}

	public static boolean greaterThan(double x, double y) {
		return (x - y) > EPSILON;
	}

	public static boolean lessThan(double x, double y) {
		return (y - x) > EPSILON;
	}

	public static boolean equalGreaterThan(double x, double y) {
		return equal(x, y) || greaterThan(x, y);
	}

	public static boolean equalLessThan(double x, double y) {
		return equal(x, y) || lessThan(x, y);
	}

	public static int compare(double x, double y) {
		if (equal(x, y))
			return 0;

		if (greaterThan(x, y))
			return 1;
		else
			return -1;
	}

	public static boolean isZero(double x) {
		return equal(x, 0);
	}

	public static boolean between(double val, double lower, double upper) {
		return greaterThan(val, lower) && lessThan(val, upper);
	}

	public static double safeDevision(double x, double y, double failValue) {
		if (isZero(y))
			return failValue;

		return x / y;
	}

}
