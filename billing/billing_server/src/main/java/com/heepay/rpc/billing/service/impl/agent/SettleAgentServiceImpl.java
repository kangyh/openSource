/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年3月14日下午2:47:38
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
package com.heepay.rpc.billing.service.impl.agent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.dao.SettleMerchantRecordMapper;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.entity.SettleMerchantRecord;
import com.heepay.billingutils.CreateDir;
import com.heepay.common.util.Constants;
import com.heepay.date.DateUtils;
import com.heepay.enums.TransType;
import com.heepay.rpc.billing.common.BillingCommon;
import com.heepay.rpc.service.RpcService;

/***
 * 
 * 
 * 描    述：代理商结算
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年3月14日下午2:47:38
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
@Repository         
@RpcService(name = "SettleAgentServiceImpl", processor = com.heepay.rpc.billing.service.AgentMerchantService.Processor.class)
public class SettleAgentServiceImpl implements com.heepay.rpc.billing.service.AgentMerchantService.Iface{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Resource(name = "billingCommon")
	BillingCommon billingCommon;

	@Autowired
	ClearMerchantRecordMapper  clearMerchantRecordDaoImpl;
	
	@Autowired
	SettleMerchantRecordMapper settleMerchantRecordDaoImpl;
	
	String herder[] = {"商户ID","商家名称", "商家订单号", "汇付宝交易单", "交易时间",	"实际支付金额",	"手续费金额", "结算日期",	 "交易类型",  "产品类型"};

	/**
	 * 	
	 * @方法说明：每天凌晨生成前一天的对账文件
	 * @author xuangang
	 * @时间：2017年3月17日下午2:18:02
	 */
	public void agentSettle(){		

		gernerateBillFile(TransType.PAY.getValue());  //支付
		
		gernerateBillFileRefund(TransType.REFUND.getValue());  //退款
			
	}
	

    
    
	private void gernerateBillFileRefund(String trType){
		
		try {
			Map paramap = new HashMap<>();
			
			//结算日期,当天日期的前一天
			Date settelDate = DateUtils.getStrDate(DateUtils.getPreDate(new Date()), "yyyy-MM-dd");
			
			String settleDate = DateUtils.getDateStr(settelDate, "yyyy-MM-dd");
			paramap.put("settleDate", settleDate);
			paramap.put("transType", trType);
			paramap.put("settleStatus", Constants.Clear.SETTLE_STATUS_Y);
			
			List<ClearMerchantRecord> list = clearMerchantRecordDaoImpl.queryAgentforSettle(paramap);
			
			if(list == null || list.size() == 0){
				logger.info("清结算代理商对账文件，无结算记录！交易类型{}", trType);
				return;
			}
          for(ClearMerchantRecord record: list){
        	  
        	    Integer merchantId = record.getMerchantId(); 	   //商户id
				String  settleStatus = Constants.Clear.SETTLE_STATUS_Y; //结算状态
				String transType = record.getTransType();        //交易类型
				Date settleTime = settelDate;       //结算日期
				
        	   String fileNamexls = merchantId + "_" + transType + "_"	+ settleDate + ".xls"; // 文件名字
  			   String fileNameCvs = merchantId + "_" + transType + "_"	+ settleDate + ".csv"; // 文件名字
  			try {
  				List<ClearMerchantRecord> clearList = clearMerchantRecordDaoImpl.queryAgentforSettle(merchantId, settleStatus, transType, settleTime);

  				String filePath = billingCommon.getAgentFilePath() + merchantId	+ "/" + DateUtils.getDateStr(new Date(), "yyyy/MM")+ "/";
  				exportExcel(fileNamexls, herder, clearList, filePath);
  				saveCsvFile(fileNameCvs, herder, clearList, filePath);
  			} catch (Exception e) {
  				logger.error("生成清结算代理商对账文件异常，商户id{},交易类型{}", merchantId,	transType, e);
  			}
          }	
		}catch (Exception e) {
  			logger.error("生成清结算代理商对账文件异常，{}", trType, e);
  		}
		
	}
	
	
	
	
	/**
	 * 
	 * @方法说明：按照交易类型生成对账文件
	 * @author xuangang
	 * @param trType
	 * @时间：2017年3月17日下午2:19:53
	 */
	private void gernerateBillFile(String trType){
		try {
			//结算日期,当天日期的前一天
			Date settelDate = DateUtils.getStrDate(DateUtils.getPreDate(new Date()), "yyyy-MM-dd");
			
			String settleDate = DateUtils.getDateStr(settelDate, "yyyy-MM-dd");
			
			logger.info("生成清结算代理商对账文件开始，结算日期{}", settleDate);
			
			//查询所有当日已结算的记录
			List<SettleMerchantRecord> list = settleMerchantRecordDaoImpl.queryAgentforSettle(Constants.Clear.SETTLE_STATUS_Y, settelDate, trType);
			
			if(list == null || list.size() == 0){
				logger.info("清结算代理商对账文件，无结算记录！交易类型{}", trType);
				return;
			}
				
			for(SettleMerchantRecord settleMerchantRecord : list){
				Integer merchantId = settleMerchantRecord.getMerchantId(); 	   //商户id
				String  settleStatus = settleMerchantRecord.getSettleStatus(); //结算状态
				String transType = settleMerchantRecord.getTransType();        //交易类型
				Date settleTime = settleMerchantRecord.getSettleTime();       //结算日期
				String fileNamexls = merchantId + "_" + transType + "_" + settleDate + ".xls";   //文件名字
				String fileNameCvs = merchantId + "_" + transType + "_" + settleDate + ".csv";   //文件名字
				
				try{					
					List<ClearMerchantRecord> clearList = clearMerchantRecordDaoImpl.queryAgentforSettlePayment(merchantId, settleStatus, transType, settleTime);
						
					String filePath = billingCommon.getAgentFilePath() + merchantId + "/" +DateUtils.getDateStr(new Date(), "yyyy/MM") + "/";
					exportExcel(fileNamexls, herder, clearList, filePath);
					saveCsvFile(fileNameCvs, herder, clearList, filePath);
				}catch(Exception e){
					logger.error("生成清结算代理商对账文件异常，商户id{},交易类型{}", merchantId, transType, e);
				}		
			}			
			
		} catch (ParseException e) {
			
			logger.error("生成清结算代理商对账文件异常，{}", trType, e);
		}
		catch(Exception e){
			logger.error("生成清结算代理商对账文件异常，{}", trType, e);
		}	
	}
	/**
	 * 
	 * @方法说明：生成对账文件
	 * @author xuangang
	 * @param fileName
	 * @param headers
	 * @param dataset
	 * @param filePath
	 * @时间：2017年3月15日上午11:39:15
	 */
	private void exportExcel(String fileName, String[] headers, List<ClearMerchantRecord> dataset, String filePath){
		
		FileOutputStream out = null;		
		
		try {
			String downName = fileName;

	        File file=new File(filePath + fileName);
	        
	        File directory =new File(filePath);    
			//如果文件夹不存在则创建    
			if  (!directory .exists()  && !directory.isDirectory())      
			{		   
				 CreateDir.createLiunxDir(filePath);   
			}
	       
	        if(!file.exists())
	            file.createNewFile();
	       
	        out = new FileOutputStream(file, false); //如果追加方式用true     
			
			// 声明一个工作薄
	        HSSFWorkbook workbook = new HSSFWorkbook();
	        // 生成一个Sheet
	        HSSFSheet sheet = workbook.createSheet(downName);
	      
	        //产生表格标题行
	        HSSFRow row = sheet.createRow(0);
	        
	        for (short i = 0; i < headers.length; i++) {
	            HSSFCell cell = row.createCell(i);
	            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
	            cell.setCellValue(text);
	        }
	       
	        int index = 0;
	        for(ClearMerchantRecord record: dataset)
	        {
	            index++;
	            row = sheet.createRow(index);
	            {
	                    HSSFCell cell0 = row.createCell(0);					
						
						cell0.setCellValue(record.getMerchantId());	 //商户id					
						
						HSSFCell cell1 = row.createCell(1);
						cell1.setCellValue(record.getMerchantName());	//商户名称
						
						HSSFCell cell2 = row.createCell(2);
						cell2.setCellValue(record.getMerchantOrderNo());//商家订单	
						
						HSSFCell cell3 = row.createCell(3);
						cell3.setCellValue(record.getTransNo());       //交易订单号
						
						HSSFCell cell4 = row.createCell(4);
						cell4.setCellValue(DateUtils.getDateStr(record.getSuccessTime(), DateUtils.DATE_FORMAT_DATE_TIME));    //交易成功时间
						
						HSSFCell cell5 = row.createCell(5);
						cell5.setCellValue(roundHalfUp(record.getRequestAmount())); //实际支付金额
						
						HSSFCell cell6 = row.createCell(6);
						cell6.setCellValue(roundHalfUp(record.getFee()));//手续费
						
						HSSFCell cell7 = row.createCell(7);
						cell7.setCellValue(DateUtils.getDateStr(record.getFinishTime(), "yyyy-MM-dd"));  //结算日期,账务系统记账成功
						
						HSSFCell cell8 = row.createCell(8);
						cell8.setCellValue(TransType.getContentByValue(record.getTransType())+ "_"+  record.getTransType()); //交易类型
						
						HSSFCell cell9 = row.createCell(9);
						cell9.setCellValue((record.getProductName()==null?"":record.getProductName())+"_"+record.getProductCode()); //产品编码					
	            }
	        }
	        workbook.write(out);//  
	      
		}catch (IOException e) {		
			logger.error("生成清结算代理商对账文件异常，文件名：{}", fileName, e);
        	
        } catch (Exception e) {
        	logger.error("生成清结算代理商对账文件异常，文件名：{}", fileName, e);
		}finally {
			
        	try {
				if(out != null){
					out.flush();
					out.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	
	/**
	 * 代理商户生成对账文件
	 * @方法说明：
	 * @author xuangang
	 * @param fileName
	 * @param headers
	 * @param dataset
	 * @param filePath
	 * @时间：2017年3月15日下午3:45:15
	 */
	private void saveCsvFile(String fileName, String[] headers, List<ClearMerchantRecord> dataset, String filePath){
		
			
		FileOutputStream outSTr = null;     
		  
		BufferedWriter Buff=null;
		try{
	        File file=new File(filePath + fileName);
	        
	        outSTr = new FileOutputStream(file);  
	        
	        OutputStreamWriter ow = new OutputStreamWriter(outSTr, "UTF-8");
	        
            Buff = new BufferedWriter(ow); 
	        
	        File directory =new File(filePath);    
			//如果文件夹不存在则创建    
			if  (!directory .exists()  && !directory .isDirectory())      
			{		   
				 CreateDir.createLiunxDir(filePath);   
			}
	        if(!file.exists())
	            file.createNewFile();
	        
	        StringBuffer sb = new StringBuffer();
	        
	        if(headers == null){
	        	return;
	        }	        	
	       
        	for(int i=0, length = headers.length; i < length; i++){
        		 sb.append(headers[i]);
        		 sb.append(",");
        	}	       
	        sb.append("\r\n");
	        Buff.write(sb.toString());
	        
	        for(ClearMerchantRecord record: dataset){
	        	
	        	String transType = record.getTransType();
	        	
	        	StringBuffer sbuff = new StringBuffer();
	        	sbuff.append(record.getMerchantId().toString());
	        	sbuff.append(",");
	        	sbuff.append(record.getMerchantName()==null?"":record.getMerchantName());
	        	sbuff.append(",");
	        	sbuff.append(record.getMerchantOrderNo());
	        	sbuff.append(",");
	        	sbuff.append(record.getTransNo());
	        	sbuff.append(",");
	        	sbuff.append(DateUtils.getDateStr(record.getSuccessTime(), DateUtils.DATE_FORMAT_DATE_TIME));
	        	sbuff.append(",");
	        	sbuff.append(roundHalfUp(record.getRequestAmount()));
	        	sbuff.append(",");
	        	sbuff.append(roundHalfUp(record.getFee()));
	        	sbuff.append(",");
	        	if(TransType.REFUND.getValue().equals(transType)){  //如果是退款
	        		sbuff.append(DateUtils.getDateStr(record.getSettleTime(), "yyyy-MM-dd"));
	        	}else{
	        		sbuff.append(DateUtils.getDateStr(record.getFinishTime(), "yyyy-MM-dd"));
	        	}
	        	
	        	sbuff.append(",");
	        	sbuff.append(TransType.getContentByValue(record.getTransType())+ "_"+  record.getTransType());
	        	sbuff.append(",");
	        	sbuff.append((record.getProductName()==null?"":record.getProductName())+"_"+record.getProductCode()); 
	        	sbuff.append("\r\n");
	        	 Buff.write(sbuff.toString());
	        }
	       
	        Buff.flush();	        
            Buff.close();  
		}catch(Exception e){
			logger.error("生成代理商对账文件异常，文件名：{}", fileName, e);
		}
		finally {     
			  
            try {  
                Buff.close();     
  
                outSTr.close();
  
            } catch (Exception e) {    
  
                e.printStackTrace(); 
            }   
        } 		
		
	}
	/**
	 * 
	 * @方法说明：保留小数俩位
	 * @author xuangang
	 * @param bg
	 * @return
	 * @时间：2017年3月15日下午5:43:30
	 */
	private String roundHalfUp(BigDecimal bg){
		
		if(bg == null)
			return "";

		BigDecimal bigDec = bg.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		return bigDec.toString();
	}
	/* (non-Javadoc)
	 * @see com.heepay.rpc.billing.service.AgentMerchantService.Iface#agentBillProcess()
	 */
	@Override
	public void agentBillProcess() throws TException {
		 agentSettle();
		
	}

}
