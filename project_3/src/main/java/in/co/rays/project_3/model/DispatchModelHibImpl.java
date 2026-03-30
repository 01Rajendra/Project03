package in.co.rays.project_3.model;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.DispatchDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of Dispatch model
 * @author Rajendra Negi
 *
 */

public class DispatchModelHibImpl implements DispatchModelInt {

	public long add(DispatchDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		long pk = 0;

		DispatchDTO existDto = findByCourierName(dto.getCourierName());

		if (existDto != null) {
			throw new DuplicateRecordException("Dispatch already exist");
		}

		session = HibDataSource.getSession();

		try {

			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			tx.commit();

		} catch (HibernateException e) {

			e.printStackTrace();

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in Dispatch Add " + e.getMessage());

		} finally {
			session.close();
		}

		return pk;
	}

	public void delete(DispatchDTO dto) throws ApplicationException {

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

			throw new ApplicationException("Exception in Dispatch delete " + e.getMessage());

		} finally {
			session.close();
		}
	}

	public void update(DispatchDTO dto) throws ApplicationException, DuplicateRecordException {

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

			throw new ApplicationException("Exception in Dispatch update " + e.getMessage());

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

			Criteria criteria = session.createCriteria(DispatchDTO.class);

			if (pageSize > 0) {

				pageNo = ((pageNo - 1) * pageSize) + 1;

				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (JDBCConnectionException e) {

			throw e;

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Dispatch list");

		} finally {

			session.close();
		}

		return list;
	}

	public List search(DispatchDTO dto) throws ApplicationException {

		return search(dto, 0, 0);
	}

	public List search(DispatchDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(DispatchDTO.class);

			if (dto.getDispatchId() != null && dto.getDispatchId() > 0) {
				criteria.add(Restrictions.eq("dispatchId", dto.getDispatchId()));
			}

			if (dto.getCourierName() != null && dto.getCourierName().length() > 0) {
				criteria.add(Restrictions.like("courierName", dto.getCourierName() + "%"));
			}

			if (dto.getStatus() != null && dto.getStatus().length() > 0) {
				criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
			}

			if (dto.getDispatchDate() != null) {
				criteria.add(Restrictions.eq("dispatchDate", dto.getDispatchDate()));
			}

			if (pageSize > 0) {

				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in Dispatch search");

		} finally {

			session.close();
		}

		return list;
	}

	public DispatchDTO findByPK(long pk) throws ApplicationException {

		Session session = HibDataSource.getSession();

		try {

			DispatchDTO dto = (DispatchDTO) session.get(DispatchDTO.class, pk);

			return dto;

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Dispatch by pk");

		} finally {

			session.close();
		}
	}

	public DispatchDTO findByCourierName(String courierName) throws ApplicationException {

		Session session = null;
		DispatchDTO dto = null;

		try {

			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(DispatchDTO.class);

			criteria.add(Restrictions.eq("courierName", courierName));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (DispatchDTO) list.get(0);
			}

		} catch (HibernateException e) {

			throw new ApplicationException("Exception in getting Dispatch by courierName " + e.getMessage());

		} finally {

			session.close();
		}

		return dto;
	}

	@Override
	public DispatchDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}