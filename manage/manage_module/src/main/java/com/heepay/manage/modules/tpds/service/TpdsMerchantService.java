package com.heepay.manage.modules.tpds.service;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.heepay.common.util.FastDFSUtils;
import com.heepay.enums.AllowSystemType;
import com.heepay.enums.MerchantStatus;
import com.heepay.enums.RouteStatus;
import com.heepay.enums.UserType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.dao.MerchantBankCardDao;
import com.heepay.manage.modules.merchant.dao.MerchantDao;
import com.heepay.manage.modules.merchant.dao.MerchantUserDao;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantBankCard;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.manage.modules.tpds.dao.TpdsMerchantDao;
import com.heepay.manage.modules.tpds.entity.TpdsMerchant;


/***
 * 
* 
* 描    述：商户信息表
*
* 创 建 者： wangl
* 创建时间：  9 Feb 201710:18:20
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
public class TpdsMerchantService extends CrudService<TpdsMerchantDao, TpdsMerchant> {

	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private TpdsMerchantDao tpdsMerchantDao;

	@Autowired
	private MerchantBankCardDao merchantBankCardDao;
	
	@Autowired
    private MerchantDao merchantDao;
	
	@Autowired
    private MerchantUserDao merchantUserDao;
	
	/**
	 * @方法说明：根据id查询对象
	 * @时间：9 Feb 201712:57:46
	 * @创建人：wangl
	 */
	public TpdsMerchant getEntityById(Integer merchantId) {
		
		return tpdsMerchantDao.getEntityById(merchantId);
	}

	/**
	 * 
	 * @方法说明：上传图片
	 * @时间：9 Feb 201714:37:18
	 * @创建人：wangl
	 */
    @Transactional(readOnly = false)
    public String upLoadPic(MultipartFile file) throws Exception {
	    if(!file.isEmpty()) {
            return FastDFSUtils.uploadPic(file.getBytes(), file.getOriginalFilename(), file.getSize());
        }
        return "";
    }

    
	/**
	 * @方法说明：保存入库的方法
	 * @时间：9 Feb 201714:42:33
	 * @创建人：wangl
	 */
    @Transactional(readOnly = false)
	public int saveEntity(TpdsMerchant record) {
		
		int num=0;
		try {
			num = tpdsMerchantDao.saveEntity(record);
		} catch (Exception e) {
			logger.error("商户信息表保存入库的方法--->{异常}"+e.getMessage());
		}
		return num;
	}

	/**
	 * @方法说明：保存用户信息
	 * @时间：9 Feb 201714:59:23
	 * @创建人：wangl
	 */
    @Transactional(readOnly = false)
	public String saveUser(TpdsMerchant tpdsMerchant) {
		
		MerchantUser merchantUser = cmssInUser(tpdsMerchant);
        merchantUserDao.insert(merchantUser);
        return merchantUser.getId();
	}
	
   //修改User表
    @Transactional(readOnly = false)
	public String updateUser(TpdsMerchant tpdsMerchant) {
		
    	MerchantUser merchantUser = merchantUserDao.get(tpdsMerchant.getMerchantNo());
    	
    	merchantUser.setId(tpdsMerchant.getMerchantNo());
    	merchantUser.setLoginName(tpdsMerchant.getLoginName());
    	merchantUser.setUserType(UserType.USER.getValue());
    	merchantUser.setPhone(tpdsMerchant.getContactorPhone());
    	merchantUser.setLinkAddress(tpdsMerchant.getBusinessAddress());
         //merchantUser.setSource("tpds");
    	merchantUser.setLoginPassword("7c4a8d09ca3762af61e59520943dc26494f8941b");  //暂时写死
    	merchantUser.setPayPassword("7c4a8d09ca3762af61e59520943dc26494f8941b");  //暂时写死
         
         //资金存管
    	merchantUser.setStatus(MerchantStatus.NORMAL.getValue());
    	merchantUser.setUpdateDate(new Date());
    	merchantUser.setSource(AllowSystemType.TPDS.getValue());
    	merchantUser.setAllowSystem(AllowSystemType.NGP_WEB.getValue());
         
       // merchantUserDao.update(merchantUser);
        return merchantUser.getId();
	}
	
	/**
	 * 
	 * @方法说明：保存到商户列表中
	 * @时间：9 Feb 201715:04:18
	 * @创建人：wangl
	 */
    @Transactional(readOnly = false)
    public MerchantUser cmssInUser(TpdsMerchant tpdsMerchant){
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setLoginName(tpdsMerchant.getLoginName());
        merchantUser.setUserType(UserType.USER.getValue());
        merchantUser.setPhone(tpdsMerchant.getContactorPhone());
        merchantUser.setLinkAddress(tpdsMerchant.getBusinessAddress());
        //merchantUser.setSource("tpds");
        merchantUser.setLoginPassword("7c4a8d09ca3762af61e59520943dc26494f8941b");  //暂时写死
        merchantUser.setPayPassword("7c4a8d09ca3762af61e59520943dc26494f8941b");  //暂时写死
        
        //资金存管
        merchantUser.setStatus(MerchantStatus.NORMAL.getValue());
        merchantUser.setCreateTime(new Date());
        merchantUser.setSource(AllowSystemType.TPDS.getValue());
        merchantUser.setAllowSystem(AllowSystemType.NGP_WEB.getValue());
        return merchantUser;
    }

	/**
	 * @方法说明：保存到商户表的基础数据
	 * @时间：9 Feb 201715:05:42
	 * @创建人：wangl
	 */
    @Transactional(readOnly = false)
	public Boolean saveMerchant(TpdsMerchant tpdsMerchant) {
		
		Merchant merchant = cmssInmerchant(tpdsMerchant);
        int insert = merchantDao.insert(merchant);
        return insert == 1;
	}
    
    //@方法说明：修改到商户表的基础数据
	
    @Transactional(readOnly = false)
	public void updateMerchant(TpdsMerchant tpdsMerchant){
        Merchant merchant = merchantDao.get(tpdsMerchant.getMerchantNo());
        merchant.setUserId(Integer.parseInt(tpdsMerchant.getMerchantNo()));
        merchant.setEmail(tpdsMerchant.getLoginName());
        merchant.setContryName(tpdsMerchant.getContryName());
        merchant.setType(tpdsMerchant.getType());
        merchant.setCertificateType(tpdsMerchant.getCertificateType());
        merchant.setCompanyName(tpdsMerchant.getCompanyName());
        merchant.setProvinceCode(tpdsMerchant.getProvinceCode());
        merchant.setProvinceName(tpdsMerchant.getProvinceName());
        merchant.setCityCode(tpdsMerchant.getCityCode());
        merchant.setCityName(tpdsMerchant.getCityName());
        merchant.setCountyCode(tpdsMerchant.getCountyCode());
        merchant.setCountyName(tpdsMerchant.getCountyName());
        merchant.setBusinessAddress(tpdsMerchant.getBusinessAddress());
        merchant.setWebsite(tpdsMerchant.getWebsite());
        merchant.setBusinessLicenseNo(tpdsMerchant.getBusinessLicenseNo());
        merchant.setBusinessLicenseEndTime(tpdsMerchant.getBusinessLicenseEndTime());
        merchant.setOrganizationCode(tpdsMerchant.getOrganizationCode());
        merchant.setTaxRegistrationCertificateNo(tpdsMerchant.getTaxRegistrationCertificateNo());
        merchant.setBusinessScope(tpdsMerchant.getBusinessScope());
        merchant.setLegalRepresentative(tpdsMerchant.getLlegalperson());
        merchant.setLegalIdcard(tpdsMerchant.getLegalIdcard());
        merchant.setLegalCertificateEndTime(tpdsMerchant.getLegalCertificateEndTime());
        merchant.setContactor(tpdsMerchant.getContactor());
        merchant.setContactorIdcardNo(tpdsMerchant.getContactorIdcardNo());
        merchant.setContactorCertificateEndTime(tpdsMerchant.getContactorCertificateEndTime());
        merchant.setContactorPhone(tpdsMerchant.getContactorPhone());
        merchant.setContryCode(tpdsMerchant.getContryCode());
        merchant.setCompanyPhone(tpdsMerchant.getCompanyPhone());
        merchant.setLegalMobile(tpdsMerchant.getLegalMobile());
        merchant.setIpcNo(tpdsMerchant.getIpcNo());
        merchant.setPermitsAccounts(tpdsMerchant.getPermitsAccounts());
        merchant.setLegalCertificateFront(tpdsMerchant.getLegalCertificateFront());
        merchant.setLegalCertificateBack(tpdsMerchant.getLegalCertificateBack());
        merchant.setTaxRegistrationCertificate(tpdsMerchant.getTaxRegistrationCertificate());
        merchant.setOrganizationCodeCertificate(tpdsMerchant.getOrganizationCodeCertificate());
        merchant.setBusinessLicenseFile(tpdsMerchant.getBusinessLicenseFile());
        merchant.setSource(AllowSystemType.TPDS.getValue());
        merchant.setLegalAuditStatus(merchantDao.get(tpdsMerchant.getMerchantNo())==null?RouteStatus.AUDING.getValue():merchantDao.get(tpdsMerchant.getMerchantNo()).getLegalAuditStatus());
        merchant.setRcAuditStatus(merchantDao.get(tpdsMerchant.getMerchantNo())==null?"":merchantDao.get(tpdsMerchant.getMerchantNo()).getRcAuditStatus());
        merchant.setUpdateDate(new Date());
        merchant.setInchargerId(UserUtils.getUser().getId());
        
       merchantDao.update(merchant);
       
    }

	
	/**
	 * 
	 * @方法说明：保存到商户表的基础数据
	 * @时间：9 Feb 201715:07:41
	 * @创建人：wangl
	 */
    @Transactional(readOnly = false)
	public Merchant cmssInmerchant(TpdsMerchant tpdsMerchant){
        Merchant merchant = new Merchant();//获取userid=user表的id
        merchant.setUserId(Integer.parseInt(tpdsMerchant.getMerchantNo()));
        merchant.setEmail(tpdsMerchant.getLoginName());
        merchant.setContryName(tpdsMerchant.getContryName());
        merchant.setType(tpdsMerchant.getType());
        merchant.setCertificateType(tpdsMerchant.getCertificateType());
        merchant.setCompanyName(tpdsMerchant.getCompanyName());
        merchant.setProvinceCode(tpdsMerchant.getProvinceCode());
        merchant.setProvinceName(tpdsMerchant.getProvinceName());
        merchant.setCityCode(tpdsMerchant.getCityCode());
        merchant.setCityName(tpdsMerchant.getCityName());
        merchant.setCountyCode(tpdsMerchant.getCountyCode());
        merchant.setCountyName(tpdsMerchant.getCountyName());
        merchant.setBusinessAddress(tpdsMerchant.getBusinessAddress());
        merchant.setWebsite(tpdsMerchant.getWebsite());
        merchant.setBusinessLicenseNo(tpdsMerchant.getBusinessLicenseNo());
        merchant.setBusinessLicenseEndTime(tpdsMerchant.getBusinessLicenseEndTime());
        merchant.setOrganizationCode(tpdsMerchant.getOrganizationCode());
        merchant.setTaxRegistrationCertificateNo(tpdsMerchant.getTaxRegistrationCertificateNo());
        merchant.setBusinessScope(tpdsMerchant.getBusinessScope());
        merchant.setLegalRepresentative(tpdsMerchant.getLlegalperson());
        merchant.setLegalIdcard(tpdsMerchant.getLegalIdcard());
        merchant.setLegalCertificateEndTime(tpdsMerchant.getLegalCertificateEndTime());
        merchant.setContactor(tpdsMerchant.getContactor());
        merchant.setContactorIdcardNo(tpdsMerchant.getContactorIdcardNo());
        merchant.setContactorCertificateEndTime(tpdsMerchant.getContactorCertificateEndTime());
        merchant.setContactorPhone(tpdsMerchant.getContactorPhone());
        merchant.setContryCode(tpdsMerchant.getContryCode());
        merchant.setCompanyPhone(tpdsMerchant.getCompanyPhone());
        merchant.setLegalMobile(tpdsMerchant.getLegalMobile());
        merchant.setIpcNo(tpdsMerchant.getIpcNo());
        merchant.setPermitsAccounts(tpdsMerchant.getPermitsAccounts());
        merchant.setLegalCertificateFront(tpdsMerchant.getLegalCertificateFront());
        merchant.setLegalCertificateBack(tpdsMerchant.getLegalCertificateBack());
        merchant.setTaxRegistrationCertificate(tpdsMerchant.getTaxRegistrationCertificate());
        merchant.setOrganizationCodeCertificate(tpdsMerchant.getOrganizationCodeCertificate());
        merchant.setBusinessLicenseFile(tpdsMerchant.getBusinessLicenseFile());
        merchant.setSource(AllowSystemType.TPDS.getValue());
        merchant.setLegalAuditStatus(merchantDao.get(tpdsMerchant.getMerchantNo())==null?RouteStatus.AUDING.getValue():merchantDao.get(tpdsMerchant.getMerchantNo()).getLegalAuditStatus());
        merchant.setRcAuditStatus(merchantDao.get(tpdsMerchant.getMerchantNo())==null?"":merchantDao.get(tpdsMerchant.getMerchantNo()).getRcAuditStatus());
        merchant.setCreateTime(new Date());
        merchant.setInchargerId(UserUtils.getUser().getId());
        return merchant;
    }

	/**
	 * @方法说明：更新操作
	 * @时间：9 Feb 201716:19:07
	 * @创建人：wangl
	 */
    @Transactional(readOnly = false)
	public int updateEntity(TpdsMerchant record) {
		
		return tpdsMerchantDao.updateEntity(record);
	}

    
	/**
	 * @方法说明：查询银行卡
	 * @时间：14 Feb 201709:02:53
	 * @创建人：wangl
	 */
	public Page<MerchantBankCard> findPageList(Page<MerchantBankCard> page, MerchantBankCard merchantBankCard) {
		
		List<MerchantBankCard> findPageList=merchantBankCardDao.findPageList(merchantBankCard);
		
		page.setList(findPageList);
		page.setCount(findPageList.size());
		return page;
	}

	  /**
     * 查询该邮箱是否存在
     * @param email    邮箱
     * @return         对象或者null
     */
    @Transactional(readOnly = false)
    public MerchantUser queryEmailExist(String email){
        return merchantUserDao.queryEmailExist(email);
    };

}
