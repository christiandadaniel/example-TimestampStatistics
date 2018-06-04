package com.n26.timestampstatistics;

import com.n26.timestampstatistics.entity.Statistics;
import com.n26.timestampstatistics.entity.Transaction;

import java.time.Instant;

public class TestDefaultEntities {

	public static Transaction defaultTransactionNow() {
		Transaction transaction = new Transaction();
		transaction.setAmount(13.0);
		transaction.setTimestamp(Instant.now().toEpochMilli());
		return transaction;
	}

	public static Transaction defaultTransaction() {
		Transaction transaction = new Transaction();
		transaction.setAmount(12.3);
		transaction.setTimestamp(1478192204000L);
		return transaction;
	}

	public static String defaultTransactionJson() {
		return "{\"amount\": 12.3, \"timestamp\": 1478192204000}";
	}

	public static Statistics defaultStatistics() {
		Statistics ret = new Statistics();
		ret.setSum(1000.0);
		ret.setAvg(100.0);
		ret.setMax(200.0);
		ret.setMin(50.0);
		ret.setCount(10L);
		return ret;
	}

	public static String defaultStatisticsJson() {
		return "{\"sum\": 1000, \"avg\": 100, \"max\": 200, \"min\": 50, \"count\": 10}";
	}
}
