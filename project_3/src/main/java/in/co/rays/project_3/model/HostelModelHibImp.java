package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.HostelDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of Hostel model
 * 
 * @author Rajendra Negi
 *
 */
public class HostelModelHibImp implements HostelModelInt {

	public long add(HostelDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;

		HostelDTO duplicateHostel = findByHostelName(dto.getHostelName());
		if (duplicateHostel != null) {
			throw new DuplicateRecordException("Hostel name already exist");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Hostel Add " + e.getMessage());
		} finally {
			session.close();
		}
		return dto.getId();
	}

	public void delete(HostelDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Hostel Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(HostelDTO dto) throws ApplicationException, DuplicateRecordException {
		Session session = null;
		Transaction tx = null;

		HostelDTO dtoExist = findByHostelName(dto.getHostelName());

		// Uncomment if you want strict duplicate check
		/*
		 * if (dtoExist != null && dtoExist.getId() != dto.getId()) { throw new
		 * DuplicateRecordException("Hostel already exist"); }
		 */

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			System.out.println("before update");

			session.saveOrUpdate(dto);

			System.out.println("after update");
			tx.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Hostel Update " + e.getMessage());
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
			Criteria criteria = session.createCriteria(HostelDTO.class);

			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize) + 1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (JDBCConnectionException e) {
			throw e;
		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in Hostel list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(HostelDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(HostelDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(HostelDTO.class);

			if (dto.getId() != null && dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}

			if (dto.getHostelName() != null && dto.getHostelName().length() > 0) {
				criteria.add(Restrictions.like("hostelName", dto.getHostelName() + "%"));
			}

			if (dto.getOccupancy() != null && dto.getOccupancy().length() > 0) {
				criteria.add(Restrictions.eq("occupancy", dto.getOccupancy()));
			}

			if (dto.getRoomType() != null && dto.getRoomType().length() > 0) {
				criteria.add(Restrictions.eq("roomType", dto.getRoomType()));
			}

			if (dto.getStatus() != null && dto.getStatus().length() > 0) {
				criteria.add(Restrictions.eq("status", dto.getStatus()));
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in Hostel search");
		} finally {
			session.close();
		}
		return list;
	}

	public HostelDTO findByPK(long pk) throws ApplicationException {
		System.out.println("======" + pk + "----------------------------------");
		Session session = null;
		HostelDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (HostelDTO) session.get(HostelDTO.class, pk);
			System.out.println(dto);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Hostel by pk");
		} finally {
			session.close();
		}

		System.out.println("++++" + dto);
		return dto;
	}

	public HostelDTO findByHostelName(String hostelName) throws ApplicationException {
		Session session = null;
		HostelDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(HostelDTO.class);
			criteria.add(Restrictions.eq("hostelName", hostelName));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (HostelDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Hostel by Name " + e.getMessage());
		} finally {
			session.close();
		}
		return dto;
	}

}
