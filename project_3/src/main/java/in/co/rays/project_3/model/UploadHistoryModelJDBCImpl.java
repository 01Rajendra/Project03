package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.UploadHistoryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class UploadHistoryModelJDBCImpl implements UploadHistoryModelInt {

	private static Logger log = Logger.getLogger(UploadHistoryModelJDBCImpl.class);

	public long nextPK() throws DatabaseException {
		Connection con = null;
		long pk = 0;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(uploadId) from ST_UPLOAD_HISTORY");
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

	public long add(UploadHistoryDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;
		long pk = 0;

		// UNIQUE check
		UploadHistoryDTO existDto = findByCode(dto.getUploadCode());
		if (existDto != null) {
			throw new DuplicateRecordException("Upload Code already exists");
		}

		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			pk = nextPK();

			PreparedStatement ps = con.prepareStatement(
					"insert into ST_UPLOAD_HISTORY (uploadId,uploadCode,fileName,uploadTime,uploadStatus) values(?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getUploadCode());
			ps.setString(3, dto.getFileName());
			ps.setTimestamp(4, Timestamp.valueOf(dto.getUploadTime()));
			ps.setString(5, dto.getUploadStatus());

			ps.executeUpdate();
			con.commit();

		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception ex) {
			}
			throw new ApplicationException("Exception in add");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return pk;
	}

	public void delete(UploadHistoryDTO dto) throws ApplicationException {

		Connection con = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("delete from ST_UPLOAD_HISTORY where uploadId=?");
			ps.setLong(1, dto.getUploadId());
			ps.executeUpdate();

		} catch (Exception e) {
			throw new ApplicationException("Exception in delete");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	public UploadHistoryDTO findByPK(long pk) throws ApplicationException {

		Connection con = null;
		UploadHistoryDTO dto = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from ST_UPLOAD_HISTORY where uploadId=?");
			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new UploadHistoryDTO();

				dto.setUploadId(rs.getLong("uploadId"));
				dto.setUploadCode(rs.getString("uploadCode"));
				dto.setFileName(rs.getString("fileName"));
				dto.setUploadTime(rs.getTimestamp("uploadTime").toLocalDateTime());
				dto.setUploadStatus(rs.getString("uploadStatus"));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByPK");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	// UNIQUE finder
	public UploadHistoryDTO findByCode(String code) throws ApplicationException {

		Connection con = null;
		UploadHistoryDTO dto = null;

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from ST_UPLOAD_HISTORY where uploadCode=?");
			ps.setString(1, code);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new UploadHistoryDTO();

				dto.setUploadId(rs.getLong("uploadId"));
				dto.setUploadCode(rs.getString("uploadCode"));
				dto.setFileName(rs.getString("fileName"));
				dto.setUploadTime(rs.getTimestamp("uploadTime").toLocalDateTime());
				dto.setUploadStatus(rs.getString("uploadStatus"));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByCode");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	public List list() throws ApplicationException {

		Connection con = null;
		List list = new ArrayList();

		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from ST_UPLOAD_HISTORY");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				UploadHistoryDTO dto = new UploadHistoryDTO();

				dto.setUploadId(rs.getLong("uploadId"));
				dto.setUploadCode(rs.getString("uploadCode"));
				dto.setFileName(rs.getString("fileName"));
				dto.setUploadTime(rs.getTimestamp("uploadTime").toLocalDateTime());
				dto.setUploadStatus(rs.getString("uploadStatus"));

				list.add(dto);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in list");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return list;
	}

	@Override
	public void update(UploadHistoryDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub

	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(UploadHistoryDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(UploadHistoryDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public UploadHistoryDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
}