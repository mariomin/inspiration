package com.inspiration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;

@Controller
public class TestController {
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ModelAndView pageHome() {
		return new ModelAndView("/pages/index");
	}

	@RequestMapping(value = { "/create" }, produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public @ResponseBody String createInstance(@RequestBody String RequestStr) {
		JsonObject json = new JsonObject();
		json.addProperty("result", "success");
		return json.toString();
	}
}
