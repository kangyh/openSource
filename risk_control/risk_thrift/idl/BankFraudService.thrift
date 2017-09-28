namespace java com.heepay.rpc.risk.service

include "risk/AsyncMsgVO.thrift"

service BankFraudService {
 	 AsyncMsgVO.AsyncMsgVO GetTransDetailList(1:string jsonStr);
 	 AsyncMsgVO.AsyncMsgVO GetAccountSubjectDetailList(1:string jsonStr);
 	 AsyncMsgVO.AsyncMsgVO GetAccountDynamic(1:string jsonStr);
 	 AsyncMsgVO.AsyncMsgVO GetAccountDynamicRelease(1:string jsonStr);
 	 AsyncMsgVO.AsyncMsgVO GetBankCardInfoByTransSerialNo(1:string jsonStr);
 	 AsyncMsgVO.AsyncMsgVO GetPaymentAccountList(1:string jsonStr);
}