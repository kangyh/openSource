package com.heepay.boss.service.rpc;

import com.heepay.boss.client.ManageSeviceClient;
import com.heepay.boss.client.RiskSeviceClient;
import com.heepay.boss.dao.ReportQueryConditionsMapper;
import com.heepay.boss.entity.ReportQueryConditions;
import com.heepay.boss.entity.ReportQueryConditionsExample;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.enums.BankcardType;
import com.heepay.rpc.boss.service.BossReportDateInfoService;
import com.heepay.rpc.service.RpcService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 描 述：
 * <p>
 * 创 建 者：wangdong
 * 创建时间：2017/6/1 15:29
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Component
@RpcService(name="BossReportDateInfoServiceImpl",processor=BossReportDateInfoService.Processor.class)
public class BossReportDateInfoServiceImpl implements com.heepay.rpc.boss.service.BossReportDateInfoService.Iface {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ReportQueryConditionsMapper reportQueryConditionsDaoImpl;

    @Autowired
    private RiskSeviceClient riskSeviceClient;

    @Autowired
    private ManageSeviceClient manageSeviceClient;

    /**
     * @方法说明：查询报表数据list
     * @时间： 2017/6/1 17:00
     * @创建人：wangdong
     */
    @Override
    public String getbossReportDateInfoList(String params) throws TException {
        try {
            Map<String, String> paramsMap = JsonMapperUtil.nonEmptyMapper().fromJson(params, Map.class);
            logger.info("查询BOSS数据List【查询条件】-->{}",params);
            List<ReportQueryConditions> list = getReportQueryConditions(paramsMap);

            //返回字段名称
            String fields = "transNo,merchantOrderNo,merchantId,merchantName,channelTransType,bankId,bankShortName,bankcardType,requestStatus,requestAmount,payAmount,payFinishTime,requestTime";
            if(null != paramsMap && paramsMap.size() > 0){
                //转化查询条件   单据编号
                if(StringUtils.isNotBlank(paramsMap.get("billNo"))){
                    paramsMap.put("transNo",paramsMap.get("billNo"));
                    paramsMap.remove("billNo");
                }
                //转化查询条件   商家内码
                if(StringUtils.isNotBlank(paramsMap.get("agentID"))){
                    paramsMap.put("merchantId",paramsMap.get("agentID"));
                    paramsMap.remove("agentID");
                }
                //转化查询条件    升序还是降序
                if(StringUtils.isNotBlank(paramsMap.get("isDesc"))){
                    if(StringUtils.equals(paramsMap.get("isDesc"),"true")){
                        paramsMap.put("isDesc","DESC");
                    }else{
                        paramsMap.put("isDesc","AEC");
                    }
                }
                joinBankIdJavaAndChannelPatnerJava(paramsMap, list);
                //查询参数
                String equalParam = JSONObject.fromObject(paramsMap).toString();
                logger.info("查询BOSS数据List【java转化之后的查询条件】-->{}",equalParam);
                //返回list
                List<Map<String,String>> infolist = riskSeviceClient.getbossReportDateInfoList(fields,equalParam);
                JSONArray json = new JSONArray();
                if(null != infolist && infolist.size() > 0){
                    for(Map<String,String> map : infolist){
                        JSONObject info = new JSONObject();
                        info.put("Bill_No", map.get("transNo"));
                        info.put("Agent_Bill_ID", map.get("merchantOrderNo"));
                        info.put("Agent_ID", map.get("merchantId"));
                        info.put("Agent_Account", "");
                        info.put("Agent_Company", map.get("merchantName"));
                        info.put("Pay_Type", manageSeviceClient.getChannelByChannelCodeList("ChannelType").get(map.get("channelTransType")));
                        info.put("Bank_Provider", "");
                        info.put("Bank_ID", map.get("bankId"));
                        info.put("Bank_Name", map.get("bankShortName"));
                        if(null != map.get("bankcardType") && StringUtils.isNotBlank(map.get("bankcardType").toString())){
                            if(StringUtils.equals(map.get("bankcardType").toString(), BankcardType.SAVINGCARD.getValue())){
                                info.put("Bank_Card_Type", "储蓄卡");
                            }else if(StringUtils.equals(map.get("bankcardType").toString(), BankcardType.CREDITCARD.getValue())){
                                info.put("Bank_Card_Type", "信用卡");
                            }else if(StringUtils.equals(map.get("bankcardType").toString(), BankcardType.UNKNOWNED.getValue())){
                                info.put("Bank_Card_Type", "未知");
                            }
                        }
                        info.put("Bank_Card_Owner_Type", "企业");
                        info.put("Bill_Status", "成功");
                        info.put("Trade_Amt", map.get("requestAmount"));
                        info.put("Real_Amt", map.get("payAmount"));
                        info.put("From_IP", "");
                        info.put("Deal_Time", stampToDate(map.get("payFinishTime")));
                        info.put("Create_Time", stampToDate(map.get("requestTime")));
                        logger.info("查询BOSS数据List【返回的数据FIND_SUCCESS】-->{}",info.toString());
                        json.add(info);
                    }
                }
                return json.toString();
            }
        }catch (Exception e){
            logger.error("查询BOSS数据【List】thrift，【FIND_ERROR】server异常：{}",e);
        }
        return "";
    }

