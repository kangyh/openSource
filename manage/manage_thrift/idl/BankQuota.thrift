namespace java com.heepay.manage.rpc.service

struct BankQuotaThrift {
    1:string bankId,
    2:string bankName,
    3:string bankCardType,
    4:string perlimitAmount,
    5:string daylimitAmount,
    6:string monlimitAmount
}

service BankQuotaService {
    BankQuotaThrift getBankQuota(1:string bankCardType,2:string bankId),
	list<BankQuotaThrift> getBankQuotaList(1:list<BankQuotaThrift> bankQuotaThrifts)
}
