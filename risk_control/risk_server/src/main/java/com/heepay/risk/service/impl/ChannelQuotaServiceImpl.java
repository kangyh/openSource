package com.heepay.risk.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.engine.entity.ChannelRiskFact;
import com.heepay.engine.impl.DroolsTemplateRuleEngineService;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.risk.RiskChannelReqResult;
import com.heepay.enums.risk.RiskChannelReqType;
import com.heepay.enums.risk.RuleDetailType;
import com.heepay.risk.RiskChannelLogVo;
import com.heepay.risk.dao.RiskChannelMapper;
import com.heepay.risk.dao.RiskControlLimitAmountMapper;
import com.heepay.risk.dao.RiskOrderMapper;
import com.heepay.risk.dao.RiskRuleDetailMapper;
import com.heepay.risk.entity.RiskChannel;
import com.heepay.risk.entity.RiskRuleDetail;
import com.heepay.risk.factory.ChannelRiskFactFactory;
import com.heepay.risk.vo.ChannelQuotaResultVo;
import com.heepay.risk.vo.ChannelRateVo;
import com.heepay.rpc.risk.model.AsyncMsgVO;
import com.heepay.rpc.risk.service.ChannelRiskService;
import com.heepay.rpc.risk.service.TransOrderRiskService;
import com.heepay.rpc.service.RpcService;

import net.sf.json.JSONObject;

