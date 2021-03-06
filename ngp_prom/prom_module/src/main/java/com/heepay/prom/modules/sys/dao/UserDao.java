/**
 *  
 */
package com.heepay.prom.modules.sys.dao;

import java.util.List;

import com.heepay.prom.common.persistence.CrudDao;
import com.heepay.prom.common.persistence.annotation.MyBatisDao;
import com.heepay.prom.modules.sys.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户DAO接口
 *  
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
	
	  
	/**     
	* @discription 更新用户验证数据
	* @author ly     
	* @created 2016年12月16日 下午4:31:23     
	* @param user
	* @return     
	*/
	public int updateVerifyInfo(User user);

	/**
	 * 修改用户登录状态
	 * ly 2017年4月17日18:16:20
	 * @return
	 */
	public int updateUserLoginFlag(@Param("loginName")String loginName,@Param("loginFlag")String loginFlag);

}
