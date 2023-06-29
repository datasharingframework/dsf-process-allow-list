package dev.dsf.bpe.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.dsf.bpe.ConstantsAllowList;
import dev.dsf.bpe.v1.ProcessPluginApi;
import dev.dsf.bpe.v1.activity.AbstractServiceDelegate;
import dev.dsf.bpe.v1.variables.Variables;

public class InsertAllowList extends AbstractServiceDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(InsertAllowList.class);

	public InsertAllowList(ProcessPluginApi api)
	{
		super(api);
	}

	@Override
	protected void doExecute(DelegateExecution execution, Variables variables)
	{
		Task task = variables.getStartTask();
		Bundle bundle = variables.getResource(ConstantsAllowList.BPMN_EXECUTION_VARIABLE_BUNDLE);
		String bundleUrl = variables.getString(ConstantsAllowList.BPMN_EXECUTION_VARIABLE_BUNDLE_URL);

		try
		{
			logger.debug("Posting bundle to local endpoint: {}",
					api.getFhirContext().newXmlParser().encodeResourceToString(bundle));
			api.getFhirWebserviceClientProvider().getLocalWebserviceClient().withMinimalReturn().postBundle(bundle);
		}
		catch (Exception e)
		{
			logger.error("Error while executing Bundle with id '{}' from organization {} locally: {}", bundleUrl,
					task.getRequester().getReference(), e.getMessage());
			throw new RuntimeException("Error while executing Bundle with id '" + bundleUrl + "' from organization "
					+ task.getRequester().getReference() + " locally, " + e.getMessage(), e);
		}
	}
}
