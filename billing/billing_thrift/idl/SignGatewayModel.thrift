namespace java com.heepay.rpc.gateway.model

//����ṹ�嶨����ǩԼ��������Ϣ
struct SignRequestVO{
	/**�̻���*/
	1: required string merchantId;
	/**�ͻ���*/
	2: required string custId;
	/**�ͻ�����/�ֿ�������*/
	3: required string custName;
	/**֤������*/
	4: string idType;
	/**֤����/�ֿ������֤��*/
	5: required string cardId;
	/**�ֻ���*/
	6: required string phoneNO;
	/**�ⲿ���ٱ��/֧��������*/
	7: required string paymentId;
	/**���׽��*/
	8: required string amount;

	/**
	 * ��Ǯ�����ֶ�
	 */
	/**���ֿܳ���֤����*/
	9: string cardHolderIdEn;
	/**����*/
	10: required string pan;
	/**���ܿ���*/
	11: string panEn;
	/**���Կ���*/
	12: required string storablePan;
	/**���д���*/
    13: string bankId;
	/**��Ч��*/
    14: string expiredDate;
	/**��У����*/
    15: string cvv2;
	/**���ܿ�У����*/
    16: string cvv2En;

	/**
	 * ���������ֶ�
	 */
	/**���ױ�־*/
	17: string transFlag;
	/**��̨��*/
	18: string cuntNo;
	/**��������*/
    19: string instlNum;
	/**�˻������־*/
    20: string cardType;
    /**��������*/
    21: required string entryDate;
    /**����ʱ��*/
    22: required string entryTime;
    /**�������к�*/
    23: required string requestSn;
  	/**ǩ��*/
    24: string signature;
	/**�ն���Ϣ*/
    25: string terminalMsg;
    /**������*/
    26:  string transCode;

	/**
     * ·���ֶ�
     */
    /**ͨ������������*/
    27: string channelPartnerCode;
    /**ͨ�����ʹ���*/
    28: string channelTypeCode;
    /**���п����ʹ���*/
    29:required string cardTypeCode;
}

//����ṹ�嶨����ǩԼ�ķ�����Ϣ
struct SignResponseVO{
	/**֧��������*/
	1: required string paymengtId;
	/**��������*/
	2: required string entryDate;
	/**����ʱ��*/
	3: string entryTime;
	/**������Ϣ*/
	4: required string token;
	/**Ӧ����*/
	5: required string responseCode;
	/**Ӧ�����ı���Ϣ*/
	6: required string responseMsg;
	/**ͨ������*/
    7: required string payChannelCode;
	/**
	 * ��Ǯ�����ֶ�
	 */
	/**�̻���*/
	8: required string merchantId;
	/**�ͻ���*/
	9: string custId;
	/**���Կ���*/
	10: string storablePan;

	/**
	 * ���������ֶ�
	 */
	/**������֤��־ 0-���飬1-������֤����*/
	11: string validFlag;
	/**����״̬*/
	12: required string status;
	/**�������к�*/
    13: required string requestSn;
    /**������*/
    14: required string transCode;
    /**ǩ��*/
    15: string signature;
	/**�ն���Ϣ*/
    16: string terminalMsg;
    
}
