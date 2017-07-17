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
 * \u67e5\u8be2\u7ecf\u7eaa\u516c\u53f8\u4ea4\u6613\u7b97\u6cd5<br>
 * <i>native declaration : line 11459</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcQryBrokerTradingAlgosField")
@Library("thosttraderapi")
public class CThostFtdcQryBrokerTradingAlgosField extends StructObject {
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
	 * \u4ea4\u6613\u6240\u4ee3\u7801<br>
	 * C type : TThostFtdcExchangeIDType
	 */
	@Array({ 9 })
	@Field(1)
	public Pointer<Byte> ExchangeID() {
		return io.getPointerField(this, 1);
	}
	
	/**
	 * \u5408\u7ea6\u4ee3\u7801<br>
	 * C type : TThostFtdcInstrumentIDType
	 */
	@Array({ 31 })
	@Field(2)
	public Pointer<Byte> InstrumentID() {
		return io.getPointerField(this, 2);
	}
	
	public CThostFtdcQryBrokerTradingAlgosField() {
		super();
	}
	
	public CThostFtdcQryBrokerTradingAlgosField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}