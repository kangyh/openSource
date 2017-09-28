/**
 *  
 */
package com.heepay.manage.modules.merchant.vo;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * merchantEntity
 * @author ly
 * @version V1.0
 */
public class MerchantRateLadder extends DataEntity<MerchantRateLadder> {
	
	private static final long serialVersionUID = 1L;
	private String chargeMax;		// 收费的上限额度，为空或0表示无上限
	private String chargeMin;		// 收费的下限额度，为空或0表示无下限
	private String ladderFee;		// 阶梯收费时的每笔固定费，多级时使用|分割各级费用
	private String ladderRatio;		// 阶梯收费时的按比率收取的费率，多级时使用|分割各级费率
	private String ladderType;		// 阶梯类型(固定，比例)
	
	public MerchantRateLadder() {
		super();
	}

	public MerchantRateLadder(String id){
		super(id);
	}

	@Length(min=0, max=100, message="收费的上限额度，为空或0表示无上限长度必须介于 0 和 100 之间")
	public String getChargeMax() {
		return chargeMax;
	}

	public void setChargeMax(String chargeMax) {
		this.chargeMax = chargeMax;
	}
	
	@Length(min=0, max=100, message="收费的下限额度，为空或0表示无下限长度必须介于 0 和 100 之间")
	public String getChargeMin() {
		return chargeMin;
	}

	public void setChargeMin(String chargeMin) {
		this.chargeMin = chargeMin;
	}
	
	@Length(min=0, max=100, message="阶梯收费时的每笔固定费，多级时使用|分割各级费用长度必须介于 0 和 100 之间")
	public String getLadderFee() {
		return ladderFee;
	}

	public void setLadderFee(String ladderFee) {
		this.ladderFee = ladderFee;
	}
	
	@Length(min=0, max=100, message="阶梯收费时的按比率收取的费率，多级时使用|分割各级费率长度必须介于 0 和 100 之间")
	public String getLadderRatio() {
		return ladderRatio;
	}

	public void setLadderRatio(String ladderRatio) {
		this.ladderRatio = ladderRatio;
	}
	
	@Length(min=0, max=6, message="阶梯类型(固定，比例)长度必须介于 0 和 6 之间")
	public String getLadderType() {
		return ladderType;
	}

	public void setLadderType(String ladderType) {
		this.ladderType = ladderType;
	}
	
}