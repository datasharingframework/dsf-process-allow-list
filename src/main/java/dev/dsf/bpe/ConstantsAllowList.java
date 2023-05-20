package dev.dsf.bpe;

public interface ConstantsAllowList
{
	String PROCESS_NAME_DOWNLOAD_ALLOW_LIST = "downloadAllowList";
	String PROCESS_NAME_UPDATE_ALLOW_LIST = "updateAllowList";

	String PROCESS_NAME_FULL_DOWNLOAD_ALLOW_LIST = "dsfdev_" + PROCESS_NAME_DOWNLOAD_ALLOW_LIST;
	String PROCESS_NAME_FULL_UPDATE_ALLOW_LIST = "dsfdev_" + PROCESS_NAME_UPDATE_ALLOW_LIST;

	String CODESYSTEM_DSF_ALLOW_LIST = "http://dsf.dev/fhir/CodeSystem/allow-list";
	String CODESYSTEM_DSF_ALLOW_LIST_VALUE_ALLOW_LIST = "allow_list";

	String PROCESS_DSF_URI_BASE = "http://dsf.dev/bpe/Process/";

	String PROFILE_DSF_TASK_DOWNLOAD_ALLOW_LIST = "http://dsf.dev/fhir/StructureDefinition/task-download-allow-list";
	String PROFILE_DSF_TASK_DOWNLOAD_ALLOW_LIST_PROCESS_URI = PROCESS_DSF_URI_BASE + PROCESS_NAME_DOWNLOAD_ALLOW_LIST;
	String PROFILE_DSF_TASK_DOWNLOAD_ALLOW_LIST_MESSAGE_NAME = "downloadAllowListMessage";

	String PROFILE_DSF_TASK_UPDATE_ALLOW_LIST = "http://dsf.dev/fhir/StructureDefinition/task-update-allow-list";
	String PROFILE_DSF_TASK_UPDATE_ALLOW_LIST_PROCESS_URI = PROCESS_DSF_URI_BASE + PROCESS_NAME_UPDATE_ALLOW_LIST;
	String PROFILE_DSF_TASK_UPDATE_ALLOW_LIST_MESSAGE_NAME = "updateAllowListMessage";
}
