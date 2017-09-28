package com.heepay.engine;

public interface IRuleEngineService {
	<T extends AbstractRiskFact> void handleRuleFact(T fact);
}


