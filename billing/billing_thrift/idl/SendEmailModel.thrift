namespace java com.heepay.rpc.billing.model

struct SendEmailModel{
	1: i64 settleId;
    2: string comeFrom;
    3: string to;  
    4: string subject;
    5: string content;        
    6: string accessories;    
   
}