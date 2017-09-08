package com.huatai.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReflectionUtil {
	private static DualMap<Class<?>, Class<?>> typeMap = new DualMap<Class<?>, Class<?>>();
	
	static {
		typeMap.put(Integer.class, Integer.TYPE);
		typeMap.put(Long.class, Long.TYPE);
		typeMap.put(Double.class, Double.TYPE);
		typeMap.put(Float.class, Float.TYPE);
		typeMap.put(Boolean.class, Boolean.TYPE);
		typeMap.put(Character.class, Character.TYPE);
		typeMap.put(Byte.class, Byte.TYPE);
		typeMap.put(Void.class, Void.TYPE);
		typeMap.put(Short.class, Short.TYPE);
	}
	
	private static Map<Class<?>, Object> defaultValues = new HashMap<Class<?>, Object>();
	
	static {
		defaultValues.put(Integer.TYPE, new Integer(0));
		defaultValues.put(Long.TYPE, new Long(0));
		defaultValues.put(Double.TYPE, new Double(0));
		defaultValues.put(Float.TYPE, new Float(0));
		defaultValues.put(Boolean.TYPE, new Boolean(false));
		defaultValues.put(Character.TYPE, new Character('\u0000'));
		defaultValues.put(Byte.TYPE, new Byte((byte) 0));
		defaultValues.put(Void.TYPE, null);
		defaultValues.put(Short.TYPE, new Short((short) 0));
	}
	
	public static Class<?> getWrapper(Class<?> t) {
		return typeMap.getKeyByValue(t);
	}
	
	public static Object getPrimitiveDefaultValues(Class<?> t) {
		return defaultValues.get(t);
	}
	
	// can only call public methods
	@SuppressWarnings({ "unchecked" })
	public static <T> T callMethod(Class<T> returnType, Object caller, String method, Object[] args) {
		Class<?> cls = caller.getClass();
		Method methods[] = cls.getMethods();
		
		for (Method m : methods) {
			if (m.getName().equals(method)) {
				Class<?>[] params = m.getParameterTypes();
				if (params.length != args.length) {
					continue;
				}
				
				for (int i = 0; i < params.length; i++) {
					Class<?> primitiveType = typeMap.get(args[i].getClass());
					if (params[i].equals(args[i].getClass()) || ((primitiveType != null) && params[i].equals(primitiveType))) {
						
						try {
							return (T) m.invoke(caller, args);
						} catch (Exception e) {
							return null;
						}
					}
					
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked" })
	public static <T> T callStaticMethod(Class<T> caller, String method, Object[] args) {
		Method methods[] = caller.getMethods();
		
		for (Method m : methods) {
			if (m.getName().equals(method) && Modifier.isStatic(m.getModifiers())) {
				Class<?>[] params = m.getParameterTypes();
				if (params.length != args.length) {
					continue;
				}
				
				for (int i = 0; i < params.length; i++) {
					Class<?> primitiveType = typeMap.get(args[i].getClass());
					if (params[i].equals(args[i].getClass()) || ((primitiveType != null) && params[i].equals(primitiveType))) {
						
						try {
							return (T) m.invoke(caller, args);
						} catch (Exception e) {
							return null;
						}
					}
					
				}
			}
		}
		return null;
	}
	
	// can only call public methods
	@SuppressWarnings({ "unchecked" })
	public static <T> T callMethod(Object caller, String method, Object[] args) {
		Class<?> cls = caller.getClass();
		Method methods[] = cls.getMethods();
		
		for (Method m : methods) {
			if (m.getName().equals(method)) {
				Class<?>[] params = m.getParameterTypes();
				if (params.length != args.length) {
					continue;
				}
				
				for (int i = 0; i < params.length; i++) {
					Class<?> primitiveType = typeMap.get(args[i].getClass());
					if (params[i].equals(args[i].getClass()) || ((primitiveType != null) && params[i].equals(primitiveType))) {
						
						try {
							return (T) m.invoke(caller, args);
						} catch (Exception e) {
							return null;
						}
					}
					
				}
			}
		}
		return null;
	}
	
	public static boolean isEnum(Object obj) {
		if (null == obj) return false;
		
		return obj.getClass().isEnum();
	}
	
	public static String[] getEnumStringValues(Object obj) {
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		List<String> list = new ArrayList<String>();
		for (Field field : fields) {
			if (field.isEnumConstant()) {
				list.add(field.getName());
			}
		}
		String[] result = new String[list.size()];
		list.toArray(result);
		return result;
	}
	
	public static Collection<Field> findFieldsWithDifferentValue(Object obj1, Object obj2, Set<String> excludes)
			throws IllegalArgumentException, IllegalAccessException {
		if ((obj1 == null) || (obj2 == null)) return null;
		
		Collection<Field> fields = collectFields(obj1.getClass());
		Collection<Field> diff = new HashSet<Field>();
		
		for (Field field : fields) {
			if ((excludes != null) && excludes.contains(field.getName())) {
				continue;
			}
			
			field.setAccessible(true);
			Object val1 = field.get(obj1);
			Object val2 = field.get(obj2);
			
			if (((val1 == null) && (val2 == null)) || ((val1 != null) && val1.equals(val2))) {
				continue;
			}
			
			diff.add(field);
		}
		
		return diff;
	}
	
	private static Collection<Field> collectFields(Class<?> clazz) {
		Set<Field> fields = new HashSet<Field>();
		
		while (clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				fields.add(field);
			}
			clazz = clazz.getSuperclass();
		}
		
		return fields;
	}
}
