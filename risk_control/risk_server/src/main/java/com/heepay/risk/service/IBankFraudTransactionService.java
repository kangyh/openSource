package com.heepay.risk.service;

import com.heepay.risk.vo.BankFraudQueryVO;

public interface IBankFraudTransactionService {
      boolean SaveTransInfo(BankFraudQueryVO vo);
      boolean SaveTransCardPaymentAccount(BankFraudQueryVO vo);
      boolean SavePaymentAccount(BankFraudQueryVO vo);
      boolean SaveAccountDynamic(BankFraudQueryVO vo);
      boolean SaveAccountDetail(BankFraudQueryVO vo);
      boolean SaveReleaseFeedback(BankFraudQueryVO vo);
}
