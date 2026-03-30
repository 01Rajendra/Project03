package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ComplaintDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Complaint model
 * @author Rajendra Negi
 */
public interface ComplaintModelInt {

    public long add(ComplaintDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(ComplaintDTO dto) throws ApplicationException;

    public void update(ComplaintDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(ComplaintDTO dto) throws ApplicationException;

    public List search(ComplaintDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public ComplaintDTO findByPK(long pk) throws ApplicationException;

    public ComplaintDTO findByName(String name) throws ApplicationException;

}