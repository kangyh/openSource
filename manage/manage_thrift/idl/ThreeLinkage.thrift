namespace java com.heepay.manage.rpc.service

service ThreeLinkageService {
  string select(1:string id,2:string callback,3:string role),
  string selectApi(1:string id,2:string role),
  string selectBank(1:string id,2:string callback,3:string role,4:string bankNo),
  string getBankList(),
  string selectAreaName(1:string name,2:string bankNo,3:string openBankName),
  string selectAreaList(),
  string selectLineNumberList(1:string bankNo,2:string provinceCode,3:string cityCode)
  string selectLineNumber(1:string bankNo,2:string provinceName,3:string cityName)
}
