package com.heepay.risk.enums;
/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月3日 下午2:21:41
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
public enum SettleResponseStatus {
	SUCCESS("S00000", "交易处理成功"),
	DATABASE_ERROR("F00001", "数据库连接异常"),
	PARAM_ERROR("BX0001", "数据库连接异常");
	
     String  _value; 
	
	/**
	 * 存放内容
	 */
	String _Content;
	
	/**
	 * 私有构造函数
	 * @param value 枚举值
	 * @param content 缓存内容
	 * @return 
	 */
	SettleResponseStatus(String value, String content) {  
		this._value = value;  
		this._Content = content;  
	}  
	/**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public String getValue() {
      return this._value;
    }
    
    /**
     * 取得内容
     * @return 内容
     */
    public String getContent() {
      return this._Content;
    }

}

 