include "MerchantUser.thrift"
namespace java com.heepay.manage.rpc.service

struct HgmsMerchantEmployeeThrift {
    /*员工ID*/
    1:string employeeId,
    /*用户ID*/
    2:string userId,
    /*员工姓名*/
    3:string name,
    /*等级*/
    4:string level,
    /*商户状态*/
    5:string status,
    /*类型*/
    6:string type,
    /*更新时间*/
    7:string updatetime,
    /*商户ID*/
    8:string merchantId,
    /* 页码 */
    9:string pageNo,
    10:string pageSize,
    /*备注*/
    11:string remarks,
    12:string totalCount,
    /*邮箱*/
    13:string email,
    /*手机*/
    14:string phone,
    /* 权限*/
    15:string permission
}

service HgmsMerchantEmployeeService {
        HgmsMerchantEmployeeThrift queryEmployeeByEmailOrMobile(1:string emailOrMobile,2:string bossId),
        HgmsMerchantEmployeeThrift save(1:HgmsMerchantEmployeeThrift hgmsMerchantEmployeeThrift,2:MerchantUser.MerchantUserThrift merchantUserThrift),
        HgmsMerchantEmployeeThrift query(1:string id),
        HgmsMerchantEmployeeThrift deleteEmployee(1:string id,2:string currentUserId),
        string queryBossIdByEmployeeId(1:string id),
        i32 selectCount(1:string userId),
        bool isMobileExisted(1:string employeeId,2:string newMobile),
        HgmsMerchantEmployeeThrift updateEmployee(1:HgmsMerchantEmployeeThrift hgmsMerchantEmployeeThrift,2:MerchantUser.MerchantUserThrift merchantUserThrift),
        list<HgmsMerchantEmployeeThrift> findList(1:HgmsMerchantEmployeeThrift hgmsMerchantEmployeeThrift)
}