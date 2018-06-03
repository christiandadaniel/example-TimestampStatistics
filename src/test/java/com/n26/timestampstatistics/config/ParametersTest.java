package com.n26.timestampstatistics.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParametersTest {

	@Autowired
	Parameters parameters;

	@Test
	public void getInterval() {
		//given

		//when
		long interval = parameters.getInterval();

		//then
		assertEquals(120000L, interval);
	}

	@Test
	public void getRefreshRate() {
		//given

		//when
		long refreshRate = parameters.getRefreshRate();

		//then
		assertEquals(5000L, refreshRate);
	}
}