package dev.dsf.bpe;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import dev.dsf.bpe.spring.config.AllowListConfig;
import dev.dsf.bpe.v1.ProcessPluginDefinition;

public class AllowListProcessPluginDefinition implements ProcessPluginDefinition
{
	public static final String VERSION = "1.0.0.0";
	public static final LocalDate RELEASE_DATE = LocalDate.of(2023, 4, 22);

	@Override
	public String getName()
	{
		return "dsf-process-allow-list";
	}

	@Override
	public String getVersion()
	{
		return VERSION;
	}

	@Override
	public LocalDate getReleaseDate()
	{
		return RELEASE_DATE;
	}

	@Override
	public List<String> getProcessModels()
	{
		return List.of("bpe/updateAllowList.bpmn", "bpe/downloadAllowList.bpmn");
	}

	@Override
	public List<Class<?>> getSpringConfigurations()
	{
		return List.of(AllowListConfig.class);
	}

	@Override
	public Map<String, List<String>> getFhirResourcesByProcessId()
	{
		var c = "fhir/CodeSystem/dsf-allow-list.xml";

		var aDown = "fhir/ActivityDefinition/dsf-downloadAllowList.xml";
		var aUp = "fhir/ActivityDefinition/dsf-updateAllowList.xml";

		var sDown = "fhir/StructureDefinition/dsf-task-download-allow-list.xml";
		var sUp = "fhir/StructureDefinition/dsf-task-update-allow-list.xml";

		var v = "fhir/ValueSet/dsf-allow-list.xml";

		return Map.of(ConstantsAllowList.PROCESS_NAME_FULL_DOWNLOAD_ALLOW_LIST, Arrays.asList(c, aDown, sDown, v),
				ConstantsAllowList.PROCESS_NAME_FULL_UPDATE_ALLOW_LIST, Arrays.asList(c, aUp, sUp, v));
	}
}
