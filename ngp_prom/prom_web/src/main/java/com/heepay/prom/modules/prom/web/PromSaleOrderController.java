/**
 * 
 */
package com.heepay.prom.modules.prom.web;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.web.BaseController;
import com.heepay.prom.modules.prom.entity.PromSaleOrder;
import com.heepay.prom.modules.prom.entity.PromSaleOrderBuild;
import com.heepay.prom.modules.prom.enums.ProfitStatus;
import com.heepay.prom.modules.prom.service.PromSaleOrderService;
import com.heepay.prom.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 李震
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/prom/promSaleOrder/view")
public class PromSaleOrderController  extends BaseController {
	@Autowired
	private PromSaleOrderService promSaleOrderService;
	
	/**
	 * 
	 * @方法说明：查询众安销售单列表
	 * @时间：2017年9月14日
	 * @创建人：李震
	 */
	@RequiresPermissions("prom:promSaleOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(PromSaleOrder promSaleOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		String pageNo = request.getParameter("pageNo");
		model =  promSaleOrderService.findPageList(new Page<PromSaleOrder>(request, response), promSaleOrder, model, pageNo);
		return "modules/prom/promSaleOrderQuery";
	}
	/**
	 * 
	 * @方法说明：批量删除销售单
	 * @时间：2017年9月14日
	 * @创建人：李震
	 */
	@RequiresPermissions("prom:promSaleOrder:edit")
	@RequestMapping(value ="deleteManyData")
	@ResponseBody
	public String deleteManyData(HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response){
		logger.info("批量------>{删除}----->{开始}");
//		String delType = request.getParameter("delType");//1按编号批量删除,2按批次删除
		String saleIds = request.getParameter("saleIds");
//		String orderBatch = request.getParameter("orderBatch") ;
		PromSaleOrder order = null;
		order = new PromSaleOrder();
		order.setSaleIds(saleIds.split(","));
		int num = 0;
		String msg = "";
		try {
			num = promSaleOrderService.deletePromSaleList(order);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "删除异常!"+e.getMessage();
			return msg;
		}
		if(num>0){
			msg = "删除成功"+order.getSaleIds().length+"条数据!";
		}else{
			msg = "删除失败"+order.getSaleIds().length+"条数据!";
		}
		logger.info("批量------>{删除}----->{开始}");
		return msg;
	}
	
	/**
	 * 
	 * @方法说明：生成销售单和分润单
	 * @时间：2017年9月15日
	 * @创建人：李震
	 */
	@RequiresPermissions("prom:promSaleOrder:edit")
	@RequestMapping(value ="saveGenSaleOrder")
	@ResponseBody
	public String saveGenSaleOrder(HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response){
		
		String orderBatch = request.getParameter("orderBatch");
		if(StringUtil.isBlank(orderBatch)) {
			return "批次号不能为空！";
		}
		logger.info("生成销售单和分润单开始,批次号="+orderBatch);
		String remark = request.getParameter("remark");
		PromSaleOrderBuild promSaleOrderBuild = null;
		promSaleOrderBuild = new PromSaleOrderBuild();
		promSaleOrderBuild.setOrderBatch(orderBatch);
		promSaleOrderBuild.setOperator(UserUtils.getUser().getName());
		promSaleOrderBuild.setRemark(remark);
		promSaleOrderBuild.setProfitTime(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
		promSaleOrderBuild.setIsProfit(ProfitStatus.UNPROFIT.getValue());
		try {
			return promSaleOrderService.generateSaleAndProfitOrder(promSaleOrderBuild);
		} catch (Exception e) {
			e.printStackTrace();
			return "生成销售单和分润单失败,批次号="+orderBatch+";"+e.getMessage();
		}
	}
	
	@RequiresPermissions("prom:promSaleOrder:edit")
	@RequestMapping(value = {"initGenSaleOrder"})
	public String initGenSaleOrder(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		return "modules/prom/promSaleOrderAdd";
	}
}
