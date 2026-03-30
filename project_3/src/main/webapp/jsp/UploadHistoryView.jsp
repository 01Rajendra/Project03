<%@page import="in.co.rays.project_3.controller.UploadHistoryCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Upload History View</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

</head>

<body
	style="background-image:url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg'); background-size:cover;">

	<%@include file="Header.jsp"%>

	<jsp:useBean id="dto" class="in.co.rays.project_3.dto.UploadHistoryDTO"
		scope="request" />

	<div class="container mt-5">

		<div class="row justify-content-center">

			<div class="col-md-6">

				<div class="card shadow-lg bg-white">

					<div class="card-body">

						<!-- Title -->
						<h3 class="text-center text-dark font-weight-bold mb-3">
							<%
								if (dto.getId() != null && dto.getId() > 0) {
							%>
							Update Upload History
							<%
								} else {
							%>
							Add Upload History
							<%
								}
							%>
						</h3>

						<!-- Messages -->
						<div class="text-center text-success">
							<%=ServletUtility.getSuccessMessage(request)%>
						</div>

						<div class="text-center text-danger">
							<%=ServletUtility.getErrorMessage(request)%>
						</div>

						<form action="<%=ORSView.UPLOADHISTORY_CTL%>" method="post">

							<!-- Hidden Fields -->
							<input type="hidden" name="id" value="<%=dto.getId()%>">
							<input type="hidden" name="createdBy"
								value="<%=dto.getCreatedBy()%>"> <input type="hidden"
								name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
								type="hidden" name="createdDatetime"
								value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

							<!-- File Name -->
							<div class="form-group">
								<label><b>File Name *</b></label> <input type="text"
									name="fileName" class="form-control"
									value="<%=DataUtility.getStringData(dto.getFileName())%>">
								<small class="text-danger"> <%=ServletUtility.getErrorMessage("fileName", request)%>
								</small>
							</div>

							<!-- Upload Code -->
							<div class="form-group">
								<label><b>Upload Code *</b></label> <input type="text"
									name="uploadCode" class="form-control"
									value="<%=DataUtility.getStringData(dto.getUploadCode())%>">
								<small class="text-danger"> <%=ServletUtility.getErrorMessage("uploadCode", request)%>
								</small>
							</div>

							<!-- Upload Time -->
							<div class="form-group">
								<label><b>Upload Time *</b></label> <input type="text"
									name="uploadTime" class="form-control"
									value="<%=dto.getUploadTime() != null ? dto.getUploadTime() : ""%>">
								<small class="text-danger"> <%=ServletUtility.getErrorMessage("uploadTime", request)%>
								</small>
							</div>

							<!-- Upload Status -->
							<div class="form-group">
								<label><b>Upload Status *</b></label> <input type="text"
									name="uploadStatus" class="form-control"
									value="<%=DataUtility.getStringData(dto.getUploadStatus())%>">
								<small class="text-danger"> <%=ServletUtility.getErrorMessage("uploadStatus", request)%>
								</small>
							</div>

							<!-- Buttons -->
							<div class="text-center">

								<%
									if (dto.getId() != null && dto.getId() > 0) {
								%>

								<input type="submit" name="operation" class="btn btn-success"
									value="<%=UploadHistoryCtl.OP_UPDATE%>"> <input
									type="submit" name="operation" class="btn btn-warning"
									value="<%=UploadHistoryCtl.OP_CANCEL%>">

								<%
									} else {
								%>

								<input type="submit" name="operation" class="btn btn-success"
									value="<%=UploadHistoryCtl.OP_SAVE%>"> <input
									type="submit" name="operation" class="btn btn-secondary"
									value="<%=UploadHistoryCtl.OP_RESET%>">

								<%
									}
								%>

							</div>

						</form>

					</div>
				</div>

			</div>

		</div>

	</div>

	<%@include file="FooterView.jsp"%>

	<!-- Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>