<%@page import="in.co.rays.project_3.controller.ResultCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Result View</title>

<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

</head>

<body
	style="background-image:url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg'); background-size:cover;">

	<%@include file="Header.jsp"%>

	<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ResultDTO"
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
							Update Result
							<%
								} else {
							%>
							Add Result
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

						<form action="<%=ORSView.RESULT_CTL%>" method="post">

							<!-- Hidden Fields -->
							<input type="hidden" name="id" value="<%=dto.getId()%>">
							<input type="hidden" name="createdBy"
								value="<%=dto.getCreatedBy()%>"> <input type="hidden"
								name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
								type="hidden" name="createdDatetime"
								value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

							<!-- Student Name -->
							<div class="form-group">
								<label><b>Student Name *</b></label> <input type="text"
									name="studentName" class="form-control"
									value="<%=DataUtility.getStringData(dto.getStudentName())%>">
								<small class="text-danger"> <%=ServletUtility.getErrorMessage("studentName", request)%>
								</small>
							</div>

							<!-- Result Code -->
							<div class="form-group">
								<label><b>Result Code *</b></label> <input type="text"
									name="resultCode" class="form-control"
									value="<%=DataUtility.getStringData(dto.getResultCode())%>">
								<small class="text-danger"> <%=ServletUtility.getErrorMessage("resultCode", request)%>
								</small>
							</div>

							<!-- Marks -->
							<div class="form-group">
								<label><b>Marks *</b></label> <input type="text" name="marks"
									class="form-control"
									value="<%=DataUtility.getStringData(dto.getMarks())%>">
								<small class="text-danger"> <%=ServletUtility.getErrorMessage("marks", request)%>
								</small>
							</div>

							<!-- Result Status -->
							<div class="form-group">
								<label><b>Result Status *</b></label> <input type="text"
									name="resultStatus" class="form-control"
									value="<%=DataUtility.getStringData(dto.getResultStatus())%>">
								<small class="text-danger"> <%=ServletUtility.getErrorMessage("resultStatus", request)%>
								</small>
							</div>

							<!-- Buttons -->
							<div class="text-center">

								<%
									if (dto.getId() != null && dto.getId() > 0) {
								%>

								<input type="submit" name="operation" class="btn btn-success"
									value="<%=ResultCtl.OP_UPDATE%>"> <input type="submit"
									name="operation" class="btn btn-warning"
									value="<%=ResultCtl.OP_CANCEL%>">

								<%
									} else {
								%>

								<input type="submit" name="operation" class="btn btn-success"
									value="<%=ResultCtl.OP_SAVE%>"> <input type="submit"
									name="operation" class="btn btn-secondary"
									value="<%=ResultCtl.OP_RESET%>">

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