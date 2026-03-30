package in.co.rays.project_3.dto;

public class PhotographerDTO extends BaseDTO {

	private Long photographerId;
	private String photographerName;
	private String eventType;
	private double charges;

	/**
	 * @return the photographerId
	 */
	public Long getPhotographerId() {
		return photographerId;
	}

	/**
	 * @param photographerId the photographerId to set
	 */
	public void setPhotographerId(Long photographerId) {
		this.photographerId = photographerId;
	}

	/**
	 * @return the photographerName
	 */
	public String getPhotographerName() {
		return photographerName;
	}

	/**
	 * @param photographerName the photographerName to set
	 */
	public void setPhotographerName(String photographerName) {
		this.photographerName = photographerName;
	}

	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the charges
	 */
	public double getCharges() {
		return charges;
	}

	public void setCharges(double charges) {
		this.charges = charges;
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
