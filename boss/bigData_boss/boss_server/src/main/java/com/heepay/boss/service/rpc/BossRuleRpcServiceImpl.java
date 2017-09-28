package com.heepay.boss.service.rpc;

import com.heepay.boss.dao.BossRuleMapper;
import com.heepay.boss.entity.BossRule;
import com.heepay.boss.entity.vo.BossRuleModel;
import com.heepay.boss.service.redis.impl.RedisOperator;
import com.heepay.boss.util.Constants;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.redis.JedisClusterUtil;
import com.heepay.rpc.boss.service.BossRuleRpcService;
import com.heepay.rpc.service.RpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 描 述：
 * <p>
 * 创 建 者：dongzhengjiang
 * 创建时间：2017/6/21 18:06
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Component
@RpcService(name="BossRuleRpcServiceImpl",processor=BossRuleRpcService.Processor.class)
public class BossRuleRpcServiceImpl implements com.heepay.rpc.boss.service.BossRuleRpcService.Iface {

    private static final Logger logger = LogManager.getLogger();

    @PostConstruct
    private void initRuleCache(){
        refreshBossRule();
    }


    @Autowired
    private BossRuleMapper bossRuleMapper;

    @Override
    public String addBossRule(String arg0) throws TException {
        logger.info("添加boss规则:{}",arg0);
        BossRuleModel vo= JsonMapperUtil.nonEmptyMapper().fromJson(arg0,BossRuleModel.class);
        BossRule entity=new BossRule();
        entity.setCreatAuthor(vo.getCreatAuthor());
        entity.setCreateTime(vo.getCreateTime()==null?null: new Date(Long.valueOf(vo.getCreateTime())));
        entity.setEndDate(vo.getEndDate()==null?null: new Date(Long.valueOf(vo.getEndDate())));
        entity.setUpdateTime(vo.getUpdateTime()==null?null: new Date(Long.valueOf(vo.getUpdateTime())));
        entity.setStartDate(vo.getStartDate()==null?null:new Date(Long.valueOf(vo.getStartDate())));
        entity.setJobStartTime(vo.getJobStartTime()==null?null:new Date(Long.valueOf(vo.getJobStartTime())));
        entity.setJobEndTime(vo.getJobEndTime()==null?null:new Date(Long.valueOf(vo.getJobEndTime())));
        entity.setUpdateAuthor(vo.getUpdateAuthor());
        entity.setJobStatus(vo.getJobStatus());
        entity.setTakeTime(vo.getTakeTime());
        entity.setRemark(vo.getRemark());
        int result=bossRuleMapper.insert(entity);
        if(result>0)
        {
            JedisClusterUtil.getJedisCluster().set(Constants.getBossRuleKey(String.valueOf(entity.getRuleId()),vo.getJobStatus()),JsonMapperUtil.nonEmptyMapper().toJson(entity));
        }
        return String.valueOf(result);
    }
    @Override
    public String editBossRule(String arg0) throws TException {
        logger.info("编辑boss规则:{}",arg0);
        BossRuleModel vo=JsonMapperUtil.nonEmptyMapper().fromJson(arg0,BossRuleModel.class);
        BossRule temp=bossRuleMapper.selectByPrimaryKey(vo.getRuleId());
        if(temp!=null)
        {
            JedisClusterUtil.getJedisCluster().del(Constants.getBossRuleKey(String.valueOf(temp.getRuleId()),temp.getJobStatus()));
        }
        BossRule entity=new BossRule();
        entity.setCreatAuthor(vo.getCreatAuthor());
        entity.setCreateTime(vo.getCreateTime()==null?null: new Date(Long.valueOf(vo.getCreateTime())));
        entity.setEndDate(vo.getEndDate()==null?null: new Date(Long.valueOf(vo.getEndDate())));
        entity.setUpdateTime(vo.getUpdateTime()==null?null: new Date(Long.valueOf(vo.getUpdateTime())));
        entity.setStartDate(vo.getStartDate()==null?null:new Date(Long.valueOf(vo.getStartDate())));
        entity.setJobStartTime(vo.getJobStartTime()==null?null:new Date(Long.valueOf(vo.getJobStartTime())));
        entity.setJobEndTime(vo.getJobEndTime()==null?null:new Date(Long.valueOf(vo.getJobEndTime())));
        entity.setUpdateAuthor(vo.getUpdateAuthor());
        entity.setJobStatus(vo.getJobStatus());
        entity.setTakeTime(vo.getTakeTime());
        entity.setRemark(vo.getRemark());
        entity.setRuleId(vo.getRuleId());
        int result=bossRuleMapper.update(entity);
        if(result>0)
        {
            JedisClusterUtil.getJedisCluster().set(Constants.getBossRuleKey(String.valueOf(vo.getRuleId()),vo.getJobStatus()==null?temp.getJobStatus():vo.getJobStatus()),arg0);
        }
        return String.valueOf(result);
    }
    @Override
    public String getBossRule(String arg0) throws TException {
        RedisOperator redisOperator = new RedisOperator();
        redisOperator.setJedisCluster( JedisClusterUtil.getJedisCluster());
        Set<String> keys=redisOperator.keys("RISK_BOSS_RULE*");
        for (String merchantkey:keys)
        {
            if(merchantkey.indexOf(arg0)>-1)
                return JedisClusterUtil.getJedisCluster().get(merchantkey);
        }
        return "";
    }
    private void refreshBossRule()
    {
        RedisOperator redisOperator = new RedisOperator();
        redisOperator.setJedisCluster( JedisClusterUtil.getJedisCluster());
        Set<String> keys=redisOperator.keys("RISK_BOSS_RULE*");
        List<BossRule> list=bossRuleMapper.getlist();
        for (String merchantkey:keys)
        {
            logger.info("删除RISK_BOSS_RULE:"+merchantkey);
            JedisClusterUtil.getJedisCluster().del(merchantkey);
        }

        if(list!=null)
        {
            for(BossRule entity:list)
            {
                logger.info("添加RISK_BOSS_RULE:"+Constants.getBossRuleKey(String.valueOf(entity.getRuleId()),entity.getJobStatus()));
                JedisClusterUtil.getJedisCluster().set(Constants.getBossRuleKey(String.valueOf(entity.getRuleId()),entity.getJobStatus()), JsonMapperUtil.nonEmptyMapper().toJson(entity));
            }
        }
    }
}
