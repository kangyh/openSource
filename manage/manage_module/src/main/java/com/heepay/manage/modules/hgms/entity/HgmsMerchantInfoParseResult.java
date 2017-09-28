/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.entity;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.HashMap;
import java.util.List;

/**
 * 描述：
 * <p>
 * 创建者: yangzd
 * 创建时间: 2017-01-03-19:51
 * 创建描述: 解析订单结果类
 * <p>
 * 审核者:
 * 审核时间:
 * 审核描述:
 * <p>
 * 修改者:
 * 修改时间:
 * 修改内容:
 */
public class HgmsMerchantInfoParseResult {
    /**
     * 解析结果, true 全部解析成功, false 存在解析失败
     */
    private boolean result = true;
    /**
     * 描述信息, 可为空
     */
    private String msg;
    /**
     * result为true时, 返回解析后订单列表
     */
    private List list;
    /**
     * result为false时, 显示错误描述
     */
    private List<String> errorList;
    /**
     * 商户订单号列表
     */
    private HashMap orderFormNoList;
    /**
     * 提交的xls是否超出规定的行数限制
     */
    private boolean overFlow;
    /**
     * 是否生成错误文件, 是 true  否 false
     */
    private boolean createErrorFile = false;


    /**
     * xls文件
     */
    private HSSFWorkbook hssfWorkbook;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    public HashMap getOrderFormNoList() {
        return orderFormNoList;
    }

    public void setOrderFormNoList(HashMap orderFormNoList) {
        this.orderFormNoList = orderFormNoList;
    }

    public HSSFWorkbook getHssfWorkbook() {
        return hssfWorkbook;
    }

    public void setHssfWorkbook(HSSFWorkbook hssfWorkbook) {
        this.hssfWorkbook = hssfWorkbook;
    }

    public boolean isOverFlow() {
        return overFlow;
    }

    public void setOverFlow(boolean overFlow) {
        this.overFlow = overFlow;
    }

    public boolean isCreateErrorFile() {
        return createErrorFile;
    }

    public void setCreateErrorFile(boolean createErrorFile) {
        this.createErrorFile = createErrorFile;
    }
}
