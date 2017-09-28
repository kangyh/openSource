package com.heepay.manage.modules.reconciliation.web;


import com.google.common.collect.Lists;
import com.heepay.billingutils.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.billing.BillingBecomeEffect;
import com.heepay.enums.billing.BillingChannelManageCheckFlg;
import com.heepay.enums.billing.CheckWay;
import com.heepay.enums.billing.RemoteAdressPath;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.DictList;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;
import com.heepay.manage.modules.reconciliation.service.SettleChannelManagerService;
import com.heepay.manage.modules.reconciliation.util.AESCode;
import com.heepay.manage.modules.reconciliation.util.ChannelTypeClear;
import com.heepay.manage.modules.reconciliation.web.util.RuleType;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.modules.sys.utils.UserUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;


/***
 * 
 * 
 * 描    述：通道管理表
 *
 * 创 建 者： wangl
 * 创建时间：  2016年9月23日下午1:38:03
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
@Controller
@RequestMapping(value = "${adminPath}/reconciliation/CheckMaintenance")
public class SettleChannelManagerController extends BaseController {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private SettleChannelManagerService settleChannelManagerService;
	
	@Autowired
	private ExcelExport excelController;//下载方法

	/**
	 * @方法说明：首页查询操作通道管理显示
	 * @时间： 2017-05-09 09:48 AM
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelManager:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleChannelManager settleChannelManager, 
					   HttpServletRequest request,
					   HttpServletResponse response, 
					   Model model) {


        //使用cookie保存查询条件
        settleChannelManager = (SettleChannelManager) SaveConditions.result(settleChannelManager, "settleChannelManager", request, response);

        String channelCode = settleChannelManager.getChannelCode();
		String bankNo = settleChannelManager.getBankNo();
		
		//生成组合的名称 ，根据通道合作方组合生产名称入库
		String channelName = settleChannelManager.getChannelName();
		String bankName = settleChannelManager.getBankName();
		if(StringUtils.isNotBlank(bankName)){
			
			String BathName=channelName+"-"+bankName;
			settleChannelManager.setChannelName(BathName);
		}
		if(StringUtils.isNotBlank(bankNo)){
			String BathCode=channelCode+"-"+bankNo;
			settleChannelManager.setChannelCode(BathCode);
		}
		//根据条件查询出符合的数据，显示到页面
		Page<SettleChannelManager> page = settleChannelManagerService.findPage(new Page<SettleChannelManager>(request, response), settleChannelManager);
		List<SettleChannelManager> clearingCRList = Lists.newArrayList();

		//支付通道类型枚举
		List<EnumBean> dataEntityChannelType = ChannelTypeClear.getList();
		model.addAttribute("checkTypeList", dataEntityChannelType);
		
		//生效标识
		List<EnumBean> becomngeEffect = Lists.newArrayList();
		for (BillingBecomeEffect checkFlg : BillingBecomeEffect.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			becomngeEffect.add(ct);
		}
		model.addAttribute("becomngeEffect", becomngeEffect);
		
		//对账标识
		List<EnumBean> manageCheckFlg = Lists.newArrayList();
		for (BillingChannelManageCheckFlg checkFlg : BillingChannelManageCheckFlg.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			manageCheckFlg.add(ct);
		}
		model.addAttribute("manageCheckFlg", manageCheckFlg);

		//查询通道合作方的内容
		List<EnumBean> dataEntity = DictList.channelPartner();
		model.addAttribute("dataEntity", dataEntity);

		for (SettleChannelManager clearingCR : page.getList()) {
			logger.info("查询数据开始------>{}",clearingCR);
			if(Constants.STATIC_N.equals(clearingCR.getEffectFlg())){//N
				clearingCR.setDeleteStatus(Constants.STATIC_N);
			}
			
			//通道业务类型
			if(StringUtils.isNotBlank(clearingCR.getChannelType())){
				clearingCR.setChannelType(ChannelTypeClear.labelOf(clearingCR.getChannelType()));
			}
			
			//通道管理生效标识类型effect_flg
			if(StringUtils.isNotBlank(clearingCR.getSettleWay())){
				clearingCR.setSettleWay(CheckWay.labelOf(clearingCR.getSettleWay()));
			}
			
			
			//通道管理生效标识类型effect_flg
			if(StringUtils.isNotBlank(clearingCR.getEffectFlg())){
				clearingCR.setEffectFlg(BillingBecomeEffect.labelOf(clearingCR.getEffectFlg()));
			}
			//通道管理对账标识类型check_flg
			if(StringUtils.isNotBlank(clearingCR.getCheckFlg())){
				clearingCR.setCheckFlg(BillingChannelManageCheckFlg.labelOf(clearingCR.getCheckFlg()));
			}
			
			//规则类型
			if(StringUtils.isNotBlank(clearingCR.getRuleType())){
				clearingCR.setRuleType(RuleType.labelOf(clearingCR.getRuleType()));
			}


			clearingCRList.add(clearingCR);
		}
		page.setList(clearingCRList);
		model.addAttribute("page", page);
		logger.info("查询数据结束------>{}",channelName,bankNo);
		
		//为页面回显返回数据，查分组合命名
		String channelCode2 = settleChannelManager.getChannelCode();
		try {
			if(StringUtils.isNotBlank(channelCode2)){
				//将组合命名的数据拆分开
				int lastIndexOf = channelCode2.lastIndexOf("-");
				if(lastIndexOf!=-1){
					String  substring= channelCode2.substring(0,lastIndexOf);
					settleChannelManager.setChannelCode(substring);
				}
			}
		} catch (Exception e) {
			logger.error("转换失败------>{}"+channelCode2,e);
		}
		
		model.addAttribute("settleChannelManager", settleChannelManager);
		
		return "modules/reconciliation/CheckMaintenance";
	}
	
	/**
	 * @方法说明：记录信息导出
	 * @时间： 2017-05-09 09:48 AM
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelManager:view")
	@RequestMapping(value = "/export")
	public void export(RedirectAttributes redirectAttributes,
						 SettleChannelManager settleChannelManager, 
						 HttpServletRequest request, 
						 HttpServletResponse response) {
		
			String channelCode = settleChannelManager.getChannelCode();
			String bankNo = settleChannelManager.getBankNo();
			
			//生成组合的名称
			String channelName = settleChannelManager.getChannelName();
			String bankName = settleChannelManager.getBankName();
			
			//生成组合的名称 ，根据通道合作方组合生产名称入库
			if(StringUtils.isNotBlank(bankName)){
				String BathName=channelName+"-"+bankName;
				settleChannelManager.setChannelName(BathName);
			}
			if(StringUtils.isNotBlank(bankNo)){
				String BathCode=channelCode+"-"+bankNo;
				settleChannelManager.setChannelCode(BathCode);
			}
		List<Map<String, Object>> clearingCR=null;
		/**
		 * 导出为excel表格
		 */
		try {
			clearingCR = settleChannelManagerService.findListExcel(settleChannelManager);
			logger.info("查询数据开始------>{}"+clearingCR);
		} catch (Exception e1) {
			logger.error("查询数据错误------>{}",e1);
		}
		
		//下载的标题行和表格插入对应的字段值
		String[] headerArray = {"通道合作方","支付通道类型","对账标识","对账文件获取地址","对账文件存放地址","生效标识","规则类型","创建者","创建时间","更新者","更新时间"};
		String[] showField = {"channelName","channelType","checkFlg","remoteAdress","localFilePath","effectFlg","ruleType","createAuthor","createTime","updateAuthor","updateTime"};
		
		try {
			excelController.exportExcel("对账参数维护记录", headerArray,clearingCR,showField, request, response);
		} catch (Exception e) {
			logger.error("ClearingChannelRecordController list has a error:", e);
		}
	}

	/**
	 * @方法说明：修改和添加跳转方法
	 * @时间： 2017-05-09 09:48 AM
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "updateAndAdd")
	public String updateAndAdd(@RequestParam(value = "channelManangeId",required = false) Integer channelManangeId,
							   Model model){

		//支付通道类型枚举
		List<EnumBean> dataEntityChannelType = ChannelTypeClear.getList();

		SettleChannelManager settleChannelManager = null;
		if(null != channelManangeId){
			logger.info("执行了修改方法--->{参数}"+channelManangeId);
			 settleChannelManager = settleChannelManagerService.getEntity(channelManangeId);

			String remoteAdress = settleChannelManager.getRemoteAdress();

			//根据下载方式判断具体的格式
			if (RemoteAdressPath.FTP.getValue().equals(remoteAdress.substring(0, 3))) {//ftp
				// 下载文件
				String substring = remoteAdress.substring(3);
				settleChannelManager.setRemoteAdress(substring);
				settleChannelManager.setRemoteAdressPath(remoteAdress.substring(0, 3));
			} else if (RemoteAdressPath.SFTP.getValue().equals(remoteAdress.substring(0, 4))) {//sftp
				String substring = remoteAdress.substring(4);
				settleChannelManager.setRemoteAdress(substring);
				settleChannelManager.setRemoteAdressPath(remoteAdress.substring(0, 4));
			}
			settleChannelManager.setRemoteUserName(AESCode.AESDncode(settleChannelManager.getRemoteUserName()));
			settleChannelManager.setRemotePassword(AESCode.AESDncode(settleChannelManager.getRemotePassword()));
			//通道业务类型
			if(StringUtils.isNotBlank(settleChannelManager.getChannelType())){
				settleChannelManager.setChannelType(ChannelTypeClear.labelOf(settleChannelManager.getChannelType()));
			}
		}else{
			settleChannelManager = new SettleChannelManager();

			model.addAttribute("checkFlgList", dataEntityChannelType);

			//通道管理对账标识类型
			List<EnumBean> checkFlg = Lists.newArrayList();
			for (BillingChannelManageCheckFlg checkFlgs : BillingChannelManageCheckFlg.values()) {
				EnumBean ct = new EnumBean();
				ct.setValue(checkFlgs.getValue());
				ct.setName(checkFlgs.getContent());
				checkFlg.add(ct);
			}
			model.addAttribute("checkFlg", checkFlg);


			//查询通道合作方的内容
			List<EnumBean> dataEntity = DictList.channelPartner();
			model.addAttribute("dataEntity", dataEntity);

		}


		//文件下载的方式
		List<EnumBean> adressPath = Lists.newArrayList();
		for (RemoteAdressPath checkFlg : RemoteAdressPath.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			adressPath.add(ct);
		}
		model.addAttribute("adressPath", adressPath);

		//生效标识
		List<EnumBean> effectFlg = Lists.newArrayList();
		for (BillingBecomeEffect checkFlg : BillingBecomeEffect.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			effectFlg.add(ct);
		}
		model.addAttribute("effectFlg", effectFlg);

		//规则类型
		List<EnumBean> ruleType = Lists.newArrayList();
		for (RuleType checkFlg : RuleType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			ruleType.add(ct);
		}
		model.addAttribute("ruleType", ruleType);

		model.addAttribute("settleChannelManager", settleChannelManager);
		return "modules/reconciliation/CheckMaintenanceAddUpdate";
	}


	/**
	 * @方法说明：修改和添加的方法
	 * @时间： 2017-05-09 09:49 AM
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "save")
	public String  saveAndAddEntity(SettleChannelManager settleChannelManager,
									RedirectAttributes redirectAttributes,
									@RequestParam(value="channelManangeId",required=false) String id) throws ParseException{

		if(StringUtils.isNotBlank(id)){
			Long channelManangeId = Long.parseLong(id);
			logger.info("通道管理表执行了修改方法--->{条件}"+channelManangeId);

			settleChannelManager.setChannelManangeId(channelManangeId);
			String remoteAdress = settleChannelManager.getRemoteAdress();
			String remoteAdressPath = settleChannelManager.getRemoteAdressPath();
			settleChannelManager.setRemoteAdress(remoteAdressPath+remoteAdress);
			//添加跟新人和更新时间
			settleChannelManager.setUpdateTime(new Date());//更新时间
			settleChannelManager.setUpdateAuthor(UserUtils.getUser().getName());//跟新人
			// 用户名和密码加密
			settleChannelManager.setRemoteUserName(AESCode.AESEncode(settleChannelManager.getRemoteUserName()));
			settleChannelManager.setRemotePassword(AESCode.AESEncode(settleChannelManager.getRemotePassword()));
			
			if("".equals(settleChannelManager.getPort())) {
				settleChannelManager.setPort(null);
			}
			try {
				settleChannelManagerService.updateEntity(settleChannelManager);
				addMessage(redirectAttributes, "更新成功");
			} catch (Exception e) {
				logger.info("通道管理表执行了修改方法--->{异常}"+e.getMessage());
				addMessage(redirectAttributes, "更新失败");
			}

		}else {

			String channelCode = settleChannelManager.getChannelCode();
			String bankNo = settleChannelManager.getBankNo();
			String remoteAdress = settleChannelManager.getRemoteAdress();
			String remoteAdressPath = settleChannelManager.getRemoteAdressPath();

			//生成组合的名称 ，根据通道合作方组合生产名称入库
			String channelName = settleChannelManager.getChannelName();
			String bankName = settleChannelManager.getBankName();

			if(channelCode.equals("DIRCON")){//只有直连才组合
				if(StringUtils.isNotBlank(bankName)){  //   bankName
					String BathName=channelName+"-"+bankName;
					settleChannelManager.setChannelName(BathName);
				}
				if(StringUtils.isNotBlank(bankNo)){
					String BathCode=channelCode+"-"+bankNo;
					settleChannelManager.setChannelCode(BathCode);
				}
			}


			String channelType = settleChannelManager.getChannelType();
			String localFilePath = settleChannelManager.getLocalFilePath();

			//配置下载地址为当前服务的目录下
			String Path=localFilePath+"/"+settleChannelManager.getChannelCode()+"/"+channelType+"/";//    home/blling
			settleChannelManager.setLocalFilePath(Path);
			settleChannelManager.setRemoteAdress(remoteAdressPath+remoteAdress);

			//添加创建人和创建时间
			settleChannelManager.setCreateTime(new Date());//创建时间
			settleChannelManager.setCreateAuthor(UserUtils.getUser().getName());//创建人
			
			// 用户名和密码加密
			settleChannelManager.setRemoteUserName(AESCode.AESEncode(settleChannelManager.getRemoteUserName()));
			settleChannelManager.setRemotePassword(AESCode.AESEncode(settleChannelManager.getRemotePassword()));
			
			if("".equals(settleChannelManager.getPort())) {
				settleChannelManager.setPort(null);
			}

			String checkFlg = settleChannelManager.getCheckFlg();

			//同时插入文件对账和接口对账时两条数据
			String stringarray1[]=checkFlg.split(",");
			int lengthNum = stringarray1.length;

			/**
			 * 如果同时插入文件对账和接口对账时，默认开启文件对账
			 */
			logger.info("添加对账标识为---->"+stringarray1);
			for(String stemp:stringarray1){
				settleChannelManager.setCheckFlg(stemp);
				try {
					if( lengthNum == 2){
						if(stemp.equals(BillingChannelManageCheckFlg.BCMCFF.getValue())){//FI
							settleChannelManager.setEffectFlg(Constants.STATIC_Y);//生效标识,生效（默认） 无效 Y
							settleChannelManagerService.addEntity(settleChannelManager);
						}else{
							settleChannelManager.setEffectFlg(Constants.STATIC_N);//生效标识,生效（默认） 无效 Y
							settleChannelManagerService.addEntity(settleChannelManager);
						}
						addMessage(redirectAttributes, "添加成功");
					}else{
						settleChannelManagerService.addEntity(settleChannelManager);
						addMessage(redirectAttributes, "添加成功");
					}
				} catch (Exception e) {
					logger.error("添加失败,请维护---->", e.getMessage());
					addMessage(redirectAttributes, "添加失败,请维护");
				}
			}
		}

		return "redirect:"+Global.getAdminPath()+"/reconciliation/CheckMaintenance?cache=1";
	}


	/**
	 * @方法说明：修改和添加的方法
	 * @时间： 2017-05-09 09:49 AM
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "/delete/{channelManangeId}")
	public String  delete(RedirectAttributes redirectAttributes,@PathVariable(value="channelManangeId") Integer channelManangeId){

		try {
		 	settleChannelManagerService.deleteEntityById(channelManangeId);
			addMessage(redirectAttributes, "删除成功!");
		} catch (Exception e) {
			addMessage(redirectAttributes, "删除失败,请维护");
			logger.error("删除失败,请维护---->", e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/reconciliation/CheckMaintenance?cache=1";
	}
	
	/**
	 * @方法说明：将用户名和密码加密更新
	 * @时间： 2017-07-26 09:49 AM
	 * @创建人：chenym
	 */
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "modify")
	@ResponseBody
	public String  modify(RedirectAttributes redirectAttributes,@RequestParam(value="username",required=false) String username,
			@RequestParam(value="password",required=false) String password){
		String msg = "";
		try {
			String remoteUserName = AESCode.AESEncode(username);
			String remotePassword = AESCode.AESEncode(password);
			settleChannelManagerService.updateAll(remoteUserName, remotePassword, username);
			msg = "1000";
		} catch (Exception e) {
			msg = "2000";
			logger.error("更新失败{}", e);
		}
		return msg;
	}
}
	
