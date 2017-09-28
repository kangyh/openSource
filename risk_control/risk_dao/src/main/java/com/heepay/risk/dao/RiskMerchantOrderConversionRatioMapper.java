package com.heepay.risk.dao;

import com.heepay.risk.entity.RiskMerchantOrderConversionRatio;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RiskMerchantOrderConversionRatioMapper {

    int insert(RiskMerchantOrderConversionRatio record);

}