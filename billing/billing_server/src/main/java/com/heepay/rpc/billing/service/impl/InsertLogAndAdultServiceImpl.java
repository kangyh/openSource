package com.heepay.rpc.billing.service.impl;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.billing.dao.SettleChannelLogMapper;
import com.heepay.billing.entity.SettleChannelLog;
import com.heepay.billingutils.ClarBatch;
import com.heepay.common.util.DateUtil;
import com.heepay.enums.billing.BillingCheckStatus;
import com.heepay.rpc.billing.service.IBillPutInStorageService;
import com.heepay.rpc.billing.service.IInsertLogAndAdultService;

/**
 * *
 * 
 * 
 * 描 述：对账文件写入日记和解析
 *
 * 创 建 者： wangjie 创建时间： 2016年11月23日上午11:28:12 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Component
public class InsertLogAndAdultServiceImpl implements IInsertLogAndAdultService {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	SettleChannelLogMapper settleChannelLogDaoImpl;

	@Autowired
	IBillPutInStorageService billPutInStorageImpl;

	@Transactional
	@Override
	public boolean insertAndAdult(String checkFileWhere, String checkFileFrom, String channelCode, 
			String channelType, String fileName, String localFilePath, String  settleWay,String ruleType, String format) {
		boolean f;
		//String format = DateUtil.getTodayYYYYMMDD();
		
		//插入日记表再判断一次文件是否下载过
		String file = localFilePath + format + "/" + fileName;
		if (settleChannelLogDaoImpl.fingSettleChannelLog(file)) {
			logger.info("文件已下载过" + file);
			return false;
		} else {
			SettleChannelLog settle = new SettleChannelLog();
			String checkBathno = ClarBatch.getRandomString(0); // 生成对账批次号
			settle.setChannelCode(channelCode); // 通道编码
			settle.setChannelType(channelType); // 通道类型
			settle.setCheckStatus(BillingCheckStatus.CHECKSTATUSWS.getValue()); // 对账状态
			settle.setCheckBathNo(checkBathno); // 对账批次
			settle.setOperBeginTime(new Date()); // 开始时间
			settle.setCheckFileName(localFilePath + format + "/" + fileName); // 文件名(目录+文件名)
			
			settle.setCheckFileWhere(checkFileWhere);  //文件存储位置
			settle.setCheckFileFrom(checkFileFrom);    //文件来源
			settle.setRuleType(ruleType);    //规则类型  COMM 一代规则 SPECIAL 二代规则

			// 把下载好的文件信息插入日记表
			settleChannelLogDaoImpl.insert(settle);
			logger.info("下载文件已经写入日记,文件为" + fileName);

			try {
				// 通知开始解析文件
				boolean fg = billPutInStorageImpl.billPutInStorage(channelCode, channelType, checkBathno,
						localFilePath + format + "/" + fileName, settleWay,ruleType);
				if (!fg) {
					return fg;
				}

				f = true;
			} catch (Exception e) {
				logger.error("对账出现异常:" , e);
				f = false;
			}
			return f;
		}
	}

}
