package com.huatai.backtest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.huatai.backtest.BackTestManager;
import com.huatai.backtest.core.BackTestContainer;
import com.huatai.common.backtest.StrategyException;
import com.huatai.common.backtest.report.BackTestRequest;

@Controller
@RequestMapping("/instance")
public class CreateBackTestInstanceController {
	private static final Logger log = LoggerFactory.getLogger(CreateBackTestInstanceController.class);
	private static final Gson gson = new Gson();
	
	@Autowired
	private BackTestManager backTestManager;
	
	@RequestMapping(value = { "/create-page" }, method = RequestMethod.GET)
	public ModelAndView createInstancePage() {
		return new ModelAndView("/pages/CreateBackTestInstance");
	}
	
	@RequestMapping(value = { "/create" }, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String createInstance(@RequestBody String backTestRequestStr) {
		BackTestRequest request = gson.fromJson(backTestRequestStr, BackTestRequest.class);
		BackTestContainer backTestContainer = null;
		
		try {
			backTestContainer = backTestManager.createBackTestContainer(request);
		} catch (StrategyException e) {
			log.error("Create strategy failed: " + request.getStrategy());
		}
		
		JsonObject json = new JsonObject();
		if (backTestContainer != null) {
			//单机模式下先清空当前容器
			backTestManager.getContainers().clear();
			
			backTestManager.getContainers().put(backTestContainer.getId(), backTestContainer);
			log.info("Strategy container create success:" + backTestContainer.toString());
			json.addProperty("result", "success");
			json.addProperty("strategy", backTestContainer.getStrategy().getName());
			json.addProperty("id", backTestContainer.getId());
		} else {
			json.addProperty("result", "failed");
		}
		
		return json.toString();
	}
	
}
