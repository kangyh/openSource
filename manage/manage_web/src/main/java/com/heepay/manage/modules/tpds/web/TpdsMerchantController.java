package com.heepay.manage.modules.tpds.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.BankcardAuthStatus;
import com.heepay.enums.tpds.AccountType;
import com.heepay.enums.tpds.BankCardAuthType;
import com.heepay.enums.tpds.CardType;
import com.heepay.enums.tpds.Status;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.cbms.entity.CbmsCountrysetting;
import com.heepay.manage.modules.cbms.service.CbmsCountrysettingService;
import com.heepay.manage.modules.cbms.validate.Validator;
import com.heepay.manage.modules.merchant.vo.MerchantBankCard;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.manage.modules.tpds.entity.TpdsMerchant;
import com.heepay.manage.modules.tpds.entity.TpdsMerchantAccount;
import com.heepay.manage.modules.tpds.service.TpdsMerchantAccountService;
import com.heepay.manage.modules.tpds.service.TpdsMerchantService;
import com.heepay.manage.modules.tpds.web.rpc.ConfigServiceClient;
import com.heepay.manage.modules.tpds.web.rpc.TpdsClient;
import com.heepay.rpc.payment.model.BankcardAuthModel;
import com.heepay.tpds.TpdsDataUtils;

/***
 * 
 * 
 * 描 述：商户信息表
 *
 * 创 建 者： wangl 创建时间： 2016年9月23日下午1:38:03 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/tpds/tpdsMerchant")
public class TpdsMerchantController extends BaseController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private TpdsMerchantService tpdsMerchantService;

	@Autowired
	private CbmsCountrysettingService cbmsCountrysettingService;

	@Autowired
	private TpdsMerchantAccountService tpdsMerchantAccountService;
	// 交易服务
	@Autowired
	private TpdsClient tpdsClient;

	@Autowired
	private ConfigServiceClient configServiceClient;

	/**
	 * 
	 * @方法说明：分润明细显示的查询方法
	 * @时间：9 Feb 201713:14:55
	 * @创建人：wangl
	 */
	@RequiresPermissions("tpds:tpdsMerchant:view")
	@RequestMapping(value = { "list", "" })
	public String list(TpdsMerchant tpdsMerchant, HttpServletRequest request, HttpServletResponse response,
			Model model) {

		Page<TpdsMerchant> page = tpdsMerchantService.findPage(new Page<TpdsMerchant>(request, response), tpdsMerchant);
		logger.info("操作日志表------>{查询记录}" + page.getList());
		for (TpdsMerchant merchant : page.getList()) {
			
			if (StringUtils.isNoneBlank(merchant.getContactor())){
				merchant.setContactor(StringUtil.getEncryptedName(merchant.getContactor()));
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("tpdsMerchant", tpdsMerchant);

		logger.info("操作日志表------>{}" + request);
		return "modules/tpds/tpdsMerchant";
	}

	/**
	 * 
	 * @方法说明：添加商户和修改操作页面跳转
	 * @时间：9 Feb 201713:14:38
	 * @创建人：wangl
	 */
	@RequiresPermissions("tpds:tpdsMerchant:edit")
	@RequestMapping(value = "/addAndUpdate")
	public String addEntity(@RequestParam(value = "merchantId", required = false) Integer merchantId,
			@RequestParam(value = "read", required = false) String read, Model model) {

		List<CbmsCountrysetting> cbmsCountrysettingList = cbmsCountrysettingService.findAllList();

		TpdsMerchant tpdsMerchant = null;
		// 添加操作
		if (merchantId == null) {
			// 创建一个模型
			tpdsMerchant = new TpdsMerchant();
			logger.info("商户信息表------>{添加操作}");
		} else {// 修改操作

			// 说明是查看操作
			if (StringUtils.isNoneBlank(read)) {
				model.addAttribute("read", read);
			}
			logger.info("商户信息表------>{修改操作}--->{主键}" + merchantId);
			tpdsMerchant = tpdsMerchantService.getEntityById(merchantId);

			// 页面图片加域，将地址设置进参数中方便页面显示
			if (StringUtils.isNoneBlank(tpdsMerchant.getPermitsAccounts())) {
				tpdsMerchant.setPermitsAccounts(RandomUtil.getFastDfs(tpdsMerchant.getPermitsAccounts()));
			}
			if (StringUtils.isNoneBlank(tpdsMerchant.getLegalCertificateFront())) {
				tpdsMerchant.setLegalCertificateFront(RandomUtil.getFastDfs(tpdsMerchant.getLegalCertificateFront()));
			}
			if (StringUtils.isNoneBlank(tpdsMerchant.getLegalCertificateBack())) {
				tpdsMerchant.setLegalCertificateBack(RandomUtil.getFastDfs(tpdsMerchant.getLegalCertificateBack()));
			}
			if (StringUtils.isNoneBlank(tpdsMerchant.getBusinessLicenseFile())) {
				tpdsMerchant.setBusinessLicenseFile(RandomUtil.getFastDfs(tpdsMerchant.getBusinessLicenseFile()));
			}
			if (StringUtils.isNoneBlank(tpdsMerchant.getTaxRegistrationCertificate())) {
				tpdsMerchant.setTaxRegistrationCertificate(
						RandomUtil.getFastDfs(tpdsMerchant.getTaxRegistrationCertificate()));
			}
			if (StringUtils.isNoneBlank(tpdsMerchant.getOrganizationCodeCertificate())) {
				tpdsMerchant.setOrganizationCodeCertificate(
						RandomUtil.getFastDfs(tpdsMerchant.getOrganizationCodeCertificate()));
			}

			// 字段解密
			if (StringUtils.isNoneBlank(tpdsMerchant.getLegalIdcard())) {

				String legalIdcard = Aes.decryptStr(tpdsMerchant.getLegalIdcard(), Constants.QuickPay.SYSTEM_KEY);
				tpdsMerchant.setLegalIdcard(StringUtil.getEncryptedIdcard(legalIdcard));
			}
			if (StringUtils.isNoneBlank(tpdsMerchant.getContactorIdcardNo())) {
				String contactorIdcardNo = Aes.decryptStr(tpdsMerchant.getContactorIdcardNo(),
						Constants.QuickPay.SYSTEM_KEY);
				tpdsMerchant.setContactorIdcardNo(StringUtil.getEncryptedIdcard(contactorIdcardNo));
			}
			if (StringUtils.isNoneBlank(tpdsMerchant.getContactorPhone())) {
				String contactorPhone = Aes.decryptStr(tpdsMerchant.getContactorPhone(), Constants.QuickPay.SYSTEM_KEY);
				tpdsMerchant.setContactorPhone(StringUtil.getEncryptedMobile(contactorPhone));
			}
			if (StringUtils.isNoneBlank(tpdsMerchant.getLegalMobile())) {
				String legalMobile = Aes.decryptStr(tpdsMerchant.getLegalMobile(), Constants.QuickPay.SYSTEM_KEY);
				tpdsMerchant.setLegalMobile(StringUtil.getEncryptedMobile(legalMobile));
			}
			if (StringUtils.isNoneBlank(tpdsMerchant.getLlegalperson())){
				tpdsMerchant.setLlegalperson(StringUtil.getEncryptedName(tpdsMerchant.getLlegalperson()));
			}
			if (StringUtils.isNoneBlank(tpdsMerchant.getContactor())){
				tpdsMerchant.setContactor(StringUtil.getEncryptedName(tpdsMerchant.getContactor()));
			}
		}

		model.addAttribute("cbmsCountrysettingList", cbmsCountrysettingList);
		model.addAttribute("tpdsMerchant", tpdsMerchant);
		return "modules/tpds/tpdsMerchantAddAndUpdate";
	}

	/**
	 * 
	 * @方法说明：保存入库的方法
	 * @时间：9 Feb 201714:26:35
	 * @创建人：wangl
	 */
	@RequiresPermissions("tpds:tpdsMerchant:edit")
	@RequestMapping(value = "/save")
	public String save(TpdsMerchant tpdsMerchant, Model model, RedirectAttributes redirectAttributes,
			@RequestParam(value = "merchantId", required = false) Integer merchantId,
			@RequestParam(value = "permitsAccountsFile", required = false) MultipartFile permitsAccountsFile,
			@RequestParam(value = "legalCertificateFrontFile", required = false) MultipartFile legalCertificateFrontFile,
			@RequestParam(value = "legalCertificateBackFile", required = false) MultipartFile legalCertificateBackFile,
			@RequestParam(value = "businessLicenseFileFrontFile", required = false) MultipartFile businessLicenseFileFrontFile,
			@RequestParam(value = "taxRegistrationCertificateFile", required = false) MultipartFile taxRegistrationCertificateFile,
			@RequestParam(value = "organizationCodeCertificateFile", required = false) MultipartFile organizationCodeCertificateFile)
					throws Exception {

		if (merchantId == null) {
			// 插入到基础用户表中
			String id = tpdsMerchantService.saveUser(tpdsMerchant);
			if (null != id) {
				tpdsMerchant.setMerchantNo(id);
			}
			logger.info("商户信息表------>{入库操作}--->{保存到交易}--->{返回主键}" + id);

			// 将图片保存到图片服务器上
			tpdsMerchant.setPermitsAccounts(tpdsMerchantService.upLoadPic(permitsAccountsFile));
			tpdsMerchant.setLegalCertificateFront(tpdsMerchantService.upLoadPic(legalCertificateFrontFile));
			tpdsMerchant.setLegalCertificateBack(tpdsMerchantService.upLoadPic(legalCertificateBackFile));
			tpdsMerchant.setBusinessLicenseFile(tpdsMerchantService.upLoadPic(businessLicenseFileFrontFile));
			tpdsMerchant.setTaxRegistrationCertificate(tpdsMerchantService.upLoadPic(taxRegistrationCertificateFile));
			tpdsMerchant.setOrganizationCodeCertificate(tpdsMerchantService.upLoadPic(organizationCodeCertificateFile));

			Boolean flage = tpdsMerchantService.saveMerchant(tpdsMerchant);

			tpdsMerchant.setCreateTime(
					new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
			tpdsMerchant.setCreateAuthor(UserUtils.getUser().getName());

			// 字段加密
			tpdsMerchant = this.encryptStr(tpdsMerchant);
			int num = tpdsMerchantService.saveEntity(tpdsMerchant);
			if (num > 0 && flage) {
				addMessage(redirectAttributes, "商户添加成功");
			} else {
				addMessage(redirectAttributes, "商户添加失败");
			}

		} else {

			if (null != permitsAccountsFile) {
				tpdsMerchant.setPermitsAccounts(tpdsMerchantService.upLoadPic(permitsAccountsFile));
			}
			if (null != legalCertificateFrontFile) {
				tpdsMerchant.setLegalCertificateFront(tpdsMerchantService.upLoadPic(legalCertificateFrontFile));
			}
			if (null != legalCertificateBackFile) {
				tpdsMerchant.setLegalCertificateBack(tpdsMerchantService.upLoadPic(legalCertificateBackFile));
			}
			if (null != businessLicenseFileFrontFile) {
				tpdsMerchant.setBusinessLicenseFile(tpdsMerchantService.upLoadPic(businessLicenseFileFrontFile));
			}
			if (null != taxRegistrationCertificateFile) {
				tpdsMerchant
						.setTaxRegistrationCertificate(tpdsMerchantService.upLoadPic(taxRegistrationCertificateFile));
			}
			if (null != taxRegistrationCertificateFile) {
				tpdsMerchant
						.setOrganizationCodeCertificate(tpdsMerchantService.upLoadPic(organizationCodeCertificateFile));
			}
			tpdsMerchant.setUpdateTime(new Date());
			// tpdsMerchant.setUpdateTime(new
			// SimpleDateFormat("yyyy-MM-dd").parse(new
			// SimpleDateFormat("yyyy-MM-dd").format(new Date())));
			tpdsMerchant.setUpdateAuthor(UserUtils.getUser().getName());

			// 插入基础表
			TpdsMerchant tpds = tpdsMerchantService.getEntityById(merchantId);
			String id1 = tpdsMerchantService.updateUser(tpds);
			if (null != id1) {
				tpds.setMerchantNo(id1);
			}
			logger.info("商户信息表------>{修改入库操作}--->{保存到交易}--->{返回主键}" + id1);
			
			//插入基础表之前先解密
			if (StringUtils.isNoneBlank(tpds.getLegalIdcard())) {

				String legalIdcard = Aes.decryptStr(tpds.getLegalIdcard(), Constants.QuickPay.SYSTEM_KEY);
				tpds.setLegalIdcard(legalIdcard);
			}
			if (StringUtils.isNoneBlank(tpds.getContactorIdcardNo())) {
				String contactorIdcardNo = Aes.decryptStr(tpds.getContactorIdcardNo(),
						Constants.QuickPay.SYSTEM_KEY);
				tpds.setContactorIdcardNo(contactorIdcardNo);
			}
			if (StringUtils.isNoneBlank(tpds.getContactorPhone())) {
				String contactorPhone = Aes.decryptStr(tpds.getContactorPhone(), Constants.QuickPay.SYSTEM_KEY);
				tpds.setContactorPhone(contactorPhone);
			}
			if (StringUtils.isNoneBlank(tpds.getLegalMobile())) {
				String legalMobile = Aes.decryptStr(tpds.getLegalMobile(), Constants.QuickPay.SYSTEM_KEY);
				tpds.setLegalMobile(legalMobile);
			}
			
			tpdsMerchantService.updateMerchant(tpds);

			// 字段加密(存管库)
			tpdsMerchant = this.encryptStr(tpdsMerchant);
			int num = tpdsMerchantService.updateEntity(tpdsMerchant);

			logger.info("商户信息表------>{更新操作操作}");
			if (num > 0) {
				addMessage(redirectAttributes, "商户更新成功");
			} else {
				addMessage(redirectAttributes, "商户更新失败");
			}
		}
		return "redirect:" + Global.getAdminPath() + "/tpds/tpdsMerchant";
	}

	/**
	 * 
	 * @方法说明：用户绑卡页面跳转 @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("tpds:tpdsMerchant:edit")
	@RequestMapping(value = "/binding/{merchantId}")
	public String binding(@PathVariable("merchantId") String merchantId, MerchantBankCard merchantBankCard,
			Model model) {

		logger.info("用户绑卡页面跳转------>{查询主键}" + merchantId);

		// 银行卡类型枚举
		List<EnumBean> cardTypeList = Lists.newArrayList();
		for (CardType cardType : CardType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(cardType.getValue());
			ct.setName(cardType.getContent());
			cardTypeList.add(ct);
		}
		model.addAttribute("cardTypeList", cardTypeList);

		// 签约类型
		List<EnumBean> bankcardAuthTypeList = Lists.newArrayList();
		for (BankCardAuthType bankCardAuthType : BankCardAuthType.values()) {
			EnumBean bt = new EnumBean();
			bt.setValue(bankCardAuthType.getValue());
			bt.setName(bankCardAuthType.getContent());
			bankcardAuthTypeList.add(bt);
		}
		model.addAttribute("bankcardAuthTypeList", bankcardAuthTypeList);

		// 账户类型
		List<EnumBean> accountTypeList = Lists.newArrayList();
		for (AccountType accountType : AccountType.values()) {
			EnumBean at = new EnumBean();
			at.setValue(accountType.getValue());
			at.setName(accountType.getContent());
			accountTypeList.add(at);
		}
		model.addAttribute("accountTypeList", accountTypeList);

		// 设置查询条件
		model.addAttribute("merchantBankCard", merchantBankCard);
		model.addAttribute("merchantId", merchantId);

		return "modules/tpds/riskMerchantFreezeAdd";
	}

	/**
	 * 
	 * @方法说明：获取商户银行信息列表 @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("tpds:tpdsMerchant:edit")
	@RequestMapping(value = "/viewList/{merchantNo}")
	public String list(@PathVariable(value = "merchantNo") String merchantNo, MerchantBankCard merchantBankCard,
			HttpServletRequest request, HttpServletResponse response, Model model) {

		merchantBankCard.setMerchantId(merchantNo);
		// Page<MerchantBankCard> page = merchantBankCardCService.findPage(new
		// Page<MerchantBankCard>(request, response), merchantBankCard);
		Page<MerchantBankCard> page = tpdsMerchantService.findPageList(new Page<MerchantBankCard>(request, response),
				merchantBankCard);
		page.setList(EnumView.changeMerchantBankCard(page.getList()));

		model.addAttribute("page", page);
		return "modules/tpds/merchantBankCardList";
	}

	/**
	 * 
	 * @方法说明：用户绑卡入库 @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("tpds:tpdsMerchant:edit")
	@RequestMapping(value = "/saveEntity/merchantBankCard")
	public String saveEntity(MerchantBankCard merchantBankCard, RedirectAttributes redirectAttributes, Model model) {

		logger.info("用户绑卡页面跳转------>{查询主键}");

		TpdsMerchant tpdsMerchant = tpdsMerchantService
				.getEntityById(Integer.parseInt(merchantBankCard.getMerchantId()));

		// 保存属性
		BankcardAuthModel bankcardAuthModel = new BankcardAuthModel();

		bankcardAuthModel.setMerchantId(Long.parseLong(tpdsMerchant.getMerchantNo()));// 商户ID
		bankcardAuthModel.setMerchantLoginName(tpdsMerchant.getLoginName());// 用户名

		bankcardAuthModel.setBankcardOwnerName(merchantBankCard.getOpenName());// 银行名称
		bankcardAuthModel.setBankcardNo(merchantBankCard.getBankId());// 银行账户号
		bankcardAuthModel.setOpenBankName(merchantBankCard.getOpenBankName());// 开户银行名称
		bankcardAuthModel.setBankName(merchantBankCard.getBankName());// 开户名称
		bankcardAuthModel.setAssociateLineNumber(merchantBankCard.getAssociateLineNumber());// 联行号
		bankcardAuthModel.setBankId(merchantBankCard.getBankNo()); // 银行id
		bankcardAuthModel.setBankcardOwnerName(merchantBankCard.getRecAccName()); // 持卡人姓名
		bankcardAuthModel.setBankcardType(merchantBankCard.getCardTypeCode()); // 银行卡类型
		bankcardAuthModel.setType(merchantBankCard.getBankCardAuthType()); // 签约类型
		bankcardAuthModel.setCreateTime(DateUtil.DATETIME_FORMAT);// 创建时间
		bankcardAuthModel.setAuthTime(DateUtil.DATETIME_FORMAT);// 时间

		if (AccountType.PUBLIC.getValue().equals(merchantBankCard.getAccountType())) {
			byte b = 1;
			bankcardAuthModel.setBankcardOwnerType(b); // 账户类型
		} else {
			byte b = 0;
			bankcardAuthModel.setBankcardOwnerType(b); // 账户类型
		}
		bankcardAuthModel.setStatus(BankcardAuthStatus.SUCCES.getValue());
		int index = 0;
		try {
			index = tpdsClient.saveTpdsTxBankCard(bankcardAuthModel);
			if (index != 0) {
				addMessage(redirectAttributes, "商户绑卡添加成功");
				// 入库操作
				TpdsMerchantAccount tpdsMerchantAccount = new TpdsMerchantAccount();
				tpdsMerchantAccount.setMerchantNo(tpdsMerchant.getMerchantNo()); // 商户编号
				tpdsMerchantAccount.setLoginName(tpdsMerchant.getCompanyName()); // 商户名称
				tpdsMerchantAccount.setCertNo(tpdsMerchant.getLegalIdcard()); // 证件号码
				tpdsMerchantAccount.setCreateDate(new Date()); // 注册时间
				tpdsMerchantAccount.setStatus(Status.ENABLE.getValue()); // 状态

				// 判断是否生成接入系统编码
				TpdsMerchantAccount tpdsAccount = tpdsMerchantAccountService
						.selectByMerchantNo(tpdsMerchant.getMerchantNo());
				if (tpdsAccount != null) {
					if (tpdsAccount.getSystemNo() == null) { // 生成接入码
						// 生成接入系统编码
						for (;;) {
							String systemNo = TpdsDataUtils.systemNo();
							TpdsMerchantAccount TpdsAccount = tpdsMerchantAccountService.selectBySystemNo(systemNo);
							if (TpdsAccount == null) {
								tpdsMerchantAccount.setSystemNo(systemNo);
								tpdsMerchantAccount.setAccNo(TpdsDataUtils.merchantAccountNo(systemNo)); // 台账账户
								break;
							}
						}
					} else {
						tpdsMerchantAccount.setSystemNo(tpdsAccount.getSystemNo());
						tpdsMerchantAccount.setAccNo(TpdsDataUtils.merchantAccountNo(tpdsAccount.getSystemNo())); // 台账账户
						logger.info("商户编码为{},商户已经存在系统接入编码:{}", tpdsMerchant.getMerchantNo(), tpdsAccount.getSystemNo());
					}
				} else {
					// 生成接入系统编码
					for (;;) {
						String systemNo = TpdsDataUtils.systemNo();
						TpdsMerchantAccount TpdsAccount = tpdsMerchantAccountService.selectBySystemNo(systemNo);
						if (TpdsAccount == null) {
							tpdsMerchantAccount.setSystemNo(systemNo);
							tpdsMerchantAccount.setAccNo(TpdsDataUtils.merchantAccountNo(systemNo)); // 台账账户
							break;
						}
					}
				}
				// 判断是存管户还是普通户
				if (BankCardAuthType.REALAUTHENTICATION.getValue().equals(merchantBankCard.getBankCardAuthType())) { // 普通
					tpdsMerchantAccount.setBankCard(merchantBankCard.getBankId()); // 银行卡号
					tpdsMerchantAccount.setBankCardBranch(merchantBankCard.getAssociateLineNumber()); // 银行卡号所在银联号
				} else { // 存管 查找对象
					tpdsMerchantAccount.setBankAccount(merchantBankCard.getBankId()); // 银行卡号
					tpdsMerchantAccount.setBankAccountBranch(merchantBankCard.getAssociateLineNumber()); // 银行卡号所在银联号
				}
				// 判断有没有该条记录（merchantNo）
				TpdsMerchantAccount merchant = tpdsMerchantAccountService
						.selectByMerchantNo(tpdsMerchant.getMerchantNo());
				JsonMapperUtil jsonUtil = new JsonMapperUtil();
				if (merchant == null) {
					// 添加缓冲
					tpdsMerchantAccount = this.encryptStr1(tpdsMerchantAccount); // 字段加密
					String msg = configServiceClient.addMerchantAccount(jsonUtil.toJson(tpdsMerchantAccount));
					// int num =
					// tpdsMerchantAccountService.saveEntity(tpdsMerchantAccount);
					if ("1".equals(msg)) {
						addMessage(redirectAttributes, "添加成功");
					} else {
						addMessage(redirectAttributes, "添加失败");
					}
				} else {
					tpdsMerchantAccount.setTpdsMerchantId(merchant.getTpdsMerchantId());
					// int num1 =
					// tpdsMerchantAccountService.updateEntity(tpdsMerchantAccount);
					tpdsMerchantAccount = this.encryptStr1(tpdsMerchantAccount); // 字段加密
					String msg = configServiceClient.editMerchantAccount(jsonUtil.toJson(tpdsMerchantAccount));
					if ("1".equals(msg)) {
						addMessage(redirectAttributes, "更新成功");
					} else {
						addMessage(redirectAttributes, "更新失败");
					}
				}

			} else {
				addMessage(redirectAttributes, "商户绑卡添加失败");
			}
		} catch (Exception e) {
			logger.info("用户绑卡入库操作异常------>{}" + e.getMessage());
			addMessage(redirectAttributes, "商户绑卡添加失败");
		}

		return "redirect:" + Global.getAdminPath() + "/tpds/tpdsMerchant";
	}

	// 验证网址
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/checkWebsite")
	public String checkWebsite(@RequestParam("webSite") String webSite) {
		boolean flag = Validator.isUrl(webSite);
		if (true == flag) {
			return 1 + "";
		} else {
			return 0 + "";
		}
	}

	// 验证手机号
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/checkTelNumber")
	public String checkTelNumber(@RequestParam("contactorPhone") String contactorPhone) {
		boolean flag = Validator.isMobile(contactorPhone);
		if (true == flag) {
			return 1 + "";
		} else {
			return 0 + "";
		}
	}

	// 验证手机号
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/checkTel1Number")
	public String checkTel1Number(@RequestParam("legalMobile") String legalMobile) {
		boolean flag = Validator.isMobile(legalMobile);
		if (true == flag) {
			return 1 + "";
		} else {
			return 0 + "";
		}
	}

	/**
	 * @discription 注册和修改是的ajax用户名验证
	 * @author guozx
	 * @created 2017年1月18日11:55:44
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "regist")
	public void regist(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		// 获取商户的登录名
		String loginName = request.getParameter("loginName");
		String id = request.getParameter("id");
		response.setContentType("text/html; charset=utf-8");
		// 判断登录名是否填写
		if (loginName == "") {
			// 没有填写返回0
			response.getWriter().write("0");
		} else {
			// 判断登录名是否是邮箱
			if (Validator.isEmail(loginName)) {
				// 查询是否存在已注册用户
				MerchantUser merchantUser = tpdsMerchantService.queryEmailExist(loginName);
				// 没有注册
				if (merchantUser == null) {
					// 可用
					response.getWriter().write("1");
				} else {
					if (id == null) {
						// 不可用
						response.getWriter().write("0");
					} else {
						// 商户名和原商户名一致
						response.getWriter().write("3");
					}
				}
			} else {
				// 邮箱格式不正确
				response.getWriter().write("2");
			}
		}
	}

	// 加密字段(字符串不包含xxx)
	public static TpdsMerchant encryptStr(TpdsMerchant tpdsMerchant) {

		if (tpdsMerchant.getLegalIdcard() != null && !tpdsMerchant.getLegalIdcard().contains("**")) {
			String legalIdcard = Aes.encryptStr(tpdsMerchant.getLegalIdcard(), Constants.QuickPay.SYSTEM_KEY);
			tpdsMerchant.setLegalIdcard(legalIdcard);
		}else{
			tpdsMerchant.setLegalIdcard(null);
		}
		if (tpdsMerchant.getContactorIdcardNo() != null && !tpdsMerchant.getContactorIdcardNo().contains("**")) {
			String contactorIdcardNo = Aes.encryptStr(tpdsMerchant.getContactorIdcardNo(),
					Constants.QuickPay.SYSTEM_KEY);
			tpdsMerchant.setContactorIdcardNo(contactorIdcardNo);
		}else{
			tpdsMerchant.setContactorIdcardNo(null);
		}
		if (tpdsMerchant.getContactorPhone() != null && !tpdsMerchant.getContactorPhone().contains("**")) {
			String contactorPhone = Aes.encryptStr(tpdsMerchant.getContactorPhone(), Constants.QuickPay.SYSTEM_KEY);
			tpdsMerchant.setContactorPhone(contactorPhone);
		}else{
			tpdsMerchant.setContactorPhone(null);
		}
		if (tpdsMerchant.getLegalMobile() != null && !tpdsMerchant.getLegalMobile().contains("**")) {
			String legalMobile = Aes.encryptStr(tpdsMerchant.getLegalMobile(), Constants.QuickPay.SYSTEM_KEY);
			tpdsMerchant.setLegalMobile(legalMobile);
		}else{
			tpdsMerchant.setLegalMobile(null);
		}

		return tpdsMerchant;
	}

	// 字段加密方法
	public static TpdsMerchantAccount encryptStr1(TpdsMerchantAccount tpdsMerchantAccount) {

		if (tpdsMerchantAccount.getBankCard() != null && !tpdsMerchantAccount.getBankCard().contains("**")) {
			String bankCard = Aes.encryptStr(tpdsMerchantAccount.getBankCard(), Constants.QuickPay.SYSTEM_KEY);
			tpdsMerchantAccount.setBankCard(bankCard);
		}
		if (tpdsMerchantAccount.getBankAccount() != null && !tpdsMerchantAccount.getBankAccount().contains("**")) {
			String bankAccount = Aes.encryptStr(tpdsMerchantAccount.getBankAccount(), Constants.QuickPay.SYSTEM_KEY);
			tpdsMerchantAccount.setBankAccount(bankAccount);
		}

		return tpdsMerchantAccount;
	}
}
