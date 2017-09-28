package com.heepay.manage.rpc.service.impl;

import com.heepay.common.util.DateUtil;
import com.heepay.enums.CertificateType;
import com.heepay.enums.RouteStatus;
import com.heepay.manage.modules.merchant.dao.MerchantDao;
import com.heepay.manage.modules.merchant.dao.MerchantEmployeeDao;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.rpc.service.MerchantService;
import com.heepay.manage.rpc.service.MerchantThrift;
import com.heepay.rpc.service.RpcService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 名称：操作商户表的service
 * <p/>
 * 创建者  B.HJ
 * 创建时间 2016-08-31-18:29
 * 创建描述：操作商户表的service
 */
@Component
@RpcService(name = "merchantServiceImpl", processor = MerchantService.Processor.class)
public class MerchantServiceImpl implements MerchantService.Iface{

    @Autowired
    private MerchantDao merchantDao;
	@Autowired
	private MerchantEmployeeDao merchantEmployeeDao;
    @Override
    public MerchantThrift get(String merchantId) throws TException {
        Merchant merchant = merchantDao.get(merchantId);
		MerchantThrift thrift = new MerchantThrift();
		if(null!=merchant){
			thrift.setUserId(String.valueOf(merchant.getUserId()));								    //用户ID
            thrift.setEmail(merchant.getEmail());													//登录email
            thrift.setPermitsAccounts(merchant.getPermitsAccounts());								//开户许可证
			thrift.setLegalCertificateFront(merchant.getLegalCertificateFront());					//法人代表证件照(正)
			thrift.setLegalCertificateBack(merchant.getLegalCertificateBack());						//法人代表证件照(反)
			thrift.setBusinessLicenseFile(merchant.getBusinessLicenseFile());						//营业执照文件本地存储路径及文件名
			thrift.setTaxRegistrationCertificate(merchant.getTaxRegistrationCertificate());			//税务登记证号码
			thrift.setOrganizationCodeCertificate(merchant.getOrganizationCodeCertificate());		//组织机构代码证
			thrift.setContactorCertificateFront(merchant.getContactorCertificateFront());			//联系人身份证正面
			thrift.setContactorCertificateBack(merchant.getContactorCertificateBack());				//联系人身份证背面
			thrift.setLegalIdcard(merchant.getLegalIdcard());										//法人代表身份证号
			thrift.setCertificateType(merchant.getCertificateType());								//证件类型(枚举类CertificateType (("ORDINA","普通证件"),("MULTIP","多证合一"),("INDIVI","个体商户")))
			/**-----------------------------------------------------------*/
			thrift.setStatus(merchant.getStatus());													//商户状态
			thrift.setLegalAuditStatus(merchant.getLegalAuditStatus());								//法务审核状态
			thrift.setRcAuditStatus(merchant.getRcAuditStatus());									//风控审核状态
			thrift.setObjection(merchant.getObjection());											//拒绝理由
			thrift.setCompanyName(merchant.getCompanyName());										//公司名称
			thrift.setType(merchant.getType());														//商户类型
			thrift.setOrganizationCode(merchant.getOrganizationCode());								//组织机构代码
			thrift.setBusinessAddress(merchant.getBusinessAddress());								//公司注册地址
			thrift.setLegalRepresentative(merchant.getLegalRepresentative());						//法人代表
			thrift.setBusinessLicenseNo(merchant.getBusinessLicenseNo());							//营业执照号码
			thrift.setContactorPhone(merchant.getContactorPhone());									//联系人电话
			thrift.setContactor(merchant.getContactor());											//联系人姓名
			thrift.setWebsite(merchant.getWebsite());												// 公司网址
			thrift.setAuthorizationLetter(merchant.getAuthorizationLetter());						//委托授权书
			thrift.setBusinessScope(merchant.getBusinessScope());									//经营范围
			thrift.setShortName(merchant.getShortName());											//企业简称
			thrift.setIpcNo(merchant.getIpcNo());													//icp号码
			thrift.setTaxRegistrationCertificateNo(merchant.getTaxRegistrationCertificateNo());		//税务登记证号码
			thrift.setContactorIdcardNo(merchant.getContactorIdcardNo());							//联系人身份证
			thrift.setBusinessLicenseEndTime(DateUtil.dateToString(merchant.getBusinessLicenseEndTime(),DateUtil.DATE_FORMAT_YYYYMMDD));//营业期限
			thrift.setLegalCertificateEndTime(DateUtil.dateToString(merchant.getLegalCertificateEndTime(),DateUtil.DATE_FORMAT_YYYYMMDD));//法人身份证有效期
			thrift.setContactorCertificateEndTime(DateUtil.dateToString(merchant.getContactorCertificateEndTime(),DateUtil.DATE_FORMAT_YYYYMMDD));//联系人身份证有效期
		}
        return thrift;
    }

