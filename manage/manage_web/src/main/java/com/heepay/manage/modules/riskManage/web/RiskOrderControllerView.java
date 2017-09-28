package com.heepay.manage.modules.riskManage.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.ChargeDeductType;
import com.heepay.enums.TransType;
import com.heepay.enums.risk.RiskFreezeRemark;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.risk.entity.RiskMerchantFreeze;
import com.heepay.manage.modules.risk.service.RiskMerchantFreezeService;
import com.heepay.manage.modules.riskManage.rpc.client.TransactionClient;
import com.heepay.risk.RiskForPaymentVO;
import com.heepay.rpc.risk.model.FreezeResponse;
import com.heepay.rpc.risk.model.TransactionModel;


/***
 * 
* 
* 描    述：商户订单冻结Controller
*
* 创 建 者：wangl
* 创建时间：  Dec 3, 20166:01:48 PM
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
@RequestMapping(value = "${adminPath}/riskManage/RiskMerchantFreezeOrderQuery/view")
public class RiskOrderControllerView extends BaseController {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private TransactionClient transactionClient;
	
	@Autowired
	private  RiskMerchantFreezeService  riskMerchantFreezeService;
	
	@Autowired
	private MerchantCService merchantCService;//调取交易
	
	/**
	 * 
	 * @方法说明：从风控系统查询出商户订单成功的数据（3个月的数据）
	 * @时间：2016年10月26日 下午3:26:17
	 * @创建人：wangl
	 */
	@RequiresPermissions("riskManage:paymentRecord:view")
	@RequestMapping(value = { "list", "" })
	public String list(RiskForPaymentVO riskForPaymentVO,HttpServletRequest request,
					   HttpServletResponse response, Model model) throws Exception{
		
			/**
			 * 调取交易组装查询条件
			 */
			String trans = riskForPaymentVO.getTransNo();
			String transNo="";
			if(trans !=null){
				transNo =trans;
			}
			
			Long id = riskForPaymentVO.getMerchantId();
			String merchantId="";
			if(id !=null){
				merchantId =id+"";
			}
			
			String  loginName= riskForPaymentVO.getMerchantLoginName();
			String merchantLoginName="";
			if(loginName !=null){
				merchantLoginName =loginName;
			}
			
			String type = riskForPaymentVO.getTransType();
			String transType2="";
			if(type !=null){
				transType2 =type;
			}
			
			Date beginCreateTime = riskForPaymentVO.getBeginCreateTime();//开始时间
			/*String beginTime = "";//开始时间
			if(beginCreateTime !=null){
				 beginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beginCreateTime);
			}*/
			
			Date endCreateTime = riskForPaymentVO.getEndCreateTime();//结束时间
			/*String endTime = "";//结束时间
			if(endCreateTime !=null){
				 endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endCreateTime);
			}*/
			
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("transNo", transNo);//交易订单号
			map.put("merchantId", merchantId);// 商户编码
			map.put("merchantLoginName", merchantLoginName);//商户账号
			map.put("transType", transType2);//交易类型
			map.put("beginCreateTime", beginCreateTime);//交易成功时间
			map.put("endCreateTime", endCreateTime);//交易成功时间
			
			JsonMapperUtil jsonMapperUtil=new JsonMapperUtil();
			String json = jsonMapperUtil.toJson(map);
			
			String no = request.getParameter("pageNo");//当前页码
			int pageNo = 1;
			if(StringUtils.isNotBlank(no)){
				pageNo=Integer.parseInt(no);
			}
			//调取风控的服务，查询出成功的数据
			FreezeResponse transactionList=null;
			try {
				logger.info("商户订单冻结页面上的数据----->{查询条件}"+json);
				transactionList = transactionClient.getTransactionList(json, pageNo-1, 30);
				
			} catch (Exception e) {
				logger.error("商户订单冻结调取风控的服务异常----->{}"+e.getMessage());
			}
		
		logger.info("商户订单冻结页面上的数据----->{}"+riskForPaymentVO.toString());
		Page<RiskForPaymentVO> page=new  Page<RiskForPaymentVO>(request,response);
		
		logger.info("商户订单冻结调取风控的服务查询----->{开始}");
		if(transactionList !=null){
			List<RiskForPaymentVO> list=new ArrayList<>();
			for (TransactionModel transactionModel : transactionList.getTransList()) {
				
				RiskForPaymentVO riskVo=new RiskForPaymentVO();
				long merchantId2 = Integer.parseInt(transactionModel.getMerchantId());
				riskVo.setMerchantId(merchantId2);  // 商户编码
				riskVo.setMerchantLoginName(transactionModel.getMerchantLoginName()); //商户账号
				
				Date successTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(transactionModel.getSuccessTime());
				riskVo.setSuccessTime(successTime);  //交易成功时间
				riskVo.setTransType(transactionModel.getTransType());//交易类型
				riskVo.setSuccessAmount(transactionModel.getSuccessAmount()); //实际支付金额
				String transNo2 = transactionModel.getTransNo();
				riskVo.setTransNo(transNo2); //交易订单号
				riskVo.setFeeWay(transactionModel.getFeeWay());//手续费扣除方式
				riskVo.setFeeWayVo(transactionModel.getFeeWay());// 手续费扣除方式的中间属性
				riskVo.setFee(transactionModel.getFee());// 手续费
				
				
				boolean flag=riskMerchantFreezeService.getRemark1(transNo2);
				if(flag){
					riskVo.setRepeat("Y");//用来判断是否是重复数据
				}
				if(StringUtils.isNotBlank(transactionModel.getTransType())){
					riskVo.setTransType(TransType.labelOf(transactionModel.getTransType()));
				}
				if(StringUtils.isNotBlank(transactionModel.getFeeWay())){//手续费扣除方式
					riskVo.setFeeWay(ChargeDeductType.labelOf(transactionModel.getFeeWay()));
				}
				list.add(riskVo);//向List添加查询出的对象
			}
			int totalCount = (int) transactionList.getTotalCount();
			page.setCount(totalCount); // *** 设置页面显示总共条数,是风控返回的数据
			//page.setPageSize(1);
			
			//将查询出来的list放到分页对象中
			page.setList(list);
			
			logger.info("商户订单冻结调取风控的服务查询----->{结束}");
		}else{
			logger.info("商户订单冻结调取风控的服务查询交易成功的数据异常----->{transactionList==null}");
		}
		
		// 交易类型
		List<EnumBean> transType = Lists.newArrayList();
		for (TransType checkFlg : TransType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			transType.add(ct);
		}
		model.addAttribute("transType", transType);
		
		model.addAttribute("riskForPaymentVO", riskForPaymentVO);
		model.addAttribute("page", page);
		return "modules/riskManage/paymentFundRecordList";
	}
	
	/**
	 * 
	 * @方法说明：将商户订单冻结页面上的数据传到商户冻结页面上进行冻结
	 * @时间：Dec 3, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("riskManage:paymentRecord:view")
	@RequestMapping(value = "/addAmount/{merchantId}/{successAmount}/{transNo}/{pageNo}/{fee}/{feeWayVo}")
	public String toAddOrder(@PathVariable("merchantId") String merchantId,
							 @PathVariable("successAmount") String successAmount,
							 @PathVariable("pageNo") String pageNo,
							 @PathVariable("fee") String fee,
							 @PathVariable("feeWayVo") String feeWayVo,
							 HttpServletRequest request,
							 @PathVariable("transNo") String transNo, Model model){
		
		
		String referer = request.getHeader("referer");
		
		
		
		
		
		logger.info("商户订单冻结页面上的数据----->{}"+merchantId+","+successAmount+","+model);
		RiskMerchantFreeze riskMerchantFreeze=new RiskMerchantFreeze();
		riskMerchantFreeze.setMerchantCode(merchantId);//商户编码
		
		String amountMsg="";
		if(feeWayVo.equals(ChargeDeductType.INTERNAL_DEDUCT.getValue())){ //坐扣
			
			//BigDecimal suAmount=new BigDecimal(successAmount);
			//BigDecimal feeAmount=new BigDecimal(fee);
			
			BigDecimal transAmount=new BigDecimal(successAmount).subtract(new BigDecimal(fee));//总金额-手续费
			riskMerchantFreeze.setTransAmount(transAmount);//订单对应得金额
			amountMsg="手续费  ￥ "+fee;
		}else if(feeWayVo.equals(ChargeDeductType.EXTERNAL_DEDUCT.getValue()) || StringUtil.isBlank(feeWayVo)){ //外扣
			
			BigDecimal transAmount=new BigDecimal(successAmount);
			riskMerchantFreeze.setTransAmount(transAmount);//订单对应得金额
			amountMsg="";
		}
		riskMerchantFreeze.setRemark1(transNo); //交易订单号
		
		
		//String merchantJson="";
		String msg="";
		String error="";
		
		try {
			//根据商户id查询商户名称
			Merchant merchant = merchantCService.get(merchantId);
			/*merchantJson = client.getMerchantVO(merchantId);
			@SuppressWarnings("unchecked")
			Map<String, String> map = JsonMapperUtil.nonEmptyMapper().fromJson(merchantJson, Map.class);*/
			if(merchant !=null){
				String companyName = merchant.getCompanyName();
				//String merchantCompany = map.get("merchantCompany");
					
				if(StringUtils.isNotBlank(companyName)){
					msg= companyName;
				}
			}else{
				logger.error("查询对象为空无法添加商户冻结-------->{查询错误,无法冻结}");
				error="查询错误,无法冻结";
			}
		} catch (Exception e) {
			logger.error("查询对象为空无法添加商户冻结-------->{消息为空}");
			error="风控服务链接异常";
		}
		
		
		//冻结/解冻原因 freeze_remark
		List<EnumBean> riskFreezeRemark = Lists.newArrayList();
		for (RiskFreezeRemark checkFlg : RiskFreezeRemark.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			riskFreezeRemark.add(ct);
		}
		model.addAttribute("riskFreezeRemark", riskFreezeRemark);
		
		
		logger.info("商户订单冻结页面上的数据传输完毕，跳转到商户余额冻结页面----->{路径}modules/riskManage/riskMerchantFreezeAdd");
		model.addAttribute("referer", referer);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("msg", msg);
		model.addAttribute("error", error);
		model.addAttribute("amountMsg", amountMsg);
		
		model.addAttribute("riskMerchantFreeze", riskMerchantFreeze);
		return "modules/riskManage/riskMerchantFreezeAdd";
	}
}
