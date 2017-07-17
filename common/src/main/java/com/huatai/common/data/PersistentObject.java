package com.huatai.common.data;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class PersistentObject implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	protected Timestamp created = new Timestamp(System.currentTimeMillis());
	protected Timestamp modified = new Timestamp(System.currentTimeMillis());
	
	public void touch() {
		modified.setTime(System.currentTimeMillis());
	}
	
	public Timestamp getCreated() {
		return created;
	}
	
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	
	public Timestamp getModified() {
		return modified;
	}
	
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	public synchronized void update(PersistentObject persistentObject) {
		created = persistentObject.getCreated();
		touch();
	}
}
