package com.huatai.common.data;

public abstract class IdentifiableObject extends PersistentObject {
	private static final long serialVersionUID = 1L;
	protected String id;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
