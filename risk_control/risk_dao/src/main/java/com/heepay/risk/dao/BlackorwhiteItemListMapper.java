package com.heepay.risk.dao;

import java.util.List;

import com.heepay.risk.entity.BlackorwhiteItemList;
import com.heepay.risk.entity.BlackorwhiteList;


public interface BlackorwhiteItemListMapper {
    

    int insert(BlackorwhiteItemList record);
    int update(BlackorwhiteItemList record);
    List<BlackorwhiteItemList> getlist();
    int delete(BlackorwhiteItemList record);
    int deleteByBlackId(BlackorwhiteItemList record);
    List<BlackorwhiteItemList> selectByBlackId(BlackorwhiteList record);
    BlackorwhiteItemList get(Integer blackItemId);
}