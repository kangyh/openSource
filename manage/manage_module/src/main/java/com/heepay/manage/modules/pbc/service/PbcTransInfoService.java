package com.heepay.manage.modules.pbc.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.pbc.PbcBorrowingSigns;
import com.heepay.enums.pbc.PbcCurrencyType;
import com.heepay.enums.pbc.PbcDeviceType;
import com.heepay.enums.pbc.PbcPaymentType;
import com.heepay.enums.pbc.PbcTransType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pbc.dao.PbcTransInfoDao;
import com.heepay.manage.modules.pbc.entity.PbcTransInfo;

/**
 * 
 *
 * 描    述：交易信息
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年12月24日 上午9:02:26
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
public class PbcTransInfoService  extends CrudService<PbcTransInfoDao, PbcTransInfo>{
	
	@Autowired
	private PbcTransInfoDao pbcTransInfoDao;
	
	/**
	 * 
	 * @方法说明：查询账户信息list
	 * @时间：2016年10月20日
	 * @创建人：wangdong
	 */
	public List<PbcTransInfo> findList(PbcTransInfo pbcTransInfo) {
		return super.findList(pbcTransInfo);
	}
	
	/**
	 * 
	 * @方法说明：根据id获取对象
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public PbcTransInfo getEntityById(Long pbcId) {
		return pbcTransInfoDao.getEntityById(pbcId);
	}
	
	/**
	 * 
	 * @方法说明：根据反馈id查询账户信息
	 * @时间：2016年12月17日 下午1:23:33
	 * @创建人：wangdong
	 */
	public PbcTransInfo getFeeBackId(String applicationId){
		return pbcTransInfoDao.getEntityByApplicationId(applicationId);
	}
	
	/**
	 * 
	 * @方法说明：获取交易信息
	 * @时间：2016年12月24日 上午9:26:31
	 * @创建人：wangdong
	 */
	public Model findPbcTransInfoPage(Page<PbcTransInfo> page, PbcTransInfo pbcTransInfo,
			Model model) throws Exception {
		try {
			Page<PbcTransInfo> pagePbcTransInfo = findPage(page, pbcTransInfo);
			List<PbcTransInfo> pbcTransInfoList = Lists.newArrayList();
			for(PbcTransInfo pbc : pagePbcTransInfo.getList()){
				//交易类型
				if(StringUtils.isNotBlank(pbc.getTransactionType())){
					pbc.setTransactionType(PbcTransType.labelOf(pbc.getTransactionType()));
				}
				//支付类型
				if(StringUtils.isNotBlank(pbc.getPaymentType())){
					pbc.setPaymentType(PbcPaymentType.labelOf(pbc.getPaymentType()));
				}
				//交易主体的出入账标识
				if(StringUtils.isNotBlank(pbc.getBorrowingSigns())){
					pbc.setBorrowingSigns(PbcBorrowingSigns.labelOf(pbc.getBorrowingSigns()));
				}
				//币种
				if(StringUtils.isNotBlank(pbc.getCurrency())){
					pbc.setCurrency(PbcCurrencyType.labelOf(pbc.getCurrency()));
				}
				//交易设备类型
				if(StringUtils.isNotBlank(pbc.getTransactionDeviceType())){
					pbc.setTransactionDeviceType(PbcDeviceType.labelOf(pbc.getTransactionDeviceType()));
				}
				pbcTransInfoList.add(pbc);
			}
			pagePbcTransInfo.setList(pbcTransInfoList);
			
			model.addAttribute("page", pagePbcTransInfo);
			model.addAttribute("pbcTransInfo",pbcTransInfo);
			return model;
		} catch (Exception e) {
			logger.error("PbcTransInfoService findPbcTransInfoPage has a error:{获取交易信息List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @方法说明：插入交易明细信息
	 * @时间：2016年12月24日 下午6:13:16
	 * @创建人：wangdong
	 */
	@Transactional(readOnly = false)
	public int saveMap(Map<String,Object> map) throws Exception{
		try {
			return pbcTransInfoDao.saveMap(map);
		} catch (Exception e) {
			logger.error("PbcTransInfoService saveMap has a error:{插入交易明细信息出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}
	
}
