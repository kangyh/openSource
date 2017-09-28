/**
 *  
 */
package com.heepay.manage.modules.route.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.route.entity.ProductDetail;
import com.heepay.manage.modules.route.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 名称：产品明细信息操作类
 * 创建者：刘萌
 * 创建日期：2016-8-25
 * 创建描述：产品明细的查看、添加功能
 *
 * 审核者：于亮
 * 审核时间：2016年9月1日
 * 审核描述：加方法注释；
 * 
 * 修 改 者：  
 * 修改时间： 
 * 修改描述： 
 */
@Controller
@RequestMapping(value = "${adminPath}/route/productDetail")
public class ProductDetailController extends BaseController {

  	@Autowired
  	private ProductDetailService productDetailService;
  	
  	  
  	/**      
  	* @discription 根据id获取产品通道信息
  	* @author L.M
  	* @created 2016年9月7日 下午5:56:26     
  	* @param id
  	* @return     
  	*/
  	@ModelAttribute
  	public ProductDetail get(@RequestParam(required=false) String id) {
  		ProductDetail entity = null;
  		if (StringUtils.isNotBlank(id)){
  			entity = productDetailService.get(id);
  		}
  		if (entity == null){
  			entity = new ProductDetail();
  		}
  		return entity;
  	}

}