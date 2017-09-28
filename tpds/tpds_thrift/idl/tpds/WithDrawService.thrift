namespace java com.heepay.rpc.tpds.service
include "DepositMsgVO.thrift"
service WithDrawService {
	
	DepositMsgVO.DepositMsgVO depositWithDraw(1:string msg);
	DepositMsgVO.DepositMsgVO depositWithDrawQuery(1:string businessSeqNo);
}