/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.subject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.subject.entity.Subject;
import com.heepay.redis.JedisClusterUtil;

import redis.clients.jedis.JedisCluster;

import com.heepay.manage.modules.subject.dao.SubjectDao;

/**
 *
 * 描    述：科目Service
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
@Service
@Transactional(readOnly = true)
public class SubjectService extends CrudService<SubjectDao, Subject> {

  @Autowired
  private SubjectDao subjectDao;
  
  private JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();
  
  private JsonMapperUtil jsonMapper = new JsonMapperUtil();
  
  private static final String KEY = "PAYMENT_SUBJECT_ALL";
  
  public Subject get(String id) {
    return super.get(id);
  }

  public List<Subject> findList(Subject subject) {
    String cacheStr = jedisCluster.get(KEY);
    List<Subject> subjectList = Lists.newArrayList();
    if(StringUtils.isEmpty(cacheStr)){
      subjectList = super.findList(subject);
      jedisCluster.set(KEY, jsonMapper.toJson(subjectList));
    }else{
      subjectList = jsonMapper.fromJson(cacheStr, jsonMapper.createCollectionType(ArrayList.class, Subject.class));
    }
    return subjectList;
  }

  public Page<Subject> findPage(Page<Subject> page, Subject subject) {
    return super.findPage(page, subject);
  }

  @Transactional(readOnly = false)
  public void save(Subject subject) {
    jedisCluster.del(KEY);
    super.save(subject, false);
  }

  @Transactional(readOnly = false)
  public void delete(Subject subject) {
    jedisCluster.del(KEY);
    super.delete(subject);
  }
  
  public List<Map<String, Object>> getParentSubjects(Map<String, Object> paramsMap) {
    return subjectDao.getParentSubjects(paramsMap);
  }
  
  @Transactional(readOnly = false)
  public int updateSubjects(Subject subject){
    jedisCluster.del(KEY);
    return subjectDao.updateSubjects(subject);
  }
  
  public List<Subject> getChildSubjects(Map<String, Object> paramsMap){
    return subjectDao.getChildSubjects(paramsMap);
  }
  
  @Transactional(readOnly = false)
  public void deleteChildSubjects(Long parentSubjectId){
    jedisCluster.del(KEY);
    subjectDao.deleteChildSubjects(parentSubjectId);
  }
  
  @Transactional(readOnly = false)
  public int updateChildSubjects(Long parentSubjectId){
    //清除缓存
    jedisCluster.del(KEY);
    return subjectDao.updateChildSubjects(parentSubjectId);
  }
  
  public Subject getByAccountCode(Map<String, Object> paramsMap) {
    return subjectDao.getByAccountCode(paramsMap);
  }
	
}