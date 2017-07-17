package com.huatai.common.ctp;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.bridj.ann.Name;
import org.bridj.cpp.CPPObject;

/**
 * \u9a8c\u8bc1\u5ba2\u6237\u4fe1\u606f<br>
 * <i>native declaration : line 13031</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcVerifyCustInfoField")
@Library("thosttraderapi")
public class CThostFtdcVerifyCustInfoField extends StructObject {
	static {
		BridJ.register();
	}
	
	/**
	 * \u5ba2\u6237\u59d3\u540d<br>
	 * C type : TThostFtdcIndividualNameType
	 */
	@Array({ 51 })
	@Field(0)
	public Pointer<Byte> CustomerName() {
		return io.getPointerField(this, 0);
	}
	
	/**
	 * \u8bc1\u4ef6\u7c7b\u578b<br>
	 * C type : TThostFtdcIdCardTypeType
	 */
	@Field(1)
	public byte IdCardType() {
		return io.getByteField(this, 1);
	}
	
	/**
	 * \u8bc1\u4ef6\u7c7b\u578b<br>
	 * C type : TThostFtdcIdCardTypeType
	 */
	@Field(1)
	public CThostFtdcVerifyCustInfoField IdCardType(byte IdCardType) {
		io.setByteField(this, 1, IdCardType);
		return this;
	}
	
	/**
	 * \u8bc1\u4ef6\u53f7\u7801<br>
	 * C type : TThostFtdcIdentifiedCardNoType
	 */
	@Array({ 51 })
	@Field(2)
	public Pointer<Byte> IdentifiedCardNo() {
		return io.getPointerField(this, 2);
	}
	
	/**
	 * \u5ba2\u6237\u7c7b\u578b<br>
	 * C type : TThostFtdcCustTypeType
	 */
	@Field(3)
	public byte CustType() {
		return io.getByteField(this, 3);
	}
	
	/**
	 * \u5ba2\u6237\u7c7b\u578b<br>
	 * C type : TThostFtdcCustTypeType
	 */
	@Field(3)
	public CThostFtdcVerifyCustInfoField CustType(byte CustType) {
		io.setByteField(this, 3, CustType);
		return this;
	}
	
	public CThostFtdcVerifyCustInfoField() {
		super();
	}
	
	public CThostFtdcVerifyCustInfoField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}