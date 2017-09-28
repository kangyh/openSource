package com.heepay.rpc.billing.service.impl.clear;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.dao.SettleDicItemMapper;
import com.heepay.billing.dao.SettleDicTypeMapper;
import com.heepay.billing.dao.SettleMerchantRecordMapper;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.entity.SettleDicItem;
import com.heepay.billing.entity.SettleDicType;
import com.heepay.billing.entity.SettleMerchantRecord;
import com.heepay.billing.entity.SettleMerchantRecordVo;
import com.heepay.billingutils.IdBatch;
import com.heepay.billingutils.util.Constants;
import com.heepay.date.DateUtils;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.BillingBecomeEffect;
import com.heepay.enums.billing.BillingYCheckStatus;
import com.heepay.enums.billing.SettleDicTypeEnum;
import com.heepay.enums.billing.SettleInterval;
import com.heepay.rpc.billing.producer.SettleMerchatProducerSender;
import com.heepay.vo.ClearMerchantDetailMessage;

/***
 * 
 * 
 * 描 述：两小时跑批汇总t+0消费实时以及用户侧的批量查询和插入
 *
 * 创 建 者： wangl 创建时间： 2016年9月12日下午6:11:19 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述： ******
 */
@Service
public class MethodTransactional2 {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private SettleMerchantRecordMapper settleMerchantRecordDao;

	@Autowired
	private ClearMerchantRecordMapper clearMerchantRecordDao;

	@Autowired
	private SettleMerchatProducerSender settleMerchatProducerSender;

	@Autowired
	private SettleDicTypeMapper settleDicTypeMapper;

	@Autowired
	private SettleDicItemMapper settleDicItemMapper;

	/**
	 * 
	 * @方法说明：两小时跑批汇总t+0 2:00:00之后
	 * 
	 * @时间：Nov 7, 2016
	 * @创建人：wangl
	 */

	@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
	public void getClearMerchantRecordMessage() throws TException {

		try {

			// ClearMerchantRecordModel clearMerchantModel = new ClearMerchantRecordModel();
			ClearMerchantRecord model = new ClearMerchantRecord();
			// 定时查询实时的数据
			model.setTransType(TransType.PAY.getValue());// 交易类型
			model.setSettleStatus(Constants.SETTLE_STATUS_N); // 结算状态（N：未结算，D：结算中）
			model.setCheckStatus(Constants.CHECK_STATUS_N); // 未对账
			model.setSettleCyc(Constants.T0); // t+0
			model.setCheckFlg(BillingYCheckStatus.BCFQSTS.getValue());// 对账标记

			// 查询结账区间右侧时间
			SettleDicType settleDicType = new SettleDicType();
			settleDicType.setCode(SettleDicTypeEnum.SETTLEAREA.getValue());
			settleDicType.setStatus(BillingBecomeEffect.BBEY.getValue());

			SettleDicType selectByPrimaryKey = settleDicTypeMapper.selectByPrimaryKey(settleDicType);// queryItemByTypeCode
			SettleDicType settleDic = new SettleDicType();
			settleDic.setTypeId(selectByPrimaryKey.getTypeId());
			settleDic.setStatus(BillingBecomeEffect.BBEY.getValue());

			List<SettleDicItem> select = settleDicItemMapper.queryItem(settleDic);
			// SettleDicItem select2 = settleDicItemMapper.queryItemByTypeCode(settleDicType);

			// 查询出当日2:00之后的
			logger.info("查询出当日--2:00--之后的开始");
			for (SettleDicItem settleDicItem : select) {

				if (SettleInterval.NTERVALMV.getValue().equals(settleDicItem.getText())) {// merchant

					String dateType = "yyyy-MM-dd " + settleDicItem.getValue();
					String format = new SimpleDateFormat(dateType).format(DateUtils.getInternalDateByDay(new Date(), 1));// 当前时间加一天  getNextDay(new  Date())
					Date date;
					try {
						date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
						model.setSuccessTime(date);// 支付成功时间
					} catch (ParseException e) {
						// 时间转换失败
					}

				}
			}

			logger.info("商户侧t+0汇总清算记录开始！");
			// 查询出清算表中实时的消费
			//ClearMerchantRecord record = new ClearMerchantRecord();
			//// ClearMerchantRecord record2 =new ClearMerchantRecord();
			//record.setSettleStatus(model.getSettleStatus());
			//record.setTransType(model.getTransType());
			//record.setCheckStatus(model.getCheckStatus());
			//record.setSettleCyc(model.getSettleCyc());
			//record.setCheckFlg(model.getCheckFlg());

			try {
				Date date = model.getSuccessTime();
				String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
				model.setSuccessTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format));
			} catch (ParseException e) {
				logger.error("时间类型转换失败", e);
			}

