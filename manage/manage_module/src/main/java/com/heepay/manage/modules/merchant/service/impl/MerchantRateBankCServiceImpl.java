/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.service.impl;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.SendMailUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.RateBankcardType;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.modules.merchant.dao.MerchantProductRateDao;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.BuildUpdateRateEmailContentOnBatch;
import com.heepay.manage.modules.merchant.dao.MerchantProductRateDao;
import com.heepay.manage.modules.merchant.dao.MerchantRateBankDao;
import com.heepay.manage.modules.merchant.dao.check.MerchantProductRateCheckDao;
import com.heepay.manage.modules.merchant.dao.check.MerchantRateBankCheckDao;
import com.heepay.manage.modules.merchant.service.MerchantRateBankCService;
import com.heepay.manage.modules.merchant.vo.MerchantProductRate;
import com.heepay.manage.modules.merchant.vo.MerchantRateBank;
import com.heepay.manage.modules.merchant.vo.MerchantRateNew;
import com.heepay.manage.modules.notification.dao.NotificationEmailDao;
import com.heepay.manage.modules.route.dao.BankInfoDao;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * 描    述：商户费率银行Service
 *
 * 创 建 者： @author ly
 * 创建时间：
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
@Service
@Transactional(readOnly = true)
public class MerchantRateBankCServiceImpl extends CrudService<MerchantRateBankCheckDao, MerchantRateBank> implements MerchantRateBankCService{

    @Value("#{task['update.rate.email']}")
    private String environment;
    @Autowired
    private MerchantRateBankDao merchantRateBankDao;

    @Autowired
    private MerchantRateBankCheckDao merchantRateBankCheckDao;

    @Autowired
    private BankInfoDao bankInfoDao;
    @Autowired
    private MerchantRateNewCServiceImpl merchantRateNewCService;

    @Autowired
    private NotificationEmailDao notificationEmailDao;

    @Autowired
    private MerchantProductRateDao merchantProductRateDao;

    @Autowired
    private MerchantProductRateCheckDao merchantProductRateCheckDao;

    @Override
    public List<MerchantRateBank> getBankCardType(String id) {
        return merchantRateBankDao.getBankCardType(id);
    }

