package com.heepay.manage.rpc.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.AllowSystemType;
import com.heepay.enums.EmployeeStatus;
import com.heepay.enums.MerchantStatus;
import com.heepay.enums.UserType;
import com.heepay.manage.modules.hgms.dao.HgmsMerchantEmployeeDao;
import com.heepay.manage.modules.hgms.dao.HgmsMerchantEmployeeFunctionsDao;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantEmployee;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantEmployeeFunctions;
import com.heepay.manage.modules.merchant.dao.MerchantEmployeeOpLogDao;
import com.heepay.manage.modules.merchant.dao.MerchantOperationLogDao;
import com.heepay.manage.modules.merchant.dao.MerchantUserDao;
import com.heepay.manage.modules.merchant.vo.MerchantEmployeeOpLog;
import com.heepay.manage.modules.merchant.vo.MerchantOperationLog;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.rpc.service.HgmsMerchantEmployeeService;
import com.heepay.manage.rpc.service.HgmsMerchantEmployeeThrift;
import com.heepay.manage.rpc.service.MerchantUserThrift;
import com.heepay.rpc.service.RpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描    述：商户员工表
 * <p>
 * 创 建 者： guozx@9186.com
 * 创建时间： 2017-08-07 09:45:30
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
@RpcService(name = "hgmsMerchantEmployeeServiceImpl", processor = HgmsMerchantEmployeeService.Processor.class)
public class HgmsMerchantEmployeeServiceImpl implements HgmsMerchantEmployeeService.Iface {

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private MerchantUserDao merchantUserDao;
    @Autowired
    private MerchantUserDao merchantDao;
    @Autowired
    private HgmsMerchantEmployeeFunctionsDao merchantEmployeeFunctionsDao;
    @Autowired
    private HgmsMerchantEmployeeDao merchantEmployeeDao;
    @Autowired
    private MerchantOperationLogDao merchantOperationLogDao;
    @Autowired
    private MerchantEmployeeOpLogDao merchantEmployeeOpLogDao;

