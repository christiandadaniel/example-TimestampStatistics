package com.n26.timestampstatistics.service;

import com.n26.timestampstatistics.config.Parameters;
import com.n26.timestampstatistics.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

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
		//check if transaction wont be considered
		long minimumTime = parameters.getCurrentMinimumTime();
		if (transaction.getTimestamp() < minimumTime) return false;

		// Making sure this call is O(1) time
		CompletableFuture.runAsync(() -> statisticsService.addTransaction(transaction));

		return true;
	}
}
