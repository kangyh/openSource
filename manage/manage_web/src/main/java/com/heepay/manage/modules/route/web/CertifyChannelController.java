/**
 *  
 */
package com.heepay.manage.modules.route.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.manage.common.utils.DictList;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.CommonStatus;
import com.heepay.manage.modules.merchant.service.ProductCService;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.entity.CertifyChannel;
import com.heepay.manage.modules.route.service.CertifyChannelService;


/**          
* 
* 描    述：实名认证通道管理Controller
*
* 创 建 者： L.M
* 创建时间： 2016年9月5日 下午8:04:34 
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
@RequestMapping(value = "${adminPath}/route/certifyChannel")
public class CertifyChannelController extends BaseController {

    @Autowired
    private CertifyChannelService certifyChannelService;

    @Autowired
    private ProductCService productCService;

    @ModelAttribute
    public Product get(@RequestParam(required=false) String id) {
        Product entity = null;
        if (StringUtils.isNotBlank(id)){
          entity = productCService.get(id);
        }
        if (entity == null){
          entity = new Product();
        }
        return entity;
    }

    /**
     * 实名认证List页面
     * @param product
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequiresPermissions("route:certifyChannel:view")
	@RequestMapping(value = {"list", ""})
	public String list(Product product, HttpServletRequest request, HttpServletResponse response, Model model) {
	    String comprehensiveCode = DictList.comprehensiveCode();
        String code = "";
        if(StringUtils.isNotBlank(comprehensiveCode)){
            code = "in ('"+comprehensiveCode+"')";
        }
        product.setCode(code);
//        product.setName("实名认证");
        Page<Product> page = productCService.findCertifyPage(new Page<Product>(request, response), product);
        //产品状态枚举类由值置内容
        if(null!=page.getList() && !page.getList().isEmpty()) {
            page.setList(EnumView.productList(page.getList()));
        }
        model.addAttribute("page", page);
        return "modules/route/certifyChannelList";
	}

    /**
     * 实名认证添加
     * @param productId
     * @param certifyChannel
     * @param model
     * @return
     */
	@RequiresPermissions("route:certifyChannel:view")
	@RequestMapping(value = "form")
    public String form(String productId,CertifyChannel certifyChannel, Model model) {
	    Product product = productCService.get(productId);
        model.addAttribute("productId", productId);
        model.addAttribute("productName", product.getName());
        model.addAttribute("productCode", product.getCode());
        model.addAttribute("certifyChannel", certifyChannel);
        return "modules/route/certifyChannelForm";
	}

    /**
     * 实名认证修改
     * @param id
     * @param productId
     * @param model
     * @return
     */
    @RequiresPermissions("route:certifyChannel:edit")
    @RequestMapping(value = "update")
    public String update(String id,String productId, Model model) {
        CertifyChannel certifyChannel = certifyChannelService.get(id);
        Product product = productCService.get(productId);
        model.addAttribute("productId", productId);
        model.addAttribute("productName", product.getName());
        model.addAttribute("productCode", product.getCode());
        model.addAttribute("certifyChannel", certifyChannel);
        return "modules/route/certifyChannelForm";
    }

    /**
     * 实名认证保存
     * @param productId
     * @param certifyChannel
     * @param model
     * @param redirectAttributes
     * @return
     */
	@RequiresPermissions("route:certifyChannel:edit")
	@RequestMapping(value = "save")
    public String save(String productId,CertifyChannel certifyChannel, Model model, RedirectAttributes redirectAttributes){
        if (!beanValidator(model, certifyChannel)){
            return form(productId,certifyChannel, model);
        }
        if(StringUtils.isBlank(certifyChannel.getId())){
            CertifyChannel certifyChannelNew = certifyChannelService.selectByPartnerCode(certifyChannel.getChannelPartnerCode()
                    ,certifyChannel.getProductCode(),certifyChannel.getChannelTypeCode());
            if(null==certifyChannelNew){
                CertifyChannel certifyChannel1=certifyChannelService.combineCertifyDetail(productId,certifyChannel);
                certifyChannelService.save(certifyChannel1,false);
                addMessage(redirectAttributes, "保存认证通道成功");
            }else{
                addMessage(redirectAttributes, "此认证通道已存在");
            }
		}else{
            CertifyChannel certifyChannel1=certifyChannelService.combineCertifyDetail(productId,certifyChannel);
            certifyChannelService.save(certifyChannel1,false);
            addMessage(redirectAttributes, "保存认证通道成功");
		} 
		return "redirect:"+Global.getAdminPath()+"/route/certifyChannel/details?id="+productId;
	}

    /**
     * 实名认证详情
     * @param product
     * @param certifyChannel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("route:certifyChannel:view")
    @RequestMapping(value = "details")
    public String details(Product product,CertifyChannel certifyChannel,HttpServletRequest request, HttpServletResponse response,Model model) {
        Page<CertifyChannel>  page  = new Page<CertifyChannel>(request, response);
        //根据创建日期倒序排列
        page.setOrderBy("createDate desc");
        certifyChannel.setProductCode(product.getCode());
        page = certifyChannelService.findPage(page, certifyChannel);
        model.addAttribute("page", page);
        if(null!=page.getList() && !page.getList().isEmpty()) {
            page.setList(EnumView.certifyChannelList(page.getList()));
        }
        product=EnumView.productShow(product);
        model.addAttribute("product", product);
        return "modules/route/certifyChannelDetails";
    }
	
}