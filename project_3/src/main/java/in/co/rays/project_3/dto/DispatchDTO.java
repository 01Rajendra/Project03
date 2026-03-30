package in.co.rays.project_3.dto;

import java.util.Date;

public class DispatchDTO extends BaseDTO {
	
	private Long dispatchId;
	private Date dispatchDate;
	private String status;
	private String courierName;
	public Long getDispatchId() {
		return dispatchId;
	}
	public void setDispatchId(Long dispatchId) {
		this.dispatchId = dispatchId;
	}
	public Date getDispatchDate() {
		return dispatchDate;
	}
	public void setDispatchDate(Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCourierName() {
		return courierName;
	}
	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
