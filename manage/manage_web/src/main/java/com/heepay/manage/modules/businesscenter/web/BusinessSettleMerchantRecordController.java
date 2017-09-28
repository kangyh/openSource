package com.heepay.manage.modules.businesscenter.web;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;
import com.heepay.manage.modules.settle.entity.SettleMerchantRecord;
import com.heepay.manage.modules.settle.service.SettleMerchantRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 描 述：商务中心-商户结算记录Controller
 * <p>
 * 创 建 者： 王亚洪
 * 创建时间：
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
@RequestMapping(value = "${adminPath}/business/settleMerchantRecord")
public class BusinessSettleMerchantRecordController extends BaseController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private SettleMerchantRecordService settleMerchantRecordService;


    /**
     * @方法说明：查询商户结算记录List
     * @时间：2016年9月19日
     * @创建人：王亚洪
     */
    @RequiresPermissions("business:settleMerchantRecord:view")
    @RequestMapping(value = "getSettleMerchantRecord")
    public String getSettleMerchantRecord(SettleMerchantRecord settleMerchantRecord, HttpServletRequest request,
                                          HttpServletResponse response, Model model) throws Exception {

        //使用cookie保存查询条件
        settleMerchantRecord = (SettleMerchantRecord) SaveConditions.result(settleMerchantRecord, "settleMerchantRecords", request, response);
        try {
            List<Long> merchantIdList = RandomUtil.getMerchantIdList();
            settleMerchantRecord.setMerchantIds(merchantIdList);
            model = settleMerchantRecordService.findSettleMerchantRecordPage(new Page<SettleMerchantRecord>(request, response), settleMerchantRecord, model);
            model.addAttribute("settleMerchantRecord", settleMerchantRecord);

            return "modules/businesscenter/settleMerchantRecordList";
        } catch (Exception e) {
            logger.error("SettleMerchantRecordController list has a error:{查询商户结算记录List错误 FIND_ERROR}", e);
            throw new Exception(e);
        }
    }

    /**
     * @方法说明：查询商户结算记录详细信息
     * @时间：2016年9月19日
     * @创建人：wangdong
     */
    @RequiresPermissions("business:settleMerchantRecord:view")
    @RequestMapping(value = "toDetail")
    public String toDetail(SettleMerchantRecord settleMerchantRecord, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        try {
            ClearingMerchantRecord clearingMerchantRecord = new ClearingMerchantRecord();
            String settleBath = request.getParameter("settleBath");
            //因为  商户侧存在T+0的支付  所以查看详情，根据结算批次查询  2016年12月2日15:09:10
            clearingMerchantRecord.setSettleBath(settleBath);//结算批次
            model = settleMerchantRecordService.findSettleMerchantRecordDetailPage(new Page<ClearingMerchantRecord>(request, response), clearingMerchantRecord, model);
            model.addAttribute("settleBath", settleBath);
            return "modules/businesscenter/settleMerchantRecordToDetail";
        } catch (Exception e) {
            logger.error("SettleMerchantRecordController toDetail has a error:{商户结算记录详细信息错误 FIND_ERROR}", e);
            throw new Exception(e);
        }
    }


}