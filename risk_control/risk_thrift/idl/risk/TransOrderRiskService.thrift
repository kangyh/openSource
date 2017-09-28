namespace java com.heepay.rpc.risk.service

include "TransOrderRiskModel.thrift"
include "AsyncMsgVO.thrift"

service TransOrderRiskService {
 	 AsyncMsgVO.AsyncMsgVO ExecuteRule(1:TransOrderRiskModel.TransOrderRiskModel model);
 	 AsyncMsgVO.AsyncMsgVO ExecuteProductEnableRule(1:TransOrderRiskModel.TransOrderRiskModel model);
}