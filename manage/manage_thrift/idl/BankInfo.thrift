namespace java com.heepay.manage.rpc.service


struct BankInfoThrift {
    1:string bankNo,
	2:string bankName

}

service BankInfoService {
	list<BankInfoThrift> queryBankInfoList(),
	string queryBankInfo()

}
