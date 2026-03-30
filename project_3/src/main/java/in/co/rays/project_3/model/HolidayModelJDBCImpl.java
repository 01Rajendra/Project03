package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.HolidayDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class HolidayModelJDBCImpl implements HolidayModelInt {

	private static Logger log = Logger.getLogger(HolidayModelJDBCImpl.class);

	public long nextPK() throws DatabaseException {

		Connection con = null;
		long pk = 0;

		try {

			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(HOLIDAY_ID) from st_holiday");

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

	public long add(HolidayDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;
		long pk = 0;

		HolidayDTO duplicateHoliday = findByCode(dto.getHolidayCode());

		if (duplicateHoliday != null) {

			throw new DuplicateRecordException("Holiday already exists");

		}

		try {

			pk = nextPK();

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("insert into st_holiday values(?,?,?,?,?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getHolidayCode());
			ps.setString(3, dto.getHolidayName());
			ps.setDate(4, (Date) dto.getHolidayDate());
			ps.setString(5, dto.getHolidayType());
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

				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());

			}

			throw new ApplicationException("Exception : Exception in add Holiday");

		} finally {

			JDBCDataSource.closeConnection(con);

		}

		return pk;
	}

	public void delete(HolidayDTO dto) throws ApplicationException {

		Connection con = null;

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("delete from st_holiday where HOLIDAY_ID=?");

			ps.setLong(1, dto.getHolidayId());

			ps.executeUpdate();

			ps.close();

			con.commit();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			try {

				con.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());

			}

			throw new ApplicationException("Exception : Exception in delete Holiday");

		} finally {

			JDBCDataSource.closeConnection(con);

		}
	}

	public void update(HolidayDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;

		HolidayDTO duplicateHoliday = findByCode(dto.getHolidayCode());

		if (duplicateHoliday != null && duplicateHoliday.getHolidayId() != dto.getHolidayId()) {

			throw new DuplicateRecordException("Holiday already exists");

		}

		try {

			con = JDBCDataSource.getConnection();

			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement(
					"update st_holiday set HOLIDAY_CODE=?,HOLIDAY_NAME=?,HOLIDAY_DATE=?,HOLIDAY_TYPE=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? where HOLIDAY_ID=?");

			ps.setString(1, dto.getHolidayCode());
			ps.setString(2, dto.getHolidayName());
			ps.setDate(3, (Date) dto.getHolidayDate());
			ps.setString(4, dto.getHolidayType());
			ps.setString(5, dto.getCreatedBy());
			ps.setString(6, dto.getModifiedBy());
			ps.setTimestamp(7, dto.getCreatedDatetime());
			ps.setTimestamp(8, dto.getModifiedDatetime());
			ps.setLong(9, dto.getHolidayId());

			ps.executeUpdate();

			ps.close();

			con.commit();

		} catch (Exception e) {

			log.error("Database Exception..", e);

			try {

				con.rollback();

			} catch (Exception ex) {

				throw new ApplicationException("Exception : update rollback exception " + ex.getMessage());

			}

			throw new ApplicationException("Exception in updating Holiday");

		} finally {

			JDBCDataSource.closeConnection(con);

		}
	}

	public List list() throws ApplicationException {

		return list(0, 0);

	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_holiday");

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + "," + pageSize);

		}

		Connection conn = null;

		HolidayDTO dto = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				dto = new HolidayDTO();

				dto.setHolidayId(rs.getLong("HOLIDAY_ID"));
				dto.setHolidayCode(rs.getString("HOLIDAY_CODE"));
				dto.setHolidayName(rs.getString("HOLIDAY_NAME"));
				dto.setHolidayDate(rs.getDate("HOLIDAY_DATE"));
				dto.setHolidayType(rs.getString("HOLIDAY_TYPE"));

				list.add(dto);

			}

			rs.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting list of Holiday");

		} finally {

			JDBCDataSource.closeConnection(conn);

		}

		return list;

	}

	public HolidayDTO findByPK(long pk) throws ApplicationException {

		Connection con = null;

		HolidayDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("select * from st_holiday where HOLIDAY_ID=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new HolidayDTO();

				dto.setHolidayId(rs.getLong("HOLIDAY_ID"));
				dto.setHolidayCode(rs.getString("HOLIDAY_CODE"));
				dto.setHolidayName(rs.getString("HOLIDAY_NAME"));
				dto.setHolidayDate(rs.getDate("HOLIDAY_DATE"));
				dto.setHolidayType(rs.getString("HOLIDAY_TYPE"));

			}

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting Holiday by pk");

		} finally {

			JDBCDataSource.closeConnection(con);

		}

		return dto;

	}

	public HolidayDTO findByCode(String code) throws ApplicationException {

		Connection con = null;

		HolidayDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("select * from st_holiday where HOLIDAY_CODE=?");

			ps.setString(1, code);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new HolidayDTO();

				dto.setHolidayId(rs.getLong("HOLIDAY_ID"));
				dto.setHolidayCode(rs.getString("HOLIDAY_CODE"));
				dto.setHolidayName(rs.getString("HOLIDAY_NAME"));
				dto.setHolidayDate(rs.getDate("HOLIDAY_DATE"));
				dto.setHolidayType(rs.getString("HOLIDAY_TYPE"));

			}

		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting Holiday by code");

		} finally {

			JDBCDataSource.closeConnection(con);

		}

		return dto;

	}

	@Override
	public List search(HolidayDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(HolidayDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
}
