package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ResultDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Result model
 * 
 * @author Rajendra Negi
 *
 */
public interface ResultModelInt {

	public long add(ResultDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(ResultDTO dto) throws ApplicationException;

	public void update(ResultDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(ResultDTO dto) throws ApplicationException;

	public List search(ResultDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public ResultDTO findByPK(long pk) throws ApplicationException;

	public ResultDTO findByName(String name) throws ApplicationException;

}