namespace java com.heepay.rpc.tpds.model

struct DataInfoResponseModel{
    1: string totalPageNo;
    2: string totalChkNo;
    3: string customerId;
    4: string phoneNo;
    5: string identityCheckStatus;
    6: string extantStatus;
    7: string accountNo;
    8: string accountStatus;
    9: string availableamount;
    10: string transitamount;
    11: string withdrawalamount;
    12: string assetamount;
    13: string secBankaccNo;    
    14: string secBankaccStatus;
    15: list<DataInfoResponseDetailModel> CardList;
    16: string serviceDate;
    17: string note;   
}

struct DataInfoResponseDetailModel{
    1: string oderNo;
    2: string tiedAccno;
    3: string tiedAccStatus;
    4: string tiedAcctelno;
}