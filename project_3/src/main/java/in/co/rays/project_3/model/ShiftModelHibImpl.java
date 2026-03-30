package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ShiftDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Shift model
 * 
 * @author  Rajendra Negi
 *
 */
public class ShiftModelHibImpl implements ShiftModelInt {

	@Override
	public long add(ShiftDTO dto) throws ApplicationException, DuplicateRecordException {

		ShiftDTO existDto = findByShiftCode(dto.getShiftCode());

		if (existDto != null) {
			throw new DuplicateRecordException("Shift Code already exists");
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
			throw new ApplicationException("Exception in Shift Add " + e.getMessage());
		} finally {
			session.close();
		}

		return dto.getId();
	}

	@Override
	public void delete(ShiftDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Shift Delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(ShiftDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		ShiftDTO existDto = findByShiftCode(dto.getShiftCode());

		if (existDto != null && existDto.getId() != dto.getId()) {
			throw new DuplicateRecordException("Shift Code already exists");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Shift Update " + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public ShiftDTO findByPK(long pk) throws ApplicationException {

		Session session = null;
		ShiftDTO dto = null;

		try {
			session = HibDataSource.getSession();
			dto = (ShiftDTO) session.get(ShiftDTO.class, pk);
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Shift by PK");
		} finally {
			session.close();
		}

		return dto;
	}

	public ShiftDTO findByShiftCode(String shiftCode) throws ApplicationException {

		Session session = null;
		ShiftDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ShiftDTO.class);
			criteria.add(Restrictions.eq("shiftCode", shiftCode));

			List list = criteria.list();
			if (list.size() == 1) {
				dto = (ShiftDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Shift by Code " + e.getMessage());
		} finally {
			session.close();
		}

		return dto;
	}

	@Override
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	@Override
	public List list(int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ShiftDTO.class);

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Shift List");
		} finally {
			session.close();
		}

		return list;
	}

	@Override
	public List search(ShiftDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(ShiftDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		ArrayList<ShiftDTO> list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ShiftDTO.class);

			if (dto != null) {

				if (dto.getId() != null && dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}

				if (dto.getShiftCode() != null && dto.getShiftCode().length() > 0) {
					criteria.add(Restrictions.like("shiftCode", dto.getShiftCode() + "%"));
				}

				if (dto.getShiftName() != null && dto.getShiftName().length() > 0) {
					criteria.add(Restrictions.like("shiftName", dto.getShiftName() + "%"));
				}

				if (dto.getStartTime() != null) {
					criteria.add(Restrictions.eq("startTime", dto.getStartTime()));
				}

				if (dto.getEndTime() != null) {
					criteria.add(Restrictions.eq("endTime", dto.getEndTime()));
				}
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = (ArrayList<ShiftDTO>) criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Shift Search");
		} finally {
			session.close();
		}

		return list;
	}

}