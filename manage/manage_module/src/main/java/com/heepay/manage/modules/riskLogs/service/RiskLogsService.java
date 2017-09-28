package com.heepay.manage.modules.riskLogs.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.common.util.IpUtil;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.riskLogs.dao.RiskLogsDao;
import com.heepay.manage.modules.riskLogs.entity.RiskLogs;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
 * 
 *
 * 描    述：用户操作日志表
 *
 * 创 建 者：   wangl
 * 创建时间：2016年11月29日 下午8:52:44
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
@Service
@Transactional(readOnly = true)
public class RiskLogsService extends CrudService<RiskLogsDao, RiskLogs> {

	@Autowired
	private RiskLogsDao riskLogsDao;
	
	/**
	 * 
	 * @方法说明：数据入库操作
	 * @时间：Dec 30, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int savaEntity(String operationType,String remark,HttpServletRequest request){
		
		RiskLogs riskLogs=new RiskLogs();
		riskLogs.setOperationType(operationType);//操作类型（增删改查）
		riskLogs.setUserId(UserUtils.getUser().getName());//用户id（登陆用户的id）
		riskLogs.setIp(IpUtil.getIpAddr(request));//设置当前登录的ip地址
		riskLogs.setUpdateUser(UserUtils.getUser().getName());//操作者
		riskLogs.setUpdateTime(new Date());//操作时间
		riskLogs.setRemark(remark);//操作类容
		
		
		int num = riskLogsDao.saveEntity(riskLogs);
		return num;
	}
	
	
	
	/**
	 * @方法说明：用户操作日志表
	 * @author wangl
	 * @时间：2016年11月18日17:27:53
	 */
	public List<RiskLogs> findList(RiskLogs riskLogs) {
		
		return super.findList(riskLogs);
	}
	
}
