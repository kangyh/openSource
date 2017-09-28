package com.heepay.risk.service.impl;



import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.heepay.billingutils.util.SendMail;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.SendWeiXinUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.enums.pbc.PbcAccountType;
import com.heepay.enums.pbc.ReportStatus;
import com.heepay.enums.pbc.RequestEventType;
import com.heepay.risk.dao.PbcAccountDynamicMapper;
import com.heepay.risk.dao.PbcAccountDynamicQueryMapper;
import com.heepay.risk.dao.PbcAccountTransDetailMapper;
import com.heepay.risk.dao.PbcAttachmentQueryMapper;
import com.heepay.risk.dao.PbcFeedBackMapper;
import com.heepay.risk.dao.PbcPaymentAccountQueryMapper;
import com.heepay.risk.dao.PbcQueryInfoMapper;
import com.heepay.risk.dao.PbcReleaseFeedbackMapper;
import com.heepay.risk.dao.PbcTransDetailQueryMapper;
import com.heepay.risk.dao.PbcTransSerialCardPaymentAccountQueryMapper;
import com.heepay.risk.entity.PbcAccountDynamicQuery;
import com.heepay.risk.entity.PbcAttachmentQuery;
import com.heepay.risk.entity.PbcFeedBack;
import com.heepay.risk.entity.PbcPaymentAccountQuery;
import com.heepay.risk.entity.PbcQueryInfo;
import com.heepay.risk.entity.PbcTransDetailQuery;
import com.heepay.risk.entity.PbcTransSerialCardPaymentAccountQuery;
import com.heepay.risk.service.IBankFraudTransactionService;
import com.heepay.risk.vo.BankFraudAttachment;
import com.heepay.risk.vo.BankFraudBodyQueryVO;
import com.heepay.risk.vo.BankFraudHeaderQueryVO;
import com.heepay.risk.vo.BankFraudQueryVO;
import com.heepay.risk.vo.FraudMailModel;
@Service
public class BankFraudTransactionServiceImpl implements IBankFraudTransactionService {
      
