package com.heepay.rpc.billing.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.csvreader.CsvReader;
import com.heepay.billing.dao.SettleBillRecordMapper;
import com.heepay.billing.dao.SettleChannelLogMapper;
import com.heepay.billing.dao.SettleChannelManagerMapper;
import com.heepay.billing.dao.SettleDifferBillRecordMapper;
import com.heepay.billing.dao.SettleRegexControlMapper;
import com.heepay.billing.dao.SettleRuleControlMapper;
import com.heepay.billing.dao.SettleRuleSecondMapper;
import com.heepay.billing.entity.SettleBillRecord;
import com.heepay.billing.entity.SettleChannelLog;
import com.heepay.billing.entity.SettleChannelManager;
import com.heepay.billing.entity.SettleDifferBillRecord;
import com.heepay.billing.entity.SettleRegexControl;
import com.heepay.billing.entity.SettleRuleControl;
import com.heepay.billing.entity.SettleRuleSecond;
import com.heepay.common.util.StringEncrypt;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.enums.ChannelPartner;
import com.heepay.enums.ChannelType;
import com.heepay.enums.billing.BillingBillStatus;
import com.heepay.enums.billing.BillingCheckStatus;
import com.heepay.enums.billing.BillingRuleManageType;
import com.heepay.rpc.billing.enums.SettleDifferType;
import com.heepay.rpc.billing.service.IBillPutInStorageService;
import com.heepay.vo.RegexVo;

/**
 * 
 * 
 * 描 述：账单入库实现类
 *
 * 创 建 者：chenyanming 
 * 创建时间： 2016年9月23日下午4:20:16 
 * 创建描述：
 * 
 * 修 改 者： 
 * 修改时间： 20170103
 * 修改描述：
 * 
 * 审 核 者： 
 * 审核时间： 
 * 审核描述：
 *
 */
@Component
public class BillPutInStorageImpl implements IBillPutInStorageService {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	SettleChannelManagerMapper settleChannelManagerDao;
	@Autowired
	SettleRuleControlMapper settleRuleControlDao;
	@Autowired
	SettleRuleSecondMapper settleRuleSecondDao;
	@Autowired
	SettleBillRecordMapper settleBillRecordDao;
	@Autowired
	SettleChannelLogMapper settleChannelLogDao; 
	@Autowired
	SettleRegexControlMapper settleRegexControlDao;
	@Autowired
	SettleDifferBillRecordMapper SettleDifferBillRecordDao;
	@Autowired
	RegexVo regexVo;

	/**
	 * 
	 * @方法说明：账单入库
	 * @author chenyanming
	 * @param channelCode
	 * @param channelType
	 * @时间：2016年9月23日下午4:27:09
	 */
	@Override
	@Transactional()
	public boolean billPutInStorage(String channelCode, String channelType, String checkBathno, String fileName, String settleWay, String ruleType) {
		boolean flg = true;
		try {
			// 查询对账日志，判断是否有符合条件的对账文件
			SettleChannelLog settleChannelLogVo = settleChannelLogDao.querySettleChannelLog(channelCode, channelType, checkBathno, BillingCheckStatus.CHECKSTATUSWS.getValue());
			if(settleChannelLogVo == null) {
				logger.error("没有符合条件的对账文件,对账批次号为:{}", checkBathno);
				flg = false;
				return flg;
			}
			// 1、检查是否符合通道对账规则
			List<SettleChannelManager> list = settleChannelManagerDao.checkSettleChannelManagerRule(channelCode,
					channelType, settleWay, ruleType);
			if (list == null || list.size() == 0) {
				logger.error("无此通道对账规则或此通道对账规则已作废,对账批次号为:{}", checkBathno);
				flg = false;
				return flg;
			}
			
			// 2、获取账单明细的规则表达式
			String billType = null;
			if (fileName.endsWith(".txt")) {
				billType = BillingRuleManageType.BRULETYPET.getValue();
			}else if(fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
				billType = BillingRuleManageType.BRULETYPEE.getValue();
			}else if(fileName.endsWith(".csv")) {
				billType = BillingRuleManageType.BRULETYPEC.getValue();
			}else {
				billType = BillingRuleManageType.BRULETYPEO.getValue();
			}
			SettleRuleControl settleRuleControl = null;
			SettleRuleSecond settleRuleSecond = null;
			if("COMM".equals(ruleType)) {
				// 一代规则
				settleRuleControl = settleRuleControlDao.getSettleRuleControl(channelCode, channelType, billType, settleWay);
				if (settleRuleControl == null) {
					logger.error("无此控制规则或此规则已作废,对账批次号为:{}", checkBathno);
					flg = false;
					return flg;
				}
			}else if("SPECIAL".equals(ruleType)) {
				// 二代规则
				settleRuleSecond = settleRuleSecondDao.getSetlleRuleSecond(channelCode, channelType, billType, settleWay);
				if (settleRuleSecond == null) {
					logger.error("无此控制规则或此规则已作废,对账批次号为:{}", checkBathno);
					flg = false;
					return flg;
				}
			}
			// 对账文件存在，开始入库，修改log日志状态为入库处理中
			settleChannelLogVo.setCheckStatus(BillingCheckStatus.CHECKSTATUSWSD.getValue());
			settleChannelLogDao.updateSettleChannelLog(settleChannelLogVo);
			
			logger.info("账单入库开始！,对账批次号为:{}", checkBathno);
	
			// 3、获取账单明细数据
			List<SettleBillRecord> settleBillRecordList = null;
			if("COMM".equals(ruleType)) {
				settleBillRecordList = this.getSettleBillRecordData(fileName, settleRuleControl, channelCode, channelType);
			}else if("SPECIAL".equals(ruleType)) {
				settleBillRecordList = this.getSettleBillRecordData2(fileName, settleRuleSecond, channelCode, channelType, ruleType);
			}
			if (settleBillRecordList == null || settleBillRecordList.size() == 0) {
				SettleChannelLog settleChannelLog = new SettleChannelLog();
				settleChannelLog.setCheckStatus(BillingCheckStatus.CHECKSTATUSCD.getValue());
				settleChannelLog.setCheckBathNo(checkBathno);
				settleChannelLogDao.updateSettleChannelLog(settleChannelLog);
				logger.info("对账文件中没有符合的对账明细数据！,对账批次号为:{}", checkBathno);
				flg = false;
				return flg;
			}
			int toatlSize = settleBillRecordList.size();
			// 账单明细数据入库
			for (SettleBillRecord settleBillRecord : settleBillRecordList) {
				settleBillRecord.setChannelCode(channelCode);
				if("UNOPAY".equals(channelCode) && "QRCODE".equals(channelType) && StringUtils.isNotBlank(settleBillRecord.getPaymentId())) {
					settleBillRecord.setChannelType("B2CEBK");
				}else {
					settleBillRecord.setChannelType(channelType);
				}
				settleBillRecord.setBillStatus(BillingBillStatus.BBSTATUSN.getValue());
				settleBillRecord.setSaveTime(DateUtils.getDate());
				settleBillRecord.setCheckBathNo(checkBathno);
				// 根据支付单号查询，防止重复入库
				SettleBillRecord settleBillRecord1 = null;
				if(("UNOPAY".equals(channelCode) && "QRCODE".equals(settleBillRecord.getChannelType())) || 
						(settleBillRecord.getField1() != null)) {
					settleBillRecord1 = settleBillRecordDao.selectByChannelNo(settleBillRecord.getChannleNo());
				}else {
					settleBillRecord1 = settleBillRecordDao.selectByPaymentId(settleBillRecord.getPaymentId());
				}
				if(settleBillRecord1 != null) {
					try {
						// 将重复入库的数据入到异常表中
						this.insertIntoSettleDifferBillRecord(settleBillRecord, SettleDifferType.DIFFERTYPEF.getValue());
					} catch (Exception e) {
						logger.info("入库异常明细表出错", e);
					}
					logger.info("此条对账明细数据已入库，支付单号和上有流水号为{},{}" , settleBillRecord.getPaymentId(), settleBillRecord1.getChannleNo());
					toatlSize--;
					continue;
				}
				// 4、将账单明细入库
				settleBillRecordDao.insert(settleBillRecord);
			}
			logger.info("解析账单明细共" + toatlSize + "条数据入库");
			
			// 5、修改对账日志状态为对账中
			SettleChannelLog settleChannelLog = new SettleChannelLog();
			settleChannelLog.setCheckStatus(BillingCheckStatus.CHECKSTATUSCD.getValue());
			settleChannelLog.setCheckBathNo(checkBathno);
			settleChannelLogDao.updateSettleChannelLog(settleChannelLog);
		} catch (Exception e) {
			flg = false;
			logger.error("读取对账文件异常，账单明细入库失败！,对账批次号为:{}", checkBathno, e);
			throw new RuntimeException();
		}
		return flg;
	}

	/**
	 * 
	 * @方法说明：将重复入库的，冲正的单据入库到异常明细表中
	 * @author chenyanming
	 * @param settleBillRecord
	 * @param settleBillRecord1
	 * @时间：2017年7月27日下午2:31:45
	 */
	private void insertIntoSettleDifferBillRecord(SettleBillRecord settleBillRecord, String differType) {
		SettleDifferBillRecord settleDifferBillRecord = new SettleDifferBillRecord();
		settleDifferBillRecord.setChannelCode(settleBillRecord.getChannelCode());
		settleDifferBillRecord.setChannelType(settleBillRecord.getChannelType());
		settleDifferBillRecord.setCheckBathNo(settleBillRecord.getCheckBathNo());
		settleDifferBillRecord.setPaymentId(settleBillRecord.getPaymentId());
		settleDifferBillRecord.setSaveTime(DateUtils.getDate());
		settleDifferBillRecord.setSuccessAmount(settleBillRecord.getSuccessAmount());
		settleDifferBillRecord.setFee(settleBillRecord.getFee());
		settleDifferBillRecord.setChannleNo(settleBillRecord.getChannleNo());
		settleDifferBillRecord.setDifferType(differType);
		settleDifferBillRecord.setFeeService(settleBillRecord.getFeeService());
		settleDifferBillRecord.setPromotionAmount(settleBillRecord.getPromotionAmount());
		settleDifferBillRecord.setRemark(settleBillRecord.getRemark());
		SettleDifferBillRecord settleDifferBillRecord1 = SettleDifferBillRecordDao.selectByPaymentId(settleDifferBillRecord.getPaymentId());
		if(settleDifferBillRecord1 != null) {
			return;
		}
		SettleDifferBillRecordDao.insert(settleDifferBillRecord);
	}

