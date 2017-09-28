package com.heepay.warning.dao;

import java.util.List;

import com.heepay.warning.entity.InfoMember;

public interface InfoMemberMapper {
   


    List<InfoMember> selectByGroupId(Integer groupId);

}