    @Override
    public String insert(MerchantThrift merchantThrift) throws TException {
		String userId = merchantThrift.getUserId();
		//构建一个对象，做更新或者插入
		Merchant merchantInsert = new Merchant();
		//没存过
		merchantInsert.setUserId(Integer.valueOf(merchantThrift.getUserId()));  // 商户ID
		merchantInsert.setType(merchantThrift.getType());//公司类型
		merchantInsert.setCertificateType(merchantThrift.getCertificateType());//证件类型
		merchantInsert.setCompanyName(merchantThrift.getCompanyName()); // 公司名称
		merchantInsert.setProvinceName(merchantThrift.getProvinceName());//省名字
		merchantInsert.setProvinceCode(merchantThrift.getProvinceCode());//省代码
		merchantInsert.setCityName(merchantThrift.getCityName());//市名字
		merchantInsert.setCityCode(merchantThrift.getCityCode());//市代码
		merchantInsert.setCountyName(merchantThrift.getCountyName());//县名字
		merchantInsert.setCountyCode(merchantThrift.getCountyCode());//县代码
		merchantInsert.setBusinessAddress(merchantThrift.getBusinessAddress());//公司注册地址
		merchantInsert.setWebsite(merchantThrift.getWebsite());//网站地址
		merchantInsert.setIpcNo(merchantThrift.getIpcNo());//ipc备案号
		merchantInsert.setBusinessLicenseNo(merchantThrift.getBusinessLicenseNo());//营业执照号
		merchantInsert.setBusinessLicenseEndTime(DateUtil.stringToDate(merchantThrift.getBusinessLicenseEndTime(),DateUtil.DATE_FORMAT_YYYYMMDD));
		merchantInsert.setOrganizationCode(merchantThrift.getOrganizationCode());
		merchantInsert.setTaxRegistrationCertificateNo(merchantThrift.getTaxRegistrationCertificateNo());//税务登记号
		merchantInsert.setBusinessScope(merchantThrift.getBusinessScope());//经营范围
		merchantInsert.setLegalRepresentative(merchantThrift.getLegalRepresentative());//法人姓名
		merchantInsert.setLegalIdcard(merchantThrift.getLegalIdcard());//法人身份证号
		merchantInsert.setLegalCertificateEndTime(DateUtil.stringToDate(merchantThrift.getLegalCertificateEndTime(),DateUtil.DATE_FORMAT_YYYYMMDD));//法人身份证有效期
		merchantInsert.setContactor(merchantThrift.getContactor());
		merchantInsert.setContactorIdcardNo(merchantThrift.getContactorIdcardNo());
		merchantInsert.setContactorCertificateEndTime(DateUtil.stringToDate(merchantThrift.getContactorCertificateEndTime(),DateUtil.DATE_FORMAT_YYYYMMDD));
		merchantInsert.setContactorPhone(merchantThrift.getContactorPhone());
		merchantInsert.setShortName(merchantThrift.getShortName());//企业简称
		merchantInsert.setAuthorizationLetter(merchantThrift.getAuthorizationLetter());//委托授权书
		Merchant merchant = merchantDao.get(userId);
			if(null == merchant){
				//做插入
				merchantInsert.setEmail(merchantThrift.getEmail());//登录邮箱名
				merchantInsert.setCreateTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
				merchantDao.insert(merchantInsert);
			}else{
				//做更新
			  merchantInsert.setLegalAuditStatus("");
				merchantInsert.setRcAuditStatus("");
				merchantDao.update(merchantInsert);
			}
			return "Success";
    	}

	@Override
	public String update(MerchantThrift merchantThrift) throws TException {
		Merchant merchant = new Merchant();
		merchant.setUserId(Integer.valueOf(merchantThrift.getUserId()));
		merchant.setBusinessLicenseFile(merchantThrift.getBusinessLicenseFile());//营业执照
		//如果是普通证件类型
		if(merchantThrift.getCertificateType() != null && merchantThrift.getCertificateType().equals(CertificateType.ORDINARY.getValue())){
			merchant.setBusinessLicenseFile(merchantThrift.getBusinessLicenseFile());
			merchant.setOrganizationCodeCertificate(merchantThrift.getOrganizationCodeCertificate());//组织机构代码
			merchant.setTaxRegistrationCertificate(merchantThrift.getTaxRegistrationCertificate());//税务登记证
			merchant.setPermitsAccounts(merchantThrift.getPermitsAccounts());//开户许可证
			merchant.setLegalCertificateFront(merchantThrift.getLegalCertificateFront());//正面
			merchant.setLegalCertificateBack(merchantThrift.getLegalCertificateBack());//背面
			merchant.setContactorCertificateFront(merchantThrift.getContactorCertificateFront());//联系人身份证正面
			merchant.setContactorCertificateBack(merchantThrift.getContactorCertificateBack());//联系人身份证背面
			merchantDao.update(merchant);
			return "TRUE";
		}
		merchant.setOrganizationCodeCertificate("");//组织机构代码
		merchant.setTaxRegistrationCertificate("");//税务登记证
		merchant.setPermitsAccounts(merchantThrift.getPermitsAccounts());//开户许可证
		merchant.setLegalCertificateFront(merchantThrift.getLegalCertificateFront());//正面
		merchant.setLegalCertificateBack(merchantThrift.getLegalCertificateBack());//背面
		merchant.setContactorCertificateFront(merchantThrift.getContactorCertificateFront());//联系人身份证正面
		merchant.setContactorCertificateBack(merchantThrift.getContactorCertificateBack());//联系人身份证背面
		merchantDao.update(merchant);
		return "TRUE";
	}

	@Override
	public String queryBossId(MerchantThrift merchantThrift) throws TException {
		String userId = merchantThrift.getUserId();
		Merchant merchant = merchantDao.get(userId);
		if(null!=merchant){
			return String.valueOf(merchant.getUserId());
		}
		return  String.valueOf(merchantEmployeeDao.queryBossIdByEmployeeId(Integer.valueOf(userId)));
	}

	@Override
	public String changeLegalAuditStatus(String merchantId) throws TException {
		Merchant merchant = new Merchant();
		merchant.setUserId(Integer.valueOf(merchantId));
		merchant.setLegalAuditStatus(RouteStatus.AUDING.getValue());//法务审核状态
		merchant.setObjection("");
		merchantDao.legalAuditStatus(merchant);
		return "Success";
	}


	@Override
    public List<MerchantThrift> findList(MerchantThrift arg0) throws TException {
        return null;
    }
}
