package com.huatai.platform.downstream.adaptor.o32.common;

import com.huatai.common.o32.tags.O32Tags.Field;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

public class O32Util {
	
	public static boolean isSuccess(IEvent event) {
		String errorCode = event.getEventDatas().getDataset(0).getString(Field.errorCode);
		return "0".equals(errorCode);
	}
	
	public static final String toString(IEvent event) {
		if (null == event) return null;
		IDatasets result = event.getEventDatas();
		if (result == null) return null;
		
		StringBuilder strBuf = new StringBuilder();
		strBuf.append("\nFunctionID=").append(event.getServiceAlias()).append("\n");
		int datasetCount = result.getDatasetCount();
		for (int i = 0; i < datasetCount; i++) {
			IDataset ds = result.getDataset(i);
			int columnCount = ds.getColumnCount();
			for (int j = 1; j <= columnCount; j++) {
				strBuf.append("\"" + ds.getColumnName(j) + "\",");
			}
			strBuf.deleteCharAt(strBuf.length() - 1);
			strBuf.append("\n");
			
			ds.beforeFirst();
			while (ds.hasNext()) {
				ds.next();
				for (int j = 1; j <= columnCount; j++) {
					strBuf.append("\"" + ds.getString(j) + "\",");
				}
				strBuf.deleteCharAt(strBuf.length() - 1);
				strBuf.append("\n");
			}
			
		}
		
		return strBuf.toString();
	}
}
