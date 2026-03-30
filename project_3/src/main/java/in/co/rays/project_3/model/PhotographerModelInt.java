package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.PhotographerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;

/**
 * Interface of Photographer model
 * 
 * @author Rajendra Negi
 *
 */
public interface PhotographerModelInt {

	public long add(PhotographerDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(PhotographerDTO dto) throws ApplicationException;

	public void update(PhotographerDTO dto) throws ApplicationException, DuplicateRecordException;

	public PhotographerDTO findByPK(long pk) throws ApplicationException;

	public PhotographerDTO findByName(String photographerName) throws ApplicationException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(PhotographerDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public List search(PhotographerDTO dto) throws ApplicationException;

	public boolean changePassword(long id, String newEventType, String oldEventType)
			throws ApplicationException, RecordNotFoundException;

	public PhotographerDTO authenticate(String photographerName, String eventType) throws ApplicationException;

	public boolean forgetPassword(String photographerName) throws ApplicationException, RecordNotFoundException;

	public boolean resetPassword(PhotographerDTO dto) throws ApplicationException, RecordNotFoundException;

	public long registerUser(PhotographerDTO dto) throws ApplicationException, DuplicateRecordException;

	public List getRoles(PhotographerDTO dto) throws ApplicationException;

}