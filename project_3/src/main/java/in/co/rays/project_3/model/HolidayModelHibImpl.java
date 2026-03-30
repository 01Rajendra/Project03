package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.HolidayDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of Holiday model
 * @author Rajendra Negi
 *
 */

public class HolidayModelHibImpl implements HolidayModelInt {

	public long add(HolidayDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		long pk = 0;

		HolidayDTO existDto = findByCode(dto.getHolidayCode());

		if (existDto != null) {
			throw new DuplicateRecordException("Holiday already exist");
		}

		session = HibDataSource.getSession();

		try {

			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getHolidayId();
			tx.commit();

		} catch (HibernateException e) {

			e.printStackTrace();

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in Holiday Add " + e.getMessage());

		} finally {
			session.close();
		}

		return pk;
	}

	public void delete(HolidayDTO dto) throws ApplicationException {

		Session session = null;
		Transaction tx = null;

		try {

			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();

		} catch (HibernateException e) {

			e.printStackTrace();

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in Holiday delete " + e.getMessage());

		} finally {
			session.close();
		}

	}

	public void update(HolidayDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		try {

			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();

		} catch (JDBCConnectionException e) {

		} catch (HibernateException e) {

			e.printStackTrace();

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in Holiday update " + e.getMessage());

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
			Criteria criteria = session.createCriteria(HolidayDTO.class);

			if (pageSize > 0) {

				pageNo = ((pageNo - 1) * pageSize) + 1;

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (JDBCConnectionException e) {

			throw e;

		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in Holiday list");

		} finally {
			session.close();
		}

		return list;
	}

	public List search(HolidayDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(HolidayDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(HolidayDTO.class);

			if (dto.getHolidayId() > 0) {
				criteria.add(Restrictions.eq("holidayId", dto.getHolidayId()));
			}

			if (dto.getHolidayCode() != null && dto.getHolidayCode().length() > 0) {
				criteria.add(Restrictions.like("holidayCode", dto.getHolidayCode() + "%"));
			}

			if (dto.getHolidayName() != null && dto.getHolidayName().length() > 0) {
				criteria.add(Restrictions.like("holidayName", dto.getHolidayName() + "%"));
			}

			if (pageSize > 0) {

				criteria.setFirstResult((pageNo - 1) * pageSize);

				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Holiday search");

		} finally {
			session.close();
		}

		return list;
	}

	public HolidayDTO findByPK(long pk) throws ApplicationException {

		Session session = HibDataSource.getSession();

		try {

			HolidayDTO dto = (HolidayDTO) session.get(HolidayDTO.class, pk);

			return dto;

		} catch (HibernateException e) {

			throw new ApplicationException("Exception : Exception in getting Holiday by pk");

		} finally {
			session.close();
		}

	}

	public HolidayDTO findByCode(String code) throws ApplicationException {

		Session session = null;

		HolidayDTO dto = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(HolidayDTO.class);

			criteria.add(Restrictions.eq("holidayCode", code));

			List list = criteria.list();

			if (list.size() > 0) {

				dto = (HolidayDTO) list.get(0);
			}

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Holiday by Code " + e.getMessage());

		} finally {

			session.close();
		}

		return dto;
	}

}