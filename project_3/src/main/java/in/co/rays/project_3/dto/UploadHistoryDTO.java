package in.co.rays.project_3.dto;

import java.time.LocalDateTime;

public class UploadHistoryDTO extends BaseDTO {

	private Long uploadId;

	private String uploadCode;

	private String fileName;

	private LocalDateTime uploadTime;

	private String uploadStatus;

	public Long getUploadId() {
		return uploadId;
	}

	public void setUploadId(Long uploadId) {
		this.uploadId = uploadId;
	}

	public String getUploadCode() {
		return uploadCode;
	}

	public void setUploadCode(String uploadCode) {
		this.uploadCode = uploadCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public LocalDateTime getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(LocalDateTime uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
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
