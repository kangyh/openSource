package com.heepay.manage.common.utils;

import com.heepay.codec.MD5Util;
import com.heepay.common.util.HttpClientUtils;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * 描    述： 动态口令校验方法
 * <p/>
 * 创 建 者： L.M
 * 创建时间： 2016/12/12 10:59
 * 创建描述：
 * <p/>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p/>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
public class DynamicPasswordUtil {

    private static final Logger logger = LogManager.getLogger();

    private static PropertiesLoader loader = new PropertiesLoader("fastDFS.properties");
    
    private static final String DYNAMIC_PASSWORD_URL = loader.getProperty("dynamic.password.url");
    
      
    /**     
    * @discription 验证动态口令
    * @author ly     
    * @created 2016年12月14日 下午9:45:27     
    * @param passW
    * @return     
    */
    public static String check(String passW, User user){
        long currTime = System.currentTimeMillis();
        String signKey = currTime + Constants.DYNAMIC_IDENTIFICATION;
        //判断是否是admin
        if(user.isAdmin()){
            return "0";
        }
        //当前登录用户的UserNO
        String sysUserNo= user.getNo();
        String params;
        String operateType;
        if(sysUserNo.length()>12){
            operateType="phone";
        }else{
            operateType="card";
        }
        params = "type="+operateType+"&passNo="+sysUserNo+"&passPwd="+passW+"&ts="+currTime;
        String sn = MD5Util.md5(params + "|" + signKey);
        
        String url = DYNAMIC_PASSWORD_URL+params+"&sn="+sn;
        logger.info(params);
        logger.info("时间：" + currTime);
        logger.info("SN:" + sn);
        String json = HttpClientUtils.httpGet(url);
        Map<?, ?> map = new JsonMapperUtil().fromJson(json, Map.class);
        logger.info("返回码code：{}",map.get("RetCode"));
        return  map.get("RetCode").toString();
    }

}
