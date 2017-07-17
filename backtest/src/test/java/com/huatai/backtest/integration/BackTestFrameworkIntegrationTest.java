package com.huatai.backtest.integration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.huatai.backtest.BackTestManager;
import com.huatai.backtest.core.BackTestContainer;
import com.huatai.common.backtest.report.BackTestRequest;
import com.huatai.common.util.GsonUtil;

@ContextConfiguration(locations = { "classpath:com/huatai/backtest/integration/backtest.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BackTestFrameworkIntegrationTest {
	private static final Gson gson = GsonUtil.getGsonInstance();
	
	@Autowired
	private BackTestManager backTestManager;
	
	@Autowired
	private String request;
	
	@Before
	public void before() throws Exception {}
	
	@After
	public void after() throws Exception {}
	
	@Test
	public void runBackTest() throws Exception {
		BackTestRequest backTestRequest = gson.fromJson(request, BackTestRequest.class);
		BackTestContainer backTestContainer = backTestManager.createBackTestContainer(backTestRequest);
		Assert.assertNotNull(backTestContainer);
	}
}
