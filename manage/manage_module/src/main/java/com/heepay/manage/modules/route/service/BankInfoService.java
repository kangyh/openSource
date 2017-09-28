/**
 *  
 */
package com.heepay.manage.modules.route.service;

import com.google.common.collect.Maps;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.CacheUtils;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.common.utils.JsonMapper;
import com.heepay.manage.modules.route.dao.BankInfoDao;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.entity.PayChannel;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.BusinessInfoUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.redis.JedisClusterUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.util.List;
import java.util.Map;

/**
 * 名称：银行信息操作类
 * 创建者：刘萌
 * 创建日期：2016-8-10
 * 创建描述：银行信息的查看、添加、修改、查询、更新缓存功能
 *
 * 审核者：于亮
 * 审核时间：2016年9月1日
 * 审核描述：父类有的方法没有改动可以直接调用；代码缩进规范；更新缓存的逻辑多余；去掉事务；updateCache注释；
 * 
 * 修改者： 马振
 * 修改时间： 2016-9-7
 * 修改描述：更新缓存的逻辑多余；去掉事务；updateCache注释
 * 
 * 
 * 修改者： 刘萌 
 * 修改时间： 2016-9-7
 * 修改描述：父类有的方法没有改动可以直接调用；代码缩进
 */

@Service
@Transactional(readOnly = true)
public class BankInfoService extends CrudService<BankInfoDao, BankInfo> {

  	@Autowired
  	private BankInfoDao bankInfoDao;
  	
  	  
  	/**     
  	* @discription 根据银行代码查询银行信息
  	* @author L.M
  	* @created 2016年9月7日 下午6:32:44     
  	* @param bankInfo
  	* @return     
  	*/
  	public List<BankInfo> selectByBankN(BankInfo bankInfo){
		List<BankInfo> bankInfos = bankInfoDao.selectByBankN(bankInfo);
		return bankInfos;
  	}
  	  
  	/** 
  	* @discription  保存银行信息
  	* @author L.M
  	* @created 2016年9月7日 下午6:33:25      
  	* @param bankInfo     
  	* @see com.heepay.manage.common.service.CrudService#save(com.heepay.manage.common.persistence.DataEntity)     
  	*/  
  	    
  	@SuppressWarnings("unchecked")
  	@Transactional(readOnly = false)
  	public void save(BankInfo bankInfo) {		
		super.save(bankInfo,false);
		//将所有银行信息放入缓存，下拉框标签使用
		Map<String, BankInfo> bankInfoMap = (Map<String, BankInfo>)CacheUtils.get(BusinessInfoUtils.CACHE_BUSINESS_MAP);
		if (null==bankInfoMap){
			bankInfoMap = Maps.newHashMap();
			for (BankInfo bankInfoInner : bankInfoDao.selectList()){
				bankInfoMap.put(bankInfoInner.getBankNo(), bankInfoInner);
			}
		}else{
			bankInfoMap.put(bankInfo.getBankNo(), bankInfo);
		}
		CacheUtils.put(BusinessInfoUtils.CACHE_BUSINESS_MAP, bankInfoMap);

        //将新添加上的银行名称添加至bankInfoCatch缓存，银行代码为key
		Map<String,String> bankMap = Maps.newHashMap();
		for (Map.Entry<String, BankInfo> entry : bankInfoMap.entrySet()) {
			bankMap.put(entry.getKey(), entry.getValue().getBankName());
		}
		String string = JsonMapper.toJsonString(bankMap);
		JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();
		jedisCluster.set(Constants.BANK_INFO_KEY, string);

  	}


	/**
	 * @方法说明：查询多个名称
	 * @时间： 2017-09-12 14:22
	 * @创建人：wangl
	 */
	public  List<BankInfo> getBankNameForList(List<String> list){

		return  bankInfoDao.getBankNameForList(list);
	}

	/**
	 * @discription 查询所有的银行信息
	 * @author M.Z
	 * @created 2016年10月20日11:33:34
	 */
	public List<BankInfo> selectList(){
		List<BankInfo> bankInfos = bankInfoDao.selectList();
		return bankInfos;
	}
  	  
  	/**     
  	* @discription 更新银行信息缓存
  	* @author M.Z
  	* @created 2016年9月7日 下午6:46:17          
  	*/
  	public void updateCache(){
        Map<String,String> bankMap = Maps.newHashMap();
		List<BankInfo> bankInfos = bankInfoDao.selectList();
		if(null!=bankInfos && !bankInfos.isEmpty()){
			for(BankInfo bankInfo:bankInfos){
				bankMap.put(bankInfo.getBankNo(), bankInfo.getBankName());
			}
			String string = JsonMapper.toJsonString(bankMap);
			JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();
			jedisCluster.set(Constants.BANK_INFO_KEY, string);
		}
  	}
  	
      
    /**     
    * @discription 银行启用、禁用状态转换
    * @author lm       
    * @created 2016年9月7日 下午6:34:14     
    * @param bankInfo     
    */
  	@Transactional(readOnly = false)
    public void status(BankInfo bankInfo) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			bankInfo.setUpdateBy(user);
		}
		bankInfoDao.status(bankInfo);
		BusinessInfoUtils.delBankList();
    }

  	/**
  	 * 
  	 * @discription: 银行代码查询银行名称
  	 * @created：Nov 9, 2016
  	 * @author：wangl
  	 */
	public BankInfo getBankNameByBankNo(String bankNo) {
		BankInfo bankInfo=bankInfoDao.getBankNameByBankNo(bankNo);
		return bankInfo;
	}

	/**
	 * 根据 银行名称查询银行代码
	 *  ly 2017年3月10日16:35:36
	 */
	public BankInfo getBankByBankName(String bankName){
		return bankInfoDao.getBankByBankName(bankName);
	}
  
}