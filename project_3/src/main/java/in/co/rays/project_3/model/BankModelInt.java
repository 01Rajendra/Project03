package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.BankDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Bank model
 * 
 * @author  Rajendra Negi Negi
 *
 */
public interface BankModelInt {

	public long add(BankDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(BankDTO dto) throws ApplicationException;

	public void update(BankDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(BankDTO dto) throws ApplicationException;

	public List search(BankDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public BankDTO findByPK(long pk) throws ApplicationException;

	public BankDTO findByName(String name) throws ApplicationException;

}