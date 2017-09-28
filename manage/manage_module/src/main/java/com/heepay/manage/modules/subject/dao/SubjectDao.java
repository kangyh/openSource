/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.subject.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.subject.entity.Subject;

/**
 *
 * 描    述：科目DAO接口
 *
 * 创 建 者： @author 王亚洪
 * 创建时间：
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
@MyBatisDao
public interface SubjectDao extends CrudDao<Subject> {
	
  /**
   *
  * @description 获取上级科目
  * @author 王亚洪       
  * @created 2016年12月28日 下午3:21:36     
  * @param paramsMap
  * @return
   */
  public List<Map<String, Object>> getParentSubjects(Map<String, Object> paramsMap);
  
  /**
   * 
  * @description 更新科目
  * @author 王亚洪       
  * @created 2016年12月28日 下午7:23:00     
  * @param subject
  * @return
   */
  public int updateSubjects(Subject subject);
  
  /**
   * 
  * @description 获取该科目下的子科目
  * @author 王亚洪       
  * @created 2016年12月29日 下午2:34:16     
  * @param paramsMap
  * @return
   */
  public List<Subject> getChildSubjects(Map<String, Object> paramsMap);
  
  /**
   * 
  * @description 删除子科目
  * @author 王亚洪       
  * @created 2016年12月29日 下午3:58:13     
  * @param parentSubjectId
   */
  public void deleteChildSubjects(Long parentSubjectId);
  
  
  /**
   * 
  * @description 更新子科目
  * @author 王亚洪       
  * @created 2016年12月29日 下午3:58:13     
  * @param parentSubjectId
   */
  public int updateChildSubjects(Long parentSubjectId);
  
  /**
   * 
  * @description 通过科目号获取科目信息
  * @author 王亚洪       
  * @created 2017年1月11日 下午4:23:47     
  * @param paramsMap
  * @return
   */
  public Subject getByAccountCode(Map<String, Object> paramsMap);
  
}