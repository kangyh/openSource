namespace java com.heepay.rpc.gateway.service
include "QuickPayGatewayModel.thrift"

//���ǿ��֧���ӿ�
service TQuickPayService {
     QuickPayGatewayModel.QuickPayResponseVO quickPay(1:required QuickPayGatewayModel.QuickPayRequestVO quickPayRequestVO);
}