package com.heepay.manage.modules.tpds.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.ChannelType;
import com.heepay.enums.ProductType;
import com.heepay.enums.TransType;
import com.heepay.enums.tpds.CutDay;
import com.heepay.enums.tpds.Status;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.manage.modules.tpds.entity.TpdsBindInterface;
import com.heepay.manage.modules.tpds.entity.TpdsMerchant;
import com.heepay.manage.modules.tpds.entity.TpdsProductKey;
import com.heepay.manage.modules.tpds.service.TpdsMerchantService;
import com.heepay.manage.modules.tpds.service.TpdsProductKeyService;
import com.heepay.manage.modules.tpds.web.rpc.ConfigServiceClient;

/**
 * *
 * 
* 
* 描    述： 商户产品密钥配置
*
* 创 建 者： wangjie
* 创建时间：  2017年3月2日下午7:03:19
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
@RequestMapping(value = "${adminPath}/tpds/tpdsProductKey")
public class TpdsProductKeyController extends BaseController{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private TpdsProductKeyService tpdsProductKeyService;
	
	@Autowired
	private ConfigServiceClient configServiceClient;
	
	/**
	 * 
	 * @方法说明：商户产品密钥配置的查询方法
	 * @时间：9 Feb 201713:14:55
	 * @创建人：wangjie
	 */
	@RequiresPermissions("tpds:tpdsProductKey:view")
	@RequestMapping(value = { "list", "" })
	public String list(TpdsProductKey tpdsProductKey, HttpServletRequest request,HttpServletResponse response, Model model) {
		
		Page<TpdsProductKey> page = tpdsProductKeyService.findPage(new Page<TpdsProductKey>(request, response), tpdsProductKey);
		logger.info("操作日志表------>{查询记录}"+ page.getList());
		
		for (TpdsProductKey tpdsProductKey1 : page.getList()) {
			//状态  ENABLE(启用) DISABL(禁用)
			if(StringUtils.isNotBlank(tpdsProductKey1.getTransType())){
				tpdsProductKey1.setTransType(TransType.labelOf(tpdsProductKey1.getTransType()));
			}
		}
		
		//交易类型
		List<EnumBean> transType = Lists.newArrayList();
		for (TransType checkFlg : TransType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			transType.add(ct);
		}
		model.addAttribute("transType", transType);
		
		//产品名称
		List<EnumBean> productType = Lists.newArrayList();
		for (ProductType checkFlg : ProductType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			productType.add(ct);
		}
		model.addAttribute("productType", productType);
		
		
		model.addAttribute("page", page);
		model.addAttribute("tpdsProductKey", tpdsProductKey);
	
		logger.info("操作日志表------>{}"+request);
		return "modules/tpds/tpdsProductKey";
	}
	
	
	@RequiresPermissions("tpds:tpdsProductKey:edit")
	@RequestMapping(value = "/updateEntity")
	public String updateEntity(@RequestParam(value = "keyId", required = false) Integer keyId, 
			@RequestParam(value = "read", required = false) String read,
			Model model) {

		TpdsProductKey tpdsProductKey = tpdsProductKeyService.getEntityById(keyId);
		
		//交易类型
		List<EnumBean> transType = Lists.newArrayList();
		for (TransType checkFlg : TransType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			transType.add(ct);
		}
		model.addAttribute("transType", transType);
		
		//产品名称
		List<EnumBean> productName = Lists.newArrayList();
		for (ProductType checkFlg : ProductType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			productName.add(ct);
		}
		model.addAttribute("productName", productName);
		
	    model.addAttribute("tpdsProductKey", tpdsProductKey);
	    return "modules/tpds/tpdsProductKeyUpdate";

	}
	
	
	@RequiresPermissions("tpds:tpdsProductKey:edit")
	@RequestMapping(value = "/addEntity")
	public String addEntity(@RequestParam(value = "keyId", required = false) Integer keyId, 
			@RequestParam(value = "read", required = false) String read,
			Model model) {
		TpdsProductKey tpdsProductKey = new TpdsProductKey();
		//交易类型
		List<EnumBean> transType = Lists.newArrayList();
		for (TransType checkFlg : TransType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			transType.add(ct);
		}
		model.addAttribute("transType", transType);
		
		//产品名称
		List<EnumBean> productName = Lists.newArrayList();
		for (ProductType checkFlg : ProductType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			productName.add(ct);
		}
		model.addAttribute("productName", productName);
		
	    model.addAttribute("tpdsProductKey", tpdsProductKey);
	    return "modules/tpds/tpdsProductKeyAdd";
	}
	
	@RequiresPermissions("tpds:tpdsProductKey:edit")
	@RequestMapping(value = "/save")
	public String save(TpdsProductKey tpdsProductKey, Model model, RedirectAttributes redirectAttributes,
					   @RequestParam(value="keyId",required = false) Integer keyId
                        ) throws Exception {
		JsonMapperUtil jsonUtil = new JsonMapperUtil();
		if(keyId==null){
			tpdsProductKey.setCreateDate(new Date());
			tpdsProductKey.setCreateOperator(UserUtils.getUser().getName());
			tpdsProductKey.setProductCode(tpdsProductKey.getProductName());
			tpdsProductKey.setProductName(ProductType.labelOf(tpdsProductKey.getProductName()));
			//int num = tpdsProductKeyService.saveEntity(tpdsProductKey);
			String msg = configServiceClient.addMerchantProduct(jsonUtil.toJson(tpdsProductKey));
			
            if("1".equals(msg)){
            	addMessage(redirectAttributes, "添加成功");
            }else{
            	addMessage(redirectAttributes, "添加失败");
            }
        	
		}else{
			
			tpdsProductKey.setUpdateTime(new Date());
			tpdsProductKey.setUpdateOperator(UserUtils.getUser().getName());
			tpdsProductKey.setProductCode(tpdsProductKey.getProductName());
			tpdsProductKey.setProductName(ProductType.labelOf(tpdsProductKey.getProductName()));
			//int num = tpdsProductKeyService.updateEntity(tpdsProductKey);
			String msg = configServiceClient.editMerchantProduct(jsonUtil.toJson(tpdsProductKey));
			if("1".equals(msg)){
	        	addMessage(redirectAttributes, "更新成功");
	        }else{
	        	addMessage(redirectAttributes, "更新失败");
	        }
		} 
		return "redirect:"+Global.getAdminPath()+"/tpds/tpdsProductKey";
	}
}
