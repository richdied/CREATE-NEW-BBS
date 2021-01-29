package evaluation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.DatabaseUtil;

public class EvaluationDAO {
	
	public int write(EvaluationDTO evaluationDTO) {
		String SQL = "INSERT INTO EVALUATION VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, evaluationDTO.getUserID());
			pstmt.setString(2, evaluationDTO.getLectureName());
			pstmt.setString(3, evaluationDTO.getProfessorName());
			pstmt.setInt(4, evaluationDTO.getLectureYear());
			pstmt.setString(5, evaluationDTO.getSemesterDivide());
			pstmt.setString(6, evaluationDTO.getLectureDivide());
			pstmt.setString(7, evaluationDTO.evaluationTitle);
			pstmt.setString(8, evaluationDTO.evaluationContent);
			pstmt.setString(9, evaluationDTO.getTotalScore());
			pstmt.setString(10, evaluationDTO.getCreditScore());
			pstmt.setString(11, evaluationDTO.getComfortableScore());
			pstmt.setString(12, evaluationDTO.lectureScore);
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close(); } catch (Exception e) {e.printStackTrace(); }
			try {if(pstmt != null) pstmt.close(); } catch (Exception e) {e.printStackTrace(); }
			try {if(rs != null) rs.close(); } catch (Exception e) {e.printStackTrace(); }
		}
		return -1; // �����ͺ��̽� ����
	}
	
	public ArrayList<EvaluationDTO> getList (String lectureDivide, String searchType, String search, int pageNumber) {
		if(lectureDivide.equals("��ü")) {
			lectureDivide = "";
		}
		ArrayList<EvaluationDTO> evaluationList = null;
		String SQL = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			if(searchType.equals("�ֽż�")) {
				SQL = "SELECT * FROM evaluation WHERE lectureDivide LIKE ? AND CONCAT(lectureName, professorName, evaluationTitle, evaluationContent)LIKE " +
			            "? ORDER BY evaluationID DESC LIMIT " + pageNumber * 5 + ", " + pageNumber * 5 + 6;
			} else if (searchType.equals("��õ��")) {
				SQL = "SELECT * FROM evaluation WHERE lectureDivide LIKE ? AND CONCAT(lectureName, professorName, evaluationTitle, evaluationContent) LIKE " +
						"? ORDER BY likeCount DESC LIMIT " + pageNumber * 5 + ", " + pageNumber * 5 + 6;
			}
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%" + lectureDivide + "%");
			pstmt.setString(2, "%" + search + "%");
			rs = pstmt.executeQuery();
			evaluationList = new ArrayList<EvaluationDTO>();
		    while(rs.next()) {
		    	EvaluationDTO evaluation = new EvaluationDTO(
		    			rs.getInt(1),
		    			rs.getString(2),
		    			rs.getString(3),
		    			rs.getString(4),
		    			rs.getInt(5),
		    			rs.getString(6),
		    			rs.getString(7),
		    			rs.getString(8),
		    			rs.getString(9),
		    			rs.getString(10),
		    			rs.getString(11),
		    			rs.getString(12),
		    			rs.getString(13),
		    			rs.getInt(14)
		    			);
		    	evaluationList.add(evaluation);
		    }
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {if(conn != null) conn.close(); } catch (Exception e) {e.printStackTrace(); }
			try {if(pstmt != null) pstmt.close(); } catch (Exception e) {e.printStackTrace(); }
			try {if(rs != null) rs.close(); } catch (Exception e) {e.printStackTrace(); }
		}
		return evaluationList; 
	}
}

