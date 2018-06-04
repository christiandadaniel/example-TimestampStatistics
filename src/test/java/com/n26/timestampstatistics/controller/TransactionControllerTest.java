package com.n26.timestampstatistics.controller;

import com.n26.timestampstatistics.entity.Transaction;
import com.n26.timestampstatistics.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.n26.timestampstatistics.TestDefaultEntities.defaultTransaction;
import static com.n26.timestampstatistics.TestDefaultEntities.defaultTransactionJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerTest {

	private static final String URL_TEMPLATE = "/transactions";

	@Mock
	private TransactionService transactionService;

	private TransactionController transactionController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		transactionController = new TransactionController(transactionService);

		mockMvc = MockMvcBuilders.standaloneSetup(this.transactionController).build();
	}

	@Test
	public void createTransactionOK() throws Exception {
		//given
		Transaction defaultTransaction = defaultTransaction();
		Transaction transactionMatcher = ArgumentMatchers.argThat(
				t -> defaultTransaction.getTimestamp().equals(t.getTimestamp())
						&& defaultTransaction.getAmount().equals(t.getAmount()));

		Mockito.when(transactionService.createTransaction(transactionMatcher)).thenReturn(true);

		//when
		final ResultActions perform = mockMvc.perform(post(URL_TEMPLATE)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(defaultTransactionJson()));

		//then
		perform.andExpect(status().is(201));
		perform.andExpect(content().string(""));
	}

	@Test
	public void createTransactionError() throws Exception {
		//given
		Transaction defaultTransaction = defaultTransaction();
		Transaction transactionMatcher = ArgumentMatchers.argThat(
				t -> defaultTransaction.getTimestamp().equals(t.getTimestamp())
						&& defaultTransaction.getAmount().equals(t.getAmount()));

		Mockito.when(transactionService.createTransaction(transactionMatcher)).thenReturn(false);

		//when
		final ResultActions perform = mockMvc.perform(post(URL_TEMPLATE)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(defaultTransactionJson()));

		//then
		perform.andExpect(status().is(204));
		perform.andExpect(content().string(""));
	}
}