	/**
	 * 
	 * @方法说明：二代对账规则解析
	 * @author chenyanming
	 * @param fileName
	 * @param settleRuleSecond
	 * @param channelCode
	 * @param channelType
	 * @return
	 * @时间：2017年5月9日下午2:08:50
	 */
	private List<SettleBillRecord> getSettleBillRecordData2(String fileName, SettleRuleSecond settleRuleSecond,
			String channelCode, String channelType, String ruleType) {
		File file = new File(fileName);
		try {
			// 判断文件是否存在
			if (file.isFile() && file.exists()) {
				List<SettleBillRecord> list = new ArrayList<SettleBillRecord>();
				List<String> paymentIdRegexList = null;
				List<String> bankSeqRegexList = null;
				if (settleRuleSecond.getBillType().equals(BillingRuleManageType.BRULETYPET.getValue())) {
					// TXT文件
					logger.info("二代规则解析，读取TXT文件开始，文件为:{}" + file);
					// 获取指定文件的编码格式
					String code = this.getFilecharset(file);
					
					// 编码格式，以后改动
					InputStreamReader read = new InputStreamReader(new FileInputStream(file),code);//GB2312
					try {
						BufferedReader bufferedReader = new BufferedReader(read);
						String lineTxt = null;
						int i = 1; // 用来判断开始行号
						
						String splitFlg = null;
						if(!settleRuleSecond.getSuccessAmount().contains("-")) {
							// 如果是按位读取，不需要分隔符；否则需要分隔符
							if(settleRuleSecond.getSplitFlg() == null) {
								logger.error("获取分隔符出错:{}" + settleRuleSecond);
								bufferedReader.close();
								return null;
							}else if("".equals(settleRuleSecond.getSplitFlg())) {
								splitFlg = "\\s+";
							}else {
								splitFlg = "\\" + settleRuleSecond.getSplitFlg();
							}
							
							paymentIdRegexList = this.getRegexShowData(settleRuleSecond.getPaymentId() == null ? null:(byte) Integer.parseInt(settleRuleSecond.getPaymentId()), settleRuleSecond.getRuleId(), "SPECIAL");
							bankSeqRegexList = this.getRegexShowData(settleRuleSecond.getBankSeq() == null ? null:(byte) Integer.parseInt(settleRuleSecond.getBankSeq()), settleRuleSecond.getRuleId(), "SPECIAL");
							
						}
						
						// 开始逐行读取明细数据
						while ((lineTxt = bufferedReader.readLine()) != null) {
							// 获取明细数据从开始行号处读取
							if (i < settleRuleSecond.getStartRow()) {
								i++;
								continue;
							}
							if(splitFlg == null) {
								// 按位读取
								int end = this.getSettleBillRecordListSpecial(list, lineTxt, settleRuleSecond, channelCode, channelType);
								// end是结束标志，1代表读取到结束标志或读取到空行，0代表继续循环下一条数据
								if(end == 1) {
									break;
								}
							}else {
								// 按一代规则逻辑解析
								// 按分隔符分割明细记录，得到明细数据的字符数组
								String[] data = lineTxt.split(splitFlg);
								int end = this.getSettleBillRecordListComm(list, data, settleRuleSecond, channelCode, channelType,
										paymentIdRegexList, bankSeqRegexList);
								// end是结束标志，1代表读取到结束标志或读取到空行，0代表继续循环下一条数据
								if(end == 1) {
									break;
								}
							}
						}
						bufferedReader.close();
						logger.info("二代规则解析，读取TXT文件结束" + file);
						return list;
					} catch (Exception e) {
						logger.error("二代规则解析，读取TXT文件出错" , file, e);
						throw new RuntimeException();
					} finally {
						read.close();
					}
				}else if(settleRuleSecond.getBillType().equals(BillingRuleManageType.BRULETYPEE.getValue())) {
					// 解析xls文件
					logger.info("二代规则解析，读取EXCEL文件开始，文件为:{}" + file);
					try {
						Workbook book = null;
						if (fileName.endsWith(".xls")) {
							// 获取操作xls文件的对象
							book = new HSSFWorkbook(new FileInputStream(file));
				        } else if (fileName.endsWith(".xlsx")) {
				        	// 获取操作xlsx文件的对象
				        	book = new XSSFWorkbook(new FileInputStream(file));
				        }

						// 获取操作xls文件的对象
						//Workbook book = new HSSFWorkbook(new FileInputStream(file));
						// 遍历所有的sheet
						//for (int i = 0; i < book.getNumberOfSheets(); i++) {
						Sheet sheet = book.getSheetAt(0);
						
						// 量化派退款对账文件解析
						int flag = 0;  // 0 是非退款     ；1 是退款对账
						if("QUAGRP".equals(channelCode) && "TRMPAY".equals(channelType)) {
							Row row = sheet.getRow(0);
							if(row.getCell(0) != null && row.getCell(0).toString().startsWith("退款")) {
								flag = 1;
							}
						}
						
						// 遍历所有的行
						for (int rowIndex = settleRuleSecond.getStartRow() - 1, lastNum = sheet.getLastRowNum(); rowIndex <= lastNum; rowIndex++) {
							SettleBillRecord settleBillRecord = new SettleBillRecord();
							Row row = sheet.getRow(rowIndex);
							// 读取到空行数据
							if(row == null) {
								break;
							}
							if(row.getCell(0) != null){
					            //row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					            //stuUser.setPhone(row.getCell(0).getStringCellValue());
					            String cell = row.getCell(0).toString();
					            //HSSFCell cell = row.getCell(0);
					            if(settleRuleSecond.getEndFlg() != null) {
					        	    // 判断是否是结束标志，遇到结束标志，直接跳出循环
					         	    if (cell == null || cell.equals(settleRuleSecond.getEndFlg())) {
					        		    break;
					        	    }
					            }
					            // 判断是否为空
					            if(!ChannelPartner.BILPAY.getValue().equals(channelCode)) {
					            	if(StringUtils.isBlank(cell)) {
					            		break;
					            	}
					            }
						    }else {
						    	break;
						    }
							// 判断是否是指定状态的数据
							if(StringUtils.isNotBlank(settleRuleSecond.getTransStatus())) {
								String[] split = settleRuleSecond.getTransStatus().split("\\.");
								String transStatus = row.getCell(Integer.parseInt(split[0]) - 1).toString();
								if(StringUtils.isNotBlank(transStatus)) {
									/*if(!this.matchRegex(transStatus, transStatusRegexList)) {
										continue;
									}*/
									int sign = 1; // 0代表包含，1代表不包含
									String[] statusList = split[1].split("#");
									for(int i = 0; i < statusList.length; i++) {
										if(transStatus.contains(statusList[i])) {
											sign = 0;
										}
									}
									if(sign == 1) {
										continue;
									}
								}
							}
							// 获取上有流水号
							if(StringUtils.isNotBlank(settleRuleSecond.getBankSeq())) {
								String channelNo = this.getSpecificData(row.getCell(Integer.parseInt(settleRuleSecond.getBankSeq()) - 1).toString());
								if(StringUtils.isNotBlank(channelNo)) {
									if(!this.matchRegex(channelNo, bankSeqRegexList)) {
										continue;
									}
									settleBillRecord.setChannleNo(channelNo);
								}
							}
							// 判断交易单号是否为空，如果为空，跳过不入库
							if(StringUtils.isNotBlank(settleRuleSecond.getPaymentId())) {
								
								Cell paymentIdData = row.getCell(Integer.parseInt(settleRuleSecond.getPaymentId()) - 1);
								if(paymentIdData != null) {
									if(StringUtils.isBlank(paymentIdData.toString())) {
										logger.error("交易单号为空，上游流水号为:{}" + settleBillRecord.getChannleNo());
										continue;
									}
								}else {
									logger.error("交易单号为空，上游流水号为:{}" + settleBillRecord.getChannleNo());
									continue;
								}
								// 获取到的数据默认会带有小数，这里是将小数部分去掉
								//String paymentId = paymentIdData.toString();
								String paymentId = this.getSpecificData(paymentIdData.toString());
								if(!this.matchRegex(paymentId, paymentIdRegexList)) {
									continue;
								}
								if(paymentId.contains(".")) {
									try {
										double parseDouble = Double.parseDouble(paymentId);
										DecimalFormat df = new DecimalFormat("#");
										paymentId = df.format(parseDouble);
									} catch (Exception e) {
										logger.error("格式化支付单号出错，单号为:{}", paymentId);
									}
								}
								settleBillRecord.setPaymentId(paymentId);
							}
							// 保留两位小数
							DecimalFormat df = new DecimalFormat("#.00");
							// 获取手续费
							if(StringUtils.isNotBlank(settleRuleSecond.getFeeBank())) {
								String[] split = settleRuleSecond.getFeeBank().split("\\.");
								//String feeBank = this.getSpecificData(row.getCell(Integer.parseInt(split[0]) - 1).toString());
								double feeBank = this.getAmountCommXls(row, split);
								settleBillRecord.setFee(new BigDecimal(df.format(feeBank)).abs());
							}
							// 获取服务费
							if(StringUtils.isNotBlank(settleRuleSecond.getFeeService())) {
								String[] split = settleRuleSecond.getFeeService().split("\\.");
								double feeService = this.getAmountCommXls(row, split);
								settleBillRecord.setFeeService(new BigDecimal(df.format(feeService)).abs());
							}
							// 获取成功支付金额
							if(StringUtils.isNotBlank(settleRuleSecond.getSuccessAmount())) {
								if(StringUtils.isNotBlank(settleRuleSecond.getFeeFree())) { // 优惠金额
									String[] split = settleRuleSecond.getFeeFree().split("\\.");
									double feeFree = this.getAmountCommXls(row, split);
									
									String[] split1 = settleRuleSecond.getSuccessAmount().split("\\.");
									double successAmount = this.getAmountCommXls(row, split1);
									settleBillRecord.setPromotionAmount(new BigDecimal(df.format(feeFree)).abs());
									settleBillRecord.setSuccessAmount(new BigDecimal(df.format(successAmount - Math.abs(feeFree))).abs());
								}else {
									String SuccessAmount;
									if(flag == 1) { // 量化派退款单据
										SuccessAmount = "3.0";
									}else {
										SuccessAmount = settleRuleSecond.getSuccessAmount();
									}
									String[] split = SuccessAmount.split("\\.");
									double successAmount = this.getAmountCommXls(row, split);
									settleBillRecord.setSuccessAmount(new BigDecimal(df.format(successAmount)).abs());
								}
							}
							// 获取摘要字段值
							if(StringUtils.isNotBlank(settleRuleSecond.getRemark())) {
								String remark = this.getSpecificData(row.getCell(Integer.parseInt(settleRuleSecond.getRemark()) - 1).toString());
								//remark = Sha.encode(remark.getBytes("UTF-8"), "SHA256");
								remark = StringEncrypt.Encrypt(remark, "SHA-256");
								settleBillRecord.setRemark(remark);
							}
							
							// 交易金额入库为0，鉴权交易，不入库
							if((settleBillRecord.getSuccessAmount().compareTo(new BigDecimal(0))) == 0) {
								continue;
							}
							list.add(settleBillRecord);
						}
						
						logger.info("二代规则解析，读取EXCEL文件结束" + file);
						return list;
					} catch (Exception e) {
						logger.error("二代规则解析，读取EXCEL文件出错" , file, e);
						throw new RuntimeException();
					}
					
				}else if(settleRuleSecond.getBillType().equals(BillingRuleManageType.BRULETYPEC.getValue())) {
					// csv文件
					logger.info("二代规则解析，读取CSV文件开始，文件为:{}" + file);
					// 获取指定文件的编码格式
					String code = this.getFilecharset(file);
					
					// 编码格式，以后改动
					InputStreamReader read = new InputStreamReader(new FileInputStream(file),code);//GB2312
					try {
						BufferedReader bufferedReader = new BufferedReader(read);
						CsvReader creader = new CsvReader(bufferedReader, ',');
						String lineTxt = null;
						int i = 1; // 用来判断开始行号
						String splitFlg = null;
						if(!settleRuleSecond.getSuccessAmount().contains("-")) {
							// 如果是按位读取，不需要分隔符；否则需要分隔符
							if(settleRuleSecond.getSplitFlg() == null) {
								logger.error("获取分隔符出错:{}" + settleRuleSecond);
								bufferedReader.close();
								return null;
							}else if("".equals(settleRuleSecond.getSplitFlg())) {
								splitFlg = "\\s+";
							}else {
								splitFlg = "\\" + settleRuleSecond.getSplitFlg();
							}
						}
						// 开始逐行读取明细数据
						while (creader.readRecord()) {
							// 获取明细数据从开始行号处读取
							if (i < settleRuleSecond.getStartRow()) {
								i++;
								continue;
							}
							lineTxt = creader.getRawRecord();
							if(splitFlg == null) {
								// 按位读取
								int end = this.getSettleBillRecordListSpecial(list, lineTxt, settleRuleSecond, channelCode, channelType);
								// end是结束标志，1代表读取到结束标志或读取到空行，0代表继续循环下一条数据
								if(end == 1) {
									break;
								}
							}else {
								// 按一代规则逻辑解析
								// 按分隔符分割明细记录，得到明细数据的字符数组
								String[] data = lineTxt.split(splitFlg);
								int end = this.getSettleBillRecordListComm(list, data, settleRuleSecond, channelCode, channelType,
										paymentIdRegexList, bankSeqRegexList);
								// end是结束标志，1代表读取到结束标志或读取到空行，0代表继续循环下一条数据
								if(end == 1) {
									break;
								}
							}
						}
						logger.info("二代规则解析，读取CSV文件结束" + file);
						creader.close();
						return list;
					} catch (Exception e) {
						logger.error("二代规则解析，读取CSV文件出错" , file, e);
						throw new RuntimeException();
					} finally {
						read.close();
					}
					
				}else {
					// 其他文件
					// others 其他格式的文件，如无后缀名文件
					logger.info("二代规则解析，读取other文件开始，文件为:{}" + file);
					// 获取指定文件的编码格式
					String code = this.getFilecharset(file);
					
					// 编码格式，以后改动
					InputStreamReader read = new InputStreamReader(new FileInputStream(file),code);//GB2312
					try {
						BufferedReader bufferedReader = new BufferedReader(read);
						String lineTxt = null;
						int i = 1; // 用来判断开始行号
						String splitFlg = null;
						if(!settleRuleSecond.getSuccessAmount().contains("-")) {
							// 如果是按位读取，不需要分隔符；否则需要分隔符
							if(settleRuleSecond.getSplitFlg() == null) {
								logger.error("获取分隔符出错:{}" + settleRuleSecond);
								bufferedReader.close();
								return null;
							}else if("".equals(settleRuleSecond.getSplitFlg())) {
								splitFlg = "\\s+";
							}else {
								splitFlg = "\\" + settleRuleSecond.getSplitFlg();
							}
						}
						
						// 开始逐行读取明细数据
						while ((lineTxt = bufferedReader.readLine()) != null) {
							// 获取明细数据从开始行号处读取
							if (i < settleRuleSecond.getStartRow()) {
								i++;
								continue;
							}
							if(splitFlg == null) {
								// 按位读取
								int end = this.getSettleBillRecordListSpecial(list, lineTxt, settleRuleSecond, channelCode, channelType);
								// end是结束标志，1代表读取到结束标志或读取到空行，0代表继续循环下一条数据
								if(end == 1) {
									break;
								}
							}else {
								// 按一代规则逻辑解析
								// 按分隔符分割明细记录，得到明细数据的字符数组
								String[] data = lineTxt.split(splitFlg);
								int end = this.getSettleBillRecordListComm(list, data, settleRuleSecond, channelCode, channelType,
										paymentIdRegexList, bankSeqRegexList);
								// end是结束标志，1代表读取到结束标志或读取到空行，0代表继续循环下一条数据
								if(end == 1) {
									break;
								}
							}
						}
						bufferedReader.close();
						logger.info("二代规则解析，读取other文件结束" + file);
						return list;
					} catch (Exception e) {
						logger.error("二代规则解析，读取other文件出错" , file, e);
						throw new RuntimeException();
					} finally {
						read.close();
					}
				}
			}else {
				logger.error("目标文件不存在" + file);
			}
		} catch (Exception e) {
			logger.error("读取文件内容出错" , file, e);
			throw new RuntimeException();
		}
		return null;
	}

