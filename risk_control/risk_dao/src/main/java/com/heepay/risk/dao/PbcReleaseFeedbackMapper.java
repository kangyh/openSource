package com.heepay.risk.dao;

import com.heepay.risk.entity.PbcReleaseFeedback;

public interface PbcReleaseFeedbackMapper {
	
	void insert(PbcReleaseFeedback entity);
	PbcReleaseFeedback selectEntity(PbcReleaseFeedback entity);

}
