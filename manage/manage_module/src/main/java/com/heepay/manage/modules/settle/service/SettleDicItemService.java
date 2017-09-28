package com.heepay.manage.modules.settle.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.billing.BillingBecomeEffect;
import com.heepay.enums.billing.SettleDicTypeEnum;
import com.heepay.enums.billing.SettleInterval;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.riskLogs.service.RiskLogsService;
import com.heepay.manage.modules.settle.dao.SettleDicItemDao;
import com.heepay.manage.modules.settle.entity.SettleDicItem;
import com.heepay.manage.modules.settle.entity.SettleDicType;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
*
* 描    述：清结算字典类型明细Service
*
* 创 建 者： @author wangdong
* 创建时间：2016年10月12日19:27:52
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
@Service
@Transactional(readOnly = true)
public class SettleDicItemService extends CrudService<SettleDicItemDao, SettleDicItem>{
	
	private static final Logger logger=LogManager.getLogger();
	
	@Autowired
	private SettleDicItemDao settleDicItemDao;
	
	@Autowired
	private SettleDicTypeService settleDicTypeService;
	
	@Autowired
	private RiskLogsService riskLogsService;//记录日志
	
	/**
	 * @方法说明：根据id获取清结算字典类型明细
	 * @author wangdong
	 * @param id
	 * @时间：2016年10月12日19:32:50
	 */
	public SettleDicItem get(Long id) {
		return settleDicItemDao.get(id);
	}
	
	/**
	 * @方法说明：获取清结算字典类型明细List
	 * @author wangdong
	 * @param settleDicItem
	 * @时间：2016年10月12日19:32:58
	 */
	public List<SettleDicItem> findList(SettleDicItem settleDicItem) {
		return super.findList(settleDicItem);
	}