	/**
	 * 
	 * @方法说明：xls格式的文件获取金额
	 * @author chenyanming
	 * @param row
	 * @param split
	 * @return
	 * @时间：2017年5月10日下午4:24:52
	 */
	private double getAmountCommXls(Row row, String[] split) {
		double amount = 0;
		String amount1 = this.getSpecificData(row.getCell(Integer.parseInt(split[0]) - 1).toString());
		if(StringUtils.isBlank(amount1)) {
			return amount;
		}
		if("0".equals(split[1])) { // 单位：元
			amount = Double.parseDouble(amount1);
		}else if("1".equals(split[1])) {
			//String amount1 = this.getSpecificData(data[Integer.parseInt(split[0]) - 1]);
			amount = Double.parseDouble(amount1)/100;
		}
		return amount;
	}

	/**
	 * 
	 * @方法说明：二代规则按照一代规则逻辑解析
	 * @author chenyanming
	 * @param list
	 * @param data
	 * @param settleRuleSecond
	 * @param channelCode
	 * @param channelType
	 * @param paymentIdRegexList
	 * @param transStatusRegexList
	 * @param successAmountRegexList
	 * @param feeServiceAmountRegexList
	 * @param feeBankAmountRegexList
	 * @param bankSeqRegexList
	 * @return
	 * @时间：2017年5月10日上午11:00:30
	 */
	private int getSettleBillRecordListComm(List<SettleBillRecord> list, String[] data,
			SettleRuleSecond settleRuleSecond, String channelCode, String channelType, List<String> paymentIdRegexList,
			List<String> bankSeqRegexList) {
		try {
			SettleBillRecord settleBillRecord = new SettleBillRecord();
			int dataSize = data.length;
			// 判断是否读取到结束标志
			if(StringUtils.isNotBlank(data[0])) {
				if(settleRuleSecond.getEndFlg() != null) {
					if(data[0].startsWith(settleRuleSecond.getEndFlg())) {
						// 读取到结束标志
						return 1;
					}
				}
			}else {
				// 读取到空行
				return 1;
			}
			
			// 获取指定交易状态的数据
			if(StringUtils.isNotBlank(settleRuleSecond.getTransStatus())) {
				String[] split = settleRuleSecond.getTransStatus().split("\\.");
				if(dataSize < Integer.parseInt(split[0])) {
					// 读取到非法数据
					return 0;
				}
				String transStatus = null;
				if("YSEPAY".equals(channelCode) && dataSize >= 11) {
					transStatus = data[9];
				}else {
					transStatus = data[Integer.parseInt(split[0]) - 1];
				}
				if(StringUtils.isNotBlank(transStatus)) {
					/*if(!this.matchRegex(transStatus, transStatusRegexList)) {
						return 0;
					}*/
					int sign = 1; // 0代表包含，1代表不包含
					String[] statusList = split[1].split("#");
					for(int i = 0; i < statusList.length; i++) {
						if(transStatus.contains(statusList[i])) {
							sign = 0;
						}
					}
					if(sign == 1) {
						return 0;
					}
				}else {
					logger.info("没有获取到交易状态字段");
					return 0;
				}
			}
			// 获取支付单号
			if(StringUtils.isNotBlank(settleRuleSecond.getPaymentId())) {
				//String[] split = settleRuleSecond.getPaymentId().split("\\.");
				if(dataSize < Integer.parseInt(settleRuleSecond.getPaymentId())) {
					// 读取到非法数据
					return 0;
				}
				//String paymentId = data[Integer.parseInt(split[0]) - 1];
				String paymentId = this.getSpecificData(data[Integer.parseInt(settleRuleSecond.getPaymentId()) - 1]);
				if(!this.matchRegex(paymentId, paymentIdRegexList)) {
					return 0;
				}
				settleBillRecord.setPaymentId(paymentId);
			}
			// 获取上游流水号
			if(StringUtils.isNotBlank(settleRuleSecond.getBankSeq())) {
				//String[] split = settleRuleSecond.getBankSeq().split("\\.");
				if(dataSize < Integer.parseInt(settleRuleSecond.getBankSeq())) {
					// 读取到非法数据
					return 0;
				}
				String bankSeq = this.getSpecificData(data[Integer.parseInt(settleRuleSecond.getBankSeq()) - 1]);
				if(!this.matchRegex(bankSeq, bankSeqRegexList)) {
					return 0;
				}
				settleBillRecord.setChannleNo(bankSeq);
				
			}
			// 保留两位小数
			DecimalFormat df = new DecimalFormat("#.00");
			// 获取交易金额
			if(StringUtils.isNotBlank(settleRuleSecond.getSuccessAmount())) {
				if(StringUtils.isNotBlank(settleRuleSecond.getFeeFree())) { // 优惠金额
					String[] split = settleRuleSecond.getFeeFree().split("\\.");
					double feeFree = 0;
					if(dataSize >= Integer.parseInt(split[0])) {
						feeFree = this.getAmountComm(data, split);
					}
					String[] split1 = settleRuleSecond.getSuccessAmount().split("\\.");
					if(dataSize < Integer.parseInt(split1[0])) {
						// 读取到非法数据
						return 0;
					}
					double successAmount = this.getAmountComm(data, split1);
					settleBillRecord.setPromotionAmount(new BigDecimal(df.format(feeFree)).abs());
					settleBillRecord.setSuccessAmount(new BigDecimal(df.format(successAmount - Math.abs(feeFree))).abs());
				}else {
					String[] split = settleRuleSecond.getSuccessAmount().split("\\.");
					if(dataSize < Integer.parseInt(split[0])) {
						// 读取到非法数据
						return 0;
					}
					double successAmount = 0;
					if("YSEPAY".equals(channelCode) && dataSize >= 11) {
						successAmount = Double.parseDouble(data[5]);
					}else {
						successAmount = this.getAmountComm(data, split);
					}
					settleBillRecord.setSuccessAmount(new BigDecimal(df.format(successAmount)).abs());
				}
			}
			// 获取服务费
			if(StringUtils.isNotBlank(settleRuleSecond.getFeeService())) {
				String[] split = settleRuleSecond.getFeeService().split("\\.");
				if(dataSize < Integer.parseInt(split[0])) {
					// 读取到非法数据
					return 0;
				}
				double feeService = this.getAmountComm(data, split);
				settleBillRecord.setFeeService(new BigDecimal(df.format(feeService)).abs());
			}
			// 获取手续费
			if(StringUtils.isNotBlank(settleRuleSecond.getFeeBank())) {
				String[] split = settleRuleSecond.getFeeBank().split("\\.");
				if(dataSize < Integer.parseInt(split[0])) {
					// 读取到非法数据
					return 0;
				}
				double feeBank = 0;
				if("YSEPAY".equals(channelCode) && dataSize >= 11) {
					feeBank = Double.parseDouble(data[8]);
				}else {
					feeBank = this.getAmountComm(data, split);
				}
				//double feeBank = this.getAmountComm(data, split);
				settleBillRecord.setFee(new BigDecimal(df.format(feeBank)).abs());
			}
			// 获取摘要信息
			if(StringUtils.isNotBlank(settleRuleSecond.getRemark())) {
				if(dataSize < Integer.parseInt(settleRuleSecond.getRemark())) {
					// 读取到非法数据
					return 0;
				}
				String remark = this.getSpecificData(data[Integer.parseInt(settleRuleSecond.getRemark()) - 1]);
				//remark = Sha.encode(remark.getBytes("UTF-8"), "SHA256");
				remark = StringEncrypt.Encrypt(remark, "SHA-256");
				settleBillRecord.setRemark(remark);
			}
			// 交易金额入库为0，鉴权交易，不入库
			if((settleBillRecord.getSuccessAmount().compareTo(new BigDecimal(0))) == 0) {
				return 0;
			}
			list.add(settleBillRecord);
			return 0;
			
		} catch (Exception e) {
			logger.error("获取数据失败" ,data , e);
			throw new RuntimeException();
		}
	}

