namespace java com.heepay.rpc.gateway.service
include "SignGatewayModel.thrift"

//����ǩԼ�ӿ�
service TSignService {
	SignGatewayModel.SignResponseVO sign(1:required SignGatewayModel.SignRequestVO signRequestVO);
}