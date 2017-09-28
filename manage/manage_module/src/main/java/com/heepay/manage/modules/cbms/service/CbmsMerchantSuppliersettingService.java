/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.heepay.common.util.FastDFSUtils;
import com.heepay.enums.AllowSystemType;
import com.heepay.enums.MerchantStatus;
import com.heepay.enums.RouteStatus;
import com.heepay.enums.UserType;
import com.heepay.manage.modules.cbms.dao.CbmsCountrysettingDao;
import com.heepay.manage.modules.cbms.dao.CbmsSuppliersettingDao;
import com.heepay.manage.modules.cbms.entity.CbmsSuppliersetting;
import com.heepay.manage.modules.cbms.utils.DataprocessUtils;
import com.heepay.manage.modules.merchant.dao.MerchantDao;
import com.heepay.manage.modules.merchant.dao.MerchantUserDao;
import com.heepay.manage.modules.merchant.service.MerchantUserCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.cbms.entity.CbmsMerchantSuppliersetting;
import com.heepay.manage.modules.cbms.dao.CbmsMerchantSuppliersettingDao;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * 描    述：总的商户增删改Service
 *
 * 创 建 者： @author guozx
 * 创建时间： 2017/1/6 10:38 2017年1月6日 10:38:25
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
public class CbmsMerchantSuppliersettingService extends CrudService<CbmsMerchantSuppliersettingDao, CbmsMerchantSuppliersetting> {

    @Autowired
    private MerchantUserDao merchantUserDao;

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private CbmsSuppliersettingDao cbmsSuppliersettingDao;

    @Autowired
    private CbmsCountrysettingDao cbmsCountrysettingDao;

	public CbmsMerchantSuppliersetting get(String id) {
		return super.get(id);
	}
	
	public List<CbmsMerchantSuppliersetting> findList(CbmsMerchantSuppliersetting cbmsMerchantSuppliersetting) {
		return super.findList(cbmsMerchantSuppliersetting);
	}
	
	public Page<CbmsMerchantSuppliersetting> findPage(Page<CbmsMerchantSuppliersetting> page, CbmsMerchantSuppliersetting cbmsMerchantSuppliersetting) {
		return super.findPage(page, cbmsMerchantSuppliersetting);
	}
	
    /**
     *添加保存跨境商户表的商户数据
     * @param cbmsMerchantSuppliersetting
     * @return
     */
    @Transactional(readOnly = false, rollbackFor=Exception.class)
	public boolean saveCbmsSuppliersetting(CbmsMerchantSuppliersetting cbmsMerchantSuppliersetting) {
        //将集团商户信息中筛选出merchantUser的数据， 存储到ngp_base数据库中的user表
        MerchantUser merchantUser = cmssInUser(cbmsMerchantSuppliersetting);
        int insertUser = merchantUserDao.insert(merchantUser);
        cbmsMerchantSuppliersetting.setMerchantNo(merchantUser.getId());
        //将集团商户信息中筛选出merchant的数据， 存储到ngp_base数据库中的merchant表
        Merchant merchant = cmssInmerchant(cbmsMerchantSuppliersetting);
        int insertMerchantStatus = merchantDao.insert(merchant);
        //添加保存跨境商户表的商户数据cbmsSuppliersetting
        CbmsSuppliersetting cbmsSuppliersetting = cmssInCss(cbmsMerchantSuppliersetting);
        int insertCbmsStatus = cbmsSuppliersettingDao.insert(cbmsSuppliersetting);
        return insertUser ==1 && insertMerchantStatus == 1 && insertCbmsStatus ==1;
	}

