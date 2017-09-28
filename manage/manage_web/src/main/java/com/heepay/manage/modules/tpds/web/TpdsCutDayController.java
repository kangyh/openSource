package com.heepay.manage.modules.tpds.web;

import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.tpds.CutDay;
import com.heepay.enums.tpds.Status;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.manage.modules.tpds.entity.TpdsCutDay;
import com.heepay.manage.modules.tpds.service.TpdsCutDayService;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年2月17日下午5:31:43
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
@RequestMapping(value = "${adminPath}/tpds/tpdsCutDay")
public class TpdsCutDayController extends BaseController{
	
    private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private TpdsCutDayService tpdsCutDayService;
	
	/**
	 * 
	 * @方法说明：页面显示
	 * @时间：18 Feb 201718:16:26
	 * @创建人：wangl
	 */
	@RequiresPermissions("tpds:tpdsCutDay:view")
	@RequestMapping(value = { "list", "" })
	public String list(TpdsCutDay tpdsCutDay, HttpServletRequest request,HttpServletResponse response, Model model) {
		
		Page<TpdsCutDay> page = tpdsCutDayService.findPage(new Page<TpdsCutDay>(request, response), tpdsCutDay);
		logger.info("操作日志表------>{查询记录}"+ page.getList());
		for (TpdsCutDay tpdsCuts : page.getList()) {
			//通道管理生效标识类型effect_flg
			if(StringUtils.isNotBlank(tpdsCuts.getCutType())){
				tpdsCuts.setCutType(CutDay.labelOf(tpdsCuts.getCutType()));
			}
			
			//状态  ENABLE(启用) DISABL(禁用)
			if(StringUtils.isNotBlank(tpdsCuts.getStatus())){
				tpdsCuts.setStatus(Status.labelOf(tpdsCuts.getStatus()));
			}
		}
		//生效标识
		List<EnumBean> cutDay = Lists.newArrayList();
		for (CutDay checkFlg : CutDay.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			cutDay.add(ct);
		}
		model.addAttribute("cutDay", cutDay);
				
		model.addAttribute("page", page);
		model.addAttribute("tpdsCutDay", tpdsCutDay);
	
		logger.info("操作日志表------>{}"+request);
		return "modules/tpds/tpdsCutDayList";
	}
	
	/**
	 * 
	 * @throws ParseException 
	 * @方法说明：修改跳转
	 * @时间：18 Feb 201718:17:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("tpds:tpdsCutDay:edit")
	@RequestMapping(value = "/updateEntity")
	public String updateEntity(@RequestParam(value = "tpdsCutDayId", required = false) Integer tpdsCutDayId, 
			@RequestParam(value = "read", required = false) String read,
			Model model) throws ParseException {

		TpdsCutDay tpdsCutDay = tpdsCutDayService.getEntityById(tpdsCutDayId);
		
		Date date = new SimpleDateFormat("HH:mm:ss").parse(tpdsCutDay.getCutTime());
		tpdsCutDay.setTime(date);
		//生效标识
		List<EnumBean> cutDay = Lists.newArrayList();
		for (CutDay checkFlg : CutDay.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			cutDay.add(ct);
		}
		
		//状态  ENABLE(启用) DISABL(禁用)
		List<EnumBean> status = Lists.newArrayList();
		for (Status checkFlg : Status.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			status.add(ct);
		}
		model.addAttribute("status", status);
				
		model.addAttribute("cutDay", cutDay);
	    model.addAttribute("tpdsCutDay", tpdsCutDay);
	    
	    return "modules/tpds/tpdsCutDayUpdate";

	}
	
	/**
	 * 
	 * @方法说明：添加跳转
	 * @时间：18 Feb 201717:51:35
	 * @创建人：wangl
	 */
	@RequiresPermissions("tpds:tpdsCutDay:edit")
	@RequestMapping(value = "/addEntity")
	public String addEntity(@RequestParam(value = "tpdsCutDayId", required = false) Integer tpdsCutDayId, 
							@RequestParam(value = "read", required = false) String read,
							Model model) {
		
		
		//生效标识
		List<EnumBean> cutDay = Lists.newArrayList();
		for (CutDay checkFlg : CutDay.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			cutDay.add(ct);
		}
		model.addAttribute("cutDay", cutDay);
				
		//状态  ENABLE(启用) DISABL(禁用)
		List<EnumBean> status = Lists.newArrayList();
		for (Status checkFlg : Status.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			status.add(ct);
		}
		model.addAttribute("status", status);
		
	    model.addAttribute("tpdsCutDay", new TpdsCutDay());
	    return "modules/tpds/tpdsCutDayAdd";
	}
	
	/**
	 * 
	 * @方法说明：保存操作
	 * @时间：18 Feb 201717:52:03
	 * @创建人：wangl
	 */
	@RequiresPermissions("tpds:tpdsCutDay:edit")
	@RequestMapping(value = "/save")
	public String save(TpdsCutDay tpdsCutDay, Model model, RedirectAttributes redirectAttributes,
					   @RequestParam(value="tpdsCutDayId",required = false) Integer tpdsCutDayId,
					   @RequestParam(value="cutTime",required = false) Date cutTime) throws Exception {
		
		//Date parse = new SimpleDateFormat("HH:mm:ss").parse(tpdsCutDay.getTime());
		//tpdsCutDay.setCutTime(parse);
		if(tpdsCutDayId==null){
			int num = tpdsCutDayService.saveEntity(tpdsCutDay);
			
            if(num>0){
            	addMessage(redirectAttributes, "添加成功");
            }else{
            	addMessage(redirectAttributes, "添加失败");
            }
        	
		}else{
			tpdsCutDay.setUpdateTime(new Date());
			tpdsCutDay.setUpdateUser(UserUtils.getUser().getName());
			int num = tpdsCutDayService.updateEntity(tpdsCutDay);
			if(num>0){
	        	addMessage(redirectAttributes, "更新成功");
	        }else{
	        	addMessage(redirectAttributes, "更新失败");
	        }
		} 
		return "redirect:"+Global.getAdminPath()+"/tpds/tpdsCutDay";
	}
			
}