    @Override
    public HgmsMerchantEmployeeThrift queryEmployeeByEmailOrMobile(String emailOrMobile, String bossId) throws TException {
        HgmsMerchantEmployeeThrift merchantEmployeeThrift = new HgmsMerchantEmployeeThrift();
        //添加时查询使用
        if (null == bossId) {
            //是邮箱
            if (emailOrMobile.contains("@")) {
                List<MerchantUser> list = merchantUserDao.queryEmployeeByEmail(emailOrMobile);
                if (null != list && list.size() > 0) {
                    merchantEmployeeThrift.setEmployeeId(list.get(0).getId());
                }
            } else {
                //是手机
                List<MerchantUser> list = merchantUserDao.queryEmployeeByMobile(emailOrMobile);
                if (null != list && list.size() > 0) {
                    merchantEmployeeThrift.setEmployeeId(list.get(0).getId());
                }
            }
            return merchantEmployeeThrift;
        } else {
            //查询时展示使用
            //查询当前的email或者mobile是否是当前bossId下的小弟所拥有
            List<MerchantUser> list = merchantUserDao.queryEmployeeByEmail(emailOrMobile);
            List<MerchantUser> listMobile = merchantUserDao.queryEmployeeByMobile(emailOrMobile);
            if (listMobile.size() > 0 || list.size() > 0) {
                //如果emailOrMobile存在，拿到userId
                MerchantUser merchantUser = null;
                if (listMobile.size() > list.size()) {
                    merchantUser = listMobile.get(0);
                } else if (listMobile.size() < list.size()) {
                    merchantUser = list.get(0);
                }
                logger.info("商户id为{}，使用了是否存在员工功能，条件:{}", bossId, emailOrMobile);
                //身份不是员工，直接返回空对象
                if (!UserType.EMPLOYEE.getValue().equals(merchantUser.getUserType())) {
                    return merchantEmployeeThrift;
                }
                //拿到根据条件查到的对应的user表内的id    100001
                String userId = merchantUser.getId();
                HgmsMerchantEmployee merchantEmployee = merchantEmployeeDao.queryBossIdByEmployeeId(Integer.valueOf(userId));
                String merchantId = String.valueOf(merchantEmployee.getMerchantId());
                //查这个userId是否在bossId下
                if (bossId.equals(merchantId)) {
                    merchantEmployeeThrift.setUserId(userId);
                    merchantEmployeeThrift.setEmail(merchantEmployee.getEmail());
                    merchantEmployeeThrift.setPhone(merchantEmployee.getMobile());
                    merchantEmployeeThrift.setRemarks(merchantEmployee.getRemarks());
                    merchantEmployeeThrift.setUpdatetime(DateUtil.dateToString(merchantEmployee.getUpdateTime()));
                    merchantEmployeeThrift.setStatus(EmployeeStatus.labelOf(merchantEmployee.getStatus()));
                }
            }
            return merchantEmployeeThrift;
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public HgmsMerchantEmployeeThrift save(HgmsMerchantEmployeeThrift merchantEmployeeThrift, MerchantUserThrift merchantUserThrift) throws TException {
        logger.info("保存员工开始，员工数据：{}，用户登录数据{}", merchantEmployeeThrift.toString(), merchantUserThrift.toString());
        MerchantUser user = new MerchantUser();
        user.setLoginName(merchantUserThrift.getLoginName());
        user.setStatus(MerchantStatus.NORMAL.getValue());
        user.setMobile(merchantUserThrift.getMobile());
        user.setUserType(UserType.EMPLOYEE.getValue());
        user.setSource(AllowSystemType.HGMS.getValue());
        user.setAllowSystem(AllowSystemType.HGMS.getValue() + "," + AllowSystemType.NGP_WEB.getValue());
        //保存用户信息
        merchantUserDao.insert(user);
        //保存到员工表
        HgmsMerchantEmployee merchantEmployee = new HgmsMerchantEmployee();
        String userType = merchantUserDao.judgmentIdentity(Integer.valueOf(merchantUserThrift.getId()));
        //当前是商户
        if (userType.equals(UserType.MERCHANT.getValue())) {
            merchantEmployee.setMerchantId(merchantUserThrift.getId());
            //记录行为日志
            MerchantOperationLog merchantOperationLog = new MerchantOperationLog();
            merchantOperationLog.setEmployeeId(user.getId());
            merchantOperationLog.setMerchantId(merchantUserThrift.getId());
            merchantOperationLog.setOperateFunction("增加员工");
            merchantOperationLog.setOperateBehavior("增加员工");
            merchantOperationLog.setOperateDate(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
            merchantOperationLog.setUpdateDate(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
            merchantOperationLogDao.insert(merchantOperationLog);
            logger.info("商户id为{}的添加了一条员工信息。员工的id为{}", merchantUserThrift.getId(), user.getId());
            //当前是员工
        } else if (userType.equals(UserType.EMPLOYEE.getValue())) {
            String bossId = queryBossIdByEmployeeId(merchantUserThrift.getId());
            merchantEmployee.setMerchantId(bossId);
            //员工操作行为日志
            MerchantEmployeeOpLog merchantEmployeeOpLog = new MerchantEmployeeOpLog();
            merchantEmployeeOpLog.setMerchantId(bossId);
            merchantEmployeeOpLog.setEmployeeId(user.getId());
            merchantEmployeeOpLog.setOpFunction("增加员工");
            merchantEmployeeOpLog.setOpAction("id为" + merchantUserThrift.getId() + "的员工增加了新员工，id为：" + user.getId());
            merchantEmployeeOpLog.setCreateDate(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
            merchantEmployeeOpLog.setUpdateDate(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
            merchantEmployeeOpLogDao.insert(merchantEmployeeOpLog);
            logger.info("员工id为{}的添加了一条员工信息。新员工的id为{}", merchantUserThrift.getId(), user.getId());
        }
        merchantEmployee.setUserId(user.getId());
        merchantEmployee.setEmail(merchantUserThrift.getLoginName());
        merchantEmployee.setMobile(merchantUserThrift.getMobile());
        merchantEmployee.setRemarks(merchantUserThrift.getRemarks());
        merchantEmployee.setCreateTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
        merchantEmployee.setUpdateTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
        merchantEmployee.setStatus(EmployeeStatus.NONACTIVATED.getValue());
        merchantEmployeeDao.insert(merchantEmployee);
        //保存权限信息
        String permission = merchantUserThrift.getPermission();
        String[] functions = permission.split(",");
        String merchantId = merchantUserThrift.getId();
        HgmsMerchantEmployeeFunctions merchantEmployeeFunctions = new HgmsMerchantEmployeeFunctions();
        merchantEmployeeFunctions.setMerchantId(Long.valueOf(merchantId));
        merchantEmployeeFunctions.setEmployeeId(Long.valueOf(user.getId()));
        for (String function : functions) {
            merchantEmployeeFunctions.setFunctionId(Long.valueOf(function));
            merchantEmployeeFunctionsDao.insert(merchantEmployeeFunctions);
        }
        logger.info("保存员工结束，员工数据：{}，用户登录数据{}", merchantEmployeeThrift.toString(), merchantUserThrift.toString());
        return merchantEmployeeThrift;
    }

    @Override
    public HgmsMerchantEmployeeThrift query(String id) throws TException {
        MerchantUser merchantUser = merchantUserDao.get(id);
        HgmsMerchantEmployee merchantEmployee = merchantEmployeeDao.queryEmployeeByEmail(merchantUser.getLoginName());
        HgmsMerchantEmployeeThrift employee = new HgmsMerchantEmployeeThrift();
        employee.setMerchantId(merchantEmployee.getMerchantId());
        employee.setEmail(merchantEmployee.getEmail());
        employee.setPhone(merchantEmployee.getMobile());
        employee.setRemarks(merchantEmployee.getRemarks());
        //时间
        employee.setUpdatetime(DateUtil.dateToString(merchantEmployee.getUpdateTime()));
        //状态
        employee.setStatus(EmployeeStatus.labelOf(merchantEmployee.getStatus()));
        //权限
        List<String> permissions = merchantEmployeeFunctionsDao.queryPermissionByEmployeeId(Long.valueOf(id));
        String permission = StringUtils.join(permissions.toArray(), ",");
        employee.setPermission(permission);
        return employee;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public HgmsMerchantEmployeeThrift deleteEmployee(String id, String currentUserId) throws TException {
        logger.info("删除员工开始，用户ID：{}，删除员工ID{}", currentUserId, id);
        String userType = merchantUserDao.judgmentIdentity(Integer.valueOf(currentUserId));
        //根据当前的userId查出bossId,如果是商户，currentUserId就是id，如果不是商户，则执行queryBossIdByEmployeeId(currentUserId)
        String bossIdd = userType.equals(UserType.MERCHANT.getValue()) ? currentUserId : queryBossIdByEmployeeId(currentUserId);
        String toBeDelBossId = queryBossIdByEmployeeId(id);
        //如果要删除的员工id不是当前boss的。不删除
        if (!bossIdd.equals(toBeDelBossId)) {
            return new HgmsMerchantEmployeeThrift();
        }
        //删除权限
        merchantEmployeeFunctionsDao.deleteByEmployeeId(Long.valueOf(id));
        //删除员工
        MerchantUser user = new MerchantUser();
        user.setId(id);
        merchantUserDao.delete(user);
        HgmsMerchantEmployee e = new HgmsMerchantEmployee();
        e.setUserId(id);
        //删除关系表内的
        merchantEmployeeDao.delete(e);
        //当前是商户
        if (userType.equals(UserType.MERCHANT.getValue())) {
            //记录行为日志
            MerchantOperationLog merchantOperationLog = new MerchantOperationLog();
            merchantOperationLog.setMerchantId(bossIdd);
            merchantOperationLog.setEmployeeId(id);
            merchantOperationLog.setOperateFunction("删除员工");
            merchantOperationLog.setOperateBehavior("删除员工");
            merchantOperationLog.setOperateDate(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
            merchantOperationLogDao.insert(merchantOperationLog);
            //当前是员工
        } else if (userType.equals(UserType.EMPLOYEE.getValue())) {
            //员工操作行为日志
            MerchantEmployeeOpLog merchantEmployeeOpLog = new MerchantEmployeeOpLog();
            merchantEmployeeOpLog.setMerchantId(bossIdd);
            merchantEmployeeOpLog.setEmployeeId(currentUserId);
            merchantEmployeeOpLog.setOpFunction("删除员工");
            merchantEmployeeOpLog.setOpAction("id为" + currentUserId + "的员工增删除了id为：" + id + "的员工。");
            merchantEmployeeOpLog.setCreateDate(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
            merchantEmployeeOpLogDao.insert(merchantEmployeeOpLog);
        }
        logger.info("删除员工结束，商户ID：{}，删除员工ID{}", bossIdd, id);
        return new HgmsMerchantEmployeeThrift();
    }

    @Override
    public String queryBossIdByEmployeeId(String employeeId) throws TException {
        HgmsMerchantEmployee merchantEmployee = merchantEmployeeDao.queryBossIdByEmployeeId(Integer.valueOf(employeeId));
        return merchantEmployee.getMerchantId();
    }

    @Override
    public int selectCount(String userId) throws TException {
        String bossId;
        //根据当前用户查询商户id
        MerchantUser merchantUser = merchantDao.get(userId);
        //当前用户不是商户
        if (null == merchantUser || merchantUser.getUserType().equals(UserType.EMPLOYEE.getValue()) ||
                merchantUser.getUserType().equals(UserType.USER.getValue())) {
            bossId = queryBossIdByEmployeeId(userId);
            //当前用户是商户
        } else {
            bossId = userId;
        }
        return merchantEmployeeDao.selectCount(Integer.valueOf(bossId));
    }

    @Override
    public boolean isMobileExisted(String employeeId, String newMobile) throws TException {
        MerchantUser merchantUser = merchantUserDao.get(employeeId);
        if (merchantUser.getMobile() != null && merchantUser.getMobile().equals(newMobile)) {
            return false;
        } else {
            List<MerchantUser> merchantUsers = merchantUserDao.queryEmployeeByMobile(newMobile);
            if (null != merchantUsers && merchantUsers.size() > 0) {
                return true;
            }
        }
        return false;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public HgmsMerchantEmployeeThrift updateEmployee(HgmsMerchantEmployeeThrift merchantEmployeeThrift, MerchantUserThrift merchantUserThrift) throws TException {
        //先查询出bossId，再根据employeeId删除全部旧权限
        String merchantId = queryBossIdByEmployeeId(merchantUserThrift.getId());
        merchantEmployeeFunctionsDao.deleteByEmployeeId(Long.valueOf(merchantUserThrift.getId()));
        //插入新权限
        String permission = merchantUserThrift.getPermission();
        String[] functions = permission.split(",");
        HgmsMerchantEmployeeFunctions merchantEmployeeFunctions = new HgmsMerchantEmployeeFunctions();
        merchantEmployeeFunctions.setMerchantId(Long.valueOf(merchantId));
        merchantEmployeeFunctions.setEmployeeId(Long.valueOf(merchantUserThrift.getId()));
        for (String function : functions) {
            merchantEmployeeFunctions.setFunctionId(Long.valueOf(function));
            merchantEmployeeFunctionsDao.insert(merchantEmployeeFunctions);
        }
        //修改员工信息
        HgmsMerchantEmployee merchantEmployee = new HgmsMerchantEmployee();
        String id = merchantUserThrift.getId();
        MerchantUser merchantUser = merchantUserDao.get(id);
        HgmsMerchantEmployee merchantEmployee1 = merchantEmployeeDao.queryEmployeeByEmail(merchantUser.getLoginName());
        merchantEmployee.setEmployeeId(merchantEmployee1.getEmployeeId());
        merchantEmployee.setMobile(merchantUserThrift.getMobile());
        merchantEmployee.setRemarks(merchantUserThrift.getRemarks());
        merchantEmployee.setUpdateTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
        merchantEmployeeDao.updateEmployeeMsg(merchantEmployee);
        //修改用户信息中的手机号
        merchantUser.setMobile(merchantUserThrift.getMobile());
        merchantUserDao.updateEmployee(merchantUser);
        //记录行为日志
        String userType = merchantUserDao.judgmentIdentity(Integer.valueOf(merchantEmployeeThrift.getUserId()));
        //当前是商户
        if (userType.equals(UserType.MERCHANT.getValue())) {
            MerchantOperationLog merchantOperationLog = new MerchantOperationLog();
            merchantOperationLog.setMerchantId(merchantEmployeeThrift.getUserId());
            merchantOperationLog.setEmployeeId(merchantUserThrift.getId());
            merchantOperationLog.setOperateFunction("更新员工信息");
            merchantOperationLog.setOperateBehavior("更新员工信息");
            //String newData = om.writeValueAsString();
            //String oldData = om.writeValueAsString();
            //merchantOperationLog.setOperateNewData();
            //merchantOperationLog.setOperateNewData();
            merchantOperationLog.setOperateDate(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
            merchantOperationLogDao.insert(merchantOperationLog);
            //当前是员工
        } else if (userType.equals(UserType.EMPLOYEE.getValue())) {
            String bossId = queryBossIdByEmployeeId(merchantEmployeeThrift.getUserId());
            //员工操作行为日志
            MerchantEmployeeOpLog merchantEmployeeOpLog = new MerchantEmployeeOpLog();
            merchantEmployeeOpLog.setMerchantId(bossId);
            merchantEmployeeOpLog.setEmployeeId(merchantUserThrift.getId());
            merchantEmployeeOpLog.setOpFunction("更新员工信息");
            merchantEmployeeOpLog.setOpAction("id为" + merchantEmployeeThrift.getUserId() + "的员工更新了id为：" + merchantUserThrift.getId() + "的员工。");
            merchantEmployeeOpLog.setCreateDate(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
            merchantEmployeeOpLogDao.insert(merchantEmployeeOpLog);
        }
        return merchantEmployeeThrift;
    }

    @Override
    public List<HgmsMerchantEmployeeThrift> findList(HgmsMerchantEmployeeThrift merchantEmployeeThrift) throws TException {
        //查询商户id
        String bossId;
        //根据当前用户查询商户id
        MerchantUser merchant = merchantDao.get(merchantEmployeeThrift.getMerchantId());
        logger.info("用户{}查询了员工列表。", merchantEmployeeThrift.getMerchantId());
        //当前用户不是商户
        if (null == merchant || merchant.getUserType().equals(UserType.EMPLOYEE.getValue())) {
            bossId = queryBossIdByEmployeeId(merchantEmployeeThrift.getMerchantId());
            merchantEmployeeDao.selectCount(Integer.valueOf(bossId));
            //当前用户是商户
        } else {
            bossId = merchantEmployeeThrift.getMerchantId();
        }
        //bossId,merchantEmployeeThrift.getPageNo(),merchantEmployeeThrift.getPageSize()
        //通过商户id和页码，页数查询出相应的list
        Integer pageNo = Integer.valueOf(merchantEmployeeThrift.getPageNo());
        Integer pageSize = Integer.valueOf(merchantEmployeeThrift.getPageSize());
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", bossId);
        params.put("start", (pageNo - 1) * pageSize);
        params.put("pageSize", pageNo * pageSize);
        List<HgmsMerchantEmployee> list = merchantEmployeeDao.pagingQuery(params);
        List<HgmsMerchantEmployeeThrift> listThrift = new ArrayList<>();
        for (HgmsMerchantEmployee employee : list) {
            HgmsMerchantEmployeeThrift met = new HgmsMerchantEmployeeThrift();
            met.setUserId(employee.getUserId());
            met.setEmail(employee.getEmail());
            met.setPhone(employee.getMobile());
            met.setRemarks(employee.getRemarks());
            met.setStatus(EmployeeStatus.labelOf(employee.getStatus()));
            met.setUpdatetime(DateUtil.dateToString(employee.getUpdateTime()));
            listThrift.add(met);
        }
        return listThrift;
    }
}