    /**
     *修改跨境商户表的商户数据
     * @param cbmsMerchantSuppliersetting
     * @return
     */
    @Transactional(readOnly = false, rollbackFor=Exception.class)
    public boolean updateCbmsSuppliersetting(CbmsMerchantSuppliersetting cbmsMerchantSuppliersetting) {
        String merchantNo = cbmsMerchantSuppliersetting.getMerchantNo();
        //将CbmsMerchantSuppliersetting类中的元素提取到MerchantUser
        MerchantUser merchantUser = cmssInUser(cbmsMerchantSuppliersetting);
        merchantUser.setId(merchantNo);
        Merchant merchant = cmssInmerchant(cbmsMerchantSuppliersetting);
        merchant.setUserId(Integer.parseInt(merchantNo));
        //如果商户的法务审核为审核不通过的时候，修改法务审核状态为待审核
        //如果商户的法务审核为审核通过的时候，而风控审核未通过，修改风控审核状态为待审核
        if(RouteStatus.AUDREJ.getValue().equals(merchant.getLegalAuditStatus())){
            merchant.setLegalAuditStatus(RouteStatus.AUDING.getValue());
        } else if (RouteStatus.AUDIT_SUCCESS.getValue().equals(merchant.getLegalAuditStatus()) && RouteStatus.AUDREJ.getValue().equals(merchant.getRcAuditStatus())){
            merchant.setRcAuditStatus(RouteStatus.AUDING.getValue());
        }
        //将CbmsMerchantSuppliersetting类中的元素提取到CbmsSuppliersetting
        CbmsSuppliersetting cbmsSuppliersetting = cmssInCss(cbmsMerchantSuppliersetting);
        //商户贸易类型名称--list转换为String保存到数据库（[1,2,3] to "1,2,3"）
        ArrayList<String> a =(ArrayList) cbmsMerchantSuppliersetting.getCbmsTradeTypeNameList();
        final int size =  a.size();
        String[] strings = a.toArray(new String[size]);
        cbmsSuppliersetting.setCbmsTradeTypeName(DataprocessUtils.convertArrToStr(strings));
        //保存商户的商户编码
        cbmsSuppliersetting.setMerchantNo(merchantNo);
        int updateUer = merchantUserDao.update(merchantUser);
        int updateMerchant = merchantDao.update(merchant);
        int updateCSS = cbmsSuppliersettingDao.update(cbmsSuppliersetting);
        return updateUer==1 && updateMerchant==1 && updateCSS==1;

    }
    /**
     *上传图片
     * @param file
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false, rollbackFor=Exception.class)
    public String upLoadPic(MultipartFile file) throws Exception {
	    if(!file.isEmpty()) {
	        //图片上传返回图片地址（不包含域名）
            return FastDFSUtils.uploadPic(file.getBytes(), file.getOriginalFilename(), file.getSize());
        }
        return "";
    }

    /**
     * 查询该邮箱是否存在
     * @param email
     * @return true         merchant表和cbmsSuppliersetting表不存在对应商户，可以添加
     *         false        merchant表和cbmsSuppliersetting表存在对应商户，不能可以添加
     */
    @Transactional(readOnly = false)
    public boolean queryEmailExist(String email){
        MerchantUser merchantUser = merchantUserDao.queryEmailExist(email);
        CbmsSuppliersetting cbmsSuppliersetting = cbmsSuppliersettingDao.queryEmailExist(email);
        return merchantUser == null && cbmsSuppliersetting ==null;
    };

    /**
     *将CbmsMerchantSuppliersetting类中的元素提取到MerchantUser
     * @param cbmsMerchantSuppliersetting
     * @return
     */
    public MerchantUser cmssInUser(CbmsMerchantSuppliersetting cbmsMerchantSuppliersetting){
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setLoginName(cbmsMerchantSuppliersetting.getLoginName());
        merchantUser.setUserType(UserType.MERCHANT.getValue());
        merchantUser.setPhone(cbmsMerchantSuppliersetting.getContactorPhone());
        merchantUser.setMobile(cbmsMerchantSuppliersetting.getContactorPhone());
        merchantUser.setLinkAddress(cbmsMerchantSuppliersetting.getBusinessAddress());
        merchantUser.setStatus(MerchantStatus.NORMAL.getValue());
        merchantUser.setCreateTime(new Date());
        merchantUser.setSource(AllowSystemType.CROSS_BORDER.getValue());
        merchantUser.setAllowSystem(AllowSystemType.CROSS_BORDER.getValue() + "," + AllowSystemType.NGP_WEB.getValue());
        return merchantUser;
    }

