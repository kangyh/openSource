package com.heepay.risk.service.impl;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.enums.ProductType;
import com.heepay.enums.risk.RiskChannelReqResult;
import com.heepay.enums.risk.RiskChannelReqType;
import com.heepay.redis.JedisClusterUtil;
import com.heepay.risk.cache.RuleRedisCache;
import com.heepay.risk.dao.RiskChannelOrderConversionRatioMapper;
import com.heepay.risk.dao.RiskControlLimitAmountMapper;
import com.heepay.risk.dao.RiskMerchantOrderConversionRatioMapper;
import com.heepay.risk.dao.RiskWarningMapper;
import com.heepay.risk.entity.RiskChannelOrderConversionRatio;
import com.heepay.risk.entity.RiskMerchantOrderConversionRatio;
import com.heepay.risk.enums.DateQueryType;
import com.heepay.risk.enums.WarningLevel;
import com.heepay.risk.vo.RiskMonitorChannelVo;
import com.heepay.risk.vo.RiskMonitorMerchantVo;
import com.heepay.rpc.risk.service.MonitorService;
import com.heepay.rpc.service.RpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 描    述：生产监控系统
 * <p>
 * 创 建 者：dongzhengjiang E-mail:dongzj@9186.com
 * 创建时间： 2017-08-10 14:10
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
@RpcService(name="MonitorServiceImpl",processor=MonitorService.Processor.class)
public class MonitorServiceImpl implements com.heepay.rpc.risk.service.MonitorService.Iface  {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private RiskControlLimitAmountMapper riskControlLimitAmountMapper;
    @Autowired
    private RiskWarningMapper riskWarningMapper;
    @Autowired
    private RiskMerchantOrderConversionRatioMapper riskMerchantOrderConversionRatioMapper;
    @Autowired
    private RiskChannelOrderConversionRatioMapper riskChannelOrderConversionRatioMapper;
    /**
     * 外部正式商户无订单报警
     * @return
     * @throws TException
     */
    @Override
    public String getNoOrderWarning() throws TException {
        Map resultMap=new HashMap();
        resultMap.put("status",0);
        Map map=new HashMap();
        if(!isDateRange("6:30","21:30")) {
            resultMap.put("status", 0);

        }
        else {

            map.put("beginTime", LocalDateTime.now().minusMinutes(5).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
            map.put("endTime",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
            List<Map> list3 = riskWarningMapper.queryMerMoniOrderCount(JsonMapperUtil.buildNonDefaultMapper().toJson(map));
            if (list3 == null || list3.size() == 0) {
                resultMap.put("status", 1);
            }
        }

        return JsonMapperUtil.buildNonDefaultMapper().toJson(resultMap);
    }
    private boolean isDateRange(String beginTime,String endTime)
    {
        if(StringUtil.isBlank(beginTime)||StringUtil.isBlank(endTime))
            return true;
        beginTime=beginTime.replace("00","0");
        endTime=endTime.replace("00","0");
        Date date=new Date();
        int min=date.getMinutes();
        int hour=date.getHours();
        int begin=Integer.parseInt(beginTime.split(":")[0])*60+Integer.parseInt(beginTime.split(":")[1]);
        int end=Integer.parseInt(endTime.split(":")[0])*60+Integer.parseInt(endTime.split(":")[1]);
        return hour*60+min>=begin&&hour*60+min<=end;
    }
    /**
     * 外部正式商户订单成功转化率低于50%告警
     * @return
     * @throws TException
     */
    @Override
    public String getOrderSuccessRateWarning() throws TException {
        Map resultMap=new HashMap();
        List<Map> list2=new ArrayList<Map>();
        resultMap.put("status",1);
        resultMap.put("list",list2);
        List<RiskMonitorMerchantVo> list=RuleRedisCache.getMonitorMerchantCache();
        if(list!=null&&list.size()>0) {
            for(RiskMonitorMerchantVo vo:list) {
                if(!isDateRange(vo.getBeginTime(),vo.getEndTime()))
                    continue;
                list2.add(this.getMerchantInfoWarning(vo));
                this.addMerchantInfo(getMerchantOrderByType(vo,DateQueryType.NOW.getValue()),vo);
            }
        }

        else
        {
            resultMap.put("status",0);
        }
        return JsonMapperUtil.buildNonDefaultMapper().toJson(resultMap);
    }

    /**
     *
     * @param mapList
     * @param vo
     */
    private void addMerchantInfo(Map<String,List<Map>> mapList,RiskMonitorMerchantVo vo) {
        Map map=this.getMerchantInfoWarning(mapList,vo);
        if (map != null ) {
            List<Map> list=(List<Map>)map.get("list");
            if(list!=null&&list.size()>0)
            {
                for(Map tempMap:list) {
                    RiskMerchantOrderConversionRatio ratio = new RiskMerchantOrderConversionRatio();
                    ratio.setBeginTime(new Date(new Date().getTime() - vo.getFrequency() * 60 * 1000));
                    ratio.setEndTime(new Date());
                    ratio.setHost(tempMap.get("host")==null?null:tempMap.get("host").toString());
                    ratio.setMerchantId(vo.getMerchantId());
                    ratio.setProductCode(vo.getProductCode());
                    ratio.setProductName(ProductType.getContentByValue(vo.getProductCode()));
                    ratio.setCreateTime(new Date());
                    ratio.setMerchantSucessOrder(Integer.parseInt(tempMap.get("sucCount").toString()));
                    ratio.setMerchantTotalOrder(Integer.parseInt(tempMap.get("allCount").toString()));
                    ratio.setTransType(tempMap.get("transType")==null?null:tempMap.get("transType").toString());
                    ratio.setSucessRatio(this.getSuccessRate((float)ratio.getMerchantSucessOrder(),(float)ratio.getMerchantTotalOrder()));
                    logger.info("添加到订单成功率统计表数据:"+JsonMapperUtil.buildNonDefaultMapper().toJson(ratio));
                    riskMerchantOrderConversionRatioMapper.insert(ratio);
                }
            }
        }
    }

    /**
     *
     * @param vo 商户配置
     * @param type 0:当前 1：前一次时间段 2：前两次时间段 3：昨日时间段 4：前日时间段
     * @return 根据查询时间类型获取订单成功率
     */
    private Map<String,List<Map>> getMerchantOrderByType(RiskMonitorMerchantVo vo,String type)
    {
        Map map=new HashMap();
        Map map1=new HashMap();
        if(!StringUtil.isBlank(vo.getMerchantId())) {
            map.put("merchantId", vo.getMerchantId());
            map1.put("merchant_id", vo.getMerchantId());
        }
        if(!StringUtil.isBlank(vo.getProductCode()))
            map.put("productType", vo.getProductCode());
            map1.put("product_code", vo.getProductCode());
        //当前
       if(type.equals(DateQueryType.NOW.getValue())) {
           ;
           map.put("beginTime", LocalDateTime.now().minusMinutes(vo.getFrequency()+2).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
           map.put("endTime",  LocalDateTime.now().minusMinutes(2).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
           map1.put("beginTime", String.valueOf(LocalDateTime.now().minusMinutes(vo.getFrequency()+2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
           map1.put("endTime", String.valueOf(LocalDateTime.now().minusMinutes(2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
       }
       //前一次时间段
       if(type.equals(DateQueryType.PREFIRST.getValue())) {
           map.put("beginTime", LocalDateTime.now().minusMinutes(2*vo.getFrequency()+2).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
           map.put("endTime", LocalDateTime.now().minusMinutes(vo.getFrequency()+2).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
           map1.put("beginTime", String.valueOf(LocalDateTime.now().minusMinutes(2*vo.getFrequency()+2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
           map1.put("endTime", String.valueOf(LocalDateTime.now().minusMinutes(vo.getFrequency()+2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        }
        //前两次时间段
        if(type.equals(DateQueryType.PRESECOND.getValue())) {
            map.put("beginTime", LocalDateTime.now().minusMinutes(3*vo.getFrequency()+2).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
            map.put("endTime", LocalDateTime.now().minusMinutes(2*vo.getFrequency()+2).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
            map1.put("beginTime", String.valueOf(LocalDateTime.now().minusMinutes(3*vo.getFrequency()+2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            map1.put("endTime", String.valueOf(LocalDateTime.now().minusMinutes(2*vo.getFrequency()+2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        }
        //昨日时间段
        if(type.equals(DateQueryType.YESTERDAY.getValue())) {
            map.put("beginTime", LocalDateTime.now().minusDays(1).minusMinutes(vo.getFrequency()+2).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
            map.put("endTime", LocalDateTime.now().minusDays(1).minusMinutes(2).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
            map1.put("beginTime", String.valueOf(LocalDateTime.now().minusDays(1).minusMinutes(vo.getFrequency()+2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            map1.put("endTime", String.valueOf(LocalDateTime.now().minusDays(1).minusMinutes(2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        }
        //前日时间段
        if(type.equals(DateQueryType.BEFOREYESTERDAY.getValue())) {
            map.put("beginTime", LocalDateTime.now().minusDays(2).minusMinutes(vo.getFrequency()+2).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
            map.put("endTime", LocalDateTime.now().minusDays(2).minusMinutes(2).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
            map1.put("beginTime",String.valueOf(LocalDateTime.now().minusDays(2).minusMinutes(vo.getFrequency()+2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            map1.put("endTime", String.valueOf(LocalDateTime.now().minusDays(2).minusMinutes(2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        }
        return riskWarningMapper.getOrderCount(JsonMapperUtil.buildNonDefaultMapper().toJson(map1),JsonMapperUtil.buildNonDefaultMapper().toJson(map));
    }
    /**
     *
     * @param vo 通道配置
     * @param type 0:当前 1：前一次时间段 2：前两次时间段 3：昨日时间段 4：前日时间段
     * @return 根据查询时间类型获取通道成功率
     */
    private List<Map<String,String>> getChannelByType(RiskMonitorChannelVo vo,String type)
    {
        Map map=new HashMap();
        Map rangeMap=new HashMap();
        if(!StringUtil.isBlank(vo.getChannelPartnerCode())) {

            //直连
            if(vo.getChannelPartnerCode().indexOf("DIRCON-")>-1)
            {
                map.put("channel_partner_code", vo.getChannelPartnerCode().split("-")[0]);
                map.put("bank_no", vo.getChannelPartnerCode().split("-")[1]);
            }
            else
                map.put("channel_partner_code", vo.getChannelPartnerCode());
        }
        if(!StringUtil.isBlank(vo.getPayTypeCode()))
            map.put("channel_type_code", vo.getPayTypeCode());
        rangeMap.put("field","req_time");


        //当前
        if(type.equals(DateQueryType.NOW.getValue())) {

            rangeMap.put("begin", String.valueOf(LocalDateTime.now().minusMinutes(vo.getFrequency()+2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            rangeMap.put("end", String.valueOf(LocalDateTime.now().minusMinutes(2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));

        }
        //前一次时间段
        if(type.equals(DateQueryType.PREFIRST.getValue())) {
            rangeMap.put("begin", String.valueOf(LocalDateTime.now().minusMinutes(2*vo.getFrequency()+2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            rangeMap.put("end", String.valueOf(LocalDateTime.now().minusMinutes(vo.getFrequency()+2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));

        }
        //前两次时间段
        if(type.equals(DateQueryType.PRESECOND.getValue())) {
            rangeMap.put("begin", String.valueOf(LocalDateTime.now().minusMinutes(3*vo.getFrequency()+2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            rangeMap.put("end", String.valueOf(LocalDateTime.now().minusMinutes(2*vo.getFrequency()+2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));

        }
        //昨日时间段
        if(type.equals(DateQueryType.YESTERDAY.getValue())) {
            rangeMap.put("begin", String.valueOf(LocalDateTime.now().minusDays(1).minusMinutes(vo.getFrequency()+2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            rangeMap.put("end", String.valueOf(LocalDateTime.now().minusDays(1).minusMinutes(2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));

        }
        //前日时间段
        if(type.equals(DateQueryType.BEFOREYESTERDAY.getValue())) {
            rangeMap.put("begin", String.valueOf(LocalDateTime.now().minusDays(2).minusMinutes(vo.getFrequency()+2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            rangeMap.put("end", String.valueOf(LocalDateTime.now().minusDays(2).minusMinutes(2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));

        }

        List<Map<String,String>> result= riskControlLimitAmountMapper.getChannelAggInfoWithHost(map,null, rangeMap);
        return this.groupByChannelCode(result);
    }
    /**
     *
     * @param mapList
     * @param vo
     */
    private Map getMerchantInfoWarning(Map<String,List<Map>> mapList,RiskMonitorMerchantVo vo)
    {
        List<Map> list1=mapList.get("sucList");
        List<Map> list2=mapList.get("allList");
        Map tempMap=new HashMap();
        List<Map> list=new ArrayList<Map>();
        tempMap.put("merchantId", vo.getMerchantId());
        tempMap.put("productType", vo.getProductCode());
        tempMap.put("list", list);
        tempMap.put("beginTime",DateUtil.dateToString(new Date(new Date().getTime() - vo.getFrequency() * 60 * 1000),"yyyy-MM-dd HH:mm:ss"));
        tempMap.put("endTime", DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
        if(list1!=null&&list1.size()>0) {
            for (int i = 0; i < list1.size(); i++) {
                Map map = new HashMap();
                map.put("host", list1.get(i).get("host"));
                map.put("allCount", this.getTotalOrderCountByHost(map.get("host").toString(),list2));
                map.put("sucCount", list1.get(i).get("orderCount")==null||list1.get(i).get("orderCount").toString().equals("")?0:list1.get(i).get("orderCount").toString());
                map.put("rate", this.getSuccessRate(Float.parseFloat(map.get("sucCount").toString()),Float.parseFloat(map.get("allCount").toString())));
                map.put("transType",list1.get(i).get("trans_type"));
                list.add(map);
            }
        }
        return tempMap;

    }

    /**
     *
     * @param host 服务器名称
     * @param list 总订单列表
     * @return 获取某个服务器的总订单
     */
    private String getTotalOrderCountByHost(String host,List<Map> list)
    {
        for(Map map:list)
        {
            if(map.get("host").toString().toLowerCase().equals(host.toLowerCase()))
                return map.get("orderCount")==null||map.get("orderCount").toString().equals("")?"0":map.get("orderCount").toString();
        }
        return "0";

    }
    private   Map getMerchantInfoWarning(RiskMonitorMerchantVo vo)
    {
        //当前
        Map<String,List<Map>> list=getMerchantOrderByType(vo,DateQueryType.NOW.getValue());
        //前一次
        Map<String,List<Map>> list1=getMerchantOrderByType(vo,DateQueryType.PREFIRST.getValue());
        //前两次
        Map<String,List<Map>> list2=getMerchantOrderByType(vo,DateQueryType.PRESECOND.getValue());
        //昨天
        Map<String,List<Map>> list3=getMerchantOrderByType(vo,DateQueryType.YESTERDAY.getValue());
        //前天
        Map<String,List<Map>> list4=getMerchantOrderByType(vo,DateQueryType.BEFOREYESTERDAY.getValue());
        List<Map> list5=list.get("sucList");
        List<Map> list6=list.get("allList");
        Map tempMap=new HashMap();
        List<Map> list7=new ArrayList<Map>();
        tempMap.put("merchantId", vo.getMerchantId());
        tempMap.put("productType", vo.getProductCode());
        tempMap.put("threshold", vo.getThreshold());
        tempMap.put("changeRate", vo.getChangeRate());
        tempMap.put("list", list7);
        tempMap.put("beginTime", DateUtil.dateToString(new Date(new Date().getTime() - vo.getFrequency() * 60 * 1000),"yyyy-MM-dd HH:mm:ss"));
        tempMap.put("endTime", DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
        if(list5!=null&&list5.size()>0) {
            for (int i = 0; i < list5.size(); i++) {
                Map map = new HashMap();
                map.put("host", list5.get(i).get("host"));
                map.put("allCount", this.getTotalOrderCountByHost(map.get("host").toString(),list6));
                map.put("sucCount", list5.get(i).get("orderCount")==null||list5.get(i).get("orderCount").equals("")?"0":list5.get(i).get("orderCount"));
                map.put("rate", this.getSuccessRate(Float.parseFloat(map.get("sucCount").toString()),Float.parseFloat(map.get("allCount").toString())));
                map.put("transType",list5.get(i).get("trans_type"));
                WarningLevel level=this.getWarningLevel(map.get("rate").toString(),vo);
                if(level==WarningLevel.NO)
                    continue;
                map.put("preFirst",this.getCompareRate(list5.get(i).get("host").toString(),list1));
                map.put("preSecond",this.getCompareRate(list5.get(i).get("host").toString(),list2));
                map.put("yesterday",this.getCompareRate(list5.get(i).get("host").toString(),list3));
                map.put("beforeYesterday",getCompareRate(list5.get(i).get("host").toString(),list4));;
                map.put("warningLevel",level==WarningLevel.FATAL?"<font color='red'>"+level.getContent()+"</font>":level.getContent());
                list7.add(map);
            }
        }
        return tempMap;

    }

    /**
     *
     * @param rate 成功率
     * @param vo 商户配置信息
     * @return  根据当前成功率和商户阈值和变化率获取报警级别
     */
    private WarningLevel getWarningLevel(String rate,RiskMonitorMerchantVo vo) {
        if (vo.getThreshold() - vo.getChangeRate() < Integer.parseInt(rate) && Integer.parseInt(rate) < vo.getThreshold() + vo.getChangeRate())
            return WarningLevel.NO;
        else if(Integer.parseInt(rate)<(vo.getThreshold() - vo.getChangeRate())/2||Integer.parseInt(rate)>(100-vo.getThreshold() - vo.getChangeRate())/2)
            return WarningLevel.FATAL;
        else
            return WarningLevel.NORMAL;
    }
    /**
     *
     * @param rate 成功率
     * @return  根据当前成功率和商户阈值和变化率获取报警级别
     */
    private WarningLevel getChannelWarningLevel(String rate,int threshold,int changeRate) {
        if (threshold - changeRate < Integer.parseInt(rate) && Integer.parseInt(rate) < threshold + changeRate)
            return WarningLevel.NO;
        else if(Integer.parseInt(rate)<(threshold - changeRate)/2||Integer.parseInt(rate)>(100-threshold - changeRate)/2)
            return WarningLevel.FATAL;
        else
            return WarningLevel.NORMAL;
    }

    /**
     *
     * @param host 服务器
     * @param mapList 订单成功率列表
     * @return 根据host查询具体host的订单成功率
     */
    private int getCompareRate(String host,Map<String,List<Map>> mapList)
    {
        String allCount="";
        List<Map> list1=mapList.get("sucList");
        List<Map> list2=mapList.get("allList");
        allCount=this.getTotalOrderCountByHost(host,list2);
        if(allCount.equals("0"))
            return 100;
        if(list1!=null&&list1.size()>0) {
            for (int i = 0; i < list1.size(); i++) {
                if(host.equals(list1.get(i).get("host")))
                {
                  return this.getSuccessRate(Float.parseFloat(list1.get(i).get("orderCount")==null||list1.get(i).get("orderCount").equals("")?"0":list1.get(i).get("orderCount").toString()),Float.parseFloat(allCount));
                }

            }
            return 0;
        }
        else
        {
           return 0;
        }
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
    private Map getChannelInfoWarningList(List<Map<String,String>> list,RiskMonitorChannelVo vo,boolean isSkip,boolean flag)
    {
        List<Map> list2=new ArrayList<Map>();
        Map tempMap=new HashMap();
        tempMap.put("channel_partner_code", vo.getChannelPartnerName());
        tempMap.put("channel_type_code", vo.getChannelTypeName());
        tempMap.put("list", list2);
        tempMap.put("beginTime", flag?DateUtil.dateToString(new Date(new Date().getTime() - vo.getFrequency() * 60 * 1000),"yyyy-MM-dd HH:mm:ss"):DateUtil.dateToString(new Date(),"yyyy-MM-dd")+" "+vo.getBeginTime());
        tempMap.put("endTime", DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
        if(!isSkip&&(list==null||list.size()==0))
        {
            Map map=new HashMap();
            map.put("totalSuccessNum", "0");
            map.put("totalNum", "0");
            map.put("rate", "100");
            list2.add(map);
        }
        else if(list!=null&&list.size()>0)
        {
            for(Map map2:list)
            {
                Long totalSuccessNum=Long.parseLong(map2.get("successNum").toString());
                Long totalFailNum=Long.parseLong(map2.get("failNum").toString());
                map2.put("totalSuccessNum", String.valueOf(totalSuccessNum));
                map2.put("totalNum", String.valueOf(totalFailNum+totalSuccessNum));
                map2.put("rate", String.valueOf(this.getSuccessRate((float)totalSuccessNum,(float)totalFailNum+totalSuccessNum)));
                if(isSkip&&this.getSuccessRate((float)totalSuccessNum,(float)totalFailNum+totalSuccessNum)>=vo.getThreshold())
                    continue;
                list2.add(map2);
            }
        }
        return tempMap;
    }
    private Map getChannelInfoWarningList(RiskMonitorChannelVo vo)
    {
        List<Map> list2=new ArrayList<Map>();
        Map tempMap=new HashMap();
        //当前
        List<Map<String,String>> list3=this.getChannelByType(vo,DateQueryType.NOW.getValue());
        //前一次
        List<Map<String,String>> list4=this.getChannelByType(vo,DateQueryType.PREFIRST.getValue());
        //前两次
        List<Map<String,String>> list5=this.getChannelByType(vo,DateQueryType.PRESECOND.getValue());
        //昨天
        List<Map<String,String>> list6=this.getChannelByType(vo,DateQueryType.YESTERDAY.getValue());
        //前天
        List<Map<String,String>> list7=this.getChannelByType(vo,DateQueryType.BEFOREYESTERDAY.getValue());
        tempMap.put("channel_partner_code", vo.getChannelPartnerName());
        tempMap.put("channel_type_code", vo.getChannelTypeName());
        tempMap.put("threshold", vo.getThreshold());
        tempMap.put("changeRate", vo.getChangeRate());
        tempMap.put("list", list2);
        tempMap.put("beginTime", DateUtil.dateToString(new Date(new Date().getTime() - vo.getFrequency() * 60 * 1000),"yyyy-MM-dd HH:mm:ss"));
        tempMap.put("endTime", DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));

        if(list3!=null&&list3.size()>0)
        {
            for(Map map2:list3)
            {
                Long totalSuccessNum=Long.parseLong(map2.get("successNum").toString());
                Long totalFailNum=Long.parseLong(map2.get("failNum").toString());
                map2.put("totalSuccessNum", String.valueOf(totalSuccessNum));
                map2.put("totalNum", String.valueOf(totalFailNum+totalSuccessNum));
                map2.put("rate", String.valueOf(this.getSuccessRate((float)totalSuccessNum,(float)totalFailNum+totalSuccessNum)));
                WarningLevel level=this.getChannelWarningLevel(map2.get("rate").toString(),vo.getThreshold(),vo.getChangeRate());
                if(level==WarningLevel.NO)
                    continue;
                map2.put("preFirst",this.getChannelCompareRate(map2.get("host").toString(),list4));
                map2.put("preSecond",this.getChannelCompareRate(map2.get("host").toString(),list5));
                map2.put("yesterday",this.getChannelCompareRate(map2.get("host").toString(),list6));
                map2.put("beforeYesterday",this.getChannelCompareRate(map2.get("host").toString(),list7));
                map2.put("warningLevel",level==WarningLevel.FATAL?"<font color='red'>"+level.getContent()+"</font>":level.getContent());
                list2.add(map2);
            }
        }
        return tempMap;
    }
    private String getChannelCompareRate(String host,List<Map<String,String>> list)
    {
        if(list==null||list.size()==0)
            return "100";
        for(Map<String,String> map :list)
        {
            if(map.get("host").equals(host))
            {
                Long totalSuccessNum=Long.parseLong(map.get("successNum").toString());
                Long totalFailNum=Long.parseLong(map.get("failNum").toString());
                return String.valueOf(this.getSuccessRate((float)totalSuccessNum,(float)totalFailNum+totalSuccessNum));
            }

        }
        return "0";
    }
    /**
     * 通道成功转化率低于50%告警
     * @return
     * @throws TException
     */
    @Override
    public String getChannelSuccessRateWarning() throws TException {
        Map resultMap=new HashMap();
        List<Map> list2=new ArrayList<Map>();
        resultMap.put("status",1);
        resultMap.put("list",list2);
        List<RiskMonitorChannelVo> list=RuleRedisCache.getMonitorChannelCache();
        if(list!=null&&list.size()>0) {
            for(RiskMonitorChannelVo vo:list) {
                if(!isDateRange(vo.getBeginTime(),vo.getEndTime()))
                    continue;

                list2.add(this.getChannelInfoWarningList(vo));
                this.addChannelRate(this.getChannelByType(vo,DateQueryType.NOW.getValue()),vo);
            }
        }

        else
        {
            resultMap.put("status",0);
        }

        return JsonMapperUtil.buildNonDefaultMapper().toJson(resultMap);

    }

     private void addChannelRate(List<Map<String,String>> list,RiskMonitorChannelVo vo)
     {
         Map tempMap=this.getChannelInfoWarningList(list,vo,false,true);
         List<Map> list2=(List<Map>)tempMap.get("list");
         if(list2!=null&&list2.size()>0)
         {
             for(Map map:list2)
             {
                 RiskChannelOrderConversionRatio ratio=new RiskChannelOrderConversionRatio();
                 ratio.setChannelPartnerCode(vo.getChannelPartnerCode());
                 ratio.setChannelPartnerName(vo.getChannelPartnerName());
                 ratio.setChannelTypeCode(vo.getPayTypeCode());
                 ratio.setChannelTypeName(vo.getChannelTypeName());
                 ratio.setBeginTime(new Date(new Date().getTime() - vo.getFrequency() * 60 * 1000));
                 ratio.setEndTime(new Date());
                 ratio.setChannelSuccessOrder(Integer.parseInt(map.get("totalSuccessNum").toString()));
                 ratio.setChannelTotalOrder(Integer.parseInt(map.get("totalNum").toString()));
                 ratio.setSucessRatio(Integer.parseInt(map.get("rate").toString()));
                 ratio.setCreateTime(new Date());
                 ratio.setHost(map.get("host")==null?null:map.get("host").toString());
                 logger.info("添加到通道成功率:"+JsonMapperUtil.buildNonDefaultMapper().toJson(ratio));
                 riskChannelOrderConversionRatioMapper.insert(ratio);
             }
         }
     }
    /**
     * 没有日志生成报警
     * @return
     * @throws TException
     */
    @Override
    public String getPaymentNoLogWarning() throws TException {
        Map map=new HashMap();
        map.put("beginTime", DateUtil.dateToString(new Date(new Date().getTime()-5*60*1000),"yyyy-MM-dd HH:mm:ss"));
        map.put("endTime", DateUtil.dateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
        Map<String,String> resultMap=riskWarningMapper.isExistsMerchantOrderLog(JsonMapperUtil.buildNonDefaultMapper().toJson(map));
        try {
            logger.info("无日志生成返回结果:" + JsonMapperUtil.buildNonDefaultMapper().toJson(resultMap));
        }
        catch(Exception ex)
        {
            
        }
        if(resultMap!=null) {
            for (Map.Entry<String,String> temp : resultMap.entrySet())
            {
                String str=temp.getValue();
                String[] arrHost=str.split(",");
                for(String host:arrHost) {
                    if (JedisClusterUtil.getJedisCluster().get("RISK_MONITOR_NOLOG_"+temp.getKey()+"_"+host) != null) {
                        str=str.replace(host,"");
                        resultMap.put(temp.getKey(),str);
                    } else {
                        JedisClusterUtil.getJedisCluster().setex("RISK_MONITOR_NOLOG_"+temp.getKey()+"_"+host, 24 * 3600, "1");
                    }
                }
            }
            return JsonMapperUtil.buildNonDefaultMapper().toJson(resultMap);
        }
        else
            return "";

    }
    private List<Map<String,String>> groupByChannelCode(List<Map<String,String>> list)
    {
        Map<String,String> newMap=new HashMap<String,String>();
        List<Map<String,String>> list2=new ArrayList<Map<String,String>>();
        for(Map<String,String> map:list) {
            Long success=0L,fail=0L;
            if (map.get("request_status").toString().equals(RiskChannelReqType.PAY.getValue().toLowerCase()) && map.get("result").toString().equals(RiskChannelReqResult.RESULT_SUCCESS.getValue().toLowerCase()))
                success = Long.parseLong(map.get("count").toString());

            if (map.get("request_status").toString().equals(RiskChannelReqType.DECRYPT_ASYNC.getValue().toLowerCase())) {
                success = Long.parseLong(map.get("count").toString());
            }
            if (map.get("request_status").toString().equals(RiskChannelReqType.ENCRYPT.getValue().toLowerCase()))
                fail = Long.parseLong(map.get("count").toString());
            if (map.get("request_status").toString().equals(RiskChannelReqType.PAY.getValue().toLowerCase()) && map.get("result").toString().equals(RiskChannelReqResult.RESULT_FAILURE.getValue().toLowerCase()))
                fail = Long.parseLong(map.get("count").toString());
            if (map.get("request_status").toString().equals(RiskChannelReqType.QUOTA.getValue().toLowerCase())) {
                success = Long.parseLong(map.get("count").toString());
            }
            if(!newMap.containsKey(map.get("host"))) {
                newMap.put(map.get("host"), String.valueOf(success) + "," + String.valueOf(fail));
            }
            else {
                Long successNum=Long.parseLong(newMap.get(map.get("host")).split(",")[0]);
                Long failNum=Long.parseLong(newMap.get(map.get("host")).split(",")[1]);
                newMap.put(map.get("host"), String.valueOf(successNum+success)+","+String.valueOf(failNum+fail));
            }
        }

        for(Map.Entry<String,String> entry:newMap.entrySet())
        {
            Map<String,String> newMap2=new HashMap<String,String>();
            newMap2.put("host",entry.getKey());
            newMap2.put("successNum",entry.getValue().split(",")[0]);
            newMap2.put("failNum",entry.getValue().split(",")[1]);
            list2.add(newMap2);
        }
        return list2;

    }
}