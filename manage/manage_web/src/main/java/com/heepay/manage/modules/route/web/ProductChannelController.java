/**
 *  
 */
package com.heepay.manage.modules.route.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.DictList;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.ProductCService;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.modules.route.entity.PayChannel;
import com.heepay.manage.modules.route.entity.ProductDetail;
import com.heepay.manage.modules.route.service.PayChannelService;
import com.heepay.manage.modules.route.service.ProductDetailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**          
* 
* 描    述：产品通道信息操作类
*
* 创 建 者： 刘萌
* 创建时间： 2016年9月7日 下午5:47:51 
* 创建描述：产品信息的查看、查询、支付通道添加、修改功能
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
@RequestMapping(value = "${adminPath}/route/productChannel")
public class ProductChannelController extends BaseController {
	
  	@Autowired
    private ProductDetailService productDetailService;
  	
  	@Autowired
    private PayChannelService payChannelService;
  	
  	@Autowired
    private ProductCService productCService;
  	
  	  
  	/**     
  	* @discription 根据id获取产品信息
  	* @author L.M
  	* @created 2016年9月7日 下午5:49:04     
  	* @param id
  	* @return     
  	*/
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
  	* @discription 获取产品信息列表
  	* @author L.M
    * @created 2016年9月7日 下午5:49:28
  	* @param product
  	* @param request
  	* @param response
  	* @param model
  	* @return     
  	*/
  	@RequiresPermissions("route:productChannel:view")
  	@RequestMapping(value = {"list", ""})
  	public String list(Product product,HttpServletRequest request, HttpServletResponse response, Model model) {

        //使用cookie保存查询条件
        product = (Product) SaveConditions.result(product, "products", request, response);

        String comprehensiveCode = DictList.comprehensiveCode();
        if(StringUtils.isNotBlank(comprehensiveCode)){
            String code = "not in ('"+comprehensiveCode+"')";
            product.setCode(code);
        }
        Page<Product> page = new Page<Product>(request, response);
        product.setPage(page);
        List<Product> findListChannel = productCService.findListChannel(product);
        //产品状态枚举类由值置内容
        if(null!=findListChannel && !findListChannel.isEmpty()){
            page.setList(EnumView.productList(findListChannel));
        }
        model.addAttribute("page", page);
        model.addAttribute("product", product);
        return "modules/route/productChannelList";
  	}
  	  
  	/**     
  	* @discription  获取为产品添加支付通道的页面
  	* @author L.M
    * @created 2016年9月7日 下午5:49:58
  	* @param productCode
  	* @param productId
  	* @param productName
  	* @param payChannel
  	* @param request
  	* @param response
  	* @param model
  	* @return     
  	*/
  	@RequiresPermissions("route:productChannel:view")
    @RequestMapping(value = "add")
    public String add(String productCode,String productId,String productName,PayChannel payChannel, HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String,Object> map = new HashMap<>();
        map.put("productCode", productCode);
        //支付通道查询-add方法
        map.put("bankNo", payChannel.getBankNo());
        map.put("channelPartnerCode", payChannel.getChannelPartnerCode());
        map.put("channelTypeCode", payChannel.getChannelTypeCode());
        map.put("cardTypeCode", payChannel.getCardTypeCode());
        Page<PayChannel>  page  = payChannelService.findProductChannelPage(new Page<>(request, response), map);
        model.addAttribute("bankNo", payChannel.getBankNo());
        model.addAttribute("channelPartnerCode", payChannel.getChannelPartnerCode());
        model.addAttribute("channelTypeCode", payChannel.getChannelTypeCode());
        model.addAttribute("cardTypeCode", payChannel.getCardTypeCode());
        model.addAttribute("productId", productId);
        model.addAttribute("productCode", productCode);
        model.addAttribute("productName", productName);
        model.addAttribute("page", page);
        return "modules/route/productChannelAdd";
    }
  	
  	
  /**     
    * @discription 产品添加通道的方法
    * @author L.M
    * @created 2016年9月7日 下午5:51:45
    * @param id
    * @param productId
    * @param productCode
    * @param productName
    * @param pageNo
    * @param payChannel
    * @param request
    * @param response
    * @param model
    * @return     
    */
    @RequiresPermissions("route:productChannel:view")
    @RequestMapping(value = "addChannel")
    public void addChannel(String id,String productId ,String productCode,String productName,String pageNo,PayChannel payChannel,HttpServletRequest request,HttpServletResponse response,Model model){
        //生成产品明细  
        ProductDetail productDetail=productDetailService.combineDetail(id,productId);
        productDetailService.save(productDetail,false);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("productCode", productCode);
        map.put("bankNo", payChannel.getBankNo());
        map.put("channelPartnerCode", payChannel.getChannelPartnerCode());
        map.put("channelTypeCode", payChannel.getChannelTypeCode());
        map.put("cardTypeCode", payChannel.getCardTypeCode());
        Page<PayChannel>  page  = payChannelService.findProductChannelPage(new Page<PayChannel>(request, response),map);   
        model.addAttribute("page", page);
        model.addAttribute("bankNo", payChannel.getBankNo()); 
        model.addAttribute("channelPartnerCode", payChannel.getChannelPartnerCode()); 
        model.addAttribute("channelTypeCode", payChannel.getChannelTypeCode()); 
        model.addAttribute("cardTypeCode", payChannel.getCardTypeCode()); 
        model.addAttribute("productId", productId);
        model.addAttribute("productCode", productCode);
        model.addAttribute("productName", productName);
        model.addAttribute("pageNo", pageNo);
        //return "modules/route/productChannelAdd";
    }

