/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.riskManage.web;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.ChannelPartner;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.DictList;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.util.ChannelCodeClear;
import com.heepay.manage.modules.reconciliation.util.ChannelTypeClear;
import com.heepay.manage.modules.risk.entity.RiskInternalMonitorChannel;
import com.heepay.manage.modules.risk.service.RiskInternalMonitorChannelService;
import com.heepay.manage.modules.riskManage.rpc.client.RiskMoniConfClient;
import com.heepay.manage.modules.sys.utils.BusinessInfoUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


/**
 *
 * 描    述：内部监控通道配置Controller
 *
 * 创 建 者： @author wj
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
@RequestMapping(value = "${adminPath}/risk/riskInternalMonitorChannel")
public class RiskInternalMonitorChannelController extends BaseController {

    @Autowired
    private RiskInternalMonitorChannelService riskInternalMonitorChannelService;

    @Autowired
    private RiskMoniConfClient riskMoniConfClient;

    private static final Logger log = LogManager.getLogger();

    @ModelAttribute
    public RiskInternalMonitorChannel get(@RequestParam(required = false) String id) {
        RiskInternalMonitorChannel entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = riskInternalMonitorChannelService.get(id);
        }
        if (entity == null) {
            entity = new RiskInternalMonitorChannel();
        }
        return entity;
    }

    @RequiresPermissions("risk:riskInternalMonitorChannel:view")
    @RequestMapping(value = {"list", ""})
    public String list(RiskInternalMonitorChannel riskInternalMonitorChannel, HttpServletRequest request, HttpServletResponse response, Model model) {

        String channelPartnerCode = riskInternalMonitorChannel.getChannelPartnerCode();
        String bankNo = riskInternalMonitorChannel.getBankNo();

        if (ChannelPartner.DIRECTCONNECTION.getValue().equals(channelPartnerCode)) {//只有直连才组合
            if (StringUtils.isNotBlank(bankNo)) {  //bankNo
                String payTypeCode = channelPartnerCode + "-" + bankNo;
                riskInternalMonitorChannel.setChannelPartnerCode(payTypeCode);
            }
        }

        Page<RiskInternalMonitorChannel> page = riskInternalMonitorChannelService.findPage(new Page<RiskInternalMonitorChannel>(request, response), riskInternalMonitorChannel);
        //支付通道类型
        List<EnumBean> pTList = ChannelTypeClear.getList();
        model.addAttribute("checkTypeList", pTList);

        //查询通道合作方的内容
        List<EnumBean> dataEntity = DictList.channelPartner();
        model.addAttribute("dataEntity", dataEntity);
        model.addAttribute("page", page);

        //为页面回显返回数据，查分组合命名

        String channelCode2 = riskInternalMonitorChannel.getChannelPartnerCode();
        try {
            if (StringUtils.isNotBlank(channelCode2)) {
                //将组合命名的数据拆分开
                int lastIndexOf = channelCode2.lastIndexOf("-");
                if (lastIndexOf != -1) {
                    String substring = channelCode2.substring(0, lastIndexOf);
                    riskInternalMonitorChannel.setChannelPartnerCode(substring);
                }
            }
        } catch (Exception e) {
            logger.error("转换失败------>{}" + channelCode2, e);
        }
        model.addAttribute("riskInternalMonitorChannel", riskInternalMonitorChannel);
        return "modules/riskManage/riskInternalMonitorChannelList";
    }

    @RequiresPermissions("risk:riskInternalMonitorChannel:view")
    @RequestMapping(value = "form")
    public String form(RiskInternalMonitorChannel riskInternalMonitorChannel, Model model) {
        if (riskInternalMonitorChannel.getInternalChannelId() == null) {
            RiskInternalMonitorChannel riskMonitorChannel = new RiskInternalMonitorChannel();
            model.addAttribute("riskInternalMonitorChannel", riskMonitorChannel);
        } else {
            String channelCode2 = riskInternalMonitorChannel.getChannelPartnerCode();
            try {
                if (StringUtils.isNotBlank(channelCode2)) {
                    //将组合命名的数据拆分开
                    int lastIndexOf = channelCode2.lastIndexOf("-");
                    if (lastIndexOf != -1) {
                        String substring = channelCode2.substring(0, lastIndexOf);
                        String substring1 = channelCode2.substring(lastIndexOf + 1, channelCode2.length());
                        riskInternalMonitorChannel.setChannelPartnerCode(substring);
                        riskInternalMonitorChannel.setBankNo(substring1);
                    }
                }
            } catch (Exception e) {
                logger.error("转换失败------>{}" + channelCode2, e);
            }
            model.addAttribute("riskInternalMonitorChannel", riskInternalMonitorChannel);
        }

        //支付通道类型
        List<EnumBean> pTList = ChannelTypeClear.getList();
        model.addAttribute("checkTypeList", pTList);

        //查询通道合作方的内容
        List<EnumBean> dataEntity = DictList.channelPartner();
        model.addAttribute("dataEntity", dataEntity);

        return "modules/riskManage/riskInternalMonitorChannelForm";
    }

    @RequiresPermissions("risk:riskInternalMonitorChannel:edit")
    @RequestMapping(value = "save")
    public String save(RiskInternalMonitorChannel riskInternalMonitorChannel, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, riskInternalMonitorChannel)) {
            return form(riskInternalMonitorChannel, model);
        }
        String channelPartnerCode = riskInternalMonitorChannel.getChannelPartnerCode();
        String bankNo = riskInternalMonitorChannel.getBankNo();

        riskInternalMonitorChannel.setChannelPartnerName(ChannelCodeClear.labelOf(channelPartnerCode));
        if (StringUtils.equals(ChannelPartner.DIRECTCONNECTION.getValue(),channelPartnerCode)) {//只有直连才组合
            if (StringUtils.isNotBlank(bankNo)) {  //   bankName
                String payTypeCode = channelPartnerCode + "-" + bankNo;
                riskInternalMonitorChannel.setChannelPartnerCode(payTypeCode);
                riskInternalMonitorChannel.setChannelPartnerName(riskInternalMonitorChannel.getChannelPartnerName()+"-"+ BusinessInfoUtils.getBankNameByBankId(bankNo).replace(",",""));
                //riskInternalMonitorChannel.setChannelPartnerName(riskInternalMonitorChannel.getChannelPartnerName()+"-"+riskInternalMonitorChannel.getBankName().replace(",",""));
            }
        }

        riskInternalMonitorChannel.setOperator(UserUtils.getUser().getName());
        riskInternalMonitorChannel.setChannelTypeName(ChannelTypeClear.labelOf(riskInternalMonitorChannel.getChannelTypeCode()));
        if (riskInternalMonitorChannel.getInternalChannelId() != null) { //修改
            String msg = riskMoniConfClient.editChannelMonitorConfig(JsonMapperUtil.nonEmptyMapper().toJson(riskInternalMonitorChannel));
            log.info("内部监控通道配置返回{}", msg);
            if ("success".equals(msg)) {
                addMessage(redirectAttributes, "修改成功");
            }
        } else {//添加
            riskInternalMonitorChannel.setCreateTime(new Date());
            String msg = riskMoniConfClient.addChannelMonitorConfig(JsonMapperUtil.nonEmptyMapper().toJson(riskInternalMonitorChannel));
            log.info("内部监控通道配置返回{}", msg);
            if ("success".equals(msg)) {
                addMessage(redirectAttributes, "添加成功");
            }
        }
        addMessage(redirectAttributes, "保存内部监控通道配置成功");
        return "redirect:" + Global.getAdminPath() + "/risk/riskInternalMonitorChannel/?repage";
    }

    @RequiresPermissions("risk:riskInternalMonitorChannel:edit")
    @RequestMapping(value = "delete")
    public String delete(RiskInternalMonitorChannel riskInternalMonitorChannel, RedirectAttributes redirectAttributes) throws TException {

        String msg = riskMoniConfClient.delChannelMonitorConfig(JsonMapperUtil.nonEmptyMapper().toJson(riskInternalMonitorChannel));
        log.info("内部监控通道配置返回{}", msg);
        if ("success".equals(msg)) {
            addMessage(redirectAttributes, "删除成功");
        } else {
            addMessage(redirectAttributes, "删除失败");
        }

        return "redirect:" + Global.getAdminPath() + "/risk/riskInternalMonitorChannel/?repage";
    }

}