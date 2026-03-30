package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.project_3.dto.SubscriptionPlanDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class SubscriptionPlanModelJDBCImpl implements SubscriptionPlanModelInt {

	// Add
	public long add(SubscriptionPlanDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		SubscriptionPlanDTO existDto = findByName(dto.getPlanName());

		if (existDto != null) {
			throw new DuplicateRecordException("Plan already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO st_subscription_plan (PLAN_ID, PLAN_NAME, PRICE, VALIDITY_DAYS, CREATED_BY, MODIFIED_BY, CREATED_DATETIME, MODIFIED_DATETIME) VALUES (?,?,?,?,?,?,?,?)");

			pk = nextPK();

			ps.setLong(1, pk);
			ps.setString(2, dto.getPlanName());
			ps.setDouble(3, dto.getPrice());
			ps.setInt(4, dto.getValidityDays());
			ps.setString(5, dto.getCreatedBy());
			ps.setString(6, dto.getModifiedBy());
			ps.setTimestamp(7, new java.sql.Timestamp(dto.getCreatedDatetime().getTime()));
			ps.setTimestamp(8, new java.sql.Timestamp(dto.getModifiedDatetime().getTime()));

			ps.executeUpdate();

			JDBCDataSource.closeConnection(conn);

		} catch (Exception e) {
			throw new ApplicationException("Exception in add SubscriptionPlan");
		}

		return pk;
	}

	// Delete
	public void delete(SubscriptionPlanDTO dto) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_subscription_plan WHERE PLAN_ID=?");

			ps.setLong(1, dto.getPlanId());
			ps.executeUpdate();

			JDBCDataSource.closeConnection(conn);

		} catch (Exception e) {
			throw new ApplicationException("Exception in delete SubscriptionPlan");
		}
	}

	// Update
	public void update(SubscriptionPlanDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		SubscriptionPlanDTO existDto = findByName(dto.getPlanName());

		if (existDto != null && existDto.getPlanId() != dto.getPlanId()) {
			throw new DuplicateRecordException("Plan already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement(
					"UPDATE st_subscription_plan SET PLAN_NAME=?, PRICE=?, VALIDITY_DAYS=?, MODIFIED_BY=?, MODIFIED_DATETIME=? WHERE PLAN_ID=?");

			ps.setString(1, dto.getPlanName());
			ps.setDouble(2, dto.getPrice());
			ps.setInt(3, dto.getValidityDays());
			ps.setString(4, dto.getModifiedBy());
			ps.setTimestamp(5, new java.sql.Timestamp(dto.getModifiedDatetime().getTime()));
			ps.setLong(6, dto.getPlanId());

			ps.executeUpdate();

			JDBCDataSource.closeConnection(conn);

		} catch (Exception e) {
			throw new ApplicationException("Exception in update SubscriptionPlan");
		}
	}

	// Find by PK
	public SubscriptionPlanDTO findByPK(long pk) throws ApplicationException {

		Connection conn = null;
		SubscriptionPlanDTO dto = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_subscription_plan WHERE PLAN_ID=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = populateDTO(rs);
			}

			JDBCDataSource.closeConnection(conn);

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPK");
		}

		return dto;
	}

	// Find by Name
	public SubscriptionPlanDTO findByName(String name) throws ApplicationException {

		Connection conn = null;
		SubscriptionPlanDTO dto = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM st_subscription_plan WHERE PLAN_NAME=?");

			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = populateDTO(rs);
			}

			JDBCDataSource.closeConnection(conn);

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByName");
		}

		return dto;
	}

	// List
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		List list = new ArrayList();

		StringBuffer sql = new StringBuffer("SELECT * FROM st_subscription_plan");

		if (pageSize > 0) {
			sql.append(" LIMIT " + (pageNo - 1) * pageSize + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(populateDTO(rs));
			}

			JDBCDataSource.closeConnection(conn);

		} catch (Exception e) {
			throw new ApplicationException("Exception in list");
		}

		return list;
	}

	// Search
	public List search(SubscriptionPlanDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(SubscriptionPlanDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		List list = new ArrayList();

		StringBuffer sql = new StringBuffer("SELECT * FROM st_subscription_plan WHERE 1=1");

		if (dto.getPlanName() != null && dto.getPlanName().length() > 0) {
			sql.append(" AND PLAN_NAME like '" + dto.getPlanName() + "%'");
		}

		if (pageSize > 0) {
			sql.append(" LIMIT " + (pageNo - 1) * pageSize + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(populateDTO(rs));
			}

			JDBCDataSource.closeConnection(conn);

		} catch (Exception e) {
			throw new ApplicationException("Exception in search");
		}

		return list;
	}

	// Next PK
	public Integer nextPK() throws DatabaseException {

		int pk = 0;

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT MAX(PLAN_ID) FROM st_subscription_plan");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

			JDBCDataSource.closeConnection(conn);

		} catch (Exception e) {
			throw new DatabaseException("Exception in nextPK");
		}

		return pk + 1;
	}

	// Populate DTO
	private SubscriptionPlanDTO populateDTO(ResultSet rs) throws Exception {

		SubscriptionPlanDTO dto = new SubscriptionPlanDTO();

		dto.setPlanId(rs.getLong("PLAN_ID"));
		dto.setPlanName(rs.getString("PLAN_NAME"));
		dto.setPrice(rs.getDouble("PRICE"));
		dto.setValidityDays(rs.getInt("VALIDITY_DAYS"));

		dto.setCreatedBy(rs.getString("CREATED_BY"));
		dto.setModifiedBy(rs.getString("MODIFIED_BY"));
		dto.setCreatedDatetime(rs.getTimestamp("CREATED_DATETIME"));
		dto.setModifiedDatetime(rs.getTimestamp("MODIFIED_DATETIME"));

		return dto;
	}
}