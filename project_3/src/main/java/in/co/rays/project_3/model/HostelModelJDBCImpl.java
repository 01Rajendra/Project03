package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.HostelDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

/**
 * JDBC implements of Hostel model
 * 
 * @author Rajendra Negi
 *
 */
public class HostelModelJDBCImpl implements HostelModelInt {

	private static Logger log = Logger.getLogger(HostelModelJDBCImpl.class);

	public long nextPK() throws DatabaseException {
		log.debug(" pk start");
		Connection con = null;
		long pk = 0;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(id) from ST_HOSTEL");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
		} catch (Exception e) {
			log.error(e);
			throw new DatabaseException("Database Exception" + e);

		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug(" pk is end");
		return pk + 1;
	}

	public long add(HostelDTO dto) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		long pk = 0;

		HostelDTO duplicateHostel = findByHostelName(dto.getHostelName());
		if (duplicateHostel != null) {
			throw new DuplicateRecordException("Hostel Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_HOSTEL VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getHostelName());
			ps.setInt(3, dto.getCapacity());
			ps.setInt(4, dto.getNumberOfRooms());
			ps.setString(5, dto.getOccupancy());
			ps.setString(6, dto.getRoomType());
			ps.setString(7, dto.getWashroomStatus());
			ps.setString(8, dto.getStatus());
			ps.setString(9, dto.getFees());
			ps.setString(10, dto.getCreatedBy());
			ps.setString(11, dto.getModifiedBy());
			ps.setTimestamp(12, dto.getCreatedDatetime());
			// If you also store modifiedDatetime at insert, then add one more column

			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Hostel");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	public void delete(HostelDTO dto) throws ApplicationException {
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_HOSTEL WHERE ID=?");
			ps.setLong(1, dto.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Hostel");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete End");
	}

	public void update(HostelDTO dto) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;

		HostelDTO dtoExist = findByHostelName(dto.getHostelName());

		// Check if updated Hostel already exist
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("Hostel already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ST_HOSTEL SET HOSTEL_NAME=?,CAPACITY=?,NO_OF_ROOMS=?,OCCUPANCY=?,ROOM_TYPE=?,WASHROOM_STATUS=?,STATUS=?,FEES=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			ps.setString(1, dto.getHostelName());
			ps.setInt(2, dto.getCapacity());
			ps.setInt(3, dto.getNumberOfRooms());
			ps.setString(4, dto.getOccupancy());
			ps.setString(5, dto.getRoomType());
			ps.setString(6, dto.getWashroomStatus());
			ps.setString(7, dto.getStatus());
			ps.setString(8, dto.getFees());
			ps.setString(9, dto.getCreatedBy());
			ps.setString(10, dto.getModifiedBy());
			ps.setTimestamp(11, dto.getCreatedDatetime());
			ps.setTimestamp(12, dto.getModifiedDatetime());
			ps.setLong(13, dto.getId());

			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Hostel ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_HOSTEL");

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
				HostelDTO dto = new HostelDTO();
				dto.setId(rs.getLong(1));
				dto.setHostelName(rs.getString(2));
				dto.setCapacity(rs.getInt(3));
				dto.setNumberOfRooms(rs.getInt(4));
				dto.setOccupancy(rs.getString(5));
				dto.setRoomType(rs.getString(6));
				dto.setWashroomStatus(rs.getString(7));
				dto.setStatus(rs.getString(8));
				dto.setFees(rs.getString(9));
				dto.setCreatedBy(rs.getString(10));
				dto.setModifiedBy(rs.getString(11));
				dto.setCreatedDatetime(rs.getTimestamp(12));
				dto.setModifiedDatetime(rs.getTimestamp(13));
				list.add(dto);
			}
			rs.close();

		} catch (JDBCConnectionException e) {
			throw e;
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of Hostels");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;
	}

	public List search(HostelDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(HostelDTO dto, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_HOSTEL WHERE 1=1");

		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND ID = " + dto.getId());
			}
			if (dto.getHostelName() != null && dto.getHostelName().length() > 0) {
				sql.append(" AND HOSTEL_NAME like '" + dto.getHostelName() + "%'");
			}
			if (dto.getOccupancy() != null && dto.getOccupancy().length() > 0) {
				sql.append(" AND OCCUPANCY = '" + dto.getOccupancy() + "'");
			}
			if (dto.getRoomType() != null && dto.getRoomType().length() > 0) {
				sql.append(" AND ROOM_TYPE = '" + dto.getRoomType() + "'");
			}
			if (dto.getStatus() != null && dto.getStatus().length() > 0) {
				sql.append(" AND STATUS = '" + dto.getStatus() + "'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				dto = new HostelDTO();
				dto.setId(rs.getLong(1));
				dto.setHostelName(rs.getString(2));
				dto.setCapacity(rs.getInt(3));
				dto.setNumberOfRooms(rs.getInt(4));
				dto.setOccupancy(rs.getString(5));
				dto.setRoomType(rs.getString(6));
				dto.setWashroomStatus(rs.getString(7));
				dto.setStatus(rs.getString(8));
				dto.setFees(rs.getString(9));
				dto.setCreatedBy(rs.getString(10));
				dto.setModifiedBy(rs.getString(11));
				dto.setCreatedDatetime(rs.getTimestamp(12));
				dto.setModifiedDatetime(rs.getTimestamp(13));
				list.add(dto);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search Hostel");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;
	}

	public HostelDTO findByPK(long pk) throws ApplicationException {
		Connection conn = null;
		HostelDTO dto = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ST_HOSTEL WHERE ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new HostelDTO();
				dto.setId(rs.getLong(1));
				dto.setHostelName(rs.getString(2));
				dto.setCapacity(rs.getInt(3));
				dto.setNumberOfRooms(rs.getInt(4));
				dto.setOccupancy(rs.getString(5));
				dto.setRoomType(rs.getString(6));
				dto.setWashroomStatus(rs.getString(7));
				dto.setStatus(rs.getString(8));
				dto.setFees(rs.getString(9));
				dto.setCreatedBy(rs.getString(10));
				dto.setModifiedBy(rs.getString(11));
				dto.setCreatedDatetime(rs.getTimestamp(12));
				dto.setModifiedDatetime(rs.getTimestamp(13));
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Hostel by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return dto;
	}

	public HostelDTO findByHostelName(String hostelName) throws ApplicationException {
		Connection conn = null;
		HostelDTO dto = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ST_HOSTEL WHERE HOSTEL_NAME=? ");
			ps.setString(1, hostelName);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new HostelDTO();
				dto.setId(rs.getLong(1));
				dto.setHostelName(rs.getString(2));
				dto.setCapacity(rs.getInt(3));
				dto.setNumberOfRooms(rs.getInt(4));
				dto.setOccupancy(rs.getString(5));
				dto.setRoomType(rs.getString(6));
				dto.setWashroomStatus(rs.getString(7));
				dto.setStatus(rs.getString(8));
				dto.setFees(rs.getString(9));
				dto.setCreatedBy(rs.getString(10));
				dto.setModifiedBy(rs.getString(11));
				dto.setCreatedDatetime(rs.getTimestamp(12));
				dto.setModifiedDatetime(rs.getTimestamp(13));
			}

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Hostel by Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByHostelName End");
		return dto;
	}

}
