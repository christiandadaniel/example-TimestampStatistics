package com.n26.timestampstatistics.service;

import com.n26.timestampstatistics.entity.Statistics;
import com.n26.timestampstatistics.entity.Transaction;

public interface StatisticsService {

	void addTransaction(Transaction transaction);

	void removeOldTransactions(Long minTimeStamp);

	Statistics getCurrentStatistics();

}
