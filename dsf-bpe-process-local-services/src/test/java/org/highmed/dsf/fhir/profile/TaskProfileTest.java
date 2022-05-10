package org.highmed.dsf.fhir.profile;

import static org.highmed.dsf.bpe.ConstantsBase.CODESYSTEM_HIGHMED_BPMN;
import static org.highmed.dsf.bpe.ConstantsBase.CODESYSTEM_HIGHMED_BPMN_VALUE_MESSAGE_NAME;
import static org.highmed.dsf.bpe.ConstantsBase.CODESYSTEM_HIGHMED_QUERY_TYPE;
import static org.highmed.dsf.bpe.ConstantsBase.CODESYSTEM_HIGMED_QUERY_TYPE_VALUE_AQL;
import static org.highmed.dsf.bpe.ConstantsBase.NAMINGSYSTEM_HIGHMED_ORGANIZATION_IDENTIFIER;
import static org.highmed.dsf.bpe.ConstantsDataSharing.CODESYSTEM_HIGHMED_DATA_SHARING;
import static org.highmed.dsf.bpe.ConstantsDataSharing.CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_BLOOM_FILTER_CONFIG;
import static org.highmed.dsf.bpe.ConstantsDataSharing.CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_NEEDS_CONSENT_CHECK;
import static org.highmed.dsf.bpe.ConstantsDataSharing.CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_NEEDS_RECORD_LINKAGE;
import static org.highmed.dsf.bpe.ConstantsLocalServices.PROFILE_HIGHMED_TASK_LOCAL_SERVICES;
import static org.highmed.dsf.bpe.ConstantsLocalServices.PROFILE_HIGHMED_TASK_LOCAL_SERVICES_MESSAGE_NAME;
import static org.highmed.dsf.bpe.ConstantsLocalServices.PROFILE_HIGHMED_TASK_LOCAL_SERVICES_PROCESS_URI_AND_LATEST_VERSION;
import static org.highmed.dsf.bpe.LocalServicesProcessPluginDefinition.RELEASE_DATE;
import static org.highmed.dsf.bpe.LocalServicesProcessPluginDefinition.VERSION;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import org.highmed.dsf.fhir.validation.ResourceValidator;
import org.highmed.dsf.fhir.validation.ResourceValidatorImpl;
import org.highmed.dsf.fhir.validation.ValidationSupportRule;
import org.hl7.fhir.r4.model.Base64BinaryType;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.ResourceType;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.Task;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.validation.ResultSeverityEnum;
import ca.uhn.fhir.validation.ValidationResult;

public class TaskProfileTest
{
	private static final Logger logger = LoggerFactory.getLogger(TaskProfileTest.class);

	@ClassRule
	public static final ValidationSupportRule validationRule = new ValidationSupportRule(VERSION, RELEASE_DATE,
			Arrays.asList("highmed-task-base-0.5.0.xml", "highmed-group-0.5.0.xml",
					"highmed-extension-group-id-0.5.0.xml", "highmed-extension-query-0.5.0.xml",
					"highmed-task-local-services-integration.xml"),
			Arrays.asList("highmed-read-access-tag-0.5.0.xml", "highmed-bpmn-message-0.5.0.xml",
					"highmed-data-sharing.xml", "highmed-query-type-0.5.0.xml"),
			Arrays.asList("highmed-read-access-tag-0.5.0.xml", "highmed-bpmn-message-0.5.0.xml",
					"highmed-data-sharing.xml", "highmed-query-type-0.5.0.xml"));

	private ResourceValidator resourceValidator = new ResourceValidatorImpl(validationRule.getFhirContext(),
			validationRule.getValidationSupport());

	@Test
	public void testTaskLocalServiceIntegrationValid() throws Exception
	{
		Task task = createValidTaskLocalServiceIntegration();

		ValidationResult result = resourceValidator.validate(task);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());
	}

	private Task createValidTaskLocalServiceIntegration()
	{
		Task task = new Task();

		task.getMeta().addProfile(PROFILE_HIGHMED_TASK_LOCAL_SERVICES);
		task.setInstantiatesUri(PROFILE_HIGHMED_TASK_LOCAL_SERVICES_PROCESS_URI_AND_LATEST_VERSION);
		task.setStatus(Task.TaskStatus.REQUESTED);
		task.setIntent(Task.TaskIntent.ORDER);
		task.setAuthoredOn(new Date());

		task.getRequester().setType(ResourceType.Organization.name()).getIdentifier()
				.setSystem(NAMINGSYSTEM_HIGHMED_ORGANIZATION_IDENTIFIER).setValue("MeDIC");
		task.getRestriction().addRecipient().setType(ResourceType.Organization.name()).getIdentifier()
				.setSystem(NAMINGSYSTEM_HIGHMED_ORGANIZATION_IDENTIFIER).setValue("MeDIC");

		task.addInput().setValue(new StringType(PROFILE_HIGHMED_TASK_LOCAL_SERVICES_MESSAGE_NAME)).getType().addCoding()
				.setSystem(CODESYSTEM_HIGHMED_BPMN).setCode(CODESYSTEM_HIGHMED_BPMN_VALUE_MESSAGE_NAME);

		task.addInput().setValue(new StringType("SELECT COUNT(e) FROM EHR e;")).getType().addCoding()
				.setSystem(CODESYSTEM_HIGHMED_QUERY_TYPE).setCode(CODESYSTEM_HIGMED_QUERY_TYPE_VALUE_AQL);
		task.addInput().setValue(new BooleanType(true)).getType().addCoding().setSystem(CODESYSTEM_HIGHMED_DATA_SHARING)
				.setCode(CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_NEEDS_CONSENT_CHECK);
		task.addInput().setValue(new BooleanType(true)).getType().addCoding().setSystem(CODESYSTEM_HIGHMED_DATA_SHARING)
				.setCode(CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_NEEDS_RECORD_LINKAGE);

		byte[] bloomFilterConfig = Base64.getDecoder().decode(
				"CIw/x19d3Oj+GLOKgYAX5KrFAl11q6qMi0qkDiyUOCvMXuF2KffVvSnjUjkTvqh4z8Xs+MuQdK6FqTedM5FY9t4qm+k92A+P");

		task.addInput().setValue(new Base64BinaryType(bloomFilterConfig)).getType().addCoding()
				.setSystem(CODESYSTEM_HIGHMED_DATA_SHARING)
				.setCode(CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_BLOOM_FILTER_CONFIG);

		return task;
	}
}
