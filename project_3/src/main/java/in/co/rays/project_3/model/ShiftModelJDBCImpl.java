package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.ShiftDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class ShiftModelJDBCImpl implements ShiftModelInt {

	private static Logger log = Logger.getLogger(ShiftModelJDBCImpl.class);

	// ========================= NEXT PK =========================

	public long nextPK() throws DatabaseException {

		Connection con = null;
		long pk = 0;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(id) from ST_SHIFT");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getLong(1);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			log.error(e);
			throw new DatabaseException("Database Exception : " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return pk + 1;
	}

	// ========================= ADD =========================

	@Override
	public long add(ShiftDTO dto) throws ApplicationException, DuplicateRecordException {

		ShiftDTO existDto = findByShiftCode(dto.getShiftCode());
		if (existDto != null) {
			throw new DuplicateRecordException("Shift Code already exists");
		}

		Connection con = null;
		long pk = 0;

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			pk = nextPK();

			PreparedStatement ps = con.prepareStatement(
					"insert into ST_SHIFT (ID, SHIFT_CODE, SHIFT_NAME, START_TIME, END_TIME) values (?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getShiftCode());
			ps.setString(3, dto.getShiftName());
			ps.setTime(4, java.sql.Time.valueOf(dto.getStartTime()));
			ps.setTime(5, java.sql.Time.valueOf(dto.getEndTime()));

			ps.executeUpdate();
			con.commit();
			ps.close();

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception : " + ex.getMessage());
			}
			throw new ApplicationException("Exception in adding Shift");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return pk;
	}

	// ========================= DELETE =========================

	@Override
	public void delete(ShiftDTO dto) throws ApplicationException {

		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("delete from ST_SHIFT where ID=?");

			ps.setLong(1, dto.getId());
			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in deleting Shift");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	// ========================= UPDATE =========================

	@Override
	public void update(ShiftDTO dto) throws ApplicationException, DuplicateRecordException {

		ShiftDTO existDto = findByShiftCode(dto.getShiftCode());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Shift Code already exists");
		}

		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(
					"update ST_SHIFT set SHIFT_CODE=?, SHIFT_NAME=?, START_TIME=?, END_TIME=? where ID=?");

			ps.setString(1, dto.getShiftCode());
			ps.setString(2, dto.getShiftName());
			ps.setTime(3, java.sql.Time.valueOf(dto.getStartTime()));
			ps.setTime(4, java.sql.Time.valueOf(dto.getEndTime()));
			ps.setLong(5, dto.getId());

			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in updating Shift");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	// ========================= FIND BY PK =========================

	@Override
	public ShiftDTO findByPK(long pk) throws ApplicationException {

		Connection con = null;
		ShiftDTO dto = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from ST_SHIFT where ID=?");

			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new ShiftDTO();
				dto.setId(rs.getLong("ID"));
				dto.setShiftCode(rs.getString("SHIFT_CODE"));
				dto.setShiftName(rs.getString("SHIFT_NAME"));
				dto.setStartTime(rs.getTime("START_TIME").toLocalTime());
				dto.setEndTime(rs.getTime("END_TIME").toLocalTime());
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Shift by PK");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	// ========================= FIND BY SHIFT CODE =========================

	public ShiftDTO findByShiftCode(String shiftCode) throws ApplicationException {

		Connection con = null;
		ShiftDTO dto = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from ST_SHIFT where SHIFT_CODE=?");

			ps.setString(1, shiftCode);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new ShiftDTO();
				dto.setId(rs.getLong("ID"));
				dto.setShiftCode(rs.getString("SHIFT_CODE"));
				dto.setShiftName(rs.getString("SHIFT_NAME"));
				dto.setStartTime(rs.getTime("START_TIME").toLocalTime());
				dto.setEndTime(rs.getTime("END_TIME").toLocalTime());
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Shift by Code");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	// ========================= LIST =========================

	@Override
	public List list() throws ApplicationException {

		List<ShiftDTO> list = new ArrayList<>();
		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from ST_SHIFT");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ShiftDTO dto = new ShiftDTO();
				dto.setId(rs.getLong("ID"));
				dto.setShiftCode(rs.getString("SHIFT_CODE"));
				dto.setShiftName(rs.getString("SHIFT_NAME"));
				dto.setStartTime(rs.getTime("START_TIME").toLocalTime());
				dto.setEndTime(rs.getTime("END_TIME").toLocalTime());
				list.add(dto);
			}

			rs.close();
			ps.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in listing Shifts");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return list;
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		return list(); // simple implementation
	}

	@Override
	public List search(ShiftDTO dto) throws ApplicationException {
		return list();
	}

	@Override
	public List search(ShiftDTO dto, int pageNo, int pageSize) throws ApplicationException {
		return list();
	}
}