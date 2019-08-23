package org.highmed.dsf.bpe.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.highmed.dsf.bpe.delegate.AbstractServiceDelegate;
import org.highmed.fhir.client.WebserviceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrepareSimpleCohortSizeQuery extends AbstractServiceDelegate
{

	private static final Logger logger = LoggerFactory.getLogger(PrepareSimpleCohortSizeQuery.class);

	public PrepareSimpleCohortSizeQuery(WebserviceClient webserviceClient) {
		super(webserviceClient);
	}

	@Override
	public void executeService(DelegateExecution execution) throws Exception
	{
		// TODO: extract query from research study referenced group (= cohort characteristics) --> extension
		// TODO: change to multiinstance for multiple cohorts of one research study
	}
}
