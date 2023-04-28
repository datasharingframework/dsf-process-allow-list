package dev.dsf.fhir.profile;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.ResourceType;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.Task;
import org.hl7.fhir.r4.model.Task.TaskIntent;
import org.hl7.fhir.r4.model.Task.TaskStatus;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.validation.ResultSeverityEnum;
import ca.uhn.fhir.validation.ValidationResult;
import dev.dsf.bpe.ConstantsBase;
import dev.dsf.bpe.ConstantsUpdateAllowList;
import dev.dsf.bpe.UpdateAllowListProcessPluginDefinition;
import dev.dsf.fhir.validation.ResourceValidator;
import dev.dsf.fhir.validation.ResourceValidatorImpl;
import dev.dsf.fhir.validation.ValidationSupportRule;

public class TaskProfileTest
{
	private static final Logger logger = LoggerFactory.getLogger(TaskProfileTest.class);

	@ClassRule
	public static final ValidationSupportRule validationRule = new ValidationSupportRule(
			UpdateAllowListProcessPluginDefinition.VERSION, UpdateAllowListProcessPluginDefinition.RELEASE_DATE,
			Arrays.asList("dsf-task-base-0.5.0.xml", "dsf-task-update-allow-list.xml",
					"dsf-task-download-allow-list.xml"),
			Arrays.asList("dsf-read-access-tag-0.5.0.xml", "dsf-bpmn-message-0.5.0.xml", "dsf-update-allow-list.xml"),
			Arrays.asList("dsf-read-access-tag-0.5.0.xml", "dsf-bpmn-message-0.5.0.xml", "dsf-update-allow-list.xml"));

	private ResourceValidator resourceValidator = new ResourceValidatorImpl(validationRule.getFhirContext(),
			validationRule.getValidationSupport());

	@Test
	public void testTaskUpdateAllowListValid() throws Exception
	{
		Task task = createValidTaskUpdateAllowList();

		ValidationResult result = resourceValidator.validate(task);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());
	}

	@Test
	public void testTaskUpdateAllowlistValidWithOutput() throws Exception
	{
		Task task = createValidTaskUpdateAllowList();
		task.addOutput().setValue(new Reference(new IdType("Bundle", UUID.randomUUID().toString(), "1"))).getType()
				.addCoding().setSystem(ConstantsUpdateAllowList.CODESYSTEM_DSF_UPDATE_ALLOW_LIST)
				.setCode(ConstantsUpdateAllowList.CODESYSTEM_DSF_UPDATE_ALLOW_LIST_VALUE_ALLOW_LIST);

		ValidationResult result = resourceValidator.validate(task);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());
	}

	private Task createValidTaskUpdateAllowList()
	{
		Task task = new Task();
		task.getMeta().addProfile(ConstantsUpdateAllowList.PROFILE_DSF_TASK_UPDATE_ALLOW_LIST);
		task.setInstantiatesUri(
				ConstantsUpdateAllowList.PROFILE_DSF_TASK_UPDATE_ALLOW_LIST_PROCESS_URI_AND_LATEST_VERSION);
		task.setStatus(TaskStatus.REQUESTED);
		task.setIntent(TaskIntent.ORDER);
		task.setAuthoredOn(new Date());
		task.getRequester().setType(ResourceType.Organization.name()).getIdentifier()
				.setSystem(ConstantsBase.NAMINGSYSTEM_DSF_ORGANIZATION_IDENTIFIER).setValue("TTP");
		task.getRestriction().addRecipient().setType(ResourceType.Organization.name()).getIdentifier()
				.setSystem(ConstantsBase.NAMINGSYSTEM_DSF_ORGANIZATION_IDENTIFIER).setValue("TTP");

		task.addInput()
				.setValue(new StringType(ConstantsUpdateAllowList.PROFILE_DSF_TASK_UPDATE_ALLOW_LIST_MESSAGE_NAME))
				.getType().addCoding().setSystem(ConstantsBase.CODESYSTEM_DSF_BPMN)
				.setCode(ConstantsBase.CODESYSTEM_DSF_BPMN_VALUE_MESSAGE_NAME);

		return task;
	}

	@Test
	public void testTaskDownloadAllowListValid() throws Exception
	{
		Task task = createValidTaskDownloadAllowList();

		ValidationResult result = resourceValidator.validate(task);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());
	}

	private Task createValidTaskDownloadAllowList()
	{
		Task task = new Task();
		task.getMeta().addProfile(ConstantsUpdateAllowList.PROFILE_DSF_TASK_DOWNLOAD_ALLOW_LIST);
		task.setInstantiatesUri(
				ConstantsUpdateAllowList.PROFILE_DSF_TASK_DOWNLOAD_ALLOW_LIST_PROCESS_URI_AND_LATEST_VERSION);
		task.setStatus(TaskStatus.REQUESTED);
		task.setIntent(TaskIntent.ORDER);
		task.setAuthoredOn(new Date());
		task.getRequester().setType(ResourceType.Organization.name()).getIdentifier()
				.setSystem(ConstantsBase.NAMINGSYSTEM_DSF_ORGANIZATION_IDENTIFIER).setValue("MeDIC 1");
		task.getRestriction().addRecipient().setType(ResourceType.Organization.name()).getIdentifier()
				.setSystem(ConstantsBase.NAMINGSYSTEM_DSF_ORGANIZATION_IDENTIFIER).setValue("MeDIC 1");

		task.addInput()
				.setValue(new StringType(ConstantsUpdateAllowList.PROFILE_DSF_TASK_DOWNLOAD_ALLOW_LIST_MESSAGE_NAME))
				.getType().addCoding().setSystem(ConstantsBase.CODESYSTEM_DSF_BPMN)
				.setCode(ConstantsBase.CODESYSTEM_DSF_BPMN_VALUE_MESSAGE_NAME);
		task.addInput()
				.setValue(
						new Reference(new IdType("https://foo.bar/fhir", "Bundle", UUID.randomUUID().toString(), "1")))
				.getType().addCoding().setSystem(ConstantsUpdateAllowList.CODESYSTEM_DSF_UPDATE_ALLOW_LIST)
				.setCode(ConstantsUpdateAllowList.CODESYSTEM_DSF_UPDATE_ALLOW_LIST_VALUE_ALLOW_LIST);

		return task;
	}
}
