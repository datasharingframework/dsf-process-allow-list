package org.highmed.dsf.bpe.message;

import static org.highmed.dsf.bpe.ConstantsBase.EXTENSION_HIGHMED_GROUP_ID;
import static org.highmed.dsf.bpe.ConstantsDataSharing.CODESYSTEM_HIGHMED_DATA_SHARING;
import static org.highmed.dsf.bpe.ConstantsDataSharing.CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_SINGLE_MEDIC_RESULT_SHARE;
import static org.highmed.dsf.bpe.ConstantsFeasibilityMpc.BPMN_EXECUTION_VARIABLE_QUERY_RESULTS_SINGLE_MEDIC_SHARES;

import java.util.stream.Stream;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.highmed.dsf.bpe.mpc.QueryResultShare;
import org.highmed.dsf.bpe.mpc.QueryResultShares;
import org.highmed.dsf.fhir.authorization.read.ReadAccessHelper;
import org.highmed.dsf.fhir.client.FhirWebserviceClientProvider;
import org.highmed.dsf.fhir.organization.OrganizationProvider;
import org.highmed.dsf.fhir.task.AbstractTaskMessageSend;
import org.highmed.dsf.fhir.task.TaskHelper;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Task;
import org.hl7.fhir.r4.model.Task.ParameterComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.context.FhirContext;

public class SendSingleMedicResultShare extends AbstractTaskMessageSend
{
	private static final Logger logger = LoggerFactory.getLogger(SendSingleMedicResultShare.class);

	public SendSingleMedicResultShare(FhirWebserviceClientProvider clientProvider, TaskHelper taskHelper,
			ReadAccessHelper readAccessHelper, OrganizationProvider organizationProvider, FhirContext fhirContext)
	{
		super(clientProvider, taskHelper, readAccessHelper, organizationProvider, fhirContext);
	}

	@Override
	protected Stream<Task.ParameterComponent> getAdditionalInputParameters(DelegateExecution execution)
	{
		String targetIdentifier = getTarget(execution).getTargetOrganizationIdentifierValue();
		QueryResultShares shares = (QueryResultShares) execution
				.getVariable(BPMN_EXECUTION_VARIABLE_QUERY_RESULTS_SINGLE_MEDIC_SHARES);

		return shares.getShares().stream().filter(s -> s.getOrganizationIdentifier().equals(targetIdentifier))
				.map(this::toInput);
	}

	private Task.ParameterComponent toInput(QueryResultShare share)
	{
		ParameterComponent input = getTaskHelper().createInputUnsignedInt(CODESYSTEM_HIGHMED_DATA_SHARING,
				CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_SINGLE_MEDIC_RESULT_SHARE,
				share.getArithmeticShare().getValue().intValue());
		input.addExtension(createCohortIdExtension(share.getCohortId()));

		logger.info("Sending SingleMedicShare with cohortId={} and size={}", share.getCohortId(),
				share.getArithmeticShare().getValue().intValue());

		return input;
	}

	private Extension createCohortIdExtension(String cohortId)
	{
		return new Extension(EXTENSION_HIGHMED_GROUP_ID, new Reference(cohortId));
	}
}
