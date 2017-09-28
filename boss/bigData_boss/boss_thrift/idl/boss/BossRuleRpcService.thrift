namespace java com.heepay.rpc.boss.service
service BossRuleRpcService {
	string  addBossRule(1:string BossRuleInfoEntity);
    string  editBossRule(1:string BossRuleInfoEntity);
    string  getBossRule(1:string jobStatus);
}