    private void joinBankIdJavaAndChannelPatnerJava(Map<String, String> paramsMap, List<ReportQueryConditions> list) {
        if(null != list && list.size() > 0){
            //转化查询条件  多值的拼接
            StringBuffer bankIds = new StringBuffer("");
            StringBuffer channelTransTypes = new StringBuffer("");
            for(ReportQueryConditions reportQueryConditions : list){
                bankIds.append(",").append(reportQueryConditions.getBankIdJava());
                channelTransTypes.append(",").append(reportQueryConditions.getChannelPartnerJava());
            }
            if(StringUtils.isNotBlank(bankIds)){
                paramsMap.put("bankId",bankIds.toString().substring(1));
                paramsMap.remove("bankID");
            }
            if(StringUtils.isNotBlank(channelTransTypes)) {
                paramsMap.put("channelTransType", channelTransTypes.toString().substring(1));
                paramsMap.remove("payType");
            }
        }
    }

    /**
     * @方法说明：获取java条件
     * @时间： 2017/6/7 16:37
     * @创建人：wangdong
     */
    private List<ReportQueryConditions> getReportQueryConditions(Map<String, String> paramsMap) {
        ReportQueryConditionsExample example = new ReportQueryConditionsExample();
        example.setDistinct(true);
        if(StringUtils.isNotBlank(paramsMap.get("payType"))) {
            example.createCriteria().andPayTypeEqualTo(paramsMap.get("payType"));
        }
        if(StringUtils.isNotBlank(paramsMap.get("payTypeName"))) {
            example.createCriteria().andPayTypeNameEqualTo(paramsMap.get("payTypeName"));
        }
        if(StringUtils.isNotBlank(paramsMap.get("bankProvider"))) {
            example.createCriteria().andBankProviderEqualTo(paramsMap.get("bankProvider"));
        }
        if(StringUtils.isNotBlank(paramsMap.get("bankProviderName"))) {
            example.createCriteria().andBankProviderNameEqualTo(paramsMap.get("bankProviderName"));
        }
        if(StringUtils.isNotBlank(paramsMap.get("bankId"))) {
            example.createCriteria().andBankIdEqualTo(paramsMap.get("bankId"));
        }
        if(StringUtils.isNotBlank(paramsMap.get("bankName"))) {
            example.createCriteria().andBankNameEqualTo(paramsMap.get("bankName"));
        }
        if(StringUtils.isNotBlank(paramsMap.get("bankNameRe"))) {
            example.createCriteria().andBankNameReEqualTo(paramsMap.get("bankNameRe"));
        }
        example.createCriteria().andStatusEqualTo("Y");
        return reportQueryConditionsDaoImpl.selectByExample(example);
    }

