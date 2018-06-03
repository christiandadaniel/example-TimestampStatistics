package com.n26.timestampstatistics.service;

import com.n26.timestampstatistics.config.Parameters;
import com.n26.timestampstatistics.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

	private Parameters parameters;
	private StatisticsService statisticsService;

	@Autowired
	public TransactionServiceImpl(Parameters parameters, StatisticsService statisticsService) {
		this.parameters = parameters;
		this.statisticsService = statisticsService;
	}

	@Override
	public boolean createTransaction(Transaction transaction) {
		long currentTimeMillis = System.currentTimeMillis();

		//check if transaction wont be considered
		long minimumTime = currentTimeMillis - parameters.getInterval();
		if (transaction.getTimestamp() < minimumTime) return false;

		statisticsService.addTransaction(transaction);

		return true;
	}
}
