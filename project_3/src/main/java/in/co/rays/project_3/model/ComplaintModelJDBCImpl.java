package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.ComplaintDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC implements of Complaint model
 * 
 * @author Rajendra Negi
 */
public class ComplaintModelJDBCImpl implements ComplaintModelInt {

	private static Logger log = Logger.getLogger(ComplaintModelJDBCImpl.class);

	public long nextPK() throws DatabaseException {
		Connection con = null;
		long pk = 0;
		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(COMPLAINT_ID) from st_complaint");
			ResultSet r = ps.executeQuery();
			while (r.next()) {
				pk = r.getLong(1);
			}
		} catch (JDBCConnectionException e) {
			throw e;
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new DatabaseException("Exception getting in pk");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return pk + 1;
	}

	public long add(ComplaintDTO dto) throws ApplicationException, DuplicateRecordException {
		Connection con = null;
		long pk = 0;

		ComplaintDTO duplicate = findByName(dto.getComplaintCode());
		if (duplicate != null) {
			throw new DuplicateRecordException("Complaint already exists");
		}

		try {
			pk = nextPK();
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("insert into st_complaint values(?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, dto.getComplaintCode());
			ps.setString(3, dto.getCustomerName());
			ps.setString(4, dto.getComplaintType());
			ps.setString(5, dto.getComplaintStatus());

			ps.executeUpdate();
			ps.close();
			con.commit();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in add Complaint");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return pk;
	}

	public void delete(ComplaintDTO dto) throws ApplicationException {
		Connection con = null;
		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("delete from st_complaint where COMPLAINT_ID=?");
			ps.setLong(1, dto.getComplaintId());

			ps.executeUpdate();
			ps.close();
			con.commit();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in delete Complaint");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	public void update(ComplaintDTO dto) throws ApplicationException, DuplicateRecordException {
		Connection con = null;

		ComplaintDTO duplicate = findByName(dto.getComplaintCode());
		if (duplicate != null && duplicate.getComplaintId() != dto.getComplaintId()) {
			throw new DuplicateRecordException("Complaint already exists");
		}

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement(
					"update st_complaint set COMPLAINT_CODE=?,CUSTOMER_NAME=?,COMPLAINT_TYPE=?,COMPLAINT_STATUS=? where COMPLAINT_ID=?");

			ps.setString(1, dto.getComplaintCode());
			ps.setString(2, dto.getCustomerName());
			ps.setString(3, dto.getComplaintType());
			ps.setString(4, dto.getComplaintStatus());
			ps.setLong(5, dto.getComplaintId());

			ps.executeUpdate();
			ps.close();
			con.commit();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Complaint");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_complaint");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ComplaintDTO dto = new ComplaintDTO();
				dto.setComplaintId(rs.getLong(1));
				dto.setComplaintCode(rs.getString(2));
				dto.setCustomerName(rs.getString(3));
				dto.setComplaintType(rs.getString(4));
				dto.setComplaintStatus(rs.getString(5));

				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in list");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	public ComplaintDTO findByPK(long pk) throws ApplicationException {
		Connection con = null;
		ComplaintDTO dto = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_complaint where COMPLAINT_ID=?");
			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new ComplaintDTO();
				dto.setComplaintId(rs.getLong(1));
				dto.setComplaintCode(rs.getString(2));
				dto.setCustomerName(rs.getString(3));
				dto.setComplaintType(rs.getString(4));
				dto.setComplaintStatus(rs.getString(5));
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPK");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return dto;
	}

	public ComplaintDTO findByName(String code) throws ApplicationException {
		Connection con = null;
		ComplaintDTO dto = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_complaint where COMPLAINT_CODE=?");
			ps.setString(1, code);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new ComplaintDTO();
				dto.setComplaintId(rs.getLong(1));
				dto.setComplaintCode(rs.getString(2));
				dto.setCustomerName(rs.getString(3));
				dto.setComplaintType(rs.getString(4));
				dto.setComplaintStatus(rs.getString(5));
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in findByName");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return dto;
	}

	public List search(ComplaintDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public ArrayList<ComplaintDTO> search(ComplaintDTO dto, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_complaint where 1=1");

		if (dto != null) {
			if (dto.getComplaintId() > 0) {
				sql.append(" AND COMPLAINT_ID=" + dto.getComplaintId());
			}
			if (dto.getComplaintCode() != null && dto.getComplaintCode().length() > 0) {
				sql.append(" AND COMPLAINT_CODE like '" + dto.getComplaintCode() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList<ComplaintDTO> list = new ArrayList<>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				dto = new ComplaintDTO();
				dto.setComplaintId(rs.getLong(1));
				dto.setComplaintCode(rs.getString(2));
				dto.setCustomerName(rs.getString(3));
				dto.setComplaintType(rs.getString(4));
				dto.setComplaintStatus(rs.getString(5));

				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in search");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}