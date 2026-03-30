package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.PhotographerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;
import in.co.rays.project_3.util.HibDataSource;

public class PhotographerModelHibImpl {

	// ===================== ADD =====================
	public long add(PhotographerDTO dto) throws ApplicationException, DuplicateRecordException {

		PhotographerDTO existDto = findByName(dto.getPhotographerName());
		if (existDto != null) {
			throw new DuplicateRecordException("Photographer already exists");
		}

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Photographer Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	// ===================== DELETE =====================
	public void delete(PhotographerDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Photographer Delete");
		} finally {
			session.close();
		}
	}

	// ===================== UPDATE =====================
	public void update(PhotographerDTO dto) throws ApplicationException, DuplicateRecordException {

		PhotographerDTO existDto = findByName(dto.getPhotographerName());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Photographer already exists");
		}

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();

			session.saveOrUpdate(dto);

			tx.commit();

		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Photographer update");
		} finally {
			session.close();
		}
	}

	// ===================== FIND BY PK =====================
	public PhotographerDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		PhotographerDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (PhotographerDTO) session.get(PhotographerDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Photographer by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	// ===================== FIND BY NAME =====================
	public PhotographerDTO findByName(String name) throws ApplicationException {

		Session session = null;
		PhotographerDTO dto = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(PhotographerDTO.class);
			criteria.add(Restrictions.eq("photographerName", name));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (PhotographerDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Photographer by Name");
		} finally {
			session.close();
		}

		return dto;
	}

	// ===================== LIST =====================
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(PhotographerDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Photographer list");
		} finally {
			session.close();
		}

		return list;
	}

	// ===================== SEARCH =====================
	public List search(PhotographerDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(PhotographerDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<PhotographerDTO> list = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(PhotographerDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getPhotographerName() != null && dto.getPhotographerName().length() > 0) {
					criteria.add(Restrictions.like("photographerName", dto.getPhotographerName() + "%"));
				}

				if (dto.getEventType() != null && dto.getEventType().length() > 0) {
					criteria.add(Restrictions.like("eventType", dto.getEventType() + "%"));
				}

				if (dto.getCharges() > 0) {
					criteria.add(Restrictions.eq("charges", dto.getCharges()));
				}
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<PhotographerDTO>) criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Photographer search");
		} finally {
			session.close();
		}

		return list;
	}

	// ===================== AUTHENTICATE (OPTIONAL) =====================
	public PhotographerDTO authenticate(String name, String eventType) throws ApplicationException {

		Session session = null;
		PhotographerDTO dto = null;

		session = HibDataSource.getSession();

		Query q = session.createQuery("from PhotographerDTO where photographerName=? and eventType=?");
		q.setString(0, name);
		q.setString(1, eventType);

		List list = q.list();

		if (list.size() > 0) {
			dto = (PhotographerDTO) list.get(0);
		}

		return dto;
	}
}