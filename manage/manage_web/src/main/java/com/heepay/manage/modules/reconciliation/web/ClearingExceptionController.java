package com.heepay.manage.modules.reconciliation.web;


import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.BankcardType;
import com.heepay.enums.ChargeDeductType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.entity.ClearingException;
import com.heepay.manage.modules.reconciliation.service.ClearingExceptionService;
import com.heepay.manage.modules.reconciliation.web.util.ClearingExceptionStatus;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/***
 *
 *
 * 描    述：清算异常表
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
@RequestMapping(value = "${adminPath}/reconciliation/clearException")
public class ClearingExceptionController extends BaseController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ClearingExceptionService clearingExceptionService;

    /**
     * @方法说明：首页查询操作
     * @时间： 2017-05-09 09:48 AM
     * @创建人：wangl
     */
    @RequiresPermissions("settle:clearException:view")
    @RequestMapping(value = {"list", ""})
    public String list(ClearingException clearingException,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       Model model) {


        //格式化查询时间条件
        Date endCheckTime = clearingException.getEndTime();
        if(endCheckTime != null){
            String format = new SimpleDateFormat("yyyy-MM-dd").format(endCheckTime);
            format=format+" 23:59:59";
            try {
                Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
                clearingException.setEndTime(time);
            } catch (ParseException e) {
                logger.error("清算异常表--->{时间转换异常}"+e.getMessage());
            }
        }

        //使用redis保存查询条件
        clearingException = (ClearingException) SaveConditions.result(clearingException, "clearingException", request, response);

        //根据条件查询出符合的数据，显示到页面
        Page<ClearingException> page = clearingExceptionService.findPage(new Page<ClearingException>(request, response), clearingException);

        //状态
        List<EnumBean> status = Lists.newArrayList();
        for (ClearingExceptionStatus checkFlg : ClearingExceptionStatus.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(checkFlg.getValue());
            ct.setName(checkFlg.getContent());
            status.add(ct);
        }
        model.addAttribute("status", status);

        //手续费扣除方式
        List<EnumBean> costWay = Lists.newArrayList();
        for (ChargeDeductType checkFlg : ChargeDeductType.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(checkFlg.getValue());
            ct.setName(checkFlg.getContent());
            costWay.add(ct);
        }
        model.addAttribute("costWay", costWay);

        //银行卡类型
        List<EnumBean> bankcardType = Lists.newArrayList();
        for (BankcardType checkFlg : BankcardType.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(checkFlg.getValue());
            ct.setName(checkFlg.getContent());
            bankcardType.add(ct);
        }
        model.addAttribute("bankcardType", bankcardType);


        for (ClearingException exception : page.getList()) {
            logger.info("查询数据开始------>{}", exception);
            //通道编码
            if (StringUtils.isNotBlank(exception.getStatus())) {
                exception.setStatus(ClearingExceptionStatus.labelOf(exception.getStatus()));
            }

        }

        model.addAttribute("page", page);
        model.addAttribute("clearingException", clearingException);

        return "modules/reconciliation/exception/clearingException";
    }


    /**
     * @方法说明：更具ID查询对象
     * @时间： 2017-04-10 03:07 PM
     * @创建人：wangl
     */
    @RequiresPermissions("settle:clearingChannelRecord:view")
    @RequestMapping(value = "/more/{clearId}")
    public String more(@PathVariable(value = "clearId") Long clearId,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       Model model){

        ClearingException clearingException = clearingExceptionService.getEntity(clearId);
        if (StringUtils.isNotBlank(clearingException.getStatus())) {
            clearingException.setStatus(ClearingExceptionStatus.labelOf(clearingException.getStatus()));
        }
        logger.info("清算异常表--->{查询结果}"+clearingException);
        model.addAttribute("clearingException",clearingException);

        return "modules/reconciliation/exception/clearingExceptionMore";
    }
}

