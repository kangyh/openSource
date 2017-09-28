namespace java com.heepay.manage.rpc.service


struct HgmsMerchantEmployeeFunctionsThrift {
        1:i64 merchantId,
        2:i64 employeeId,
        3:i64 functionId
}

service HgmsMerchantEmployeeFunctionsService {
        HgmsMerchantEmployeeFunctionsThrift get(1:string arg0),
        string save(1:HgmsMerchantEmployeeFunctionsThrift arg0),
        list<string> findList(1:HgmsMerchantEmployeeFunctionsThrift arg0)
}