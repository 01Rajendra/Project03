package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.PhotographerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class PhotographerModelJDBCImpl {

	private static Logger log = Logger.getLogger(PhotographerModelJDBCImpl.class);

	// ===================== NEXT PK =====================
	public long nextPK() throws DatabaseException {
		long pk = 0;
		Connection con = null;
		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT MAX(ID) FROM ST_PHOTOGRAPHER");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
		} catch (Exception e) {
			throw new DatabaseException("Exception in nextPK");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		return pk + 1;
	}

	// ===================== ADD =====================
	public long add(PhotographerDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;
		long pk = 0;

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			pk = nextPK();

			PreparedStatement ps = con.prepareStatement("INSERT INTO ST_PHOTOGRAPHER VALUES(?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setLong(2, dto.getPhotographerId());
			ps.setString(3, dto.getPhotographerName());
			ps.setString(4, dto.getEventType());
			ps.setDouble(5, dto.getCharges());;

			ps.executeUpdate();
			con.commit();

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception");
			}
			throw new ApplicationException("Exception in add Photographer");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return pk;
	}

	// ===================== DELETE =====================
	public void delete(PhotographerDTO dto) throws ApplicationException {

		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("DELETE FROM ST_PHOTOGRAPHER WHERE ID=?");

			ps.setLong(1, dto.getId());
			ps.executeUpdate();

			con.commit();

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception");
			}
			throw new ApplicationException("Exception in delete");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	// ===================== UPDATE =====================
	public void update(PhotographerDTO dto) throws ApplicationException {

		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(
					"UPDATE ST_PHOTOGRAPHER SET PHOTOGRAPHER_ID=?, NAME=?, EVENT_TYPE=?, CHARGES=? WHERE ID=?");

			ps.setLong(1, dto.getPhotographerId());
			ps.setString(2, dto.getPhotographerName());
			ps.setString(3, dto.getEventType());
			ps.setDouble(4, dto.getCharges());;
			ps.setLong(5, dto.getId());

			ps.executeUpdate();
			con.commit();

		} catch (Exception e) {
			throw new ApplicationException("Exception in update Photographer");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	// ===================== FIND BY PK =====================
	public PhotographerDTO findByPK(long pk) throws ApplicationException {

		Connection con = null;
		PhotographerDTO dto = null;

		try {
			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_PHOTOGRAPHER WHERE ID=?");

			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new PhotographerDTO();

				dto.setId(rs.getLong(1));
				dto.setPhotographerId(rs.getLong(2));
				dto.setPhotographerName(rs.getString(3));
				dto.setEventType(rs.getString(4));
				dto.setCharges(rs.getDouble(5));;
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPK");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	// ===================== LIST =====================
	public List list() throws ApplicationException {

		List list = new ArrayList();
		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("SELECT * FROM ST_PHOTOGRAPHER");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				PhotographerDTO dto = new PhotographerDTO();

				dto.setId(rs.getLong(1));
				dto.setPhotographerId(rs.getLong(2));
				dto.setPhotographerName(rs.getString(3));
				dto.setEventType(rs.getString(4));
				dto.setCharges(rs.getDouble(5));;

				list.add(dto);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in list");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return list;
	}
}