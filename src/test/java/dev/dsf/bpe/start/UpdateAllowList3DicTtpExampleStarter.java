package dev.dsf.bpe.start;

import java.util.Date;

import org.hl7.fhir.r4.model.ResourceType;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.Task;

import dev.dsf.bpe.AllowListProcessPluginDefinition;
import dev.dsf.bpe.ConstantsAllowList;
import dev.dsf.bpe.v1.constants.CodeSystems;
import dev.dsf.bpe.v1.constants.NamingSystems;

public class UpdateAllowList3DicTtpExampleStarter
{
	// Environment variable "DSF_CLIENT_CERTIFICATE_PATH" or args[0]: the path to the client-certificate
	// dsf/dsf-tools/dsf-tools-test-data-generator/cert/Webbrowser_Test_User/Webbrowser_Test_User_certificate.p12
	// Environment variable "DSF_CLIENT_CERTIFICATE_PASSWORD" or args[1]: the password of the client-certificate
	// password
	public static void main(String[] args) throws Exception
	{
		ExampleStarter.forServer(args, ConstantsExampleStarters.TTP_FHIR_BASE_URL).startWith(task());
	}

	private static Task task()
	{
		var def = new AllowListProcessPluginDefinition();

		Task task = new Task();
		task.getMeta()
				.addProfile(ConstantsAllowList.PROFILE_DSF_TASK_UPDATE_ALLOW_LIST + "|" + def.getResourceVersion());
		task.setInstantiatesCanonical(
				ConstantsAllowList.PROFILE_DSF_TASK_UPDATE_ALLOW_LIST_PROCESS_URI + "|" + def.getResourceVersion());
		task.setStatus(Task.TaskStatus.REQUESTED);
		task.setIntent(Task.TaskIntent.ORDER);
		task.setAuthoredOn(new Date());
		task.getRequester().setType(ResourceType.Organization.name()).setIdentifier(NamingSystems.OrganizationIdentifier
				.withValue(ConstantsExampleStarters.NAMINGSYSTEM_DSF_ORGANIZATION_IDENTIFIER_VALUE_TTP));
		task.getRestriction().addRecipient().setType(ResourceType.Organization.name())
				.setIdentifier(NamingSystems.OrganizationIdentifier
						.withValue(ConstantsExampleStarters.NAMINGSYSTEM_DSF_ORGANIZATION_IDENTIFIER_VALUE_TTP));

		task.addInput().setValue(new StringType(ConstantsAllowList.PROFILE_DSF_TASK_UPDATE_ALLOW_LIST_MESSAGE_NAME))
				.getType().addCoding(CodeSystems.BpmnMessage.messageName());

		return task;
	}
}
