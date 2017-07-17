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
 * \u5220\u9664\u9884\u57cb\u64a4\u5355<br>
 * <i>native declaration : line 11016</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcRemoveParkedOrderActionField")
@Library("thosttraderapi")
public class CThostFtdcRemoveParkedOrderActionField extends StructObject {
	static {
		BridJ.register();
	}
	
	/**
	 * \u7ecf\u7eaa\u516c\u53f8\u4ee3\u7801<br>
	 * C type : TThostFtdcBrokerIDType
	 */
	@Array({ 11 })
	@Field(0)
	public Pointer<Byte> BrokerID() {
		return io.getPointerField(this, 0);
	}
	
	/**
	 * \u6295\u8d44\u8005\u4ee3\u7801<br>
	 * C type : TThostFtdcInvestorIDType
	 */
	@Array({ 13 })
	@Field(1)
	public Pointer<Byte> InvestorID() {
		return io.getPointerField(this, 1);
	}
	
	/**
	 * \u9884\u57cb\u64a4\u5355\u7f16\u53f7<br>
	 * C type : TThostFtdcParkedOrderActionIDType
	 */
	@Array({ 13 })
	@Field(2)
	public Pointer<Byte> ParkedOrderActionID() {
		return io.getPointerField(this, 2);
	}
	
	public CThostFtdcRemoveParkedOrderActionField() {
		super();
	}
	
	public CThostFtdcRemoveParkedOrderActionField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}