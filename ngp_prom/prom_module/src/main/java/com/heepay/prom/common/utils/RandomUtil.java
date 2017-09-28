package com.heepay.prom.common.utils;

import com.heepay.codec.Md5;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.date.DateUtils;
import com.heepay.enums.MerchantAutographType;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

/**
 * 
 * 描 述：生成需要的值工具
 *
 * 创 建 者： ly 创建时间： 2016年9月14日 上午11:50:41 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */

public class RandomUtil {

    private static HashSet<Integer> set = new HashSet<Integer>();

    private static PropertiesLoader loader = new PropertiesLoader("fastDFS.properties");

    /**
     * @discription 获取签约号
     * @author ly
     * @created 2016年9月14日 上午11:50:38
     * @param now
     * @return
     */
    public static synchronized String getNum(String now) {
        String date = DateFormatUtils.format(new Date(), DateUtils.DB_FORMAT_DATE);
        if (!date.equals(now)) {
            set = new HashSet<Integer>();
        }
        String str2 = null;
        while (true) {
            int setSizeBefore = set.size();
            Random rd = new Random();
            int rdnum = rd.nextInt(9999);
            str2 = getFourNumber(rdnum);
            set.add(rdnum);
            int setSizeAfter = set.size();
            if (setSizeBefore + 1 == setSizeAfter) {
                break;
            }
        }
        return now + str2;
    }

    /**
     * @discription 获取4位数字
     * @author ly
     * @created 2016年11月16日 下午7:58:05
     * @param rdnum
     * @return
     */
    public static String getFourNumber(int rdnum) {
        String str2;
        DecimalFormat df = new DecimalFormat("0000");
        str2 = df.format(rdnum);
        return str2;
    }

    /**
     * @discription 获取随机打款金额
     * @author ly
     * @created 2016年10月13日 上午10:45:23
     * @return
     */
    public static String getMoney() {
        Random rd = new Random();
        int rdnum = rd.nextInt(98) + 1;
        BigDecimal a = new BigDecimal(rdnum);
        BigDecimal b = new BigDecimal(100);
        return a.divide(b, 2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * @discription 获取fastDfs图片地址
     * @author ly
     * @created 2016年10月13日 上午10:44:41
     * @param imageAddr
     * @return
     */
    public static String getFastDfs(String imageAddr) {
        String fastDfs = loader.getProperty("fastdfs.image.host");
        return fastDfs + imageAddr;
    }

    /**
     * @discription 获取产品签名加密key
     * @author ly
     * @created 2016年10月13日 上午10:44:57
     * @return
     */
    public static String getKey(String merchantId, String productCode, String autographType) {
        // String key = loader.getProperty("key");//Aes , Des加密key
        String content = merchantId + productCode + System.currentTimeMillis() + autographType;
        String key = null;
        if (MerchantAutographType.MD5.getValue().equals(autographType)) {
            key = Md5.encode(content);
        }
        return key;
    }

    /**
     * @discription bankNo脱敏处理
     * @author ly
     * @created 2016年10月17日 下午2:09:16
     * @param bankNo
     * @return
     */
    public static String getBankNo(String bankNo) {
        return bankNo.substring(0, 6) + "****" + bankNo.substring(bankNo.length() - 4);
    }

    /**
     * @discription 获取插件下载地址
     * @author ly
     * @created 2017年1月9日14:25:28
     */
    public static String getDownloadAddress() {
        return loader.getProperty("downloadAddress");
    }

}
