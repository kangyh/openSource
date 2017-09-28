package com.heepay.manage.modules.tpds.web;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.manage.modules.tpds.entity.TpdsMerchantCer;
import com.heepay.manage.modules.tpds.service.TpdsMerchantCerService;
import com.heepay.manage.modules.tpds.service.TpdsMerchantService;
import com.heepay.manage.modules.tpds.web.rpc.ConfigServiceClient;


/***
 * 
* 
* 描    述：商户证书密钥管理
*
* 创 建 者： wangl
* 创建时间：  18 Feb 201710:38:10
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
@RequestMapping(value = "${adminPath}/tpds/tpdsMerchantCer")
public class TpdsMerchantCerController extends BaseController {
	
    private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private TpdsMerchantCerService tpdsMerchantCerService;
	
	
	@Autowired
	private TpdsMerchantService tpdsMerchantService;
	
	@Autowired
	private ConfigServiceClient configServiceClient;
	
	
	@SuppressWarnings("static-access")
	@RequiresPermissions("tpds:tpdsMerchantCer:view")
	@RequestMapping(value = { "list", "" })
	public String list(TpdsMerchantCer tpdsMerchantCer, HttpServletRequest request,HttpServletResponse response, Model model) {
		
		Page<TpdsMerchantCer> page = tpdsMerchantCerService.findPage(new Page<TpdsMerchantCer>(request, response), tpdsMerchantCer);
		page.setList(tpdsMerchantCerService.getKeyValue(page.getList()));
		
		logger.info("商户证书密钥管理------>{查询记录}"+ page.getList());
		model.addAttribute("page", page);
		model.addAttribute("tpdsMerchantCer", tpdsMerchantCer);
	
		logger.info("商户证书密钥管理------>{}"+request);
		return "modules/tpds/tpdsMerchantCer";
	}
	
	
	
	/**
	 * 
	 * @方法说明：商户证书密钥管理页面跳转
	 * @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("tpds:tpdsMerchantCer:edit")
	@RequestMapping(value = "/addAndUpdate")
	public String addUpdate(@RequestParam(value="tpdsMerchantCerId",required = false) Integer tpdsMerchantCerId, 
					        TpdsMerchantCer tpdsMerchantCer, 
							Model model) {
		
		if(null != tpdsMerchantCerId){
			logger.info("商户证书密钥管理页面跳转------>{修改操作}"+tpdsMerchantCerId);
			tpdsMerchantCer=tpdsMerchantCerService.getEntityById(tpdsMerchantCerId);
			//证书文件存储路径
			if(StringUtils.isNoneBlank(tpdsMerchantCer.getCerPath())){
				tpdsMerchantCer.setCerPath(RandomUtil.getFastDfs(tpdsMerchantCer.getCerPath()));
			}
		}else{
			logger.info("商户证书密钥管理页面跳转------>{添加操作}");
			tpdsMerchantCer=new TpdsMerchantCer();
		}
		model.addAttribute("tpdsMerchantCer", tpdsMerchantCer);
		return "modules/tpds/tpdsMerchantCerAddAndUpdate";
	}
	
	
	/**
	 * 
	 * @方法说明：验证是否存在商户id
	 * @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/changeValue/{merchantNo}")
	public String chanageValue(@PathVariable("merchantNo") String merchantNo) {
		
		int num=tpdsMerchantCerService.changeValue(merchantNo);
		return num+"";
	}
	
	
	
	/**
	 * 
	 * @方法说明：验证是否存在商户id
	 * @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("tpds:tpdsMerchantCer:edit")
	@RequestMapping(value = "/delete/{tpdsMerchantCerId}")
	public String delete(@PathVariable("tpdsMerchantCerId") Integer tpdsMerchantCerId,RedirectAttributes redirectAttributes) {
		try {
			TpdsMerchantCer tpdsMerchantCer =tpdsMerchantCerService.getEntityById(tpdsMerchantCerId);
			String msg = configServiceClient.deleteMerchantCer(tpdsMerchantCer.getMerchantNo());
			//int num=tpdsMerchantCerService.delete(tpdsMerchantCerId);
			if("1".equals(msg)){
				addMessage(redirectAttributes, "删除成功");
			}else{
				addMessage(redirectAttributes, "删除失败");
			}
		} catch (Exception e) {
			logger.error("商户证书删除失败{}",tpdsMerchantCerId);
		}
		return "redirect:"+Global.getAdminPath()+"/tpds/tpdsMerchantCer";
	}
	
	
	
	
	/**
	 * 
	 * @throws ParseException 
	 * @方法说明：商户证书密钥管理页面
	 * @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("tpds:tpdsMerchantCer:edit")
	@RequestMapping(value = "/saveEntity")
	public String save(@RequestParam(value="tpdsMerchantCerId",required = false) Integer tpdsMerchantCerId, 
					   TpdsMerchantCer tpdsMerchantCer,
					   RedirectAttributes redirectAttributes,
					   @RequestParam(value="permitsAccountsFile",required = false) MultipartFile permitsAccountsFile,
					   Model model) throws ParseException {
		JsonMapperUtil jsonUtil = new JsonMapperUtil();
		String message="";
		if(null != tpdsMerchantCerId){
			logger.info("商户证书密钥管理------>{修改操作}"+tpdsMerchantCerId);
			
			tpdsMerchantCer.setUpdateTime(new Date());
			tpdsMerchantCer.setUpdateUser(UserUtils.getUser().getName());
			//int num=tpdsMerchantCerService.updateEntity(tpdsMerchantCer);
			String msg = configServiceClient.editMerchantCer(jsonUtil.toJson(tpdsMerchantCer));
			if("1".equals(msg)){
				message="修改操作成功";
			}else{
				message="修改操作失败";
			}
		}else{
			logger.info("商户证书密钥管理------>{添加操作}");
			//将文件
			try {
				tpdsMerchantCer.setCerPath(tpdsMerchantService.upLoadPic(permitsAccountsFile));
				tpdsMerchantCer.setCreateTime(new Date());
				//int	num=tpdsMerchantCerService.saveEntity(tpdsMerchantCer);
				String msg = configServiceClient.addMerchantCer(jsonUtil.toJson(tpdsMerchantCer));
				if("1".equals(msg)){
					message="保存操作成功";
				}else{
					message="保存操作失败";
				}
			} catch (Exception e) {
				message="保存操作异常"+e.getMessage();
				logger.info("商户证书密钥管理------>{文件上传失败}"+e.getMessage());
			}
		}
		
		addMessage(redirectAttributes, message);
		return "redirect:"+Global.getAdminPath()+"/tpds/tpdsMerchantCer";
		
	}
}
