package com.n26.timestampstatistics.controller;

import com.n26.timestampstatistics.controller.exception.OldTransactionException;
import com.n26.timestampstatistics.entity.Transaction;
import com.n26.timestampstatistics.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("/transactions")
public class TransactionController {

	private TransactionService transactionService;

	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) // 201
	public void createTransaction(@RequestBody Transaction transaction) throws OldTransactionException {
		boolean ok = transactionService.createTransaction(transaction);

		if (!ok) throw new OldTransactionException();
	}
}
