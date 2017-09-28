package com.heepay.risk.dao;

import com.heepay.risk.entity.PbcFeedBack;

public interface PbcFeedBackMapper {
     void insert(PbcFeedBack entity);
     PbcFeedBack GetFeedBack(String applicationCode);
}
