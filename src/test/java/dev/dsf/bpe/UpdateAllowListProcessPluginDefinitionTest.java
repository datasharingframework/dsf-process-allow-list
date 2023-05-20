package dev.dsf.bpe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import dev.dsf.bpe.v1.ProcessPluginDefinition;

public class UpdateAllowListProcessPluginDefinitionTest
{
	@Test
	public void testResourceLoading()
	{
		ProcessPluginDefinition definition = new AllowListProcessPluginDefinition();
		Map<String, List<String>> resourcesByProcessId = definition.getFhirResourcesByProcessId();

		var download = resourcesByProcessId.get(ConstantsAllowList.PROCESS_NAME_FULL_DOWNLOAD_ALLOW_LIST);
		assertNotNull(download);
		assertEquals(4, download.stream().filter(this::exists).count());

		var update = resourcesByProcessId.get(ConstantsAllowList.PROCESS_NAME_FULL_UPDATE_ALLOW_LIST);
		assertNotNull(update);
		assertEquals(4, update.stream().filter(this::exists).count());
	}

	private boolean exists(String file)
	{
		return getClass().getClassLoader().getResourceAsStream(file) != null;
	}
}
