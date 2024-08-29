package com.javalec.ex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.javalec.ex.dto.BDto;
import com.javalec.ex.util.Constant;

public class BDao {

	JdbcTemplate template;

	public BDao() {
		this.template = Constant.template;
	}

	/**
	 * 게시글 추가 쿼리
	 * 
	 * @param bName    > 작성자
	 * @param bTitle   > 제목
	 * @param bContent > 내용
	 */
	public void write(final String bName, final String bTitle, final String bContent) {

		this.template.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String query = "insert into mvc_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0)";
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1, bName);
				pstmt.setString(2, bTitle);
				pstmt.setString(3, bContent);
				return pstmt;
			}
		});

	}

	/**
	 * 게시글 목록 조회 쿼리
	 */
	public ArrayList<BDto> list() {

		String query = "select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent from mvc_board order by bGroup desc, bStep asc";
		return (ArrayList<BDto>) template.query(query, new BeanPropertyRowMapper<BDto>(BDto.class));

	}

	/**
	 * 게시글 조회 쿼리
	 * 
	 * @param strID > 게시글 ID
	 * @return
	 */
	public BDto contentView(String strID) {

		upHit(strID);

		String query = "select * from mvc_board where bId = " + strID;

		return template.queryForObject(query, new BeanPropertyRowMapper<BDto>(BDto.class));

	}

	/**
	 * 게시글 수정 쿼리
	 * 
	 * @param bId      > 게시글 ID
	 * @param bName    > 작성자
	 * @param bTitle   > 게시글 제목
	 * @param bContent > 게시글 내용
	 */
	public void modify(final String bId, final String bName, final String bTitle, final String bContent) {

		String query = "update mvc_board set bName = ?, bTitle = ?, bContent = ? where bId = ?";

		this.template.update(query, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bName);
				ps.setString(2, bTitle);
				ps.setString(3, bContent);
				ps.setInt(4, Integer.parseInt(bId));
			}
		});

	}

	/**
	 * 게시글 삭제 쿼리
	 */
	public void delete(final String bId) {

		String query = "delete from mvc_board where bId = ?";

		this.template.update(query, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bId);
			}
		});

	}

	/**
	 * 답변 보기 쿼리
	 * 
	 * @param id > 게시글 ID
	 */
	public BDto reply_view(String id) {

		String query = "select * from mvc_board where bId = " + id;
		return template.queryForObject(query, new BeanPropertyRowMapper<BDto>(BDto.class));

	}

	/**
	 * 게시글 답변 작성
	 * 
	 * @param bId      > 게시글 ID
	 * @param bName    > 작성자
	 * @param bTitle   > 게시글 제목
	 * @param bContent > 게시글 내용
	 * @param bGroup   > 그룹
	 * @param bStep
	 * @param bIndent
	 */
	public void reply(final String bId, final String bName, final String bTitle, final String bContent,
			final String bGroup, final String bStep, final String bIndent) {

		replyShape(bGroup, bStep);

		String query = "insert into mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";

		this.template.update(query, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bName);
				ps.setString(2, bTitle);
				ps.setString(3, bContent);
				ps.setInt(4, Integer.parseInt(bGroup));
				ps.setInt(5, Integer.parseInt(bStep) + 1);
				ps.setInt(6, Integer.parseInt(bIndent));
			}
		});

	}

	/**
	 * 답변 목록 정렬
	 * 
	 * @param strGroup
	 * @param strStep
	 */
	private void replyShape(final String strGroup, final String strStep) {

		String query = "update mvc_board set bStep = bStep + 1 where bGroup = ? and bStep > ?";

		this.template.update(query, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, strGroup);
				ps.setString(2, strStep);
			}
		});

	}

	/**
	 * 조회 수 증가 쿼리
	 * 
	 * @param bId > 게시글 ID
	 */
	private void upHit(final String bId) {

		String query = "update mvc_board set bHit = bHit + 1 where bId = ?";
		this.template.update(query, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, Integer.parseInt(bId));
			}
		});

	}

}
