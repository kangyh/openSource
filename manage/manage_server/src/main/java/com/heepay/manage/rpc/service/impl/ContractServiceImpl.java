package com.heepay.manage.rpc.service.impl;

import com.heepay.common.util.DateUtil;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.modules.contract.dao.ContractDao;
import com.heepay.manage.modules.contract.entity.Contract;
import com.heepay.manage.modules.contract.entity.ContractInfo;
import com.heepay.manage.modules.contract.service.ContractInfoService;
import com.heepay.manage.modules.sys.dao.DictDao;
import com.heepay.manage.modules.sys.entity.Dict;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.rpc.utils.GenerateContract;
import com.heepay.manage.rpc.service.ContractService;
import com.heepay.manage.rpc.service.ContractThrift;
import com.heepay.manage.rpc.utils.FastDFSUtils;
import com.heepay.rpc.service.RpcService;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@RpcService(name = "contractServiceImpl" , processor = ContractService.Processor.class)
public class ContractServiceImpl implements ContractService.Iface{

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ContractInfoService contractInfoService;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private DictDao dictDao;

    @Override
    public ContractThrift getContract(String contractId, String companyName, String address, String linkMan,
                         String phone, String linkManB, String phoneB) throws TException {
        try {
            ContractThrift contractThrift = new ContractThrift();
            //获取合同信息
            Contract contract = contractDao.selectByContractCode(contractId);
            Dict dict = dictDao.getLabelByValue(Constants.BY_COMPANY, contract.getByCompany());
            if(null != dict){
                contractThrift.setCompany(dict.getLabel());
            }else{
                contractThrift.setCompany("");
            }
            //构建模版参数map
            Map<String,Object> data = new HashMap<>();
            data.put("companyName",companyName);
            data.put("address",address);
            data.put("linkMan",linkMan);
            data.put("phone",phone);
            data.put("linkManB",linkManB);
            data.put("phoneB",phoneB);
            data.put("date", DateUtil.getTodayYYYYMMDD());
            //生成合同
            Map<String, String> generate = GenerateContract.generate(data,contractId);
            //上传FastDFS
            File file = new File(generate.get("path"));
            InputStream is = new FileInputStream(file);
            String pic = FastDFSUtils.uploadPic(IOUtils.toByteArray(is), file.getName(), file.length());
            logger.info("fastDFS合同地址{}",pic);
            contractThrift.setPath(RandomUtil.getFastDfs(pic));
            contractThrift.setContent(generate.get("content"));
            //保存生成记录
            ContractInfo contractInfo = new ContractInfo();
            contractInfo.setContractId(contractId);
            contractInfo.setCompanyName(companyName);
            contractInfo.setAddress(address);
            contractInfo.setLinkMan(linkMan);
            contractInfo.setPhone(phone);
            contractInfo.setLinkManB(linkManB);
            contractInfo.setPhoneB(phoneB);
            contractInfo.setPath(pic);
            contractInfoService.save(contractInfo,false);
            return contractThrift;
        } catch (Exception e) {
            logger.error("生成合同失败:{}",e);
            return new ContractThrift();
        }
    }
}
