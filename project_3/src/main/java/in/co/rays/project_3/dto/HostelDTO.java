package in.co.rays.project_3.dto;

/**
 * Hostel JavaBean encapsulates Hostel attributes
 * 
 * @author Rajendra Negi
 *
 */

public class HostelDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String hostelName;
	private int capacity;
	private int numberOfRooms;

	// Single / Double / Triple / Dormitory
	private String occupancy;

	// AC / Non-AC
	private String roomType;

	// Attached / Common
	private String washroomStatus;

	// Vacant / Full
	private String status;

	// Hostel Fees
	private String fees;

	public String getHostelName() {
		return hostelName;
	}

	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public String getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(String occupancy) {
		this.occupancy = occupancy;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getWashroomStatus() {
		return washroomStatus;
	}

	public void setWashroomStatus(String washroomStatus) {
		this.washroomStatus = washroomStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	@Override
	public String getKey() {
		return id + "";
	}

	@Override
	public String getValue() {
		return hostelName;
	}

}
