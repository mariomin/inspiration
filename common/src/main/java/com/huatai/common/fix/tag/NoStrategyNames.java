package com.huatai.common.fix.tag;

import quickfix.Group;

public class NoStrategyNames extends Group {
	private static final long serialVersionUID = 1L;
	public static final int FIELD = FixTags.NoStrategyNames;
	public static final int DELIM = FixTags.NoStrategyNames;
	
	public NoStrategyNames() {
		super(FIELD, DELIM);
	}
	
}
