package com.heepay.risk.vo;
/***
 * 
* 
* 描    述：反欺诈发送邮件类
*
* 创 建 者： dongzhengjiang
* 创建时间：  2017.01.06
* 创建描述：
* 
* 修 改 者：  
* 修改时间： 
* 修改描述： 
* 
* 审 核 者：
* 审核时间：
* 审核描述：
*
 */
public class FraudMailModel {
	/**
	 * 接受者可以多个以逗号分隔
	*/
   private String mailTo;
   /**
	 * 邮件主题
   */
   private String subject;
   /**
	 * 邮件内容
   */
   private String body;
public String getMailTo() {
	return mailTo;
}
public void setMailTo(String mailTo) {
	this.mailTo = mailTo;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public String getBody() {
	return body;
}
public void setBody(String body) {
	this.body = body;
}

}
