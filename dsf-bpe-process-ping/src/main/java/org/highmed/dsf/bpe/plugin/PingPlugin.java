package org.highmed.dsf.bpe.plugin;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

public class PingPlugin extends AbstractProcessEnginePlugin
{
	private static final String PING_FILE = "ping.bpmn";
	private static final String PONG_FILE = "pong.bpmn";

	@Override
	public void postProcessEngineBuild(ProcessEngine processEngine)
	{
		BpmnModelInstance pingProcess = readAndValidateModel("/" + PING_FILE);
		delpoy(processEngine, PING_FILE, pingProcess);

		BpmnModelInstance pongProcess = readAndValidateModel("/" + PONG_FILE);
		delpoy(processEngine, PONG_FILE, pongProcess);
	}
}
