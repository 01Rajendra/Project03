package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.DispatchDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class DispatchModelJDBCImpl implements DispatchModelInt {

	private static Logger log = Logger.getLogger(DispatchModelJDBCImpl.class);

	public long nextPK() throws DatabaseException {
		Connection con = null;
		long pk = 0;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(ID) from st_dispatch");
			ResultSet r = ps.executeQuery();

			while (r.next()) {
				pk = r.getLong(1);
			}

		} catch (JDBCConnectionException e) {
			throw e;
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new DatabaseException("Exception getting pk");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return pk + 1;
	}

	public long add(DispatchDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;
		long pk = 0;

		DispatchDTO duplicate = findByCourierName(dto.getCourierName());

		if (duplicate != null) {
			throw new DuplicateRecordException("Dispatch already exists");
		}

		try {

			pk = nextPK();

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("insert into st_dispatch values(?,?,?,?,?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setLong(2, dto.getDispatchId());
			ps.setDate(3, new java.sql.Date(dto.getDispatchDate().getTime()));
			ps.setString(4, dto.getStatus());
			ps.setString(5, dto.getCourierName());
			ps.setString(6, dto.getCreatedBy());
			ps.setString(7, dto.getModifiedBy());
			ps.setTimestamp(8, dto.getCreatedDatetime());
			ps.setTimestamp(9, dto.getModifiedDatetime());

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

			throw new ApplicationException("Exception in add Dispatch");

		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return pk;
	}

	public void delete(DispatchDTO dto) throws ApplicationException {

		Connection con = null;

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("delete from st_dispatch where ID=?");

			ps.setLong(1, dto.getId());

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

			throw new ApplicationException("Exception in delete Dispatch");

		} finally {

			JDBCDataSource.closeConnection(con);
		}
	}

	public void update(DispatchDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement(
					"update st_dispatch set DISPATCH_ID=?,DISPATCH_DATE=?,STATUS=?,COURIER_NAME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? where ID=?");

			ps.setLong(1, dto.getDispatchId());
			ps.setDate(2, new java.sql.Date(dto.getDispatchDate().getTime()));
			ps.setString(3, dto.getStatus());
			ps.setString(4, dto.getCourierName());
			ps.setString(5, dto.getCreatedBy());
			ps.setString(6, dto.getModifiedBy());
			ps.setTimestamp(7, dto.getCreatedDatetime());
			ps.setTimestamp(8, dto.getModifiedDatetime());
			ps.setLong(9, dto.getId());

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

			throw new ApplicationException("Exception in updating Dispatch");

		} finally {

			JDBCDataSource.closeConnection(con);
		}
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_dispatch");

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

				DispatchDTO dto = new DispatchDTO();

				dto.setId(rs.getLong(1));
				dto.setDispatchId(rs.getLong(2));
				dto.setDispatchDate(rs.getDate(3));
				dto.setStatus(rs.getString(4));
				dto.setCourierName(rs.getString(5));
				dto.setCreatedBy(rs.getString(6));
				dto.setModifiedBy(rs.getString(7));
				dto.setCreatedDatetime(rs.getTimestamp(8));
				dto.setModifiedDatetime(rs.getTimestamp(9));

				list.add(dto);
			}

			rs.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			throw new ApplicationException("Exception in getting Dispatch list");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	public DispatchDTO findByPK(long pk) throws ApplicationException {

		Connection con = null;

		DispatchDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("select * from st_dispatch where ID=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new DispatchDTO();

				dto.setId(rs.getLong(1));
				dto.setDispatchId(rs.getLong(2));
				dto.setDispatchDate(rs.getDate(3));
				dto.setStatus(rs.getString(4));
				dto.setCourierName(rs.getString(5));
			}

			ps.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			throw new ApplicationException("Exception in getting Dispatch by pk");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	public DispatchDTO findByCourierName(String courierName) throws ApplicationException {

		Connection con = null;

		DispatchDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("select * from st_dispatch where COURIER_NAME=?");

			ps.setString(1, courierName);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new DispatchDTO();

				dto.setId(rs.getLong(1));
				dto.setDispatchId(rs.getLong(2));
				dto.setDispatchDate(rs.getDate(3));
				dto.setStatus(rs.getString(4));
				dto.setCourierName(rs.getString(5));
			}

			ps.close();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			throw new ApplicationException("Exception in getting Dispatch by courierName");

		} finally {

			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	public List search(DispatchDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	public ArrayList<DispatchDTO> search(DispatchDTO dto, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_dispatch where 1=1");

		if (dto != null) {

			if (dto.getDispatchId() != null && dto.getDispatchId() > 0) {
				sql.append(" AND DISPATCH_ID=" + dto.getDispatchId());
			}

			if (dto.getCourierName() != null && dto.getCourierName().length() > 0) {
				sql.append(" AND COURIER_NAME like '" + dto.getCourierName() + "%'");
			}

			if (dto.getStatus() != null && dto.getStatus().length() > 0) {
				sql.append(" AND STATUS like '" + dto.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList<DispatchDTO> list = new ArrayList<DispatchDTO>();

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				dto = new DispatchDTO();

				dto.setId(rs.getLong(1));
				dto.setDispatchId(rs.getLong(2));
				dto.setDispatchDate(rs.getDate(3));
				dto.setStatus(rs.getString(4));
				dto.setCourierName(rs.getString(5));

				list.add(dto);
			}

			rs.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in Dispatch search");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	@Override
	public DispatchDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}