	/**
	 * 
	 * @方法说明：二代规则用一代逻辑解析  获取金额方法
	 * @author chenyanming
	 * @param data
	 * @param split
	 * @return
	 * @时间：2017年5月10日下午1:54:07
	 */
	private double getAmountComm(String[] data, String[] split) {
		double amount = 0;
		String amount1 = this.getSpecificData(data[Integer.parseInt(split[0]) - 1]);
		if(StringUtils.isBlank(amount1)) {
			return amount;
		}
		if("0".equals(split[1])) { // 单位：元
			amount = Double.parseDouble(amount1);
		}else if("1".equals(split[1])) {
			//String amount1 = this.getSpecificData(data[Integer.parseInt(split[0]) - 1]);
			amount = Double.parseDouble(amount1)/100;
		}
		return amount;
	}

	/**
	 * 
	 * @方法说明：按位解析对账文件
	 * @author chenyanming
	 * @param list
	 * @param lineTxt
	 * @param settleRuleSecond
	 * @param channelCode
	 * @param channelType
	 * @return
	 * @时间：2017年5月9日下午4:25:45
	 */
	private int getSettleBillRecordListSpecial(List<SettleBillRecord> list, String lineTxt,
			SettleRuleSecond settleRuleSecond, String channelCode, String channelType) {
		try {
			SettleBillRecord settleBillRecord = new SettleBillRecord();
			int length = lineTxt.length();
			// 判断是否读取到结束标志
			if(StringUtils.isNotBlank(lineTxt)) {
				if(settleRuleSecond.getEndFlg() != null) {
					//String endflag = data[0].substring(0, 3);
					//endflag.startsWith(settleRuleControl.getEndFlg());
					if(lineTxt.startsWith(settleRuleSecond.getEndFlg())) {
						// 读取到结束标志
						return 1;
					}
				}
			}else {
				// 读取到空行
				return 1;
			}
			// 只读取交易成功的交易明细
			if(StringUtils.isNotBlank(settleRuleSecond.getTransStatus())) {
				String[] data = settleRuleSecond.getTransStatus().split("-");
				String[] data1 = data[1].split("\\.");
				if(length < (Integer.parseInt(data[0]) + Integer.parseInt(data1[0]) - 1)) {
					// 读取到非法数据
					return 0;
				}
				String status = data1[1];
				String transStatus = lineTxt.substring(Integer.parseInt(data[0]) - 1, Integer.parseInt(data[0]) - 1 + Integer.parseInt(data1[0]));
				if(!transStatus.contains(status)) {
					// 只读取指定状态的数据
					return 0;
				}
			}
			
			// 支付单号
			if(StringUtils.isNotBlank(settleRuleSecond.getPaymentId())) {
				String[] data = settleRuleSecond.getPaymentId().split("-");
				if(length < (Integer.parseInt(data[0]) + Integer.parseInt(data[1]) - 1)) {
					// 读取到非法数据
					return 0;
				}
				String paymentId1 = lineTxt.substring(Integer.parseInt(data[0]) - 1, Integer.parseInt(data[0]) - 1 + Integer.parseInt(data[1]));
				String paymentId = this.getSpecificData(paymentId1);
				settleBillRecord.setPaymentId(paymentId);
			}
			// 上游流水号
			if(StringUtils.isNotBlank(settleRuleSecond.getBankSeq())) {
				String[] data = settleRuleSecond.getBankSeq().split("-");
				if(length < (Integer.parseInt(data[0]) + Integer.parseInt(data[1]) - 1)) {
					// 读取到非法数据
					return 0;
				}
				String bankSeq1 = lineTxt.substring(Integer.parseInt(data[0]) - 1, Integer.parseInt(data[0]) - 1 + Integer.parseInt(data[1]));
				String bankSeq = this.getSpecificData(bankSeq1);
				if(bankSeq.startsWith("P") || bankSeq.length() > 15) { // 银联一码一付和一码多付
					settleBillRecord.setChannleNo(bankSeq);
				}else {
					settleBillRecord.setPaymentId(bankSeq);
				}
			}
			// 保留两位小数
			DecimalFormat df = new DecimalFormat("#.00");
			// 成功支付金额
			if(StringUtils.isNotBlank(settleRuleSecond.getSuccessAmount())) {
				if(StringUtils.isNotBlank(settleRuleSecond.getFeeFree())) { // 获取优惠金额
					String[] data = settleRuleSecond.getFeeFree().split("-");
					String[] data1 = data[1].split("\\.");
					if(length < (Integer.parseInt(data[0]) + Integer.parseInt(data1[0]) - 1)) {
						// 读取到非法数据
						return 0;
					}
					double feeFree = this.getAmountData(lineTxt, data, data1);
					String[] amountData = settleRuleSecond.getSuccessAmount().split("-");
					String[] amountData1 = amountData[1].split("\\.");
					if(length < (Integer.parseInt(amountData[0]) + Integer.parseInt(amountData1[0]) - 1)) {
						// 读取到非法数据
						return 0;
					}
					double successAmount = this.getAmountData(lineTxt, amountData, amountData1);
					settleBillRecord.setPromotionAmount(new BigDecimal(df.format(feeFree)).abs());
					settleBillRecord.setSuccessAmount(new BigDecimal(df.format(successAmount - Math.abs(feeFree))).abs());
					
				}else {
					String[] amountData = settleRuleSecond.getSuccessAmount().split("-");
					String[] amountData1 = amountData[1].split("\\.");
					if(length < (Integer.parseInt(amountData[0]) + Integer.parseInt(amountData1[0]) - 1)) {
						// 读取到非法数据
						return 0;
					}
					double successAmount = this.getAmountData(lineTxt, amountData, amountData1);
					settleBillRecord.setSuccessAmount(new BigDecimal(df.format(successAmount)).abs());
				}
			}
			// 获取服务费（银联、网联）
			if(StringUtils.isNotBlank(settleRuleSecond.getFeeService())) {
				String[] data = settleRuleSecond.getFeeService().split("-");
				String[] data1 = data[1].split("\\.");
				if(length < (Integer.parseInt(data[0]) + Integer.parseInt(data1[0]) - 1)) {
					// 读取到非法数据
					return 0;
				}
				double feeService = this.getAmountData(lineTxt, data, data1);
				settleBillRecord.setFeeService(new BigDecimal(df.format(feeService)).abs());
			}
			// 获取发卡行手续费
			if(StringUtils.isNotBlank(settleRuleSecond.getFeeBank())) {
				String[] data = settleRuleSecond.getFeeBank().split("-");
				String[] data1 = data[1].split("\\.");
				if(length < (Integer.parseInt(data[0]) + Integer.parseInt(data1[0]) - 1)) {
					// 读取到非法数据
					return 0;
				}
				double feeBank = this.getAmountData(lineTxt, data, data1);
				settleBillRecord.setFee(new BigDecimal(df.format(feeBank)).abs());
			}
			// 获取摘要信息
			if(StringUtils.isNotBlank(settleRuleSecond.getRemark())) {
				String[] data = settleRuleSecond.getRemark().split("-");
				if(length < (Integer.parseInt(data[0]) + Integer.parseInt(data[1]) - 1)) {
					// 读取到非法数据
					return 0;
				}
				String remark1 = lineTxt.substring(Integer.parseInt(data[0]) - 1, Integer.parseInt(data[0]) - 1 + Integer.parseInt(data[1]));
				String remark2 = this.getSpecificData(remark1);
				//String remark = Sha.encode(remark2.getBytes("UTF-8"), "SHA256");
				String remark = StringEncrypt.Encrypt(remark2, "SHA-256");
				settleBillRecord.setRemark(remark);
			}
			list.add(settleBillRecord);
			
			/*String paymentId = lineTxt.substring(323, 363);
			String sunccessAmount = lineTxt.substring(62,74);
			String fee1 = lineTxt.substring(205,217);
			String fee2 = lineTxt.substring(218,230);*/
			return 0;
		} catch (Exception e) {
			logger.error("获取数据失败" ,lineTxt , e);
			throw new RuntimeException();
		}
	}

