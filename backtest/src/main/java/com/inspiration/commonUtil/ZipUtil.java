package com.inspiration.commonUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	
	public static final byte[] compress(String content) throws IOException {
		if (content == null) return null;
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ZipOutputStream zout = new ZipOutputStream(bout);
		
		try {
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(content.getBytes());
			zout.closeEntry();
			return bout.toByteArray();
		}
		finally {
			if (zout != null) {
				zout.close();
			}
			if (bout != null) {
				bout.close();
			}
		}
	}
	
	public static final String decompress(byte[] content) throws IOException {
		if (content == null) return null;
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ByteArrayInputStream bin = new ByteArrayInputStream(content);
		ZipInputStream zin = new ZipInputStream(bin);
		
		int len;
		byte[] buffer = new byte[1024];
		try {
			zin.getNextEntry();
			while ((len = zin.read(buffer)) != -1) {
				bout.write(buffer, 0, len);
			}
			return bout.toString();
		}
		finally {
			if (zin != null) {
				zin.close();
			}
			if (bin != null) {
				bin.close();
			}
			if (bout != null) {
				bout.close();
			}
		}
	}
	
}
