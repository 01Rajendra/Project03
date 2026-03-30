package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.SubscriptionPlanDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class SubscriptionPlanModelHibImpl implements SubscriptionPlanModelInt {

	@Override
	public long add(SubscriptionPlanDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		long pk = 0;

		SubscriptionPlanDTO existDto = findByName(dto.getPlanName());

		if (existDto != null) {
			throw new DuplicateRecordException("Plan already exists");
		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();

			session.save(dto);
			pk = dto.getPlanId();

			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw new ApplicationException("Exception in adding SubscriptionPlan");
		} finally {
			session.close();
		}

		return pk;
	}

	@Override
	public void delete(SubscriptionPlanDTO dto) throws ApplicationException {

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();

			session.delete(dto);

			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw new ApplicationException("Exception in delete");
		} finally {
			session.close();
		}
	}

	@Override
	public void update(SubscriptionPlanDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();

			session.update(dto);

			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw new ApplicationException("Exception in update");
		} finally {
			session.close();
		}
	}

	@Override
	public SubscriptionPlanDTO findByPK(long pk) throws ApplicationException {

		Session session = HibDataSource.getSession();
		SubscriptionPlanDTO dto = (SubscriptionPlanDTO) session.get(SubscriptionPlanDTO.class, pk);
		session.close();

		return dto;
	}

	@Override
	public SubscriptionPlanDTO findByName(String name) throws ApplicationException {

		Session session = null;
		SubscriptionPlanDTO dto = null;

		try {
			session = HibDataSource.getSession();

			Criteria criteria = session.createCriteria(SubscriptionPlanDTO.class);
			criteria.add(Restrictions.eq("planName", name));

			List list = criteria.list();

			if (list.size() > 0) {
				dto = (SubscriptionPlanDTO) list.get(0);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in findByName");
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

		Session session = HibDataSource.getSession();
		Criteria criteria = session.createCriteria(SubscriptionPlanDTO.class);

		if (pageSize > 0) {
			criteria.setFirstResult((pageNo - 1) * pageSize);
			criteria.setMaxResults(pageSize);
		}

		List list = criteria.list();
		session.close();

		return list;
	}

	@Override
	public List search(SubscriptionPlanDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	@Override
	public List search(SubscriptionPlanDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = HibDataSource.getSession();
		Criteria criteria = session.createCriteria(SubscriptionPlanDTO.class);

		if (dto.getPlanName() != null && dto.getPlanName().length() > 0) {
			criteria.add(Restrictions.like("planName", dto.getPlanName() + "%"));
		}

		if (pageSize > 0) {
			criteria.setFirstResult((pageNo - 1) * pageSize);
			criteria.setMaxResults(pageSize);
		}

		List list = criteria.list();
		session.close();

		return list;
	}
}