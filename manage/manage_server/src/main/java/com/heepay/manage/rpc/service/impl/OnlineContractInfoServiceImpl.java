/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.rpc.service.impl;

import com.heepay.common.util.DateUtil;
import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.modules.merchant.dao.MerchantProductRateDao;
import com.heepay.manage.modules.merchant.dao.OnlineContractInfoDao;
import com.heepay.manage.modules.merchant.vo.MerchantProductRate;
import com.heepay.manage.modules.merchant.vo.OnlineContractInfo;
import com.heepay.manage.modules.sys.entity.Dict;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.rpc.service.OnlineContractInfoService;
import com.heepay.manage.rpc.service.OnlineContractInfoThrift;
import com.heepay.rpc.service.RpcService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 描述：线上签约service
 *
 * 创建者  B.HJ
 * 创建时间 2017-04-27-17:23
 * 创建描述：线上签约service
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
@Component
@RpcService(name = "onlineContractInfoServiceImpl", processor = OnlineContractInfoService.Processor.class)
public class OnlineContractInfoServiceImpl implements OnlineContractInfoService.Iface{


    private static final String FORMATE = "yyyy.MM.dd";
    @Autowired
    private OnlineContractInfoDao onlineContractInfoDao;

    @Autowired
    private MerchantProductRateDao merchantProductRateDao;

