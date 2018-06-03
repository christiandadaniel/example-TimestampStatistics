package com.n26.timestampstatistics.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Parameters {

	@Value("${interval}")
	private Long interval;

	@Value("${refreshRate}")
	private Long refreshRate;

	public long getInterval() {
		return interval;
	}

	public long getRefreshRate() {
		return refreshRate;
	}
}
