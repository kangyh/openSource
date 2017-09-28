package com.heepay.risk.vo;



public class BankFraudQueryVO {
	private  BankFraudHeaderQueryVO head;
	private BankFraudBodyQueryVO body;

	public BankFraudHeaderQueryVO getHead() {
		return head;
	}

	public void setHead(BankFraudHeaderQueryVO head) {
		this.head = head;
	}

	public BankFraudBodyQueryVO getBody() {
		return body;
	}

	public void setBody(BankFraudBodyQueryVO body) {
		this.body = body;
	}
}