	/**
	 * @param request 
	 * @throws Exception 
	 * @方法说明：保存/修改  字典明细
	 * @时间：2016年10月13日
	 * @创建人：wangdong
	 */
	@Transactional(readOnly = false)
	public void saveSettleDicItem(SettleDicItem settleDicItem, HttpServletRequest request) throws Exception {
		String message = "";
		try {
			if (null != settleDicItem.getItemId()){
				settleDicItem.setUpdateOperator(UserUtils.getUser().getName());
				settleDicItemDao.update(settleDicItem);
				message = "更新";
				//记录日志
				riskLogsService.savaEntity("更新", "字典明细更新:名称:"+settleDicItem.getText()+",值:"+settleDicItem.getValue()+",类型ID:"+settleDicItem.getTypeId(), request);
			}else{
				settleDicItem.setValue(settleDicItem.getValue().toUpperCase());
				settleDicItem.setCreateOperator(UserUtils.getUser().getName());
				settleDicItemDao.insert(settleDicItem);
				message = "插入";
				//记录日志
				riskLogsService.savaEntity("插入", "字典明细插入:名称:"+settleDicItem.getText()+",值:"+settleDicItem.getValue()+",类型ID:"+settleDicItem.getTypeId(), request);
			}
		} catch (Exception e) {
			logger.error("SettleDicItemService saveSettleDicItem has a error:{"+message+"字典明细错误  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * @throws Exception 
	 * @方法说明：查询是否存在相同编码
	 * @时间：2016年10月13日
	 * @创建人：wangdong
	 */
	public Integer findBySettleDicItemExist(SettleDicItem settleDicItem) throws Exception {
		try {
			return settleDicItemDao.findBySettleDicItemExist(settleDicItem);
		} catch (Exception e) {
			logger.error("SettleDicItemService findCodeBySettleDicItemExist has a error:",e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：查询明细根据编码，名称，类型去重
	 * @时间：2016年10月25日 下午7:57:30
	 * @创建人：wangdong
	 */
	public List<SettleDicItem> findDistinctItem() throws Exception {
		try {
			return settleDicItemDao.findDistinctItem();
		} catch (Exception e) {
			logger.error("SettleDicItemService findDistinctItem has a error:{查询明细根据编码，名称，类型去重出错 FIND_ERROR}",e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @param model 
	 * @throws Exception 
	 * @方法说明：清结算字典明细管理List 
	 * @时间：2016年11月16日 下午6:48:48
	 * @创建人：wangdong
	 */
	public Model findSettleDicItemPage(Page<SettleDicItem> page, SettleDicItem settleDicItem, Model model) throws Exception {
		try {
			Page<SettleDicItem> pageSettleDicItem = findPage(page,settleDicItem);

			List<SettleDicItem> itemStatusList = Lists.newArrayList();
			//循环翻译部分字段
			for (SettleDicItem item : pageSettleDicItem.getList()) {
				// 状态
				if (StringUtils.isNotBlank(item.getStatus())) {
					item.setStatus(BillingBecomeEffect.labelOf(item.getStatus()));
				}
				itemStatusList.add(item);
			}
			pageSettleDicItem.setList(itemStatusList);

			// 获取字典类型   用于页面的查询条件
			List<EnumBean> typeList = Lists.newArrayList();
			settleDicItem.setFlg("list");
			List<SettleDicType> typeStatusList = settleDicTypeService.findDistinctTypeIdType(settleDicItem);
			for (SettleDicType type : typeStatusList) {
				EnumBean enums = new EnumBean();
				enums.setValue(type.getTypeId().toString());
				enums.setName(type.getText());
				typeList.add(enums);
			}
			model.addAttribute("typeList", typeList);

			// 获取字典明细    用于页面的查询条件
			List<EnumBean> itemList = Lists.newArrayList();
			List<SettleDicItem> dicItems = settleDicItemDao.findDistinctItem();
			for (SettleDicItem item : dicItems) {
				EnumBean enums = new EnumBean();
				enums.setValue(item.getValue());
				enums.setName(item.getText());
				itemList.add(enums);
			}
			model.addAttribute("itemList", itemList);
			// 前端页面 类型条件显示
			CommonUtil.enumsShow(model,Constants.Clear.SETTLE);
			model.addAttribute("page", pageSettleDicItem);
			return model;
		} catch (Exception e) {
			logger.error("SettleDicItemService findSettleDicItemPage has a error:{清结算字典明细管理List出错  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：添加字典明细页面跳转
	 * @时间：2016年11月16日 下午6:54:10
	 * @创建人：wangdong
	 */
	public Model goToSettleDicItemAddJsp(SettleDicItem settleDicItem, Model model) throws Exception {
		try {
			// 获取字典类型
			List<EnumBean> typeList = Lists.newArrayList();
			settleDicItem.setFlg("add");//用于区分是列表查询还是添加查询
			List<SettleDicType> typeStatusList = settleDicTypeService.findDistinctTypeIdType(settleDicItem);
			//获取字典类型  用于添加明细时选择类型下拉选
			for (SettleDicType type : typeStatusList) {
				EnumBean enums = new EnumBean();
				enums.setValue(type.getTypeId().toString());
				enums.setName(type.getText());
				typeList.add(enums);
			}
			model.addAttribute("typeList", typeList);

			// 修改查询当前类型明细
			if (null != settleDicItem && null != settleDicItem.getItemId()) {
				settleDicItem = get(settleDicItem.getItemId().toString());
				model.addAttribute("settleDicItem", settleDicItem);
			}

			List<EnumBean> textList = Lists.newArrayList();
			//结算区间类型  显示商户侧和通道侧名称下拉选
			for (SettleInterval status : SettleInterval.values()) {
				EnumBean ct = new EnumBean();
				ct.setValue(status.getValue());
				ct.setName(status.getContent());
				textList.add(ct);
			}
			model.addAttribute("textList", textList);
			// 前端页面 类型条件显示
			CommonUtil.enumsShow(model,Constants.Clear.SETTLE);
			return model;
		} catch (Exception e) {
			logger.error("SettleDicItemService goToSettleDicItemAddJsp has a error:{添加字典明细页面跳转出错  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：查询结算区间是否存在明细
	 * @时间：2016年11月16日 下午8:36:35
	 * @创建人：wangdong
	 */
	public String chechTypeSettleTle(SettleDicItem settleDicItem) throws Exception {
		StringBuffer flg = new StringBuffer("");	
		try {
			// 判断是不是结算区间类型
			if (null != settleDicItem && null != settleDicItem.getTypeId()) {
				SettleDicType dicType = settleDicTypeService.get(settleDicItem.getTypeId());
				// 判断是否是结算区间
				if (null != dicType && StringUtils.equals(dicType.getText(), SettleDicTypeEnum.SETTLEAREA.getContent())) {
					//根据条件查询类型明细（结算区间类型）
					List<SettleDicItem> settleDicItemList = findList(settleDicItem);
					if (null != settleDicItemList && settleDicItemList.size() > 0) {
						// 如果存在两个结算区间明细 说明已经添加过通道侧和商户侧，不允许添加
						if (settleDicItemList.size() > 1) {
							flg.append("false,all");
							// 存在一个结算区间明细
						} else if (settleDicItemList.size() > 0) {
							// 判断是否存在通道侧 结算区间类型明细
							if (StringUtils.equals(settleDicItemList.get(0).getText(), "channel")) {
								flg.append("false,channel");
							}
							// 判断是否存在商户侧 结算区间类型明细
							if (StringUtils.equals(settleDicItemList.get(0).getText(), "merchant")) {
								flg.append("false,merchant");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("SettleDicItemService chechTypeSettleTle has a error:{查询结算区间是否存在明细错误  FIND_ERROR}", e);
			throw new Exception(e);
		}
		return flg.toString();
	}

	/**
	 * 
	 * @param model 
	 * @throws Exception 
	 * @方法说明：存在相同的类型明细
	 * @时间：2016年11月16日 下午8:44:54
	 * @创建人：wangdong
	 */
	public Model settleDicItemExist(SettleDicItem settleDicItem, Model model) throws Exception {
		try {
			// 获取字典类型
			List<EnumBean> typeList = Lists.newArrayList();
			settleDicItem.setFlg("add");//用于区分是列表查询还是添加查询
			//用于页面查询条件
			List<SettleDicType> typeStatusList = settleDicTypeService.findDistinctTypeIdType(settleDicItem);
			for (SettleDicType type : typeStatusList) {
				EnumBean enums = new EnumBean();
				enums.setValue(type.getTypeId().toString());
				enums.setName(type.getText());
				typeList.add(enums);
			}
			model.addAttribute("typeList", typeList);

			// 状态
			List<EnumBean> statusList = Lists.newArrayList();
			for (BillingBecomeEffect status : BillingBecomeEffect.values()) {
				EnumBean ct = new EnumBean();
				ct.setValue(status.getValue());
				ct.setName(status.getContent());
				statusList.add(ct);
			}
			model.addAttribute("statusList", statusList);
			model.addAttribute("settleDicItem", settleDicItem);//用于显示重复的类型明细
			model.addAttribute("message",
					"保存的字典明细，明细名称为：" + settleDicItem.getText() + ",明细值为：" + settleDicItem.getValue() + "的重复！");
			return model;
		} catch (Exception e) {
			logger.error("SettleDicItemService settleDicItemExist has a error:{存在相同的类型明细错误  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
}
