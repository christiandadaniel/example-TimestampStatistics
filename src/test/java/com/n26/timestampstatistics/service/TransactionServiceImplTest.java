package com.n26.timestampstatistics.service;

import com.n26.timestampstatistics.config.Parameters;
import com.n26.timestampstatistics.entity.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.n26.timestampstatistics.TestDefaultEntities.defaultTransactionNow;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TransactionServiceImplTest {
	@Mock
	private Parameters parameters;

	@Mock
	private StatisticsService statisticsService;

	private TransactionServiceImpl transactionService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		transactionService = new TransactionServiceImpl(parameters, statisticsService);
	}

	@Test
	public void createTransactionNow() {
		//given
		when(parameters.getCurrentMinimumTime()).thenReturn(100000L);
		Transaction transaction = defaultTransactionNow();

		//when
		boolean ret = transactionService.createTransaction(transaction);

		//then
		assertTrue(ret);
		verify(parameters, times(1)).getCurrentMinimumTime();
		verify(statisticsService, times(1)).addTransaction(transaction);
		verifyNoMoreInteractions(parameters);
		verifyNoMoreInteractions(statisticsService);
	}

	@Test
	public void createTransactionClosePast() {
		//given
		when(parameters.getCurrentMinimumTime()).thenReturn(100000L);
		Transaction transaction = defaultTransactionNow();
		transaction.setTimestamp(100001L);


		//when
		boolean ret = transactionService.createTransaction(transaction);

		//then
		assertTrue(ret);
		verify(parameters, times(1)).getCurrentMinimumTime();
		verify(statisticsService, times(1)).addTransaction(transaction);
		verifyNoMoreInteractions(parameters);
		verifyNoMoreInteractions(statisticsService);
	}

	@Test
	public void createTransactionFarPast() {
		//given
		when(parameters.getCurrentMinimumTime()).thenReturn(100000L);
		Transaction transaction = defaultTransactionNow();
		transaction.setTimestamp(99999L);

		//when
		boolean ret = transactionService.createTransaction(transaction);

		//then
		assertFalse(ret);
		verify(parameters, times(1)).getCurrentMinimumTime();
		verifyNoMoreInteractions(parameters);
		verifyZeroInteractions(statisticsService);
	}
}