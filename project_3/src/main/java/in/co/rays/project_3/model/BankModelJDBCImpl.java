package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BankDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC implements of Bank model
 * 
 * @author  Rajendra Negi Negi
 *
 */
public class BankModelJDBCImpl implements BankModelInt {

	private static Logger log = Logger.getLogger(BankModelJDBCImpl.class);

	public long nextPK() throws DatabaseException {

		Connection con = null;
		long pk = 0;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(ID) from st_bank");
			ResultSet r = ps.executeQuery();
			while (r.next()) {
				pk = r.getLong(1);
			}
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new DatabaseException("Exception getting in pk");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return pk + 1;
	}

	public long add(BankDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;
		long pk = 0;

		BankDTO duplicateBank = findByName(dto.getBank_Name());
		if (duplicateBank != null) {
			throw new DuplicateRecordException("Bank already exists");
		}

		try {
			pk = nextPK();
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("insert into st_bank values(?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, dto.getBank_Name());
			ps.setString(3, dto.getAccount_NO());
			ps.setString(4, dto.getCustomer_Name());
			ps.setDate(5, new java.sql.Date(dto.getDob().getTime()));
			ps.setString(6, dto.getAddress());

			ps.executeUpdate();
			ps.close();
			con.commit();

		} catch (Exception e) {

			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback " + ex.getMessage());
			}
			throw new ApplicationException("Exception in add Bank");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return pk;
	}

	public void delete(BankDTO dto) throws ApplicationException {

		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("delete from st_bank where ID=?");
			ps.setLong(1, dto.getId());
			ps.executeUpdate();
			ps.close();
			con.commit();

		} catch (Exception e) {

			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in delete Bank");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	public void update(BankDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;

		BankDTO duplicateBank = findByName(dto.getBank_Name());
		if (duplicateBank != null && duplicateBank.getId() != dto.getId()) {
			throw new DuplicateRecordException("Bank already exists");
		}

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement(
					"update st_bank set BANK_NAME=?,ACCOUNT_NO=?,CUSTOMER_NAME=?,DOB=?,ADDRESS=? where ID=?");

			ps.setString(1, dto.getBank_Name());
			ps.setString(2, dto.getAccount_NO());
			ps.setString(3, dto.getCustomer_Name());
			ps.setDate(4, new java.sql.Date(dto.getDob().getTime()));
			ps.setString(5, dto.getAddress());
			ps.setLong(6, dto.getId());

			ps.executeUpdate();
			ps.close();
			con.commit();

		} catch (Exception e) {

			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Bank");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_bank");

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

				BankDTO dto = new BankDTO();
				dto.setId(rs.getLong(1));
				dto.setBank_Name(rs.getString(2));
				dto.setAccount_NO(rs.getString(3));
				dto.setCustomer_Name(rs.getString(4));
				dto.setDob(rs.getDate(5));
				dto.setAddress(rs.getString(6));

				list.add(dto);
			}

			rs.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting list of Bank");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	public BankDTO findByPK(long pk) throws ApplicationException {

		Connection con = null;
		BankDTO dto = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_bank where ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new BankDTO();
				dto.setId(rs.getLong(1));
				dto.setBank_Name(rs.getString(2));
				dto.setAccount_NO(rs.getString(3));
				dto.setCustomer_Name(rs.getString(4));
				dto.setDob(rs.getDate(5));
				dto.setAddress(rs.getString(6));
			}

			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Bank by PK");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	public BankDTO findByName(String name) throws ApplicationException {

		Connection con = null;
		BankDTO dto = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from st_bank where BANK_NAME=?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new BankDTO();
				dto.setId(rs.getLong(1));
				dto.setBank_Name(rs.getString(2));
				dto.setAccount_NO(rs.getString(3));
				dto.setCustomer_Name(rs.getString(4));
				dto.setDob(rs.getDate(5));
				dto.setAddress(rs.getString(6));
			}

			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Bank by Name");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	@Override
	public List search(BankDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(BankDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}