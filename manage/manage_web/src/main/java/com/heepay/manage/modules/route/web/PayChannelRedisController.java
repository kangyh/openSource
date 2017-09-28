/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.route.web;

import com.heepay.manage.common.cache.PayChannelRedisUtil;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.route.entity.LineBankNumber;
import com.heepay.manage.modules.route.service.LineBankNumberService;
import com.heepay.manage.modules.route.service.PayChannelService;
import com.payment.solr.bean.LineBankNumberSolr;
import com.payment.solr.client.SolrCloudClient;
import com.payment.solr.index.ReflectThread;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * 描    述：通道Redis缓存管理Controller
 *
 * 创 建 者： M.Z
 * 创建时间： 2016/9/27 10:27 
 * 创建描述：利用页面管理包括商家通道，最优通道缓存
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
@RequestMapping(value = "${adminPath}/route/PayChannelRedis")
public class PayChannelRedisController extends BaseController {

	@Autowired
	private PayChannelService payChannelService;
	@Autowired
	private LineBankNumberService lineBankNumberService;

	/**
	 * @discription 通道缓存列表
	 * @author M.Z
	 * @created 2016年9月27日
	 * @return
	 */
	@RequiresPermissions("route:PayChannelRedis:view")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/route/payChannelRedisList";
	}

	/**
	 * @discription 商户通道缓存更新
	 * @author M.Z
	 * @created 2016年9月27日
	 * @return
	 */
	@RequiresPermissions("route:PayChannelRedis:edit")
	@RequestMapping(value = {"merchantChannel"})
	public String merchantChannel() {
		PayChannelRedisUtil.queryMerchantChannel();
		return "modules/route/payChannelRedisList";
	}

	/**
	 * @discription 最优通道缓存删除
	 * @author M.Z
	 * @created 2016年10月20日16:04:14
	 * @return
	 */
	@RequiresPermissions("route:PayChannelRedis:edit")
	@RequestMapping(value = {"delBestChannel"})
	public String delBestChannel() {
		PayChannelRedisUtil.delChannelRedis(Constants.BEST_PAY_CHANNEL_KEY);
		return "modules/route/payChannelRedisList";
	}

	/**
	 * @discription 通道信息缓存删除
	 * @author M.Z
	 * @created 2016年10月20日16:04:14
	 * @return
	 */
	@RequiresPermissions("route:PayChannelRedis:edit")
	@RequestMapping(value = {"delAllPayChannel"})
	public String delAllPayChannel() {
		PayChannelRedisUtil.delChannelRedis(Constants.PAY_CHANNEL_KEY);
		return "modules/route/payChannelRedisList";
	}

	/**
	 * @discription 通道类型对应的银行信息缓存删除
	 * @author M.Z
	 * @created 2016年10月20日16:04:14
	 * @return
	 */
	@RequiresPermissions("route:PayChannelRedis:edit")
	@RequestMapping(value = {"delPayChannelType"})
	public String delPayChannelType() {
		PayChannelRedisUtil.delChannelRedis(Constants.CHANNEL_TYPE_KEY);
		return "modules/route/payChannelRedisList";
	}

	/**
	 * @discription 产品对应的银行信息缓存删除
	 * @author M.Z
	 * @created 2016年12月20日
	 * @return
	 */
	@RequiresPermissions("route:PayChannelRedis:edit")
	@RequestMapping(value = {"delBankInfoOfProduct"})
	public String delBankInfoOfProduct() {
		PayChannelRedisUtil.delChannelRedis(Constants.BANK_OF_PRODUCT_KEY);
		return "modules/route/payChannelRedisList";
	}

	/**
	 * @discription 商户通道缓存删除
	 * @author M.Z
	 * @created 2016年10月20日16:04:14
	 * @return
	 */
	@RequiresPermissions("route:PayChannelRedis:edit")
	@RequestMapping(value = {"delMerchantChannel"})
	public String delMerchantChannel() {
		PayChannelRedisUtil.delChannelRedis(Constants.MERCHANT_CHANNEL_KEY);
		return "modules/route/payChannelRedisList";
	}

	/**
	 * @discription 最优通道缓存更新
	 * @author M.Z
	 * @created 2016年9月27日
	 * @return
	 */
	@RequiresPermissions("route:PayChannelRedis:edit")
	@RequestMapping(value = {"payChannel"})
	public String payChannel() {
		PayChannelRedisUtil.queryPayChannel();
		return "modules/route/payChannelRedisList";
	}

	/**
	 * @discription 通道所有信息缓存更新
	 * @author M.Z
	 * @created 2016年9月27日
	 * @return
	 */
	@RequiresPermissions("route:PayChannelRedis:edit")
	@RequestMapping(value = {"payChannelAll"})
	public String payChannelAll() {
		PayChannelRedisUtil.queryPayChannelAll();
		return "modules/route/payChannelRedisList";
	}

	/**
	 * @discription 每种通道类型对应的银行及卡类型
	 * @author M.Z
	 * @created 2016年9月27日
	 * @return
	 */
	@RequiresPermissions("route:PayChannelRedis:edit")
	@RequestMapping(value = {"channelType"})
	public String channelType() {
		PayChannelRedisUtil.queryChannelType();
		return "modules/route/payChannelRedisList";
	}

	/**
	 * @discription 路由通道缓存删除
	 * @author M.Z
	 * @created 2016年9月27日
	 * @return
	 */
	@RequiresPermissions("route:PayChannelRedis:edit")
	@RequestMapping(value = {"delRouteMap"})
	public String delRouteMap() {
		PayChannelRedisUtil.delRouteMap(Constants.PAY_CHANNEL_ROUTE);
		return "modules/route/payChannelRedisList";
	}



	/***
	 *
	* @discription 把支行信息的数据同步到SOLR上
	* @author 杨春龙
	* @created 2017年1月18日 下午5:28:10
	* @param request
	* @param response
	* @return
	 */
	@RequiresPermissions("route:PayChannelRedis:edit")
	@RequestMapping(value = {"syncDataLineBankNumber"})
	public String syncDataToSolr(HttpServletRequest request,HttpServletResponse response)throws Exception {

		LineBankNumber entity=new LineBankNumber();

		List<LineBankNumber> list=new ArrayList<LineBankNumber>();
		Page<LineBankNumber> page=new Page<LineBankNumber>();
		page.setPageNo(0);
		page.setPageSize(1000);
		page.setOrderBy("id asc");
		page=lineBankNumberService.findPage(page, entity);
		list=page.getList();
		long count=page.getCount();
		SolrClient solrClient=SolrCloudClient.getZkSolrClient();
		long sum=0;

		while(sum<count){
			//同步到数据库
			List<LineBankNumberSolr> solrList=new ArrayList<LineBankNumberSolr>();
			for(LineBankNumber bank:list){
				LineBankNumberSolr solrBean=new LineBankNumberSolr();
				solrBean.setId(bank.getId());
				solrBean.setBankName(bank.getBankName());
				solrBean.setBankNo(bank.getBankNo());
				solrBean.setProvinceName(bank.getProvinceName());
				solrBean.setProvinceCode(bank.getProvinceCode());
				solrBean.setCityName(bank.getCityName());
				solrBean.setCityCode(bank.getCityCode());
				solrBean.setOpenBankName(bank.getOpenBankName());
				solrBean.setOpenBankCode(bank.getOpenBankCode());
				solrBean.setAssociateLineNumber(bank.getAssociateLineNumber());
				solrBean.setStatus(bank.getStatus());
				solrList.add(solrBean);
			}
			sum=sum+solrList.size();
			ReflectThread<LineBankNumberSolr> thread=new ReflectThread<LineBankNumberSolr>(
					solrClient,solrList,"line_bank_number",LineBankNumberSolr.class);
			thread.start();
			//查询下一页数据
			page.setPageNo(page.getPageNo()+1);
			page=lineBankNumberService.findPage(page, entity);

			list=page.getList();
		}
		//休息30秒再关闭链接  防止数据没提交完
		Thread.sleep(30*1000);
		solrClient.close();
		return "modules/route/payChannelRedisList";
	}
}
