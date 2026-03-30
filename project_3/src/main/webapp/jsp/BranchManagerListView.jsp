<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.BranchManagerListCtl"%>
<%@page import="in.co.rays.project_3.dto.BranchManagerDTO"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Branch Manager List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list.png');
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

	<form action="<%=ORSView.BRANCH_MANAGER_LIST_CTL%>" method="post">

		<jsp:useBean id="dto"
			class="in.co.rays.project_3.dto.BranchManagerDTO" scope="request"></jsp:useBean>

		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List list = ServletUtility.getList(request);
			Iterator<BranchManagerDTO> it = list.iterator();
		%>

		<center>
			<h1>
				<u>Branch Manager List</u>
			</h1>
		</center>

		<!-- Search Fields -->
		<div class="row text-center">

			<input type="text" name="managerName" placeholder="Manager Name"
				value="<%=ServletUtility.getParameter("managerName", request)%>">

			<input type="text" name="branchName" placeholder="Branch Name"
				value="<%=ServletUtility.getParameter("branchName", request)%>">

			<input type="text" name="contactNumber" placeholder="Contact Number"
				value="<%=ServletUtility.getParameter("contactNumber", request)%>">

			<input type="submit" name="operation"
				value="<%=BranchManagerListCtl.OP_SEARCH%>"> <input
				type="submit" name="operation"
				value="<%=BranchManagerListCtl.OP_RESET%>">

		</div>

		<br>

		<!-- TABLE -->
		<table border="1" width="100%" style="text-align: center;">

			<tr style="background-color: orange;">
				<th>Select</th>
				<th>S.NO</th>
				<th>Manager Name</th>
				<th>Branch Name</th>
				<th>Contact Number</th>
				<th>Edit</th>
			</tr>

			<%
				while (it.hasNext()) {
					dto = it.next();
			%>

			<tr>
				<td><input type="checkbox" name="ids"
					value="<%=dto.getManagerId()%>"></td>
				<td><%=index++%></td>
				<td><%=dto.getManagerName()%></td>
				<td><%=dto.getBranchName()%></td>
				<td><%=dto.getContactNumber()%></td>
				<td><a href="BranchManagerCtl?id=<%=dto.getManagerId()%>">Edit</a>
				</td>
			</tr>

			<%
				}
			%>

		</table>

		<br>

		<!-- BUTTONS -->
		<table width="100%">
			<tr>
				<td><input type="submit" name="operation"
					value="<%=BranchManagerListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" name="operation"
					value="<%=BranchManagerListCtl.OP_NEW%>"></td>

				<td><input type="submit" name="operation"
					value="<%=BranchManagerListCtl.OP_DELETE%>"></td>

				<td align="right"><input type="submit" name="operation"
					value="<%=BranchManagerListCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
			</tr>
		</table>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

</body>
<%@include file="FooterView.jsp"%>
</html>