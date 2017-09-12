package com.inspiration.commonUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayUtil {
	
	public static double[] reverse(List<Double> list) {
		int size = list.size();
		double[] result = new double[size];
		
		int index = size - 1;
		for (Object price : list) {
			result[index--] = (double) price;
		}
		
		return result;
	}
	
	public static Object[] reverse(Object[] objects) {
		if ((objects == null) || (objects.length == 0)) return objects;
		
		List<?> list = Arrays.asList(objects);
		Collections.reverse(list);
		return list.toArray();
	}
	
}
