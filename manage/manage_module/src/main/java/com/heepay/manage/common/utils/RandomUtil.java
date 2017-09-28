package com.heepay.manage.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.heepay.manage.modules.merchant.dao.MerchantDao;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.sys.entity.Dict;
import com.heepay.manage.modules.sys.entity.Office;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.BusinessInfoUtils;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.heepay.codec.Md5;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.date.DateUtils;
import com.heepay.enums.MerchantAutographType;

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

    private static MerchantDao merchantDao = SpringContextHolder.getBean(MerchantDao.class);
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

    /**
     * @discription 获取T+X产品
     * @author ly
     * @created 2017年1月9日14:26:11
     */
    public static String getSettleX() {
        String productX = "";
        List<Dict> dictList = DictUtils.getDictList(Constants.SETTLEX);
        if(null != dictList && !dictList.isEmpty()){
            for(Dict dict : dictList){
                productX += dict.getValue();
            }
        }
        return productX;
    }


    /**
     * 获取当前用户及部门下面的员工Id拼接成的in();(只限商务查询商户使用)
     */
    public static String getInchargeId() {
        String inchargerId = "";
        boolean business = UserUtils.isBusiness();
        if(business){
            List<Office> officeList = UserUtils.getOfficeList();
            for(Office office : officeList){
                List<User> userList = BusinessInfoUtils.getUserList(office.getId());
                for(User user : userList){
                    inchargerId += "'" + user.getId()+"',";
                }
            }
            if(inchargerId.length()>0){
                inchargerId = inchargerId.substring(0,inchargerId.length()-1);
                inchargerId = "in (" + inchargerId + ")";
            }
        }
        return inchargerId;
    }

    /**
     * 获取当前用户及部门下面的员工可查看的商户id拼接成的in();(只限商务查询商户使用)
     */
    public static String getMerchantId(){
        String merchantId = "";
        String inchargerId = getInchargeId();
        Merchant merchantFind = new Merchant();
        merchantFind.setInchargerId(inchargerId);
        boolean business = UserUtils.isBusiness();
        if(business){
            List<Merchant> merchantList = merchantDao.findList(merchantFind);
            for(Merchant merchant : merchantList){
                merchantId += "'" + merchant.getUserId() + "',";
            }
            if(merchantId.length()>0){
                merchantId = merchantId.substring(0,merchantId.length()-1);
                merchantId = "in (" + merchantId + ")";
            }
        }
        return merchantId;
    }

    /**
     * 获取当前用户及部门下面的员工可查看的商户id list;(只限商务查询商户使用)
     */
    public static List<Long> getMerchantIdList(){
        List<Long> merchants = Lists.newArrayList();
        String inchargerId = getInchargeId();
        Merchant merchantFind = new Merchant();
        merchantFind.setInchargerId(inchargerId);
        boolean business = UserUtils.isBusiness();
        if(business){
            List<Merchant> merchantList = merchantDao.findList(merchantFind);
            for(Merchant merchant : merchantList){
                merchants.add(Long.parseLong(merchant.getUserId().toString()));
            }
        }
        return merchants;
    }
}
