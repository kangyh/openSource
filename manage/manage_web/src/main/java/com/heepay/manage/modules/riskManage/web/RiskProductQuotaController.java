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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.AccountType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.rpc.client.RiskServiceClient;
import com.heepay.manage.modules.merchant.service.ProductCService;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.risk.entity.RiskProductQuota;
import com.heepay.manage.modules.risk.service.RiskProductQuotaService;
import com.heepay.manage.modules.riskLogs.service.RiskLogsService;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
*
* 描 述： 产品限额查询Controller
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
@RequestMapping(value = "${adminPath}/riskManage/riskProductQuotaQuery")
public class RiskProductQuotaController extends BaseController {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private RiskProductQuotaService riskProductQuotaService;
	
	@Autowired
	private ProductCService productCService;
	
	@Autowired
	private RiskServiceClient riskServiceClient;
	
	@Autowired
	private RiskLogsService riskLogsService;//记录日志
	
	/**
	 * 
	 * @方法说明：产品限额信息查询
	 * @时间：2016年10月26日 下午3:26:17
	 * @创建人：wangdong
	 */
	@RequiresPermissions("riskManage:riskProductQuota:view")
	@RequestMapping(value = { "list", "" })
	public String list(RiskProductQuota riskProductQuota, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception{
		try {
			model = riskProductQuotaService.findRiskProductQuotaPage(new Page<RiskProductQuota>(request, response), riskProductQuota,model);
			//获取所有的产品
			getAllproductInfo(model);
			model.addAttribute("flg", request.getParameter("flg"));
		} catch (Exception e) {
			logger.error("RiskProductQuotaController list has a error:", e);
		}
		return "modules/riskManage/riskProductQuotaList";
	}
	
	/**
	 * @方法说明：添加/修改产品限额数据页面跳转
	 * @时间：2016年11月18日18:55:48
	 * @创建人：wangdong
	 */
	@RequiresPermissions("riskManage:riskProductQuota:view")
	@RequestMapping(value = "edit")
	public String editRiskProductQuota(RiskProductQuota riskProductQuota, Model model) throws Exception{
		try {
			model = riskProductQuotaService.goToRiskProductQuotaAddOrEditJsp(riskProductQuota,model);
			//获取所有的产品
			getAllproductInfo(model);
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaController editRiskProductQuota has a error:{添加/修改产品限额数据页面跳转错误 FIND_ERROR}", e);
		}
		return "modules/riskManage/riskProductQuotaEdit";
	}
	
	/**
	 * @方法说明：保存/修改产品限额数据
	 * @时间：2016年11月18日20:17:14
	 * @创建人：wangdong
	 */
	@RequiresPermissions("riskManage:riskProductQuota:edit")
	@RequestMapping(value = "save")
	public String saveRiskProductQuota(RiskProductQuota riskProductQuota, Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			//查询是否存在相同产品名称、账户类型、银行卡持卡人类型
			Integer count = riskProductQuotaService.findByRiskProductQuotaExist(riskProductQuota);
			if (null != count && count > 0) {
				//存在相同产品和银行卡持卡人类型
				model = riskProductQuotaService.riskProductQuotaExist(riskProductQuota,model);
				//获取所有的产品
				getAllproductInfo(model);
				return "modules/riskManage/riskProductQuotaEdit";
			} else {
				//不存在相同产品和银行卡持卡人类型
				String message = "";//用于拼接错误信息
				if(null != riskProductQuota){
					if(StringUtils.isBlank(riskProductQuota.getProductName())){
						message += " 产品名称为空";
					}
					if(StringUtils.isBlank(riskProductQuota.getAccountType())){
						message += " 账户类型为空";				
					}
					//账户类型为对私的时候，验证银行卡类型
					if(StringUtils.equals(riskProductQuota.getAccountType(), AccountType.PRIVAT.getValue())){
						if(StringUtils.isBlank(riskProductQuota.getBankcardType())){
							message += "银行卡类型为空";				
						}
					}
					if(null == riskProductQuota.getPerItem()){
						message += " 单笔限额为空";
					}
					if(null == riskProductQuota.getPerDay()){
						message += " 单日限额为空";
					}
					if(null == riskProductQuota.getPerMonth()){
						message += " 单月限额为空";
					}
				}
				//判断是否存在为空的字段  用于提示错误信息
				if(StringUtils.isBlank(message)){
//					riskProductQuotaService.saveRiskProductQuota(riskProductQuota,request);
					
					if (null != riskProductQuota.getProId()){
						riskProductQuota.setUpdateAuthor(UserUtils.getUser().getName());
						
						riskServiceClient.editRiskProductQuota(JsonMapperUtil.nonEmptyMapper().toJson(riskProductQuota));
						riskLogsService.savaEntity("更新", "产品限额数据更新:产品名称:"+riskProductQuota.getProductName(), request);
					}else{
						//如果是对公的，银行类型设置为null
						if(StringUtils.isBlank(riskProductQuota.getBankcardType())){
							riskProductQuota.setBankcardType(null);
						}
						riskProductQuota.setCreateTime(new Date());
						riskProductQuota.setCreateAuthor(UserUtils.getUser().getName());
							
						riskServiceClient.addRiskProductQuota(JsonMapperUtil.nonEmptyMapper().toJson(riskProductQuota));
						riskLogsService.savaEntity("插入", "产品限额数据插入:产品名称:"+riskProductQuota.getProductName(), request);
					}
					
					addMessage(redirectAttributes, "保存默认限额成功");
					return "redirect:" + adminPath + "/riskManage/riskProductQuotaQuery/";
				}else{
					//存在错误信息
					model = riskProductQuotaService.goToRiskProductQuotaAddOrEditJsp(riskProductQuota, model);
					//获取所有的产品
					getAllproductInfo(model);
					model.addAttribute("message","保存默认限额:"+message);
					return "modules/riskManage/riskProductQuotaEdit";
				}
			}
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaController saveRiskProductQuota has a error:{添加或者修改字典明细错误  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @方法说明：修改产品限额的状态
	 * @时间：2016年11月21日 上午10:03:34
	 * @创建人：wangdong
	 */
	@RequiresPermissions("riskManage:riskProductQuota:edit")
	@RequestMapping(value = "status")
	public String editStatus(RiskProductQuota riskProductQuota,  Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
//			riskProductQuotaService.saveRiskProductQuota(riskProductQuota,request);
			
			RiskProductQuota temp = riskProductQuotaService.get(riskProductQuota.getProId());
			temp.setUpdateAuthor(UserUtils.getUser().getName());
			temp.setStatus(riskProductQuota.getStatus());
			
			riskServiceClient.editRiskProductQuota(JsonMapperUtil.nonEmptyMapper().toJson(temp));
			riskLogsService.savaEntity("更新", "产品限额数据更新:产品名称:"+riskProductQuota.getProductName(), request);
			
			return "redirect:" + adminPath + "/riskManage/riskProductQuotaQuery/";
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaController editStatus has a error:{修改产品限额的状态错误  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * @注意：修改该方法时也要修改   RiskOrderController  RiskMerchantProductQuotaController 中的同名方法
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