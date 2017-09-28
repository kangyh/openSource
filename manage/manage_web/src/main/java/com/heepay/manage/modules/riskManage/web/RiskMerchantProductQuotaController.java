package com.heepay.manage.modules.riskManage.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.AccountType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.rpc.client.RiskServiceClient;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.service.MerchantProductRateCService;
import com.heepay.manage.modules.merchant.service.ProductCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.risk.entity.RiskMerchantProductQuota;
import com.heepay.manage.modules.risk.entity.RiskProductQuota;
import com.heepay.manage.modules.risk.service.RiskMerchantProductQuotaService;
import com.heepay.manage.modules.risk.service.RiskProductQuotaService;
import com.heepay.manage.modules.riskLogs.service.RiskLogsService;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
*
* 描 述： 商户产品限额查询Controller
*
* 创 建 者： @author wangdong 
* 创建时间： 2016年10月26日15:24:11
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
@RequestMapping(value = "${adminPath}/riskManage/riskMerchantProductQuotaQuery")
public class RiskMerchantProductQuotaController extends BaseController {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private RiskMerchantProductQuotaService riskMerchantProductQuotaService;
	
	@Autowired
	private ProductCService productCService;
	
	@Autowired
	private RiskProductQuotaService riskProductQuotaService;
	
	@Autowired
	private MerchantCService merchantCService;

	@Autowired
	private MerchantProductRateCService merchantProductRateCService;
	
	@Autowired
	private RiskServiceClient riskServiceClient;
	
	@Autowired
	private RiskLogsService riskLogsService;//记录日志
	
