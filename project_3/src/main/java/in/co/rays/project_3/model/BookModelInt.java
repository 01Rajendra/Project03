package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.BookDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Book model
 * @author Rajendra Negi
 *
 */

public interface BookModelInt {

	public long add(BookDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(BookDTO dto) throws ApplicationException;

	public void update(BookDTO dto) throws ApplicationException, DuplicateRecordException;

	public BookDTO findByPK(long pk) throws ApplicationException;

	public BookDTO findByTitle(String title) throws ApplicationException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(BookDTO dto) throws ApplicationException;

	public List search(BookDTO dto, int pageNo, int pageSize) throws ApplicationException;

}