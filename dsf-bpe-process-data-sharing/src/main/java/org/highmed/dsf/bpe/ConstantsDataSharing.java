package org.highmed.dsf.bpe;

import static org.highmed.dsf.bpe.ConstantsBase.PROCESS_HIGHMED_URI_BASE;
import static org.highmed.dsf.bpe.DataSharingProcessPluginDefinition.VERSION;

public interface ConstantsDataSharing
{
	String BPMN_EXECUTION_ERROR_CODE_SINGLE_MEDIC_DATA_SHARING_RESULT = "errorSingleMedicDataSharingResult";
	String BPMN_EXECUTION_ERROR_CODE_MULTI_MEDIC_RECEIVED_DATA_SHARING_RESULT = "errorMultiMedicReceivedDataSharingResult";
	String BPMN_EXECUTION_ERROR_CODE_MULTI_MEDIC_PSEUDONYMIZED_DATA_SHARING_RESULT = "errorMultiMedicPseudonymizedDataSharingResult";

	String BPMN_EXECUTION_VARIABLE_RESEARCH_STUDY = "researchStudy";
	String BPMN_EXECUTION_VARIABLE_RESEARCH_STUDY_IDENTIFIER = "researchStudyIdentifier";
	String BPMN_EXECUTION_VARIABLE_NEEDS_CONSENT_CHECK = "needsConsentCheck";
	String BPMN_EXECUTION_VARIABLE_NEEDS_RECORD_LINKAGE = "needsRecordLinkage";
	String BPMN_EXECUTION_VARIABLE_BLOOM_FILTER_CONFIG = "bloomFilterConfig";
	String BPMN_EXECUTION_VARIABLE_MDAT_AES_KEY = "mdatAesKey";
	String BPMN_EXECUTION_VARIABLE_COHORTS = "cohorts";
	String BPMN_EXECUTION_VARIABLE_QUERIES = "queries";
	String BPMN_EXECUTION_VARIABLE_QUERY_RESULTS = "queryResults";
	String BPMN_EXECUTION_VARIABLE_FINAL_QUERY_RESULTS = "finalQueryResults";

	String CODESYSTEM_HIGHMED_DATA_SHARING = "http://highmed.org/fhir/CodeSystem/data-sharing";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_RESEARCH_STUDY_REFERENCE = "research-study-reference";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_RESEARCH_STUDY_IDENTIFIER = "research-study-identifier";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_CONSORTIUM_IDENTIFIER = "consortium-identifier";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_NEEDS_RECORD_LINKAGE = "needs-record-linkage";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_NEEDS_CONSENT_CHECK = "needs-consent-check";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_BLOOM_FILTER_CONFIG = "bloom-filter-configuration";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_MDAT_AES_KEY = "mdat-aes-key";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_PARTICIPATING_MEDIC_CORRELATION_KEY = "medic-correlation-key";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_SINGLE_MEDIC_COUNT_RESULT = "single-medic-count-result";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_SINGLE_MEDIC_RESULT_SET_REFERENCE = "single-medic-result-set-reference";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_SINGLE_MEDIC_RESULT_SHARE = "single-medic-result-share";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_MULTI_MEDIC_COUNT_RESULT = "multi-medic-count-result";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_MULTI_MEDIC_RESULT_SET_REFERENCE = "multi-medic-result-set-reference";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_MULTI_MEDIC_RESULT_SHARE = "multi-medic-result-share";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_PARTICIPATING_MEDICS = "participating-medics";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_REQUEST_FORM_REFERENCE = "request-form-reference";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_CONTRACT_REFERENCE = "contract-reference";
	String CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_FEASIBILITY_QUERY_REFERENCE = "feasibility-query-reference";

	String NAMINGSYSTEM_HIGHMED_RESEARCH_STUDY_IDENTIFIER = "http://highmed.org/sid/research-study-identifier";

	String EXTENSION_HIGHMED_PARTICIPATING_MEDIC = "http://highmed.org/fhir/StructureDefinition/extension-participating-medic";
	String EXTENSION_HIGHMED_PARTICIPATING_TTP = "http://highmed.org/fhir/StructureDefinition/extension-participating-ttp";
	String EXTENSION_HIGHMED_GROUP_ID = "http://highmed.org/fhir/StructureDefinition/extension-group-id";
	String EXTENSION_HIGHMED_QUERY = "http://highmed.org/fhir/StructureDefinition/extension-query";