    /**
     * 将CbmsMerchantSuppliersetting类中的元素提取到Merchant
     * @param cbmsMerchantSuppliersetting
     * @return
     */
    public Merchant cmssInmerchant(CbmsMerchantSuppliersetting cbmsMerchantSuppliersetting){
        Merchant merchant = new Merchant();//获取userid=user表的id
        merchant.setUserId(Integer.parseInt(cbmsMerchantSuppliersetting.getMerchantNo()));
        merchant.setEmail(cbmsMerchantSuppliersetting.getLoginName());
        merchant.setContryName(cbmsMerchantSuppliersetting.getContryName());
        merchant.setType(cbmsMerchantSuppliersetting.getType());
        merchant.setCertificateType(cbmsMerchantSuppliersetting.getCertificateType());
        merchant.setCompanyName(cbmsMerchantSuppliersetting.getCompanyName());
        merchant.setProvinceCode(cbmsMerchantSuppliersetting.getProvinceCode());
        merchant.setProvinceName(cbmsMerchantSuppliersetting.getProvinceName());
        merchant.setCityCode(cbmsMerchantSuppliersetting.getCityCode());
        merchant.setCityName(cbmsMerchantSuppliersetting.getCityName());
        merchant.setCountyCode(cbmsMerchantSuppliersetting.getCountyCode());
        merchant.setCountyName(cbmsMerchantSuppliersetting.getCountyName());
        merchant.setBusinessAddress(cbmsMerchantSuppliersetting.getBusinessAddress());
        merchant.setWebsite(cbmsMerchantSuppliersetting.getWebsite());
        merchant.setBusinessLicenseNo(cbmsMerchantSuppliersetting.getBusinessLicenseNo());
        merchant.setBusinessLicenseEndTime(cbmsMerchantSuppliersetting.getBusinessLicenseEndTime());
        merchant.setOrganizationCode(cbmsMerchantSuppliersetting.getOrganizationCode());
        merchant.setTaxRegistrationCertificateNo(cbmsMerchantSuppliersetting.getTaxRegistrationCertificateNo());
        merchant.setBusinessScope(cbmsMerchantSuppliersetting.getBusinessScope());
        merchant.setLegalRepresentative(cbmsMerchantSuppliersetting.getLlegalperson());
        merchant.setLegalIdcard(cbmsMerchantSuppliersetting.getLegalIdcard());
        merchant.setLegalCertificateEndTime(cbmsMerchantSuppliersetting.getLegalCertificateEndTime());
        merchant.setContactor(cbmsMerchantSuppliersetting.getContactor());
        merchant.setContactorIdcardNo(cbmsMerchantSuppliersetting.getContactorIdcardNo());
        merchant.setContactorCertificateEndTime(cbmsMerchantSuppliersetting.getContactorCertificateEndTime());
        merchant.setContactorPhone(cbmsMerchantSuppliersetting.getContactorPhone());
        merchant.setContryCode(cbmsMerchantSuppliersetting.getContryCode());
        merchant.setCompanyPhone(cbmsMerchantSuppliersetting.getCompanyPhone());
        merchant.setLegalMobile(cbmsMerchantSuppliersetting.getLegalMobile());
        merchant.setIpcNo(cbmsMerchantSuppliersetting.getIpcNo());
        merchant.setPermitsAccounts(cbmsMerchantSuppliersetting.getPermitsAccounts());
        merchant.setLegalCertificateFront(cbmsMerchantSuppliersetting.getLegalCertificateFront());
        merchant.setLegalCertificateBack(cbmsMerchantSuppliersetting.getLegalCertificateBack());
        merchant.setTaxRegistrationCertificate(cbmsMerchantSuppliersetting.getTaxRegistrationCertificate());
        merchant.setOrganizationCodeCertificate(cbmsMerchantSuppliersetting.getOrganizationCodeCertificate());
        merchant.setBusinessLicenseFile(cbmsMerchantSuppliersetting.getBusinessLicenseFile());
        merchant.setSource(AllowSystemType.CROSS_BORDER.getValue());
        merchant.setLegalAuditStatus(merchantDao.get(cbmsMerchantSuppliersetting.getMerchantNo())==null?RouteStatus.AUDING.getValue():merchantDao.get(cbmsMerchantSuppliersetting.getMerchantNo()).getLegalAuditStatus());
        merchant.setRcAuditStatus(merchantDao.get(cbmsMerchantSuppliersetting.getMerchantNo())==null?"":merchantDao.get(cbmsMerchantSuppliersetting.getMerchantNo()).getRcAuditStatus());
        merchant.setCreateTime(new Date());
        merchant.setInchargerId(UserUtils.getUser().getId());
        return merchant;
    }

