package dev.dsf.bpe.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ca.uhn.fhir.context.FhirContext;
import dev.dsf.bpe.service.DownloadAllowList;
import dev.dsf.bpe.service.UpdateAllowList;
import dev.dsf.fhir.authorization.read.ReadAccessHelper;
import dev.dsf.fhir.client.FhirWebserviceClientProvider;
import dev.dsf.fhir.task.TaskHelper;

@Configuration
public class UpdateAllowListConfig
{
	@Autowired
	private FhirWebserviceClientProvider clientProvider;

	@Autowired
	private TaskHelper taskHelper;

	@Autowired
	private ReadAccessHelper readAccessHelper;

	@Autowired
	private FhirContext fhirContext;

	@Bean
	public UpdateAllowList updateAllowList()
	{
		return new UpdateAllowList(clientProvider, taskHelper, readAccessHelper);
	}

	@Bean
	public DownloadAllowList downloadAllowList()
	{
		return new DownloadAllowList(clientProvider, taskHelper, readAccessHelper, fhirContext);
	}
}
