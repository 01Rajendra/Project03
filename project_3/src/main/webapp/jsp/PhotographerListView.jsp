<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.dto.PhotographerDTO"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.PhotographerListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Photographer List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list.png');
	background-repeat: no-repeat;
	background-size: cover;
	padding-top: 85px;
}

.text {
	text-align: center;
}
</style>
</head>

<%@include file="Header.jsp"%>

<body class="hm">

	<form action="<%=ORSView.PHOTOGRAPHER_LIST_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.PhotographerDTO"
			scope="request"></jsp:useBean>

		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List list = ServletUtility.getList(request);
			Iterator<PhotographerDTO> it = list.iterator();
		%>

		<%
			if (list.size() != 0) {
		%>

		<center>
			<h1>
				<u>Photographer List</u>
			</h1>
		</center>

		<!-- Success Message -->
		<%
			if (!ServletUtility.getSuccessMessage(request).equals("")) {
		%>
		<div class="alert alert-success text-center">
			<%=ServletUtility.getSuccessMessage(request)%>
		</div>
		<%
			}
		%>

		<!-- Error Message -->
		<%
			if (!ServletUtility.getErrorMessage(request).equals("")) {
		%>
		<div class="alert alert-danger text-center">
			<%=ServletUtility.getErrorMessage(request)%>
		</div>
		<%
			}
		%>

		<!-- Search Bar -->
		<div class="row">
			<div class="col-sm-3"></div>

			<div class="col-sm-2">
				<input type="text" name="photographerName" placeholder="Enter Name"
					class="form-control"
					value="<%=ServletUtility.getParameter("photographerName", request)%>">
			</div>

			<div class="col-sm-2">
				<input type="text" name="eventType" placeholder="Event Type"
					class="form-control"
					value="<%=ServletUtility.getParameter("eventType", request)%>">
			</div>

			<div class="col-sm-2">
				<input type="submit" name="operation" class="btn btn-primary"
					value="<%=PhotographerListCtl.OP_SEARCH%>"> <input
					type="submit" name="operation" class="btn btn-dark"
					value="<%=PhotographerListCtl.OP_RESET%>">
			</div>
		</div>

		<br>

		<!-- TABLE -->
		<div class="table-responsive">
			<table class="table table-bordered table-striped">
				<thead>
					<tr style="background-color: #f79d65;">
						<th>Select</th>
						<th>S.NO</th>
						<th>Name</th>
						<th>Event Type</th>
						<th>Charges</th>
						<th>Edit</th>
					</tr>
				</thead>

				<tbody>
					<%
						while (it.hasNext()) {
								dto = it.next();
					%>

					<tr>
						<td align="center"><input type="checkbox" name="ids"
							value="<%=dto.getPhotographerId()%>"></td>

						<td class="text"><%=index++%></td>
						<td class="text"><%=dto.getPhotographerName()%></td>
						<td class="text"><%=dto.getEventType()%></td>
						<td class="text"><%=dto.getCharges()%></td>

						<td class="text"><a
							href="PhotographerCtl?id=<%=dto.getPhotographerId()%>">Edit</a></td>
					</tr>

					<%
						}
					%>
				</tbody>
			</table>
		</div>

		<!-- Pagination Buttons -->
		<table width="100%">
			<tr>
				<td><input type="submit" name="operation"
					class="btn btn-warning"
					value="<%=PhotographerListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" name="operation"
					class="btn btn-primary" value="<%=PhotographerListCtl.OP_NEW%>">
				</td>

				<td><input type="submit" name="operation"
					class="btn btn-danger" value="<%=PhotographerListCtl.OP_DELETE%>">
				</td>

				<td align="right"><input type="submit" name="operation"
					class="btn btn-warning" value="<%=PhotographerListCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
			</tr>
		</table>

		<%
			} else {
		%>

		<center>
			<h2>Photographer List</h2>
		</center>

		<div class="text-center text-danger">
			<%=ServletUtility.getErrorMessage(request)%>
		</div>

		<br>

		<center>
			<input type="submit" name="operation" class="btn btn-primary"
				value="<%=PhotographerListCtl.OP_BACK%>">
		</center>

		<%
			}
		%>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

</body>

<%@include file="FooterView.jsp"%>

</html>
