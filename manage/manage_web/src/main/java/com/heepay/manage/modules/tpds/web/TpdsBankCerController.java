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
import com.heepay.manage.modules.tpds.entity.TpdsBankCer;
import com.heepay.manage.modules.tpds.service.TpdsBankCerService;
import com.heepay.manage.modules.tpds.service.TpdsMerchantService;
import com.heepay.manage.modules.tpds.web.rpc.ConfigServiceClient;


/***
 * 
* 
* 描    述：银行证书密钥管理
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
@RequestMapping(value = "${adminPath}/tpds/tpdsBankCer")
public class TpdsBankCerController extends BaseController {
	
    private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private TpdsBankCerService tpdsBankCerService;
	
	
	@Autowired
	private TpdsMerchantService tpdsMerchantService;
	
	
	@Autowired
	private ConfigServiceClient configServiceClient;
	
	@SuppressWarnings("static-access")
	@RequiresPermissions("tpds:tpdsBankCer:view")
	@RequestMapping(value = { "list", "" })
	public String list(TpdsBankCer tpdsBankCer, HttpServletRequest request,HttpServletResponse response, Model model) {
		
		Page<TpdsBankCer> page = tpdsBankCerService.findPage(new Page<TpdsBankCer>(request, response), tpdsBankCer);
		page.setList(tpdsBankCerService.getKeyValue(page.getList()));
		
		logger.info("银行证书密钥管理------>{查询记录}"+ page.getList());
		model.addAttribute("page", page);
		model.addAttribute("tpdsBankCer", tpdsBankCer);
	
		logger.info("银行证书密钥管理------>{}"+request);
		return "modules/tpds/tpdsBankCer";
	}
	
	
	
	/**
	 * 
	 * @方法说明：银行证书密钥管理页面跳转
	 * @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("tpds:tpdsBankCer:edit")
	@RequestMapping(value = "/addAndUpdate")
	public String addUpdate(@RequestParam(value="tpdsBankId",required = false) Integer tpdsBankId, 
							TpdsBankCer tpdsBankCer, 
							Model model) {
		
		if(null != tpdsBankId){
			logger.info("银行证书密钥管理页面跳转------>{修改操作}"+tpdsBankId);
			tpdsBankCer=tpdsBankCerService.getEntityById(tpdsBankId);
			//证书文件存储路径
			if(StringUtils.isNoneBlank(tpdsBankCer.getCerPath())){
				tpdsBankCer.setCerPath(RandomUtil.getFastDfs(tpdsBankCer.getCerPath()));
			}
			/*//公钥
            if (StringUtils.isNotBlank(tpdsBankCer.getPubKey())) {
            	tpdsBankCer.setPubKey(TpdsBankCerService.getKey(tpdsBankCer.getPubKey()));
            }
            //私钥
            if (StringUtils.isNotBlank(tpdsBankCer.getPriKey())) {
            	tpdsBankCer.setPriKey(TpdsBankCerService.getKey(tpdsBankCer.getPriKey()));
            }*/
            
		}else{
			logger.info("银行证书密钥管理页面跳转------>{添加操作}");
			tpdsBankCer=new TpdsBankCer();
		}
		model.addAttribute("tpdsBankCer", tpdsBankCer);
		return "modules/tpds/tpdsBankCerAddAndUpdate";
	}
	
	
	/**
	 * 
	 * @方法说明：验证是否存在商户id
	 * @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/changeValue/{bankNo}")
	public String chanageValue(@PathVariable("bankNo") String bankNo) {
		
		int num=tpdsBankCerService.changeValue(bankNo);
		return num+"";
	}
	
	
	
	/**
	 * 
	 * @方法说明：验证是否存在商户id
	 * @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("tpds:tpdsBankCer:edit")
	@RequestMapping(value = "/delete/{tpdsBankId}")
	public String delete(@PathVariable("tpdsBankId") Integer tpdsBankId,RedirectAttributes redirectAttributes) {
		try {
			TpdsBankCer tpdsBankCer = tpdsBankCerService.getEntityById(tpdsBankId);
			String msg = configServiceClient.deleteBankCer(tpdsBankCer.getBankNo());
			//int num=tpdsBankCerService.delete(tpdsBankId);
			if("1".equals(msg)){
				addMessage(redirectAttributes, "删除成功");
			}else{
				addMessage(redirectAttributes, "删除失败");
			}
		} catch (Exception e) {
			logger.error("银行证书删除失败{}",tpdsBankId);
		}
		return "redirect:"+Global.getAdminPath()+"/tpds/tpdsBankCer";
	}
	
	
	
	
	/**
	 * 
	 * @throws ParseException 
	 * @方法说明：银行证书密钥管理页面
	 * @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("tpds:tpdsBankCer:edit")
	@RequestMapping(value = "/saveEntity")
	public String save(@RequestParam(value="tpdsBankId",required = false) Integer tpdsBankId, 
					   TpdsBankCer tpdsBankCer,
					   RedirectAttributes redirectAttributes,
					   @RequestParam(value="permitsAccountsFile",required = false) MultipartFile permitsAccountsFile,
					   Model model) throws ParseException {
		JsonMapperUtil jsonUtil = new JsonMapperUtil();
		String message="";
		if(null != tpdsBankId){
			logger.info("银行证书密钥管理------>{修改操作}"+tpdsBankId);
			
			tpdsBankCer.setUpdateTime(new Date());
			tpdsBankCer.setUpdateUser(UserUtils.getUser().getName());
			//int num=tpdsBankCerService.updateEntity(tpdsBankCer);
			String msg = configServiceClient.editBankcert(jsonUtil.toJson(tpdsBankCer));
			if("1".equals(msg)){
				message="修改操作成功";
			}else{
				message="修改操作失败";
			}
		}else{
			logger.info("银行证书密钥管理------>{添加操作}");
			//将文件
			try {
				tpdsBankCer.setCerPath(tpdsMerchantService.upLoadPic(permitsAccountsFile));
				tpdsBankCer.setCreateTime(new Date());
				//int	num=tpdsBankCerService.saveEntity(tpdsBankCer);
				String msg = configServiceClient.addBankcer(jsonUtil.toJson(tpdsBankCer));
				if("1".equals(msg)){
					message="保存操作成功";
				}else{
					message="保存操作失败";
				}
			} catch (Exception e) {
				message="保存操作异常"+e.getMessage();
				logger.info("银行证书密钥管理------>{文件上传失败}"+e.getMessage());
			}
		}
		
		addMessage(redirectAttributes, message);
		return "redirect:"+Global.getAdminPath()+"/tpds/tpdsBankCer";
		
	}
}
