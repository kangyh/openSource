namespace java com.heepay.rpc.tpds.model

struct FundTransRequestModel {
    1: string businessSeqNo;
    2: string busiTradeType;
    3: string entrustflag;
    4: list<FundTransDetailModel> AccountList;
    5: string objectId;
    6: string note;
}

struct FundTransDetailModel{
    1: string oderNo;
    2: string debitAccountNo;
    3: string cebitAccountNo;
    4: string currency;
    5: string amount;
    6: string otherAmounttype;
    7: string otherAmount;
}