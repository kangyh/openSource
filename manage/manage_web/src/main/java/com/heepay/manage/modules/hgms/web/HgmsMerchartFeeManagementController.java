/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.web;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantFeeManagementObj;
import com.heepay.manage.modules.hgms.service.HgmsMerchantFeeManagementService;
import com.heepay.manage.modules.hgms.util.DateDemo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.heepay.manage.common.utils.DateUtil.dateToString;


/**
 *
 * 描    述：批量收款记录汇总表Controller
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
@RequestMapping(value = "${adminPath}/hgms/hgmsMerchantFeeManagementObj")
public class HgmsMerchartFeeManagementController extends BaseController {

    @Autowired
    private HgmsMerchantFeeManagementService hgmsMerchantFeeManagementService;


    /**
     * @discription 月结手续费管理 汇总
     * @author njp
     * @created 2017年3月23日
     * @param
     * @return
     */
    @RequiresPermissions("hgms:hgmsMerchantFeeManagementObj:view")
    @RequestMapping(value = {"list", ""})
    public String list(HgmsMerchantFeeManagementObj hgmsMerchantFeeManagementObj, HttpServletRequest request, HttpServletResponse response, Model model) {

        Date createdate = null;
        //计算指定月份的启始日期和结束日期
        if (hgmsMerchantFeeManagementObj.getCreateTime() != null) {
            createdate = hgmsMerchantFeeManagementObj.getCreateTime();

            //logger.info("月末日期为==============:"  );
            hgmsMerchantFeeManagementObj.setBeginCreateTime(DateDemo.getMinMonthDate(dateToString(createdate, "yyyy-MM") + "-01 00:00:00"));
            hgmsMerchantFeeManagementObj.setEndCreateTime(DateDemo.getMaxMonthDate(dateToString(createdate, "yyyy-MM") + "-01 00:00:00"));
        }
        Page<HgmsMerchantFeeManagementObj> page = hgmsMerchantFeeManagementService.findPage(new Page<HgmsMerchantFeeManagementObj>(request, response), hgmsMerchantFeeManagementObj);
        for (HgmsMerchantFeeManagementObj gmsMerchantFeeManagement : page.getList()) {
            gmsMerchantFeeManagement.setCreateTime(createdate);
            gmsMerchantFeeManagement.setBeginCreateTime(hgmsMerchantFeeManagementObj.getBeginCreateTime());
            gmsMerchantFeeManagement.setEndCreateTime(hgmsMerchantFeeManagementObj.getEndCreateTime());
        }

        model.addAttribute("page", page);
        return "modules/hgms/TranMerchantMonthlyFeeList";
    }

    /**
     * @discription 月结手续费管理 明细
     * @author njp
     * @created 2017年3月23日
     * @param
     * @return
     */
    @RequiresPermissions("hgms:hgmsMerchantFeeManagementObj:view")
    @RequestMapping(value = {"detaillist", "detaillist"})
    public String detaillist(HgmsMerchantFeeManagementObj hgmsMerchantFeeManagementObj, HttpServletRequest request, HttpServletResponse response, Model model) {
        String createdateflag = null;
        //判断查询条件 月份
        if (hgmsMerchantFeeManagementObj.getCreateTime() != null) {
            createdateflag = "1";
        }
        HgmsMerchantFeeManagementObj feeManageobjPayquery = new HgmsMerchantFeeManagementObj();
        HgmsMerchantFeeManagementObj feeManageobjPay = hgmsMerchantFeeManagementService.findPaySummery(hgmsMerchantFeeManagementObj);
        HgmsMerchantFeeManagementObj feeManageobjCollection = hgmsMerchantFeeManagementService.findCollectionSummery(hgmsMerchantFeeManagementObj);
        if (feeManageobjPay != null) {
            feeManageobjPay.setFlagstr("代付");
            if (createdateflag != null) {
                feeManageobjPay.setCreateTime(hgmsMerchantFeeManagementObj.getCreateTime());
                feeManageobjPay.setBeginCreateTime(hgmsMerchantFeeManagementObj.getBeginCreateTime());
                feeManageobjPay.setEndCreateTime(hgmsMerchantFeeManagementObj.getEndCreateTime());
            }
        }
        if (feeManageobjCollection != null) {
            feeManageobjCollection.setFlagstr("代收");
            if (createdateflag != null) {
                feeManageobjCollection.setCreateTime(hgmsMerchantFeeManagementObj.getCreateTime());
                feeManageobjCollection.setBeginCreateTime(hgmsMerchantFeeManagementObj.getBeginCreateTime());
                feeManageobjCollection.setEndCreateTime(hgmsMerchantFeeManagementObj.getEndCreateTime());
            }
        }

        model.addAttribute("feeManageobjPay", feeManageobjPay);
        model.addAttribute("feeManageobjCollection", feeManageobjCollection);
        return "modules/hgms/TranMerchantMonthlyFeeDetailList";
    }

    /**
     * @discription 日结手续费管理 汇总
     * @author njp
     * @created 2017年3月23日
     * @param
     * @return
     */
    @RequiresPermissions("hgms:hgmsMerchantFeeManagementObj:view")
    @RequestMapping(value = {"dayFeeList", "dayFeeList"})
    public String dayFeeList(HgmsMerchantFeeManagementObj hgmsMerchantFeeManagementObj, HttpServletRequest request, HttpServletResponse response, Model model) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdate = null;
        //计算指定月份的启始日期和结束日期
        if (hgmsMerchantFeeManagementObj.getCreateTime() != null) {
            createdate = hgmsMerchantFeeManagementObj.getCreateTime();

            //logger.info("月末日期为==============:"  );

            try {
                hgmsMerchantFeeManagementObj.setBeginCreateTime(sdf.parse(dateToString(createdate, "yyyy-MM-dd") + " 00:00:00"));
                hgmsMerchantFeeManagementObj.setEndCreateTime(sdf.parse(dateToString(createdate, "yyyy-MM-dd") + " 23:59:59"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        Page<HgmsMerchantFeeManagementObj> page = hgmsMerchantFeeManagementService.findPage(new Page<HgmsMerchantFeeManagementObj>(request, response), hgmsMerchantFeeManagementObj);
        for (HgmsMerchantFeeManagementObj gmsMerchantFeeManagement : page.getList()) {
            gmsMerchantFeeManagement.setCreateTime(createdate);
            gmsMerchantFeeManagement.setBeginCreateTime(hgmsMerchantFeeManagementObj.getBeginCreateTime());
            gmsMerchantFeeManagement.setEndCreateTime(hgmsMerchantFeeManagementObj.getEndCreateTime());
        }

        model.addAttribute("page", page);
        return "modules/hgms/TranMerchantDayFeeList";
    }

    /**
     * @discription 日结手续费管理 明细
     * @author njp
     * @created 2017年3月23日
     * @param
     * @return
     */
    @RequiresPermissions("hgms:hgmsMerchantFeeManagementObj:view")
    @RequestMapping(value = {"dayDetailList", "dayDetailList"})
    public String dayDetailList(HgmsMerchantFeeManagementObj hgmsMerchantFeeManagementObj, HttpServletRequest request, HttpServletResponse response, Model model) {

        String createdateflag = null;
        //判断查询条件 月份
        if (hgmsMerchantFeeManagementObj.getCreateTime() != null) {
            createdateflag = "1";
        }
        HgmsMerchantFeeManagementObj feeManageobjPay = hgmsMerchantFeeManagementService.findPaySummery(hgmsMerchantFeeManagementObj);
        HgmsMerchantFeeManagementObj feeManageobjCollection = hgmsMerchantFeeManagementService.findCollectionSummery(hgmsMerchantFeeManagementObj);

        if (feeManageobjPay != null) {
            feeManageobjPay.setFlagstr("代付");
            if (createdateflag != null) {
                feeManageobjPay.setCreateTime(hgmsMerchantFeeManagementObj.getCreateTime());
                feeManageobjPay.setBeginCreateTime(hgmsMerchantFeeManagementObj.getBeginCreateTime());
                feeManageobjPay.setEndCreateTime(hgmsMerchantFeeManagementObj.getEndCreateTime());
            }
        }
        if (feeManageobjCollection != null) {
            feeManageobjCollection.setFlagstr("代收");
            if (createdateflag != null) {
                feeManageobjCollection.setCreateTime(hgmsMerchantFeeManagementObj.getCreateTime());
                feeManageobjCollection.setBeginCreateTime(hgmsMerchantFeeManagementObj.getBeginCreateTime());
                feeManageobjCollection.setEndCreateTime(hgmsMerchantFeeManagementObj.getEndCreateTime());
            }
        }

        model.addAttribute("feeManageobjPay", feeManageobjPay);
        model.addAttribute("feeManageobjCollection", feeManageobjCollection);
        return "modules/hgms/TranMerchantDayFeeDetailList";
    }

}