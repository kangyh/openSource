/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.subject.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.google.common.collect.Maps;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.BalanceDirection;
import com.heepay.enums.SubjectIsLast;
import com.heepay.enums.SubjectLevel;
import com.heepay.enums.SubjectStatus;
import com.heepay.enums.SubjectType;
import com.heepay.manage.modules.subject.entity.Subject;
import com.heepay.manage.modules.subject.service.SubjectService;
import com.heepay.manage.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.heepay.manage.modules.sys.utils.UserUtils;


/**
 *
 * 描    述：科目Controller
 *
 * 创 建 者： @author 王亚洪
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
@RequestMapping(value = "${adminPath}/subject/subject")
public class SubjectController extends BaseController {

	@Autowired
	private SubjectService subjectService;
	
	@ModelAttribute
	public Subject get(@RequestParam(required=false) String id) {
		Subject entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = subjectService.get(id);
		}
		if (entity == null){
			entity = new Subject();
		}
		return entity;
	}
	
	@RequiresPermissions("subject:subject:view")
	@RequestMapping(value = {"list", ""})
	public String list(Subject subject, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Subject> page = subjectService.findPage(new Page<Subject>(request, response), subject); 
		List<Subject> list = page.getList();
		if(list != null && !list.isEmpty()){
		  for(Subject subjectVo : list){
		    subjectVo.setStatusName(SubjectStatus.labelOf(subjectVo.getStatus()));
		    subjectVo.setIsLastName(SubjectIsLast.labelOf(subjectVo.getIsLast()));
		    subjectVo.setLevelName(SubjectLevel.labelOf(subjectVo.getSubjectLevel()));
		    subjectVo.setDirectionName(BalanceDirection.labelOf(subjectVo.getBalanceDirection()));
		    subjectVo.setTypeName(SubjectType.labelOf(subjectVo.getType()));
		    if(subjectVo.getParentSubjectId() == 0){
		      subjectVo.setParentSubjectName("");
		    }else{
		      Subject parentSubject = subjectService.get(String.valueOf(subjectVo.getParentSubjectId()));
		      subjectVo.setParentSubjectName(parentSubject.getSubjectName());
		    }
		  }
		}
		model.addAttribute("page", page);
		return "modules/subject/subjectList";
	}

	@RequiresPermissions("subject:subject:view")
	@RequestMapping(value = "form")
	public String form(Subject subject, Model model) {
		model.addAttribute("subject", subject);
		return "modules/subject/subjectForm";
	}

	@RequiresPermissions("subject:subject:edit")
	@RequestMapping(value = "save")
	public String save(Subject subject, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, subject)){
			return form(subject, model);
		}
		Principal principal = UserUtils.getPrincipal();
		subject.setCreator(principal.getLoginName());
		String subjectLevel = subject.getSubjectLevel();
		if(subjectLevel.equals(SubjectLevel.FIRST_SUBJECT.getValue())){
		  subject.setParentSubjectId(0L);
		}
		subject.setStatus(SubjectStatus.NORMAL.getValue());
		subject.setCreateTime(new Date());
		subject.setUpdateTime(subject.getCreateTime());
		subjectService.save(subject);
		addMessage(redirectAttributes, "保存科目成功");
		return "redirect:"+Global.getAdminPath()+"/subject/subject/?repage";
	}
	
	@RequiresPermissions("subject:subject:edit")
	@RequestMapping(value = "delete")
	public String delete(String subjectId, RedirectAttributes redirectAttributes) {
	  Subject subject = subjectService.get(subjectId);
	  //看科目级别，如果是三级科目，只删除当前记录，如果是二级科目，则还要删除对应的三级科目，如果是一级科目则要删除对应的二级和三级科目
	  String subjectLevel = subject.getSubjectLevel();
	  if(subjectLevel.equals(SubjectLevel.FIRST_SUBJECT.getValue())){
	    Map<String, Object> paramsMap = new HashMap<String, Object>();
	    paramsMap.put("parentSubjectId", subject.getSubjectId());
	    List<Subject> list = subjectService.getChildSubjects(paramsMap);
	    if(list != null && !list.isEmpty()){
	      for(Subject twoLevelSubject : list){
	        if(twoLevelSubject.getIsLast().equals(SubjectIsLast.YES.getValue())){
	          subjectService.delete(twoLevelSubject);
	        }else{
	          paramsMap.clear();
	          paramsMap.put("parentSubjectId", twoLevelSubject.getSubjectId());
	          List<Subject> childList = subjectService.getChildSubjects(paramsMap);
	          if(childList != null && !childList.isEmpty()){
	            for(Subject threeLevelSubject : childList){
	              subjectService.deleteChildSubjects(threeLevelSubject.getSubjectId());
	            }
	          }
	          subjectService.deleteChildSubjects(twoLevelSubject.getSubjectId());
	        }
	      }
	    }
	    subjectService.deleteChildSubjects(subject.getSubjectId());
	  }else if(subjectLevel.equals(SubjectLevel.SECOND_SUBJECT.getValue())){
	    if(subject.getIsLast().equals(SubjectIsLast.NO.getValue())){
	      subjectService.deleteChildSubjects(subject.getSubjectId());
	    }
	  }
    subjectService.delete(subject);
		addMessage(redirectAttributes, "删除科目成功");
		return "redirect:"+Global.getAdminPath()+"/subject/subject/?repage";
	}

	
	@RequiresPermissions("subject:subject:edit")
  @RequestMapping(value = "updateStatus")
	public String updateStatus(String subjectId, RedirectAttributes redirectAttributes){
	  Subject subject = subjectService.get(subjectId);
	  String status = null;
	  Date datetime = new Date();
	  Principal principal = UserUtils.getPrincipal();
	  if(subject.getStatus().equals(SubjectStatus.CLOSED.getValue())){
	    status = SubjectStatus.NORMAL.getValue();
	  }else{
	    status = SubjectStatus.CLOSED.getValue();
	  }
	  subject.setStatus(status);
    subject.setModifier(principal.getLoginName());
    subject.setUpdateTime(datetime);
	  String subjectLevel = subject.getSubjectLevel();
	  if(subjectLevel.equals(SubjectLevel.FIRST_SUBJECT.getValue())){
      Map<String, Object> paramsMap = new HashMap<String, Object>();
      paramsMap.put("parentSubjectId", subject.getSubjectId());
      List<Subject> list = subjectService.getChildSubjects(paramsMap);
      if(list != null && !list.isEmpty()){
        for(Subject twoLevelSubject : list){
          if(twoLevelSubject.getIsLast().equals(SubjectIsLast.NO.getValue())){
            paramsMap.clear();
            paramsMap.put("parentSubjectId", twoLevelSubject.getSubjectId());
            List<Subject> childList = subjectService.getChildSubjects(paramsMap);
            if(childList != null && !childList.isEmpty()){
              for(Subject threeLevelSubject : childList){
                threeLevelSubject.setStatus(status);
                threeLevelSubject.setModifier(principal.getLoginName());
                threeLevelSubject.setUpdateTime(datetime);
                subjectService.updateSubjects(threeLevelSubject);
              }
            }
            twoLevelSubject.setStatus(status);
            twoLevelSubject.setModifier(principal.getLoginName());
            twoLevelSubject.setUpdateTime(datetime);
            subjectService.updateSubjects(twoLevelSubject);
          }
        }
      }
    }else if(subjectLevel.equals(SubjectLevel.SECOND_SUBJECT.getValue())){
      if(subject.getIsLast().equals(SubjectIsLast.NO.getValue())){
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("parentSubjectId", subject.getSubjectId());
        List<Subject> list = subjectService.getChildSubjects(paramsMap);
        if(list != null && !list.isEmpty()){
          for(Subject twoLevelSubject : list){
            twoLevelSubject.setStatus(status);
            twoLevelSubject.setModifier(principal.getLoginName());
            twoLevelSubject.setUpdateTime(datetime);
            subjectService.updateSubjects(twoLevelSubject);
          }
        }
      }
    }
    subjectService.updateSubjects(subject);
    addMessage(redirectAttributes, "更新状态成功");
	  return "redirect:"+Global.getAdminPath()+"/subject/subject/?repage";
	}
	
	
	@RequiresPermissions("subject:subject:view")
	@RequestMapping(value = "getParentSubjects", method=RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> getParentSubjects(String subjectLevel){
	  Map<String, Object> paramsMap = Maps.newHashMap();
	  paramsMap.put("subjectLevel", Integer.parseInt(subjectLevel)-1);
	  return subjectService.getParentSubjects(paramsMap);
	}
	
	
	@RequiresPermissions("subject:subject:view")
  @RequestMapping(value = "toUpdatePage", method=RequestMethod.GET)
  public String toUpdatePage(String subjectId, Model model){
	  Subject subject = subjectService.get(subjectId);
	  if(!subject.getSubjectLevel().equals(SubjectLevel.FIRST_SUBJECT)){
	    Map<String, Object> paramsMap = Maps.newHashMap();
	    paramsMap.put("subjectLevel", Integer.parseInt(subject.getSubjectLevel())-1);
	    List<Map<String, Object>> list = subjectService.getParentSubjects(paramsMap);
	    model.addAttribute("parentSubjectList", list);
	  }
	  model.addAttribute("parentId", subject.getParentSubjectId());
	  model.addAttribute("subject", subject);
    return "modules/subject/subjectUpdate";
  }
	
	@RequiresPermissions("subject:subject:edit")
  @RequestMapping(value = "updateSubject")
  public String updateSubject(Subject subject, String subjectId, String subjectCode, String subjectName, String subjectLevel, String parentSubjectId, 
      String type, String isLast, String balanceDirection, RedirectAttributes redirectAttributes, Model model) {
	  if (!beanValidator(model, subject)){
      return form(subject, model);
    }
	  Subject subjectVo = subjectService.get(subjectId);
	  subjectVo.setSubjectCode(subjectCode);
	  subjectVo.setSubjectName(subjectName);
	  subjectVo.setSubjectLevel(subjectLevel);
	  subjectVo.setType(type);
	  subjectVo.setIsLast(isLast);
	  if(subjectLevel.equals(SubjectLevel.FIRST_SUBJECT.getValue())){
	    subjectVo.setParentSubjectId(0L);
	  }else{
	    subjectVo.setParentSubjectId(Long.parseLong(parentSubjectId));
	  }
	  subjectVo.setBalanceDirection(balanceDirection);
	  Principal principal = UserUtils.getPrincipal();
	  subjectVo.setModifier(principal.getLoginName());
	  subjectVo.setUpdateTime(new Date());
    subjectService.updateSubjects(subjectVo);
    addMessage(redirectAttributes, "修改科目成功");
    return "redirect:" + Global.getAdminPath() + "/subject/subject/?repage";
  }
	
	
	@RequestMapping(value="isAdminUser")
  public @ResponseBody boolean isAdminUser(){
    return UserUtils.getUser().isAdmin();
  }
	
}