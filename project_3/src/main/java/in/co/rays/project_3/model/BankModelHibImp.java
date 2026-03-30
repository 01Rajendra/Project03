package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import in.co.rays.project_3.dto.BankDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implements of Bank model
 * 
 * @author  Rajendra Negi Negi
 *
 */
public class BankModelHibImp implements BankModelInt {

	public long add(BankDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = null;
		Transaction tx = null;
		long pk = 0;

		BankDTO existDto = findByName(dto.getBank_Name());

		if (existDto != null) {
			throw new DuplicateRecordException("Bank already exists");
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
			throw new ApplicationException("Exception in Bank Add " + e.getMessage());
		} finally {
			session.close();
		}
		return pk;
	}

	public void delete(BankDTO dto) throws ApplicationException {

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
			throw new ApplicationException("Exception in Bank delete " + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(BankDTO dto) throws ApplicationException, DuplicateRecordException {

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
			throw new ApplicationException("Exception in Bank update " + e.getMessage());
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
			Criteria criteria = session.createCriteria(BankDTO.class);

			if (pageSize > 0) {
				pageNo = ((pageNo - 1) * pageSize);
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Bank list");
		} finally {
			session.close();
		}
		return list;
	}

	public List search(BankDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(BankDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(BankDTO.class);

			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}

			if (dto.getBank_Name() != null && dto.getBank_Name().length() > 0) {
				criteria.add(Restrictions.like("bank_Name", dto.getBank_Name() + "%"));
			}

			if (dto.getAccount_NO() != null && dto.getAccount_NO().length() > 0) {
				criteria.add(Restrictions.eq("account_NO", dto.getAccount_NO()));
			}

			if (dto.getCustomer_Name() != null && dto.getCustomer_Name().length() > 0) {
				criteria.add(Restrictions.like("customer_Name", dto.getCustomer_Name() + "%"));
			}

			if (dto.getDob() != null) {
				criteria.add(Restrictions.eq("dob", dto.getDob()));
			}

			if (dto.getAddress() != null && dto.getAddress().length() > 0) {
				criteria.add(Restrictions.like("address", dto.getAddress() + "%"));
			}

			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}

			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Bank search");
		} finally {
			session.close();
		}

		return list;
	}

	public BankDTO findByPK(long pk) throws ApplicationException {

		Session session = HibDataSource.getSession();

		try {
			BankDTO dto = (BankDTO) session.get(BankDTO.class, pk);
			return dto;
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Bank by PK");
		} finally {
			session.close();
		}
	}

	public BankDTO findByName(String name) throws ApplicationException {

		Session session = null;
		BankDTO dto = null;

		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(BankDTO.class);
			criteria.add(Restrictions.eq("bank_Name", name));
			List list = criteria.list();

			if (list.size() > 0) {
				dto = (BankDTO) list.get(0);
			}

		} catch (HibernateException e) {
			throw new ApplicationException("Exception in getting Bank by Name " + e.getMessage());
		} finally {
			session.close();
		}

		return dto;
	}

}