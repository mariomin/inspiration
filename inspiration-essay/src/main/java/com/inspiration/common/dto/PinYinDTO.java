package com.inspiration.common.dto;

/**
 * 汉字转拼音DTO 
 * chineseChar 中文字符 
 * pinyinChar 拼音字符 
 * Abbreviation 缩写 flag 是否翻译标志 1=已翻译(不翻译英文字符、拼音)
 **/
public class PinYinDTO {
	private String chineseChar;
	private String[] pinyinChar;
	private Boolean flag;

	public String getChineseChar() {
		return chineseChar;
	}

	public void setChineseChar(String chineseChar) {
		this.chineseChar = chineseChar;
	}

	public String[] getPinyinChar() {
		return pinyinChar;
	}

	public void setPinyinChar(String[] pinyinChar) {
		this.pinyinChar = pinyinChar;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

}