			// 查询出t+0 2:00之后的的批次
			List<SettleMerchantRecordVo> list = clearMerchantRecordDao.queryClearChant(model);
			int i = list.size();// 跑批数据条数
			logger.info("商户侧生成结算批次共" + i + "条!");
			if (i > 0) {
				
				// 保存到结算表中
				for (SettleMerchantRecordVo settleMerchanyRecordVo : list) {
					try {
						meThodTransactional2(model, settleMerchanyRecordVo);

					} catch (Exception e) {
						logger.error("商户侧的汇总发送失败", e);
					}
				}
			} else {
				logger.info("没有查询到当日2:00之后的t+0的数据！");
			}
		} catch (NumberFormatException e) {
			logger.error("商户侧汇总清算记录异常", e);
		}

	}

	/**
	 * 
	 * @方法说明：批量查询出当日2:00之后的
	 * @时间：Nov 8, 2016
	 * @创建人：wangl
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { RuntimeException.class, Exception.class })
	private void meThodTransactional2(ClearMerchantRecord model, SettleMerchantRecordVo settleMerchanyRecordVo) {

		// 保持更新条件的对象
		ClearMerchantRecord record2 = new ClearMerchantRecord();
		SettleMerchantRecord settleMerchantlRecord = new SettleMerchantRecord();

		try {
			settleMerchantlRecord.setMerchantId(Integer.parseInt(settleMerchanyRecordVo.getMerchantId()));// 用户ID

			settleMerchantlRecord.setTransType(settleMerchanyRecordVo.getTransType());// 业务类型（产品编码）
			settleMerchantlRecord.setSettleAmount(settleMerchanyRecordVo.getSettleAmountPlan());

			settleMerchantlRecord.setTotalFee(settleMerchanyRecordVo.getTotalFee());// 总交易手续费
			// settleMerchanyRecordVo.getTotalFee()
			settleMerchantlRecord.setPayNum(settleMerchanyRecordVo.getPayNum());// 交易总笔数
			settleMerchantlRecord.setTotalAmount(settleMerchanyRecordVo.getTotalAmount());

			settleMerchantlRecord.setCheckTime(DateUtils.getStrDate(new Date(), "yyyy-MM-dd"));

			settleMerchantlRecord.setSettleBath(IdBatch.getRandomString(1));
			settleMerchanyRecordVo.setSettleBath(settleMerchantlRecord.getSettleBath());// 生成结算批次号返回
			settleMerchantlRecord.setCurrency(Constants.CURRENCY_RMB);
			settleMerchantlRecord.setSettleStatus(Constants.SETTLE_STATUS_D); // 结算状态是结算中（N：未结算，D：结算中  Y：已结算）
			settleMerchantlRecord.setCheckStatus(Constants.SETTLE_STATUS_N); // 结算状态（N：未结算，D：结算中  Y：已结算）
			settleMerchantlRecord.setSettleCyc(Constants.T0);
			settleMerchantlRecord.setMerchantName(settleMerchanyRecordVo.getMerchantName()); //商户名称
			// 用户结算表插入
			settleMerchantRecordDao.insert(settleMerchantlRecord);

			record2.setSettleBath(settleMerchantlRecord.getSettleBath());// 插入查询结算批次
			record2.setSettleStatus(model.getSettleStatus());
			record2.setMerchantId(settleMerchantlRecord.getMerchantId());
			record2.setTransType(settleMerchantlRecord.getTransType());
			//record2.setMerchantType(settleMerchantlRecord.getMerchantType());
			record2.setSuccessTime(model.getSuccessTime());

			record2.setSettleCyc(Constants.T0);
			record2.setCheckFlg(BillingYCheckStatus.BCFQSTS.getValue());
			record2.setCheckStatus(Constants.CHECK_STATUS_N);
			record2.setClearingId(settleMerchanyRecordVo.getClearingId());

			clearMerchantRecordDao.updateClearMerchantRecord(record2);

			// ClearMerchantRecord clearMerchantRecord= clearMerchantRecordDao.getSettleMerchantl(record2);根据批次号和商户id获取手续费扣除方式
			SettleMerchantRecord settleMerchantlCheck = new SettleMerchantRecord();
			// settleMerchantlCheck.setFeeWay(clearMerchantRecord.getFeeWay());设置手续费扣除方式
			settleMerchantlCheck.setSettleTime(DateUtils.getStrDate(new Date(), "yyyy-MM-dd"));
			settleMerchantlCheck.setSettleBath(settleMerchantlRecord.getSettleBath());

			// 用户结算表更新
			settleMerchantRecordDao.upateByFeeWay(settleMerchantlCheck);

			// 查询商户侧结算表
			SettleMerchantRecord settleDetail = settleMerchantRecordDao
					.querySettleMerchantByBatchNo(settleMerchantlRecord.getSettleBath());
			// 根据批次号查询清算明细
			List<ClearMerchantDetailMessage> clearMessageList = clearMerchantRecordDao.queryClearDetailBySettleBatch(settleMerchantlRecord.getSettleBath());

			// 汇总后的结算批次+清算明细
			SettleMerchantMessage settleMessage = new SettleMerchantMessage();
			settleMessage.setSettleMerchantRecord(settleDetail); // 结算批次
			settleMessage.setClearMerchantMessage(clearMessageList); // 清算明细

			// 将结算数据发生到队列
			settleMerchatProducerSender.sendDataToQueue(settleMessage);
			logger.info("商户侧汇总清算记录结束！----->{}" + settleMessage.getSettleMerchantRecord().toString());
		} catch (Exception e) {
			logger.error("查询出当日2:00之后的查询错误", e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	// 按照交易的规则发送汇总的数据实体对象+list
	private class SettleMerchantMessage implements Serializable {

		private static final long serialVersionUID = 4946173065449810591L;
		// 结算记录
		private SettleMerchantRecord settleMerchantRecord;
		// 清算明细
		private List<ClearMerchantDetailMessage> clearMerchantMessage;

		public SettleMerchantRecord getSettleMerchantRecord() {
			return settleMerchantRecord;
		}

		public void setSettleMerchantRecord(SettleMerchantRecord settleMerchantRecord) {
			this.settleMerchantRecord = settleMerchantRecord;
		}

		@SuppressWarnings("unused")
		public List<ClearMerchantDetailMessage> getClearMerchantMessage() {
			return clearMerchantMessage;
		}

		public void setClearMerchantMessage(List<ClearMerchantDetailMessage> clearMerchantMessage) {
			this.clearMerchantMessage = clearMerchantMessage;
		}

		public String toString() {
			return "SettleMerchantMessage [settleMerchantRecord=" + settleMerchantRecord + ", clearMerchantMessage="+ clearMerchantMessage + "]";
		}

	}
}
