package com.heepay.tpds.dao;

import com.heepay.tpds.entity.TpdsBindInterface;
import com.heepay.tpds.entity.TpdsBindInterfaceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TpdsBindInterfaceMapper {
    
    int insert(TpdsBindInterface record);
    int update(TpdsBindInterface record);
}