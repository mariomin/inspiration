package com.huatai.platform.event;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.function.Consumer;

import com.huatai.common.event.base.AsyncEvent;

public class LambdaInnovationPack {
	private static final Lookup caller = MethodHandles.lookup();
	private static final MethodType samMethodType = MethodType.methodType(void.class, Object.class);
	
	private final MethodType invokeType;
	private final MethodType actualmethodType;
	private final MethodHandle methodHandle;
	private final CallSite callSite;
	private final Consumer<AsyncEvent> lambda;
	
	public LambdaInnovationPack(Object handler, Class<? extends AsyncEvent> eventClass, String methodName) throws Throwable {
		invokeType = MethodType.methodType(Consumer.class, handler.getClass());
		actualmethodType = MethodType.methodType(void.class, eventClass);
		methodHandle = caller.findVirtual(handler.getClass(), methodName, actualmethodType);
		
		callSite = LambdaMetafactory.metafactory(caller, "accept", invokeType, samMethodType, methodHandle, actualmethodType);
		lambda = (Consumer<AsyncEvent>) callSite.getTarget().bindTo(handler).invoke();
	}
	
	public Consumer<AsyncEvent> getLambda() {
		return lambda;
	}
}
