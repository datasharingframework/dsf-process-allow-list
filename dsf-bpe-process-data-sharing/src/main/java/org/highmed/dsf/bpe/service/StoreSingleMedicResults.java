package org.highmed.dsf.bpe.service;

import static org.highmed.dsf.bpe.ConstantsBase.BPMN_EXECUTION_VARIABLE_TTP_IDENTIFIER;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.highmed.dsf.fhir.authorization.read.ReadAccessHelper;
import org.highmed.dsf.fhir.client.FhirWebserviceClientProvider;
import org.highmed.dsf.fhir.task.TaskHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StoreSingleMedicResults extends StoreResults
{
	public StoreSingleMedicResults(FhirWebserviceClientProvider clientProvider, TaskHelper taskHelper,
			ReadAccessHelper readAccessHelper, ObjectMapper openEhrObjectMapper)
	{
		super(clientProvider, taskHelper, readAccessHelper, openEhrObjectMapper);
	}

	@Override
	protected String getSecurityIdentifier(DelegateExecution execution)
	{
		return (String) execution.getVariable(BPMN_EXECUTION_VARIABLE_TTP_IDENTIFIER);
	}
}
