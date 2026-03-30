package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.ComplaintDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of Complaint model
 * 
 * @author Rajendra Negi
 *
 */
public class ComplaintModelHibImpl implements ComplaintModelInt {

	public long add(ComplaintDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		long pk = 0;

		ComplaintDTO existDto = findByName(dto.getComplaintCode());

		if (existDto != null) {
			throw new DuplicateRecordException("Complaint already exist");
		}

		session = HibDataSource.getSession();

		try {
			tx = session.beginTransaction();
			session.save(dto);
			pk = dto.getComplaintId();
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();

			if (tx != null) {
				tx.rollback();
			}

			throw new ApplicationException("Exception in Complaint Add " + e.getMessage());

		} finally {
			session.close();
		}

		return pk;
	}

	public void delete(ComplaintDTO dto) throws ApplicationException {

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

			throw new ApplicationException("Exception in Complaint delete " + e.getMessage());

		} finally {
			session.close();
		}
	}

	public void update(ComplaintDTO dto) throws ApplicationException, DuplicateRecordException {

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

			throw new ApplicationException("Exception in Complaint update " + e.getMessage());

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
			Criteria criteria = session.createCriteria(ComplaintDTO.class);

			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (JDBCConnectionException e) {
			throw e;

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in complaint list");

		} finally {
			session.close();
		}

		return list;
	}

	public List search(ComplaintDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(ComplaintDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ComplaintDTO.class);

			if (dto.getComplaintCode() != null && dto.getComplaintCode().length() > 0) {
				criteria.add(Restrictions.eq("complaintId", dto.getComplaintId()));
			}

			if (dto.getComplaintCode() != null && dto.getComplaintCode().length() > 0) {
				criteria.add(Restrictions.like("complaintCode", dto.getComplaintCode() + "%"));
			}

			if (dto.getCustomerName() != null && dto.getCustomerName().length() > 0) {
				criteria.add(Restrictions.like("customerName", dto.getCustomerName() + "%"));
			}

			if (dto.getComplaintType() != null && dto.getComplaintType().length() > 0) {
				criteria.add(Restrictions.like("complaintType", dto.getComplaintType() + "%"));
			}

			if (dto.getComplaintStatus() != null && dto.getComplaintStatus().length() > 0) {
				criteria.add(Restrictions.like("complaintStatus", dto.getComplaintStatus() + "%"));
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in complaint search");

		} finally {
			session.close();
		}

		return list;
	}

	public ComplaintDTO findByPK(long pk) throws ApplicationException {

		Session session = HibDataSource.getSession();

		try {
			ComplaintDTO dto = (ComplaintDTO) session.get(ComplaintDTO.class, pk);
			return dto;

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Complaint by pk");

		} finally {
			session.close();
		}
	}

	public ComplaintDTO findByName(String name) throws ApplicationException {

		Session session = null;
		ComplaintDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ComplaintDTO.class);
			criteria.add(Restrictions.eq("complaintCode", name));
			List list = criteria.list();

			if (list.size() > 0) {
				dto = (ComplaintDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Complaint by Code " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

}