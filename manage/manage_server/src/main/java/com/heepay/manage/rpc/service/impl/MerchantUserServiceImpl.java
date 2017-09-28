package com.heepay.manage.rpc.service.impl;

import com.heepay.common.util.DateUtil;
import com.heepay.enums.AllowSystemType;
import com.heepay.enums.EmployeeStatus;
import com.heepay.enums.MerchantStatus;
import com.heepay.enums.UserType;
import com.heepay.manage.modules.hgms.dao.HgmsMerchantEmployeeDao;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantEmployee;
import com.heepay.manage.modules.merchant.dao.*;
import com.heepay.manage.modules.merchant.service.MerchantUserCService;
import com.heepay.manage.modules.merchant.vo.*;
import com.heepay.manage.modules.sys.dao.UserDao;
import com.heepay.manage.rpc.service.MerchantUserService;
import com.heepay.manage.rpc.service.MerchantUserThrift;
import com.heepay.rpc.service.RpcService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 名称：商户注册服务实现
 * <p/>
 * 创建者  B.HJ
 * 创建时间 2016-08-12 16:44
 * 创建描述：商户注册服务实现
 * <p>
 * 修 改 者：guozx@9186.com
 * 修改时间：2017-08-09 11:37:54
 * 修改描述：添加汇聚财员工注册成功后修改汇聚财员工状态，170行开始
 */
@Component
@RpcService(name = "merchantUserServiceImpl", processor = MerchantUserService.Processor.class)
public class MerchantUserServiceImpl implements MerchantUserService.Iface {

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private MerchantUserDao merchantUserDao;
    @Autowired
    private MerchantDao merchantDao;
    @Autowired
    private MerchantUserCService merchantUserCServiceImpl;
    @Autowired
    private MerchantOperationLogDao merchantOperationLogDao;
    @Autowired
    private MerchantEmployeeOpLogDao merchantEmployeeOpLogDao;
    @Autowired
    private MerchantEmployeeDao merchantEmployeeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private HgmsMerchantEmployeeDao hgmsMerchantEmployeeDao;

    @Override
    public MerchantUserThrift get(String arg0) throws TException {
        MerchantUser merchantUser = merchantUserDao.get(arg0);
        MerchantUserThrift user = new MerchantUserThrift();
        if(null != merchantUser){
            if(null != merchantUser.getLastLoginDate()){
                user.setLastLoginDate(DateUtil.dateToString(merchantUser.getLastLoginDate()));
            }
            user.setUserType(merchantUser.getUserType());
            user.setStatus(merchantUser.getStatus());
            user.setLoginName(merchantUser.getLoginName());
            //允许登录的系统,来源
            user.setAllowSystem(merchantUser.getAllowSystem());
            user.setSource(merchantUser.getSource());
            user.setMerchantFlag(merchantUser.getMerchantFlag());
            user.setCompanyName(merchantUser.getCompanyName());
            user.setInchargerId(userDao.get(merchantUser.getInchargerId()) == null ?
                    "":userDao.get(merchantUser.getInchargerId()).getName());
        }
        return user;
    }

