/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.route.web.client;

import com.heepay.route.thrift.account.TDownloadAccFileService;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * 描    述：网关下载对账文件
 *
 * 创 建 者： ly
 * 创建时间： 2017年2月27日11:10:54
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
@Service
public class DownloadAccFileClient extends BaseClientDistribute {


    private static Logger logger = LogManager.getLogger();

    private static final String SERVICE_NAME = "downloadAccFileService";

    private static final String NODE_NAME = "gateway-route";

    /**
     * 建行批付对账文件下载
     * @discription
     * @author ly
     * @created 2017年2月27日11:11:16
     * @return
     */
    public boolean downloadCCBBatchAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadCCBBatchAccFile(date);
        } catch (TException e) {
            logger.error("获取网银client失败", e);
        }finally {
            this.close();
        }
        return false;
    }

    /**
     * 建行B2C支付类对账文件下载
     * @discription
     * @author W.X
     * @created 2016年11月8日 下午7:59:29
     * @return
     */
    public boolean downloadCCBEbankAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadCCBEbankAccFile(date);
        } catch (TException e) {
            logger.error("获取网银client失败", e);
        }finally {
            this.close();
        }
        return false;
    }

    /**
     * 建行B2C退款类对账文件下载
     * @discription
     * @author W.X
     * @created 2016年11月8日 下午7:59:29
     * @return
     */
    public boolean downloadCCBEbankRefAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadCCBEbankRefAccFile(date);
        } catch (TException e) {
            logger.error("获取网银client失败", e);
        }finally {
            this.close();
        }
        return false;
    }

    /**
     * 兴业点芯对账文件下载
     * @param date
     * @return
     */
    public boolean downloadCIBWeChatAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadCIBWechatAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }
    /**
     * 兴业点芯退款对账文件下载
     * @param date
     * @return
     */
    public boolean downloadCIBWeChatRefundAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadCIBWechatRefundAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }
    /**
     * 邮储B2C网银支付对账文件下载
     * @param date
     * @return
     */
    public boolean downloadPSBCEbankAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadPSBCAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }
    /**
     * 邮储B2C网银退款对账文件下载
     * @param date
     * @return
     */
    public boolean downloadPSBCEbankRefundAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadPSBCRefundAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }

    /**
     * 农业银行对账文件下载
     * @param date
     * @return
     */
    public boolean downloadABCAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadABCEbankAccFile(date);
        } catch (TException e) {
            logger.error("获取网银client失败", e);
        }finally {
            this.close();
        }
        return false;
    }

    /**
     * 浦发银行对账文件下载
     * @param date
     * @return
     */
    public boolean downloadAPDBAccFile(String date,String settleFileType) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadSPDBEbankAccFile(date, settleFileType);
        } catch (TException e) {
            logger.error("获取网银client失败", e);
        }finally {
            this.close();
        }
        return false;
    }

    /**
     * 快钱对账文件下载
     * @param date
     * @return
     */
    public boolean downloadBILLAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadBILLAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }

    /**
     * 盛付通对账文件下载
     * @param date
     * @return
     */
    public boolean downloadShengpayAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadShengpayAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }

    /**
     * 邮储B2C网银支付对账文件下载
     * @param date
     * @return
     */
    public boolean downloadNBCBEbankAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadNBCBB2CAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }
    /**
     * 邮储B2C网银退款对账文件下载
     * @param date
     * @return
     */
    public boolean downloadNBCBEbankRefundAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadNBCBRefundAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }
    /**
     * 财付通B2C网银支付对账文件下载
     * @param date
     * @return
     */
    public boolean downloadTenpayEbankAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadTenpayB2CAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }
    /**
     * 汇付宝微信对账文件下载
     * @param date
     * @return
     */
    public boolean downloadHeepayWechatAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadHeepayWechatAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }
    /**
     * 汇付宝支付宝对账文件下载
     * @param date
     * @return
     */
    public boolean downloadHeepayAlipayAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadHeepayAlipayAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }
    /**
     * 汇付宝微信对账文件下载
     * @param date
     * @return
     */
    public boolean downloadHeepayWechatRefAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadHeepayWechatRefAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }
    /**
     * 汇付宝支付宝对账文件下载
     * @param date
     * @return
     */
    public boolean downloadHeepayAlipayRefAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadHeepayAlipayRefAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }
    /**
     * 财付通B2C网银退款对账文件下载
     * @param date
     * @return
     */
    public boolean downloadTenpayEbankRefundAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadTenpayRefundAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }
    /**
     * 统统付对账文件下载
     * @param date
     * @return
     */
    public boolean downloadSumpayAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadSumpayAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }

    /**
     * 宝付B2C网银对账文件下载
     * @param date
     * @return
     */
    public boolean downloadBaofuEbankAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadBaofuAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }

    /**
     * 工商B2C网银对账文件下载
     * @param date
     * @return
     */
    public boolean downloadICBCAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadICBCAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }

    /**
     * 工商B2C退款对账文件下载
     * @param date
     * @return
     */
    public boolean downloadICBCRefAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadICBCRefAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }


    /**
     * 银盛对账
     * @param date
     * @return
     */
    public boolean downloadysePayAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadysePayAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        } finally {
            this.close();
        }
        return false;
    }

    /**
     * 广发对账
     * @param date
     * @return
     */
    public boolean downloadCgbAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadCgbAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        } finally {
            this.close();
        }
        return false;
    }

    /**
     * 北京B2C网银对账文件下载
     * @param date
     * @return
     */
    public boolean downloadBOBEbankAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadBOBEbankAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }

    /**
     * 易联代扣对账文件下载
     * @param date
     * @return
     */
    public boolean downloadEasyLinkWithholdAccFile(String date){
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadEasyLinkWithholdAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }

    /**
     * 易联快捷对账文件下载
     * @param date
     * @return
     */
    public boolean downloadEasyLinkQuickPayAccFile(String date){
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadEasyLinkQuickPayAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }

    /**
     * 浦发快捷支付类对账文件下载
     * @param date
     * @return
     */
    public boolean downloadSPDBQuickAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadSPDBQuickAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }

    /**
     * 浦发快捷退款类对账文件下载
     * @param date
     * @return
     */
    public boolean downloadSPDBQuickRefAccFile(String date) {
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadSPDBQuickRefAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }

    /**
     * 翼支付代扣对账文件下载
     * @param date
     * @return
     */
    public boolean downloadBestPayWithholdAccFile(String date){
        try {
            TDownloadAccFileService.Client client = this.getClient();
            return client.downloadBestPayWithholdAccFile(date);
        } catch (TException e) {
            logger.error("获取对账client失败", e);
        }finally {
            this.close();
        }
        return false;
    }


    @Resource(name = "gatewayClient")
    private ThriftClientProxy clientProxy;

    @Override
    public ThriftClientProxy getClientProxy() {

        return clientProxy;
    }

    /**
     * 获取client
     *
     * @return
     */
    public TDownloadAccFileService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new TDownloadAccFileService.Client(ClientThreadLocal.getInstance()
                .getProtocol());

    }

    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICE_NAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(NODE_NAME);
    }


}
