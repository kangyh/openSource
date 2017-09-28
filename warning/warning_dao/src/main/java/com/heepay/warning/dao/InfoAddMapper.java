package com.heepay.warning.dao;

import com.heepay.warning.entity.InfoAdd;

public interface InfoAddMapper {

    int insert(InfoAdd record);
    InfoAdd getByMsgId(Integer msgId);
     
}