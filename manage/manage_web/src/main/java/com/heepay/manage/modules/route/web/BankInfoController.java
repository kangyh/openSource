/**
 *  
 */
package com.heepay.manage.modules.route.web;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.CommonStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.service.BankInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * 名称：银行信息操作类
 * 创建者：刘萌
 * 创建日期：2016-8-10
 * 创建描述：银行信息的查看、添加、修改、查询、更新缓存功能
 *
 * 审核者：于亮
 * 审核时间：2016年9月1日
 * 审核描述：代码缩进部分不标准、权限注解不全；save方法重复返回可以最后写一次；多方法返回相同可以抽象；加方法注释；
 * 
 * 修改者： 刘萌 
 * 修改时间： 2016-9-7
 * 修改描述：  代码缩进，save方法返回一次，方法加注释
 */
@Controller
@RequestMapping(value = "${adminPath}/route/bankInfo")
public class BankInfoController extends BaseController {

  	@Autowired
  	private BankInfoService bankInfoService;
  	
  	  
  	/**     
  	* @discription 根据id获取银行信息
  	* @author 刘萌      
  	* @created 2016年9月7日 下午5:20:50     
  	* @param id
  	* @return     
  	*/
  	@ModelAttribute
  	public BankInfo get(@RequestParam(required=false) String id) {
		BankInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bankInfoService.get(id);
		}
		if (entity == null){
			entity = new BankInfo();
		}
		return entity;
  	}
  	
  	  
  	/**     
  	* @discription 获取银行信息列表
  	* @author 刘萌      
  	* @created 2016年9月7日 下午5:21:38     
  	* @param bankInfo
  	* @param request
  	* @param response
  	* @param model
  	* @return     
  	*/
  	@RequiresPermissions("route:bankInfo:view")
  	@RequestMapping(value = {"list",""})
  	public String list(BankInfo bankInfo,String bankNameCode,String pageNo,HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isNotBlank(bankNameCode)){
			try {
				bankInfo.setBankName(URLDecoder.decode(bankNameCode,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		Page<BankInfo>  page  = new Page<BankInfo>(request, response);
		//根据银行代码排序
		page.setOrderBy("bankNo");
		if(StringUtils.isNotBlank(pageNo)){
			page.setPageNo(Integer.valueOf(pageNo));
		}
		bankInfoService.findPage(page,bankInfo);
		if(null!=page.getList() && !page.getList().isEmpty()) {
			page.setList(EnumView.BankInfo(page.getList()));
		}
		model.addAttribute("bankInfoFind", bankInfo);
		model.addAttribute("page", page);
		return "modules/route/bankInfoList";
  	}
      
  	  
  	/**     
  	* @discription 获取银行信息新增修改页面
  	* @author L.M
  	* @created 2016年9月7日 下午5:22:47     
  	* @param bankInfo
  	* @param model
  	* @return     
  	*/
  	@RequiresPermissions("route:bankInfo:view")
  	@RequestMapping(value = "form")
  	public String form(BankInfo bankInfo,String bankNoFind,String bankNameFind,String selectStatusFind,String pageNo, Model model) {
		model.addAttribute("bankInfo", bankInfo);
		model.addAttribute("bankNoFind", bankNoFind);
		model.addAttribute("bankNameFind", bankNameFind);
		model.addAttribute("selectStatusFind", selectStatusFind);
		model.addAttribute("pageNo", pageNo);
		return "modules/route/bankInfoForm";
  	}

  	/**     
  	* @discription 保存修改银行信息
  	* @author L.M
  	* @created 2016年9月7日 下午5:27:59     
  	* @param bankInfo
  	* @param model
  	* @param redirectAttributes
  	* @return     
  	*/
  	@RequiresPermissions("route:bankInfo:edit")	
  	@RequestMapping(value = "save")
  	public String save(BankInfo bankInfo,String bankNoFind,String bankNameFind,String selectStatusFind,String pageNo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, bankInfo)){
//			return form(bankInfo,bankNoFind,bankNameFind,selectStatusFind,pageNo, model);
			return form(bankInfo,null,null,null,null,model);
		}
		List<BankInfo> bankInfoNew = bankInfoService.selectByBankN(bankInfo);
		if(StringUtils.isBlank(bankInfo.getId())){
			if(null!=bankInfoNew && bankInfoNew.isEmpty()){
				bankInfo.setStatus(CommonStatus.ENABLE.getValue());
				bankInfoService.save(bankInfo);
				addMessage(redirectAttributes, "保存成功");
				return "redirect:"+Global.getAdminPath()+"/route/bankInfo/?repage";
			}else{
				model.addAttribute("msg", "填写了重复信息，请核查");
//				return form(bankInfo,bankNoFind,bankNameFind,selectStatusFind,pageNo, model);
				return form(bankInfo,null,null,null,null,model);
			}
		}else{
			if((Constants.LENGTH).equals(bankInfoNew.size())){
				addMessage(redirectAttributes, "保存成功");
				bankInfoService.save(bankInfo);
				String bankNameCode = null;
				try {
					bankNameCode = URLEncoder.encode(bankNameFind, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
//				return "redirect:"+Global.getAdminPath()+"/route/bankInfo/?repage";
				return "redirect:"+Global.getAdminPath()+"/route/bankInfo/?bankNo="+bankNoFind+"&bankNameCode="+bankNameCode+"&status="+selectStatusFind+"&pageNo="+pageNo;
			}else {
				model.addAttribute("msg", "该银行简称已存在");
				return form(bankInfo,bankNoFind,bankNameFind,selectStatusFind,pageNo, model);
			}
		}
  	}
  	
    
    /**     
    * @discription 获取银行信息详情页面
    * @author L.M
    * @created 2016年9月7日 下午5:26:56     
    * @param bankInfo
    * @param model
    * @return     
    */
    @RequiresPermissions("route:bankInfo:view")
    @RequestMapping(value = "details")
    public String details(BankInfo bankInfo, Model model) {
        //银行状态由值取内容
		bankInfo=EnumView.bankInfoShow(bankInfo);
        model.addAttribute("bankInfo", bankInfo);
        return "modules/route/bankInfoDetails";
    }
  	
  	
  	/**     
  	* @discription 银行信息缓存更新
  	* @author L.M
  	* @created 2016年9月7日 下午5:32:59     
  	* @param redirectAttributes
  	* @return     
  	*/
  	@RequestMapping(value = "updateCache")
  	public String updateCache(RedirectAttributes redirectAttributes){
		bankInfoService.updateCache();
		addMessage(redirectAttributes, "银行信息缓存更新成功");
		return "redirect:"+Global.getAdminPath()+"/route/bankInfo/?repage";
  	}
    
  	  
  	/**     
  	* @discription 银行启用、禁用状态转换
  	* @author L.M
	* @created 2016年9月7日 下午5:33:18
  	* @param bankInfo
  	* @param redirectAttributes
  	* @return     
  	*/
  	
  	@RequiresPermissions("route:bankInfo:edit")
    @RequestMapping(value = "status")
    public String status(BankInfo bankInfo,String pageNo, RedirectAttributes redirectAttributes, Model model) {
		bankInfoService.status(bankInfo);
		addMessage(redirectAttributes, "成功");
		String bankNameCode = null;
		try {
			bankNameCode = URLEncoder.encode(bankInfo.getBankName(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:"+Global.getAdminPath()+"/route/bankInfo/?bankNo="+bankInfo.getBankNo()+"&bankNameCode="+bankNameCode+"&status="+bankInfo.getSelectStatus()+"&pageNo="+pageNo;
    }
}