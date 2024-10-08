package com.javalec.ex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.javalec.ex.dto.ContentDto;

public class ContentDao implements IDao {

	private JdbcTemplate template;

	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public ContentDao() {
	}

	/**
	 * 글 목록 조회
	 */
	@Override
	public ArrayList<ContentDto> listDao() {
		String query = "select * from board order by mId desc";
		ArrayList<ContentDto> dtos = (ArrayList<ContentDto>) template.query(query,
				new BeanPropertyRowMapper<ContentDto>(ContentDto.class));
		return dtos;
	}

	/**
	 * 글 작성
	 */
	@Override
	public void writeDao(final String mWriter, final String mContent) {
		System.out.println("글 작성 쿼리 수행");

		this.template.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String query = "insert into board (mId, mWriter, mContent) values (board_seq.nextval, ?, ?)";
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1, mWriter);
				pstmt.setString(2, mContent);
				return pstmt;
			}
		});
	}

	/**
	 * 글 조회
	 */
	@Override
	public ContentDto viewDao(String strID) {
		System.out.println("글 조회 쿼리 수행");

		String query = "select * from board where mId = " + strID;

		return template.queryForObject(query, new BeanPropertyRowMapper<ContentDto>(ContentDto.class));
	}

	/**
	 * 글 삭제
	 */
	@Override
	public void deleteDao(final String bId) {
		System.out.println("글 삭제 쿼리 수행");

		String query = "delete from board where mId = " + bId;

		this.template.update(query, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, Integer.parseInt(bId));
			}
		});
	}

}
