package com.heepay.manage.modules.payment.param;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 
 * 请求参数注解，描述一个请求参数类是否为必须，或者是否参与签名 
 * @author houjianchun
 * 
 * */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME) 
@Inherited
public  @interface ParamConfig {
	
	//是否必须 true/false
	public abstract boolean required() default false;
	
	//是否进行签名 true /false
	public abstract boolean isSign() default false;
	
	//签名排序值
	public abstract int sort() default 0;
}
