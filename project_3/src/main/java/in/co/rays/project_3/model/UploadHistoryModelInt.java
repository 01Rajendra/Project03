package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.UploadHistoryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of UploadHistory model
 * 
 * @author Rajendra Negi
 *
 */

public interface UploadHistoryModelInt {

	public long add(UploadHistoryDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(UploadHistoryDTO dto) throws ApplicationException;

	public void update(UploadHistoryDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(UploadHistoryDTO dto) throws ApplicationException;

	public List search(UploadHistoryDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public UploadHistoryDTO findByPK(long pk) throws ApplicationException;

	public UploadHistoryDTO findByCode(String code) throws ApplicationException;

}