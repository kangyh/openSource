/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heepay.enums.*;
import com.heepay.manage.common.cache.PrimaryKeyCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.enums.AccountCode;
import com.heepay.enums.InternalAccountType;
import com.heepay.enums.MerchantAccountType;
import com.heepay.enums.MerchantStatus;
import com.heepay.manage.common.cache.PrimaryKeyCreator;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.account.dao.MerchantAccountDao;
import com.heepay.manage.modules.account.entity.MerchantAccount;

/**
 *
 * 描    述：账户管理Service
 *
 * 创 建 者： @author zjx
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
@Service
@Transactional(readOnly = true)
public class MerchantAccountService extends CrudService<MerchantAccountDao, MerchantAccount> {

	@Autowired
	MerchantAccountDao merchantAccountDao;

	private static final Logger log = LogManager.getLogger();

	public MerchantAccount get(String id) {
		return super.get(id);
	}
	
	public List<MerchantAccount> findList(MerchantAccount merchantAccount) {
		return super.findList(merchantAccount);
	}
	
	public Page<MerchantAccount> findPage(Page<MerchantAccount> page, MerchantAccount merchantAccount) {
		return super.findPage(page, merchantAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantAccount merchantAccount) {
		super.save(merchantAccount, false);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantAccount merchantAccount) {
		super.delete(merchantAccount);
	}

	@Transactional(readOnly = false)
	public void updateStatus(MerchantAccount merchantAccount) {
		merchantAccountDao.updateStatus(merchantAccount);
	}

	@Transactional(readOnly = false)
	public List<MerchantAccount> getByMerchantId(String merchantId){
		return merchantAccountDao.getByMerchantId(merchantId);
	}

	@Transactional(readOnly = false)
	public MerchantAccount getMerchantByType(Map<String, Object> paramsMap){
		List<MerchantAccount> list = merchantAccountDao.getMerchantByType(paramsMap);
		if(list != null && !list.isEmpty()){
			MerchantAccount merchantAccount = list.get(0);
			return merchantAccount;
		}
		return null;
	}

	/**
	 * 商户注册账号
	 * @param merchantId
	 * @param manchantLonginName
	 * @param accountName
	 * @param accountType
	 */
	@Transactional(readOnly = false)
	public boolean createMerchanAccount(Long merchantId,String manchantLonginName,String accountName,String accountType,String opSystemResouce) {

		log.info("商户ID:{},商户名称:{},账户名称:{},账户类型:{},商户注册余额账户开始。", merchantId, manchantLonginName, accountName, accountType);

		List<MerchantAccount> merchantAccounts= getByMerchantId(String.valueOf(merchantId));
		if(merchantAccounts != null && merchantAccounts.size() > 0){
			log.error("商户ID:{},商户名称:{},账户名称:{},账户类型:{},商户余额账户已经存在。", merchantId, manchantLonginName, accountName, accountType);
			return false;
		}
		MerchantAccount merchantAccount = new MerchantAccount();
		merchantAccount.setAccountId(PrimaryKeyCreator.getMerchantAccountId());
		merchantAccount.setMerchantId(merchantId);
		merchantAccount.setMerchantLoginName(manchantLonginName);
		merchantAccount.setAccountName(accountName);
		merchantAccount.setType(accountType);

		String initAmount = "0.0000";
		merchantAccount.setBalanceAmount(initAmount);
		merchantAccount.setBalanceAvailableAmount(initAmount);
		merchantAccount.setBalanceAvailableWithdrawAmount(initAmount);
		merchantAccount.setBalanceFreezedAmount(initAmount);
		merchantAccount.setTotalInAmount(initAmount);
		merchantAccount.setTotalOutAmount(initAmount);
		merchantAccount.setStatus(MerchantStatus.NORMAL.getValue());
		merchantAccount.setCreateTime(new Date());
		merchantAccount.setUpdateTime(new Date());
		merchantAccount.setCurrency("156");
		merchantAccount.setBalanceDirection("1");
		merchantAccount.setAccountCode("20000000");
		merchantAccount.setIsHot("Y");
		merchantAccount.setVersion("0");
		int total = 0;
		Long accountId = merchantAccount.getAccountId();
		try {
			save(merchantAccount, false);
			if(AllowSystemType.TPDS.getValue().equals(opSystemResouce)){
				merchantAccount.setAccountId(PrimaryKeyCreator.getDepositoryMerchantAccountId(accountId));
				merchantAccount.setAccountName(accountName + "_" + MerchantAccountType.DEPOSITORY_ACCOUNT.getContent());
				merchantAccount.setType(MerchantAccountType.DEPOSITORY_ACCOUNT.getValue());
				save(merchantAccount, false);
			}
			log.info("商户ID:{},商户名称:{},账户名称:{},账户类型:{},注册商户账户成功。", merchantId, manchantLonginName, accountName, accountType);
			merchantAccount.setAccountId(PrimaryKeyCreator.getMerchanSettlementAccountId(accountId));
			merchantAccount.setAccountCode("21000000");
			merchantAccount.setAccountName(accountName + "_" + InternalAccountType.SETTLEMENT_ACCOUNT.getContent());
			merchantAccount.setAccountTitle(InternalAccountType.SETTLEMENT_ACCOUNT.getContent());
			merchantAccount.setType(MerchantAccountType.INTERNAL_ACCOUNT.getValue());
			merchantAccount.setIsHot("N");
			save(merchantAccount, false);
			log.info("商户ID:{},商户名称:{},账户名称:{},账户类型:{},注册商户待结算账户成功。", merchantId, manchantLonginName, accountName, accountType);
			merchantAccount.setAccountId(PrimaryKeyCreator.getManualMerchanSettlementAccountId(accountId));
			merchantAccount.setAccountCode("22000000");
			merchantAccount.setAccountName(accountName + "_" + InternalAccountType.MANUAL_SETTLEMENT_ACCOUNT.getContent());
			merchantAccount.setAccountTitle(InternalAccountType.MANUAL_SETTLEMENT_ACCOUNT.getContent());
			merchantAccount.setType(MerchantAccountType.INTERNAL_ACCOUNT.getValue());
			merchantAccount.setIsHot("N");
			save(merchantAccount, false);
			log.info("商户ID:{},商户名称:{},账户名称:{},账户类型:{},注册手动商户待结算账户成功。", merchantId, manchantLonginName, accountName, accountType);
		} catch (Exception e) {
			e.printStackTrace();
			total = 0;
			log.error("商户ID:{},商户名称:{},账户名称:{},账户类型:{},商户注册账户异常。",merchantId,manchantLonginName,accountName,accountType);
		}
		log.info("商户注册账户信息: {} ", merchantAccount.toString());
		if(total == 0){
			return false;
		}
		return true;
	}
	
	
	
	/**
	 * 
	* @description 获取入账备付金账户
	* @author 王亚洪       
	* @created 2016年12月12日 下午7:48:00     
	* @param paramsMap
	* @return
	 */
	public List<MerchantAccount> getReserveAccount(Map<String, Object> paramsMap){
	  return merchantAccountDao.getReserveAccount(paramsMap);
	}

	/**
	 * 
	* @description 根据账户id和类型获取账户信息
	* @author 王亚洪       
	* @created 2016年12月19日 下午1:39:34     
	* @param accountId
	* @param type
	* @return
	 */
	public MerchantAccount getMerchantAccountByAccountIdAndType(Long accountId, String type){
	    Map<String, Object> paramsMap = new HashMap<String, Object>();
	    paramsMap.put("accountId", accountId);
	    paramsMap.put("type", type);
	    List<MerchantAccount> list = merchantAccountDao.getMerchantAccountByAccountIdAndType(paramsMap);
	    if(list != null && !list.isEmpty()){
	      MerchantAccount merchantAccount = list.get(0);
	      merchantAccount.setBalanceAmount(merchantAccount.getBalanceAmount().substring(0, merchantAccount.getBalanceAmount().length()-2));
	      return merchantAccount;
	    }
	    return null;
	}

	
	/**
	 * 
	* @description 根据科目获取merchantId
	* @author 王亚洪       
	* @created 2017年1月9日 下午5:12:24     
	* @param paramsMap
	* @return
	 */
	public List<Long> getSelectMerchantIds(Map<String, Object> paramsMap){
	  return merchantAccountDao.getSelectMerchantIds(paramsMap);
	}
	
	/**
	 * 
	* @description 更新商户
	* @author 王亚洪       
	* @created 2017年1月12日 下午1:29:59     
	* @param merchantAccount
	* @return
	 */
	@Transactional(readOnly = false)
  public int updateMerchantAccount(MerchantAccount merchantAccount){
    return merchantAccountDao.updateMerchantAccount(merchantAccount);
  }



	/**
	 *
	 * @return
	 */
	public List<MerchantAccount> getMerchantIdGroupByMerchantId(){
		List<MerchantAccount> merchantAccountList = new ArrayList<MerchantAccount>();
		List<String> list = merchantAccountDao.getMerchantIdGroupByMerchantId();
		if(list.size() > 0){
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("merchantIds", list);
			merchantAccountList = merchantAccountDao.getAllMerchantAccountInMerchantId(paramsMap);
		}
		return merchantAccountList;
	}

	/**
	 *
	 */
	@Transactional
	public void onSupplementManualAccount(){
		List<MerchantAccount> merchantAccounts = getMerchantIdGroupByMerchantId();
		Map<Long,List<String>> mlongListMap = new HashMap<Long,List<String>>();
		Map<Long,MerchantAccount> merchantAccountMap = new HashMap<Long,MerchantAccount>();
		for(MerchantAccount mMerchantAccount : merchantAccounts) {
			if(mlongListMap.get(mMerchantAccount.getMerchantId()) ==null){
				mlongListMap.put(mMerchantAccount.getMerchantId(),Collections.singletonList(mMerchantAccount.getAccountCode()));
			}else{
				List<String> tempMerchantAccounts = new ArrayList<String>(mlongListMap.get(mMerchantAccount.getMerchantId()));
				tempMerchantAccounts.add(mMerchantAccount.getAccountCode());
				mlongListMap.put(mMerchantAccount.getMerchantId(), tempMerchantAccounts);
			}
			if(mMerchantAccount.getType().equals(MerchantAccountType.MERCHANT_ACCOUNT.getValue())){
				merchantAccountMap.put(mMerchantAccount.getMerchantId(),mMerchantAccount);
			}
		}
		for(Map.Entry<Long,List<String>> m : mlongListMap.entrySet()){
			List<String> codeList = m.getValue();
			MerchantAccount merchantAccount1 = merchantAccountMap.get(m.getKey());
			if(!codeList.contains(AccountCode.MERCHANT_SETTLEMENT_CODE.getValue())){
				createMerchanSettleAccount(merchantAccount1.getMerchantId(), merchantAccount1.getMerchantLoginName(), merchantAccount1.getAccountName());
			}
			if(!codeList.contains(AccountCode.MERCHANT_MANUAL_SETTLEMENT_CODE.getValue())){
				createMerchanManualSettleAccount(merchantAccount1.getMerchantId(), merchantAccount1.getMerchantLoginName(), merchantAccount1.getAccountName());
			}
		}
	}

	@Transactional(readOnly = false)
	public boolean createMerchanSettleAccount(Long merchantId,String manchantLonginName,String accountName) {
		log.info("商户ID:{},商户名称:{},账户名称:{},商户注册余额账户开始。", merchantId, manchantLonginName, accountName);
		List<MerchantAccount> merchantAccounts= getByMerchantId(String.valueOf(merchantId));
		if(merchantAccounts != null && merchantAccounts.size() ==3){
			log.error("商户ID:{},商户名称:{},账户名称:{},商户余额账户已经存在。", merchantId, manchantLonginName, accountName);
			return false;
		}
		MerchantAccount iMerchantAccount = new MerchantAccount();
		for(MerchantAccount merchantAccount :merchantAccounts){
			if(merchantAccount.getType().equals(MerchantAccountType.MERCHANT_ACCOUNT.getValue())){
				iMerchantAccount = merchantAccount;
				break;
			}
		}
		MerchantAccount merchantAccount = new MerchantAccount();
		int total = 0;
		try {
			merchantAccount.setAccountId(PrimaryKeyCreator.getMerchanSettlementAccountId(iMerchantAccount.getAccountId()));
			merchantAccount.setMerchantId(merchantId);
			merchantAccount.setMerchantLoginName(manchantLonginName);
			merchantAccount.setType(MerchantAccountType.INTERNAL_ACCOUNT.getValue());
			String initAmount = "0.0000";
			merchantAccount.setBalanceAmount(initAmount);
			merchantAccount.setBalanceAvailableAmount(initAmount);
			merchantAccount.setBalanceAvailableWithdrawAmount(initAmount);
			merchantAccount.setBalanceFreezedAmount(initAmount);
			merchantAccount.setTotalInAmount(initAmount);
			merchantAccount.setTotalOutAmount(initAmount);
			merchantAccount.setStatus(MerchantStatus.NORMAL.getValue());
			merchantAccount.setCreateTime(new Date());
			merchantAccount.setUpdateTime(new Date());
			merchantAccount.setCurrency("156");
			merchantAccount.setBalanceDirection("1");
			merchantAccount.setAccountCode("21000000");
			merchantAccount.setAccountName(accountName + "_" + InternalAccountType.MANUAL_SETTLEMENT_ACCOUNT.getContent());
			merchantAccount.setAccountTitle(InternalAccountType.SETTLEMENT_ACCOUNT.getContent());
			merchantAccount.setType(MerchantAccountType.INTERNAL_ACCOUNT.getValue());
			merchantAccount.setIsHot("N");
			merchantAccount.setVersion("0");
			save(merchantAccount, false);
			log.info("商户ID:{},商户名称:{},账户名称:{},注册手动商户待结算账户成功。", merchantId, manchantLonginName, accountName);
		} catch (Exception e) {
			e.printStackTrace();
			total = 0;
			log.error("商户ID:{},商户名称:{},账户名称:{},商户注册账户异常。",merchantId,manchantLonginName,accountName);
		}
		log.info("商户注册账户信息: {} ", merchantAccount.toString());
		if(total == 0){
			return false;
		}
		return true;
	}
	/**
	 * 商户注册手动结算账号
	 * @param merchantId
	 * @param manchantLonginName
	 * @param accountName
	 */
	@Transactional(readOnly = false)
	public boolean createMerchanManualSettleAccount(Long merchantId,String manchantLonginName,String accountName) {
		log.info("商户ID:{},商户名称:{},账户名称:{},商户注册余额账户开始。", merchantId, manchantLonginName, accountName);
		List<MerchantAccount> merchantAccounts= getByMerchantId(String.valueOf(merchantId));
		if(merchantAccounts != null && merchantAccounts.size() ==3){
			log.error("商户ID:{},商户名称:{},账户名称:{},商户余额账户已经存在。", merchantId, manchantLonginName, accountName);
			return false;
		}
		MerchantAccount iMerchantAccount = new MerchantAccount();
		for(MerchantAccount merchantAccount :merchantAccounts){
			if(merchantAccount.getType().equals(MerchantAccountType.MERCHANT_ACCOUNT.getValue())){
				iMerchantAccount = merchantAccount;
				break;
			}
		}
		MerchantAccount merchantAccount = new MerchantAccount();
		int total = 0;
		try {
			merchantAccount.setAccountId(PrimaryKeyCreator.getManualMerchanSettlementAccountId(iMerchantAccount.getAccountId()));
			merchantAccount.setMerchantId(merchantId);
			merchantAccount.setMerchantLoginName(manchantLonginName);
			merchantAccount.setType(MerchantAccountType.INTERNAL_ACCOUNT.getValue());
			String initAmount = "0.0000";
			merchantAccount.setBalanceAmount(initAmount);
			merchantAccount.setBalanceAvailableAmount(initAmount);
			merchantAccount.setBalanceAvailableWithdrawAmount(initAmount);
			merchantAccount.setBalanceFreezedAmount(initAmount);
			merchantAccount.setTotalInAmount(initAmount);
			merchantAccount.setTotalOutAmount(initAmount);
			merchantAccount.setStatus(MerchantStatus.NORMAL.getValue());
			merchantAccount.setCreateTime(new Date());
			merchantAccount.setUpdateTime(new Date());
			merchantAccount.setCurrency("156");
			merchantAccount.setBalanceDirection("1");
			merchantAccount.setAccountCode("22000000");
			merchantAccount.setAccountName(accountName + "_" + InternalAccountType.MANUAL_SETTLEMENT_ACCOUNT.getContent());
			merchantAccount.setAccountTitle(InternalAccountType.MANUAL_SETTLEMENT_ACCOUNT.getContent());
			merchantAccount.setType(MerchantAccountType.INTERNAL_ACCOUNT.getValue());
			merchantAccount.setIsHot("N");
			merchantAccount.setVersion("0");
			save(merchantAccount, false);
			log.info("商户ID:{},商户名称:{},账户名称:{},注册手动商户待结算账户成功。", merchantId, manchantLonginName, accountName);
		} catch (Exception e) {
			e.printStackTrace();
			total = 0;
			log.error("商户ID:{},商户名称:{},账户名称:{},商户注册账户异常。",merchantId,manchantLonginName,accountName);
		}
		log.info("商户注册账户信息: {} ", merchantAccount.toString());
		if(total == 0){
			return false;
		}
		return true;
	}

	/**
	 * 
	* @discription 
	* @author yanxb       
	* @created 2017年3月3日 下午4:47:32     
	* @return
	 */
	public MerchantAccount getAccountId() {
		return merchantAccountDao.getAccountId();
	}



}
