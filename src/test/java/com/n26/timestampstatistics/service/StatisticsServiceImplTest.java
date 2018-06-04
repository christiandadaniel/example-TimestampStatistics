package com.n26.timestampstatistics.service;

import com.n26.timestampstatistics.entity.Statistics;
import com.n26.timestampstatistics.entity.Transaction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatisticsServiceImplTest {

	private StatisticsServiceImpl statisticsService;

	@Before
	public void setUp() {
		this.statisticsService = new StatisticsServiceImpl();
	}

	@Test
	public void addTransaction() {
		// given
		statisticsService.addTransaction(transactionOf(3.0, 10L));
		statisticsService.addTransaction(transactionOf(5.0, 20L));
		statisticsService.addTransaction(transactionOf(7.0, 30L));

		// when
		Statistics currentStatistics = this.statisticsService.getCurrentStatistics();

		// then
		assertEquals(3L, (long) currentStatistics.getCount());
		assertEquals(15.0, currentStatistics.getSum(), 0.001);
		assertEquals(5.0, currentStatistics.getAvg(), 0.001);
		assertEquals(3.0, currentStatistics.getMin(), 0.001);
		assertEquals(7.0, currentStatistics.getMax(), 0.001);
	}

	@Test
	public void addTransactionReplicates() {
		// given
		statisticsService.addTransaction(transactionOf(1.0, 10L));
		statisticsService.addTransaction(transactionOf(1.0, 10L));
		statisticsService.addTransaction(transactionOf(1.0, 10L));

		// when
		Statistics currentStatistics = this.statisticsService.getCurrentStatistics();

		// then
		assertEquals(3L, (long) currentStatistics.getCount());
		assertEquals(3.0, currentStatistics.getSum(), 0.001);
		assertEquals(1.0, currentStatistics.getAvg(), 0.001);
		assertEquals(1.0, currentStatistics.getMin(), 0.001);
		assertEquals(1.0, currentStatistics.getMax(), 0.001);
	}

	@Test
	public void removeOldTransactions() {
		// given
		statisticsService.addTransaction(transactionOf(5.0, 20L));
		statisticsService.addTransaction(transactionOf(7.0, 30L));
		statisticsService.addTransaction(transactionOf(1.0, 50L));
		statisticsService.addTransaction(transactionOf(7.0, 40L));
		statisticsService.addTransaction(transactionOf(3.0, 10L));

		// when
		this.statisticsService.removeOldTransactions(30L);
		Statistics currentStatistics = this.statisticsService.getCurrentStatistics();

		// then
		assertEquals(3L, (long) currentStatistics.getCount());
		assertEquals(15.0, currentStatistics.getSum(), 0.001);
		assertEquals(5.0, currentStatistics.getAvg(), 0.001);
		assertEquals(1.0, currentStatistics.getMin(), 0.001);
		assertEquals(7.0, currentStatistics.getMax(), 0.001);
	}

	@Test
	public void removeOldTransactionsEmpty() {
		// given

		// when
		this.statisticsService.removeOldTransactions(10000L);
		Statistics currentStatistics = this.statisticsService.getCurrentStatistics();

		// then
		verifyStatisticsZeroes(currentStatistics);
	}

	@Test
	public void getCurrentStatisticsEmpty() {
		// given

		// when
		Statistics currentStatistics = this.statisticsService.getCurrentStatistics();

		// then
		verifyStatisticsZeroes(currentStatistics);
	}

	private void verifyStatisticsZeroes(Statistics currentStatistics) {
		assertEquals(0L, (long) currentStatistics.getCount());
		assertEquals(0.0, currentStatistics.getSum(), 0.001);
		assertEquals(0.0, currentStatistics.getAvg(), 0.001);
		assertEquals(0.0, currentStatistics.getMin(), 0.001);
		assertEquals(0.0, currentStatistics.getMax(), 0.001);
	}

	private Transaction transactionOf(double amount, long timestamp) {
		Transaction ret = new Transaction();

		ret.setAmount(amount);
		ret.setTimestamp(timestamp);

		return ret;
	}
}