    @Override
    public String insertNewProduct(List<OnlineContractInfoThrift> thriftList) throws TException {
        String userId = thriftList.get(0).getUserId();
        String batchNo = onlineContractInfoDao.queryMaxBatchNoByUserId(userId);
        for (OnlineContractInfoThrift thrift:thriftList) {
            OnlineContractInfo onlineContractInfo = new OnlineContractInfo();
            onlineContractInfo.setUserId(thrift.getUserId());// 用户id
            onlineContractInfo.setNormProductCode(thrift.getNormProductCode());//标准产品编码
            onlineContractInfo.setUrl(thrift.getUrl());// 网址
            onlineContractInfo.setNotifyUrl(thrift.getNotifyUrl());//异步通知地址
            onlineContractInfo.setBackUrl(thrift.getBackUrl());// 同步返回地址
            onlineContractInfo.setIpDomain(thrift.getIpDomain());// ip/域名
            onlineContractInfo.setFile1(thrift.getFile1());//应用1
            onlineContractInfo.setFile2(thrift.getFile2());//应用2
            onlineContractInfo.setFile3(thrift.getFile3());//应用3
            onlineContractInfo.setFile4(thrift.getFile4());//应用4
            onlineContractInfo.setFile5(thrift.getFile5());//应用5
            onlineContractInfo.setStatus("COMMIT");//状态
            onlineContractInfo.setOriginalFilePath(thrift.getOriginalFilePath());//源文件地址
            onlineContractInfo.setMiddleFilePath(thrift.getMiddleFilePath());//盖了一个章的文件地址
            onlineContractInfo.setLegalAuditStatus(RouteStatus.AUDING.getValue());
            onlineContractInfo.setCreateTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));// 创建时间
            onlineContractInfo.setBatchNo(batchNo == null ? "1":String.valueOf(Integer.valueOf(batchNo)+1));//批次号
            onlineContractInfoDao.insert(onlineContractInfo);
        }
        return Boolean.TRUE.toString();
    }

    @Override
    public int queryProductsBatchByUserId(String userId) throws TException {
        Integer num = onlineContractInfoDao.queryProductsBatchByUserId(userId);
        if(null == num){
            return 0;
        }
        return num;
    }

    @Override
    public List<OnlineContractInfoThrift> queryProductsBybatchNoAndUserId(String batchNo, String userId) throws TException {
        List<OnlineContractInfo> list = onlineContractInfoDao.queryProductsBybatchNoAndUserId(batchNo,userId);
        List<OnlineContractInfoThrift> thriftList = new ArrayList<>();
        for (OnlineContractInfo info:list) {
            OnlineContractInfoThrift thrift = new OnlineContractInfoThrift();
            thrift.setBatchNo(info.getBatchNo());
            thrift.setNormProductCode(info.getNormProductCode());//标准产品编码
            thrift.setUrl(info.getUrl());// 网址
            thrift.setNotifyUrl(info.getNotifyUrl());//异步通知地址
            thrift.setBackUrl(info.getBackUrl());// 同步返回地址
            thrift.setIpDomain(info.getIpDomain());// ip/域名
            thrift.setFile1(info.getFile1());//应用1
            thrift.setFile2(info.getFile2());//应用2
            thrift.setFile3(info.getFile3());//应用3
            thrift.setFile4(info.getFile4());//应用4
            thrift.setFile5(info.getFile5());//应用5
            thrift.setStatus(info.getStatus());
            thrift.setTimes(info.getTimes());//审核失败次数
            thrift.setNormProductCode(info.getNormProductCode());
            //创建时间
            thrift.setCreateTime(info.getCreateTime() == null ? "":DateUtil.dateToString(info.getCreateTime(),FORMATE));
            //审核通过时间
            thrift.setPassTime(info.getPassTime() == null ? "":DateUtil.dateToString(info.getPassTime(),FORMATE));
            //拒绝时间
            thrift.setRejectTime(info.getRejectTime() == null ? "":DateUtil.dateToString(info.getRejectTime(),FORMATE));

            thriftList.add(thrift);
        }
        return thriftList;
    }

    @Override
    public String updateSignFailProduct(List<OnlineContractInfoThrift> thriftList, String batchNo) throws TException {
        for (OnlineContractInfoThrift thrift:thriftList) {
            OnlineContractInfo onlineContractInfo = new OnlineContractInfo();
            onlineContractInfo.setUserId(thrift.getUserId());// 用户id
            onlineContractInfo.setNormProductCode(thrift.getNormProductCode());//标准产品编码
            onlineContractInfo.setUrl(thrift.getUrl());// 网址
            onlineContractInfo.setNotifyUrl(thrift.getNotifyUrl());//异步通知地址
            onlineContractInfo.setBackUrl(thrift.getBackUrl());// 同步返回地址
            onlineContractInfo.setIpDomain(thrift.getIpDomain());// ip/域名
            onlineContractInfo.setBatchNo(batchNo);
            // 如果不是sdk产品
            if("BZCP09".equals(thrift.getNormProductCode())){
                onlineContractInfo.setFile1(null);//应用1
                onlineContractInfo.setFile2(null);//应用2
                onlineContractInfo.setFile3(null);//应用3
                onlineContractInfo.setFile4(null);//应用4
                onlineContractInfo.setFile5(null);//应用5
            }else {
                onlineContractInfo.setFile1(thrift.getFile1());//应用1
                onlineContractInfo.setFile2(thrift.getFile2());//应用2
                onlineContractInfo.setFile3(thrift.getFile3());//应用3
                onlineContractInfo.setFile4(thrift.getFile4());//应用4
                onlineContractInfo.setFile5(thrift.getFile5());//应用5
            }
            onlineContractInfo.setStatus("COMMIT");// 重置状态
            onlineContractInfo.setCreateTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));// 创建时间
            onlineContractInfo.setPassTime(null);// 重置审核通过时间
            onlineContractInfo.setContractTime(null);// 重置签约时间
            onlineContractInfo.setRejectTime(null);// 重置审核拒绝时间
            onlineContractInfoDao.updateSignFailProduct(onlineContractInfo);
        }
        return Boolean.TRUE.toString();
    }

    @Override
    public OnlineContractInfoThrift queryProductByUserIdAndProductCode(String userId, String productCode) throws TException {
        OnlineContractInfo onlineContractInfo = onlineContractInfoDao.queryProductByUserIdAndProductCode(userId, productCode);
        OnlineContractInfoThrift onlineContractInfoThrift = new OnlineContractInfoThrift();
        //无结果
        if(null == onlineContractInfo){
            return onlineContractInfoThrift;
        }
        //有结果
        onlineContractInfoThrift.setId(onlineContractInfo.getId());
        onlineContractInfoThrift.setUrl(onlineContractInfo.getUrl());
        onlineContractInfoThrift.setNotifyUrl(onlineContractInfo.getNotifyUrl());
        onlineContractInfoThrift.setIpDomain(onlineContractInfo.getIpDomain());
        onlineContractInfoThrift.setBackUrl(onlineContractInfo.getBackUrl());
        onlineContractInfoThrift.setStatus(onlineContractInfo.getStatus());
        return onlineContractInfoThrift;
    }


    @Override
    public String queryProductIsOpenByProductCode(String userId, String productCode) throws TException {
        String result = onlineContractInfoDao.queryProductIsOpenByProductCode(userId, productCode);
        return result == null ? "" : result;
    }

    @Override
    public List<OnlineContractInfoThrift> queryUnfinishedProductInfoByUserId(String userId) throws TException {
        List<OnlineContractInfoThrift> resultList = new ArrayList<>();
        List<OnlineContractInfo> list = onlineContractInfoDao.queryUnfinishedProductInfoByUserId(userId);
        if(list.isEmpty()){
            return resultList;
        }else{
            for (OnlineContractInfo onlineContractInfo:list) {
                OnlineContractInfoThrift onlineContractInfoThrift = new OnlineContractInfoThrift();
                onlineContractInfoThrift.setBatchNo(onlineContractInfo.getBatchNo());//批次号
                onlineContractInfoThrift.setStatus(onlineContractInfo.getStatus());
                onlineContractInfoThrift.setTimes(onlineContractInfo.getTimes());//审核失败次数
                onlineContractInfoThrift.setNormProductCode(onlineContractInfo.getNormProductCode());
                //创建时间
                onlineContractInfoThrift.setCreateTime(onlineContractInfo.getCreateTime() == null ? "":DateUtil.dateToString(onlineContractInfo.getCreateTime(),FORMATE));
                //审核通过时间
                onlineContractInfoThrift.setPassTime(onlineContractInfo.getPassTime() == null ? "":DateUtil.dateToString(onlineContractInfo.getPassTime(),FORMATE));
                //拒绝时间
                onlineContractInfoThrift.setRejectTime(onlineContractInfo.getRejectTime() == null ? "":DateUtil.dateToString(onlineContractInfo.getRejectTime(),FORMATE));
                resultList.add(onlineContractInfoThrift);
            }
            return resultList;
        }
    }

    @Override
    public List<OnlineContractInfoThrift> queryAllProductInfoByUserId(String userId) throws TException {
        List<OnlineContractInfoThrift> resultList = new ArrayList<>();
        List<OnlineContractInfo> list = onlineContractInfoDao.queryAllProductInfoByUserId(userId);
        if(list.isEmpty()){
            return resultList;
        }else {
            for (OnlineContractInfo onlineContractInfo:list) {
                OnlineContractInfoThrift onlineContractInfoThrift = new OnlineContractInfoThrift();
                onlineContractInfoThrift.setNormProductCode(onlineContractInfo.getNormProductCode());
                onlineContractInfoThrift.setStatus(onlineContractInfo.getStatus());
                resultList.add(onlineContractInfoThrift);
            }
            return resultList;
        }
    }

    /**
     * 产品开通线上签约产品编码
     * ly  2017年5月12日14:04:05
     */
    @Override
    public String queryProductIsOpen(String userId) throws TException {
        String products = "";
        //获取商户开通的产品
        List<MerchantProductRate> merchantProducts = merchantProductRateDao.findMerchantProduct(userId);
        if(null != merchantProducts && !merchantProducts.isEmpty()){
            for(MerchantProductRate merchantProductRate : merchantProducts) {
                //拼入返回的String
                products += "BZ"+merchantProductRate.getProductCode();
            }
        }
        return products;
    }

}
