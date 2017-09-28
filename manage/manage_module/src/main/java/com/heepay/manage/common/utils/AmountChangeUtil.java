package com.heepay.manage.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**          
* 
* 描    述：金钱转换工具类
*
* 创 建 者： ly
* 创建时间： 2016年9月7日 下午7:11:45 
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
    
public class AmountChangeUtil {
  public static BigDecimal change(BigDecimal amount){
    return new BigDecimal(new DecimalFormat("#.00").format(amount));
  }
}
