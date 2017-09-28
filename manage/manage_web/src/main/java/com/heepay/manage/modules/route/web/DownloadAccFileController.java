package com.heepay.manage.modules.route.web;

import com.heepay.date.DateUtils;
import com.heepay.enums.TransType;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.route.entity.DownloadAccFile;
import com.heepay.manage.modules.route.web.client.DownloadAccFileClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * 描    述：下载对账文件
 *
 * 创 建 者： ly
 * 创建时间： 2017年2月27日11:08:54
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/route/download")
public class DownloadAccFileController extends BaseController {
    @Autowired
    private DownloadAccFileClient downloadAccFileClient;

    private static final Logger logger = LogManager.getLogger();

    @RequestMapping(value="")
    public String list(DownloadAccFile downloadAccFile,Model model){
        return "modules/route/downloadAccFile";
    }


    @RequestMapping(value="/down")
    public String down(DownloadAccFile downloadAccFile, Model model){
        switch (downloadAccFile.getBankNo()){
            case "ABCEbank" :
                logger.info("下载农行网银对账文件开始");
                downloadAccFileClient.downloadABCAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyy/MM/dd"));
                logger.info("下载农行网银对账文件结束");
                break;
            case "BILL" :
                logger.info("下载快钱对账文件开始");
                downloadAccFileClient.downloadBILLAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyy-MM-dd"));
                logger.info("下载快钱对账文件结束");
                break;
            case "CCBBatch" :
                logger.info("下载建行批付对账文件开始");
                downloadAccFileClient.downloadCCBBatchAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("下载建行批付对账文件结束");
                break;
            case "CCBEbank" :
                logger.info("下载建行B2C支付对账文件开始");
                downloadAccFileClient.downloadCCBEbankAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("下载建行B2C支付对账文件结束");
                break;
            case "CCBEbankRefund" :
                logger.info("下载建行B2C退款对账文件开始");
                downloadAccFileClient.downloadCCBEbankRefAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("下载建行B2C退款对账文件结束");
                break;
            case "CIBWeChat" :
                logger.info("兴业点芯微信支付对账文件下载开始");
                downloadAccFileClient.downloadCIBWeChatAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("兴业点芯微信支付对账文件下载结束");
                break;
            case "CIBWeChatRefund" :
                logger.info("兴业点芯微信退款对账文件下载开始");
                downloadAccFileClient.downloadCIBWeChatRefundAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("兴业点芯微信退款对账文件下载结束");
                break;
            case "PSBCEbank" :
                logger.info("邮储B2C网银支付对账文件下载开始");
                downloadAccFileClient.downloadPSBCEbankAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("邮储B2C网银支付对账文件下载结束");
                break;
            case "PSBCEbankRefund" :
                logger.info("邮储B2C网银退款对账文件下载开始");
                downloadAccFileClient.downloadPSBCEbankRefundAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("邮储B2C网银退款对账文件下载结束");
                break;
            case "SPDBEbank" :
                logger.info("浦发B2C支付对账文件下载开始");
                downloadAccFileClient.downloadAPDBAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"), TransType.PAY.getValue());
                logger.info("浦发B2C支付对账文件下载结束");
                break;
            case "SPDBEbankRefund" :
                logger.info("浦发B2C退款对账文件下载开始");
                downloadAccFileClient.downloadAPDBAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"),TransType.REFUND.getValue());
                logger.info("浦发B2C退款对账文件下载结束");
                break;
            case "ShengPay" :
                logger.info("盛付通对账文件下载开始");
                downloadAccFileClient.downloadShengpayAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("盛付通对账文件下载结束");
                break;
            case "NBCBEbank" :
                logger.info("宁波B2C网银对账文件下载开始");
                downloadAccFileClient.downloadNBCBEbankAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("宁波B2C网银对账文件下载结束");
                break;
            case "NBCBEbankRef" :
                logger.info("宁波B2C网银退款对账文件下载开始");
                downloadAccFileClient.downloadNBCBEbankRefundAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("宁波B2C网银退款对账文件下载结束");
                break;
            case "TenpayEbank" :
                logger.info("财付通B2C网银对账文件下载开始");
                downloadAccFileClient.downloadTenpayEbankAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyy-MM-dd"));
                logger.info("财付通B2C网银对账文件下载结束");
                break;
            case "HeepayWechat" :
                logger.info("汇付宝微信对账文件下载开始");
                downloadAccFileClient.downloadHeepayWechatAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("汇付宝微信对账文件下载结束");
                break;
            case "HeepayAlipay" :
                logger.info("汇付宝支付宝对账文件下载开始");
                downloadAccFileClient.downloadHeepayAlipayAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("汇付宝支付宝对账文件下载结束");
                break;
            case "HeepayWechatRef" :
                logger.info("汇付宝微信退款对账文件下载开始");
                downloadAccFileClient.downloadHeepayWechatRefAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("汇付宝微信退款对账文件下载结束");
                break;
            case "HeepayAlipayRef" :
                logger.info("汇付宝支付宝退款对账文件下载开始");
                downloadAccFileClient.downloadHeepayAlipayRefAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("汇付宝支付宝退款对账文件下载结束");
                break;
            case "TenpayEbankRef" :
                logger.info("财付通B2C网银退款对账文件下载开始");
                downloadAccFileClient.downloadTenpayEbankRefundAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyy-MM-dd"));
                logger.info("财付通B2C网银退款对账文件下载结束");
                break;
            case "Sumpay" :
                logger.info("统统付对账文件下载开始");
                downloadAccFileClient.downloadSumpayAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("统统付对账文件下载结束");
                break;
            case "BaofuEbank" :
                logger.info("宝付B2C网银对账文件下载开始");
                downloadAccFileClient.downloadBaofuEbankAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyy-MM-dd"));
                logger.info("宝付B2C网银对账文件下载结束");
                break;
            case "ICBC" :
                logger.info("工商B2C网银对账文件下载开始");
                downloadAccFileClient.downloadICBCAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("工商B2C网银对账文件下载结束");
                break;
            case "ICBCRef" :
                logger.info("工商B2C退款对账文件下载开始");
                downloadAccFileClient.downloadICBCRefAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("工商B2C退款对账文件下载结束");
                break;
            case "YsePay" :
                logger.info("银盛对账文件下载开始");
                downloadAccFileClient.downloadysePayAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyy-MM-dd"));
                logger.info("银盛对账文件下载结束");
                break;
            case "Cgb" :
                logger.info("广发对账文件下载开始");
                downloadAccFileClient.downloadCgbAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("广发对账文件下载结束");
                break;
            case "BOBEbank" :
                logger.info("北京B2C网银对账文件下载开始");
                downloadAccFileClient.downloadBOBEbankAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("北京B2C网银对账文件下载结束");
                break;
            case "EasyLinkWithhold" :
                logger.info("易联代扣对账文件下载开始");
                downloadAccFileClient.downloadEasyLinkWithholdAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("易联代扣对账文件下载结束");
                break;
            case "EasyLinkQuickPay" :
                logger.info("易联快捷对账文件下载开始");
                downloadAccFileClient.downloadEasyLinkQuickPayAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("易联快捷对账文件下载结束");
                break;
            case "SPDBQuick" :
                logger.info("浦发快捷支付类对账文件下载开始");
                downloadAccFileClient.downloadSPDBQuickAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("浦发快捷支付类对账文件下载结束");
                break;
            case "SPDBQuickRef" :
                logger.info("浦发快捷退款类对账文件下载开始");
                downloadAccFileClient.downloadSPDBQuickRefAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("浦发快捷退款类对账文件下载结束");
                break;
            case "BestPayWithhold" :
                logger.info("翼支付代扣对账文件下载开始");
                downloadAccFileClient.downloadBestPayWithholdAccFile(DateUtils.getDateStr(downloadAccFile.getDownDate(),"yyyyMMdd"));
                logger.info("翼支付代扣对账文件下载结束");
                break;
        }
        model.addAttribute("msg","下载完成");
        return "modules/route/downloadAccFile";
    }


}
