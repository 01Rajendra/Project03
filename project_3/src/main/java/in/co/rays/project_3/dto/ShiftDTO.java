package in.co.rays.project_3.dto;

import java.time.LocalTime;

public class ShiftDTO extends BaseDTO {

	private Long ShiftId;
	private String ShiftCode;
	private String ShiftName;
	private LocalTime StartTime;
	private LocalTime EndTime;

	public Long getShiftId() {
		return ShiftId;
	}

	public void setShiftId(Long shiftId) {
		ShiftId = shiftId;
	}

	public String getShiftCode() {
		return ShiftCode;
	}

	public void setShiftCode(String shiftCode) {
		ShiftCode = shiftCode;
	}

	public String getShiftName() {
		return ShiftName;
	}

	public void setShiftName(String shiftName) {
		ShiftName = shiftName;
	}

	public LocalTime getStartTime() {
		return StartTime;
	}

	public void setStartTime(LocalTime startTime) {
		StartTime = startTime;
	}

	public LocalTime getEndTime() {
		return EndTime;
	}

	public void setEndTime(LocalTime endTime) {
		EndTime = endTime;
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
