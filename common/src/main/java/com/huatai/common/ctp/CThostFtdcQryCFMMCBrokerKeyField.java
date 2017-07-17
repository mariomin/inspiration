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
 * \u67e5\u8be2\u4fdd\u8bc1\u91d1\u76d1\u7ba1\u7cfb\u7edf\u7ecf\u7eaa\u516c\u53f8\u5bc6\u94a5<br>
 * <i>native declaration : line 11527</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Name("CThostFtdcQryCFMMCBrokerKeyField")
@Library("thosttraderapi")
public class CThostFtdcQryCFMMCBrokerKeyField extends StructObject {
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
	
	public CThostFtdcQryCFMMCBrokerKeyField() {
		super();
	}
	
	public CThostFtdcQryCFMMCBrokerKeyField(Pointer<? extends CPPObject> pointer) {
		super(pointer);
	}
}
