namespace java com.heepay.rpc.gateway.model

//����ṹ�嶨���˿��֧����������Ϣ
struct QuickPayRequestVO{
    /**
     * ��������
     */
    /**������/֧����ˮ��*/
    1:required string paymentId;
    /**֧��������*/
    2:required string custName;
    /**����*/
    3:required string cardNo;
    /**������֤��*/
    4:required string validCode;
    /**�ֻ���*/
    5:required string phoneNo;
    /**���*/
    6:required string amount;
    /**�̻���*/
    7:required string merchantId;
    /**�ն˺�*/
    8: string terminalId;
    /**֧��ʱ��*/
    9:required string entryTime;

    /**
     * ��Ǯ�����ֶ�
     */
    /**�ͻ���*/
    10:required string custId;
    /**���Կ���*/
    11:required string storableCardNo;
    /**����Ч��*/
    12: string expiredDate;
    /****CVV2**/
    13: string cvv2;
    /**�ֿ��˹���*/
    14: string issuerCountry;
    /**���׷����ص�����*/
    15: string siteType;
    /**���׷����ص���*/
    16: string siteID;
    /**�ֿ������֤��*/
    17:required string cardHolderId;
    /**֤������*/
    18:required string idType;
    /**���⽻�ױ�־*/
    19: string spFlag;
    

    /**
     * ��չ�ֶ�
     */
    /**�Ƿ񱣴��Ȩ��Ϣ*/
    20: string savePciFlag;
    /**token*/
    21:required string token;
    /**֧������*/
    22: string payBatch;

    /**
     * ���������ֶ�
     */
    /**���ױ�־ 0-���֧����1-���ÿ�����֧�����ݲ�֧��1��*/
    23:required string transFlag;
    /**��̨��*/
    24: string cuntNo;
    /**���׻��Ҵ��� ���֣�Ŀǰ��֧������ҡ�01��*/
    25:required string transCurrencyType;
    /**���׳����־ 0������ 1���㻧��Ŀǰȡ��0��*/
    26:required string transCurrencyFlag;
    /**��ע*/
    27: string remark;
    /**��������*/
    28: string instlNum;
    /**�����̻�����*/
    29: string subMerchantId;
    /**�����̻�����*/
    30: string submerchantName;
    /**�����̻�������*/
    31: string subMerchantType;
    /**�����̻��������*/
    32: string subMerchantTypeName;
    /**��Ʒ������*/
    33: string goodsType;
    /**��Ʒ�����*/
    34: string goodsTypeName;
    /**�˻�������*/
    35: string cardType;
    /**��������*/
    36: string transType;
    /**ƽ̨����*/
    37: string siteName;
    /**ƽ̨��ַ*/
    38: string siteUrl;

    /**
     * ·���ֶ�
     */
    /**���д���*/
    39:required string bankId;
    /**ͨ������������*/
    40: string channelPartnerCode;
    /**ͨ�����ʹ���*/
    41: string channelTypeCode;
    /**���п����ʹ���*/
    42:required string cardTypeCode;
    /**֧��ͨ������*/
    43:required string payChannelCode;
}

//����ṹ�嶨���˿��֧���ķ�����Ϣ
struct QuickPayResponseVO{
    /**
     * ��������
     */
    /**������/֧����ˮ��*/
    1:required string paymentId;
    /**���*/
    2:required string amount;
    /**����ʱ��*/
    3:required string transTime;
    /**�̻���*/
    4:required string merchantId;


    /**
     * ��Ǯ�����ֶ�
     */
    /**��������*/
    5:required string transType;
    /**��Ϣ״̬*/
    6:required string interactiveStatus;
    /**�ն˺�*/
    7:required string terminalId;
    /**֧��ʱ��*/
    8:required string entryTime;
    /**�ͻ���*/
    9:required string custId;
    /**ϵͳ�ο���*/
    10:required string refNumber;
    /**Ӧ����*/
    11:required string responseCode;
    /**Ӧ�����ı���Ϣ*/
    12: required string responseMsg;
    /**ͨ������*/
    13:required string payChannelCode;
    /**����֯���*/
    14: string cardOrg;
    /**������������*/
    15: string issuer;
    /**���Կ���*/
    16: string storableCardNo;
    /**��Ȩ��*/
    17: string authorizationCode;
	
    /**
     * ���������ֶ�
     */
    /**������ˮ��*/
    18: string bankSid;
    /**����*/
    19: string curCode;

}
