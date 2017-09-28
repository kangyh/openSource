namespace java com.heepay.rpc.risk.model

struct MerchantProductModel {
	1:string id;
    2:string productCode;
    3:string productName;
    4:string productDescription;
    5:string status;
    6:string riskLevel;
    7:string remarks;
    8:string createUser;
    9:i64 createTime;
    10:string modifyUser;
}
