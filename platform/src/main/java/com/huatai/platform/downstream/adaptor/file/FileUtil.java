package com.huatai.platform.downstream.adaptor.file;

import java.io.File;

public class FileUtil {
	
	public static boolean mkdirs(String file) {
		String path = new File(file).getParent();
		File folder = new File(path);
		if (!folder.exists()) return folder.mkdirs();
		
		return true;
	}
}
