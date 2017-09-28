/**
 * 
 */
package com.heepay.prom.modules.prom.web;

import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.web.BaseController;
import com.heepay.prom.modules.prom.entity.PromProfitOrder;
import com.heepay.prom.modules.prom.service.PromProfitOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/prom/promProfitOrder/view")
public class PromProfitOrderController  extends BaseController {
	
	@Autowired
	private PromProfitOrderService promProfitOrderService;
	
	/**
	 * 查询众安分润单列表
	 * @方法说明：
	 * @时间：2017年9月14日
	 * @创建人：李震
	 */
	@RequiresPermissions("prom:promProfitOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(PromProfitOrder promProfitOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		String pageNo = request.getParameter("pageNo");
		model =  promProfitOrderService.findPageList(new Page<PromProfitOrder>(request, response), promProfitOrder, model, pageNo);
		return "modules/prom/promProfitOrderQuery";
	}
	/**
	 * 
	 * @方法说明：批量删除分润单
	 * @时间：
	 * @创建人：
	 */
	@RequiresPermissions("prom:promProfitOrder:edit")
	@RequestMapping(value ="deleteManyData")
	@ResponseBody
	public String deleteManyData(HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response){
		logger.info("批量------>{删除}----->{开始}");
//		String delType = request.getParameter("delType");//1按编号批量删除,2按批次删除
		String profitIds = request.getParameter("profitIds");
//		String orderBatch = request.getParameter("orderBatch") ;
		PromProfitOrder order = null;
		order = new PromProfitOrder();
		order.setProfitIds(profitIds.split(","));
		int num = 0;
		String msg = "";
		try {
			num = promProfitOrderService.deletePromProfitList(order);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "删除异常!"+e.getMessage();
			return msg;
		}
		if(num>0){
			msg = "删除成功"+order.getProfitIds().length+"条数据!";
		}else{
			msg = "删除失败"+order.getProfitIds().length+"条数据!";
		}
		logger.info("批量------>{删除}----->{开始}");
		return msg;
	}

}
