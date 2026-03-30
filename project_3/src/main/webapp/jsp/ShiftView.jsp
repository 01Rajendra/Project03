<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.ShiftCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shift View</title>
</head>

<body class="p4">
	<div class="header">
		<%@include file="Header.jsp"%>
	</div>

	<div>
		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ShiftDTO"
			scope="request"></jsp:useBean>

		<main>
			<form action="<%=ORSView.SHIFT_CTL%>" method="post">

				<div class="row pt-3 pb-3">
					<div class="col-md-4 mb-4"></div>
					<div class="col-md-4 mb-4">
						<div class="card">
							<div class="card-body">

								<%
									long id = DataUtility.getLong(request.getParameter("id"));
									if (dto != null && id > 0) {
								%>
								<h3 class="text-center text-primary">Update Shift</h3>
								<%
									} else {
								%>
								<h3 class="text-center text-primary">Add Shift</h3>
								<%
									}
								%>

								<!-- Success Message -->
								<h4 align="center">
									<%
										if (!ServletUtility.getSuccessMessage(request).equals("")) {
									%>
									<div class="alert alert-success">
										<%=ServletUtility.getSuccessMessage(request)%>
									</div>
									<%
										}
									%>
								</h4>

								<!-- Error Message -->
								<h4 align="center">
									<%
										if (!ServletUtility.getErrorMessage(request).equals("")) {
									%>
									<div class="alert alert-danger">
										<%=ServletUtility.getErrorMessage(request)%>
									</div>
									<%
										}
									%>
								</h4>

								<!-- Hidden Fields -->
								<input type="hidden" name="id" value="<%=dto.getId()%>">
								<input type="hidden" name="createdBy"
									value="<%=dto.getCreatedBy()%>"> <input type="hidden"
									name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
									type="hidden" name="createdDatetime"
									value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
								<input type="hidden" name="modifiedDatetime"
									value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

								<!-- Shift Name -->
								<span><b>Shift Name</b><span style="color: red;">*</span></span>
								<div class="col-sm-12">
									<input type="text" class="form-control" name="name"
										placeholder="Shift Name"
										value="<%=DataUtility.getStringData(dto.getShiftName())%>">
								</div>
								<font color="red"> <%=ServletUtility.getErrorMessage("name", request)%>
								</font> <br>

								<!-- Start Time -->
								<span><b>Start Time</b><span style="color: red;">*</span></span>
								<div class="col-sm-12">
									<input type="time" class="form-control" name="startTime"
										value="<%=DataUtility.getStringData(dto.getStartTime())%>">
								</div>
								<font color="red"> <%=ServletUtility.getErrorMessage("startTime", request)%>
								</font> <br>

								<!-- End Time -->
								<span><b>End Time</b><span style="color: red;">*</span></span>
								<div class="col-sm-12">
									<input type="time" class="form-control" name="endTime"
										value="<%=DataUtility.getStringData(dto.getEndTime())%>">
								</div>
								<font color="red"> <%=ServletUtility.getErrorMessage("endTime", request)%>
								</font> <br>

								<%
									if (id > 0) {
								%>
								<div class="text-center">
									<input type="submit" class="btn btn-success" name="operation"
										value="<%=ShiftCtl.OP_UPDATE%>"> <input type="submit"
										class="btn btn-warning" name="operation"
										value="<%=ShiftCtl.OP_CANCEL%>">
								</div>
								<%
									} else {
								%>
								<div class="text-center">
									<input type="submit" name="operation" class="btn btn-success"
										value="<%=ShiftCtl.OP_SAVE%>"> <input type="submit"
										name="operation" class="btn btn-warning"
										value="<%=ShiftCtl.OP_RESET%>">
								</div>
								<%
									}
								%>

							</div>
						</div>
					</div>
					<div class="col-md-4 mb-4"></div>
				</div>

			</form>
		</main>
	</div>

</body>

<%@include file="FooterView.jsp"%>
</html>