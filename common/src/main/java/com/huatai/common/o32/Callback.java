package com.huatai.common.o32;

import com.hundsun.t2sdk.common.share.dataset.DatasetService;
import com.hundsun.t2sdk.common.share.event.PackService;
import com.hundsun.t2sdk.impl.client.ClientSocket;
import com.hundsun.t2sdk.interfaces.ICallBackMethod;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;
import com.hundsun.t2sdk.interfaces.share.event.IPack;

public class Callback implements ICallBackMethod {
	private static IClient client;
	
	@Override
	public void execute(IEvent arg0, ClientSocket arg1) {
		long iFunctionID = arg0.getIntegerAttributeValue(EventTagdef.TAG_FUNCTION_ID);
		
		if (iFunctionID == 620000) {
			arg0.changeToresponse();
			try {
				Callback.client.send(arg0);
			} catch (T2SDKException e) {
				e.printStackTrace();
			}
		} else if ((iFunctionID == 620003) || (iFunctionID == 620025)) {
			byte[] keyInfo = arg0.getByteArrayAttributeValue(EventTagdef.TAG_KEY_INFO);
			IPack outPack = PackService.getPacker(keyInfo, "utf-8");
			StringBuilder sb = new StringBuilder();
			
			IDataset ds = outPack.getDataset(0);
			int columnCount = ds.getColumnCount();
			
			for (int j = 1; j <= columnCount; j++) {
				sb.append(ds.getColumnName(j));
				sb.append("|");
				sb.append(ds.getColumnType(j));
				sb.append("\n");
			}
			ds.beforeFirst();
			while (ds.hasNext()) {
				sb.append("\n");
				ds.next();
				for (int j = 1; j <= columnCount; j++) {
					sb.append(ds.getString(j));
				}
			}
			
			if (arg0.getEventDatas().getDatasetCount() > 0) {
				DatasetService.printDataset(arg0.getEventDatas().getDataset(0));
			}
		}
	}
	
	public static void setClient(IClient client) {
		Callback.client = client;
	}
}
