package com.heepay.risk.dao;

import java.util.List;

import com.heepay.risk.entity.BlackorwhiteItemList;
import com.heepay.risk.entity.BlackorwhiteList;


public interface BlackorwhiteListMapper {
  

    int insert(BlackorwhiteList record);
    int update(BlackorwhiteList record);
    List<BlackorwhiteList> getlist();
    int delete(BlackorwhiteList record);
    BlackorwhiteList get (Integer blackId);
}