    /**
     * 批量修改费率方法
     */
    @Override
    @Transactional(readOnly = false)
    public void batchSaveRate(String checkedstr, String somerateType, MerchantRateNew merchantRateNew){
        //修改审核状态为待审核
        merchantProductRateCheckDao.uodateRateAudit(merchantRateNew.getRateId(),"S");
        //判断是否为按卡类型修改
        try {
            if(StringUtils.isNotBlank(somerateType)){
                saveByBankCardType(checkedstr, merchantRateNew);
            }else{
                saveNotBankCardType(checkedstr, merchantRateNew);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    /**
     * @方法说明：查询整个对象
     * @时间： 2017-06-05 03:39 PM
     * @创建人：wangl
     */
    @Override
    public  List<MerchantRateBank> getEntityByRateId(String rateId) {
        return merchantRateBankDao.getEntityByRateId(rateId);
    }

    /**
     * 批量修改费率 按卡类型
     */
    private void saveByBankCardType(String checkedstr, MerchantRateNew merchantRateNew) throws UnsupportedEncodingException {
        String json = URLDecoder.decode(checkedstr,"utf-8");
        String bankCardType = JsonMapperUtil.nonDefaultMapper().fromJson(json,String.class);
        MerchantRateBank merchantRateBank = new MerchantRateBank();
        merchantRateBank.setRateId(merchantRateNew.getRateId());
        merchantRateBank.setBankCardType(bankCardType);
        changeRate(merchantRateNew, merchantRateBank);
        //修改费率发邮件
        List<MerchantRateBank> merchantRateBanks = merchantRateBankDao.selectInfoByBankCardType(merchantRateBank);
        merchantRateBank.preUpdate();
        MerchantProductRate merchantProductRate = merchantProductRateDao.get(merchantRateNew.getRateId());
        merchantRateNew.setDelFlag("0");
        merchantRateNew.setBankCardType(bankCardType);
        List<String> emails = notificationEmailDao.queryList();
        String toEmail = listToString(emails, ',');
        String type = "";
        String table;
        // 构建page对象，调用获取页面的方法，在页面中获取list
        Page<MerchantRateNew> page =  new Page<>();
        page.setPageNo(1);
        page.setPageSize(1000);
        Page<MerchantRateNew> page1 = merchantRateNewCService.findPage(page, merchantRateNew);
        if(merchantRateBanks.isEmpty()){
            table = "";
        }else {
            if (checkedstr.contains("SAVING")) {
                type = "<font color = \"#FF0000;\" >储蓄卡</font>";
                merchantRateBank.setBankCardType(RateBankcardType.DEBITCARD.getValue());
                table = BuildUpdateRateEmailContentOnBatch.getTableByCardType(page1.getList(),merchantRateBank);
            } else if (checkedstr.contains("CREDIT")) {
                type = "<font color = \"#FF0000;\" >信用卡</font>";
                merchantRateBank.setBankCardType(RateBankcardType.CREDITCARD.getValue());
                table = BuildUpdateRateEmailContentOnBatch.getTableByCardType(page1.getList(),merchantRateBank);
            } else {
                type = "<font color = \"#FF0000;\" >全部</font>";
                table = BuildUpdateRateEmailContentOnBatch.getTableByCardType(page1.getList(),merchantRateBank);
            }
        }
        String content = "用户(id:name)"+ UserUtils.getUser().getId() +":"+ UserUtils.getUser().getName() + "在时间："
                + com.heepay.common.util.DateUtil.getTodayYYYYMMDD_HHMMSS() + "修改了商户ID为："+ merchantProductRate.getMerchantId() +
                "，产品《" + merchantProductRate.getProductName() +"》的费率；" +"</br>修改方式：批量修改费率-按卡类型(" + type + ")" + table;
        boolean isSuccess = SendMailUtil.getInstance().sendMail(toEmail,environment + "费率修改通知",content,null);
        logger.info("批量修改费率 按卡类型  发送邮件通知结果{}。发送内容为{}",isSuccess,content);
        merchantRateBankCheckDao.updateRateFee(merchantRateBank);
    }

    /**
     * 转换费率
     */
    private void changeRate(MerchantRateNew merchantRateNew, MerchantRateBank merchantRateBank) {
        if(StringUtils.isNotBlank(merchantRateNew.getChargeType())){
            merchantRateBank.setChargeType(merchantRateNew.getChargeType());
            merchantRateBank.setMaxFee(merchantRateNew.getMaxFee());
            merchantRateBank.setMinFee(merchantRateNew.getMinFee());
            //阶梯(1)
            merchantRateBank.setChargeRatio(merchantRateNew.getChargeRatio());
            merchantRateBank.setChargeFee(merchantRateNew.getChargeFee());
            merchantRateBank.setChargeMax(merchantRateNew.getChargeMax());
            merchantRateBank.setChargeMin(merchantRateNew.getChargeMin());
            //阶梯(2)
            merchantRateBank.setChargeRatio2(merchantRateNew.getChargeRatio2());
            merchantRateBank.setChargeFee2(merchantRateNew.getChargeFee2());
            merchantRateBank.setChargeMax2(merchantRateNew.getChargeMax2());
            merchantRateBank.setChargeMin2(merchantRateNew.getChargeMin2());
            //阶梯(3)
            merchantRateBank.setChargeRatio3(merchantRateNew.getChargeRatio3());
            merchantRateBank.setChargeFee3(merchantRateNew.getChargeFee3());
            merchantRateBank.setChargeMax3(merchantRateNew.getChargeMax3());
            merchantRateBank.setChargeMin3(merchantRateNew.getChargeMin3());
        }
    }

    /**
     * 批量修改费率 不按卡类型
     */
    private void saveNotBankCardType(String checkedstr, MerchantRateNew merchantRateNew) throws UnsupportedEncodingException {
        //转换json
        String json = URLDecoder.decode(checkedstr,"utf-8");
        List<Map<String,String>> list = JsonMapperUtil.nonDefaultMapper().fromJson(json,List.class);
        List<String> emails = notificationEmailDao.queryList();
        String toEmail = listToString(emails, ',');
        List<MerchantRateBank> original = new ArrayList<>();
        List<MerchantRateBank> after = new ArrayList<>();
        //循环数据
        for(Map<String,String> map : list){
            MerchantRateBank merchantRateBank = new MerchantRateBank();
            merchantRateBank.setRateId(merchantRateNew.getRateId());
            String bankName = map.get("bankName");
            String bankType = map.get("bankType");
            //根据银行名称获取银行no
            if(StringUtils.isNotBlank(bankName)){
                BankInfo bankByBankName = bankInfoDao.getBankByBankName(bankName);
                merchantRateBank.setBankNo(bankByBankName.getBankNo());
                merchantRateBank.setBankName(bankName);
            }
            //获取银行卡类型
            if(StringUtils.isNotBlank(bankType)){
                String bankCardType = RateBankcardType.getLabel(bankType);
                merchantRateBank.setBankCardType(bankCardType);
            }
            //根据rate_id,bankNo,bankCardType获取merchantRateBankReturn
            MerchantRateBank merchantRateBankReturn = merchantRateBankCheckDao.getRateBank(merchantRateBank);
            //判断merchantRateBankReturn是否存在
            if(null != merchantRateBankReturn){//存在则修改
                original.add(merchantRateBankReturn);
                merchantRateBank.setId(merchantRateBankReturn.getId());
            }else{
                //查询配置的默认ALL费率
                MerchantRateBank merchantRateBankBefore = new MerchantRateBank();
                merchantRateBankBefore.setRateId(merchantRateBank.getRateId());
                merchantRateBankBefore.setBankCardType(merchantRateBank.getBankCardType());
                merchantRateBankBefore.setBankNo(Constants.MERCHANT_RATE_ALL_BANK);
                original.add(merchantRateBankDao.getRateBank(merchantRateBankBefore));
            }
            //存入费率数据
            changeRate(merchantRateNew, merchantRateBank);
            after.add(merchantRateBank);
            super.save(merchantRateBank,false);
        }
        // 根据rateId查询用户的id和产品名称
        MerchantProductRate merchantProductRate = merchantProductRateDao.get(merchantRateNew.getRateId());
        String table;
        if(original.isEmpty()){
            table = "";
        }else{
            table = BuildUpdateRateEmailContentOnBatch.getTable(original,after.get(0));
        }
        String content = "用户(id:name)"+ UserUtils.getUser().getId() +":"+ UserUtils.getUser().getName() +"在时间："
                + com.heepay.common.util.DateUtil.getTodayYYYYMMDD_HHMMSS() + "修改了商户ID为："+ merchantProductRate.getMerchantId() +
                "，产品《" + merchantProductRate.getProductName() +"》的费率；" +"</br>修改方式：批量修改费率-不按卡类型；" + table;
        boolean isSuccess = SendMailUtil.getInstance().sendMail(toEmail,environment + "费率修改通知",content,null);
        logger.info("批量修改费率 不按卡类型  发送邮件通知结果{}。发送内容为{}",isSuccess,content);

    }
    /**
     * 把list变成用 ,链接的字符串
     * @param list          list
     * @param separator     分隔符
     * @return
     */
    private String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        // 没配置发给ngp@9186.com
        if(list.isEmpty()){
            return "ngp@9186.com";
        }else{
            return sb.toString().substring(0, sb.toString().length() - 1);
        }
    }
}