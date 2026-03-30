package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BranchManagerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class BranchManagerModelJDBCImpl implements BranchManagerModelInt {

	private static Logger log = Logger.getLogger(BranchManagerModelJDBCImpl.class);

	// NEXT PK
	public long nextPK() throws DatabaseException {

		long pk = 0;
		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(managerId) from ST_BRANCH_MANAGER");
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

	// ADD
	public long add(BranchManagerDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;
		long pk = 0;

		BranchManagerDTO existDto = findByManagerName(dto.getManagerName());
		if (existDto != null) {
			throw new DuplicateRecordException("Manager Name already exists");
		}

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			pk = nextPK();

			PreparedStatement ps = con.prepareStatement("INSERT INTO ST_BRANCH_MANAGER VALUES(?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getManagerName());
			ps.setString(3, dto.getBranchName());
			ps.setString(4, dto.getContactNumber());

			ps.executeUpdate();
			con.commit();

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in add BranchManager");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return pk;
	}

	// DELETE
	public void delete(BranchManagerDTO dto) throws ApplicationException {

		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("DELETE FROM ST_BRANCH_MANAGER WHERE managerId=?");

			ps.setLong(1, dto.getManagerId());
			ps.executeUpdate();

			con.commit();

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in delete BranchManager");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	// UPDATE
	public void update(BranchManagerDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;

		BranchManagerDTO existDto = findByManagerName(dto.getManagerName());

		if (existDto != null && existDto.getManagerId() != dto.getManagerId()) {
			throw new DuplicateRecordException("Manager Name already exists");
		}

		try {
			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(
					"UPDATE ST_BRANCH_MANAGER SET managerName=?, branchName=?, contactNumber=? WHERE managerId=?");

			ps.setString(1, dto.getManagerName());
			ps.setString(2, dto.getBranchName());
			ps.setString(3, dto.getContactNumber());
			ps.setLong(4, dto.getManagerId());

			ps.executeUpdate();

		} catch (Exception e) {
			throw new ApplicationException("Exception in update BranchManager");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	// FIND BY PK
	public BranchManagerDTO findByPK(long pk) throws ApplicationException {

		Connection con = null;
		BranchManagerDTO dto = null;

		try {
			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_BRANCH_MANAGER WHERE managerId=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new BranchManagerDTO();
				dto.setManagerId(rs.getLong(1));
				dto.setManagerName(rs.getString(2));
				dto.setBranchName(rs.getString(3));
				dto.setContactNumber(rs.getString(4));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPK");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	// FIND BY NAME
	public BranchManagerDTO findByManagerName(String name) throws ApplicationException {

		Connection con = null;
		BranchManagerDTO dto = null;

		try {
			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_BRANCH_MANAGER WHERE managerName=?");

			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new BranchManagerDTO();
				dto.setManagerId(rs.getLong(1));
				dto.setManagerName(rs.getString(2));
				dto.setBranchName(rs.getString(3));
				dto.setContactNumber(rs.getString(4));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByManagerName");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	// LIST
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		Connection con = null;
		List list = new ArrayList();

		String sql = "SELECT * FROM ST_BRANCH_MANAGER";

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql += " LIMIT " + pageNo + "," + pageSize;
		}

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BranchManagerDTO dto = new BranchManagerDTO();
				dto.setManagerId(rs.getLong(1));
				dto.setManagerName(rs.getString(2));
				dto.setBranchName(rs.getString(3));
				dto.setContactNumber(rs.getString(4));
				list.add(dto);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in list");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return list;
	}

	// SEARCH
	public List search(BranchManagerDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(BranchManagerDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Connection con = null;
		List list = new ArrayList();

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_BRANCH_MANAGER WHERE 1=1");

		if (dto != null) {

			if (dto.getManagerId() != null) {
				sql.append(" AND managerId=" + dto.getManagerId());
			}

			if (dto.getManagerName() != null && dto.getManagerName().length() > 0) {
				sql.append(" AND managerName LIKE '" + dto.getManagerName() + "%'");
			}

			if (dto.getBranchName() != null && dto.getBranchName().length() > 0) {
				sql.append(" AND branchName LIKE '" + dto.getBranchName() + "%'");
			}

			if (dto.getContactNumber() != null && dto.getContactNumber().length() > 0) {
				sql.append(" AND contactNumber LIKE '" + dto.getContactNumber() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BranchManagerDTO bm = new BranchManagerDTO();
				bm.setManagerId(rs.getLong(1));
				bm.setManagerName(rs.getString(2));
				bm.setBranchName(rs.getString(3));
				bm.setContactNumber(rs.getString(4));
				list.add(bm);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in search");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return list;
	}
}