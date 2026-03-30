```jsp
<%@page import="in.co.rays.project_3.controller.DispatchCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Dispatch View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}
</style>
</head>

<body class="p4">

	<div class="header">
		<%@include file="Header.jsp"%>
	</div>

	<jsp:useBean id="dto" class="in.co.rays.project_3.dto.DispatchDTO"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.DISPATCH_CTL%>" method="post">

		<div class="row pt-3">
			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card">
					<div class="card-body">

						<%
							long id = DataUtility.getLong(request.getParameter("id"));
							if (dto.getId() != null) {
						%>
						<h3 class="text-center text-primary font-weight-bold">Update
							Dispatch</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary font-weight-bold">Add
							Dispatch</h3>
						<%
							}
						%>

						<input type="hidden" name="id" value="<%=dto.getId()%>">

						<!-- Dispatch Id -->
						<label><b>Dispatch Id</b> *</label>
						<input type="text" name="dispatchId" class="form-control"
							value="<%=DataUtility.getStringData(dto.getDispatchId())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("dispatchId", request)%></font>

						<br>

						<!-- Courier Name -->
						<label><b>Courier Name</b> *</label>
						<input type="text" name="courierName" class="form-control"
							value="<%=DataUtility.getStringData(dto.getCourierName())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("courierName", request)%></font>

						<br>

						<!-- Dispatch Date -->
						<label><b>Dispatch Date</b> *</label>
						<input type="date" name="dispatchDate" class="form-control"
							value="<%=DataUtility.getDateString(dto.getDispatchDate())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("dispatchDate", request)%></font>

						<br>

						<!-- Status -->
						<label><b>Status</b> *</label>
						<textarea name="status" class="form-control"><%=DataUtility.getStringData(dto.getStatus())%></textarea>
						<font color="red"><%=ServletUtility.getErrorMessage("status", request)%></font>

						<br><br>

						<%
							if (id > 0) {
						%>
						<input type="submit" name="operation" class="btn btn-success"
							value="<%=DispatchCtl.OP_UPDATE%>">
						<input type="submit" name="operation" class="btn btn-warning"
							value="<%=DispatchCtl.OP_CANCEL%>">
						<%
							} else {
						%>
						<input type="submit" name="operation" class="btn btn-success"
							value="<%=DispatchCtl.OP_SAVE%>">
						<input type="submit" name="operation" class="btn btn-warning"
							value="<%=DispatchCtl.OP_RESET%>">
						<%
							}
						%>

					</div>
				</div>
			</div>

			<div class="col-md-4"></div>
		</div>

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>
```