    @Override
    public MerchantUserThrift save(MerchantUserThrift merchantUserThrift) throws TException {
        //定义一个返回对象，一个数据库存储对象
        MerchantUser merchantUser = new MerchantUser();
        //把属性copy到存储对象内
        try {
            BeanUtils.copyProperties(merchantUser, merchantUserThrift);
        } catch (Exception e) {
            logger.error("保存新用户时copy属性出错！{}来自{}",e,merchantUserThrift.getSource());
            return merchantUserThrift;
        }
        //用户状态
        merchantUser.setStatus(MerchantStatus.NORMAL.getValue());
        merchantUser.setCreateTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
        merchantUser.setLastLoginDate(DateUtil.stringToDate(DateUtil.getTodayYYYYMMDD_HHMMSS()));
        //如果是ngp_web系统的用户
        if(AllowSystemType.NGP_WEB.getValue().equals(merchantUserThrift.getSource())){
            try {
                //用户类型
                merchantUser.setUserType(UserType.USER.getValue());
                merchantUserDao.insert(merchantUser);
                //行为日志
                MerchantOperationLog merchantOperationLog =  new MerchantOperationLog();
                merchantOperationLog.setMerchantId(merchantUser.getId());
                merchantOperationLog.setOperateFunction("商户注册");
                merchantOperationLog.setOperateBehavior("注册账号");
                merchantOperationLog.setCurrentIp(merchantUser.getLastLoginIp());//IP
                merchantOperationLog.setOperateDate(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
                merchantOperationLogDao.insert(merchantOperationLog);
            } catch (Exception e) {
                logger.error("属性copy异常。", e);
            }
        }else{
            //其他系统的
            merchantUser.setUserType(UserType.MERCHANT.getValue());
            merchantUserDao.insert(merchantUser);
        }
        logger.info("{}系统注册了一个新用户{}",merchantUserThrift.getSource(),merchantUserThrift.toString());
        //回显使用
        merchantUserThrift.setId(merchantUser.getId());
        merchantUserThrift.setUserType(merchantUser.getUserType());
        return merchantUserThrift;
    }

    @Override
    public MerchantUserThrift update(MerchantUserThrift mrchantUserThrift) throws TException {
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setId(mrchantUserThrift.getId());
        merchantUser.setLastLoginIp(mrchantUserThrift.getLastLoginIp());
        merchantUser.setLastLoginDate(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
        merchantUserDao.update(merchantUser);
        //行为日志
        MerchantOperationLog merchantOperationLog =  new MerchantOperationLog();

        merchantOperationLog.setMerchantId(mrchantUserThrift.getId());
        merchantOperationLog.setOperateFunction("登录");
        merchantOperationLog.setOperateBehavior("登录");
        merchantOperationLog.setCurrentIp(mrchantUserThrift.getLastLoginIp());//IP
        merchantOperationLog.setOperateDate(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
        merchantOperationLogDao.insert(merchantOperationLog);
        return new MerchantUserThrift();
    }

    @Override
    public MerchantUserThrift employeeRegist(MerchantUserThrift mrchantUserThrift) throws TException {
        MerchantUser merchantUser = new MerchantUser();
        //根据邮箱查询修改，以后根据id
        MerchantUser merchantUser1 = merchantUserDao.queryEmailExist(mrchantUserThrift.getLoginName());
        merchantUser.setId(merchantUser1.getId());
        merchantUser.setLoginPassword(mrchantUserThrift.getLoginPassword());
        merchantUser.setPayPassword(mrchantUserThrift.getPayPassword());
        merchantUser.setCreateTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
        merchantUser.setLastLoginDate(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
        merchantUser.setSecretQuestion(mrchantUserThrift.getSecretQuestion());
        merchantUser.setAllowSystem(mrchantUserThrift.getAllowSystem());
        merchantUser.setSource(mrchantUserThrift.getSource());
        merchantUser.setAnswerSecretQuestion(mrchantUserThrift.getAnswerSecretQuestion());
        //行为日志，员工注册成功
        MerchantEmployeeOpLog merchantEmployeeOpLog = new MerchantEmployeeOpLog();
        merchantEmployeeOpLog.setEmployeeId(merchantUser1.getId());
        merchantEmployeeOpLog.setOpFunction("注册");
        merchantEmployeeOpLog.setOpAction("激活账号");
        merchantEmployeeOpLog.setCreateDate(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
        merchantEmployeeOpLogDao.insert(merchantEmployeeOpLog);
        merchantUserDao.update(merchantUser);
        //修改员工表的状态值
        if (AllowSystemType.NGP_WEB.getValue().equals(mrchantUserThrift.getSource())) {
            MerchantEmployee merchantEmployee = merchantEmployeeDao.queryEmployeeByEmail(merchantUser1.getLoginName());
            merchantEmployee.setStatus(EmployeeStatus.ENABLE.getValue());
            merchantEmployee.setUpdateTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
            merchantEmployeeDao.updateByEmployeeId(merchantEmployee);
        }
        //添加汇聚财的员工注册成功后修改员工状态
        if (AllowSystemType.HGMS.getValue().equals(mrchantUserThrift.getSource())) {
            HgmsMerchantEmployee hgmsMerchantEmployee = hgmsMerchantEmployeeDao.queryEmployeeByEmail(merchantUser1.getLoginName());
            hgmsMerchantEmployee.setStatus(EmployeeStatus.ENABLE.getValue());
            hgmsMerchantEmployee.setUpdateTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
            hgmsMerchantEmployeeDao.updateByEmployeeId(hgmsMerchantEmployee);
        }
        return new MerchantUserThrift();
    }

    @Override
    public List<MerchantUserThrift> findList(MerchantUserThrift merchantUserThrift) throws TException {
        return null;
    }

    @Override
    public MerchantUserThrift queryEmailExist(String email) throws TException {
        MerchantUser merchantUser = merchantUserDao.queryEmailExist(email);
        MerchantUserThrift user = new MerchantUserThrift();
        if(null == merchantUser){
            return user;
        }
        Merchant merchant = merchantDao.get(merchantUser.getId());
        user.setLoginName(merchantUser.getLoginName());
        user.setId(merchantUser.getId());
        user.setLoginPassword(merchantUser.getLoginPassword());
        user.setPayPassword(merchantUser.getPayPassword());
        user.setMobile(merchantUser.getMobile());
        user.setUserType(merchantUser.getUserType());
        user.setStatus(merchantUser.getStatus());
        user.setLastLoginIp(merchantUser.getLastLoginIp());
        user.setLoginIpsAllowed(merchantUser.getLoginIpsAllowed());
        //上一次登录的cpu,disk,mac,version信息
        user.setCpuInfo(merchantUser.getCpuInfo());
        user.setDiskInfo(merchantUser.getDiskInfo());
        user.setMacInfo(merchantUser.getMacInfo());
        //user.setPassGuardCtrlVersion(merchantUser.getPassGuardCtrlVersion());
        //注册的系统，允许登陆的系统
        user.setSource(merchantUser.getSource());
        user.setAllowSystem(merchantUser.getAllowSystem());
        //用户是否进行了认证
        if(null != merchant && null != merchant.getUserId()){
            user.setCompanyName(merchant.getCompanyName() != null ? merchant.getCompanyName():"");
        }else{
            user.setCompanyName("");
        }
        user.setLastLoginDate(DateUtil.dateToString(merchantUser.getLastLoginDate()));
        return user;
    }

    @Override
    public MerchantUserThrift getCertificationStatus(MerchantUserThrift arg0) throws TException {
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setLoginName(arg0.getLoginName());
        List<MerchantUser> list = merchantUserDao.findList(merchantUser);
        MerchantUser user = list.get(0);
        if(UserType.USER.getValue().equals(user.getUserType())){
            user.setUserType(UserType.MERCHANT.getValue());
        }
        MerchantUser merchantUser1 = merchantUserCServiceImpl.setInfoAuthStatus(user);
        MerchantUserThrift merchantUserThrift = new MerchantUserThrift();
        merchantUserThrift.setInfoAuthStatus(merchantUser1.getInfoAuthStatus());
        return merchantUserThrift;
    }

    @Override
    public MerchantUserThrift resetPayPassword(MerchantUserThrift merchantUserThrift) throws TException {
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setPayPassword(merchantUserThrift.getPayPassword());
        merchantUser.setLoginName(merchantUserThrift.getLoginName());
        merchantUserDao.resetPayPassword(merchantUser);
        //行为日志
        MerchantOperationLog merchantOperationLog =  new MerchantOperationLog();
        merchantOperationLog.setMerchantId(merchantUser.getId());
        merchantOperationLog.setOperateFunction("修改支付密码");
        merchantOperationLog.setOperateBehavior("账号为"+merchantUserThrift.getLoginName()+"通过"+merchantUserThrift.getModifyWay()+"找回密码。");
        merchantOperationLog.setOperateDate(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
        merchantOperationLogDao.insert(merchantOperationLog);
        return merchantUserThrift;
    }

    @Override
    public MerchantUserThrift resetLoginPassword(MerchantUserThrift merchantUserThrift) throws TException {
        MerchantUser merchantUser = new MerchantUser();
        merchantUser.setLoginName(merchantUserThrift.getLoginName());
        merchantUser.setLoginPassword(merchantUserThrift.getLoginPassword());
        merchantUserDao.resetLoginPassword(merchantUser);
        //行为日志
        MerchantOperationLog merchantOperationLog =  new MerchantOperationLog();
        merchantOperationLog.setMerchantId(merchantUser.getId());
        merchantOperationLog.setOperateFunction("修改登录密码");
        merchantOperationLog.setOperateBehavior("账号为"+merchantUserThrift.getLoginName()+"通过客服找回密码。");
        merchantOperationLog.setOperateDate(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
        merchantOperationLogDao.insert(merchantOperationLog);
        return merchantUserThrift;
    }

    @Override
    public MerchantUserThrift queryPhoneNo(String arg0) throws TException {
      return null;
    }

    @Override
    public MerchantUserThrift changIpMsg(MerchantUserThrift merchantUserThrift) throws TException {
        MerchantUser user = new MerchantUser();
        user.setId(merchantUserThrift.getId());

        user.setLastLoginIp(merchantUserThrift.getLastLoginIp());

        user.setCpuInfo(merchantUserThrift.getCpuInfo());
        user.setDiskInfo(merchantUserThrift.getDiskInfo());
        user.setMacInfo(merchantUserThrift.getMacInfo());
        merchantUserDao.update(user);
        return merchantUserThrift;
    }

}
