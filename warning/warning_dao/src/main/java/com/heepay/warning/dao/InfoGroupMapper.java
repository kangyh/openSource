package com.heepay.warning.dao;

import com.heepay.warning.entity.InfoGroup;
import java.util.List;


public interface InfoGroupMapper {
   

    List<InfoGroup> selectAll();
    InfoGroup getByGroupCode(String groupCode);
}