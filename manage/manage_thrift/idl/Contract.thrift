namespace java com.heepay.manage.rpc.service

struct ContractThrift {
    1:string content,
    2:string path,
    3:string company
}


service ContractService {
    ContractThrift getContract(1:string contractId,2:string companyName,3:string address,
                        4:string linkMan,5:string phone,6:string linkManB,7:string phoneB );
}