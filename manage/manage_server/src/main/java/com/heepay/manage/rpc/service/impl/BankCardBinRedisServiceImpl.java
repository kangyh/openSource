package com.heepay.manage.rpc.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.manage.modules.route.dao.BankCardBinDao;
import com.heepay.manage.modules.route.entity.BankCardBin;
import com.heepay.manage.rpc.service.BankCardBinRedisService;
import com.heepay.redis.JedisClusterUtil;
import com.heepay.rpc.service.RpcService;

import redis.clients.jedis.JedisCluster;

@Service
@RpcService(name = "BankCardBinRedisServiceImpl", processor = BankCardBinRedisService.Processor.class)
public class BankCardBinRedisServiceImpl implements BankCardBinRedisService.Iface {

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private BankCardBinDao bankCardBinDao;

    // 传入卡号 查出银行信息
    @Override
    public String getNameOfBank(String cardNo) throws TException {
        logger.info("获取卡bin,卡号{}",cardNo);
        if (cardNo == null || cardNo.length() < 13 || cardNo.length() > 19) {
            return "";
        }

        // 6位Bin号
        String cardNoSix = cardNo.substring(0, 6);
        String binSix = intercept("6", cardNoSix);
        if (StringUtils.isNotBlank(binSix)) {
            return binSix;
        }

        // 5位Bin号
        String cardNoFive = cardNo.substring(0, 5);
        String binFive = intercept("5", cardNoFive);
        if (StringUtils.isNotBlank(binFive)) {
            return binFive;
        }

        // 8位Bin号
        String cardNoEight = cardNo.substring(0, 8);
        String binEight = intercept("8", cardNoEight);
        if (StringUtils.isNotBlank(binEight)) {
            return binEight;
        }

        // 7位的bin号
        String cardNoSeven = cardNo.substring(0, 7);
        String binSeven = intercept("7", cardNoSeven);
        if (StringUtils.isNotBlank(binSeven)) {
            return binSeven;
        }
        return "";
    }

    // 通过key 和发卡行标识 得到json串银行信息
    @Override
    public String intercept(String bankcardNoteLength, String bankcardNote) throws TException {
        // 查询Redis
        JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();
        String cardBinNo = "bankCardBinCatch" + bankcardNoteLength;
        String json = jedisCluster.hget(cardBinNo, bankcardNote);
        if (StringUtils.isBlank(json)) {
            BankCardBin bankCardBin = new BankCardBin();
            bankCardBin.setBankcardNote(bankcardNote);
            bankCardBin.setBankcardNoteLength(bankcardNoteLength);
            // 查数据库
            BankCardBin bankCard = bankCardBinDao.selectBnakCardBin(bankCardBin);
            if (null != bankCard) {
                String bankCardBinJson = new JsonMapperUtil().toJson(bankCard);
                jedisCluster.hset(cardBinNo, bankCardBin.getBankcardNote(), bankCardBinJson);
                logger.info("获取卡bin数据库返回{},发卡行标示{},bin的位数{}",bankCardBinJson,bankcardNote,bankcardNoteLength);
                return bankCardBinJson;
            }
        } else {
            logger.info("获取卡bin redis返回{},发卡行标示{},bin的位数{}",json,bankcardNote,bankcardNoteLength);
            return json;
        }
        return "";

    }

}