	/**
	 * 
	 * @方法说明：按位获取金额方法
	 * @author chenyanming
	 * @param amountData
	 * @param amountData1
	 * @return
	 * @时间：2017年5月9日下午6:01:51
	 */
	private double getAmountData(String lineTxt, String[] data, String[] data1) {
		double amount = 0;
		String feeFree1 = lineTxt.substring(Integer.parseInt(data[0]) - 1, 
				Integer.parseInt(data[0]) - 1 + Integer.parseInt(data1[0]));
		String feeFree = this.getSpecificData(feeFree1);
		if(StringUtils.isBlank(feeFree)) {
			return amount;
		}
		if("0".equals(data1[1])) { // 单位：元
			amount = Double.parseDouble(feeFree);
		}else if("1".equals(data1[1])) { // 单位：分
			/*String feeFree1 = lineTxt.substring(Integer.parseInt(data[0]) - 1, 
					Integer.parseInt(data[0]) - 1 + Integer.parseInt(data1[0]));
			String feeFree = this.getSpecificData(feeFree1);*/
			amount = Double.parseDouble(feeFree)/100;
		}
		return amount;
	}

	/**
	 * 
	 * @方法说明：读取对账文件，获取账单明细数据
	 * @author chenyanming
	 * @param file
	 * @return
	 * @时间：2016年9月25日上午10:24:14
	 */
	public List<SettleBillRecord> getSettleBillRecordData(String fileName, SettleRuleControl settleRuleControl,
			String channelCode, String channelType) {
		File file = new File(fileName);
		try {
			// 判断文件是否存在
			if (file.isFile() && file.exists()) {
				List<SettleBillRecord> list = new ArrayList<SettleBillRecord>();
				// 获取每一列对应的正则表达式
				List<String> paymentIdRegexList = this.getRegexShowData(settleRuleControl.getPaymentId(), settleRuleControl.getRuleControlId(), "COMM");
				List<String> transStatusRegexList = this.getRegexShowData(settleRuleControl.getTransStatus(), settleRuleControl.getRuleControlId(), "COMM");
				List<String> successAmountRegexList = this.getRegexShowData(settleRuleControl.getSuccessAmount(), settleRuleControl.getRuleControlId(), "COMM");
				List<String> costAmountRegexList = this.getRegexShowData(settleRuleControl.getCostAmount(), settleRuleControl.getRuleControlId(), "COMM");
				List<String> channelNoRegexList = this.getRegexShowData(settleRuleControl.getChannelNo(), settleRuleControl.getRuleControlId(), "COMM");
				List<String> promotionAmountList = this.getRegexShowData(settleRuleControl.getPromotionAmount(), settleRuleControl.getRuleControlId(), "COMM");
				
				if (settleRuleControl.getBillType().equals(BillingRuleManageType.BRULETYPET.getValue())) {
					// 解析txt文件
					logger.info("读取TXT文件开始，文件为:{}" + file);
					// 获取指定文件的编码格式
					String code = this.getFilecharset(file);
					
					// 编码格式，以后改动
					InputStreamReader read = new InputStreamReader(new FileInputStream(file),code);//GB2312
					try {
						BufferedReader bufferedReader = new BufferedReader(read);
						String lineTxt = null;
						int i = 1; // 用来判断开始行号
						int j = 1; // 用来判断宝付读取数据的开始行
						int GHFlag = 1;
						String splitFlg = null;
						// 对账控制规则的分隔符不能为null，可以为空，如果不为null添加相应的转义字符，读取文件
						if(settleRuleControl.getSplitFlg() == null) {
							logger.error("获取分隔符出错:{}" + settleRuleControl);
							bufferedReader.close();
							return null;
						}else if("".equals(settleRuleControl.getSplitFlg())) {
							splitFlg = "\\s+";
						}else {
							splitFlg = "\\" + settleRuleControl.getSplitFlg();
						}
						boolean isRefund = false;
						if("TENPAY".equals(channelCode) && fileName.contains("REFUND")) {
							isRefund = true;
						}
						// 开始逐行读取明细数据
						while ((lineTxt = bufferedReader.readLine()) != null) {
							// 获取明细数据从开始行号处读取
							if("BFUPAY".equals(channelCode)) {
								if(j != 0) {
									if(lineTxt.split(splitFlg).length <= 8) {
										continue;
									}else {
										j = 0;
										continue;
									}
								}
							}else {
								if (i < settleRuleControl.getBeginRowNum()) {
									i++;
									continue;
								}
							}
							String[] data = lineTxt.split(splitFlg);
							if("DIRCON-102".equals(channelCode) && data.length > 9) {
								if(GHFlag == 1) {
									GHFlag = 0;
									continue;
								}
								int end = this.getGHRefundData(list, data);
								if(end == 1) {
									break;
								}
							}else if("TENPAY".equals(channelCode) && isRefund) {
								int end = this.getRefundData(list, data);
								if(end == 1) {
									break;
								}
							}else if("DIRCON-103".equals(channelCode) && ChannelType.B2CEBANK.getValue().equals(channelType)) { // 农行B2C
								this.getB2CData(list, lineTxt, splitFlg, settleRuleControl, channelCode, channelType,
										paymentIdRegexList, transStatusRegexList, successAmountRegexList, costAmountRegexList, channelNoRegexList);
							}else { // 一般逻辑
								// 按分隔符分割明细记录，得到明细数据的字符数组
								//String[] data = lineTxt.split(splitFlg);
								int end = this.getSettleBillRecordList(list, data, settleRuleControl, channelCode, channelType,
										paymentIdRegexList, transStatusRegexList, successAmountRegexList, costAmountRegexList, channelNoRegexList);
								// end是结束标志，1代表读取到结束标志或读取到空行，0代表继续循环下一条数据
								if(end == 1) {
									break;
								}
							}
						}
						bufferedReader.close();
						logger.info("读取TXT文件结束" + file);
						return list;
					} catch (Exception e) {
						logger.error("读取TXT文件出错" , file, e);
						throw new RuntimeException();
					} finally {
						read.close();
					}
				} else if (settleRuleControl.getBillType().equals(BillingRuleManageType.BRULETYPEE.getValue())) {
					// 解析xls文件
					logger.info("读取EXCEL文件开始，文件为:{}" + file);
					try {
						// 获取操作xls文件的对象
						HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(file));
						// 遍历所有的sheet
						//for (int i = 0; i < book.getNumberOfSheets(); i++) {
						HSSFSheet sheet = book.getSheetAt(0);
						// 遍历所有的行
						List<String> listData = null;
						if("DIRCON-105".equals(channelCode) && ChannelType.BATCHPAY.getValue().equals(channelType)) {
							// 建行批付特殊处理
							listData = this.getDataList(sheet, settleRuleControl);
						}
						for (int rowIndex = settleRuleControl.getBeginRowNum() - 1, lastNum = sheet.getLastRowNum(); rowIndex <= lastNum; rowIndex++) {
							SettleBillRecord settleBillRecord = new SettleBillRecord();
							HSSFRow row = sheet.getRow(rowIndex);
							// 读取到空行数据
							if(row == null) {
								break;
							}
							if(row.getCell(0) != null){
								//row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
								//stuUser.setPhone(row.getCell(0).getStringCellValue());
								String cell = row.getCell(0).toString();
								//HSSFCell cell = row.getCell(0);
								if(settleRuleControl.getEndFlg() != null) {
									// 判断是否是结束标志，遇到结束标志，直接跳出循环
									if (cell == null || cell.equals(settleRuleControl.getEndFlg())) {
										break;
									}
								}
								// 判断是否为空
								if(!"BILPAY".equals(channelCode)) {
									if(StringUtils.isBlank(cell)) {
										break;
									}
								}
							}else {
								break;
							}
							if("DIRCON-105".equals(channelCode) && ChannelType.BATCHPAY.getValue().equals(channelType)) {
								if(listData != null && listData.size() > 0) {
									String sign = row.getCell(3).toString();
									int ss = 0;
									for (String signdata : listData) {
										if(signdata.equals(sign)) {
											ss = 1;
										}
									}
									if(ss == 1) {
										try {
											settleBillRecord.setChannelCode(channelCode);
											settleBillRecord.setChannelType(channelType);
											settleBillRecord.setPaymentId(this.getSpecificData(row.getCell(settleRuleControl.getPaymentId() - 1).toString()));
											if(StringUtils.isNotBlank(settleBillRecord.getPaymentId())) {
												settleBillRecord.setChannleNo(this.getSpecificData(row.getCell(settleRuleControl.getChannelNo() - 1).toString()));
												settleBillRecord.setSuccessAmount(new BigDecimal(this.getSpecificData(row.getCell(settleRuleControl.getSuccessAmount() - 1).toString())).abs());
												this.insertIntoSettleDifferBillRecord(settleBillRecord, SettleDifferType.DIFFERTYPEZ.getValue());
											}
										} catch (Exception e) {
											logger.info("入库异常明细表出错", e);
										}
										continue;
									}
								}
							}
							// 判断是否是交易成功的数据
							if(settleRuleControl.getTransStatus() != null) {
								String transStatus = row.getCell(settleRuleControl.getTransStatus() - 1).toString();
								if(StringUtils.isNotBlank(transStatus)){
									if(!this.matchRegex(transStatus, transStatusRegexList)) {
										continue;
									}
									if(!transStatus.contains("成功")) {
										continue;
									}
								}
							}
							
							// 快钱快捷退款单据处理
							if("BILPAY".equals(channelCode)) {
								String payType = row.getCell(6).toString();
								if("退货".equals(payType)) {
									settleBillRecord.setField1("TK");
								}
							}
							
							if(settleRuleControl.getChannelNo() != null) {
								//String channelNo = row.getCell(settleRuleControl.getChannelNo() - 1).toString();
								String channelNo = this.getSpecificData(row.getCell(settleRuleControl.getChannelNo() - 1).toString());
								if(StringUtils.isNotBlank(channelNo)) {
									if(!this.matchRegex(channelNo, channelNoRegexList)) {
										continue;
									}
									if("BILPAY".equals(channelCode)) {
										settleBillRecord.setChannleNo(this.addZeroForNum(channelNo, 12));
									}else {
										settleBillRecord.setChannleNo(channelNo);
									}
								}
							}
							// 判断交易单号是否为空，如果为空，跳过不入库
							HSSFCell paymentIdData = row.getCell(settleRuleControl.getPaymentId() - 1);
							if(paymentIdData != null) {
								if(StringUtils.isBlank(paymentIdData.toString())) {
									if("DIRCON-105".equals(channelCode) && ChannelType.BATCHPAY.getValue().equals(channelType)) {
										paymentIdData = row.getCell(11);
										if(StringUtils.isBlank(paymentIdData.toString())) {
											continue;
										}
									}else {
										logger.error("交易单号为空，上游流水号为:{}" + settleBillRecord.getChannleNo());
										continue;
									}
								}
							}else {
								logger.error("交易单号为空，上游流水号为:{}" + settleBillRecord.getChannleNo());
								continue;
							}
							// 获取到的数据默认会带有小数，这里是将小数部分去掉
							//String paymentId = paymentIdData.toString();
							String paymentId = this.getSpecificData(paymentIdData.toString());
							if(!this.matchRegex(paymentId, paymentIdRegexList)) {
								continue;
							}
							if(paymentId.startsWith("H")) {
								// 判断支付单号是不是.net系统的交易，如果是，跳过不入库
								continue;
							}
							if(paymentId.contains(".")) {
								try {
									double parseDouble = Double.parseDouble(paymentId);
									DecimalFormat df = new DecimalFormat("#");
									paymentId = df.format(parseDouble);
								} catch (Exception e) {
									logger.error("格式化支付单号出错，单号为:{}", paymentId);
								}
							}
							settleBillRecord.setPaymentId(paymentId);
							// 获取手续费
							if(settleRuleControl.getCostAmount() != null) {
								if(StringUtils.isNotBlank(settleRuleControl.getCostAmount().toString())) {
									//String costAmount = row.getCell(settleRuleControl.getCostAmount() - 1).toString();
									String costAmount = this.getSpecificData(row.getCell(settleRuleControl.getCostAmount() - 1).toString());
									if(StringUtils.isNotBlank(costAmount)) {
										if(!this.matchRegex(costAmount, costAmountRegexList)) {
											continue;
										}
									}
									settleBillRecord.setFee(new BigDecimal(costAmount).abs());
								}
							}
							// 获取成功支付金额
							//String successAmount = row.getCell(settleRuleControl.getSuccessAmount() - 1).toString();
							String successAmount = this.getSpecificData(row.getCell(settleRuleControl.getSuccessAmount() - 1).toString());
							if(StringUtils.isNotBlank(successAmount)) {
								if(!this.matchRegex(successAmount, successAmountRegexList)) {
									continue;
								}
							}
							try {
								settleBillRecord.setSuccessAmount(new BigDecimal(successAmount).abs());
							} catch (Exception e) {
								logger.info("格式化金额出错，支付单号为:{}", settleBillRecord.getPaymentId());
								continue;
							}
							// 交易金额入库为0，鉴权交易，不入库
							if((settleBillRecord.getSuccessAmount().compareTo(new BigDecimal(0))) == 0) {
								continue;
							}
							list.add(settleBillRecord);
						}
						logger.info("读取EXCEL文件结束" + file);
						return list;
					} catch (Exception e) {
						logger.error("读取EXCEL文件出错" , file, e);
						throw new RuntimeException();
					}

				} else if(settleRuleControl.getBillType().equals(BillingRuleManageType.BRULETYPEC.getValue())) {
					// 解析csv文件
					logger.info("读取CSV文件开始，文件为:{}" + file);
					// 获取指定文件的编码格式
					String code = this.getFilecharset(file);
					// 编码格式，以后改动
					InputStreamReader read = new InputStreamReader(new FileInputStream(file),code);//GB2312
					try {
						BufferedReader bufferedReader = new BufferedReader(read);
						CsvReader creader = new CsvReader(bufferedReader, ',');
						String lineTxt = null;
						int i = 1; // 用来判断开始行号
						String splitFlg = null;
						// 对账控制规则的分隔符不能为null，可以为空，如果不为null添加相应的转义字符，读取文件
						if(settleRuleControl.getSplitFlg() == null) {
							logger.error("获取分隔符出错:{}" + settleRuleControl);
							bufferedReader.close();
							return null;
						}else if("".equals(settleRuleControl.getSplitFlg())) {
							splitFlg = "\\s+";
						}else {
							splitFlg = "\\" + settleRuleControl.getSplitFlg();
						}
						// 开始逐行读取明细数据
						while (creader.readRecord()) {
							// 获取明细数据从开始行号处读取
							if (i < settleRuleControl.getBeginRowNum()) {
								i++;
								continue;
							}
							lineTxt = creader.getRawRecord();
							// 一般逻辑
							// 按分隔符分割明细记录，得到明细数据的字符数组
							String[] data = lineTxt.split(splitFlg);
							int end = this.getSettleBillRecordList(list, data, settleRuleControl, channelCode, channelType,
									paymentIdRegexList, transStatusRegexList, successAmountRegexList, costAmountRegexList, channelNoRegexList);
							// end是结束标志，1代表读取到结束标志或读取到空行，0代表继续循环下一条数据
							if(end == 1) {
								break;
							}
						}
						logger.info("读取CSV文件结束" + file);
						creader.close();
						return list;
					} catch (Exception e) {
						logger.error("读取CSV文件出错" , file, e);
						throw new RuntimeException();
					} finally {
						read.close();
					}
				}else {
					// others 其他格式的文件，如无后缀名文件
					logger.info("读取other文件开始，文件为:{}" + file);
					// 获取指定文件的编码格式
					String code = this.getFilecharset(file);
					// 编码格式，以后改动
					InputStreamReader read = new InputStreamReader(new FileInputStream(file),code);//GB2312
					try {
						BufferedReader bufferedReader = new BufferedReader(read);
						String lineTxt = null;
						int i = 1; // 用来判断开始行号
						String splitFlg = null;
						// 对账控制规则的分隔符不能为null，可以为空，如果不为null添加相应的转义字符，读取文件
						if(settleRuleControl.getSplitFlg() == null) {
							logger.error("获取分隔符出错:{}" + settleRuleControl);
							bufferedReader.close();
							return null;
						}else if("".equals(settleRuleControl.getSplitFlg())) {
							splitFlg = "\\s+";
						}else {
							splitFlg = "\\" + settleRuleControl.getSplitFlg();
						}
						// 开始逐行读取明细数据
						while ((lineTxt = bufferedReader.readLine()) != null) {
							// 获取明细数据从开始行号处读取
							if (i < settleRuleControl.getBeginRowNum()) {
								i++;
								continue;
							}
							// 一般逻辑
							// 按分隔符分割明细记录，得到明细数据的字符数组
							String[] data = lineTxt.split(splitFlg);
							int end = this.getSettleBillRecordList(list, data, settleRuleControl, channelCode, channelType,
									paymentIdRegexList, transStatusRegexList, successAmountRegexList, costAmountRegexList, channelNoRegexList);
							// end是结束标志，1代表读取到结束标志或读取到空行，0代表继续循环下一条数据
							if(end == 1) {
								break;
							}
						}
						logger.info("读取other文件结束" + file);
						return list;
					} catch (Exception e) {
						logger.error("读取other文件出错" , file, e);
						throw new RuntimeException();
					} finally {
						read.close();
					}
				}
			} else {
				logger.error("目标文件不存在" + file);
			}

		} catch (Exception e) {
			logger.error("读取文件内容出错" , file, e);
			throw new RuntimeException();
		}
		return null;

	}
	
	/**
	 * 
	 * @方法说明：补齐字符串，不足指定长度的左边补0
	 * @author chenyanming
	 * @param str
	 * @param strLength
	 * @return
	 * @时间：2017年6月26日下午5:15:22
	 */
	public String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
         while (strLen < strLength) {
               sb = new StringBuffer();
               sb.append("0").append(str);// 左补0
            // sb.append(str).append("0");//右补0
               str = sb.toString();
               strLen = str.length();
         }
        return str;
    }
	
	/**
	 * 
	 * @方法说明：工商银行退款对账文件解析
	 * @author chenyanming
	 * @param list
	 * @param data
	 * @return
	 * @时间：2017年6月23日上午11:02:08
	 */
	private int getGHRefundData(List<SettleBillRecord> list, String[] data) {
		try {
			SettleBillRecord settleBillRecord = new SettleBillRecord();
			// 判断是否读取到结束标志
			if(StringUtils.isBlank(data[0])) {
				// 读取到空行
				return 0;
			}
			// 保留两位小数
			DecimalFormat df = new DecimalFormat("#.00");
			String channelNo = this.getSpecificData(data[3]);
			String paymentId = this.getSpecificData(data[0]);
			if(paymentId.startsWith("H")) {
				// 判断支付单号是不是.net系统的交易，如果是，跳过不入库
				return 0;
			}
			String successAmount = this.getSpecificData(data[5]);
			double amount = Double.parseDouble(successAmount);
			settleBillRecord.setPaymentId(paymentId);
			settleBillRecord.setChannleNo(channelNo);
			settleBillRecord.setSuccessAmount(new BigDecimal(df.format(amount)).abs());
			settleBillRecord.setField1("TK");
			list.add(settleBillRecord);
			return 0;
		} catch (Exception e) {
			logger.error("获取数据失败" ,data , e);
			throw new RuntimeException();
		}
	}

	/**
	 * 
	 * @方法说明：财付通退款对账文件解析
	 * @author chenyanming
	 * @param list
	 * @param data
	 * @return 
	 * @时间：2017年6月9日上午10:33:51
	 */
	private int getRefundData(List<SettleBillRecord> list, String[] data) {
		try {
			SettleBillRecord settleBillRecord = new SettleBillRecord();
			int dataSize = data.length;
			// 判断是否读取到结束标志
			if(StringUtils.isNotBlank(data[0])) {
				if(data[0].startsWith("退款")) {
					// 读取到结束标志
					return 1;
				}
			}else {
				// 读取到空行
				return 1;
			}
			if(dataSize < 8) {
				// 读取到非法数据
				return 0;
			}
			// 保留两位小数
			DecimalFormat df = new DecimalFormat("#.00");
			String channelNo = this.getSpecificData(data[7]);
			String paymentId = this.getSpecificData(data[3]);
			String successAmount = this.getSpecificData(data[10]);
			double amount = Double.parseDouble(successAmount);
			settleBillRecord.setPaymentId(paymentId);
			settleBillRecord.setChannleNo(channelNo);
			settleBillRecord.setSuccessAmount(new BigDecimal(df.format(amount)).abs());
			settleBillRecord.setField1("TK");
			list.add(settleBillRecord);
			return 0;
		} catch (Exception e) {
			logger.error("获取数据失败" ,data , e);
			throw new RuntimeException();
		}
	}

	/**
	 * 
	 * @方法说明：建行批付文件特殊处理     手动    xls文件
	 * @author chenyanming
	 * @param sheet
	 * @param paymentIdRegexList 
	 * @param settleRuleControl 
	 * @return
	 * @时间：2017年5月18日下午1:49:37
	 */
	private List<String> getDataList(HSSFSheet sheet, SettleRuleControl settleRuleControl) {
		List<String> list = new ArrayList<String>();
		for (int rowIndex = settleRuleControl.getBeginRowNum() - 1, lastNum = sheet.getLastRowNum(); rowIndex <= lastNum; rowIndex++) {
			HSSFRow row = sheet.getRow(rowIndex);
			// 读取到空行数据
			if(row == null) {
				break;
			}
			if(row.getCell(0) != null){
				String cell = row.getCell(0).toString();
				if(settleRuleControl.getEndFlg() != null) {
					// 判断是否是结束标志，遇到结束标志，直接跳出循环
					if (cell == null || cell.equals(settleRuleControl.getEndFlg())) {
						break;
					}
				}
			}else {
				break;
			}
			String data = row.getCell(settleRuleControl.getSuccessAmount() - 1).toString();
			
			int signum = 1;
			try {
				signum = new BigDecimal(data).signum();
			} catch (Exception e) {
				logger.info("建行批付格式化金额出错", e);
			}
			if(signum == -1) {
				String string = row.getCell(3).toString();
				list.add(string);
			}
		}
		return list;
	}

	/**
	 * 
	 * @方法说明：是否匹配正则表达式
	 * @author chenyanming
	 * @param transStatus
	 * @param transStatusRegex
	 * @return
	 * @时间：2017年1月18日上午10:18:27
	 */
	private boolean matchRegex(String data, List<String> regexList) {
		boolean bb = true;
		if(regexList != null && regexList.size() > 0) {
			for (String regex : regexList) {
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(data);
				boolean find = m.find();
				if(!find) {
					bb = find;
					break;
				}
			}
		}
		return bb;
	}

	/**
	 * 
	 * @方法说明：根据ruleId和ruleKey获取正则表达式
	 * @author chenyanming
	 * @param paymentId
	 * @param ruleControlId
	 * @return
	 * @时间：2017年1月17日下午6:15:06
	 */
	private List<String> getRegexShowData(Byte ruleKey, Long ruleControlId, String ruleType) {
		if(ruleKey == null) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		List<SettleRegexControl> settleRegexControlList = settleRegexControlDao.queryRegex(ruleKey, ruleControlId, ruleType);
		if(settleRegexControlList != null && settleRegexControlList.size() > 0) {
			for (SettleRegexControl settleRegexControl : settleRegexControlList) {
				list.add(settleRegexControl.getRegexShow());
			}
		}
		return list;
	}

	/**
	 * 
	 * @方法说明：农行B2C对账文件解析
	 * @author chenyanming
	 * @param lineTxt
	 * @param splitFlg
	 * @param settleRuleControl
	 * @return
	 * @时间：2016年12月12日下午4:10:08
	 */
	public void getB2CData(List<SettleBillRecord> list, String lineTxt, String splitFlg, SettleRuleControl settleRuleControl,
			String channelCode, String channelType, List<String> paymentIdRegexList,List<String> transStatusRegexList, 
			List<String> successAmountRegexList,List<String> costAmountRegexList,List<String> channelNoRegexList) {
		try {
			// 按照指定分隔符获取所有的数据
			String[] splitData = lineTxt.split("\\^\\^");
			// 第一行是表头，从第二行开始读取数据
			for(int i = 1, totalLength = splitData.length ; i < totalLength; i++) {
				String billData = splitData[i];
				String[] data = billData.split(splitFlg);
				int end = this.getSettleBillRecordList(list, data, settleRuleControl, channelCode, channelType,
						paymentIdRegexList, transStatusRegexList, successAmountRegexList, costAmountRegexList, channelNoRegexList);
				// end是结束标志，1代表读取到结束标志或读取到空行，0代表继续循环下一条数据
				if(end == 1) {
					break;
				}
			}
		} catch (Exception e) {
			logger.error("读取文件内容出错" , lineTxt, e);
			throw new RuntimeException();
		}
	}

	/**
	 * 
	 * @方法说明：获取对账文件中SettleBillRecordList的集合
	 * @author chenyanming
	 * @param list
	 * @param data
	 * @时间：2016年12月12日下午4:26:37
	 */
	public int getSettleBillRecordList(List<SettleBillRecord> list, String[] data, SettleRuleControl settleRuleControl, String channelCode, String channelType, 
			 List<String> paymentIdRegexList,List<String> transStatusRegexList, List<String> successAmountRegexList,
			 List<String> costAmountRegexList,List<String> channelNoRegexList) {
		try {
			SettleBillRecord settleBillRecord = new SettleBillRecord();
			int dataSize = data.length;
			// 判断是否读取到结束标志
			if(StringUtils.isNotBlank(data[0])) {
				if(settleRuleControl.getEndFlg() != null) {
					//String endflag = data[0].substring(0, 3);
					//endflag.startsWith(settleRuleControl.getEndFlg());
					if(data[0].startsWith(settleRuleControl.getEndFlg())) {
						// 读取到结束标志
						return 1;
					}
				}
			}else {
				// 读取到空行
				return 1;
			}
			// 只读取交易成功的交易明细
			if(settleRuleControl.getTransStatus() != null) {
				if(dataSize < settleRuleControl.getTransStatus()) {
					// 读取到非法数据
					return 0;
				}
				if(StringUtils.isNotBlank(data[settleRuleControl.getTransStatus() - 1])) {
					String transStatus = null;
					if("YSEPAY".equals(channelCode) && dataSize >= 11) {
						transStatus = data[9];
					}else {
						transStatus = data[settleRuleControl.getTransStatus() - 1];
					}
					if(!this.matchRegex(transStatus, transStatusRegexList)) {
						return 0;
					}
					/*if(!transStatus.contains("成功")) {
						return 0;
					}*/
				}
			}
			// 如果读取到的交易单号为空，不入库
			if(settleRuleControl.getPaymentId() != null) {
				if(StringUtils.isNotBlank(settleRuleControl.getPaymentId().toString())) {
					if(dataSize < settleRuleControl.getPaymentId()) {
						// 读取到非法数据
						return 0;
					}
					if(StringUtils.isNotBlank(data[settleRuleControl.getPaymentId() - 1])) {
						String paymentId = null;
						// 盛付通退款单据的特殊处理
						if("SHEPAY".equals(channelCode) && ChannelType.B2CEBANK.getValue().equals(channelType)) {
							if(dataSize >= 14) {
								String paymentId1 = this.getSpecificData(data[13]);
								if(StringUtils.isNotBlank(paymentId1)) {
									paymentId = paymentId1;
									// 退款金额为对账单中的“出账金额”字段
									String successAmount = this.getSpecificData(data[4]);
									settleBillRecord.setSuccessAmount(new BigDecimal(successAmount).abs());
								}
							}
						}
						if("BFUPAY".equals(channelCode)) {
							if("01".equals(data[3]) && dataSize >= 13) {  // 退款交易
								String paymentId1 = this.getSpecificData(data[12]);
								if(StringUtils.isNotBlank(paymentId1)) {
									paymentId = paymentId1;
								}
							}
						}
						if(paymentId == null) {
							paymentId = this.getSpecificData(data[settleRuleControl.getPaymentId() - 1]);
						}
						//String paymentId = data[settleRuleControl.getPaymentId() - 1].trim();
						if(!this.matchRegex(paymentId, paymentIdRegexList)) {
							return 0;
						}
						/*// 判断支付单号是不是.net系统的交易，如果是，跳过不入库
						if(paymentId.length() != 7) {
							return 0;
						}*/
						if(paymentId.startsWith("H")) {
							// 判断支付单号是不是.net系统的交易，如果是，跳过不入库
							return 0;
						}
						settleBillRecord.setPaymentId(paymentId);
					}else {
						logger.info("读取到的paymentId字段数据为空，跳过不入库");
						return 0;
					}
				}
			}else {
				logger.error("对账控制规则的PaymentId字段不能为空，记录主键为:{}" + settleRuleControl.getRuleControlId());
			}
			// 获取上游流水号
			if(settleRuleControl.getChannelNo() != null) {
				if(StringUtils.isNotBlank(settleRuleControl.getChannelNo().toString())) {
					if(dataSize < settleRuleControl.getChannelNo()) {
						// 读取到非法数据
						return 0;
					}
					String channelNo = this.getSpecificData(data[settleRuleControl.getChannelNo() - 1]);
					//String channelNo = data[settleRuleControl.getChannelNo() - 1];
					if(!this.matchRegex(channelNo, channelNoRegexList)) {
						return 0;
					}
					settleBillRecord.setChannleNo(channelNo);
				}
			}
			// 如果读取到的交易单号为空，不入库
			/*if(settleRuleControl.getPaymentId() != null) {
				if(StringUtils.isBlank(settleRuleControl.getPaymentId().toString())) {
					logger.error("交易单号为空，上游流水号为:{}" + settleBillRecord.getChannleNo());
					return 0;
				}
			}else {
				logger.error("对账控制规则的PaymentId字段不能为空，记录主键为:{}" + settleBillRecord.getBillDetailId());
			}*/
			if((ChannelPartner.CIBDXT.getValue().equals(channelCode) && ChannelType.WECHAT.getValue().equals(channelType))
					 || (ChannelPartner.UNIONPAY.getValue().equals(channelCode) && ChannelType.QRCODE.getValue().equals(channelType))) {
				// 保留两位小数
				DecimalFormat df = new DecimalFormat("#.00");
				// 获取手续费,微信支付对账文件中金额的单位为分
				if(settleRuleControl.getCostAmount() != null) {
					if(dataSize < settleRuleControl.getCostAmount()) {
						// 读取到非法数据
						return 0;
					}
					double fee = Double.parseDouble(data[settleRuleControl.getCostAmount() - 1])/100;
					settleBillRecord.setFee(new BigDecimal(df.format(fee)).abs());
				}
				// 获取优惠金额
				if(settleRuleControl.getPromotionAmount() != null) {
					if(dataSize < settleRuleControl.getPromotionAmount() || dataSize < settleRuleControl.getSuccessAmount()) {
						// 读取到非法数据
						return 0;
					}
					double promotionAmount = Double.parseDouble(data[settleRuleControl.getPromotionAmount() - 1])/100;
					settleBillRecord.setPromotionAmount(new BigDecimal(df.format(promotionAmount)).abs());
					double successAmount = Double.parseDouble(data[settleRuleControl.getSuccessAmount() - 1])/100;
					settleBillRecord.setSuccessAmount(new BigDecimal(df.format(successAmount - promotionAmount)).abs());
				}else {
					// 获取成功交易金额
					if(dataSize < settleRuleControl.getSuccessAmount()) {
						// 读取到非法数据
						return 0;
					}
					double successAmount = Double.parseDouble(data[settleRuleControl.getSuccessAmount() - 1])/100;
					settleBillRecord.setSuccessAmount(new BigDecimal(df.format(successAmount)).abs());
				}
			} else if("SHEPAY".equals(channelCode) && ChannelType.B2CEBANK.getValue().equals(channelType)) {
				// 盛付通通道
				if(settleBillRecord.getSuccessAmount() == null) {
					// 获取成功交易金额
					if(dataSize < settleRuleControl.getSuccessAmount()) {
						// 读取到非法数据
						return 0;
					}
					String successAmount = this.getSpecificData(data[settleRuleControl.getSuccessAmount() - 1]);
					settleBillRecord.setSuccessAmount(new BigDecimal(successAmount).abs());
				}
				
			} else {
				// 获取手续费
				if(settleRuleControl.getCostAmount() != null) {
					if(StringUtils.isNotBlank(settleRuleControl.getCostAmount().toString())) {
						if(dataSize < settleRuleControl.getCostAmount()) {
							// 读取到非法数据
							return 0;
						}
						String fee = null;
						if("YSEPAY".equals(channelCode) && dataSize >= 11) {
							fee = data[8];
						}else {
							fee = this.getSpecificData(data[settleRuleControl.getCostAmount() - 1]);
						}
						//String fee = data[settleRuleControl.getCostAmount() - 1];
						if(StringUtils.isNotBlank(fee)) {
							if(!this.matchRegex(fee, costAmountRegexList)) {
								return 0;
							}
							settleBillRecord.setFee(new BigDecimal(fee).abs());
						}
					}
				}
				// 获取优惠金额
				if(settleRuleControl.getPromotionAmount() != null) {
					if(StringUtils.isNotBlank(settleRuleControl.getPromotionAmount().toString())) {
						if(dataSize < settleRuleControl.getPromotionAmount() || dataSize < settleRuleControl.getSuccessAmount()) {
							// 读取到非法数据
							return 0;
						}
						settleBillRecord.setPromotionAmount(new BigDecimal(data[settleRuleControl.getPromotionAmount() - 1]).abs());
						BigDecimal successAmount = 
								new BigDecimal(data[settleRuleControl.getSuccessAmount() - 1]).subtract(
										new BigDecimal(data[settleRuleControl.getPromotionAmount() - 1]));
						settleBillRecord.setSuccessAmount(successAmount.abs());
					}else {
						String successAmount = this.getSpecificData(data[settleRuleControl.getSuccessAmount() - 1]);
						settleBillRecord.setSuccessAmount(new BigDecimal(successAmount).abs());
					}
				}else {
					// 获取成功交易金额
					if(dataSize < settleRuleControl.getSuccessAmount()) {
						// 读取到非法数据
						return 0;
					}
					String successAmount = null;
					if("YSEPAY".equals(channelCode) && dataSize >= 11) {
						successAmount = data[5];
					}else {
						successAmount = this.getSpecificData(data[settleRuleControl.getSuccessAmount() - 1]);
					}
					settleBillRecord.setSuccessAmount(new BigDecimal(successAmount).abs());
				}
			}
			// 交易金额入库为0，鉴权交易，不入库
			if((settleBillRecord.getSuccessAmount().compareTo(new BigDecimal(0))) == 0) {
				return 0;
			}
			list.add(settleBillRecord);
			return 0;
		} catch (Exception e) {
			logger.error("获取数据失败" ,data , e);
			throw new RuntimeException();
		}
		
	}

	/**
	 * 
	 * @方法说明：去除字符串前后的空格和双引号
	 * @author chenyanming
	 * @param string
	 * @return
	 * @时间：2017年3月7日下午4:01:17
	 */
	private String getSpecificData(String data) {
		if(data.contains("\"")) {
			data = data.replace("\"", "");
		}
		//return data.trim();
		data = data.replaceAll(" ", "");
		data = data.replaceAll(",", "");
		String regex =  regexVo.getRegex();
        Pattern p = Pattern.compile(regex);  
        Matcher m = p.matcher(data);  
        return m.replaceAll("").trim();
	}

	/**
	 * 
	 * @方法说明：获取指定文件的编码格式
	 * @author chenyanming
	 * @时间：2016年12月8日下午4:39:55
	 */
	@SuppressWarnings({ "unused", "resource" })
	public String getFilecharset(File sourceFile) {
		String charset = "GBK";
		byte[] first3Bytes = new byte[3];
		try {
			boolean checked = false;
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1) {
				return charset; // 文件编码为 ANSI
			} else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE"; // 文件编码为 Unicode
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
				charset = "UTF-16BE"; // 文件编码为 Unicode big endian
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8"; // 文件编码为 UTF-8
				checked = true;
			}
			bis.reset();
			if (!checked) {
				int loc = 0;
				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 0xF0)
						break;
					if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
							// (0x80
							// - 0xBF),也可能在GB编码内
							continue;
						else
							break;
					} else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else
								break;
						} else
							break;
					}
				}
			}
			bis.close();
		} catch (Exception e) {
			logger.error("获取对账文件的编码格式出错:{}", sourceFile, e);
		}
		return charset;
	}

}
