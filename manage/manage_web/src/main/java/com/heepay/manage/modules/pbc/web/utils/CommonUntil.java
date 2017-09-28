package com.heepay.manage.modules.pbc.web.utils;

import java.util.Random;


/***
 * 
* 
* 描    述：传输报文流水号编码规则
*
* 创 建 者：wangl
* 创建时间：  Dec 13, 201611:51:16 AM
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
public class CommonUntil {

   
    public String  getReqHeader(){
       
        //不同请求有不同的交易类型编码
        
        //传输报文流水号（参见附录H）
        /*Random rnd = new Random();
        int digCount = 28;
        StringBuilder sb = new StringBuilder(digCount);
        for (int i = 0; i < digCount; i++){
            sb.append(rnd.nextInt(10));
        }*/

        String serialNo= "Z11611000017"+"_"+"000000000000"+getRandomNumber(28);
        
        return serialNo;
    }

    public static String getRandomNumber(int digCount){
        Random rndnew = new Random();
        StringBuilder sb = new StringBuilder(digCount);
        for (int i = 0; i < digCount; i++){
            sb.append(rndnew.nextInt(10));
        }
        return sb.toString();
    }


}
