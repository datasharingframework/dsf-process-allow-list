package org.highmed.dsf.bpe.message;

import java.util.stream.Stream;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.highmed.dsf.bpe.Constants;
import org.highmed.dsf.fhir.client.FhirWebserviceClientProvider;
import org.highmed.dsf.fhir.organization.OrganizationProvider;
import org.highmed.dsf.fhir.task.AbstractTaskMessageSend;
import org.highmed.dsf.fhir.task.TaskHelper;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.ResearchStudy;
import org.hl7.fhir.r4.model.Task;

public class SendFeasibilityRequest extends AbstractTaskMessageSend
{
	public SendFeasibilityRequest(OrganizationProvider organizationProvider,
			FhirWebserviceClientProvider clientProvider, TaskHelper taskHelper)
	{
		super(organizationProvider, clientProvider, taskHelper);
	}

	@Override
	protected Stream<Task.ParameterComponent> getAdditionalInputParameters(DelegateExecution execution)
	{
		ResearchStudy researchStudy = (ResearchStudy) execution.getVariable(Constants.VARIABLE_RESEARCH_STUDY);
		return Stream.of(toInputParameterResearchStudyReference(researchStudy.getId()));
	}

	private Task.ParameterComponent toInputParameterResearchStudyReference(String researchStudyId)
	{
		return new Task.ParameterComponent(new CodeableConcept(new Coding(Constants.CODESYSTEM_HIGHMED_TASK_INPUT,
				Constants.CODESYSTEM_HIGHMED_TASK_INPUT_VALUE_RESEARCH_STUDY_REFERENCE, null)),
				new Reference().setReference(researchStudyId));
	}
}