/**
 *
 *
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年5月27日 上午11:55:19
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
@Component
@RpcService(name="ChannelQuotaServiceImpl",processor=ChannelRiskService.Processor.class)
public class ChannelQuotaServiceImpl implements com.heepay.rpc.risk.service.ChannelRiskService.Iface {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private DroolsTemplateRuleEngineService droolsTemplateRuleEngineService;
    @Autowired
    private RiskChannelMapper riskChannelMapper;
    @Autowired
    private RiskRuleDetailMapper riskRuleDetailMapper;
    @Autowired
    private RiskControlLimitAmountMapper riskControlLimitAmountMapper;
    @Override
    /**
     * 获取通道限额
     */
    public String getChannelQuotaList(String arg0) throws TException {
        logger.info("通道限额参数信息:--->"+arg0);
        HashMap page=new HashMap();
        page.put("pagefrom", "0");
        page.put("pagesize", "1");
        ChannelRateVo vo=new ChannelRateVo();
        @SuppressWarnings("rawtypes")
        Map map=JsonMapperUtil.nonDefaultMapper().fromJson(arg0,Map.class);
        int pageIndex=Integer.parseInt(map.get("pageIndex").toString());
        int pageSize=Integer.parseInt(map.get("pageSize").toString());
        map.remove("pageIndex");
        map.remove("pageSize");
        logger.info("参数信息:--->"+JsonMapperUtil.nonDefaultMapper().toJson(map));
        JSONObject json=new JSONObject();
        json.fromObject(map);
        Map  result=riskControlLimitAmountMapper.getChannelLimitAmountInfo(JsonMapperUtil.nonDefaultMapper().toJson(map));
        int totalSize=0;
        Map<String,Object> resultMap=new HashMap<String,Object>();
        List<Map> tempList=new ArrayList<Map>();
        if(result!=null)
        {
            if(map.get("channel_code")==null) {
                List<Map> dayMap = (List<Map>) result.get("dayList");
                List<Map> monthMap = (List<Map>) result.get("monthList");
                this.mergeMap(monthMap, dayMap);
                logger.info("结果信息:--->"+JsonMapperUtil.nonDefaultMapper().toJson(result));
                totalSize = monthMap.size();
                int begin = pageIndex * pageSize;
                int end = totalSize - (pageIndex + 1) * pageSize >= 0 ? (pageIndex + 1) * pageSize : begin + totalSize % pageSize;
                for (int i = begin; i < end; i++) {
                    Map<String, String> tempMap = monthMap.get(i);
                    HashMap channelMap = new HashMap();
                    channelMap.put("channel_code", tempMap.get("channel_code"));
                    List<Map> list = riskControlLimitAmountMapper.getChannelList(channelMap, null, page);
                    Map newMap = ConvertMap(list.get(0));
                    newMap.put("dayAmount", tempMap.get("day") == null ? "0" : String.valueOf(tempMap.get("day")));
                    newMap.put("monthAmount", String.valueOf(tempMap.get("month")));
                    tempList.add(newMap);
                }
            }
            else if((result.get("day")!=null&&((BigDecimal)result.get("day")).compareTo(BigDecimal.ZERO)==1)||(result.get("month")!=null&&((BigDecimal)result.get("month")).compareTo(BigDecimal.ZERO)==1)){
                String dayMap = String.valueOf(result.get("day"));
                String monthMap = String.valueOf(result.get("month"));

                logger.info("结果信息:--->"+JsonMapperUtil.nonDefaultMapper().toJson(result));
                totalSize = 1;

                HashMap channelMap = new HashMap();
                channelMap.put("channel_code", map.get("channel_code"));
                List<Map> list = riskControlLimitAmountMapper.getChannelList(channelMap, null, page);
                Map newMap = ConvertMap(list.get(0));
                newMap.put("dayAmount", dayMap);
                newMap.put("monthAmount", monthMap);
                tempList.add(newMap);
            }
        }
        resultMap.put("totalSize", String.valueOf(totalSize));
        resultMap.put("resultList", tempList);
        return JsonMapperUtil.nonDefaultMapper().toJson(resultMap);
    }
    private Map ConvertMap(Map map)
    {
        Map newMap=new HashMap();
        newMap.put("paymentId", map.get("payment_id"));
        newMap.put("channelCode", map.get("channel_code"));
        newMap.put("channelPartnerCode", map.get("channel_partner_code"));
        newMap.put("channelPartnerName", map.get("channel_partner_name"));
        newMap.put("channelTypeCode", map.get("channel_type_code"));
        newMap.put("channelTypeName", map.get("channel_type_name"));
        newMap.put("bankNo", map.get("bank_no"));
        newMap.put("bankName", map.get("bank_name"));
        newMap.put("accountType",map.get("account_type"));
        newMap.put("businessType", map.get("business_type"));
        newMap.put("Amount", map.get("amount"));
        newMap.put("requestStatus", map.get("request_status"));
        newMap.put("Result", map.get("result"));
        newMap.put("Url",map.get("url"));
        newMap.put("cardTypeCode", map.get("card_type_code"));
        newMap.put("cardTypeName", map.get("card_type_name"));
        newMap.put("reqTime", map.get("req_time"));
        newMap.put("respTime", map.get("resp_time"));
        newMap.put("daylimitAmount", map.get("day_limit_amount")==null?"0":String.valueOf(map.get("day_limit_amount")));
        newMap.put("monthlimitAmount", map.get("month_limit_amount")==null?"0":String.valueOf(map.get("month_limit_amount")));
        return newMap;
    }
    private List<Map> mergeMap(List<Map> map1,List<Map> map2)
    {
        for(int i=0;i<map1.size();i++)
        {
            Map tempMap= map1.get(i);
            for(int j=0;j<map2.size();j++)
            {
                Map tempMap2= map2.get(j);
                if(tempMap2.get("channel_code").toString().equals(tempMap.get("channel_code").toString()))
                    tempMap.put("day", tempMap2.get("day"));
            }
        }
        return map1;
    }
    /**
     * 获取通道列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public String getChannelList(String arg0) throws TException {
        logger.info("获取通道列表参数信息:---> {}",arg0);
        @SuppressWarnings("rawtypes")
        Map map=JsonMapperUtil.nonDefaultMapper().fromJson(arg0,Map.class);

        @SuppressWarnings("rawtypes")
        HashMap range=new HashMap();
        HashMap resultMap=new HashMap();
        range.put("field", "req_time");
        if(map.get("begin")!=null)
        {
            range.put("begin", String.valueOf(DateUtil.stringToDate(map.get("begin").toString(),"yyyyMMddHHmmssSSS").getTime()));
        }
        else
            range.put("begin",map.get("begin"));

        if(map.get("end")!=null)
        {
            range.put("end", String.valueOf(DateUtil.stringToDate(map.get("end").toString(),"yyyyMMddHHmmssSSS").getTime()));
        }
        else
            range.put("end",map.get("end"));
        String pageindex= map.get("pageIndex").toString();
        String pagesize= map.get("pageSize").toString();
        @SuppressWarnings("rawtypes")
        HashMap page=new HashMap();
        page.put("pagefrom", Integer.parseInt(pageindex)*Integer.parseInt(pagesize));
        page.put("pagesize", pagesize);
        map.remove("begin");
        map.remove("end");
        map.remove("pageIndex");
        map.remove("pageSize");
        logger.info("获取时间参数信息:--->"+JsonMapperUtil.buildNonDefaultMapper().toJson(range));
        List<Map> list=riskControlLimitAmountMapper.getChannelList(map,range,page);
        List<RiskChannelLogVo> resultList=new ArrayList<RiskChannelLogVo>();
        if(list!=null)
        {
            //logger.info("获取通道列表信息:---> {}",JsonMapperUtil.buildNonDefaultMapper().toJson(list));
            for(Map tempMap:list)
            {
                RiskChannelLogVo riskChannelLogVo=new RiskChannelLogVo();
                riskChannelLogVo.setAccountType(tempMap.get("account_type")==null?null:tempMap.get("account_type").toString());
                riskChannelLogVo.setAmount(tempMap.get("amount")==null?null:tempMap.get("amount").toString());
                riskChannelLogVo.setBankName(tempMap.get("bank_name")==null?null:tempMap.get("bank_name").toString());
                riskChannelLogVo.setBankNo(tempMap.get("bank_no")==null?null:tempMap.get("bank_no").toString());
                riskChannelLogVo.setBusinessType(tempMap.get("business_type")==null?null:tempMap.get("business_type").toString());
                riskChannelLogVo.setCardTypeCode(tempMap.get("card_type_code")==null?null:tempMap.get("card_type_code").toString());
                riskChannelLogVo.setCardTypeName(tempMap.get("card_type_name")==null?null:tempMap.get("card_type_name").toString());
                riskChannelLogVo.setChannelCode(tempMap.get("channel_code")==null?null:tempMap.get("channel_code").toString());
                riskChannelLogVo.setChannelPartnerCode(tempMap.get("channel_partner_code")==null?null:tempMap.get("channel_partner_code").toString());
                riskChannelLogVo.setChannelPartnerName(tempMap.get("channel_partner_name")==null?null:tempMap.get("channel_partner_name").toString());
                riskChannelLogVo.setChannelTypeCode(tempMap.get("channel_type_code")==null?null:tempMap.get("channel_type_code").toString());
                riskChannelLogVo.setChannelTypeName(tempMap.get("channel_type_name")==null?null:tempMap.get("channel_type_name").toString());
                riskChannelLogVo.setPaymentId(tempMap.get("payment_id")==null?null:tempMap.get("payment_id").toString());
                riskChannelLogVo.setReqTime(tempMap.get("req_time")==null?null:tempMap.get("req_time").toString());
                riskChannelLogVo.setRequestStatus(tempMap.get("request_status")==null?null:tempMap.get("request_status").toString());
                riskChannelLogVo.setRespTime(tempMap.get("resp_time")==null?null:tempMap.get("resp_time").toString());
                riskChannelLogVo.setResult(tempMap.get("result")==null?null:tempMap.get("result").toString());
                riskChannelLogVo.setUrl(tempMap.get("url")==null?null:tempMap.get("url").toString());
                riskChannelLogVo.setDaylimitAmount(tempMap.get("day_limit_amount")==null?null:tempMap.get("day_limit_amount").toString());
                riskChannelLogVo.setMonlimitAmount(tempMap.get("month_limit_amount")==null?null:tempMap.get("month_limit_amount").toString());
                resultList.add(riskChannelLogVo);
            }
        }
        resultMap.put("totalSize", page.get("totalsize")==null?"0":page.get("totalsize"));
        resultMap.put("resultList", resultList);
        return JsonMapperUtil.nonDefaultMapper().toJson(resultMap);

    }
    private List<Map<String,String>> getDistinctMap( List<Map<String,String>> list)
    {
        List<Map<String,String>> newList=new ArrayList<Map<String,String>>();
        for(Map<String,String> map:list)
        {   Boolean b=false;
            for(Map<String,String> map1:newList)
            {
                if(map.get("channel_code").equals(map1.get("channel_code")))
                    b=true;
            }
            if(!b&&(map.get("request_status").equals(RiskChannelReqType.ENCRYPT.getValue().toLowerCase())
                    ||map.get("request_status").equals(RiskChannelReqType.PAY.getValue().toLowerCase())
                    ||map.get("request_status").equals(RiskChannelReqType.DECRYPT_ASYNC.getValue().toLowerCase())))
            {
                Map<String,String> newMap=new HashMap<String,String>();
                newMap.put("channel_code",map.get("channel_code"));
                newMap.put("successNum","0");
                newMap.put("failNum","0");
                newList.add(newMap);
            }
        }
        return newList;
    }
    private Map<String,String> getMapByChannelCode(List<Map<String,String>> list,String channelCode)
    {
        for(Map<String,String> map:list)
        {
            if(map.get("channel_code").equals(channelCode))
                return map;
        }
        return null;
    }
    private List<Map<String,String>> ConvertNewRateMap( List<Map<String,String>> list)
    {
        //logger.info("test2："+JsonMapperUtil.buildNonDefaultMapper().toJson(list));
        List<Map<String,String>> newList =getDistinctMap(list);
        //logger.info("test3："+JsonMapperUtil.buildNonDefaultMapper().toJson(newList));
        for(Map<String,String> map:list)
        {
           // Long success=0L,fail=0L;
            Map<String,String> newMap=getMapByChannelCode(newList,map.get("channel_code"));
            //logger.info("test4："+JsonMapperUtil.buildNonDefaultMapper().toJson(newMap));
            if(newMap!=null) {
                Long success=Long.parseLong(newMap.get("successNum"));
                Long fail=Long.parseLong(newMap.get("failNum"));
                if (map.get("request_status").toString().equals(RiskChannelReqType.PAY.getValue().toLowerCase()) && map.get("result").toString().equals(RiskChannelReqResult.RESULT_SUCCESS.getValue().toLowerCase()))
                    success = Long.parseLong(newMap.get("successNum")) + Long.parseLong(map.get("count").toString());

                if (map.get("request_status").toString().equals(RiskChannelReqType.DECRYPT_ASYNC.getValue().toLowerCase())) {
                    success = Long.parseLong(newMap.get("successNum")) + Long.parseLong(map.get("count").toString());
                }
                if (map.get("request_status").toString().equals(RiskChannelReqType.QUOTA.getValue().toLowerCase())) {
                    success = Long.parseLong(newMap.get("successNum")) + Long.parseLong(map.get("count").toString());
                }
                if (map.get("request_status").toString().equals(RiskChannelReqType.ENCRYPT.getValue().toLowerCase()))
                    fail = Long.parseLong(newMap.get("failNum")) + Long.parseLong(map.get("count").toString());
                if (map.get("request_status").toString().equals(RiskChannelReqType.PAY.getValue().toLowerCase()) && map.get("result").toString().equals(RiskChannelReqResult.RESULT_FAILURE.getValue().toLowerCase()))
                    fail = Long.parseLong(newMap.get("failNum")) + Long.parseLong(map.get("count").toString());
                newMap.put("successNum", String.valueOf(success));
                newMap.put("failNum", String.valueOf(fail));
                //logger.info("test5："+JsonMapperUtil.buildNonDefaultMapper().toJson(newMap));
            }

        }
        return newList;
    }
    private List<Map<String,String>> initial()
    {
        List<Map<String,String>> list=new ArrayList<Map<String,String>>();
        Map map1=new HashMap<String,String>();
        map1.put("channel_code","708UNOPAY_10481");
        map1.put("result","success");
        map1.put("request_status","encrypt");
        map1.put("count","10107");
        Map map2=new HashMap<String,String>();
        map2.put("channel_code","708UNOPAY_10481");
        map2.put("result","success");
        map2.put("request_status","decrypt_async");
        map2.put("count","2470");
        Map map3=new HashMap<String,String>();
        map3.put("channel_code","706HEEPAY_10519");
        map3.put("result","success");
        map3.put("request_status","encrypt");
        map3.put("count","6789");
        Map map4=new HashMap<String,String>();
        map4.put("channel_code","706HEEPAY_10519");
        map4.put("result","success");
        map4.put("request_status","decrypt_async");
        map4.put("count","1545");
        Map map5=new HashMap<String,String>();
        map5.put("channel_code","102DIRCON_10075");
        map5.put("result","success");
        map5.put("request_status","encrypt");
        map5.put("count","2626");
        Map map6=new HashMap<String,String>();
        map6.put("channel_code","102DIRCON_10075");
        map6.put("result","success");
        map6.put("request_status","decrypt_async");
        map6.put("count","646");

        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);
        return list;
    }
    private List<Map<String,String>> initial2()
    {
        List<Map<String,String>> list=new ArrayList<Map<String,String>>();
        Map map1=new HashMap<String,String>();
        map1.put("channel_code","708UNOPAY_10481");
        map1.put("result","success");
        map1.put("request_status","encrypt");
        map1.put("count","10107");
        Map map2=new HashMap<String,String>();
        map2.put("channel_code","708UNOPAY_10481");
        map2.put("result","success");
        map2.put("request_status","decrypt_async");
        map2.put("count","2470");
        Map map3=new HashMap<String,String>();
        map3.put("channel_code","706HEEPAY_10519");
        map3.put("result","success");
        map3.put("request_status","encrypt");
        map3.put("count","6789");
        Map map4=new HashMap<String,String>();
        map4.put("channel_code","706HEEPAY_10519");
        map4.put("result","success");
        map4.put("request_status","decrypt_async");
        map4.put("count","1545");
        Map map5=new HashMap<String,String>();
        map5.put("channel_code","104BILPAY_10209");
        map5.put("result","success");
        map5.put("request_status","encrypt");
        map5.put("count","2626");
        Map map6=new HashMap<String,String>();
        map6.put("channel_code","104BILPAY_10209");
        map6.put("result","success");
        map6.put("request_status","decrypt_async");
        map6.put("count","646");

        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);
        return list;
    }
    /**
     * 获取最近15分钟和1小时通道成功率
     */
    private  List<Map<String,String>> getCompareChannelSuccessRate(List<Map<String,String>> list1,List<Map<String,String>> list2)
    {
        List<Map<String,String>> list=new ArrayList<Map<String,String>>();
        if(list1!=null&&list1.size()>0)
        {
             for(Map map:list1)
             {
                 Map tempMap=new HashMap();
                 tempMap.put("channel_code",map.get("channel_code"));
                 Long successNum=Long.parseLong(map.get("successNum").toString());
                 Long failNum=Long.parseLong(map.get("failNum").toString());
                 tempMap.put("successNum",map.get("successNum").toString());
                 tempMap.put("failNum",map.get("failNum").toString());
                 tempMap.put("quarterRate",String.valueOf(this.getSuccessRate(Float.parseFloat(String.valueOf(successNum)),Float.parseFloat(String.valueOf(successNum+failNum)))));
                 tempMap.put("dayRate","0");
                 list.add(tempMap);
             }
        }
        if(list2!=null&&list2.size()>0)
        {
            for(Map map:list2)
            {
                Optional<Map<String,String>> map2=list.stream().filter(p->p.get("channel_code").equals(map.get("channel_code"))).findFirst();
                if(!map2.isPresent()) {
                    Map tempMap = new HashMap();
                    tempMap.put("channel_code", map.get("channel_code"));
                    Long successNum = Long.parseLong(map.get("successNum").toString());
                    Long failNum = Long.parseLong(map.get("failNum").toString());
                    tempMap.put("successNum",map.get("successNum").toString());
                    tempMap.put("failNum",map.get("failNum").toString());
                    tempMap.put("dayRate", String.valueOf(this.getSuccessRate(Float.parseFloat(String.valueOf(successNum)), Float.parseFloat(String.valueOf(successNum + failNum)))));
                    tempMap.put("quarterRate","0");
                    list.add(tempMap);
                }
                else
                {
                    Long successNum = Long.parseLong(map.get("successNum").toString());
                    Long failNum = Long.parseLong(map.get("failNum").toString());
                    map2.get().put("dayRate", String.valueOf(this.getSuccessRate(Float.parseFloat(String.valueOf(successNum)), Float.parseFloat(String.valueOf(successNum + failNum)))));
                }
            }
        }
        return list;
    }
    /**
     * 计算成功率
     * @param success 成功订单
     * @param total 总订单
     * @return 获取订单成功率
     */
    private int getSuccessRate(Float success,Float total)
    {
        if(total==0)
            return 100;
        if(success==0)
            return 0;
        float d = success / total;
        float b = (float) (Math.round(d * 100)) / 100;
        return new Float(b * 100L).intValue();
    }

    /**
     * 获取通道成功率
     */
    @Override
    public String getChannelRate(String arg0) throws TException {
        logger.info("获取通道成功率参数信息:--->"+arg0);
        HashMap page=new HashMap();
        HashMap rangeMap=new HashMap();
        HashMap rangeMap2=new HashMap();
        page.put("pagefrom", "0");
        page.put("pagesize", "1");
        rangeMap.put("field","req_time");
        rangeMap.put("begin",String.valueOf(LocalDateTime.now().minusMinutes(15).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        rangeMap.put("end",String.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        rangeMap2.put("field","req_time");
        rangeMap2.put("begin",String.valueOf(LocalDateTime.now().minusMinutes(60).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        rangeMap2.put("end",String.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        ChannelRateVo vo=new ChannelRateVo();
        @SuppressWarnings("rawtypes")
        Map map=JsonMapperUtil.nonDefaultMapper().fromJson(arg0,Map.class);
        int pageIndex=Integer.parseInt(map.get("pageIndex").toString());
        int pageSize=Integer.parseInt(map.get("pageSize").toString());
        map.remove("pageIndex");
        map.remove("pageSize");
        logger.info("参数信息:--->"+JsonMapperUtil.nonDefaultMapper().toJson(map));
        logger.info("最近15分钟参数信息:--->"+JsonMapperUtil.nonDefaultMapper().toJson(rangeMap));
        logger.info("最近1小时参数信息:--->"+JsonMapperUtil.nonDefaultMapper().toJson(rangeMap2));
        List<Map<String,String>>  result1=riskControlLimitAmountMapper.getChannelAggregationInfo(map, rangeMap);
        //List<Map<String,String>>  result1=this.initial();
        List<Map<String,String>>  result2=riskControlLimitAmountMapper.getChannelAggregationInfo(map, rangeMap2);
        //List<Map<String,String>>  result2=this.initial2();
        List<Map<String,String>> result3=ConvertNewRateMap(result1);
        List<Map<String,String>> result4=ConvertNewRateMap(result2);
        List<Map<String,String>> result=this.getCompareChannelSuccessRate(result3,result4);
        logger.info("result:{}",JsonMapperUtil.nonDefaultMapper().toJson(result));
        Map<String,Object> resultMap=new HashMap<String,Object>();
        List<Map> tempList=new ArrayList<Map>();
        int totalSize=0;
        if(result!=null)
        {
            long success=0L,fail=0L;
            totalSize=result.size();
            int begin=pageIndex*pageSize;
            int end=totalSize-(pageIndex+1)*pageSize>=0?(pageIndex+1)*pageSize:begin+totalSize%pageSize;
            for(int i=begin;i< end;i++)
            {

                Map<String,String> tempMap=result.get(i);

                HashMap channelMap=new HashMap();
                channelMap.put("channel_code",tempMap.get("channel_code"));
                List<Map> list=riskControlLimitAmountMapper.getChannelList(channelMap, null, page);
                Map newMap=ConvertMap(list.get(0));
                newMap.put("successNum", Long.parseLong(tempMap.get("successNum")));
                newMap.put("failNum", Long.parseLong(tempMap.get("failNum")));
                newMap.put("dayRate",tempMap.get("dayRate"));
                newMap.put("quarterRate",tempMap.get("quarterRate"));
                tempList.add(newMap);
            }
        }
        resultMap.put("totalSize", String.valueOf(totalSize));
        resultMap.put("resultList", tempList);
        return JsonMapperUtil.nonDefaultMapper().toJson(resultMap);
    }
    private  RiskChannelLogVo mapToVo(Map tempMap)
    {
        RiskChannelLogVo riskChannelLogVo=new RiskChannelLogVo();
        riskChannelLogVo.setAccountType(tempMap.get("account_type").toString());
        riskChannelLogVo.setAmount(tempMap.get("amount").toString());
        riskChannelLogVo.setBankName(tempMap.get("bank_name").toString());
        riskChannelLogVo.setBankNo(tempMap.get("bank_no").toString());
        riskChannelLogVo.setBusinessType(tempMap.get("business_type").toString());
        riskChannelLogVo.setCardTypeCode(tempMap.get("card_type_code").toString());
        riskChannelLogVo.setCardTypeName(tempMap.get("card_type_name").toString());
        riskChannelLogVo.setChannelCode(tempMap.get("channel_code").toString());
        riskChannelLogVo.setChannelPartnerCode(tempMap.get("channel_partner_code").toString());
        riskChannelLogVo.setChannelPartnerName(tempMap.get("channel_partner_name").toString());
        riskChannelLogVo.setChannelTypeCode(tempMap.get("channel_type_code").toString());
        riskChannelLogVo.setChannelTypeName(tempMap.get("channel_type_name").toString());
        riskChannelLogVo.setPaymentId(tempMap.get("payment_id").toString());
        riskChannelLogVo.setReqTime(tempMap.get("req_time").toString());
        riskChannelLogVo.setRequestStatus(tempMap.get("request_status").toString());
        riskChannelLogVo.setRespTime(tempMap.get("resp_time").toString());
        riskChannelLogVo.setResult(tempMap.get("result").toString());
        riskChannelLogVo.setUrl(tempMap.get("url").toString());
        return riskChannelLogVo;
    }
}

 