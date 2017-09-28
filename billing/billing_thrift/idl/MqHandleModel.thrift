namespace java com.heepay.rpc.payment.model

struct MqHandleModel {
    1: string paymentId;
    2: i64 merchantId;
    3: string bankSerialNo;
    4: string result;
    5: string notifyUrl;
    6: string merchantOrderNo;
    7: string successAmount;
    8: string merchantCompany;
    9: string payAmount;
    10: string transNo;
}