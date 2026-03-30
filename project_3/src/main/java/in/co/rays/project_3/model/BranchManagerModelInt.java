package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.BranchManagerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of BranchManager model
 * 
 * @author Rajendra Negi
 *
 */
public interface BranchManagerModelInt {

	public long add(BranchManagerDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(BranchManagerDTO dto) throws ApplicationException;

	public void update(BranchManagerDTO dto) throws ApplicationException, DuplicateRecordException;

	public BranchManagerDTO findByPK(long pk) throws ApplicationException;

	public BranchManagerDTO findByManagerName(String managerName) throws ApplicationException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(BranchManagerDTO dto) throws ApplicationException;

	public List search(BranchManagerDTO dto, int pageNo, int pageSize) throws ApplicationException;
}