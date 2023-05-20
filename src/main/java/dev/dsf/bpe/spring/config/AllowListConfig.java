package dev.dsf.bpe.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import dev.dsf.bpe.service.DownloadAllowList;
import dev.dsf.bpe.service.UpdateAllowList;
import dev.dsf.bpe.v1.ProcessPluginApi;

@Configuration
public class AllowListConfig
{
	@Autowired
	ProcessPluginApi api;

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public UpdateAllowList updateAllowList()
	{
		return new UpdateAllowList(api);
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public DownloadAllowList downloadAllowList()
	{
		return new DownloadAllowList(api);
	}
}
