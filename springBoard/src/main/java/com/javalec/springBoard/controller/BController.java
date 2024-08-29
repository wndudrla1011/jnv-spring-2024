package com.javalec.springBoard.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javalec.springBoard.command.BCommand;
import com.javalec.springBoard.command.BContentCommand;
import com.javalec.springBoard.command.BDeleteCommand;
import com.javalec.springBoard.command.BListCommand;
import com.javalec.springBoard.command.BModifyCommand;
import com.javalec.springBoard.command.BReplyCommand;
import com.javalec.springBoard.command.BReplyViewCommand;
import com.javalec.springBoard.command.BWriteCommand;

@Controller
public class BController {

	private static final Logger logger = LoggerFactory.getLogger(BController.class);
	BCommand command;

	@RequestMapping("/list")
	public String list(Model model) {

		logger.info("게시글 목록 조회");

		command = new BListCommand();
		command.execute(model);

		return "list";

	}

	@RequestMapping("/write_view")
	public String write_view(Model model) {

		logger.info("게시글 작성 화면");

		return "write_view";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/write")
	public String write(HttpServletRequest request, Model model) {

		logger.info("게시글 작성");

		model.addAttribute("request", request);
		command = new BWriteCommand();
		command.execute(model);

		return "redirect:list";

	}

	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {

		logger.info("게시글 조회");

		model.addAttribute("request", request);
		command = new BContentCommand();
		command.execute(model);

		return "content_view";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/modify")
	public String modify(HttpServletRequest request, Model model) {

		logger.info("게시글 수정");

		model.addAttribute("request", request);
		command = new BModifyCommand();
		command.execute(model);

		return "redirect:list";

	}

	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model) {

		logger.info("답변 조회");

		model.addAttribute("request", request);
		command = new BReplyViewCommand();
		command.execute(model);

		return "reply_view";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/reply")
	public String reply(HttpServletRequest request, Model model) {

		logger.info("답변 작성");

		model.addAttribute("request", request);
		command = new BReplyCommand();
		command.execute(model);

		return "redirect:list";

	}

	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request, Model model) {

		logger.info("게시글 삭제");

		model.addAttribute("request", request);
		command = new BDeleteCommand();
		command.execute(model);

		return "redirect:list";

	}

}
