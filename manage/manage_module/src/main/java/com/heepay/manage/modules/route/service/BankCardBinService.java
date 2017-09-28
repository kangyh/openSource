/**
 *  
 */
package com.heepay.manage.modules.route.service;

import java.util.List;

import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.JsonMapper;
import com.heepay.manage.modules.route.entity.BankCardBin;
import com.heepay.manage.modules.sys.utils.BusinessInfoUtils;
import com.heepay.redis.JedisClusterUtil;
import com.heepay.manage.modules.route.dao.BankCardBinDao;
import redis.clients.jedis.JedisCluster;

/**
 * 银行卡bin管理
 *
 * 名称：BatchPayReason.java
 *
 * 创建者： lgk
 * 创建时间：2016年8月23日
 * 创建描述：
 *
 * 修改者：李金徽
 * 修改时间：2016年8月24日
 * 修改描述：调整子类继承父类不用重复实现父类方法；缓存方法健壮性调整；调整映射的DAO.xml中sql问题
 *
 *
 * 审核者：李金徽
 * 审核时间：2016年8月24日
 * 审核描述：子类继承父类不用重复实现父类方法；缓存方法健壮性调整；调整映射的DAO.xml中sql问题
 *
 *
 */
@Service
@Transactional(readOnly = true)
public class BankCardBinService extends CrudService<BankCardBinDao, BankCardBin> {

    @Autowired
    private BankCardBinDao bankCardBinDao;
      
    /**     
    * @discription 缓存
    * @author lgk
    * @created 2016年9月9日 下午4:06:56          
    */
    @Transactional(readOnly = false)
    public void updateCache() {
        List<BankCardBin> bankCardBinList = bankCardBinDao.selectList();
        JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();
        if(!bankCardBinList.isEmpty()) {
            for (BankCardBin bankCardBin : bankCardBinList) {
                String bankCardBinJson = JsonMapper.toJsonString(bankCardBin);
                if ("5".equals(bankCardBin.getBankcardNoteLength())) {
                  jedisCluster.hset("bankCardBinCatch5", bankCardBin.getBankcardNote(), bankCardBinJson);
                } else if ("6".equals(bankCardBin.getBankcardNoteLength())) {
                  jedisCluster.hset("bankCardBinCatch6", bankCardBin.getBankcardNote(), bankCardBinJson);
                } else if ("7".equals(bankCardBin.getBankcardNoteLength())) {
                  jedisCluster.hset("bankCardBinCatch7", bankCardBin.getBankcardNote(), bankCardBinJson);
                } else if ("8".equals(bankCardBin.getBankcardNoteLength())) {
                  jedisCluster.hset("bankCardBinCatch8", bankCardBin.getBankcardNote(), bankCardBinJson);
                }
          }
        } 
    }

    /**
     * @discription 根据发卡行标识查询信息
     * @author L.M
     * @created 2016年9月7日 下午6:32:44
     * @param bankCardBin
     * @return
     */
    public BankCardBin selectBankCardNote(BankCardBin bankCardBin){
        return bankCardBinDao.selectBankCardNote(bankCardBin);
    }

    /**
     * @discription 银行卡bin启用、禁用状态转换
     * @author L.M
     * @created 2016年9月7日 下午6:34:14
     * @param bankCardBin
     */
    @Transactional(readOnly = false)
    public void status(BankCardBin bankCardBin) {
        User user = UserUtils.getUser();
        if (StringUtils.isNotBlank(user.getId())){
            bankCardBin.setUpdateBy(user);
        }
        bankCardBinDao.status(bankCardBin);
        BusinessInfoUtils.delBankList();
    }

}
