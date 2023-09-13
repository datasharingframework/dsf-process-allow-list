package dev.dsf.bpe.service;

import java.util.EnumSet;
import java.util.function.Predicate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Bundle.HTTPVerb;
import org.hl7.fhir.r4.model.Endpoint;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.OrganizationAffiliation;
import org.hl7.fhir.r4.model.Resource;
import org.hl7.fhir.r4.model.ResourceType;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.dsf.bpe.ConstantsAllowList;
import dev.dsf.bpe.v1.ProcessPluginApi;
import dev.dsf.bpe.v1.activity.AbstractServiceDelegate;
import dev.dsf.bpe.v1.constants.CodeSystems;
import dev.dsf.bpe.v1.variables.Variables;

public class CheckAllowList extends AbstractServiceDelegate
{
	private static final Logger logger = LoggerFactory.getLogger(CheckAllowList.class);

	public CheckAllowList(ProcessPluginApi api)
	{
		super(api);
	}

	@Override
	protected void doExecute(DelegateExecution execution, Variables variables)
	{
		Bundle bundle = variables.getResource(ConstantsAllowList.BPMN_EXECUTION_VARIABLE_BUNDLE);
		String bundleUrl = variables.getString(ConstantsAllowList.BPMN_EXECUTION_VARIABLE_BUNDLE_URL);

		if (!EnumSet.of(Bundle.BundleType.TRANSACTION, Bundle.BundleType.BATCH).contains(bundle.getType()))
		{
			logger.error("Bundle type TRANSACTION or BATCH expected, but got '{}' in Bundle with id '{}'",
					bundle.getType(), bundleUrl);
			throw new RuntimeException("Bundle type TRANSACTION or BATCH expected, but got '" + bundle.getType()
					+ "' in Bundle with id '" + bundleUrl + "'");
		}

		Task task = variables.getStartTask();

		if (bundle.getEntry().stream().anyMatch(entryNotAllowedWithError(task, bundleUrl)))
		{
			variables.updateTask(task);

			logger.error("Expected Bundle with id '{}' containing only resource types Organization, "
					+ "OrganizationAffiliation or Endpoint and request methods PUT or DELETE, but found different "
					+ "types or methods.", bundleUrl);
			throw new RuntimeException("Expected Bundle with id '" + bundleUrl + " 'containing only resource "
					+ "types Organization, OrganizationAffiliation or Endpoint and "
					+ "request methods PUT or DELETE, but found different types or methods.");
		}
	}

	private Predicate<BundleEntryComponent> entryNotAllowedWithError(Task task, String bundleUrl)
	{
		return entry ->
		{
			boolean resourceNotAllowed = resourceNotAllowedWithError(entry, task, bundleUrl);
			boolean requestNotAllowed = requestNotAllowedWithError(entry, task, bundleUrl);

			// Split into two method calls and not inline to ensure that both methods
			// are executed so that all given error messages can be written to Task.output
			return resourceNotAllowed || requestNotAllowed;
		};
	}

	private boolean resourceNotAllowedWithError(BundleEntryComponent entry, Task task, String bundleUrl)
	{
		Resource resource = entry.getResource();
		boolean resourceAllowed = (HTTPVerb.DELETE.equals(entry.getRequest().getMethod())
				&& (entry.getRequest().getUrl().startsWith(ResourceType.Organization.name())
						|| entry.getRequest().getUrl().startsWith(ResourceType.OrganizationAffiliation.name())
						|| entry.getRequest().getUrl().startsWith(ResourceType.Endpoint.name())))
				|| (resource instanceof Organization || resource instanceof OrganizationAffiliation
						|| resource instanceof Endpoint);

		if (HTTPVerb.DELETE.equals(entry.getRequest().getMethod()) && !resourceAllowed)
			addError(task, "Resource delete of '" + entry.getRequest().getUrl() + "' not allowed in Bundle with id '"
					+ bundleUrl + "'");
		else if (!HTTPVerb.DELETE.equals(entry.getRequest().getMethod()) && !resourceAllowed)
			addError(task, "Resource of type '" + resource.getResourceType().name()
					+ "' not allowed in Bundle with id '" + bundleUrl + "'");

		return !resourceAllowed;
	}

	private boolean requestNotAllowedWithError(BundleEntryComponent entry, Task task, String bundleUrl)
	{
		boolean requestAllowed = false;

		if (entry.hasRequest())
			requestAllowed = EnumSet.of(HTTPVerb.PUT, HTTPVerb.DELETE).contains(entry.getRequest().getMethod());

		if (!requestAllowed)
			addError(task, "Request with Method '" + entry.getRequest().getMethod()
					+ "' not allowed in Bundle with id '" + bundleUrl + "'");

		return !requestAllowed;
	}

	private void addError(Task task, String message)
	{
		logger.warn(message);
		task.addOutput(api.getTaskHelper().createOutput(new StringType(message), CodeSystems.BpmnMessage.error()));
	}
}
