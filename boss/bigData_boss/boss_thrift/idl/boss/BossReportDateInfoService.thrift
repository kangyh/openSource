namespace java com.heepay.rpc.boss.service
service BossReportDateInfoService {
	string getbossReportDateInfoList(1:string params);
	string getbossReportDateInfoDetail(1:string params);
	string getbossReportDateInfoTotal(1:string params);
}