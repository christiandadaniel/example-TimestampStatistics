package com.n26.timestampstatistics.jobs;

import com.n26.timestampstatistics.config.Parameters;
import com.n26.timestampstatistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class RemoveTask {

	private Parameters parameters;
	private StatisticsService statisticsService;

	@Autowired
	public RemoveTask(Parameters parameters, StatisticsService statisticsService) {
		this.parameters = parameters;
		this.statisticsService = statisticsService;
	}

	@Scheduled(fixedRateString = "${refreshRate}")
	public void checkAndRemoveOldTransactions() {
		statisticsService.removeOldTransactions(parameters.getCurrentMinimumTime());
	}
}
