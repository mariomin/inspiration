package com.huatai.common.fix.tag;

import quickfix.Group;

public class NoStrategies extends Group {
	private static final long serialVersionUID = 1L;
	public static final int FIELD = FixTags.NoStrategies;
	public static final int DELIM = FixTags.NoStrategies;
	
	public NoStrategies() {
		super(FIELD, DELIM);
	}
	
}
