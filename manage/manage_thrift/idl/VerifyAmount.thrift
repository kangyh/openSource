namespace java com.heepay.manage.rpc.service

struct VerifyAmountThrift{
	1:string merchantId,
	2:string bankCardNo,
	3:string openBankName,
	4:string createTime
}

service VerifyAmountService {
    string verifyAmount(1:string merchantId,2:string amount),
    string editStatus(1:string merchantId,2:string status,3:string amount),
   	VerifyAmountThrift getVerifyAmount(1:string merchantId)
}