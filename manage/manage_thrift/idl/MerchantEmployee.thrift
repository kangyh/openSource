include "MerchantUser.thrift"
namespace java com.heepay.manage.rpc.service

struct MerchantEmployeeThrift {
        1:string employeeId,
        2:string userId,
        3:string name,
        4:string level,
        5:string status,
        6:string type,
        7:string updatetime,
        8:string merchantId,
        9:string pageNo,
        10:string pageSize,
        11:string remarks,
        12:string totalCount,
        13:string email,
        14:string phone,
        15:string permission
}

service MerchantEmployeeService {
        MerchantEmployeeThrift queryEmployeeByEmailOrMobile(1:string emailOrMobile,2:string bossId),
        MerchantEmployeeThrift save(1:MerchantEmployeeThrift merchantEmployeeThrift,2:MerchantUser.MerchantUserThrift merchantUserThrift),
        MerchantEmployeeThrift query(1:string id),
        MerchantEmployeeThrift deleteEmployee(1:string id,2:string currentUserId),
        string queryBossIdByEmployeeId(1:string id),
        i32 selectCount(1:string userId),
        bool isMobileExisted(1:string employeeId,2:string newMobile),
        MerchantEmployeeThrift updateEmployee(1:MerchantEmployeeThrift merchantEmployeeThrift,2:MerchantUser.MerchantUserThrift merchantUserThrift),
        list<MerchantEmployeeThrift> findList(1:MerchantEmployeeThrift merchantEmployeeThrift)
}