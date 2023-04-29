package dev.dsf.bpe.start;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.ResourceType;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.Task;

import dev.dsf.bpe.AllowListProcessPluginDefinition;
import dev.dsf.bpe.ConstantsAllowList;
import dev.dsf.bpe.v1.constants.CodeSystems;
import dev.dsf.bpe.v1.constants.NamingSystems;
import dev.dsf.fhir.client.FhirWebserviceClient;

public class DownloadAllowListFromTtpViaMedic1ExampleStarter
{
	private static final String REQUESTER_FHIR_BASE_URL = ConstantsExampleStarters.MEDIC_1_FHIR_BASE_URL;
	private static final String REQUESTER_RECIPIENT = ConstantsExampleStarters.NAMINGSYSTEM_DSF_ORGANIZATION_IDENTIFIER_VALUE_MEDIC_1;
	private static final String ALLOW_LIST_FHIR_BASE_URL = ConstantsExampleStarters.TTP_FHIR_BASE_URL;

	// Environment variable "DSF_CLIENT_CERTIFICATE_PATH" or args[0]: the path to the client-certificate
	// dsf/dsf-tools/dsf-tools-test-data-generator/cert/Webbrowser_Test_User/Webbrowser_Test_User_certificate.p12
	// Environment variable "DSF_CLIENT_CERTIFICATE_PASSWORD" or args[1]: the password of the client-certificate
	// password
	public static void main(String[] args) throws Exception
	{
		ExampleStarter starter = ExampleStarter.forServer(args, REQUESTER_FHIR_BASE_URL);

		Bundle allowList = allowList(starter);
		Task task = task(allowList);

		starter.startWith(task);
	}

	private static Task task(Bundle allowList)
	{
		var def = new AllowListProcessPluginDefinition();

		Task task = new Task();
		task.getMeta()
				.addProfile(ConstantsAllowList.PROFILE_DSF_TASK_DOWNLOAD_ALLOW_LIST + "|" + def.getResourceVersion());
		task.setInstantiatesCanonical(
				ConstantsAllowList.PROFILE_DSF_TASK_DOWNLOAD_ALLOW_LIST_PROCESS_URI + "|" + def.getResourceVersion());
		task.setStatus(Task.TaskStatus.REQUESTED);
		task.setIntent(Task.TaskIntent.ORDER);
		task.setAuthoredOn(new Date());
		task.getRequester().setType(ResourceType.Organization.name())
				.setIdentifier(NamingSystems.OrganizationIdentifier.withValue(REQUESTER_RECIPIENT));
		task.getRestriction().addRecipient().setType(ResourceType.Organization.name())
				.setIdentifier(NamingSystems.OrganizationIdentifier.withValue(REQUESTER_RECIPIENT));

		task.addInput().setValue(new StringType(ConstantsAllowList.PROFILE_DSF_TASK_DOWNLOAD_ALLOW_LIST_MESSAGE_NAME))
				.getType().addCoding(CodeSystems.BpmnMessage.messageName());
		task.addInput()
				.setValue(new Reference(new IdType(ALLOW_LIST_FHIR_BASE_URL, ResourceType.Bundle.name(),
						allowList.getIdElement().getIdPart(), allowList.getIdElement().getVersionIdPart())))
				.getType().addCoding().setSystem(ConstantsAllowList.CODESYSTEM_DSF_ALLOW_LIST)
				.setCode(ConstantsAllowList.CODESYSTEM_DSF_ALLOW_LIST_VALUE_ALLOW_LIST);

		return task;
	}

	private static Bundle allowList(ExampleStarter starter) throws Exception
	{
		FhirWebserviceClient client = starter.createClient(ALLOW_LIST_FHIR_BASE_URL);
		Bundle searchResult = client.searchWithStrictHandling(Bundle.class, Map.of("identifier",
				Collections.singletonList("http://dsf.dev/fhir/CodeSystem/allow-list|allow_list")));

		if (searchResult.getTotal() != 1 && searchResult.getEntryFirstRep().getResource() instanceof Bundle)
			throw new IllegalStateException("Expected a single allow list Bundle");

		return (Bundle) searchResult.getEntryFirstRep().getResource();
	}
}
