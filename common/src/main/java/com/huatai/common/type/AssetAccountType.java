package com.huatai.common.type;

import java.util.HashMap;
import java.util.Map;

public enum AssetAccountType {
	cash("cash"), credit("credit"), future("future"), option("option");
	
	private String displayName;
	
	private static Map<String, AssetAccountType> maps = new HashMap<String, AssetAccountType>();
	
	static {
		for (AssetAccountType assetAccountType : AssetAccountType.values()) {
			maps.put(assetAccountType.value(), assetAccountType);
		}
	}
	
	AssetAccountType(String englishName) {
		displayName = englishName;
	}
	
	public String value() {
		return displayName;
	}
	
	public static AssetAccountType getAssetAccTypeByName(String englishName) {
		return maps.get(englishName);
	}
}