    /**
     * @方法说明：查询数据详情
     * @时间： 2017/6/1 17:00
     * @创建人：wangdong
     */
    @Override
    public String getbossReportDateInfoDetail(String params) throws TException {
        try {
            Map<String, String> paramsMap = JsonMapperUtil.nonEmptyMapper().fromJson(params, Map.class);
            //返回字段名称
            String fields = "transNo,merchantId,merchantName,channelTransType,bankId,bankShortName,bankcardType,requestStatus,requestAmount,payAmount,productDescription,provinceId,payFinishTime,settleAmount,requestTime";
            //查询参数
            logger.info("查询BOSS数据详情-->{}",paramsMap.get("billNo"));
            String infoDetail = riskSeviceClient.getbossReportDateInfoDetail(fields,paramsMap.get("billNo"));
            logger.info("查询数据详情【返回的数据FIND_SUCCESS】-->{}",infoDetail.toString());
            JSONObject info = new JSONObject();
            JSONObject ret_info = new JSONObject();
            if(StringUtils.isNotBlank(infoDetail)){
                Map<String, String> retMap = JsonMapperUtil.nonEmptyMapper().fromJson(infoDetail, Map.class);
                ret_info.put("Exist",1);
                ret_info.put("Msg","获取成功");
                info.put("Bill_No", retMap.get("transNo"));                   // 单据编号
                info.put("Agent_ID", retMap.get("merchantOrderNo"));          // 商家客户内码
                info.put("Agent_Account", "");    // 商户账号
                info.put("Agent_Company", retMap.get("merchantName"));    // 商户公司
                info.put("Agent_Bill_ID", "");    // 商户提供的定单号|每个商户必须唯一
                info.put("Agent_Bill_Time", "");  // 商户定单时间
                info.put("Ref_Agent_ID", "");     // 关联的商户ID|主要用于Agent_ID小于100以内的，对应真实的商户ID
                info.put("Pay_Type", manageSeviceClient.getChannelByChannelCodeList("ChannelType").get(retMap.get("channelTransType")));         // 支付类型
                info.put("Bank_Provider", "");    // 银行支付提供者
                info.put("Bank_ID", retMap.get("bankId"));          // 银行ID
                info.put("Bank_Name", retMap.get("bankShortName"));        // 银行名称
                // 银行卡类型|-1=未知,0=储蓄卡(借记卡),1=信用卡(贷记卡)
                if(null != retMap.get("bankcardType") && StringUtils.isNotBlank(retMap.get("bankcardType").toString())){
                    if(StringUtils.equals(retMap.get("bankcardType").toString(), BankcardType.SAVINGCARD.getValue())){
                        info.put("Bank_Card_Type", "储蓄卡");
                    }else if(StringUtils.equals(retMap.get("bankcardType").toString(), BankcardType.CREDITCARD.getValue())){
                        info.put("Bank_Card_Type", "信用卡");
                    }else if(StringUtils.equals(retMap.get("bankcardType").toString(), BankcardType.UNKNOWNED.getValue())){
                        info.put("Bank_Card_Type", "未知");
                    }
                }
                info.put("Bank_Card_Owner_Type", "企业");  // 银行卡所有者类型|-1=未知,0=个人,1=企业
                info.put("Trade_Amt", retMap.get("requestAmount"));        // 提交支付金额
                info.put("Real_Amt", retMap.get("payAmount"));         // 实际支付金额|一般情况=Trade_Amt,如果有活动什么，可以折扣等
                info.put("Goods_Name", "");       // 支付产品信息
                info.put("Goods_Num", "");        // 支付产品数量
                info.put("Goods_Note", retMap.get("productDescription"));       // 支付产品具体描述
                info.put("From_IP", "");          // FromIP
                info.put("From_Province_ID", retMap.get("provinceId")); // IP来源的省ID
                info.put("User_From_IP", "");     // 商户传递过来的用户IP
                info.put("Note", "");             // 说明
                info.put("Deal_Mode", "");        // 处理方式|0=自动,1=后台手动,2=前台手动
                info.put("Deal_User_ID", "");     // 处理人内码
                info.put("Deal_User_Name", "");   // 处理人
                info.put("Deal_From_IP", "");     // 处理IP
                info.put("Deal_Note", "");        // 处理说明
                info.put("Deal_Time", stampToDate(retMap.get("payFinishTime")));        // 处理时间
                info.put("Bill_Status", "成功");      // 单据状态|-1=无效,0=未处理,1=成功
                info.put("Settle_Amt", retMap.get("settleAmount"));       // 结算金额
                info.put("Settle_Status", "结算完成");    // 结算过程状态|0=未开始结算,1=开始结算中,2=结算完成
                info.put("Settle_Time", "");      // 结算时间
                info.put("Modify_User", "");      // 修改人
                info.put("Modify_Time", "");      // 修改时间
                info.put("Create_User", "");      // 创建人
                info.put("Create_Time", "");      // 创建时间
                ret_info.put("Info",info);
            }else{
                ret_info.put("Exist",0);
                ret_info.put("Msg","单据不存在");
            }
            return ret_info.toString();
        }catch (Exception e){
            logger.error("查询BOSS数据【详情FIND_ERROR】thrift，server异常：{}",e);
        }
        return "";
    }

