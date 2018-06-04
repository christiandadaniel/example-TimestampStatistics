package com.n26.timestampstatistics.controller;

import com.n26.timestampstatistics.entity.Statistics;
import com.n26.timestampstatistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/statistics")
public class StatisticsController {

	private StatisticsService statisticsService;

	@Autowired
	public StatisticsController(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}


	@GetMapping
	public Statistics createTransaction() {
		return statisticsService.getCurrentStatistics();
	}
}
