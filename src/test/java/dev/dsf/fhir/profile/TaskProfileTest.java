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
import dev.dsf.bpe.AllowListProcessPluginDefinition;
import dev.dsf.bpe.ConstantsAllowList;
import dev.dsf.bpe.v1.constants.CodeSystems;
import dev.dsf.bpe.v1.constants.NamingSystems;
import dev.dsf.fhir.validation.ResourceValidator;
import dev.dsf.fhir.validation.ResourceValidatorImpl;
import dev.dsf.fhir.validation.ValidationSupportRule;

public class TaskProfileTest
{
	private static final Logger logger = LoggerFactory.getLogger(TaskProfileTest.class);

	private static final AllowListProcessPluginDefinition def = new AllowListProcessPluginDefinition();

	@ClassRule
	public static final ValidationSupportRule validationRule = new ValidationSupportRule(def.getResourceVersion(),
			def.getReleaseDate(),
			Arrays.asList("dsf-task-base-1.0.0.xml", "dsf-task-update-allow-list.xml",
					"dsf-task-download-allow-list.xml"),
			Arrays.asList("dsf-read-access-tag-0.5.0.xml", "dsf-bpmn-message-1.0.0.xml", "dsf-allow-list.xml"),
			Arrays.asList("dsf-read-access-tag-0.5.0.xml", "dsf-bpmn-message-1.0.0.xml", "dsf-allow-list.xml"));

	private ResourceValidator resourceValidator = new ResourceValidatorImpl(validationRule.getFhirContext(),
			validationRule.getValidationSupport());

	@Test
	public void testTaskUpdateAllowListValid()
	{
		Task task = createValidTaskUpdateAllowList();

		ValidationResult result = resourceValidator.validate(task);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());
	}

	@Test
	public void testTaskUpdateAllowlistValidWithOutput()
	{
		Task task = createValidTaskUpdateAllowList();
		task.addOutput().setValue(new Reference(new IdType("Bundle", UUID.randomUUID().toString(), "1"))).getType()
				.addCoding().setSystem(ConstantsAllowList.CODESYSTEM_DSF_ALLOW_LIST)
				.setCode(ConstantsAllowList.CODESYSTEM_DSF_ALLOW_LIST_VALUE_ALLOW_LIST);

		ValidationResult result = resourceValidator.validate(task);
		ValidationSupportRule.logValidationMessages(logger, result);

		assertEquals(0, result.getMessages().stream().filter(m -> ResultSeverityEnum.ERROR.equals(m.getSeverity())
				|| ResultSeverityEnum.FATAL.equals(m.getSeverity())).count());
	}

	private Task createValidTaskUpdateAllowList()
	{
		Task task = new Task();
		task.getMeta().addProfile(ConstantsAllowList.PROFILE_DSF_TASK_UPDATE_ALLOW_LIST);
		task.setInstantiatesCanonical(
				ConstantsAllowList.PROFILE_DSF_TASK_UPDATE_ALLOW_LIST_PROCESS_URI + "|" + def.getResourceVersion());
		task.setStatus(TaskStatus.REQUESTED);
		task.setIntent(TaskIntent.ORDER);
		task.setAuthoredOn(new Date());
		task.getRequester().setType(ResourceType.Organization.name())
				.setIdentifier(NamingSystems.OrganizationIdentifier.withValue("TTP"));
		task.getRestriction().addRecipient().setType(ResourceType.Organization.name())
				.setIdentifier(NamingSystems.OrganizationIdentifier.withValue("TTP"));

		task.addInput().setValue(new StringType(ConstantsAllowList.PROFILE_DSF_TASK_UPDATE_ALLOW_LIST_MESSAGE_NAME))
				.getType().addCoding(CodeSystems.BpmnMessage.messageName());

		return task;
	}

	@Test
	public void testTaskDownloadAllowListValid()
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
		task.getMeta().addProfile(ConstantsAllowList.PROFILE_DSF_TASK_DOWNLOAD_ALLOW_LIST);
		task.setInstantiatesCanonical(
				ConstantsAllowList.PROFILE_DSF_TASK_DOWNLOAD_ALLOW_LIST_PROCESS_URI + "|" + def.getResourceVersion());
		task.setStatus(TaskStatus.REQUESTED);
		task.setIntent(TaskIntent.ORDER);
		task.setAuthoredOn(new Date());
		task.getRequester().setType(ResourceType.Organization.name())
				.setIdentifier(NamingSystems.OrganizationIdentifier.withValue("TTP"));
		task.getRestriction().addRecipient().setType(ResourceType.Organization.name())
				.setIdentifier(NamingSystems.OrganizationIdentifier.withValue("TTP"));

		task.addInput().setValue(new StringType(ConstantsAllowList.PROFILE_DSF_TASK_DOWNLOAD_ALLOW_LIST_MESSAGE_NAME))
				.getType().addCoding(CodeSystems.BpmnMessage.messageName());
		task.addInput()
				.setValue(
						new Reference(new IdType("https://foo.bar/fhir", "Bundle", UUID.randomUUID().toString(), "1")))
				.getType().addCoding().setSystem(ConstantsAllowList.CODESYSTEM_DSF_ALLOW_LIST)
				.setCode(ConstantsAllowList.CODESYSTEM_DSF_ALLOW_LIST_VALUE_ALLOW_LIST);

		return task;
	}
}
