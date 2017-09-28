/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.web;

import com.heepay.common.util.StringUtil;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.MerchantLog;
import com.heepay.manage.modules.payment.entity.MerchantSettleBean;
import com.heepay.manage.modules.payment.param.AccountSettleReportResult;
import com.payment.solr.bean.MerchantSettleSolrBean;
import com.payment.solr.handler.MerchantLogSettleHandler;
import com.payment.solr.params.BetweenQueryParam;
import com.payment.solr.params.PageSolr;
import com.payment.solr.query.MerchantSettleQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * 描    述：账户明细查询Controller
 *
 * 创 建 者： @author Zhangjx
 * 创建时间：
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
@RequestMapping(value = "${adminPath}/account/merchantSettle")
public class MerchantSettleController extends BaseController {


	private static Logger logger = LogManager.getLogger();

	@RequiresPermissions("account:merchantSettle:view")
	@RequestMapping(value = {"merchantSettle", ""})
	public String merchantSettle(MerchantSettleBean merchantSettleBean,HttpServletRequest request, HttpServletResponse response, Model model) {

		String pageNo = request.getParameter("pageNo");
		if(StringUtil.isBlank(pageNo)){
			pageNo="1";
		}
		String pageSize = request.getParameter("pageSize");
		if(StringUtil.isBlank(pageSize)){
			pageSize= "30";
		}
		PageSolr page = new PageSolr();
		page.setField("create_time");
		page.setOrder(SolrQuery.ORDER.desc);
		page.setStart((Integer.parseInt(pageNo)-1) * Integer.parseInt(pageSize));
		page.setRows(Integer.parseInt(pageSize));
		MerchantSettleQuery merchantSettleQuery = new MerchantSettleQuery();
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			if(StringUtil.notBlank(merchantSettleBean.getMerchantId())){
				params.put("merchant_id",merchantSettleBean.getMerchantId());
			}
			if(StringUtil.notBlank(merchantSettleBean.getTransNo())){
				params.put("trans_no",merchantSettleBean.getTransNo());
			}
			if(StringUtil.notBlank(merchantSettleBean.getMerchantSettleStatus())){
				params.put("merchant_settle_status",merchantSettleBean.getMerchantSettleStatus());
			}
			Map<String,Object> resultMerchantMap = merchantSettleQuery.selectByParams(MerchantSettleSolrBean.class,params.size()==0?null:params,page,new BetweenQueryParam());
			logger.info("查询商户结算返回,resultMerchantMap:{}",resultMerchantMap.toString());
			List<MerchantSettleSolrBean> list = new ArrayList<MerchantSettleSolrBean>();
			if(Integer.parseInt(String.valueOf(resultMerchantMap.get("count"))) != 0){
				list = (List<MerchantSettleSolrBean>)resultMerchantMap.get("list");
			}
			Page<MerchantSettleSolrBean> pageMsg = new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize), Integer.parseInt(resultMerchantMap.get("count")+""));
			pageMsg.setList(list);
			model.addAttribute("page", pageMsg);
		}catch (Exception e){
			logger.error("查询商户结算信息异常。",e);
		}
		return "modules/account/merchantSettleList";
	}

	@RequiresPermissions("account:merchantLogSettle:clear")
	@RequestMapping(value = "clearDate")
	public String clearDate(MerchantSettleBean merchantSettleBean,HttpServletRequest request, HttpServletResponse response, Model model) {
		MerchantLogSettleHandler merchantLogSettleHandler = new MerchantLogSettleHandler();
		try {
			merchantLogSettleHandler.deleteAll("merchant_settle_data");
		}catch (Exception e){
			logger.error("删除商户结算数据异常。",e);
		}
		return "redirect:"+ Global.getAdminPath()+"/account/merchantSettle/merchantSettle/?repage";
	}
}