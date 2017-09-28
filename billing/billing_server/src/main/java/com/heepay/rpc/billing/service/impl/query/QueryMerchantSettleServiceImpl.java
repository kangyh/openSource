/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年7月7日上午9:25:32
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
package com.heepay.rpc.billing.service.impl.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.common.util.StringUtil;
import com.heepay.date.DateUtils;
import com.heepay.rpc.billing.model.SettleBatchMsgModel;
import com.heepay.rpc.billing.model.SettleMerchantModel;
import com.heepay.rpc.billing.service.IqueryMerchantSettleService;
import com.heepay.vo.ClearMerchantDetailMessage;

/***
 * 
 * 
 * 描    述：为账务系统提供查询清算明细接口
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年7月7日上午9:25:32
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
public class QueryMerchantSettleServiceImpl implements IqueryMerchantSettleService {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	ClearMerchantRecordMapper  clearMerchantRecordDaoImpl;
   
	@Override
	public SettleBatchMsgModel querySettleBathMsg(String settleBatch, int pageNum, int pageSize) {
		
		SettleBatchMsgModel msg = new SettleBatchMsgModel();
		
		msg.setPageNum(pageNum);
		msg.setPageSize(pageSize);
		msg.setSettleBatch(settleBatch);
		try{
			logger.info("账务系统查询清结算明细，结算批次：{},页码：{}，每页条数：{}",settleBatch, pageNum, pageSize);
			if(StringUtil.notBlank(settleBatch)){			
				
				Map<String, Object> paraMap = new HashMap<String, Object>();
				
				if(pageNum < 0){
					paraMap.put("settleBatch", settleBatch);
				}else{
					paraMap.put("settleBatch", settleBatch);
					paraMap.put("pageSize", pageSize);			
					paraMap.put("beginNum",(pageNum-1)*pageSize);	
				}
				
				List<ClearMerchantDetailMessage> clearMessageList = clearMerchantRecordDaoImpl.queryClearDataByPage(paraMap);
				
				if(clearMessageList != null && clearMessageList.size() > 0){
					
					List<SettleMerchantModel> list = new ArrayList<SettleMerchantModel>();
					for(ClearMerchantDetailMessage cmd : clearMessageList){
						SettleMerchantModel model = new SettleMerchantModel();
						
						model.setTransNo(cmd.getTransNo());
						model.setSuccessAmount(cmd.getSuccessAmount()==null?"":cmd.getSuccessAmount().toString());
												
						if(cmd.getSuccessTime()!=null){
							model.setSuccessTime(DateUtils.getDateStr(cmd.getSuccessTime(), "yyyy-MM-dd"));
						}						
						model.setTransType(cmd.getTransType());
						model.setFeeAmount(cmd.getFeeAmount()==null?"":cmd.getFeeAmount().toString());
						model.setRequestAmount(cmd.getRequestAmount()==null?"":cmd.getRequestAmount().toString());
						model.setMerchantId(cmd.getMerchantId());
						
						list.add(model);
					}
					msg.setSettleMerchantList(list);
					
				}else{
					msg.setSettleMerchantList(null);
				}			
			}
		}catch(Exception e){
			logger.error("账务系统查询清结算系统异常，结算批次：{}", settleBatch, e);
		}
						
		return msg;
	}
}
