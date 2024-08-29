package com.javalec.ex.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.javalec.ex.dao.BDao;
import com.javalec.ex.dto.BDto;

public class BListCommand implements BCommand {

	/**
	 * 게시글 목록 조회
	 */
	@Override
	public void execute(Model model) {

		BDao dao = new BDao();
		ArrayList<BDto> dtos = dao.list();

		model.addAttribute("list", dtos);

	}

}
