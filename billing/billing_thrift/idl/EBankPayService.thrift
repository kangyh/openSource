namespace java com.heepay.rpc.payment.service
include "EBankPayModel.thrift"

service EBankPayService {
  	
	EBankPayModel.EBankPayRes ebankPay(1:EBankPayModel.EBankPayReq eBankPayReq)
	
}