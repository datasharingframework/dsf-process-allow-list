package org.highmed.dsf.bpe.start;

import static org.highmed.dsf.bpe.ConstantsBase.CODESYSTEM_HIGHMED_BPMN;
import static org.highmed.dsf.bpe.ConstantsBase.CODESYSTEM_HIGHMED_BPMN_VALUE_MESSAGE_NAME;
import static org.highmed.dsf.bpe.ConstantsBase.CODESYSTEM_HIGHMED_QUERY_TYPE;
import static org.highmed.dsf.bpe.ConstantsBase.CODESYSTEM_HIGMED_QUERY_TYPE_VALUE_AQL;
import static org.highmed.dsf.bpe.ConstantsBase.NAMINGSYSTEM_HIGHMED_ORGANIZATION_IDENTIFIER;
import static org.highmed.dsf.bpe.ConstantsDataSharing.CODESYSTEM_HIGHMED_DATA_SHARING;
import static org.highmed.dsf.bpe.ConstantsDataSharing.CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_BLOOM_FILTER_CONFIG;
import static org.highmed.dsf.bpe.ConstantsDataSharing.CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_NEEDS_CONSENT_CHECK;
import static org.highmed.dsf.bpe.ConstantsDataSharing.CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_NEEDS_RECORD_LINKAGE;
import static org.highmed.dsf.bpe.ConstantsLocalServices.PROFILE_HIGHMED_TASK_LOCAL_SERVICES_AND_LATEST_VERSION;
import static org.highmed.dsf.bpe.ConstantsLocalServices.PROFILE_HIGHMED_TASK_LOCAL_SERVICES_MESSAGE_NAME;
import static org.highmed.dsf.bpe.ConstantsLocalServices.PROFILE_HIGHMED_TASK_LOCAL_SERVICES_PROCESS_URI_AND_LATEST_VERSION;
import static org.highmed.dsf.bpe.start.ConstantsExampleStarters.NAMINGSYSTEM_HIGHMED_ORGANIZATION_IDENTIFIER_VALUE_MEDIC_1;

import java.util.Date;
import java.util.Random;

import javax.crypto.KeyGenerator;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.highmed.dsf.bpe.variable.BloomFilterConfig;
import org.hl7.fhir.r4.model.Base64BinaryType;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.ResourceType;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.Task;

public abstract class AbstractLocalServicesMedic1ExampleStarter
{
	private static boolean NEEDS_CONSENT_CHECK = true;
	private static boolean NEEDS_RECORD_LINKAGE = true;

	protected void main(String[] args, String baseUrl) throws Exception
	{
		Task task = createStartResource();
		ExampleStarter.forServer(args, baseUrl).startWith(task);
	}

	private Task createStartResource()
	{
		Task task = new Task();

		task.getMeta().addProfile(PROFILE_HIGHMED_TASK_LOCAL_SERVICES_AND_LATEST_VERSION);
		task.setInstantiatesUri(PROFILE_HIGHMED_TASK_LOCAL_SERVICES_PROCESS_URI_AND_LATEST_VERSION);
		task.setStatus(Task.TaskStatus.REQUESTED);
		task.setIntent(Task.TaskIntent.ORDER);
		task.setAuthoredOn(new Date());

		task.getRequester().setType(ResourceType.Organization.name()).getIdentifier()
				.setSystem(NAMINGSYSTEM_HIGHMED_ORGANIZATION_IDENTIFIER)
				.setValue(NAMINGSYSTEM_HIGHMED_ORGANIZATION_IDENTIFIER_VALUE_MEDIC_1);
		task.getRestriction().addRecipient().setType(ResourceType.Organization.name()).getIdentifier()
				.setSystem(NAMINGSYSTEM_HIGHMED_ORGANIZATION_IDENTIFIER)
				.setValue(NAMINGSYSTEM_HIGHMED_ORGANIZATION_IDENTIFIER_VALUE_MEDIC_1);

		task.addInput().setValue(new StringType(PROFILE_HIGHMED_TASK_LOCAL_SERVICES_MESSAGE_NAME)).getType().addCoding()
				.setSystem(CODESYSTEM_HIGHMED_BPMN).setCode(CODESYSTEM_HIGHMED_BPMN_VALUE_MESSAGE_NAME);

		task.addInput().setValue(new StringType("SELECT COUNT(e) FROM EHR e;")).getType().addCoding()
				.setSystem(CODESYSTEM_HIGHMED_QUERY_TYPE).setCode(CODESYSTEM_HIGMED_QUERY_TYPE_VALUE_AQL);
		task.addInput().setValue(new BooleanType(NEEDS_CONSENT_CHECK)).getType().addCoding()
				.setSystem(CODESYSTEM_HIGHMED_DATA_SHARING)
				.setCode(CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_NEEDS_CONSENT_CHECK);
		task.addInput().setValue(new BooleanType(NEEDS_RECORD_LINKAGE)).getType().addCoding()
				.setSystem(CODESYSTEM_HIGHMED_DATA_SHARING)
				.setCode(CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_NEEDS_RECORD_LINKAGE);

		if (NEEDS_RECORD_LINKAGE)
		{
			try
			{
				BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();
				BloomFilterConfig bloomFilterConfig = new BloomFilterConfig(new Random().nextLong(),
						KeyGenerator.getInstance("HmacSHA256", bouncyCastleProvider).generateKey(),
						KeyGenerator.getInstance("HmacSHA3-256", bouncyCastleProvider).generateKey());

				task.addInput().setValue(new Base64BinaryType(bloomFilterConfig.toBytes())).getType().addCoding()
						.setSystem(CODESYSTEM_HIGHMED_DATA_SHARING)
						.setCode(CODESYSTEM_HIGHMED_DATA_SHARING_VALUE_BLOOM_FILTER_CONFIG);
			}
			catch (Exception exception)
			{
				throw new RuntimeException("Could not create BloomFilterConfig", exception);
			}
		}

		return task;
	}
}