	/**
	 * 
	 * @方法说明：商户产品限额查询
	 * @时间：2016年10月26日 下午3:26:17
	 * @创建人：wangdong
	 */
	@RequiresPermissions("riskManage:riskMerchantProductQuota:view")
	@RequestMapping(value = { "list", "" })
	public String list(RiskMerchantProductQuota riskMerchantProductQuota, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception{
		try {
			model = riskMerchantProductQuotaService
					.findRiskMerchantProductQuotaPage(new Page<RiskMerchantProductQuota>(request, response), riskMerchantProductQuota,model);
			getAllproductInfo(model);
			model.addAttribute("flg", request.getParameter("flg"));
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaController list has a error:{商户产品限额查询错误 FIND_ERROR}", e);
		}
		return "modules/riskManage/riskMerchantProductQuotaList";
	}
	
	/**
	 * @方法说明：修改商户产品限额数据页面跳转
	 * @时间：2016年11月18日18:55:48
	 * @创建人：wangdong
	 */
	@RequiresPermissions("riskManage:riskMerchantProductQuota:view")
	@RequestMapping(value = "edit")
	public String editRiskMerchantProductQuota(RiskMerchantProductQuota riskMerchantProductQuota, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try {
			//判断添加的时候是不是从商户配置页面跳转过来的
			String merchantId = request.getParameter("merchantId");
			String merchantPageNum = request.getParameter("merchantPageNum");
			String referer = request.getHeader("referer");
			if(StringUtils.isNotBlank(merchantId) && StringUtils.isNotBlank(merchantPageNum) && StringUtils.isNotBlank(referer)){
				riskMerchantProductQuota.setMerchantId(Integer.valueOf(merchantId));
				model.addAttribute("merchantPageNum", merchantPageNum);
				model.addAttribute("referer", referer);
			}
			model = riskMerchantProductQuotaService.goToRiskMerchantProductQuotaEditJsp(riskMerchantProductQuota,model);
			return "modules/riskManage/riskMerchantProductQuotaEdit";
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaController editRiskMerchantProductQuota has a error:{修改商户产品限额数据页面跳转错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * @方法说明：修改商户产品限额信息
	 * @时间：2016年10月13日 
	 * @创建人：wangdong
	 */
	@RequiresPermissions("riskManage:riskMerchantProductQuota:edit")
	@RequestMapping(value = "save")
	public String saveRiskMerchantProductQuota(RiskMerchantProductQuota riskMerchantProductQuota, Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			//验证是否存在相同商户号，商户公司，产品编码，账户类型，银行卡类型
			Integer count = riskMerchantProductQuotaService.findRiskMerchantProductQuotaExist(riskMerchantProductQuota);
			if(null != count && count > 0){
				//存在相同商户号，商户公司，产品编码，账户类型，银行卡类型
				model = riskMerchantProductQuotaService.riskMerchantProductQuotaExist(riskMerchantProductQuota,model);
				String merchantPageNum = request.getParameter("merchantPageNum");
				String referer = request.getParameter("referer");
				model.addAttribute("merchantPageNum",merchantPageNum);
				model.addAttribute("referer",referer);
				return "modules/riskManage/riskMerchantProductQuotaEdit";
			}else{
				//不存在相同商户号，商户公司，产品编码，账户类型，银行卡类型
				String message = "";//用于拼接错误信息
				if(null != riskMerchantProductQuota){
					if(null == riskMerchantProductQuota.getMerchantId()){
						message += " 商户编码为空";
					}
					if(StringUtils.isBlank(riskMerchantProductQuota.getProductCode())){
						message += " 产品编码为空";
					}
					if(StringUtils.isBlank(riskMerchantProductQuota.getAccountType())){
						message += " 账户类型为空";
					}
					//账户类型为对私的时候，验证银行卡类型
					if(StringUtils.equals(riskMerchantProductQuota.getAccountType(), AccountType.PRIVAT.getValue())){
						if(StringUtils.isBlank(riskMerchantProductQuota.getBankcardType())){
							message += "银行卡类型为空";				
						}
					}
					if(null == riskMerchantProductQuota.getPerItem()){
						message += " 单笔限额为空";
					}
					if(null == riskMerchantProductQuota.getPerDay()){
						message += " 单日限额为空";				
					}
					if(null == riskMerchantProductQuota.getPerMonth()){
						message += " 单月限额为空";
					}
				}
				//判断是否存在为空的字段  用于提示错误信息
				if(StringUtils.isBlank(message)){
//					riskMerchantProductQuotaService.saveRiskMerchantProductQuota(riskMerchantProductQuota,request);
					
					if (null != riskMerchantProductQuota.getMerProId()){
						riskMerchantProductQuota.setUpdateAuthor(UserUtils.getUser().getName());
						riskServiceClient.editRiskMerchantProductQuota(JsonMapperUtil.nonEmptyMapper().toJson(riskMerchantProductQuota));
						//记录日志
						riskLogsService.savaEntity("更新", "更新商户产品限额信息:商户编码:"+riskMerchantProductQuota.getMerchantId()+",产品名称:"+riskMerchantProductQuota.getProductName(), request);
					}else{
						//根据商户号查询商户的信息
						Merchant merchant = merchantCService.get(riskMerchantProductQuota.getMerchantId().toString());
						if(null != merchant){
							riskMerchantProductQuota.setMerchantAccount(merchant.getEmail());//商户账户
							riskMerchantProductQuota.setMerchantName(merchant.getCompanyName());//商户名称
						}
						//如果是对公的，银行类型设置为null
						if(StringUtils.isBlank(riskMerchantProductQuota.getBankcardType())){
							riskMerchantProductQuota.setBankcardType(null);
						}
						riskMerchantProductQuota.setCreateTime(new Date());
						riskMerchantProductQuota.setCreateAuthor(UserUtils.getUser().getName());
						
						riskServiceClient.addRiskMerchantProductQuota(JsonMapperUtil.nonEmptyMapper().toJson(riskMerchantProductQuota));
						//记录日志
						riskLogsService.savaEntity("插入", "商户产品限额信息插入:商户编码:"+riskMerchantProductQuota.getMerchantId()+",产品名称:"+riskMerchantProductQuota.getProductName(), request);
					}					
					
					addMessage(redirectAttributes, "保存商户产品限额成功！");
					
					//判断是否是从商户配置过来的添加的商户产品限额
					String merchantPageNum = request.getParameter("merchantPageNum");
					String referer = request.getParameter("referer");
					if(StringUtils.isNotBlank(merchantPageNum) && StringUtils.isNotBlank(referer)){
						//返回商户产品配置
						return "redirect:" + referer + "?pageNo=" + merchantPageNum;
					}else{
						//返回商户限额页面
						//return list(riskMerchantProductQuota, request, response, model);
						return "redirect:" + adminPath + "/riskManage/riskMerchantProductQuotaQuery/";
					}
				}else{
					//存在错误信息
					model = riskMerchantProductQuotaService.goToRiskMerchantProductQuotaEditJsp(riskMerchantProductQuota, model);
					String merchantPageNum = request.getParameter("merchantPageNum");
					String referer = request.getParameter("referer");
					model.addAttribute("merchantPageNum",merchantPageNum);
					model.addAttribute("referer",referer);
					model.addAttribute("message","保存商户产品限额:"+message);
					return "modules/riskManage/riskMerchantProductQuotaEdit";
				}
			}
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaController saveRiskMerchantProductQuota has a error:{修改商户产品限额信息错误  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @方法说明：修改商户产品限额的状态
	 * @时间：2016年11月21日 上午10:03:34
	 * @创建人：wangdong
	 */
	@RequiresPermissions("riskManage:riskMerchantProductQuota:edit")
	@RequestMapping(value = "status")
	public String editStatus(RiskMerchantProductQuota riskMerchantProductQuota,  Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
//			riskMerchantProductQuotaService.saveRiskMerchantProductQuota(riskMerchantProductQuota,request);
			
			RiskMerchantProductQuota temp = riskMerchantProductQuotaService.get(riskMerchantProductQuota.getMerProId());
			temp.setUpdateAuthor(UserUtils.getUser().getName());
			temp.setStatus(riskMerchantProductQuota.getStatus());
			riskServiceClient.editRiskMerchantProductQuota(JsonMapperUtil.nonEmptyMapper().toJson(temp));
			//记录日志
			riskLogsService.savaEntity("更新", "更新商户产品限额信息:商户编码:"+riskMerchantProductQuota.getMerchantId()+",产品名称:"+riskMerchantProductQuota.getProductName(), request);
			
			return "redirect:" + adminPath + "/riskManage/riskMerchantProductQuotaQuery/";
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaController editStatus has a error:{修改产品限额的状态错误  FIND_ERROR}", e);
			throw new Exception(e);
			
		}
	}
	
	/**
	 * 
	 * @方法说明：根据商户号获取该商户的产品信息
	 * @时间：2016年11月21日 上午10:03:34
	 * @创建人：wangdong
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "product")
	public List<Product> getProductInfo(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		try {
			String merchantId = request.getParameter("merchantId");
			Merchant merchant = merchantCService.get(merchantId);
			if(null != merchant){
				return merchantProductRateCService.getMerchantProduct(merchant.getUserId().toString());
			}else{
				logger.info("RiskMerchantProductQuotaController editStatus has a error:{根据商户号获取该商户信息  FIND_SUCCESS}", "根据商户号获取商户信息服务返回  null");
				return null;
			}
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaController editStatus has a error:{根据商户号获取该商户的产品信息错误  FIND_ERROR}", e);
			return null;
		}
	}
	
	/**
	 * 
	 * @方法说明：根据产品编码获取产品限额表获取限额信息
	 * @时间：2016年11月21日 上午10:03:34
	 * @创建人：wangdong
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "quota")
	public RiskProductQuota getProductQuota(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			String productCode = request.getParameter("productCode");
			String accountType = request.getParameter("accountType");
			String bankcardType = request.getParameter("bankcardType");
			RiskProductQuota riskProductQuota = new RiskProductQuota();
			riskProductQuota.setProductCode(productCode);
			riskProductQuota.setAccountType(accountType);
			riskProductQuota.setBankcardType(bankcardType);
			List<RiskProductQuota> rpList = riskProductQuotaService.findList(riskProductQuota);
			if(null != rpList && rpList.size() > 0){
				return rpList.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaController editStatus has a error:{根据商户号获取该商户的产品信息错误  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * @注意：修改该方法时也要修改   RiskOrderController  RiskProductQuotaController 中的同名方法
	 * @方法说明：获取现有的所有产品信息
	 * @时间：2016年11月25日 上午10:35:21
	 * @创建人：wangdong
	 */
	public void getAllproductInfo(Model model) throws Exception {
		try {
			List<EnumBean> prodList = Lists.newArrayList();  //生效状态
			List<Product> productList = productCService.findList(new Product());
			if(null != productList && productList.size() > 0){
				for(Product product : productList){
					EnumBean ct = new EnumBean();
					ct.setValue(product.getCode());
					ct.setName(product.getName());
					prodList.add(ct);
				}
				model.addAttribute("prodList", prodList);
			}
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaController getAllproductInfo has a error:{获取现有的所有产品信息错误  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
}