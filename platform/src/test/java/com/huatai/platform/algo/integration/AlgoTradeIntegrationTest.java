package com.huatai.platform.algo.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huatai.platform.Platform;
import com.huatai.platform.algo.AlgoTradeManager;
import com.huatai.platform.client.FixBaseClient;

@Ignore
@ContextConfiguration(locations = { "classpath:com/huatai/platform/algo/integration/algo.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@RunWith(SpringJUnit4ClassRunner.class)
public class AlgoTradeIntegrationTest {
	@Autowired
	private Platform platform;
	
	@Autowired
	private AlgoTradeManager algoTradeManager;
	
	@Autowired
	private FixBaseClient client;
	
	@Before
	public void before() throws Exception {
		platform.init();
		client.init();
		client.waitServerReady();
	}
	
	@After
	public void after() {
		client.uninit();
		platform.shutdown();
	}
	
	@Test
	public void test() throws Exception {
		AlgoTradeTestCase.testAlgoTrade(client, algoTradeManager);
		AlgoTradeTestCase.testAcceptXStepEvent();
	}
}
