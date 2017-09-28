package com.heepay.manage.modules.settle.web.rabbit;

import com.heepay.manage.modules.settle.entity.rabbit.SettleMerchantRecordRabbit;

import java.io.Serializable;
import java.util.List;

/***
 *
 *
 * 描    述：
 *
 * 创 建 者： wangl
 * 创建时间：  2017/7/1011:01
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
public class SettleMerchantMessage implements Serializable {

    private static final long serialVersionUID = 4946173065449810591L;
    //结算记录
    private SettleMerchantRecordRabbit settleMerchantRecord;
    //清算明细
    private List<ClearMerchantDetailMessage> clearMerchantMessage;

    public SettleMerchantRecordRabbit getSettleMerchantRecord() {
        return settleMerchantRecord;
    }

    public void setSettleMerchantRecord(SettleMerchantRecordRabbit settleMerchantRecord) {
        this.settleMerchantRecord = settleMerchantRecord;
    }

    public List<ClearMerchantDetailMessage> getClearMerchantMessage() {
        return clearMerchantMessage;
    }
    public void setClearMerchantMessage(
            List<ClearMerchantDetailMessage> clearMerchantMessage) {
        this.clearMerchantMessage = clearMerchantMessage;
    }

}
