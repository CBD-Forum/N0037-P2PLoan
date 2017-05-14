package cn.edu.fudan.bclab.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

	@RequestMapping(value = { "", "/", "index" },method = RequestMethod.GET)
	@ResponseBody
	public String index() {

		return "index";
	}

}