	String PROFILE_HIGHMED_TASK_REQUEST_DATA_SHARING = "http://highmed.org/fhir/StructureDefinition/task-request-data-sharing";
	String PROFILE_HIGHMED_TASK_REQUEST_DATA_SHARING_AND_VERSION = "http://highmed.org/fhir/StructureDefinition/task-request-data-sharing"
			+ "|" + VERSION;
	String PROFILE_HIGHMED_TASK_REQUEST_DATA_SHARING_PROCESS_URI = PROCESS_HIGHMED_URI_BASE + "requestDataSharing/";
	String PROFILE_HIGHMED_TASK_REQUEST_DATA_SHARING_PROCESS_URI_AND_LATEST_VERSION = PROFILE_HIGHMED_TASK_REQUEST_DATA_SHARING_PROCESS_URI
			+ VERSION;
	String PROFILE_HIGHMED_TASK_REQUEST_DATA_SHARING_MESSAGE_NAME = "requestDataSharingMessage";

	String PROFILE_HIGHMED_TASK_EXECUTE_DATA_SHARING = "http://highmed.org/fhir/StructureDefinition/task-execute-data-sharing";
	String PROFILE_HIGHMED_TASK_EXECUTE_DATA_SHARING_AND_VERSION = "http://highmed.org/fhir/StructureDefinition/task-execute-data-sharing"
			+ "|" + VERSION;
	String PROFILE_HIGHMED_TASK_EXECUTE_DATA_SHARING_PROCESS_URI = PROCESS_HIGHMED_URI_BASE + "executeDataSharing/";
	String PROFILE_HIGHMED_TASK_EXECUTE_DATA_SHARING_PROCESS_URI_AND_LATEST_VERSION = PROFILE_HIGHMED_TASK_EXECUTE_DATA_SHARING_PROCESS_URI
			+ VERSION;
	String PROFILE_HIGHMED_TASK_EXECUTE_DATA_SHARING_MESSAGE_NAME = "executeDataSharingMessage";

	String PROFILE_HIGHMED_TASK_COMPUTE_DATA_SHARING = "http://highmed.org/fhir/StructureDefinition/task-compute-data-sharing";
	String PROFILE_HIGHMED_TASK_COMPUTE_DATA_SHARING_AND_VERSION = "http://highmed.org/fhir/StructureDefinition/task-compute-data-sharing"
			+ "|" + VERSION;
	String PROFILE_HIGHMED_TASK_COMPUTE_DATA_SHARING_PROCESS_URI = PROCESS_HIGHMED_URI_BASE + "computeDataSharing/";
	String PROFILE_HIGHMED_TASK_COMPUTE_DATA_SHARING_PROCESS_URI_AND_LATEST_VERSION = PROFILE_HIGHMED_TASK_COMPUTE_DATA_SHARING_PROCESS_URI
			+ VERSION;
	String PROFILE_HIGHMED_TASK_COMPUTE_DATA_SHARING_MESSAGE_NAME = "computeDataSharingMessage";

	String PROFILE_HIGHMED_TASK_SINGLE_MEDIC_RESULT_DATA_SHARING = "http://highmed.org/fhir/StructureDefinition/task-single-medic-result-data-sharing";
	String PROFILE_HIGHMED_TASK_SINGLE_MEDIC_RESULT_DATA_SHARING_AND_VERSION = "http://highmed.org/fhir/StructureDefinition/task-single-medic-result-data-sharing"
			+ "|" + VERSION;
	String PROFILE_HIGHMED_TASK_SINGLE_MEDIC_RESULT_DATA_SHARING_MESSAGE_NAME = "resultSingleMedicDataSharingMessage";

	String PROFILE_HIGHMED_TASK_MULTI_MEDIC_RESULT_DATA_SHARING = "http://highmed.org/fhir/StructureDefinition/task-multi-medic-result-data-sharing";
	String PROFILE_HIGHMED_TASK_MULTI_MEDIC_RESULT_DATA_SHARING_AND_VERSION = "http://highmed.org/fhir/StructureDefinition/task-multi-medic-result-data-sharing"
			+ "|" + VERSION;
	String PROFILE_HIGHMED_TASK_MULTI_MEDIC_RESULT_DATA_SHARING_MESSAGE_NAME = "resultMultiMedicDataSharingMessage";

	String PROFILE_HIGHMED_TASK_ERROR_DATA_SHARING = "http://highmed.org/fhir/StructureDefinition/task-multi-medic-error-data-sharing";
	String PROFILE_HIGHMED_TASK_ERROR_DATA_SHARING_VERSION = "http://highmed.org/fhir/StructureDefinition/task-multi-medic-error-data-sharing"
			+ "|" + VERSION;
	String PROFILE_HIGHMED_TASK_ERROR_DATA_SHARING_MESSAGE_NAME = "errorMultiMedicDataSharingMessage";
}
