package com.javalec.ex;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping("/index")
	public String goIndex() {
		return "index";
	}

	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public String goStudent(HttpServletRequest httpServletRequest, Model model) {

		System.out.println("RequestMethod.GET");

		String id = httpServletRequest.getParameter("id");
		System.out.println("id : " + id);
		model.addAttribute("studentId", id);

		return "student/studentId";

	}

	@RequestMapping(value = "/student", method = RequestMethod.POST)
	public ModelAndView goStudent(HttpServletRequest httpServletRequest) {

		System.out.println("RequestMethod.POST");

		String id = httpServletRequest.getParameter("id");
		System.out.println("id : " + id);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("student/studentId");
		mv.addObject("studentId", id);

//		model.addAttribute("studentId", id);

//		return "student/studentId";
		return mv;
	}

}
