package com.heepay.manage.modules.pbc.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.pbc.PbcCurrencyType;
import com.heepay.enums.pbc.PbcMeasureType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pbc.dao.PbcMeasureInfoDao;
import com.heepay.manage.modules.pbc.entity.PbcMeasureInfo;

/**
 * 
 *
 * 描    述：措施信息
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年12月24日 下午12:06:27
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
public class PbcMeasureInfoService  extends CrudService<PbcMeasureInfoDao, PbcMeasureInfo>{

	@Autowired
	private PbcMeasureInfoDao pbcMeasureInfoDao;
	
	/**
	 * 
	 * @方法说明：查询账户信息list
	 * @时间：2016年10月20日
	 * @创建人：wangdong
	 */
	public List<PbcMeasureInfo> findList(PbcMeasureInfo pbcMeasureInfo) {
		return super.findList(pbcMeasureInfo);
	}
	
	/**
	 * 
	 * @方法说明：根据id获取对象
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public PbcMeasureInfo getEntityById(Long pbcId) {
		return pbcMeasureInfoDao.getEntityById(pbcId);
	}
	
	/**
	 * 
	 * @方法说明：根据业务编码查询账户信息
	 * @时间：2016年12月17日 下午1:23:33
	 * @创建人：wangdong
	 */
	public PbcMeasureInfo getFeeBackId(String applicationId){
		return pbcMeasureInfoDao.getEntityByApplicationId(applicationId);
	}
	
	/**
	 * 
	 * @方法说明：获取措施信息
	 * @时间：2016年12月24日 上午9:26:31
	 * @创建人：wangdong
	 */
	public Model findPbcMeasureInfoPage(Page<PbcMeasureInfo> page, PbcMeasureInfo pbcMeasureInfo,
			Model model) throws Exception {
		try {
			Page<PbcMeasureInfo> pagePbcMeasureInfo = findPage(page, pbcMeasureInfo);
			List<PbcMeasureInfo> pbcMeasureInfoList = Lists.newArrayList();
			for(PbcMeasureInfo pbc : pagePbcMeasureInfo.getList()){
				//措施类型
				if(StringUtils.isNotBlank(pbc.getMeasureType())){
					pbc.setMeasureType(PbcMeasureType.labelOf(pbc.getMeasureType()));
				}
				//币种
				if(StringUtils.isNotBlank(pbc.getCurrency())){
					pbc.setCurrency(PbcCurrencyType.labelOf(pbc.getCurrency()));
				}
				pbcMeasureInfoList.add(pbc);
			}
			pagePbcMeasureInfo.setList(pbcMeasureInfoList);
			
			model.addAttribute("page", pagePbcMeasureInfo);
			model.addAttribute("pbcMeasureInfo",pbcMeasureInfo);
			return model;
		} catch (Exception e) {
			logger.error("PbcBankInfoService findPbcMeasureInfoPage has a error:{获取措施信息List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @方法说明：存储措施信息
	 * @时间：2016年12月26日 上午9:57:07
	 * @创建人：wangdong
	 */
	@Transactional(readOnly = false)
	public int saveMap(Map<String, Object> map) throws Exception {
		try {
			return pbcMeasureInfoDao.saveMap(map);
		} catch (Exception e) {
			logger.error("PbcBankInfoService saveMap has a error:{存储措施信息出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}
	
}
