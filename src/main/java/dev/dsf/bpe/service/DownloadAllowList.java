package dev.dsf.bpe.service;

import java.util.EnumSet;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.dsf.bpe.ConstantsAllowList;
import dev.dsf.bpe.v1.ProcessPluginApi;
import dev.dsf.bpe.v1.activity.AbstractServiceDelegate;
import dev.dsf.bpe.v1.variables.Variables;
import dev.dsf.fhir.client.FhirWebserviceClient;
import jakarta.ws.rs.WebApplicationException;

public class DownloadAllowList extends AbstractServiceDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(DownloadAllowList.class);

	public DownloadAllowList(ProcessPluginApi api)
	{
		super(api);
	}

	@Override
	protected void doExecute(DelegateExecution execution, Variables variables) throws Exception
	{
		Task task = variables.getStartTask();
		IdType bundleId = getBundleId(task);
		FhirWebserviceClient requesterClient = api.getFhirWebserviceClientProvider()
				.getWebserviceClient(bundleId.getBaseUrl());

		Bundle bundle;
		try
		{
			if (bundleId.hasVersionIdPart())
				bundle = requesterClient.read(Bundle.class, bundleId.getIdPart(), bundleId.getVersionIdPart());
			else
				bundle = requesterClient.read(Bundle.class, bundleId.getIdPart());
		}
		catch (WebApplicationException e)
		{
			logger.error("Error while reading Bundle with id {} from organization {}: {}", bundleId.getValue(),
					task.getRequester().getReference(), e.getMessage());
			throw new RuntimeException("Error while reading Bundle with id " + bundleId.getValue()
					+ " from organization " + task.getRequester().getReference() + ", " + e.getMessage(), e);
		}

		if (!EnumSet.of(BundleType.TRANSACTION, BundleType.BATCH).contains(bundle.getType()))
		{
			logger.error("Bundle type TRANSACTION or BATCH expected, but got {}", bundle.getType());
			throw new RuntimeException("Bundle type TRANSACTION or BATCH expected, but got " + bundle.getType());
		}

		try
		{
			logger.debug("Posting bundle to local endpoint: {}",
					api.getFhirContext().newXmlParser().encodeResourceToString(bundle));
			api.getFhirWebserviceClientProvider().getLocalWebserviceClient().withMinimalReturn().postBundle(bundle);
		}
		catch (Exception e)
		{
			logger.error("Error while executing Bundle with id {} from organization {} locally: {}",
					bundleId.getValue(), task.getRequester().getReference(), e.getMessage());
			throw new RuntimeException("Error while executing Bundle with id " + bundleId.getValue()
					+ " from organization " + task.getRequester().getReference() + " locally, " + e.getMessage(), e);
		}
	}

	private IdType getBundleId(Task task)
	{
		List<Reference> bundleReferences = api.getTaskHelper()
				.getInputParameterValues(task, ConstantsAllowList.CODESYSTEM_DSF_ALLOW_LIST,
						ConstantsAllowList.CODESYSTEM_DSF_ALLOW_LIST_VALUE_ALLOW_LIST, Reference.class)
				.toList();

		if (bundleReferences.size() != 1)
		{
			logger.error("Task input parameter {} contains unexpected number of Bundle IDs, expected 1, got {}",
					ConstantsAllowList.CODESYSTEM_DSF_ALLOW_LIST_VALUE_ALLOW_LIST, bundleReferences.size());
			throw new RuntimeException(
					"Task input parameter " + ConstantsAllowList.CODESYSTEM_DSF_ALLOW_LIST_VALUE_ALLOW_LIST
							+ " contains unexpected number of Bundle IDs, expected 1, got " + bundleReferences.size());
		}
		else if (!bundleReferences.get(0).hasReference()
				|| !bundleReferences.get(0).getReference().contains("/Bundle/"))
		{
			logger.error("Task input parameter {} has no Bundle reference",
					ConstantsAllowList.CODESYSTEM_DSF_ALLOW_LIST_VALUE_ALLOW_LIST);
			throw new RuntimeException("Task input parameter "
					+ ConstantsAllowList.CODESYSTEM_DSF_ALLOW_LIST_VALUE_ALLOW_LIST + " has no Bundle reference");
		}

		return new IdType(bundleReferences.get(0).getReference());
	}
}
