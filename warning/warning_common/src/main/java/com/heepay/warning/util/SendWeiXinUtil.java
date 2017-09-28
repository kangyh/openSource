package com.heepay.warning.util;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;
import com.heepay.codec.MD5Util;
import com.heepay.common.util.HttpClientUtils;
import com.heepay.common.util.StringUtil;
import com.heepay.enums.WeiXinType;


/**
 * 
* 
* 描    述：发送微信工具类
*
* 创 建 者： 王亚洪  
* 创建时间： 2016年12月26日 下午2:11:34 
* 创建描述：
* 
* 修 改 者：  
* 修改时间： 
* 修改描述： 
* 
* 审 核 者：
* 审核时间：
* 审核描述：
*
 */
public class SendWeiXinUtil {
  
  private static final String AT = "&";
  
  private static final Logger logger = LogManager.getLogger();
  
  private static Properties pros = null;
  
  private static SendWeiXinUtil wexinUtil = null;
  
  static {
    pros = new Properties();
    try {
      pros.load(SendWeiXinUtil.class.getResourceAsStream("/weixin.properties"));
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("获取微信配置文件失败", e);
    }
  }

  private static final String REQUEST_PATH = pros.getProperty("weixin.request.path");
  private static final String TYPE = pros.getProperty("weixin.request.param.type");
  private static final String ID = pros.getProperty("weixin.request.param.id");
  private static final String KEY = pros.getProperty("weixin.request.key");
  
  public static SendWeiXinUtil getInstance() {
    if (wexinUtil == null)
      wexinUtil = new SendWeiXinUtil();
    return wexinUtil;
  }
  
  
  /**
   * 
  * @description 发送微信
  * @author 王亚洪       
  * @created 2016年12月26日 下午2:24:01     
  * @param msg
  * @return
   */
  public String sendWeiXin(String id,String msg){
    
    //发送的内容不允许为空
    if(StringUtil.isBlank(msg)){
      return WeiXinType.labelOf(WeiXinType.NOTNULL.getValue());
    }
    
    try {
      
      StringBuilder md5Builer = new StringBuilder(200); //构造要MD5加密的字符串
      String ts = String.valueOf(System.currentTimeMillis());
      
      String signKey = ts + KEY;
      md5Builer.append("type=").append(TYPE).append(AT)
               .append("id=").append(id).append(AT)
               .append("msg=").append(msg).append(AT)
               .append("ts=").append(ts);
      md5Builer.append("|").append(signKey);
      logger.info("要md5的字符串{}", md5Builer.toString());
      String sn = MD5Util.md5(md5Builer.toString(), "UTF-8");
      logger.info("sn={}", sn);
      Map<String, String> params = Maps.newHashMap();
      params.put("type", TYPE);
      params.put("id", id);
      params.put("msg", msg);
      params.put("ts", ts);
      params.put("sn", sn);
      
      HttpUtils.requestByPostMethodForWeiXin(REQUEST_PATH, params);
      
    } catch (Exception e) {
      logger.error("发送微信异常{}", e.getMessage());
      return WeiXinType.labelOf(WeiXinType.FAILED.getValue());
    }
    return WeiXinType.labelOf(WeiXinType.SUCCESS.getValue());
    
  }
  
  
  
}
