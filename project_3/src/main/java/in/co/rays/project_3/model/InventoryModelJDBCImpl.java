package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.InventoryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class InventoryModelJDBCImpl implements InventoryModelInt {

	private static Logger log = Logger.getLogger(InventoryModelJDBCImpl.class);

	/**
	 * Next PK
	 */
	public long nextPK() throws DatabaseException {
		Connection con = null;
		long pk = 0;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(ID) from st_inventory");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new DatabaseException("Exception getting PK");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return pk + 1;
	}

	/**
	 * Add Inventory
	 */
	public long add(InventoryDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;
		long pk = 0;

		try {
			pk = nextPK();
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("insert into st_inventory values(?,?,?,?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getSupplierName());
			ps.setString(3, dto.getProduct());
			ps.setLong(4, dto.getQuantity());
			ps.setDate(5, new java.sql.Date(dto.getLastUpdatedDate().getTime()));
			ps.setString(6, dto.getCreatedBy());
			ps.setString(7, dto.getModifiedBy());
			ps.setTimestamp(8, dto.getCreatedDatetime());

			ps.executeUpdate();
			ps.close();
			con.commit();

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in add Inventory");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return pk;
	}

	/**
	 * Delete
	 */
	public void delete(InventoryDTO dto) throws ApplicationException {

		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("delete from st_inventory where ID=?");

			ps.setLong(1, dto.getId());
			ps.executeUpdate();

			ps.close();
			con.commit();

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception");
			}
			throw new ApplicationException("Exception in delete Inventory");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	/**
	 * Update
	 */
	public void update(InventoryDTO dto) throws ApplicationException {

		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement(
					"update st_inventory set SUPPLIER_NAME=?, PRODUCT=?, QUANTITY=?, LAST_UPDATED_DATE=?, MODIFIED_BY=? where ID=?");

			ps.setString(1, dto.getSupplierName());
			ps.setString(2, dto.getProduct());
			ps.setLong(3, dto.getQuantity());
			ps.setDate(4, new java.sql.Date(dto.getLastUpdatedDate().getTime()));
			ps.setString(5, dto.getModifiedBy());
			ps.setLong(6, dto.getId());

			ps.executeUpdate();
			ps.close();
			con.commit();

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception");
			}
			throw new ApplicationException("Exception in updating Inventory");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	/**
	 * Find By PK
	 */
	public InventoryDTO findByPK(long pk) throws ApplicationException {

		Connection con = null;
		InventoryDTO dto = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_inventory where ID=?");

			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new InventoryDTO();
				dto.getId();
				dto.setSupplierName(rs.getString(2));
				dto.setProduct(rs.getString(3));
				dto.setQuantity(rs.getLong(4));
				dto.setLastUpdatedDate(rs.getDate(5));
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPK");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	/**
	 * List
	 */
	public List list() throws ApplicationException {
		ArrayList list = new ArrayList();

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_inventory");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				InventoryDTO dto = new InventoryDTO();
				dto.setId(rs.getLong(1));
				dto.setSupplierName(rs.getString(2));
				dto.setProduct(rs.getString(3));
				dto.setQuantity(rs.getLong(4));
				dto.setLastUpdatedDate(rs.getDate(5));
				list.add(dto);
			}

			rs.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Inventory list");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	@Override
	public InventoryDTO findByLogin(String login) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(InventoryDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(InventoryDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
}