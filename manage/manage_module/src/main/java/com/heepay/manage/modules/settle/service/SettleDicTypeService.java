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
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.riskLogs.service.RiskLogsService;
import com.heepay.manage.modules.settle.dao.SettleDicTypeDao;
import com.heepay.manage.modules.settle.entity.SettleDicItem;
import com.heepay.manage.modules.settle.entity.SettleDicType;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
*
* 描    述：清结算字典类型Service
*
* 创 建 者： @author wangdong
* 创建时间：2016年10月12日19:28:02
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
public class SettleDicTypeService extends CrudService<SettleDicTypeDao, SettleDicType>{
	
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private SettleDicTypeDao settleDicTypeDao;
	
	@Autowired
	private RiskLogsService riskLogsService;//记录日志
	
	/**
	 * @方法说明：根据id获取清结算字典类型
	 * @author wangdong
	 * @param id
	 * @时间：2016年10月12日19:33:40
	 */
	public SettleDicType get(Integer id) {
		return settleDicTypeDao.get(id);
	}
	
	/**
	 * @方法说明：获取清结算字典类型List
	 * @author wangdong
	 * @param settleDicType
	 * @时间：2016年10月12日19:34:02
	 */
	public List<SettleDicType> findList(SettleDicType settleDicType) {
		return super.findList(settleDicType);
	}

	/**
	 * @param request 
	 * @throws Exception 
	 * @方法说明：保存/修改数据字典类型
	 * @时间：2016年10月12日
	 * @创建人：wangdong
	 */
	@Transactional(readOnly = false)
	public void saveSettleDicType(SettleDicType settleDicType, HttpServletRequest request) throws Exception {
		String message = "";//提示信息
		try {
			if (null != settleDicType.getTypeId()){
				settleDicType.setUpdateOperator(UserUtils.getUser().getName());
				settleDicTypeDao.update(settleDicType);
				message = "更新";
				//记录日志
				riskLogsService.savaEntity("更新", "字典类型更新:名称:"+settleDicType.getText()+",值:"+settleDicType.getCode(), request);
			}else{
				message = "插入";
				settleDicType.setCode(settleDicType.getCode().toUpperCase());
				settleDicType.setCreateOperator(UserUtils.getUser().getName());
				settleDicTypeDao.insert(settleDicType);
				//记录日志
				riskLogsService.savaEntity("插入", "字典类型插入:名称:"+settleDicType.getText()+",值:"+settleDicType.getCode(), request);
			}
		} catch (Exception e) {
			logger.error("SettleDicTypeService saveSettleDicType has a error:{"+message+"数据字典类型错误 FIND_ERROR}",e);
			throw new Exception(e);
		}
	}

	/**
	 * @throws Exception 
	 * @方法说明：查询是否存在相同编码
	 * @时间：2016年10月13日
	 * @创建人：wangdong
	 */
	public Integer findCodeTextBySettleDicTypeExist(SettleDicType settleDicType) throws Exception {
		try {
			return settleDicTypeDao.findCodeTextBySettleDicTypeExist(settleDicType);
		} catch (Exception e) {
			logger.error("SettleDicTypeService findCodeBySettleDicTypeExist has a error:{查询是否存在相同编码错误 FIND_ERROR}",e);
			throw new Exception(e);
		}
	}

	/**
	 * @throws Exception 
	 * @方法说明：查询去重的类型List
	 * @时间：2016年10月13日
	 * @创建人：wangdong
	 */
	public List<SettleDicType> findDistinctType() throws Exception {
		try {
			return settleDicTypeDao.findDistinctType();
		} catch (Exception e) {
			logger.error("SettleDicTypeService findDistinctType has a error:",e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @param settleDicItem 
	 * @throws Exception 
	 * @方法说明：查询类型根据typeId和text去重
	 * @时间：2016年10月25日 下午7:54:56
	 * @创建人：wangdong
	 */
	public List<SettleDicType> findDistinctTypeIdType(SettleDicItem settleDicItem) throws Exception {
		try {
			return settleDicTypeDao.findDistinctTypeIdType(settleDicItem);
		} catch (Exception e) {
			logger.error("SettleDicTypeService findDistinctTypeIdType has a error:{查询类型根据typeId和text去重错误  FIND_ERROR}",e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：查询类型List
	 * @时间：2016年11月16日 下午8:58:55
	 * @创建人：wangdong
	 */
	public Model findSettleDicTypePage(Page<SettleDicType> page, SettleDicType settleDicType, Model model) throws Exception {
		try {
			Page<SettleDicType> pageSettleDicType = findPage(page, settleDicType);
			List<SettleDicType> typeInfoList = Lists.newArrayList();
			//循环翻译部分字段
			for (SettleDicType type : pageSettleDicType.getList()) {
				//状态
				if(StringUtils.isNotBlank(type.getStatus())){
					type.setStatus(BillingBecomeEffect.labelOf(type.getStatus()));
				}
				typeInfoList.add(type);
			}
			pageSettleDicType.setList(typeInfoList);
			
			//获取字典类型
			List<EnumBean> typeList = Lists.newArrayList();
			//用于页面查询条件
			List<SettleDicType> typeDISTINCTList = findDistinctType();
			for (SettleDicType type : typeDISTINCTList) {
				EnumBean enums = new EnumBean();
				enums.setValue(type.getCode());
				enums.setName(type.getText());
				typeList.add(enums);
			}
			model.addAttribute("typeList", typeList);
			//前端页面  类型条件显示
			CommonUtil.enumsShow(model,Constants.Clear.SETTLE);
			model.addAttribute("page", pageSettleDicType);
			return model;
		} catch (Exception e) {
			logger.error("SettleDicTypeService findSettleDicTypePage has a error:{查询类型List错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：添加数据类型页面跳转错误
	 * @时间：2016年11月16日 下午9:03:01
	 * @创建人：wangdong
	 */
	public Model goToSettleDicTypeAddJsp(SettleDicType settleDicType, Model model) throws Exception {
		try {
			//修改查询当前类型
			if(null != settleDicType && null != settleDicType.getTypeId()){
				//根据主键查询类型信息
				settleDicType = get(settleDicType.getTypeId().toString());
				model.addAttribute("settleDicType", settleDicType);
			}
			//前端页面  类型条件显示
			CommonUtil.enumsShow(model,Constants.Clear.SETTLE);
			return model;
		} catch (Exception e) {
			logger.error("SettleDicTypeService goToSettleDicTypeAddJsp has a error:{添加数据类型页面跳转错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
}
