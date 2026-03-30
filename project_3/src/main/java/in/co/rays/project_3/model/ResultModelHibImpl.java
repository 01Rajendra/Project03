package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ResultDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Result model
 * 
 * @author Rajendra Negi
 *
 */
public class ResultModelHibImpl implements ResultModelInt {

	// ADD
	public long add(ResultDTO dto) throws ApplicationException, DuplicateRecordException {

		ResultDTO existDto = findByResultCode(dto.getResultCode());

		if (existDto != null) {
			throw new DuplicateRecordException("Result Code already exists");
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
			throw new ApplicationException("Exception in Result Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	// DELETE
	public void delete(ResultDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Result Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	// UPDATE
	public void update(ResultDTO dto) throws ApplicationException, DuplicateRecordException {

		ResultDTO existDto = findByResultCode(dto.getResultCode());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Result Code already exists");
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
			throw new ApplicationException("Exception in Result Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	// FIND BY PK
	public ResultDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		ResultDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (ResultDTO) session.get(ResultDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Result by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	// FIND BY RESULT CODE
	public ResultDTO findByResultCode(String resultCode) throws ApplicationException {

		Session session = null;
		ResultDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ResultDTO.class);
			criteria.add(Restrictions.eq("resultCode", resultCode));

			List list = criteria.list();

			if (list.size() == 1) {
				dto = (ResultDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Result by Code");
		} finally {
			session.close();
		}

		return dto;
	}

	// LIST
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ResultDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Result list");
		} finally {
			session.close();
		}

		return list;
	}

	// SEARCH
	public List search(ResultDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(ResultDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<ResultDTO> list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ResultDTO.class);

			if (dto != null) {

				if (dto.getId() != null) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getResultCode() != null && dto.getResultCode().length() > 0) {
					criteria.add(Restrictions.like("resultCode", dto.getResultCode() + "%"));
				}

				if (dto.getStudentName() != null && dto.getStudentName().length() > 0) {
					criteria.add(Restrictions.like("studentName", dto.getStudentName() + "%"));
				}

				if (dto.getMarks() != null && dto.getMarks() > 0) {
					criteria.add(Restrictions.eq("marks", dto.getMarks()));
				}

				if (dto.getResultStatus() != null && dto.getResultStatus().length() > 0) {
					criteria.add(Restrictions.like("resultStatus", dto.getResultStatus() + "%"));
				}
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<ResultDTO>) criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Result search");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public ResultDTO findByName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
}