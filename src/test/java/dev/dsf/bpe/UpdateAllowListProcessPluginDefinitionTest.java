package dev.dsf.bpe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.core.env.StandardEnvironment;

import ca.uhn.fhir.context.FhirContext;
import dev.dsf.fhir.resources.ResourceProvider;

public class UpdateAllowListProcessPluginDefinitionTest
{
	@Test
	public void testResourceLoading() throws Exception
	{
		ProcessPluginDefinition definition = new UpdateAllowListProcessPluginDefinition();
		ResourceProvider provider = definition.getResourceProvider(FhirContext.forR4(), getClass().getClassLoader(),
				new StandardEnvironment());
		assertNotNull(provider);

		var download = provider.getResources(ConstantsUpdateAllowList.PROCESS_NAME_FULL_DOWNLOAD_ALLOW_LIST + "/"
				+ UpdateAllowListProcessPluginDefinition.VERSION);
		assertNotNull(download);
		assertEquals(4, download.count());

		var update = provider.getResources(ConstantsUpdateAllowList.PROCESS_NAME_FULL_UPDATE_ALLOW_LIST + "/"
				+ UpdateAllowListProcessPluginDefinition.VERSION);
		assertNotNull(update);
		assertEquals(4, update.count());
	}
}
