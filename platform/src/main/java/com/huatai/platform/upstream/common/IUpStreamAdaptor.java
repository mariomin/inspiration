package com.huatai.platform.upstream.common;

import com.huatai.common.fix.FixException;
import com.huatai.common.stream.IStreamAdaptor;

public interface IUpStreamAdaptor<T> extends IStreamAdaptor<T> {
	void init(IUpStreamCallbackHandler handler) throws FixException;
}