    /**
     * 将CbmsMerchantSuppliersetting类中的元素提取到CbmsSuppliersetting
     * @param cbmsMerchantSuppliersetting
     * @return
     */
    public CbmsSuppliersetting cmssInCss(CbmsMerchantSuppliersetting cbmsMerchantSuppliersetting){
        CbmsSuppliersetting cbmsSuppliersetting = new CbmsSuppliersetting();
        cbmsSuppliersetting.setMerchantNo(cbmsMerchantSuppliersetting.getMerchantNo());
        cbmsSuppliersetting.setEmail(cbmsMerchantSuppliersetting.getLoginName());
        cbmsSuppliersetting.setSupplierName(cbmsMerchantSuppliersetting.getSupplierName());
        cbmsSuppliersetting.setProvinceNo(cbmsMerchantSuppliersetting.getProvinceCode());
        cbmsSuppliersetting.setCityCode(cbmsMerchantSuppliersetting.getCityCode());
        cbmsSuppliersetting.setBusiaddr(cbmsMerchantSuppliersetting.getBusinessAddress());
        cbmsSuppliersetting.setBusinessScope(cbmsMerchantSuppliersetting.getBusinessScope());
        cbmsSuppliersetting.setLlegalperson(cbmsMerchantSuppliersetting.getLlegalperson());
        cbmsSuppliersetting.setContactName(cbmsMerchantSuppliersetting.getContactor());
        cbmsSuppliersetting.setPhone(cbmsMerchantSuppliersetting.getContactorPhone());
        cbmsSuppliersetting.setOrgCustomsCode(cbmsMerchantSuppliersetting.getOrgCustomsCode());
        cbmsSuppliersetting.setEnterpriseName(cbmsMerchantSuppliersetting.getEnterpriseName());
        cbmsSuppliersetting.setElectricityName(cbmsMerchantSuppliersetting.getElectricityName());
        cbmsSuppliersetting.setElectricityUrl(cbmsMerchantSuppliersetting.getElectricityUrl());
        cbmsSuppliersetting.setOrgCustomsName(cbmsMerchantSuppliersetting.getOrgCustomsName());
        cbmsSuppliersetting.setCentralOfficeNumber(cbmsMerchantSuppliersetting.getCentralOfficeNumber());
        cbmsSuppliersetting.setCbmsEnterpriseTypeName(cbmsMerchantSuppliersetting.getCbmsEnterpriseTypeName());
        cbmsSuppliersetting.setCbmsSupplierCategoryName(cbmsMerchantSuppliersetting.getCbmsSupplierCategoryName());
        cbmsSuppliersetting.setCbmsTradeTypeName(cbmsMerchantSuppliersetting.getCbmsTradeTypeName());
        cbmsSuppliersetting.setLlegalPersonFrontPhotoAddress(cbmsMerchantSuppliersetting.getLegalCertificateFront());
        cbmsSuppliersetting.setLlegalPersonBankPhotoAddress(cbmsMerchantSuppliersetting.getLegalCertificateBack());
        cbmsSuppliersetting.setTaxRegisCertPhotoAddress(cbmsMerchantSuppliersetting.getTaxRegistrationCertificate());
        cbmsSuppliersetting.setOrgCodePhotoAddress(cbmsMerchantSuppliersetting.getOrganizationCodeCertificate());
        cbmsSuppliersetting.setBusLicensePhotoAddress(cbmsMerchantSuppliersetting.getBusinessLicenseFile());
        cbmsSuppliersetting.setTrademarkRegisPhotoAddress(cbmsMerchantSuppliersetting.getTrademarkRegisPhotoAddress());
        cbmsSuppliersetting.setTrademarkRegisPhotoAddress(cbmsMerchantSuppliersetting.getTrademarkRegisPhotoAddress());
        cbmsSuppliersetting.setCommodityQualityPhotoAddress(cbmsMerchantSuppliersetting.getCommodityQualityPhotoAddress());
        cbmsSuppliersetting.setStatus("0");
        cbmsSuppliersetting.setCountryId(cbmsMerchantSuppliersetting.getContryCode());
        cbmsSuppliersetting.setTimeInterval("60");
        cbmsSuppliersetting.setAutomaticAudit("2");
        return cbmsSuppliersetting;
    }


}