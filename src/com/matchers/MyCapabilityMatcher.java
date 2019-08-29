package com.matchers;

import java.util.Map;

import org.openqa.grid.internal.utils.DefaultCapabilityMatcher;

public class MyCapabilityMatcher extends DefaultCapabilityMatcher {
	private final String resolution = "resolution";

	@Override
	public boolean matches(Map<String, Object> nodeCapability, Map<String, Object> clientCapability) {
		boolean defaultChecks = super.matches(nodeCapability, clientCapability);
		if (!clientCapability.containsKey(resolution)) {
			return defaultChecks;
		}
		return (defaultChecks && nodeCapability.get(resolution).equals(clientCapability.get(resolution)));
	}

}