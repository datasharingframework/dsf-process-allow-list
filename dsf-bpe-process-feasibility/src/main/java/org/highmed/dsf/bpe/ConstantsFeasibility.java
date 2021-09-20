package org.highmed.dsf.bpe;

import static org.highmed.dsf.bpe.ConstantsBase.PROCESS_HIGHMED_URI_BASE;
import static org.highmed.dsf.bpe.FeasibilityProcessPluginDefinition.VERSION;

public interface ConstantsFeasibility
{
	String BPMN_EXECUTION_ERROR_CODE_MULTI_MEDIC_FEASIBILITY_RESULT = "errorMultiMedicFeasibilityResult";

	// Must be 3 or larger, as otherwise it is possible to draw conclusions about the individual MeDICs
	// (if I already know the cohort size in my MeDIC)
	int MIN_PARTICIPATING_MEDICS = 3;
	int MIN_COHORT_DEFINITIONS = 1;
	String FEASIBILITY_QUERY_PREFIX = "SELECT COUNT";

	String PROFILE_HIGHMED_TASK_REQUEST_FEASIBILITY = "http://highmed.org/fhir/StructureDefinition/task-request-feasibility";
	String PROFILE_HIGHMED_TASK_REQUEST_FEASIBILITY_AND_LATEST_VERSION = PROFILE_HIGHMED_TASK_REQUEST_FEASIBILITY + "|"
			+ VERSION;
	String PROFILE_HIGHMED_TASK_REQUEST_FEASIBILITY_PROCESS_URI = PROCESS_HIGHMED_URI_BASE + "requestFeasibility/";
	String PROFILE_HIGHMED_TASK_REQUEST_FEASIBILITY_PROCESS_URI_AND_LATEST_VERSION = PROFILE_HIGHMED_TASK_REQUEST_FEASIBILITY_PROCESS_URI
			+ VERSION;
	String PROFILE_HIGHMED_TASK_REQUEST_FEASIBILITY_MESSAGE_NAME = "requestFeasibilityMessage";

	String PROFILE_HIGHMED_TASK_LOCAL_SERVICES_PROCESS_URI = PROCESS_HIGHMED_URI_BASE + "localServicesIntegration/";

	String PROFILE_HIGHMED_TASK_EXECUTE_FEASIBILITY = "http://highmed.org/fhir/StructureDefinition/task-execute-feasibility";
	String PROFILE_HIGHMED_TASK_EXECUTE_FEASIBILITY_AND_LATEST_VERSION = PROFILE_HIGHMED_TASK_EXECUTE_FEASIBILITY + "|"
			+ VERSION;
	String PROFILE_HIGHMED_TASK_EXECUTE_FEASIBILITY_PROCESS_URI = PROCESS_HIGHMED_URI_BASE + "executeFeasibility/";
	String PROFILE_HIGHMED_TASK_EXECUTE_FEASIBILITY_PROCESS_URI_AND_LATEST_VERSION = PROFILE_HIGHMED_TASK_EXECUTE_FEASIBILITY_PROCESS_URI
			+ VERSION;
	String PROFILE_HIGHMED_TASK_EXECUTE_FEASIBILITY_MESSAGE_NAME = "executeFeasibilityMessage";

	String PROFILE_HIGHMED_TASK_COMPUTE_FEASIBILITY = "http://highmed.org/fhir/StructureDefinition/task-compute-feasibility";
	String PROFILE_HIGHMED_TASK_COMPUTE_FEASIBILITY_AND_LATEST_VERSION = PROFILE_HIGHMED_TASK_COMPUTE_FEASIBILITY + "|"
			+ VERSION;
	String PROFILE_HIGHMED_TASK_COMPUTE_FEASIBILITY_PROCESS_URI = PROCESS_HIGHMED_URI_BASE + "computeFeasibility/";
	String PROFILE_HIGHMED_TASK_COMPUTE_FEASIBILITY_PROCESS_URI_AND_LATEST_VERSION = PROFILE_HIGHMED_TASK_COMPUTE_FEASIBILITY_PROCESS_URI
			+ VERSION;
	String PROFILE_HIGHMED_TASK_COMPUTE_FEASIBILITY_MESSAGE_NAME = "computeFeasibilityMessage";

	String PROFILE_HIGHMED_TASK_SINGLE_MEDIC_RESULT_FEASIBILITY = "http://highmed.org/fhir/StructureDefinition/task-single-medic-result-feasibility";
	String PROFILE_HIGHMED_TASK_SINGLE_MEDIC_RESULT_FEASIBILITY_AND_LATEST_VERSION = PROFILE_HIGHMED_TASK_SINGLE_MEDIC_RESULT_FEASIBILITY
			+ "|" + VERSION;
	String PROFILE_HIGHMED_TASK_SINGLE_MEDIC_RESULT_FEASIBILITY_MESSAGE_NAME = "resultSingleMedicFeasibilityMessage";

	String PROFILE_HIGHMED_TASK_MULTI_MEDIC_RESULT_FEASIBILITY = "http://highmed.org/fhir/StructureDefinition/task-multi-medic-result-feasibility";
	String PROFILE_HIGHMED_TASK_MULTI_MEDIC_RESULT_FEASIBILITY_AND_LATEST_VERSION = PROFILE_HIGHMED_TASK_MULTI_MEDIC_RESULT_FEASIBILITY
			+ "|" + VERSION;
	String PROFILE_HIGHMED_TASK_MULTI_MEDIC_RESULT_FEASIBILITY_MESSAGE_NAME = "resultMultiMedicFeasibilityMessage";

	String PROFILE_HIGHMED_TASK_ERROR_FEASIBILITY = "http://highmed.org/fhir/StructureDefinition/task-multi-medic-error-feasibility";
	String PROFILE_HIGHMED_TASK_ERROR_FEASIBILITY_AND_LATEST_VERSION = PROFILE_HIGHMED_TASK_ERROR_FEASIBILITY + "|"
			+ VERSION;
	String PROFILE_HIGHMED_TASK_ERROR_FEASIBILITY_MESSAGE_NAME = "errorMultiMedicFeasibilityMessage";
}
