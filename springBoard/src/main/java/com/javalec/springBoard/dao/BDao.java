package com.javalec.springBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.javalec.springBoard.dto.BDto;

public class BDao {

	DataSource dataSource;

	public BDao() {

		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/OracleDS");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 게시글 추가 쿼리
	 * 
	 * @param bName    > 작성자
	 * @param bTitle   > 제목
	 * @param bContent > 내용
	 */
	public void write(String bName, String bTitle, String bContent) {

		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dataSource.getConnection();
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0)";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			int rn = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	/**
	 * 게시글 목록 조회 쿼리
	 */
	public ArrayList<BDto> list() {

		ArrayList<BDto> dtos = new ArrayList<BDto>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = dataSource.getConnection();

			String query = "select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent from mvc_board order by bGroup desc, bStep asc";
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");

				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				dtos.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dtos;

	}

	/**
	 * 게시글 조회 쿼리
	 * 
	 * @param strID > 게시글 ID
	 * @return
	 */
	public BDto contentView(String strID) {

		upHit(strID);

		BDto dto = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = dataSource.getConnection();

			String query = "select * from mvc_board where bId = ?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strID));
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");

				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;

	}

	/**
	 * 게시글 수정 쿼리
	 * 
	 * @param bId      > 게시글 ID
	 * @param bName    > 작성자
	 * @param bTitle   > 게시글 제목
	 * @param bContent > 게시글 내용
	 */
	public void modify(String bId, String bName, String bTitle, String bContent) {

		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dataSource.getConnection();

			String query = "update mvc_board set bName = ?, bTitle = ?, bContent = ? where bId = ?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bId));
			int rn = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	/**
	 * 게시글 삭제 쿼리
	 */
	public void delete(String bId) {

		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dataSource.getConnection();
			String query = "delete from mvc_board where bId = ?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(bId));
			int rn = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	/**
	 * 답변 보기 쿼리
	 * 
	 * @param id > 게시글 ID
	 */
	public BDto reply_view(String id) {

		BDto dto = null;

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = dataSource.getConnection();
			String query = "select * from mvc_board where bId = ?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(id));
			rs = pstmt.executeQuery();

			if (rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");

				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;

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
	public void reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep,
			String bIndent) {

		replyShape(bGroup, bStep);

		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dataSource.getConnection();
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
			pstmt = connection.prepareStatement(query);

			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, Integer.parseInt(bGroup));
			pstmt.setInt(5, Integer.parseInt(bStep) + 1);
			pstmt.setInt(6, Integer.parseInt(bIndent));

			int rn = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	/**
	 * 답변 목록 정렬
	 * 
	 * @param strGroup
	 * @param strStep
	 */
	private void replyShape(String strGroup, String strStep) {

		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dataSource.getConnection();
			String query = "update mvc_board set bStep = bStep + 1 where bGroup = ? and bStep > ?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(strGroup));
			pstmt.setInt(2, Integer.parseInt(strStep));

			int rn = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	/**
	 * 조회 수 증가 쿼리
	 * 
	 * @param bId > 게시글 ID
	 */
	private void upHit(String bId) {

		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dataSource.getConnection();
			String query = "update mvc_board set bHit = bHit + 1 where bId = ?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, bId);

			int rn = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

}
