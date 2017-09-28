package com.heepay.rpc.billing.service.impl.profit.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.dao.ClearProfitRecordMapper;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.entity.ClearProfitRecord;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.ChargeDeductType;
import com.heepay.enums.billing.SettleDifferIsProfit;
import com.heepay.rpc.billing.model.ClearShareProfitDetailModel;
import com.heepay.rpc.billing.model.ClearShareProfitModel;
import com.heepay.rpc.billing.service.IClearingProfitDetailService;
/**
 * 
 *
 * 描    述：分润明细入库和更新商户侧清算记录表中的是否分润字段
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月8日 上午10:09:26
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
public class ClearingProfitDetailServiceTransactionalImpl implements IClearingProfitDetailService{
	
	private static final Logger logger = LoggerFactory.getLogger(ClearingProfitDetailServiceTransactionalImpl.class);

	@Autowired
	private ClearProfitRecordMapper clearProfitRecordMapperDaoImpl;
	@Autowired
	private ClearMerchantRecordMapper clearMerchantRecordMapperDaoImpl;
	

	/**
	 * 
	 * @方法说明：保存分润明细和更新商户侧清算记录表中的是否分润
	 * @author xuangang
	 * @param clearShareProfitVo
	 * @时间：2016年12月21日下午5:55:23
	 */
	@Transactional()
	public void saveClearingProfit(ClearShareProfitModel clearShareProfitVo){
		try{
			//验证对象不为空
			if(null != clearShareProfitVo){
				logger.info("保存分润明细和更新商户侧清算记录表{}", clearShareProfitVo);
				//用于查询
				ClearMerchantRecord clearMerchantRecord = new ClearMerchantRecord();
				if(0 != clearShareProfitVo.getMerchantId()){//商户侧id
					clearMerchantRecord.setMerchantId(Integer.valueOf(clearShareProfitVo.getMerchantId()));
				}
				if(StringUtils.isNotBlank(clearShareProfitVo.getTransNo())){//交易订单号
					clearMerchantRecord.setTransNo(clearShareProfitVo.getTransNo());
				}
				List<ClearShareProfitDetailModel> profitDetailList = clearShareProfitVo.getClearShareProfitDetail();//分润明细
				if(null != profitDetailList && profitDetailList.size() > 0){
					//循环分润明细
					for(ClearShareProfitDetailModel profitDetail : profitDetailList){
						try {
							//保存分润明细
							saveClearingProfitDetail(profitDetail,clearShareProfitVo.getShareId());
						} catch (Exception e) {
							logger.error("ClearingProfitDetailServiceImpl clearingProfitRecordMapper has a error {分润明细入库出错 FIND_ERROR}", e);
							throw new Exception();
						}
					}
				}
				logger.info("ClearingProfitDetailServiceImpl clearingProfitRecordMapper has a {分润明细入库  FIND_SUCCESS}", "交易订单号为："+clearShareProfitVo!=null?clearShareProfitVo.getTransNo():"clearShareProfitVo 对象为空"+"完成！共"+profitDetailList.size()+"条！");
				//更新商户侧清算记录中的是否分润字段
				updateClearMerChantRecordIsProfitByMerChantIdAndTransNo(clearShareProfitVo, clearMerchantRecord);
			}
		}catch(Exception e){
			logger.error("保存分润明细和更新商户侧清算记录表失败{}", clearShareProfitVo, e);
			throw new RuntimeException();
		}
		
	}
	
	
	/**
	 * 
	 * @throws Exception 
	 * @方法说明：分润明细入库
	 * @时间：2016年11月3日 下午4:34:35
	 * @创建人：wangdong
	 */
	public void saveClearingProfitDetail(ClearShareProfitDetailModel profitDetail, String shareId) throws Exception {
		
		//判断是否重复发送的数据
		String shareDetailId = profitDetail.getShareDetailId();
		logger.info("获取消息队列中的shareDetailId ="+shareDetailId);
		int id=Integer.parseInt(shareDetailId);//转换格式为int
		int flag = clearProfitRecordMapperDaoImpl.getBooleanExist(id);
		logger.info("根据shareDetailId查询的结果是---------->"+flag);
		if(flag==0){
			try {
				ClearProfitRecord clearingProfitRecord = new ClearProfitRecord();
				if(StringUtils.isNotBlank(profitDetail.getShareDetailId())){//分润明细ID（交易传过来的ID）  
					clearingProfitRecord.setShareDetailId(profitDetail.getShareDetailId());
				}else{
					logger.error("分润明细入库,分润明细ID为空！");
				}
				if(StringUtils.isNotBlank(shareId)){//分润号（交易系统用，清结算只负责记录）
					clearingProfitRecord.setShareId(shareId);
				}else{
					logger.error("分润明细入库,分润号为空！");
				}
				if(StringUtils.isNotEmpty(profitDetail.getMerchantId())){//商户号
					clearingProfitRecord.setMerchantId(Integer.valueOf(profitDetail.getMerchantId()));
				}else{
					logger.error("分润明细入库,商户号为空！");
				}
				if(StringUtils.isNotBlank(profitDetail.getTransNo())){//交易单号
					clearingProfitRecord.setTransNo(profitDetail.getTransNo());
				}else{
					logger.error("分润明细入库,交易单号为空！");
				}
				if(StringUtils.isNotBlank(profitDetail.getProfitAmount())){//分润金额
					clearingProfitRecord.setProfitAmount(new BigDecimal(profitDetail.getProfitAmount()));
					//订单应结算金额
					clearingProfitRecord.setProfitAmountPlan(new BigDecimal(profitDetail.getProfitAmount()));
				}else{
					logger.error("分润明细入库,分润金额为空！");
				}
				clearingProfitRecord.setProfitFee(new BigDecimal(0));//分润手续费
				clearingProfitRecord.setFeeWay(ChargeDeductType.EXTERNAL_DEDUCT.getValue());//手续费扣除方式
				clearingProfitRecord.setProfitStatus(SettleDifferIsProfit.ACCOUNTFLGN.getValue());//默认分润状态  N
				if(StringUtils.isNotBlank(profitDetail.getTransType())){//交易类型
					clearingProfitRecord.setTransType(profitDetail.getTransType());
				}else{
					logger.error("分润明细入库,交易类型为空！");
				}
				//分润明细入库
				clearProfitRecordMapperDaoImpl.insertClearProfitRecord(clearingProfitRecord);
				logger.info("ClearingProfitDetailServiceImpl saveClearingProfitDetail {分润明细入库成功 FIND_SUCCESS}", "分润明细ID"+profitDetail.getShareDetailId());
			} catch (Exception e) {
				logger.error("ClearingProfitDetailServiceImpl saveClearingProfitDetail has a error {分润明细入库出错 FIND_ERROR}", e);
				throw new Exception();
			}
		}else{
			logger.info("获取消息队列中的数据是重复数据不允许插入!");
		}
	}

	/**
	 * 
	 * @方法说明：更新商户侧清算记录中的是否分润字段
	 * @时间：2016年11月3日 下午2:33:03
	 * @创建人：wangdong
	 */
	public void updateClearMerChantRecordIsProfitByMerChantIdAndTransNo(ClearShareProfitModel clearShareProfitVo,
			ClearMerchantRecord clearMerchantRecord)throws Exception {
		try {
			//分润为是的    
			if(StringUtils.isNotBlank(clearShareProfitVo.getIsShare()) && StringUtils.equals(clearShareProfitVo.getIsShare(), "true")){
				//根据  商户号  交易单号  查询相应的商户侧清算记录  更新是否为分润
				clearMerchantRecord.setIsProfit(SettleDifferIsProfit.ACCOUNTFLGY.getValue());
				clearMerchantRecordMapperDaoImpl.updateIsProfitByMerChantIdAndTransNo(clearMerchantRecord);
				logger.info("ClearingProfitDetailServiceImpl clearingProfitRecordMapper has a {更新商户侧清算记录  更新是否为分润  FIND_SUCCESS}", "交易订单号为："+clearShareProfitVo.getTransNo()+"完成！");
			}
		} catch (Exception e) {
			logger.error("更新商户侧清算记录中的是否分润字段 有错误！FIND_ERROR{}", clearMerchantRecord, e);
			throw new Exception();
		}
	}
	
	
}
