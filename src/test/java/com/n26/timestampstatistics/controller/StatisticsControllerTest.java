package com.n26.timestampstatistics.controller;

import com.n26.timestampstatistics.service.StatisticsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.n26.timestampstatistics.TestDefaultEntities.defaultStatistics;
import static com.n26.timestampstatistics.TestDefaultEntities.defaultStatisticsJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StatisticsControllerTest {

	private static final String URL_TEMPLATE = "/statistics";

	@Mock
	private StatisticsService statisticsService;

	private StatisticsController statisticsController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		statisticsController = new StatisticsController(statisticsService);

		mockMvc = MockMvcBuilders.standaloneSetup(this.statisticsController).build();
	}

	@Test
	public void createTransaction() throws Exception {
		//given
		Mockito.when(statisticsService.getCurrentStatistics()).thenReturn(defaultStatistics());

		//when
		final ResultActions perform = mockMvc.perform(get(URL_TEMPLATE)
				.accept(MediaType.APPLICATION_JSON_UTF8));

		//then
		perform.andExpect(status().isOk());
		perform.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
		perform.andExpect(content().json(defaultStatisticsJson()));
	}
}