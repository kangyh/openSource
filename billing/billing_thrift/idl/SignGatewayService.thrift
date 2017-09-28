namespace java com.heepay.rpc.gateway.service
include "SignGatewayModel.thrift"

//这是签约接口
service TSignService {
	SignGatewayModel.SignResponseVO sign(1:required SignGatewayModel.SignRequestVO signRequestVO);
}