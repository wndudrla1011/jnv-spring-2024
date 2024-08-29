package com.javalec.ex;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StudentController {

	@RequestMapping("/studentForm")
	public String studentForm() {
		return "createPage";
	}

	@RequestMapping("/student/create")
	public String studentCreate(@Valid Student student, BindingResult result) {

		String page = "createDonePage";

		if (result.hasErrors()) {
			page = "createPage";
		}

		return page;

	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new StudentValidator());
	}

}
