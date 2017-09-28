/**
 * 
 */
package com.heepay.risk.service;

import com.heepay.risk.entity.RiskIpbase;

/**
 * @author Administrator
 *
 */
public interface IpQueryService {
	
	public boolean checkIfChineseIp(String ip) ;
	public RiskIpbase getIpInfo(String ip) ;

}
