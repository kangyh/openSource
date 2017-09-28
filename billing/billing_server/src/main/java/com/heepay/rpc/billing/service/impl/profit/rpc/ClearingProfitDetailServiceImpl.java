package com.heepay.rpc.billing.service.impl.profit.rpc;

import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.common.util.StringUtils;
import com.heepay.rpc.billing.model.ClearShareProfitDetailModel;
import com.heepay.rpc.billing.model.ClearShareProfitModel;
import com.heepay.rpc.billing.service.IClearingProfitDetailService;
import com.heepay.rpc.service.RpcService;

/**
 * 
 *
 * 描    述：分润明细入库Service
 *
 * 创 建 者： @author wangdong
 * 创建时间：2016年9月23日
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
@RpcService(name="ClearingProfitDetailServiceImpl",processor=com.heepay.rpc.billing.service.ClearShareProfitService.Processor.class)
public class ClearingProfitDetailServiceImpl implements com.heepay.rpc.billing.service.ClearShareProfitService.Iface{
	
	private static final Logger logger = LoggerFactory.getLogger(ClearingProfitDetailServiceImpl.class);
	
	@Autowired
	private IClearingProfitDetailService clearingProfitDetailService;
	
	/**	
	 * 
	 * @方法说明：根据交易传过来的信息更新商户侧清算表和入库
	 * @时间：2016年11月3日 上午11:09:03
	 * @创建人：wangdong
	 */
	@Override
	public void saveClearShareProfitRecord(ClearShareProfitModel clearShareProfitVo)throws TException {
		try {
			clearingProfitDetailService.saveClearingProfit(clearShareProfitVo);
		} catch (Exception e) {
			logger.error("ClearingProfitDetailServiceImpl saveClearShareProfitRecord has a error{根据交易传过来的信息更新商户侧清算表和入库FIND_ERROR}", e);
		}
	}
//	/**
//	 * 
//	 * @方法说明：存储分润数据
//	 * @author xuangang
//	 * @param clearShareProfitVo
//	 * @throws Exception
//	 * @时间：2016年12月21日下午5:52:40
//	 */
//	private void saveClearingProfit(ClearShareProfitModel clearShareProfitVo) throws Exception{
//		//验证对象不为空
//		if(null != clearShareProfitVo){
//			//用于查询
//			ClearMerchantRecord clearMerchantRecord = new ClearMerchantRecord();
//			if(0 != clearShareProfitVo.getMerchantId()){//商户侧id
//				clearMerchantRecord.setMerchantId(Integer.valueOf(clearShareProfitVo.getMerchantId()));
//			}
//			if(StringUtils.isNotBlank(clearShareProfitVo.getTransNo())){//交易订单号
//				clearMerchantRecord.setTransNo(clearShareProfitVo.getTransNo());
//			}
//			List<ClearShareProfitDetailModel> profitDetailList = clearShareProfitVo.getClearShareProfitDetail();//分润明细
//			if(null != profitDetailList && profitDetailList.size() > 0){
//				//循环分润明细
//				for(ClearShareProfitDetailModel profitDetail : profitDetailList){
//					try {
//						//保存分润明细
//						saveClearingProfitDetail(profitDetail,clearShareProfitVo.getShareId());
//					} catch (Exception e) {
//						logger.error("ClearingProfitDetailServiceImpl clearingProfitRecordMapper has a error {分润明细入库出错 FIND_ERROR}", e);
//						throw new Exception();
//					}
//				}
//			}
//			logger.info("ClearingProfitDetailServiceImpl clearingProfitRecordMapper has a {分润明细入库  FIND_SUCCESS}", "交易订单号为："+clearShareProfitVo!=null?clearShareProfitVo.getTransNo():"clearShareProfitVo 对象为空"+"完成！共"+profitDetailList.size()+"条！");
//			//更新商户侧清算记录中的是否分润字段
////			updateClearMerChantRecordIsProfitByMerChantIdAndTransNo(clearShareProfitVo, clearMerchantRecord);
//		}
//	}
}
