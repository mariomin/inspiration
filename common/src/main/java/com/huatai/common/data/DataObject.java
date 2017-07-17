package com.huatai.common.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.huatai.common.type.OrderField;
import com.huatai.common.util.ReflectionUtil;

public class DataObject implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;
	private HashMap<String, Object> fields;
	
	public Map<String, Object> getFields() {
		return fields;
	}
	
	public DataObject() {
		fields = new HashMap<String, Object>();
	}
	
	public DataObject(Map<String, Object> f) {
		fields = new HashMap<String, Object>(f);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> t, String fieldName) {
		if (fields != null) {
			Object obj = fields.get(fieldName);
			if (t.isPrimitive() && (obj == null)) return (T) ReflectionUtil.getPrimitiveDefaultValues(t);
			return (T) obj;
		}
		return null;
	}
	
	public <T> T get(T e, Class<T> t, String fieldName) {
		if (fieldExists(fieldName))
			return get(t, fieldName);
		else return e;
	}
	
	public boolean fieldExists(String name) {
		return fields.containsKey(name);
	}
	
	public Object put(String name, Object value) {
		return fields.put(name, value);
	}
	
	public void putAll(DataObject object) {
		fields.putAll(object.getFields());
	}
	
	public Object remove(OrderField name) {
		return fields.remove(name);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public DataObject clone() {
		try {
			DataObject obj = (DataObject) super.clone();
			if (fields != null) {
				obj.fields = (HashMap<String, Object>) fields.clone();
			}
			
			return obj;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((fields == null) ? 0 : fields.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		DataObject other = (DataObject) obj;
		if (fields == null) {
			if (other.fields != null) return false;
		} else if (!fields.equals(other.fields)) return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataObject [fields=");
		builder.append(fields);
		builder.append("]");
		return builder.toString();
	}
	
}
