/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.HgmsContractStatus;
import com.heepay.manage.common.enums.ListEnums;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsBusiness;
import com.heepay.manage.modules.hgms.entity.HgmsValidContract;
import com.heepay.manage.modules.hgms.service.HgmsBusinessService;
import com.heepay.manage.modules.hgms.service.HgmsValidContractService;
import com.heepay.manage.modules.hgms.util.UploadUtil;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isEmpty;


/**
 * 描    述：有效合约风控审核Controller
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-06-03 17:08:56
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Controller
@RequestMapping(value = "${adminPath}/hgms/hgmsValidContractRcAudit")
public class HgmsValidContractRcAuditController extends BaseController {

    @Autowired
    private HgmsValidContractService hgmsValidContractService;
    @Autowired
    private HgmsBusinessService hgmsBusinessService;

    @ModelAttribute
    public HgmsValidContract get(@RequestParam(required = false) String id) {
        HgmsValidContract entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = hgmsValidContractService.get(id);
        }
        if (entity == null) {
            entity = new HgmsValidContract();
        }
        return entity;
    }

    /**
     * 风控审核列表页面
     *
     * @param hgmsValidContract
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsValidContractRcAudit:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsValidContract hgmsValidContract, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<HgmsValidContract> page = new Page<>(request, response);
        //已创建时间反向排序
        page.setOrderBy("a.entering_time desc");
        hgmsValidContract.setLegalAuditStatus(RouteStatus.AUDIT_SUCCESS.getValue());
        page = hgmsValidContractService.findPage(page, hgmsValidContract);
        for (HgmsValidContract hgmsValidContractnew : page.getList()) {
            //合同启用停用状态赋值
            if (!isEmpty(hgmsValidContractnew.getContractStatus()))
                hgmsValidContractnew.setContractStatus(HgmsContractStatus.labelOf(hgmsValidContractnew.getContractStatus()));
            //合同审核状态状态赋值
            if (!isEmpty(hgmsValidContractnew.getRcAuditStatus()))
                hgmsValidContractnew.setRcAuditStatus(RouteStatus.labelOf(hgmsValidContractnew.getRcAuditStatus()));
        }
        List<EnumBean> contractStatus = ListEnums.hgmsContractStatus();
        model.addAttribute("contractStatus", contractStatus);
        model.addAttribute("page", page);
        return "modules/hgms/hgmsValidContractRcAuditList";
    }

    /**
     * 风控审核页面
     *
     * @param hgmsValidContract
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsValidContractRcAudit:view")
    @RequestMapping(value = "form")
    public String form(HgmsValidContract hgmsValidContract, Model model) {
        //如果审核通过和审核不通过的商户重新定向到风控审核页面
        if (RouteStatus.AUDIT_SUCCESS.getValue().equals(hgmsValidContract.getRcAuditStatus()) || RouteStatus.AUDREJ.getValue().equals(hgmsValidContract.getRcAuditStatus())) {
            return "redirect:" + Global.getAdminPath() + "/hgms/hgmsValidContractRcAudit/?repage";
        }
        //合约文件添加域
        if (hgmsValidContract.getContractFileAddress() != null) {
            hgmsValidContract.setContractFileAddress(RandomUtil.getFastDfs(hgmsValidContract.getContractFileAddress()));
        }
        //合同启用停用状态赋值
        if (!isEmpty(hgmsValidContract.getContractStatus()))
            hgmsValidContract.setContractStatus(HgmsContractStatus.labelOf(hgmsValidContract.getContractStatus()));
        //风控审核状态
        if (!isEmpty(hgmsValidContract.getRcAuditStatus()))
            hgmsValidContract.setRcAuditStatus(RouteStatus.labelOf(hgmsValidContract.getRcAuditStatus()));
        //录入人赋值
        User userInput = UserUtils.get(hgmsValidContract.getEnteringId());
        if (!ObjectUtils.isEmpty(userInput)) {
            hgmsValidContract.setEnteringId(userInput.getName());
        }
        model.addAttribute("hgmsValidContract", hgmsValidContract);
        return "modules/hgms/hgmsValidContractRcAuditForm";
    }

    /**
     * 风控审核
     *
     * @param hgmsValidContract
     * @return
     */
    @RequiresPermissions("hgms:hgmsValidContractRcAudit:edit")
    @RequestMapping(value = "audit")
    public String audit(HgmsValidContract hgmsValidContract) {
        //进行合约风控审核
        boolean rcAuditStatus = hgmsValidContractService.rcAudit(hgmsValidContract);
        if (rcAuditStatus) {
            logger.info("合约审核过程成功合约的状态：" + hgmsValidContract.getRcAuditStatus());
        } else {
            logger.info("合约审核过程失败合约的状态：" + hgmsValidContract.getRcAuditStatus());
        }
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsValidContractRcAudit/?repage";
    }

    /**
     * 风控审核通过后审核详情
     *
     * @param hgmsValidContract
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsValidContractRcAudit:view")
    @RequestMapping(value = "detail")
    public String detail(HgmsValidContract hgmsValidContract, Model model) {
        //合约文件添加域
        if (hgmsValidContract.getContractFileAddress() != null) {
            hgmsValidContract.setContractFileAddress(RandomUtil.getFastDfs(hgmsValidContract.getContractFileAddress()));
        }
        //合同启用停用状态赋值
        if (!isEmpty(hgmsValidContract.getContractStatus()))
            hgmsValidContract.setContractStatus(HgmsContractStatus.labelOf(hgmsValidContract.getContractStatus()));
        //风控审核状态
        if (!isEmpty(hgmsValidContract.getRcAuditStatus()))
            hgmsValidContract.setRcAuditStatus(RouteStatus.labelOf(hgmsValidContract.getRcAuditStatus()));
        //录入人赋值
        User userInput = UserUtils.get(hgmsValidContract.getEnteringId());
        if (!ObjectUtils.isEmpty(userInput)) {
            hgmsValidContract.setEnteringId(userInput.getName());
        }
        model.addAttribute("hgmsValidContract", hgmsValidContract);
        return "modules/hgms/hgmsValidContractRcAuditDetail";
    }

    /**
     * 风控进入修改页面
     *
     * @param hgmsValidContract
     * @param model
     * @return
     */
    @RequiresPermissions("hgms:hgmsValidContractRcAudit:view")
    @RequestMapping(value = "edit")
    public String edit(HgmsValidContract hgmsValidContract, Model model) {
        List<HgmsBusiness> businessList = hgmsBusinessService.findList(new HgmsBusiness());
        //合约文件添加域
        if (hgmsValidContract.getContractFileAddress() != null) {
            hgmsValidContract.setContractFileAddress(RandomUtil.getFastDfs(hgmsValidContract.getContractFileAddress()));
        }
        model.addAttribute("businessList", businessList);
        model.addAttribute("hgmsValidContract", hgmsValidContract);
        return "modules/hgms/hgmsValidContractRcAuditEdit";
    }

    /**
     * 风控修改
     *
     * @param hgmsValidContract
     * @param model
     * @param redirectAttributes
     * @param contractFileAddressFile
     * @return
     */
    @RequiresPermissions("hgms:hgmsValidContractRcAudit:edit")
    @RequestMapping(value = "update")
    public String update(HgmsValidContract hgmsValidContract, Model model, RedirectAttributes redirectAttributes, @RequestParam("contractFileAddressFile") MultipartFile contractFileAddressFile) {
        try {
            hgmsValidContract.setContractFileAddress(contractFileAddressFile.getSize() == 0 ? deleteUrl(hgmsValidContract.getContractFileAddress()) : UploadUtil.upLoadFile(contractFileAddressFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean updateStatus = hgmsValidContractService.rcUpdate(hgmsValidContract);
        if (updateStatus) {
            addMessage(redirectAttributes, "修改有效合约成功");
            logger.info("合约修改成功-合约编号：" + hgmsValidContract.getContractId());
        } else {
            addMessage(redirectAttributes, "修改有效合约失败");
            logger.info("合约修改失败-合约编号：" + hgmsValidContract.getContractId());
        }
        return "redirect:" + Global.getAdminPath() + "/hgms/hgmsValidContractRcAudit/?repage";
    }

    /**
     * 修改商户的时候删除图片传回的域名（删除前缀）
     *
     * @param PreSaveUrl
     * @return
     */
    public String deleteUrl(String PreSaveUrl) {
        //获取对应的（对应服务器的fastDFS.properties）
        PropertiesLoader propertiesLoader = new PropertiesLoader("fastDFS.properties");
        //获取#fastDFS图片地址
        String property = propertiesLoader.getProperty("fastdfs.image.host");
        //去除已上传商户的存在域名照片域名
        return PreSaveUrl.replace(property, "");
    }

}