package com.heepay.manage.modules.riskManage.web;

import java.text.ParseException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.AccountType;
import com.heepay.enums.BankcardType;
import com.heepay.enums.ChargeDeductType;
import com.heepay.enums.FeeDeductType;
import com.heepay.enums.ProductType;
import com.heepay.enums.TransType;
import com.heepay.enums.tpds.CardType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.riskManage.rpc.client.MonitorOrderServiceClient;
import com.heepay.manage.modules.riskManage.util.RiskTransOrderVo;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年8月15日上午10:24:53
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
@RequestMapping(value = "${adminPath}/risk/riskTransOrder")
public class RiskTransOrderController extends BaseController{
	
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	private MonitorOrderServiceClient monitorOrderServiceClient;
	
	
	@RequiresPermissions("risk:riskTransOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(RiskTransOrderVo riskTransOrderVo, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		JsonMapperUtil jsonUtil = JsonMapperUtil.buildNonDefaultMapper();
		
		String parameter ="merchantOrderNo,transNo,productType,productName,merchantId,merchantCompany,transType,transAmount,channelCode,channelName,createTime";
		Map<String,Object> map =new HashMap<String,Object>();
	 if(StringUtils.isNotBlank(riskTransOrderVo.getMerchantOrderNo())){
		 map.put("merchantOrderNo", riskTransOrderVo.getMerchantOrderNo());
        }
        if(StringUtils.isNotBlank(riskTransOrderVo.getMerchantId())){
        	map.put("merchantId", riskTransOrderVo.getMerchantId());
        }
        if(StringUtils.isNotBlank(riskTransOrderVo.getProductType())){
        	map.put("productType", riskTransOrderVo.getProductType());
        }
        if(StringUtils.isNotBlank(riskTransOrderVo.getTransType())){
        	map.put("transType", riskTransOrderVo.getTransType());
        }

		if(riskTransOrderVo.getBeginTime() != null){
			map.put("beginTime", riskTransOrderVo.getBeginTime().getTime());
		}

		if(riskTransOrderVo.getEndTime() != null){
			map.put("endTime", DateUtils.addDays(riskTransOrderVo.getEndTime(),1).getTime());
		}
	        
		
		Page<RiskTransOrderVo> page=new  Page<RiskTransOrderVo>(request,response);
		String no = request.getParameter("pageNo");//当前页码
		int pageNo = 1;
		if(StringUtils.isNotBlank(no)){
			pageNo=Integer.parseInt(no);
		}
		map.put("pagefrom", (pageNo-1)*page.getPageSize());
		map.put("pagesize", page.getPageSize());
		
		JsonMapperUtil jsonMapperUtil=new JsonMapperUtil();
		String json = jsonMapperUtil.toJson(map);
		
		Map<String, List<Map<String, String>>> riskTransOrderVoList =null;
		//调取风控的服务，查询出成功的数据
		try {
			logger.info("商户订单监控页面上的数据----->{查询条件}"+json);
			 riskTransOrderVoList = monitorOrderServiceClient.getMonitorOrderList(parameter, json);
			
		} catch (Exception e) {
			logger.error("商户订单监控调取风控的服务异常----->{}"+e.getMessage());
		}
	
	logger.info("商户订单监控页面上的数据----->{}"+riskTransOrderVo.toString());
	
	logger.info("商户订单冻结调取风控的服务查询----->{开始}");
	if(riskTransOrderVoList !=null){
		List<RiskTransOrderVo> list=new ArrayList<>();
		
			  page.setCount(Long.valueOf(riskTransOrderVoList.get("totalsizelist").get(0).get("totalsize")));
			  List<Map<String,String>> listVo =  riskTransOrderVoList.get("list");
	          for (Map map2 : listVo) {
	        	  RiskTransOrderVo riskTransOrderVo2 = JsonMapperUtil.nonEmptyMapper().fromJson(jsonUtil.toJson(map2), RiskTransOrderVo.class);
	        	//交易类型
	  			if(StringUtils.isNotBlank(riskTransOrderVo2.getTransType())){
	  				riskTransOrderVo2.setTransType(TransType.labelOf(riskTransOrderVo2.getTransType()));
	  			}
	  			//产品类型
	  			if(StringUtils.isNotBlank(riskTransOrderVo2.getProductType())){
	  				riskTransOrderVo2.setProductType(ProductType.labelOf(riskTransOrderVo2.getProductType()));
	  			}

	  			//时间转换
				  //riskTransOrderVo2.setCreateDate(new Date(riskTransOrderVo2.getCreateTime()));
				  riskTransOrderVo2.setCreateDate(DateUtils.parseDate(riskTransOrderVo2.getCreateTime().toString(),"yyyyMMddHHmmss"));
				  list.add(riskTransOrderVo2);
			}
	          page.setList(list);
		logger.info("商户订单冻结调取风控的服务查询----->{结束}");
	}else{
		logger.info("商户订单冻结调取风控的服务查询交易成功的数据异常----->{transactionList==null}");
	}
	
	// 交易类型
	List<EnumBean> tranTypeList = Lists.newArrayList();
	for (TransType checkFlg : TransType.values()) {
		EnumBean ct = new EnumBean();
		ct.setValue(checkFlg.getValue());
		ct.setName(checkFlg.getContent());
		tranTypeList.add(ct);
	}
	model.addAttribute("tranTypeList", tranTypeList);
	
	//产品类型
	List<EnumBean> productList = Lists.newArrayList();
	for (ProductType checkFlg : ProductType.values()) {
		EnumBean ct = new EnumBean();
		ct.setValue(checkFlg.getValue());
		ct.setName(checkFlg.getContent());
		productList.add(ct);
	}
	model.addAttribute("productList", productList);
	
	model.addAttribute("riskTransOrderVo", riskTransOrderVo);
	model.addAttribute("page", page);
	return "modules/riskManage/riskTransOrderList";
}
	
	
	

	
	
	@RequiresPermissions("risk:riskTransOrder:view")
	@RequestMapping(value = "toDetail")
	public String toDetail(RiskTransOrderVo riskTransOrderVo,HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		try {
			
			String orderId = request.getParameter("orderId");
			
			String result = monitorOrderServiceClient
			.getMonitorOrderInfo("bankCardType,bankCardNo,bankCardOwnerName,bankCardOwnerIdCard,bankCardOwnerMobile,channelTransType,feeType,bankCardOwnerType,feeAmount,reqIp", orderId);
			
			RiskTransOrderVo riskTransVo = JsonMapperUtil.nonEmptyMapper().fromJson(result, RiskTransOrderVo.class);
			//产品类型
			if(riskTransVo != null){
				if(StringUtils.isNotBlank(riskTransVo.getBankCardOwnerType())){
	  				riskTransVo.setBankCardOwnerType(AccountType.labelOf(riskTransVo.getBankCardOwnerType()));
	  			}
				if(StringUtils.isNotBlank(riskTransVo.getBankCardType())){
	  				riskTransVo.setBankCardType(CardType.labelOf(riskTransVo.getBankCardType()));
	  			}
				if(StringUtils.isNotBlank(riskTransVo.getFeeType())){
	  				riskTransVo.setFeeType(ChargeDeductType.labelOf(riskTransVo.getFeeType()));
	  			}
			}
			model.addAttribute("riskTransOrderVo", riskTransVo);
		    return "modules/riskManage/riskTransOrderDetail";
		} catch (Exception e) {
			logger.error("RiskTransOrderVo toDetail has a error:{风控商户控制详细信息错误FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	


}
