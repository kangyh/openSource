/**
 *  
 */
package com.heepay.manage.modules.merchant.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.modules.merchant.dao.ProductTrxTypeDao;
import com.heepay.manage.modules.merchant.vo.ProductTrxType;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferRecord;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.pagehelper.Constant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.CommonStatus;
import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.ProductCService;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.springframework.web.util.HtmlUtils;

/**
 * 
 * 描 述：产品信息controller
 *
 * 创 建 者： ly 创建时间： 2016年9月2日 下午5:00:19 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */

@Controller
@RequestMapping(value = "${adminPath}/merchant/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductCService productCService;

    @Autowired
    private ProductTrxTypeDao productTrxTypeDao;

    /**
     * @discription 根据id获取产品信息
     * @author ly
     * @created 2016年9月2日 下午5:01:16
     * @param id
     * @return
     */
    @ModelAttribute
    public Product get(@RequestParam(required = false) String id) {
        Product entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = productCService.get(id);
        }
        if (entity == null) {
            entity = new Product();
        }
        return entity;
    }

    /**
     * @discription 获取产品信息列表
     * @author ly
     * @created 2016年9月2日 下午5:02:03
     * @param product
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:product:view")
    @RequestMapping(value = { "list", "" })
    public String list(Product product, HttpServletRequest request, HttpServletResponse response, Model model) {

        //使用cookie保存查询条件
        product = (Product) SaveConditions.result(product, "productsController", request, response);

        Page<Product> page = new Page<Product>(request, response);
        if (StringUtils.isNotBlank(product.getName())) {
            product.setName(HtmlUtils.htmlUnescape(product.getName()));
        }
        page.setOrderBy("update_time desc");
        page = productCService.findPage(page, product);
        page.setList(EnumView.productList(page.getList()));

        model.addAttribute("page", page);
        model.addAttribute("product", product);
        return "modules/merchant/productList";
    }

    /**
     * @discription 获取产品信息新增修改页面
     * @author ly
     * @created 2016年9月2日 下午5:02:14
     * @param product
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:product:view")
    @RequestMapping(value = "form")
    public String form(Product product, Model model) {
        model.addAttribute("product", product);
        return "modules/merchant/productForm";
    }

    /**
     * @discription 获取产品信息审核页面
     * @author ly
     * @created 2016年9月2日 下午5:02:50
     * @param product
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:product:audit")
    @RequestMapping(value = "audit")
    public String audit(Product product, Model model) {
        if(StringUtils.isNotBlank(product.getCreateBy().getId())){
            product.setCreateName(UserUtils.get(product.getCreateBy().getId()).getName());
        }
        model.addAttribute("product", product);
        return "modules/merchant/productAudit";
    }

    /**
     * @discription 保存修改产品信息
     * @author ly
     * @created 2016年9月2日 下午5:03:00
     * @param product
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("merchant:product:edit")
    @RequestMapping(value = "save")
    public String save(Product product, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, product)) {
            return form(product, model);
        }
        if (StringUtils.isNotBlank(product.getName())) {
            product.setName(HtmlUtils.htmlUnescape(product.getName()));
        }
        Product productExist = productCService.existByName(product);
        if(null != productExist){
            model.addAttribute("msg","产品名称重复");
            return form(product, model);
        }
        productCService.save(product);
        addMessage(redirectAttributes, "保存产品成功");
        return "redirect:" + Global.getAdminPath() + "/merchant/product?cache=1&repage";
    }

    /**
     * @discription 修改产品信息状态
     * @author ly
     * @created 2016年9月2日 下午5:03:18
     * @param product
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("merchant:product:audit")
    @RequestMapping(value = "status")
    public String status(Product product, RedirectAttributes redirectAttributes) {
        productCService.status(product);
        addMessage(redirectAttributes, "成功");
        return "redirect:" + Global.getAdminPath() + "/merchant/product?cache=1&repage";
    }

    /**
     * @discription 修改产品信息审核状态
     * @author ly
     * @created 2016年9月2日 下午5:03:33
     * @param product
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("merchant:product:audit")
    @RequestMapping(value = "auditStatus")
    public String auditStatus(Product product, RedirectAttributes redirectAttributes) {
        if (RouteStatus.AUDIT_SUCCESS.getValue().equals(product.getAuditStatus())) {
            product.setStatus(CommonStatus.ENABLE.getValue());
        }
        productCService.status(product);
        addMessage(redirectAttributes, "成功");
        return "redirect:" + Global.getAdminPath() + "/merchant/product?cache=1&repage";
    }

    /**
     * @discription 删除产品
     * @author ly
     * @created 2016年11月1日 上午11:32:43
     * @param product
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("merchant:product:audit")
    @RequestMapping(value = "delete")
    public String delete(Product product, RedirectAttributes redirectAttributes) {
        productCService.delete(product);
        addMessage(redirectAttributes, "删除产品成功");
        return "redirect:" + Global.getAdminPath() + "/merchant/product?cache=1&repage";
    }

    /**
     * @discription 产品信息
     * @author ly
     * @created 2016年11月1日 上午11:33:13
     * @param product
     * @param model
     * @return
     */
    @RequiresPermissions("merchant:product:view")
    @RequestMapping(value = "details")
    public String details(Product product, Model model) {
        product = EnumView.changeProduct(product);
        model.addAttribute("product", product);
        return "modules/merchant/productDetails";
    }

    /**
     * @discription 根据业务类型获取产品信息
     * @author ly
     * @created 2016年11月17日19:46:30
     * @param businessType
     * @param
     * @return
     */
    @RequestMapping(value = "getProduct")
    @ResponseBody
    public String getProduct(String businessType) {
        if(Constants.IS_CHECK_YES.equals(DictUtils.getDictValue(Constants.IS_BUSINESSTYPE,
                Constants.SYS_COMMON,""))){
            businessType = "";
        }
        List<Product> list = productCService.getProductList(businessType);
        return new JsonMapperUtil().toJson(list);
    }

    /**
     * @discription 根据产品code获取交易类型
     * @author ly
     * @created 2016年11月17日19:46:30
     * @param
     * @param code
     * @return
     */
    @RequestMapping(value = "getTypeByCode")
    @ResponseBody
    public String getTypeByCode(String code) {
        Product product = productCService.selectByCode(code);
        return new JsonMapperUtil().toJson(product);
    }

    /**
     * @discription 根据业务类型获取交易类型
     * @author ly
     * @created 2017年1月6日14:37:56
     * @param businessType
     * @return
     */
    @RequestMapping(value = "getProductTrxType")
    @ResponseBody
    public String getProductTrxType(String businessType) {
        List<ProductTrxType> list = productTrxTypeDao.selectListByBusinessType(businessType);
        return new JsonMapperUtil().toJson(list);
    }

}