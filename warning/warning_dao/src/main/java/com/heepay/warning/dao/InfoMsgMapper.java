package com.heepay.warning.dao;

import com.heepay.warning.entity.InfoMsg;
import java.util.List;

public interface InfoMsgMapper {
   

    int insert(InfoMsg record);
    List<InfoMsg> getList();
    int update(InfoMsg record);
    InfoMsg getBodyByMsgId(Integer msgId);
   
}