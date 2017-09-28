namespace java com.heepay.rpc.gateway.service
include "QuickPayGatewayModel.thrift"

//这是快捷支付接口
service TQuickPayService {
     QuickPayGatewayModel.QuickPayResponseVO quickPay(1:required QuickPayGatewayModel.QuickPayRequestVO quickPayRequestVO);
}