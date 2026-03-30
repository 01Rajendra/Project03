package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.HostelDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Hostel model
 * 
 * @author Rajendra Negi
 *
 */
public interface HostelModelInt {

	public long add(HostelDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(HostelDTO dto) throws ApplicationException;

	public void update(HostelDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(HostelDTO dto) throws ApplicationException;

	public List search(HostelDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public HostelDTO findByPK(long pk) throws ApplicationException;

	public HostelDTO findByHostelName(String hostelName) throws ApplicationException;

}
