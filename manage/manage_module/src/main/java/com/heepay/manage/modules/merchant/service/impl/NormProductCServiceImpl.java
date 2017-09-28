/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.service.impl;

import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.dao.NormProductDao;
import com.heepay.manage.modules.merchant.service.NormProductCService;
import com.heepay.manage.modules.merchant.vo.NormProduct;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;

/**
 *
 * 描    述：标准产品Service
 *
 * 创 建 者： @author ly
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
@Service
@Transactional(readOnly = true)
public class NormProductCServiceImpl extends CrudService<NormProductDao, NormProduct> implements NormProductCService {

    @Autowired
    private NormProductDao normProductDao;

    @Override
    public NormProduct selectExist(NormProduct normProduct){
        return normProductDao.selectExist(normProduct);
    }

    @Transactional(readOnly = false)
    public void save(NormProduct normProduct, boolean flag){
        if (StringUtils.isBlank(normProduct.getCode())) {
            String code = "BZ" + normProduct.getProductCode();
            normProduct.setCode(code);
        }
        super.save(normProduct,flag);
    }

}