package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.BookDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of Book model
 * @author Rajendra Negi
 */

public class BookModelHibImpl implements BookModelInt {

	public long add(BookDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		long pk = 0;

		BookDTO existDto = findByTitle(dto.getBookTitle());

		if (existDto != null) {
			throw new DuplicateRecordException("Book already exist");
		}

		session = HibDataSource.getSession();

		try {
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in Book Add " + e.getMessage());

		} finally {
			session.close();
		}

		return pk;
	}

	public void delete(BookDTO dto) throws ApplicationException {

		Session session = null;
		Transaction tx = null;

		try {

			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in Book delete " + e.getMessage());

		} finally {
			session.close();
		}
	}

	public void update(BookDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		try {

			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();

		} catch (JDBCConnectionException e) {

		} catch (HibernateException e) {

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in Book update " + e.getMessage());

		} finally {
			session.close();
		}
	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(BookDTO.class);

			if (pageSize > 0) {

				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Book list");

		} finally {
			session.close();
		}

		return list;
	}

	public List search(BookDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(BookDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(BookDTO.class);

			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}

			if (dto.getBookTitle() != null && dto.getBookTitle().length() > 0) {
				criteria.add(Restrictions.like("bookTitle", dto.getBookTitle() + "%"));
			}

			if (dto.getRenterName() != null && dto.getRenterName().length() > 0) {
				criteria.add(Restrictions.like("renterName", dto.getRenterName() + "%"));
			}

			if (pageSize > 0) {

				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Book search");

		} finally {
			session.close();
		}

		return list;
	}

	public BookDTO findByPK(long pk) throws ApplicationException {

		Session session = HibDataSource.getSession();

		try {

			BookDTO dto = (BookDTO) session.get(BookDTO.class, pk);
			return dto;

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Book by PK");

		} finally {
			session.close();
		}
	}

	public BookDTO findByTitle(String title) throws ApplicationException {

		Session session = null;
		BookDTO dto = null;

		try {

			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(BookDTO.class);

			criteria.add(Restrictions.eq("bookTitle", title));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (BookDTO) list.get(0);
			}

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Book by Title " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}
}