    /**
     * 为产品批量添加支付通道（当前页面批量操作）
     * @param checkedstr
     * @param productId
     * @param productCode
     * @param productName
     * @param pageNo
     * @param payChannel
     * @param request
     * @param response
     * @param model
     */
    @RequiresPermissions("route:productChannel:view")
    @RequestMapping(value = "addBatchChannel")
    public void addBatchChannel(String checkedstr,String productId ,String productCode,String productName,String pageNo,PayChannel payChannel,HttpServletRequest request,HttpServletResponse response,Model model){
        //批量生成产品明细
        if(StringUtils.isNotBlank(checkedstr)){
            productDetailService.saveBatchChannel(checkedstr,productId);
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("productCode", productCode);
        map.put("bankNo", payChannel.getBankNo());
        map.put("channelPartnerCode", payChannel.getChannelPartnerCode());
        map.put("channelTypeCode", payChannel.getChannelTypeCode());
        map.put("cardTypeCode", payChannel.getCardTypeCode());
        Page<PayChannel>  page  = payChannelService.findProductChannelPage(new Page<PayChannel>(request, response),map);
        model.addAttribute("page", page);
        model.addAttribute("bankNo", payChannel.getBankNo());
        model.addAttribute("channelPartnerCode", payChannel.getChannelPartnerCode());
        model.addAttribute("channelTypeCode", payChannel.getChannelTypeCode());
        model.addAttribute("cardTypeCode", payChannel.getCardTypeCode());
        model.addAttribute("productId", productId);
        model.addAttribute("productCode", productCode);
        model.addAttribute("productName", productName);
        model.addAttribute("pageNo", pageNo);
    }

      
    /**     
    * @discription 获取产品通道详情页面
    * @author L.M
    * @created 2016年9月7日 下午5:51:05
    * @param product
    * @param productDetail
    * @param request
    * @param response
    * @param model
    * @return     
    */
    @RequiresPermissions("route:productChannel:view")
    @RequestMapping(value = "details")
    public String details(Product product,ProductDetail productDetail,String pageNo,PayChannel payChannel,HttpServletRequest request, HttpServletResponse response,Model model) {
        //根据productCode查询产品明细
        productDetail.setProductCode(product.getCode()); 
        Page<ProductDetail>  page  = new Page<ProductDetail>(request, response);
        //根据创建日期倒序排列 
        page.setOrderBy("createDate desc");
        page = productDetailService.findPage(page, productDetail);
        if(null!=page.getList() && !page.getList().isEmpty()) {
            page.setList(EnumView.ProductDetail(page.getList()));
        }
        model.addAttribute("page", page);
        product=EnumView.productShow(product);
        model.addAttribute("product", product);
        model.addAttribute("productDetail", productDetail);
        model.addAttribute("pageNo", pageNo);

        //使用cookie保存查询条件(清除上次操作的cache)
        ProductDetail productDetailcc = (ProductDetail) SaveConditions.result(productDetail, "productDetails", request, response);
        return "modules/route/productChannelDetails";
    }

    /**
     * @discription 删除产品的支付通道信息
     * @author L.M
     * @created 2016年10月13日 上午11:21:49
     * @param productDetail
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("route:productChannel:edit")
    @RequestMapping(value = "delete")
    public String delete(ProductDetail productDetail,String productId,String pageNo,RedirectAttributes redirectAttributes) {
        productDetailService.delete(productDetail);
        addMessage(redirectAttributes, "删除成功");
//        return "redirect:"+ Global.getAdminPath()+"/route/productChannel/details?id="+ productId;
        return "redirect:"+ Global.getAdminPath()+"/route/productChannel/selectChannelDetail?cache=1&productId="+ productId+"&pageNo="+pageNo;
    }

    /**
     * 为产品批量删除支付通道（当前页面批量操作）
     * @param checkedstr
     * @param productId
     * @param pageNo
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequiresPermissions("route:productChannel:view")
    @RequestMapping(value = "delBatchChannel")
    public String addBatchChannel(String checkedstr,String productId ,String pageNo,RedirectAttributes redirectAttributes,Model model){
        //批量删除通道
        if(StringUtils.isNotBlank(checkedstr)){
            productDetailService.delBatchChannel(checkedstr);
        }
        addMessage(redirectAttributes, "删除成功");
//        return "redirect:"+ Global.getAdminPath()+"/route/productChannel/details?id="+ productId+"&pageNo="+pageNo;
        return "redirect:"+ Global.getAdminPath()+"/route/productChannel/selectChannelDetail?cache=1&productId="+ productId+"&pageNo="+pageNo;
    }


    @RequiresPermissions("route:productChannel:view")
    @RequestMapping(value = "selectChannelDetail")
    public String selectChannelDetail(String productId,ProductDetail productDetail, HttpServletRequest request, HttpServletResponse response, Model model){
        //使用cookie保存查询条件
        productDetail = (ProductDetail) SaveConditions.result(productDetail, "productDetails", request, response);
        Page<ProductDetail>  page  = new Page<ProductDetail>(request, response);
        //根据创建日期倒序排列
        page.setOrderBy("createDate desc");
        page = productDetailService.findPage(page, productDetail);
        if(null!=page.getList() && !page.getList().isEmpty()) {
            page.setList(EnumView.ProductDetail(page.getList()));
        }
        Product product =get(productId);
        model.addAttribute("page", page);
        product=EnumView.productShow(product);
        model.addAttribute("product", product);
        model.addAttribute("productDetail", productDetail);
//        model.addAttribute("pageNo", pageNo);
        return "modules/route/productChannelDetails";
    }
}