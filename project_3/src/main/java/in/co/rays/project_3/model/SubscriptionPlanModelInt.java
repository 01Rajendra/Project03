package in.co.rays.project_3.model;

import java.util.List;
import in.co.rays.project_3.dto.SubscriptionPlanDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface SubscriptionPlanModelInt {

	public long add(SubscriptionPlanDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(SubscriptionPlanDTO dto) throws ApplicationException;

	public void update(SubscriptionPlanDTO dto) throws ApplicationException, DuplicateRecordException;

	public SubscriptionPlanDTO findByPK(long pk) throws ApplicationException;

	public SubscriptionPlanDTO findByName(String name) throws ApplicationException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(SubscriptionPlanDTO dto) throws ApplicationException;

	public List search(SubscriptionPlanDTO dto, int pageNo, int pageSize) throws ApplicationException;
}