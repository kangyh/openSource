package com.heepay.manage.modules.riskChannel.web;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.riskChannel.entity.RiskChannelLogQuotaVo;
import com.heepay.manage.modules.riskChannel.rpc.client.RiskChannelRpcClient;
import com.heepay.risk.RiskChannelLogVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 名称：风险通道 Controller
 * <p>
 * 创建者：yuliang
 * 创建时间：2017-06-26 13:53
 * 创建描述：风险通道 Controller
 * <p>
 * 审核者：
 * 审核时间：
 * 审核描述：
 * <p>
 * 修改者：
 * 修改时间：
 * 修改内容：
 */

@Controller
@RequestMapping(value = "${adminPath}/riskChannel")
public class RiskChannelController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private RiskChannelRpcClient riskChannelRpcClient;


    @RequiresPermissions("riskChannel:channelQuota:view")
    @RequestMapping(value = {"channelQuota/list", ""})
    public String channelQuotaList(RiskChannelLogQuotaVo riskChannelLogQuotaVo, @RequestParam(defaultValue = "1")String pageNo, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<RiskChannelLogQuotaVo> page = new Page<>(request, response);
        int pageIndex = Integer.parseInt(pageNo);
        page.setPageNo(pageIndex);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("pageIndex", String.valueOf(--pageIndex));
        paramMap.put("pageSize", String.valueOf(page.getPageSize()));
        if(StringUtils.isNotBlank(riskChannelLogQuotaVo.getChannelCode())){
            paramMap.put("channel_code", riskChannelLogQuotaVo.getChannelCode());
        }
        if(StringUtils.isNotBlank(riskChannelLogQuotaVo.getBankNo())){
            paramMap.put("bank_no", riskChannelLogQuotaVo.getBankNo());
        }
        if(StringUtils.isNotBlank(riskChannelLogQuotaVo.getChannelPartnerCode())){
            paramMap.put("channel_partner_code", riskChannelLogQuotaVo.getChannelPartnerCode());
        }
        if(StringUtils.isNotBlank(riskChannelLogQuotaVo.getChannelTypeCode())){
            paramMap.put("channel_type_code", riskChannelLogQuotaVo.getChannelTypeCode());
        }
        if(StringUtils.isNotBlank(riskChannelLogQuotaVo.getCardTypeCode())){
            paramMap.put("card_type_code", riskChannelLogQuotaVo.getCardTypeCode());
        }
        String resultJson = riskChannelRpcClient.getChannelQuotaList(paramMap);
        if (StringUtil.notBlank(resultJson)){
            Map<String, Object> resultMap = JsonMapperUtil.nonEmptyMapper().fromJson(resultJson, Map.class);
            page.setCount(Long.valueOf(resultMap.get("totalSize").toString()));
            List<RiskChannelLogQuotaVo> list = (List<RiskChannelLogQuotaVo>) resultMap.get("resultList");
            page.setList(list);
        }
        model.addAttribute("page", page);
        return "modules/riskChannel/channelQuota";
    }

    @RequiresPermissions("riskChannel:channelRate:view")
    @RequestMapping(value = {"channelRate/list", ""})
    public String channelRateList(RiskChannelLogVo riskChannelLogVo, @RequestParam(defaultValue = "1")String pageNo, HttpServletRequest request, HttpServletResponse response, Model model) {
        int pageIndex = Integer.parseInt(pageNo);
        Page page = new Page<>(request, response);
        page.setPageNo(pageIndex);
        //查询条件组装
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("pageIndex", String.valueOf(--pageIndex));
        paramMap.put("pageSize", String.valueOf(page.getPageSize()));
        if (riskChannelLogVo != null){
            if(StringUtil.notBlank(riskChannelLogVo.getChannelCode())){
                paramMap.put("channel_code", riskChannelLogVo.getChannelCode());
            }
            if(StringUtil.notBlank(riskChannelLogVo.getBankNo())){
                paramMap.put("bank_no",riskChannelLogVo.getBankNo());
            }
            if(StringUtil.notBlank(riskChannelLogVo.getChannelPartnerCode())){
                paramMap.put("channel_partner_code",riskChannelLogVo.getChannelPartnerCode());
            }
            if(StringUtil.notBlank(riskChannelLogVo.getChannelTypeCode())){
                paramMap.put("channel_type_code",riskChannelLogVo.getChannelTypeCode());
            }
            if(StringUtil.notBlank(riskChannelLogVo.getCardTypeCode())){
                paramMap.put("card_type_code",riskChannelLogVo.getCardTypeCode());
            }
        }
        String resultJson = riskChannelRpcClient.getChannelRate(paramMap);
        if (StringUtil.notBlank(resultJson)){
            //查询结果转换
            Map<String, Object> resultMap = JsonMapperUtil.nonEmptyMapper().fromJson(resultJson,Map.class);
            page.setCount(Long.valueOf(resultMap.get("totalSize").toString()));
            List list = (List) resultMap.get("resultList");
            page.setList(list);
        }
        model.addAttribute("page", page);
        return "modules/riskChannel/channelRateList";
    }

    @RequiresPermissions("riskChannel:channelLog:view")
    @RequestMapping(value = {"channelLog/list", ""})
    public String channelLogList(RiskChannelLogVo riskChannelLogVo, @RequestParam(defaultValue = "1")String pageNo, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<RiskChannelLogVo> page = new Page<>(request, response);
        int pageIndex = Integer.parseInt(pageNo);
        page.setPageNo(pageIndex);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("pageIndex", String.valueOf(--pageIndex));
        paramMap.put("pageSize", String.valueOf(page.getPageSize()));
        if(StringUtils.isNotBlank(riskChannelLogVo.getChannelCode())){
            paramMap.put("channel_code", riskChannelLogVo.getChannelCode());
        }
        if(StringUtils.isNotBlank(riskChannelLogVo.getBankNo())){
            paramMap.put("bank_no", riskChannelLogVo.getBankNo());
        }
        if(StringUtils.isNotBlank(riskChannelLogVo.getChannelPartnerCode())){
            paramMap.put("channel_partner_code", riskChannelLogVo.getChannelPartnerCode());
        }
        if(StringUtils.isNotBlank(riskChannelLogVo.getChannelTypeCode())){
            paramMap.put("channel_type_code", riskChannelLogVo.getChannelTypeCode());
        }
        if(StringUtils.isNotBlank(riskChannelLogVo.getCardTypeCode())){
            paramMap.put("card_type_code", riskChannelLogVo.getCardTypeCode());
        }
        if(StringUtils.isNotBlank(riskChannelLogVo.getResult())){
            paramMap.put("result", riskChannelLogVo.getResult());
        }
        if(StringUtils.isNotBlank(riskChannelLogVo.getReqTime()) && StringUtils.isNotBlank(riskChannelLogVo.getRespTime())){
            paramMap.put("begin", riskChannelLogVo.getReqTime().replace("-", "").replace(" ", "").replace(":", "")+"000");
            paramMap.put("end", riskChannelLogVo.getRespTime().replace("-", "").replace(" ", "").replace(":", "")+"000");
        }
        if(StringUtils.isNotBlank(riskChannelLogVo.getPaymentId())){
            paramMap.put("payment_id", riskChannelLogVo.getPaymentId());
        }
        String resultJson = riskChannelRpcClient.getChannelLogList(paramMap);
        if (StringUtil.notBlank(resultJson)){
            Map<String, Object> resultMap = JsonMapperUtil.nonEmptyMapper().fromJson(resultJson, Map.class);
            page.setCount(Long.valueOf(resultMap.get("totalSize").toString()));
            List<RiskChannelLogVo> list = (List<RiskChannelLogVo>) resultMap.get("resultList");
            page.setList(list);
        }
        model.addAttribute("page", page);
        return "modules/riskChannel/channelLog";
    }
}
