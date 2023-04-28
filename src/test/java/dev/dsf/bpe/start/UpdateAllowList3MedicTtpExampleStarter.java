package dev.dsf.bpe.start;

import java.util.Date;

import org.hl7.fhir.r4.model.ResourceType;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.Task;

import ca.uhn.fhir.context.FhirContext;
import dev.dsf.bpe.ConstantsBase;
import dev.dsf.bpe.ConstantsUpdateAllowList;

public class UpdateAllowList3MedicTtpExampleStarter
{
	// Environment variable "DSF_CLIENT_CERTIFICATE_PATH" or args[0]: the path to the client-certificate
	// dsf/dsf-tools/dsf-tools-test-data-generator/cert/Webbrowser_Test_User/Webbrowser_Test_User_certificate.p12
	// Environment variable "DSF_CLIENT_CERTIFICATE_PASSWORD" or args[1]: the password of the client-certificate
	// password
	public static void main(String[] args) throws Exception
	{
		System.out.println(FhirContext.forR4().newXmlParser().encodeResourceToString(task()));
		ExampleStarter.forServer(args, ConstantsExampleStarters.TTP_FHIR_BASE_URL).startWith(task());
	}

	private static Task task()
	{
		Task task = new Task();
		task.getMeta().addProfile(ConstantsUpdateAllowList.PROFILE_DSF_TASK_UPDATE_ALLOW_LIST_AND_LATEST_VERSION);
		task.setInstantiatesUri(
				ConstantsUpdateAllowList.PROFILE_DSF_TASK_UPDATE_ALLOW_LIST_PROCESS_URI_AND_LATEST_VERSION);
		task.setStatus(Task.TaskStatus.REQUESTED);
		task.setIntent(Task.TaskIntent.ORDER);
		task.setAuthoredOn(new Date());
		task.getRequester().setType(ResourceType.Organization.name()).getIdentifier()
				.setSystem(ConstantsBase.NAMINGSYSTEM_DSF_ORGANIZATION_IDENTIFIER)
				.setValue(ConstantsExampleStarters.NAMINGSYSTEM_DSF_ORGANIZATION_IDENTIFIER_VALUE_TTP);
		task.getRestriction().addRecipient().setType(ResourceType.Organization.name()).getIdentifier()
				.setSystem(ConstantsBase.NAMINGSYSTEM_DSF_ORGANIZATION_IDENTIFIER)
				.setValue(ConstantsExampleStarters.NAMINGSYSTEM_DSF_ORGANIZATION_IDENTIFIER_VALUE_TTP);

		task.addInput()
				.setValue(new StringType(ConstantsUpdateAllowList.PROFILE_DSF_TASK_UPDATE_ALLOW_LIST_MESSAGE_NAME))
				.getType().addCoding().setSystem(ConstantsBase.CODESYSTEM_DSF_BPMN)
				.setCode(ConstantsBase.CODESYSTEM_DSF_BPMN_VALUE_MESSAGE_NAME);

		return task;
	}
}
