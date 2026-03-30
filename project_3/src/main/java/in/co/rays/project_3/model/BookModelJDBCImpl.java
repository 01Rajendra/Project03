package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BookDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class BookModelJDBCImpl implements BookModelInt {

	private static Logger log = Logger.getLogger(BookModelJDBCImpl.class);

	public long nextPK() throws DatabaseException {

		Connection con = null;
		long pk = 0;

		try {

			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(ID) from st_book");
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

	public long add(BookDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;
		long pk = 0;

		BookDTO duplicateBook = findByTitle(dto.getBookTitle());

		if (duplicateBook != null) {
			throw new DuplicateRecordException("Book already exists");
		}

		try {

			pk = nextPK();

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("insert into st_book values(?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getBookTitle());
			ps.setString(3, dto.getRenterName());
			ps.setDate(4, new java.sql.Date(dto.getRentDate().getTime()));
			ps.setDate(5, new java.sql.Date(dto.getReturnDate().getTime()));

			ps.executeUpdate();

			ps.close();
			con.commit();

		} catch (Exception e) {

			log.error("Database Exception", e);

			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in Book Add");

		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return pk;
	}

	public void delete(BookDTO dto) throws ApplicationException {

		Connection con = null;

		try {

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement("delete from st_book where ID=?");

			ps.setLong(1, dto.getId());

			ps.executeUpdate();

			ps.close();
			con.commit();

		} catch (Exception e) {

			log.error("Database Exception", e);

			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in Book Delete");

		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	public void update(BookDTO dto) throws ApplicationException, DuplicateRecordException {

		Connection con = null;

		try {

			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);

			PreparedStatement ps = con.prepareStatement(
					"update st_book set BOOK_TITLE=?,RENTER_NAME=?,RENT_DATE=?,RETURN_DATE=? where ID=?");

			ps.setString(1, dto.getBookTitle());
			ps.setString(2, dto.getRenterName());
			ps.setDate(3, new java.sql.Date(dto.getRentDate().getTime()));
			ps.setDate(4, new java.sql.Date(dto.getReturnDate().getTime()));
			ps.setLong(5, dto.getId());

			ps.executeUpdate();

			ps.close();
			con.commit();

		} catch (Exception e) {

			log.error("Database Exception", e);

			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Rollback Exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in Book Update");

		} finally {
			JDBCDataSource.closeConnection(con);
		}
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_book");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		BookDTO dto = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				dto = new BookDTO();

				dto.setId(rs.getLong(1));
				dto.setBookTitle(rs.getString(2));
				dto.setRenterName(rs.getString(3));
				dto.setRentDate(rs.getDate(4));
				dto.setReturnDate(rs.getDate(5));

				list.add(dto);
			}

			rs.close();

		} catch (Exception e) {

			log.error("Database Exception", e);
			throw new ApplicationException("Exception in getting Book list");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	public BookDTO findByPK(long pk) throws ApplicationException {

		Connection con = null;
		BookDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("select * from st_book where ID=?");

			ps.setLong(1, pk);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new BookDTO();

				dto.setId(rs.getLong(1));
				dto.setBookTitle(rs.getString(2));
				dto.setRenterName(rs.getString(3));
				dto.setRentDate(rs.getDate(4));
				dto.setReturnDate(rs.getDate(5));
			}

		} catch (Exception e) {

			log.error("Database Exception", e);
			throw new ApplicationException("Exception in getting Book by PK");

		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	public BookDTO findByTitle(String title) throws ApplicationException {

		Connection con = null;
		BookDTO dto = null;

		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement("select * from st_book where BOOK_TITLE=?");

			ps.setString(1, title);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new BookDTO();

				dto.setId(rs.getLong(1));
				dto.setBookTitle(rs.getString(2));
				dto.setRenterName(rs.getString(3));
				dto.setRentDate(rs.getDate(4));
				dto.setReturnDate(rs.getDate(5));
			}

		} catch (Exception e) {

			log.error("Database Exception", e);
			throw new ApplicationException("Exception in getting Book by Title");

		} finally {
			JDBCDataSource.closeConnection(con);
		}

		return dto;
	}

	public List search(BookDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(BookDTO dto, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_book where 1=1");

		if (dto != null) {

			if (dto.getId() > 0) {
				sql.append(" AND ID=" + dto.getId());
			}

			if (dto.getBookTitle() != null && dto.getBookTitle().length() > 0) {
				sql.append(" AND BOOK_TITLE like '" + dto.getBookTitle() + "%'");
			}

			if (dto.getRenterName() != null && dto.getRenterName().length() > 0) {
				sql.append(" AND RENTER_NAME like '" + dto.getRenterName() + "%'");
			}
		}

		if (pageSize > 0) {

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				dto = new BookDTO();

				dto.setId(rs.getLong(1));
				dto.setBookTitle(rs.getString(2));
				dto.setRenterName(rs.getString(3));
				dto.setRentDate(rs.getDate(4));
				dto.setReturnDate(rs.getDate(5));

				list.add(dto);
			}

			rs.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in Book Search " + e.getMessage());

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}