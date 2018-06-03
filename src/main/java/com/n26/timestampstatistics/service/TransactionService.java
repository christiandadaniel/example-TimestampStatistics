package com.n26.timestampstatistics.service;

import com.n26.timestampstatistics.entity.Transaction;

public interface TransactionService {

	boolean createTransaction(Transaction transaction);

}