    /**
     * @方法说明：查询数据统计
     * @时间： 2017/6/1 17:00
     * @创建人：wangdong
     */
    @Override
    public String getbossReportDateInfoTotal(String params) throws TException {
        try {
            Map<String, String> paramsMap = JsonMapperUtil.nonEmptyMapper().fromJson(params, Map.class);
            List<ReportQueryConditions> list = getReportQueryConditions(paramsMap);
            if(null != paramsMap && paramsMap.size() > 0) {

                paramsMap.put("beginTime", String.valueOf(dateToTimeInMillis(paramsMap.get("beginTime")+" 00:00:00")));
                paramsMap.put("endTime",String.valueOf(dateToTimeInMillis(paramsMap.get("endTime")+" 23:59:59")));
                //查询参数
                String groupField = "channelCode,channelTransType,transType,merchantId,bankCode,bankcardType,day";
                String equalParam = JSONObject.fromObject(paramsMap).toString();
                logger.info("查询数据统计参数-->{},分组字段-->{}", equalParam,groupField);
                String infoTotal = riskSeviceClient.getbossReportDateInfoTotal(equalParam,groupField);
                logger.info("查询数据统计，返回参数【FIND_SUCCESS】--->{}", infoTotal.toString());
                JSONArray json = new JSONArray();
                if (StringUtils.isNotBlank(infoTotal)) {
                    JSONArray array = JSONArray.fromObject(infoTotal);
                    for(int i = 0; i < array.size(); i++){
                        String info = array.getJSONObject(i).toString();

                        Map<String, String> infoMap = JsonMapperUtil.nonEmptyMapper().fromJson(info, Map.class);

                        if(StringUtils.isNotBlank(infoMap.get("transType"))){
                            infoMap.put("transType", infoMap.get("transType").toUpperCase());
                        }
                        if(StringUtils.isNotBlank(infoMap.get("channelTransType"))){
                            infoMap.put("channelTransType", infoMap.get("channelTransType").toUpperCase());
                        }
                        if(StringUtils.isNotBlank(infoMap.get("channelCode"))){
                            infoMap.put("channelCode", infoMap.get("channelCode").toUpperCase());
                        }

                        if(StringUtils.isNotBlank(infoMap.get("bankcardType"))){
                            if(StringUtils.equals(infoMap.get("bankcardType").toString().toUpperCase(), BankcardType.SAVINGCARD.getValue())){
                                infoMap.put("bankcardType", "储蓄卡|0");
                            }else if(StringUtils.equals(infoMap.get("bankcardType").toString().toUpperCase(), BankcardType.CREDITCARD.getValue())){
                                infoMap.put("bankcardType", "信用卡|1");
                            }else if(StringUtils.equals(infoMap.get("bankcardType").toString().toUpperCase(), BankcardType.UNKNOWNED.getValue())){
                                infoMap.put("bankcardType", "未知|-1");
                            }
                        }
                        json.add(JSONObject.fromObject(infoMap));
                    }
                }
                return json.toString();
            }
        }catch (Exception e){
            logger.error("查询BOSS数据【统计FIND_ERROR】thrift，server异常：{}",e);
        }
        return "";
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 方法说明：将时间字符串转化为毫秒值
     * @时间： 2017/6/13 18:10
     * @创建人：wangdong
     */
    public static long dateToTimeInMillis(String dateTime) throws ParseException {
        return DateUtils.getStrDate(dateTime,"yyyy-MM-dd HH:mm:ss").getTime();
    }
}
