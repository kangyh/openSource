/**
 *  
 */
package com.heepay.manage.modules.payment.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.heepay.enums.PaymentRecordStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.service.PaymentRecordService;

/**
 * 
* 
* 描    述：汇付宝个人钱包
*
* 创 建 者： yanxb  
* 创建时间： 2017年4月5日 上午11:11:35 
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
@Controller
@RequestMapping(value = "${adminPath}/payment/userRecord")
public class UserCenterListController extends BaseController {

	@Autowired
	private PaymentRecordService paymentRecordService;
	
	//记录日志
	private static final Logger log = LogManager.getLogger();
	

	/**
	 * 
	* @discription汇付宝个人钱包查询
	* @author yanxb       
	* @created 2017年4月5日 上午11:24:47     
	* @param userRecord
	* @param request
	* @param response
	* @param model
	* @return
	 */
	@RequestMapping(value = {"list", ""})
	public String list(PaymentRecord paymentRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		try{
			Page<PaymentRecord> page = paymentRecordService.findUserWithdrawPage(new Page<PaymentRecord>(request, response), paymentRecord);
			if(page != null){
				List<PaymentRecord> resultList = new ArrayList<PaymentRecord>();
				List<PaymentRecord> paymentRecordList = page.getList();
				for(PaymentRecord record : paymentRecordList){
					if(!PaymentRecordStatus.SUCCESS.getValue().equals(record.getStatus())
							&& !PaymentRecordStatus.FAILED.getValue().equals(record.getStatus())){
						record.setStatus("PROCES");
					}else{
						record.setStatus(record.getStatus());
					}
					resultList.add(record);
				}
				page.setList(resultList);
			}
			model.addAttribute("page", page);
		}catch(Exception e){
			log.error("汇付宝个人钱包查询出错，错误信息={}",e.getMessage());
		}
		return "modules/payment/userCenterRecordList";
	}

}