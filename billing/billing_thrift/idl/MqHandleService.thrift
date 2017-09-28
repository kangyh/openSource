namespace java com.heepay.rpc.payment.service

include "MqHandleModel.thrift"

//消息系统业务处理
service MqHandleService {
 //充值
 i32 mqUpdateChargeRecord(1:MqHandleModel.MqHandleModel mqHandleModel);
 //交易
 i32 mqUpdateGatewayRecord(1:MqHandleModel.MqHandleModel mqHandleModel);
 //退款
 i32 mqUpdateRefundRecord(1:MqHandleModel.MqHandleModel mqHandleModel);
 //代扣
 i32 mqUpdateBatchCollectionRecord(1:MqHandleModel.MqHandleModel mqHandleModel);
 //转账，批付
 i32 mqUpdateBatchPayRecord(1:MqHandleModel.MqHandleModel mqHandleModel);
 //提现
 i32 mqUpdateWithdrawRecord(1:MqHandleModel.MqHandleModel mqHandleModel);
}