package com.n26.timestampstatistics;

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
}
