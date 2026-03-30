package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.ResultDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class ResultModelJDBCImpl implements ResultModelInt {

	private static Logger log = Logger.getLogger(ResultModelJDBCImpl.class);

	public long nextPK() throws DatabaseException {
		Connection con = null;
		long pk = 0;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(resultId) from ST_RESULT");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getLong(1);
			}

		} catch (Exception e) {
			throw new DatabaseException("Database Exception " + e);
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return pk + 1;
	}

	public long add(ResultDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;
		long pk = 0;

		// UNIQUE check
		ResultDTO existDto = findByCode(dto.getResultCode());
		if (existDto != null) {
			throw new DuplicateRecordException("Result Code already exists");
		}

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			pk = nextPK();

			PreparedStatement ps = con.prepareStatement(
					"insert into ST_RESULT (resultId,resultCode,studentName,marks,resultStatus) values(?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getResultCode());
			ps.setString(3, dto.getStudentName());
			ps.setInt(4, dto.getMarks());
			ps.setString(5, dto.getResultStatus());

			ps.executeUpdate();
			con.commit();

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			throw new ApplicationException("Exception in add");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return pk;
	}

	public void delete(ResultDTO dto) throws ApplicationException {

		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("delete from ST_RESULT where resultId=?");
			ps.setLong(1, dto.getResultId());
			ps.executeUpdate();

		} catch (Exception e) {
			throw new ApplicationException("Exception in delete");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	public ResultDTO findByPK(long pk) throws ApplicationException {

		Connection con = null;
		ResultDTO dto = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from ST_RESULT where resultId=?");
			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new ResultDTO();

				dto.setResultId(rs.getLong("resultId"));
				dto.setResultCode(rs.getString("resultCode"));
				dto.setStudentName(rs.getString("studentName"));
				dto.setMarks(rs.getInt("marks"));
				dto.setResultStatus(rs.getString("resultStatus"));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPK");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	// UNIQUE finder
	public ResultDTO findByCode(String code) throws ApplicationException {

		Connection con = null;
		ResultDTO dto = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from ST_RESULT where resultCode=?");
			ps.setString(1, code);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new ResultDTO();

				dto.setResultId(rs.getLong("resultId"));
				dto.setResultCode(rs.getString("resultCode"));
				dto.setStudentName(rs.getString("studentName"));
				dto.setMarks(rs.getInt("marks"));
				dto.setResultStatus(rs.getString("resultStatus"));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByCode");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	public List list() throws ApplicationException {

		Connection con = null;
		List list = new ArrayList();

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from ST_RESULT");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ResultDTO dto = new ResultDTO();

				dto.setResultId(rs.getLong("resultId"));
				dto.setResultCode(rs.getString("resultCode"));
				dto.setStudentName(rs.getString("studentName"));
				dto.setMarks(rs.getInt("marks"));
				dto.setResultStatus(rs.getString("resultStatus"));

				list.add(dto);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in list");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return list;
	}

	@Override
	public void update(ResultDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub

	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(ResultDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(ResultDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
}