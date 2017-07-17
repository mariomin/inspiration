package com.huatai.common.algo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yachen Zhu
 * For Strategy Scan Purpose
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AtsStrategy {
	
	Class<?> param() default Object.class;
	
	String name() default "";
	
	String value() default "";
	
	String chineseName() default "";
	
	boolean available() default true;
}
