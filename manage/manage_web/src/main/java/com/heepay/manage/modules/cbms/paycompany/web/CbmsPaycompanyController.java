/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.paycompany.web;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.modules.cbms.entity.CbmsPaycompany;
import com.heepay.manage.modules.cbms.service.CbmsPaycompanyService;
import java.util.Date;
import java.util.List;


/**
 *
 * 描    述：支付机构设置Controller
 *
 * 创 建 者： @author 牛俊鹏
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
@Controller
@RequestMapping(value = "${adminPath}/cbms/cbmsPaycompany")
public class CbmsPaycompanyController extends BaseController {

	@Autowired
	private CbmsPaycompanyService cbmsPaycompanyService;
	/**
	 * 根据id查询数据库对象（页面发送请求第一时间执行此方法）
	 * @param
	 * @return
	 */
	@ModelAttribute
	public CbmsPaycompany get(@RequestParam(required=false) String id) {
		logger.info("页面传入的id为"+id);
		CbmsPaycompany entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cbmsPaycompanyService.get(id);
		}
		if (entity == null){
			entity = new CbmsPaycompany();
		}
		return entity;
	}
	/**
	 * 支付公司信息设置  只有修改页面
	 * @param
	 * @return
	 */
	@RequiresPermissions("cbms:cbmsPaycompany:view")
	@RequestMapping(value = {"form",""})
	public String form(CbmsPaycompany cbmsPaycompany, Model model) {
		logger.debug("form进入cbmsPaycompany.getCompanyId()===:"+cbmsPaycompany.getCompanyId());
		//查询所有的数据
		List<CbmsPaycompany>  companyList = cbmsPaycompanyService.findAllList();
		if(companyList.size() > 0){
			cbmsPaycompany = companyList.get(0);//因为只有一条数据所以取下标为0的对象
			logger.debug("获取companyList中第一个ID:"+cbmsPaycompany.getCompanyId());
		}
		model.addAttribute("cbmsPaycompany", cbmsPaycompany);
		return "modules/cbms/paycompany/cbmsPaycompanyForm";
	}
	/**
	 * 新增修改操作
	 * @param
	 * @return
	 */
	@RequiresPermissions("cbms:cbmsPaycompany:edit")
	@RequestMapping(value = "save")
	public String save(CbmsPaycompany cbmsPaycompany, Model model, RedirectAttributes redirectAttributes) {
		logger.info("添加修改操作对象为"+cbmsPaycompany.toString());
		//验证id为空或者是null true则是添加操作
		if(StringUtils.isBlank(cbmsPaycompany.getCompanyId()) || cbmsPaycompany.getCompanyId().equals("")){
			cbmsPaycompany.setCompanyId(null);
			cbmsPaycompany.setInputuserId(UserUtils.getUser().getId());
			cbmsPaycompany.setCreatedTime(new Date());
			cbmsPaycompanyService.save(cbmsPaycompany);
			addMessage(redirectAttributes, "保存支付机构设置成功");
		}else{//反之是修改操作
			cbmsPaycompany.setUpdateUserId(UserUtils.getUser().getId());
			cbmsPaycompany.setUpdateTime(new Date());
			cbmsPaycompanyService.update(cbmsPaycompany);
			addMessage(redirectAttributes, "更新支付机构设置成功");
		}
		logger.debug("save进入cbmsPaycompany.getCompanyId()===:"+cbmsPaycompany.getCompanyId());
		//return "redirect:"+Global.getAdminPath()+"/cbms/cbmsPaycompany/?repage"+"&id="+cbmsPaycompany.getCompanyId();
		return form(cbmsPaycompany, model);//因为这个功能是只能不断反复修改所以commint后返回修改修改页面
	}
}