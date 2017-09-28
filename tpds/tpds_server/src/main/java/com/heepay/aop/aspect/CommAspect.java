package com.heepay.aop.aspect;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/** 
 * Advice通知类 
 * after,before,around,throwing,returning Advice. 
 * @author kangyh 
 * 
 */
public class CommAspect {
	
	private static final Logger logger = LogManager.getLogger();
	
    private String methodName=null;
	private Map<String,Object> inputParamMap = null ; 	// 传入参数  
    private Map<String, Object> outputParamMap = null; 	// 存放输出结果  
    private long startTimeMillis = 0; 					// 开始时间  
    private long endTimeMillis = 0; 					// 结束时间  
    
    /**
     * 清理参数
     */
    private void delParam(){
        try {
			methodName="";
			inputParamMap.clear();
			outputParamMap.clear();
			startTimeMillis=0L;
			endTimeMillis=0L;
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /** 
     * 在核心业务执行前执行，不能阻止核心业务的调用。 
     * @param joinPoint 
     */
    public void doBefore(JoinPoint joinPoint) {
        startTimeMillis = System.currentTimeMillis(); // 记录方法开始执行的时间  
    } 
    
    /** 
     * 核心业务逻辑退出后（包括正常执行结束和异常退出），执行此Advice 
     * @param joinPoint 
     */
    public void doAfter(JoinPoint joinPoint) {  
        endTimeMillis = System.currentTimeMillis(); // 记录方法执行完成的时间  
        this.printOptLog();
        this.delParam();
    } 
    
    /** 
     * 手动控制调用核心业务逻辑，以及调用前和调用后的处理, 
     *  
     * 注意：当核心业务抛异常后，立即退出，转向After Advice 
     * 执行完毕After Advice，再转到Throwing Advice 
     * @param pjp 
     * @return 
     * @throws Throwable 
     */ 
    public Object doAround(ProceedingJoinPoint pjp){
    	Object result = null;
    	Object[] args = pjp.getArgs();
        try {  
        	result = pjp.proceed(args);  // result的值就是被拦截方法的返回值 
        } catch (Throwable e) {
            logger.error("统计方法：{"+methodName+"}执行环绕通知出错", e);
        }
        inputParamMap = new HashMap<String, Object>();
        if(!(args==null)&&(args.length>0)){
        	for(int len=0;len<args.length;len++){
        		inputParamMap.put(String.valueOf(len), args[len]);
        	}
        }
        
        // 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行  
        outputParamMap = new HashMap<String, Object>();
        
        // 获取执行的方法名 
        MethodSignature signature = (MethodSignature) pjp.getSignature();  
        methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        outputParamMap.put("result", result);
        this.printAroundTime(methodName, inputParamMap, outputParamMap);
        return result;  
    }
    
    /** 
     * 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice 
     * @param joinPoint 
     */  
    private void doReturn(JoinPoint joinPoint) {  
    	logger.debug("-----doReturn().invoke-----");  
    	logger.debug(" 此处可以对返回值做进一步处理");  
    	logger.debug(" 可通过joinPoint来获取所需要的内容");  
    	logger.debug("-----End of doReturn()------");  
    }  
      
    /** 
     * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息 
     * @param joinPoint 
     * @param ex 
     */  
    private void doThrowing(JoinPoint joinPoint,Throwable ex) {  
    	logger.debug("-----doThrowing().invoke-----");  
    	logger.debug(" 错误信息："+ex.getMessage());  
    	logger.debug(" 此处意在执行核心业务逻辑出错时，捕获异常，并可做一些日志记录操作等等");  
    	logger.debug(" 可通过joinPoint来获取所需要的内容");  
    	logger.debug("-----End of doThrowing()------");  
    } 
    
    private void printOptLog() {
    	long diffTime = endTimeMillis-startTimeMillis;
    	logger.info("统计："+methodName+ " 方法执行耗时：" + diffTime + " ms");
    }
    
    private void printAroundTime(String methodName,Map<String,Object> inputParamMap,Map<String,Object> outputParamMap) {  
    	logger.debug("-----" + methodName + " 方法执行请求参数：" + inputParamMap.toString() + ",应答结果："+outputParamMap.toString());
    } 
    
    
} 
