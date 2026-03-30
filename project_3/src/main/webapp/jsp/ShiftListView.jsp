<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.ShiftListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Shift List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list.png');
	background-repeat: no-repeat;
	background-attachment: fixed;
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

	<form class="pb-5" action="<%=ORSView.SHIFT_LIST_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ShiftDTO"
			scope="request"></jsp:useBean>

		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List list = ServletUtility.getList(request);
			Iterator<in.co.rays.project_3.dto.ShiftDTO> it = list.iterator();
		%>

		<center>
			<h1 class="text-dark font-weight-bold pt-3">
				<u>Shift List</u>
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

		<!-- Search Section -->
		<div class="row mb-3">
			<div class="col-sm-3"></div>

			<div class="col-sm-2">
				<input type="text" name="shiftCode" placeholder="Shift Code"
					class="form-control"
					value="<%=ServletUtility.getParameter("shiftCode", request)%>">
			</div>

			<div class="col-sm-2">
				<input type="text" name="shiftName" placeholder="Shift Name"
					class="form-control"
					value="<%=ServletUtility.getParameter("shiftName", request)%>">
			</div>

			<div class="col-sm-3">
				<input type="submit" class="btn btn-primary" name="operation"
					value="<%=ShiftListCtl.OP_SEARCH%>"> <input type="submit"
					class="btn btn-dark" name="operation"
					value="<%=ShiftListCtl.OP_RESET%>">
			</div>

		</div>

		<!-- Table -->
		<div class="table-responsive">
			<table class="table table-bordered table-striped table-hover">
				<thead>
					<tr style="background-color: #81d4fa;">
						<th><input type="checkbox" id="select_all"> Select
							All</th>
						<th class="text">S.No</th>
						<th class="text">Shift Code</th>
						<th class="text">Shift Name</th>
						<th class="text">Start Time</th>
						<th class="text">End Time</th>
						<th class="text">Edit</th>
					</tr>
				</thead>

				<tbody>
					<%
						while (it.hasNext()) {
							dto = it.next();
					%>
					<tr>
						<td align="center"><input type="checkbox" class="checkbox"
							name="ids" value="<%=dto.getId()%>"></td>
						<td class="text"><%=index++%></td>
						<td class="text"><%=dto.getShiftCode()%></td>
						<td class="text"><%=dto.getShiftName()%></td>
						<td class="text"><%=dto.getStartTime()%></td>
						<td class="text"><%=dto.getEndTime()%></td>
						<td class="text"><a href="ShiftCtl?id=<%=dto.getId()%>">Edit</a>
						</td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
		</div>

		<!-- Buttons -->
		<table width="100%">
			<tr>
				<td><input type="submit" name="operation"
					class="btn btn-warning" value="<%=ShiftListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" name="operation"
					class="btn btn-primary" value="<%=ShiftListCtl.OP_NEW%>"></td>

				<td><input type="submit" name="operation"
					class="btn btn-danger" value="<%=ShiftListCtl.OP_DELETE%>">
				</td>

				<td align="right"><input type="submit" name="operation"
					class="btn btn-warning" value="<%=ShiftListCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
			</tr>
		</table>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>