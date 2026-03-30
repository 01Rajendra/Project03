package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.HolidayDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Holiday model
 * @author Rajendra Negi
 *
 */

public interface HolidayModelInt {

    public long add(HolidayDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(HolidayDTO dto) throws ApplicationException;

    public void update(HolidayDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(HolidayDTO dto) throws ApplicationException;

    public List search(HolidayDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public HolidayDTO findByPK(long pk) throws ApplicationException;

    public HolidayDTO findByCode(String code) throws ApplicationException;

}