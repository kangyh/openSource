package com.heepay.manage.modules.riskManage.web;

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
import com.heepay.common.util.StringUtils;
import com.heepay.enums.TransType;
import com.heepay.enums.risk.HandlingRule;
import com.heepay.enums.risk.LogicalRelationshipRule;
import com.heepay.enums.risk.ProductQuotaType;
import com.heepay.enums.risk.QuotaType;
import com.heepay.enums.risk.RiskMerChantStatus;
import com.heepay.enums.risk.RiskOrderDealType;
import com.heepay.enums.risk.RiskOrderStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.ProductCService;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.risk.entity.RiskOrder;
import com.heepay.manage.modules.risk.entity.RiskRule;
import com.heepay.manage.modules.risk.entity.RiskRuleDetail;
import com.heepay.manage.modules.risk.service.RiskOrderService;
import com.heepay.manage.modules.risk.service.RiskRuleDetailService;
import com.heepay.manage.modules.risk.service.RiskRuleService;

/**
*
* 描 述： 风险订单查询Controller
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
@RequestMapping(value = "${adminPath}/riskManage/riskOrderQuery")
public class RiskOrderController extends BaseController {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private RiskOrderService riskOrderService;
	
	@Autowired
	private RiskRuleDetailService riskRuleDetailService;
	
	@Autowired
	private RiskRuleService riskRuleService;
	
	@Autowired
	private ProductCService productCService;
	
	/**
	 * 
	 * @方法说明：产品限额信息查询
	 * @时间：2016年10月26日 下午3:26:17
	 * @创建人：wangdong
	 */
	@RequiresPermissions("riskManage:riskOrder:view")
	@RequestMapping(value = { "list", "" })
	public String list(RiskOrder riskOrder, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception{
		try {
			model = riskOrderService
					.findRiskOrderPage(new Page<RiskOrder>(request, response), riskOrder,model);
			//获取所有的产品
			getAllproductInfo(model);
		} catch (Exception e) {
			logger.error("RiskOrderController list has a error:", e);
		}
		return "modules/riskManage/riskOrderList";
	}
	
	/**
	 * @方法说明：风险订单信息导出
	 * @时间：2016年11月22日14:41:46
	 * @创建人：wangdong
	 */
	@RequiresPermissions("riskManage:riskOrder:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes,RiskOrder riskOrder, HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			riskOrderService.exportRiskOrderExcel(riskOrder,request,response);
		} catch (Exception e) {
			logger.error("RiskOrderController export has a error:{风险订单信息导出出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
		
	}
	
	/**
	 * @方法说明：添加/修改风险订单信息页面跳转
	 * @时间：2016年11月18日18:55:48
	 * @创建人：wangdong
	 */
	@RequiresPermissions("riskManage:riskOrder:view")
	@RequestMapping(value = "show")
	public String show(RiskOrder riskOrder, Model model) throws Exception{
		try {
			if(null != riskOrder){
				if(null != riskOrder.getRiskId()){
					riskOrder = riskOrderService.get(riskOrder.getRiskId());
					//交易类型
					if(StringUtils.isNotBlank(riskOrder.getTransType())){
						riskOrder.setTransType(TransType.labelOf(riskOrder.getTransType()));
					}
					//风险订单处理方式
					if(StringUtils.isNotBlank(riskOrder.getOrderDealwith())){
						riskOrder.setOrderDealwith(RiskOrderDealType.labelOf(riskOrder.getOrderDealwith()));
					}
					//风险订单状态
					if(StringUtils.isNotBlank(riskOrder.getOrderStatus())){
						riskOrder.setOrderStatus(RiskOrderStatus.labelOf(riskOrder.getOrderStatus()));
					}
					riskOrder.setQuotaItem(ProductQuotaType.labelOf(riskOrder.getQuotaItem()));
					model.addAttribute("riskOrder",riskOrder);
				}
				//获取规则明细信息和规则信息
				if(StringUtils.isNotBlank(riskOrder.getRuleDetailId())){
					RiskRuleDetail riskRuleDetail = riskRuleDetailService.get(Long.valueOf(riskOrder.getRuleDetailId()));
					if(null != riskRuleDetail && null != riskRuleDetail.getRuleId() && StringUtils.isNotBlank(riskRuleDetail.getRuleId().toString())){
						riskRuleDetail.setRelationKey(LogicalRelationshipRule.labelOf(riskRuleDetail.getRelationKey()));
						riskRuleDetail.setStatus(RiskMerChantStatus.labelOf(riskRuleDetail.getStatus()));
						model.addAttribute("riskRuleDetail",riskRuleDetail);
						RiskRule riskRule = riskRuleService.get(riskRuleDetail.getRuleId());
						riskRule.setHandleType(HandlingRule.labelOf(riskRule.getHandleType()));
						model.addAttribute("riskRule",riskRule);
					}
				}
				
			}
			return "modules/riskManage/riskOrderShow";
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaController editRiskProductQuota has a error:{添加/修改风险订单信息页面跳转错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @方法说明：根据类型查询限额信息
	 * @时间：2016年11月28日 下午9:10:39
	 * @创建人：wangdong
	 */
	@RequiresPermissions("riskManage:riskOrder:view")
	@RequestMapping(value = "search")
	public String search(RiskOrder riskOrder, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			String referer = "";
			String riskId = request.getParameter("riskId");
			String flg = request.getParameter("flg");
			if(StringUtils.isNotBlank(riskId)){
				riskOrder = riskOrderService.get(Long.valueOf(riskId));
				if(riskOrder != null && StringUtils.equals(riskOrder.getQuotaType(),QuotaType.MERCHANTPRODUCT.getValue())){
					referer = "redirect:" + adminPath + "/riskManage/riskMerchantProductQuotaQuery?flg="+flg+"&merProId="+riskOrder.getQuotaId();
				}else if(StringUtils.equals(riskOrder.getQuotaType(),QuotaType.PRODUCT.getValue())){
					referer = "redirect:" + adminPath + "/riskManage/riskProductQuotaQuery?flg="+flg+"&proId="+riskOrder.getQuotaId();
				}
			}
			return referer;
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaController editRiskProductQuota has a error:{添加/修改风险订单信息页面跳转错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
			
	
	/**
	 * @注意：修改该方法时也要修改   RiskProductQuotaController RiskMerchantProductQuotaController 中的同名方法
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
