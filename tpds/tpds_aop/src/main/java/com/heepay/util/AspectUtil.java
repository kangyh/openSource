/*
 * Copyright (c) 2017 Pay. All rights reserved.
 */
package com.heepay.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.model.User;

/**
 * <P>
 * Description:
 * </p>
 *
 * @author kangyh
 * @version 1.0
 * @Date 
 */
public class AspectUtil {
    private final Logger logger = LoggerFactory.getLogger(AspectUtil.class);
    private final LinkedBlockingQueue<User> queue = new LinkedBlockingQueue<User>(100); //阻塞队列	

    private Map<String, Map<Long, Long>> minuteInvokeCostTime = new HashMap<String, Map<Long, Long>>();	//统计
    
    @Autowired
    DataHandleOut dataHandleOut;

    public Object doAround(ProceedingJoinPoint joinPoint) {
        Long beginTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            Long expend = (System.currentTimeMillis() - beginTime);
            String targetPackage = joinPoint.getTarget().getClass().getPackage().getName();
            String targetClass = joinPoint.getTarget().getClass().getName();
            String targetMethod = joinPoint.getSignature().getName();
            String name = targetPackage + targetClass +"."+ targetMethod;
            User user = new User(name, beginTime, expend);
            try {
                queue.put(user);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void entrance() {
        try {
            User InvokeUser = queue.take();
            dataHandleOut.DataHandle(InvokeUser, minuteInvokeCostTime, InvokeUser.getBeginTime(), 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Map<Long, Long>> getMinuteInvokeCostTime() {
        return minuteInvokeCostTime;
    }

    public void setMinuteInvokeCostTime(Map<String, Map<Long, Long>> minuteInvokeCostTime) {
        this.minuteInvokeCostTime = minuteInvokeCostTime;
    }
}
