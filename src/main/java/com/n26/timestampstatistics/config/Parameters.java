package com.n26.timestampstatistics.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
public class Parameters {

	@Value("${interval}")
	private Long interval;

	public long getCurrentMinimumTime() {
		return Instant.now().toEpochMilli() - interval;
	}
}
