package com.heepay.manage.modules.pbc.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.pbc.PbcBankStatus;
import com.heepay.enums.pbc.PbcCardType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pbc.dao.PbcBankInfoDao;
import com.heepay.manage.modules.pbc.entity.PbcBankInfo;

/**
 * 
 *
 * 描    述：银行信息
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
public class PbcBankInfoService  extends CrudService<PbcBankInfoDao, PbcBankInfo>{

	@Autowired
	private PbcBankInfoDao pbcBankInfoDao;
	
	/**
	 * 
	 * @方法说明：查询账户信息list
	 * @时间：2016年10月20日
	 * @创建人：wangdong
	 */
	public List<PbcBankInfo> findList(PbcBankInfo pbcBankInfo) {
		return super.findList(pbcBankInfo);
	}
	
	/**
	 * 
	 * @方法说明：根据id获取对象
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public PbcBankInfo getEntityById(Long pbcId) {
		return pbcBankInfoDao.getEntityById(pbcId);
	}
	
	/**
	 * 
	 * @方法说明：根据业务编码查询账户信息
	 * @时间：2016年12月17日 下午1:23:33
	 * @创建人：wangdong
	 */
	public PbcBankInfo getFeeBackId(String applicationId){
		return pbcBankInfoDao.getEntityByApplicationId(applicationId);
	}
	
	/**
	 * 
	 * @方法说明：获取银行卡信息
	 * @时间：2016年12月24日 上午9:26:31
	 * @创建人：wangdong
	 */
	public Model findPbcBankInfoPage(Page<PbcBankInfo> page, PbcBankInfo pbcBankInfo,
			Model model) throws Exception {
		try {
			Page<PbcBankInfo> pagePbcBankInfo = findPage(page, pbcBankInfo);
			List<PbcBankInfo> pbcBankInfoList = Lists.newArrayList();
			for(PbcBankInfo pbc : pagePbcBankInfo.getList()){
				//银行卡类型
				if(StringUtils.isNotBlank(pbc.getCardType())){
					pbc.setCardType(PbcCardType.labelOf(pbc.getCardType()));
				}
				//银行卡状态
				if(StringUtils.isNotBlank(pbc.getCardValidation())){
					pbc.setCardValidation(PbcBankStatus.labelOf(pbc.getCardValidation()));
				}
				pbcBankInfoList.add(pbc);
			}
			pagePbcBankInfo.setList(pbcBankInfoList);
			
			model.addAttribute("page", pagePbcBankInfo);
			model.addAttribute("pbcBankInfo",pbcBankInfo);
			return model;
		} catch (Exception e) {
			logger.error("PbcBankInfoService findPbcBankInfoPage has a error:{获取银行卡信息List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @方法说明：保存银行卡信息
	 * @时间：2016年12月26日 下午3:06:42
	 * @创建人：wangdong
	 */
	public int saveMap(Map<String, Object> map) throws Exception {
		try {
			return pbcBankInfoDao.saveMap(map);
		} catch (Exception e) {
			logger.error("PbcBankInfoService saveMap has a error:{保存银行卡信息出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}
	
}
