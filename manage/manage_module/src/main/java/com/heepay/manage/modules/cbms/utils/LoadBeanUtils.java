package com.heepay.manage.modules.cbms.utils;
import com.heepay.manage.modules.cbms.entity.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import static com.heepay.manage.modules.cbms.utils.MathUtils.addBigDecimal;
/**
 * Created by 牛俊鹏 on 2017/2/6.
 */
public class LoadBeanUtils {

    /**
     * @param cbmsOrderSum
     * @param date
     * @return
     * @discription 将传入数据转换为CbmsCustomorderSum对象
     * @author 牛俊鹏
     * @created 2017年1月24日
     */
    public static CbmsCustomorderSum gettfcbmsOrderSum(CbmsOrderSum cbmsOrderSum,  String customcode, Date date, String declarationBatchHumber,List<CbmsOrderform> arrayList){
        BigDecimal decimal = new BigDecimal(0);
        BigDecimal decimalaount = new BigDecimal(0);//手续费
        int declarationNumber =0;
        //计算对应海关的手续费和申报金额
        for (CbmsOrderform  cbmsOrderform:arrayList) {
            if (customcode.equals(cbmsOrderform.getCustomCode())) {
                decimal=addBigDecimal(cbmsOrderform.getRmbPayAmount(),decimal);
                decimalaount=addBigDecimal(cbmsOrderform.getChargeAmount(),decimalaount);
                declarationNumber++;
            }
        }
        CbmsCustomorderSum cbmsCustomorderSum = new CbmsCustomorderSum();
        cbmsCustomorderSum.setMerchantNo(cbmsOrderSum.getMerchantNo());
        cbmsCustomorderSum.setCbmsCompanyName(cbmsOrderSum.getCbmsCompanyName());
        cbmsCustomorderSum.setImportBatchNumber(cbmsOrderSum.getImportBatchNumber());
        cbmsCustomorderSum.setReLoadNumber(cbmsOrderSum.getReLoadNumber());
        cbmsCustomorderSum.setNewNumber(cbmsOrderSum.getNewNumber());
        cbmsCustomorderSum.setRecAmount(cbmsOrderSum.getRecAmount());
        cbmsCustomorderSum.setImportTime(cbmsOrderSum.getImportTime());
        cbmsCustomorderSum.setDeclarationNumber(String.valueOf(declarationNumber));
        cbmsCustomorderSum.setDeclarationMoney(decimal.toString());
        cbmsCustomorderSum.setFee(decimalaount.toString());
        cbmsCustomorderSum.setCustomCode(customcode);
        cbmsCustomorderSum.setDeclarationBatchHumber(declarationBatchHumber);//申报批次号
        cbmsCustomorderSum.setDeclarationTime(date);//申报时间
        cbmsCustomorderSum.setStatus("1");//待申报
        cbmsCustomorderSum.setDeclareType(cbmsOrderSum.getDeclareType());//申报方式
        cbmsCustomorderSum.setMerchantBatchNo(cbmsOrderSum.getMerchantBatchNo());//商户批次号
        cbmsCustomorderSum.setNotifyUrl(cbmsOrderSum.getNotifyUrl());//异步通知地址
        return cbmsCustomorderSum;
    }
}