	private static final Logger logger = LogManager.getLogger();
	 @Autowired
		private PbcQueryInfoMapper pbcQueryInfoMapper;
	   @Autowired
	 	private PbcTransDetailQueryMapper pbcTransDetailQueryMapper;
	   @Autowired
		private PbcPaymentAccountQueryMapper pbcPaymentAccountQueryMapper;
	   @Autowired
		private PbcAccountDynamicQueryMapper pbcAccountDynamicQueryMapper;
	   @Autowired
		private PbcFeedBackMapper pbcFeedbackMapper;
	   @Autowired 
	  private  PbcAttachmentQueryMapper pbcAttachmentQueryMapper;
	   @Autowired 
		  private  PbcTransSerialCardPaymentAccountQueryMapper pbcTransSerialCardPaymentAccountQueryMapper;
	   @Autowired 
	   private FraudMailModel fraudMailModel;
	   /**
		 * 
		* 
		* 描    述：
		*
		* 创 建 者：   dongzj
		* 创建时间： 2016年12月21日 
		* 创建描述：保存支付账户交易明细

		* 
		*        
		 */
   @Transactional
	@Override
	public boolean SaveTransInfo(BankFraudQueryVO vo) {
	     
		if(vo==null)
			return false;
		BankFraudBodyQueryVO body=vo.getBody();
		PbcQueryInfo existQueryInfo=this.pbcQueryInfoMapper.GetQueryInfo(body.getApplicationID());
		if(existQueryInfo!=null)
		{   
			logger.info("支付账户交易明细查询已经存在相同的业务申请编号:"+body.getApplicationID());
			return  true;
		}
		
		List<BankFraudAttachment> list=body.getAttachments();
		
		PbcTransDetailQuery transDetailQuery=new PbcTransDetailQuery();
		transDetailQuery.setTransStartTime(body.getStartTime()==null?null:DateUtil.stringToDate(body.getStartTime(),"yyyyMMddHHmmss"));
		transDetailQuery.setTransEndTime(body.getExpireTime()==null?null:DateUtil.stringToDate(body.getExpireTime(),"yyyyMMddHHmmss"));
		transDetailQuery.setData(body.getData());
		transDetailQuery.setDataType(body.getDataType());
		transDetailQuery.setPaymentAccountDetailType(PbcAccountType.PBC_FEATURE_CODE_ENTERPRISE.getValue());
		transDetailQuery.setApplicationCode(body.getApplicationID());
		transDetailQuery.setInquiryMode(body.getInquiryMode());
		String mailBody=String.format(fraudMailModel.getBody(),vo.getHead().getTxCode(),"支付账户交易明细查询",vo.getBody().getApplicationOrgName(),vo.getBody().getApplicationTime(),vo.getHead().getTransSerialNumber(),vo.getBody().getApplicationID());
		String str=fraudMailModel.getMailTo();
		try
		{
		SendWeiXinUtil.getInstance().sendWeiXin(mailBody);
		if(!StringUtil.isBlank(str))
		{   
	      String[] arrMailTo=str.split(",");
		  for(String mailto:arrMailTo)
			   SendMail.getInstance().sendMail(mailto, fraudMailModel.getSubject(), mailBody, null);
		}
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage());
		}
		try
		{
			//保存查询信息主表
			this.SaveQueryInfo(vo);
			this.SaveAttachmentInfo(list, vo);
			this.pbcTransDetailQueryMapper.insert(transDetailQuery);
			//保存反馈信息主表
		   this.SaveFeedBack(vo, RequestEventType.CASE_REPORT.getValue());
		   
		   
		}
		catch(Exception ex)
		{   
			 logger.error("保存支付账户交易明细出现异常:"+ex.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   
			return false;
		}
		return true;
	}
   /**
	 * 
	* 
	* 描    述：
	*
	* 创 建 者：   dongzj
	* 创建时间： 2016年12月21日 
	* 创建描述：保存交易流水号查询银行卡/支付帐号

	* 
	*        
	 */
@Transactional
@Override
public boolean SaveTransCardPaymentAccount(BankFraudQueryVO vo) {
	if(vo==null)
		return false ;
	BankFraudBodyQueryVO body=vo.getBody();
	PbcQueryInfo existQueryInfo=this.pbcQueryInfoMapper.GetQueryInfo(body.getApplicationID());
	if(existQueryInfo!=null)
	{   
		logger.info("交易流水号查询银行卡/支付帐号查询已经存在相同的业务申请编号:"+body.getApplicationID());
		return true ;
	}
	
	List<BankFraudAttachment> list=body.getAttachments();

	PbcTransSerialCardPaymentAccountQuery transSerialCardPaymentAccountQuery=new PbcTransSerialCardPaymentAccountQuery();
	transSerialCardPaymentAccountQuery.setBankId(body.getBankID());
	transSerialCardPaymentAccountQuery.setTransTime(body.getTransactionDate()==null?null:DateUtil.stringToDate(body.getTransactionDate(),"yyyyMMdd"));
	transSerialCardPaymentAccountQuery.setData(body.getData());
	transSerialCardPaymentAccountQuery.setDataType(body.getDataType());
	transSerialCardPaymentAccountQuery.setApplicationCode(body.getApplicationID());
	try
	{
		String mailBody=String.format(fraudMailModel.getBody(),vo.getHead().getTxCode(),"按交易流水号查询银行卡/支付帐号",vo.getBody().getApplicationOrgName(),vo.getBody().getApplicationTime(),vo.getHead().getTransSerialNumber(),vo.getBody().getApplicationID());
	SendWeiXinUtil.getInstance().sendWeiXin(mailBody);  
	String str=fraudMailModel.getMailTo();
	   if(!StringUtil.isBlank(str))
	   {   
		   String[] arrMailTo=str.split(",");
		   for(String mailto:arrMailTo)
		   SendMail.getInstance().sendMail(mailto, fraudMailModel.getSubject(), mailBody, null);
	   }
	}
	catch(Exception ex)
	{
		logger.error(ex.getMessage());
	}
	try
	{
		this.SaveQueryInfo(vo);
		this.SaveAttachmentInfo(list, vo);
		this.pbcTransSerialCardPaymentAccountQueryMapper.insert(transSerialCardPaymentAccountQuery);
		this.SaveFeedBack(vo, RequestEventType.TRANSNO_ACCOUNT.getValue());
		
	}
    catch(Exception ex)
	{   
    	 logger.error("保存交易流水号查询银行卡/支付帐号出现异常:"+ex.getMessage());
    	  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   
    	return false;
	}
	return true;
}
/**
 * 
* 
* 描    述：
*
* 创 建 者：   dongzj
* 创建时间： 2016年12月21日 
* 创建描述：保存关联全支付账号信息

* 
*        
 */
@Transactional
@Override
public boolean SavePaymentAccount(BankFraudQueryVO vo) {
	
	if(vo==null)
		return false;
	BankFraudBodyQueryVO body=vo.getBody();
	PbcQueryInfo existQueryInfo=this.pbcQueryInfoMapper.GetQueryInfo(body.getApplicationID());
	if(existQueryInfo!=null)
	{   
		logger.info("关联全支付账号信息查询已经存在相同的业务申请编号:"+body.getApplicationID());
		return true ;	
	}
	PbcPaymentAccountQuery paymentAccountQuery=new PbcPaymentAccountQuery();
	paymentAccountQuery.setAccountOwnerIdType(body.getAccountOwnerIDType());
	paymentAccountQuery.setAccountOwnerName(body.getAccountOwnerName());
	paymentAccountQuery.setData(body.getData());
	paymentAccountQuery.setDataType(body.getDataType());
	paymentAccountQuery.setSubjectType(PbcAccountType.PBC_FEATURE_CODE_ENTERPRISE.getValue());
	paymentAccountQuery.setApplicationCode(body.getApplicationID());
	try
	{
		String mailBody=String.format(fraudMailModel.getBody(),vo.getHead().getTxCode(),"关联全支付账号查询",vo.getBody().getApplicationOrgName(),vo.getBody().getApplicationTime(),vo.getHead().getTransSerialNumber(),vo.getBody().getApplicationID());
	SendWeiXinUtil.getInstance().sendWeiXin(mailBody);
	   String str=fraudMailModel.getMailTo();
	   if(!StringUtil.isBlank(str))
	   {   
		   String[] arrMailTo=str.split(",");
		   for(String mailto:arrMailTo)
		   SendMail.getInstance().sendMail(mailto, fraudMailModel.getSubject(), mailBody, null);
	   }
	}
	catch(Exception ex)
	{
		logger.error(ex.getMessage());
	}
	try
	{
		this.SaveQueryInfo(vo);
		List<BankFraudAttachment> list=body.getAttachments();
		this.SaveAttachmentInfo(list, vo);
		this.pbcPaymentAccountQueryMapper.insert(paymentAccountQuery);		
		this.SaveFeedBack(vo, RequestEventType.PAYMENY_ACCOUNT.getValue());
		
	}
	catch(Exception ex)
	{  
		 logger.error("保存关联全支付账号信息出现异常:"+ex.getMessage());
		 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   
		return false;
	}
	return true;
}
/**
 * 
* 
* 描    述：
*
* 创 建 者：   dongzj
* 创建时间： 2016年12月21日 
* 创建描述：保存动态查询信息

* 
*        
 */
@Transactional
@Override
public boolean SaveAccountDynamic(BankFraudQueryVO vo) {
	
	if(vo==null)
		return false ;
	BankFraudBodyQueryVO body=vo.getBody();
	PbcQueryInfo existQueryInfo=this.pbcQueryInfoMapper.GetQueryInfo(body.getApplicationID());
	if(existQueryInfo!=null)
	{   
		logger.info("动态查询信息查询已经存在相同的业务申请编号:"+body.getApplicationID());
		return true ;	
	}
	
	List<BankFraudAttachment> list=body.getAttachments();
	
	PbcAccountDynamicQuery pbcAccountDynamicQuery=new PbcAccountDynamicQuery();
	pbcAccountDynamicQuery.setIdType(body.getidType());
	pbcAccountDynamicQuery.setIdNumber(body.getidNumber());
	pbcAccountDynamicQuery.setAccountNumber(body.getAccountNumber());
	pbcAccountDynamicQuery.setDataType(body.getDataType());
	pbcAccountDynamicQuery.setSubjectType(PbcAccountType.PBC_FEATURE_CODE_ENTERPRISE.getValue());
	pbcAccountDynamicQuery.setApplicationCode(body.getApplicationID());
	pbcAccountDynamicQuery.setStartTime(body.getStartTime()==null?null:DateUtil.stringToDate(body.getStartTime(),"yyyyMMddHHmmss"));
	pbcAccountDynamicQuery.setEndTime(body.getExpireTime()==null?null:DateUtil.stringToDate(body.getExpireTime(),"yyyyMMddHHmmss"));
	try
	{
		String mailBody=String.format(fraudMailModel.getBody(),vo.getHead().getTxCode(),"账户动态查询",vo.getBody().getApplicationOrgName(),vo.getBody().getApplicationTime(),vo.getHead().getTransSerialNumber(),vo.getBody().getApplicationID());
	SendWeiXinUtil.getInstance().sendWeiXin(mailBody);  
	String str=fraudMailModel.getMailTo();
	   if(!StringUtil.isBlank(str))
	   {   
		   String[] arrMailTo=str.split(",");
		   for(String mailto:arrMailTo)
		   SendMail.getInstance().sendMail(mailto, fraudMailModel.getSubject(), mailBody, null);
	   }
	}
	catch(Exception ex)
	{
		logger.error(ex.getMessage());
	}
	try
	{
		this.SaveQueryInfo(vo);
		this.SaveAttachmentInfo(list, vo);
		this.pbcAccountDynamicQueryMapper.insert(pbcAccountDynamicQuery);
		this.SaveFeedBack(vo, RequestEventType.ACCOUNTS_INVOLVED.getValue());
		
	}
	catch(Exception ex)
	{  
		 logger.error("保存动态查询信息出现异常:"+ex.getMessage());
		  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   
		return false;
	}
	return true;
}
/**
 * 
* 
* 描    述：
*
* 创 建 者：   dongzj
* 创建时间： 2016年12月21日 
* 创建描述：保存账号主体详情

* 
*        
 */
@Transactional
@Override
public boolean SaveAccountDetail(BankFraudQueryVO vo) {
	if(vo==null)
		return false;
	BankFraudBodyQueryVO body=vo.getBody();
	PbcQueryInfo existQueryInfo=this.pbcQueryInfoMapper.GetQueryInfo(body.getApplicationID());
	if(existQueryInfo!=null)
	{   
		logger.info("账号主体详情查询已经存在相同的业务申请编号:"+body.getApplicationID());
		return true ;	
	}
	
	List<BankFraudAttachment> list=body.getAttachments();
	try
	{
		String mailBody=String.format(fraudMailModel.getBody(),vo.getHead().getTxCode(),"账户主体详情查询",vo.getBody().getApplicationOrgName(),vo.getBody().getApplicationTime(),vo.getHead().getTransSerialNumber(),vo.getBody().getApplicationID());
	SendWeiXinUtil.getInstance().sendWeiXin(mailBody); 
	String str=fraudMailModel.getMailTo();
	   if(!StringUtil.isBlank(str))
	   {   
		   String[] arrMailTo=str.split(",");
		   for(String mailto:arrMailTo)
		   SendMail.getInstance().sendMail(mailto, fraudMailModel.getSubject(), mailBody, null);
	   }
	}
	catch(Exception ex)
	{
		logger.error(ex.getMessage());
	}
    try
    {
    	this.SaveQueryInfo(vo);
    	this.SaveAttachmentInfo(list, vo);
    	this.SaveFeedBack(vo, RequestEventType.ABNORMAL_ACCOUNTS.getValue());
    	
    }
    catch(Exception ex)
    {
    	 logger.error("保存账号主体详情出现异常:"+ex.getMessage());
    	  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   
 		return false;
    }
    return true;
}
/**
 * 
* 
* 描    述：
*
* 创 建 者：   dongzj
* 创建时间： 2016年12月21日 
* 创建描述：保存动态查询解除信息

* 
*        
 */
@Transactional
@Override
public boolean SaveReleaseFeedback(BankFraudQueryVO vo) {
	if(vo==null)
		return false;
	BankFraudBodyQueryVO body=vo.getBody();
	List<PbcAccountDynamicQuery> dynamicList=this.pbcAccountDynamicQueryMapper.selectByApplicationCode(body.getOriginalApplicationID());
	if(dynamicList==null||dynamicList.size()==0)
		return  true;
	PbcQueryInfo existQueryInfo=this.pbcQueryInfoMapper.GetQueryInfo(body.getApplicationID());
	if(existQueryInfo!=null)
	{   
		logger.info("动态查询解除信息查询已经存在相同的业务申请编号:"+body.getApplicationID());
		return true ;	
	}
	List<BankFraudAttachment> list=body.getAttachments();
	/**PbcAccountDynamicQuery entity=dynamicList.get(0);
	PbcReleaseFeedback pbcReleaseFeedback=new PbcReleaseFeedback();
	pbcReleaseFeedback.setCaseNumber(body.getCaseNumber());
	pbcReleaseFeedback.setApplicationCode(entity.getApplicationCode());
	pbcReleaseFeedback.setAccountNumber(entity.getAccountNumber());*/
	try
	{
		String mailBody=String.format(fraudMailModel.getBody(),vo.getHead().getTxCode(),"账户动态查询解除",vo.getBody().getApplicationOrgName(),vo.getBody().getApplicationTime(),vo.getHead().getTransSerialNumber(),vo.getBody().getApplicationID());
	SendWeiXinUtil.getInstance().sendWeiXin(mailBody); 
	String str=fraudMailModel.getMailTo();
	   if(!StringUtil.isBlank(str))
	   {   
		   String[] arrMailTo=str.split(",");
		   for(String mailto:arrMailTo)
		   SendMail.getInstance().sendMail(mailto, fraudMailModel.getSubject(), mailBody, null);
	   }
	}
	catch(Exception ex)
	{
		logger.error(ex.getMessage());
	}
	try
	{
		this.SaveQueryInfo(vo);
		this.SaveAttachmentInfo(list, vo);
		this.SaveFeedBack(vo,RequestEventType.EXCEPTION_EVENTS.getValue());
		
		//this.pbcReleaseFeedbackMapper.insert(pbcReleaseFeedback);
	}
	catch(Exception ex)
	{  
	     logger.error("保存动态查询解除信息出现异常:"+ex.getMessage());
	     TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();   
		return false;
	}
	return true;
		
}

private void SaveQueryInfo(BankFraudQueryVO vo)
{   
	BankFraudHeaderQueryVO header=vo.getHead();
	BankFraudBodyQueryVO body=vo.getBody();
	PbcQueryInfo queryInfo=new PbcQueryInfo();
	queryInfo.setApplicationId(body.getApplicationID());
	queryInfo.setApplicationOrgId(body.getApplicationOrgID());
	queryInfo.setApplicationOrgName(body.getApplicationOrgName());
	queryInfo.setCaseNumber(body.getCaseNumber());
	queryInfo.setCaseType(body.getCaseType());
	queryInfo.setApplicationTime(body.getApplicationTime()==null?null:DateUtil.stringToDate(body.getApplicationTime(),"yyyyMMddHHmmss"));
	queryInfo.setData(body.getData());
	queryInfo.setDataType(body.getDataType());		
	queryInfo.setOnlinePayCompanyId(body.getOnlinePayCompanyID());
	queryInfo.setOnlinePayCompanyName(body.getOnlinePayCompanyName());
	queryInfo.setInvestigatorIdNumber(body.getInvestigatorIDNumber());
	queryInfo.setInvestigatorIdType(body.getInvestigatorIDType());
	queryInfo.setInvestigatorName(body.getInvestigatorName());
	queryInfo.setMessageFromCode(header.getMessageFrom());
	queryInfo.setTransSerialNumber(header.getTransSerialNumber());
	queryInfo.setTransTypeCode(header.getTxCode());
	queryInfo.setOperatorIdNumber(body.getOperatorIDNumber());
	queryInfo.setOperatorIdType(body.getOperatorIDType());
	queryInfo.setOperatorName(body.getOperatorName());
	queryInfo.setOperatorPhoneNumber(body.getOperatorPhoneNumber());
	queryInfo.setOriginalApplicationId(body.getOriginalApplicationID());
	queryInfo.setReason(body.getReason());
	queryInfo.setRemark(body.getRemark());
	PbcQueryInfo existQueryInfo=this.pbcQueryInfoMapper.GetQueryInfo(body.getApplicationID());
	if(existQueryInfo==null)
	this.pbcQueryInfoMapper.insert(queryInfo);
}
private void SaveFeedBack(BankFraudQueryVO vo,String status)
{   
	
	BankFraudHeaderQueryVO header=vo.getHead();
	BankFraudBodyQueryVO body=vo.getBody();
	PbcFeedBack pbcFeedBack =new PbcFeedBack();
	pbcFeedBack.setApplicationId(body.getApplicationID());
	pbcFeedBack.setApplicationOrgCode(body.getApplicationOrgID());
	pbcFeedBack.setApplicationOrgName(body.getApplicationOrgName());
	pbcFeedBack.setApplicationTime(body.getApplicationTime()==null?null:DateUtil.stringToDate(body.getApplicationTime(),"yyyyMMddHHmmss"));
	pbcFeedBack.setOperatorName(body.getOperatorName());
	pbcFeedBack.setOperatorPhoneNumber(body.getOperatorPhoneNumber());
	pbcFeedBack.setTransSerialNumber(header.getTxCode());
	pbcFeedBack.setTransSerialNumber(header.getTransSerialNumber());
	pbcFeedBack.setRequestEventType(status);
	pbcFeedBack.setToId(header.getMessageFrom());
	pbcFeedBack.setStatus(ReportStatus.NOREPORTED.getValue());
	pbcFeedBack.setRiskStatus(ReportStatus.NOREPORTED.getValue());
	pbcFeedBack.setParamType(body.getDataType());
	pbcFeedBack.setParams(body.getData());
	this.pbcFeedbackMapper.insert(pbcFeedBack);
}
private void SaveAttachmentInfo(List<BankFraudAttachment> list,BankFraudQueryVO vo)
{
	BankFraudBodyQueryVO body=vo.getBody();
	if(list!=null&&list.size()>0)
	{
		for(BankFraudAttachment attachment :list)
		{
			PbcAttachmentQuery attachmentQuery =new PbcAttachmentQuery();
			attachmentQuery.setApplicationCode(body.getApplicationID());
			attachmentQuery.setFileName(attachment.getFileName());
			attachmentQuery.setFileContent(attachment.getContent());
			attachmentQuery.setCreateTime(new Date());
			this.pbcAttachmentQueryMapper.insert(attachmentQuery);
		}		
	}		
}
}
