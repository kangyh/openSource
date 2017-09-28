package com.heepay.manage.modules.pbc.web;

import java.io.Serializable;

/***
 * 
* 
* 描    述：用于上传时保存这个对象和子数据
*
* 创 建 者： wangl
* 创建时间：  Dec 22, 201610:48:12 AM
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

public class LoadEntity {

	public class  getLoad<T,E> implements Serializable {

		private static final long serialVersionUID = 4946173065449810591L;
		
		private E head;
		
		// 反馈表的数据
		private T body;
		// 关联的子数据
		
		// 关联的子数据下的子数据
		
		//private List<T> accountsList;

		public T getBody() {
			return body;
		}

		public void setBody(T body) {
			this.body = body;
		}

		public E getHead() {
			return head;
		}

		public void setHead(E head) {
			this.head = head;
		}


		/*public List<T> getAccountsList() {
			return accountsList;
		}

		public void setAccountsList(List<T> accountsList) {
			this.accountsList = accountsList;
		}*/

			
	}
}
