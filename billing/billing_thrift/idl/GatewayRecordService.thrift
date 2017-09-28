namespace java com.heepay.rpc.payment.service
include "GatewayRecordModel.thrift"
service GatewayRecordService{
	i32 batchInsert(1:list<GatewayRecordModel.GatewayRecordModel> gatewayRecordlist);
	list<GatewayRecordModel.GatewayRecordModel> queryGatewayRecords(1:GatewayRecordModel.GatewayQueryParam gatewayParam);
	GatewayRecordModel.GatewayRecordModel queryGatewayRecordByGatewayId(1:string gatewayId);
	i32 getGatewayRecordCount(1:GatewayRecordModel.GatewayQueryParam gatewayParam);
}