<%@page import="in.co.rays.project_3.dto.ComplaintDTO"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.ComplaintListCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Complaint List View</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
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

<body class="p4">

	<%@include file="Header.jsp"%>

	<form action="<%=ORSView.COMPLAINT_LIST_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ComplaintDTO"
			scope="request"></jsp:useBean>

		<%
			List list1 = (List) request.getAttribute("complaintList");
			if (list1 == null) {
				list1 = new java.util.ArrayList();
			}

			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;

			int nextPageSize = 0;
			Object sizeObj = request.getAttribute("nextListSize");
			if (sizeObj != null) {
				nextPageSize = DataUtility.getInt(sizeObj.toString());
			}

			List list = ServletUtility.getList(request);
			if (list == null) {
				list = new java.util.ArrayList();
			}

			Iterator<ComplaintDTO> it = list.iterator();
		%>

		<center>
			<h1>Complaint List</h1>
		</center>

		<!-- Dropdown/Search -->
		<%=HTMLUtility.getList("complaintId", String.valueOf(dto.getComplaintId()), list1)%>

		<input type="submit" name="operation"
			value="<%=ComplaintListCtl.OP_SEARCH%>"> <input type="submit"
			name="operation" value="<%=ComplaintListCtl.OP_RESET%>"> <br>
		<br>

		<%
			if (list.size() != 0) {
		%>

		<table border="1" width="100%">
			<tr>
				<th>Select</th>
				<th>S.NO</th>
				<th>Complaint Code</th>
				<th>Customer Name</th>
				<th>Complaint Type</th>
				<th>Complaint Status</th>
				<th>Edit</th>
			</tr>

			<%
				while (it.hasNext()) {
						dto = it.next();
			%>

			<tr>
				<td><input type="checkbox" name="ids"
					value="<%=dto.getComplaintId()%>"></td>
				<td><%=index++%></td>
				<td><%=dto.getComplaintCode()%></td>
				<td><%=dto.getCustomerName()%></td>
				<td><%=dto.getComplaintType()%></td>
				<td><%=dto.getComplaintStatus()%></td>
				<td><a href="ComplaintCtl?id=<%=dto.getComplaintId()%>">Edit</a>
				</td>
			</tr>

			<%
				}
			%>

		</table>

		<br> <input type="submit" name="operation"
			value="<%=ComplaintListCtl.OP_PREVIOUS%>"
			<%=pageNo > 1 ? "" : "disabled"%>> <input type="submit"
			name="operation" value="<%=ComplaintListCtl.OP_NEW%>"> <input
			type="submit" name="operation"
			value="<%=ComplaintListCtl.OP_DELETE%>"> <input type="submit"
			name="operation" value="<%=ComplaintListCtl.OP_NEXT%>"
			<%=nextPageSize != 0 ? "" : "disabled"%>>

		<%
			} else {
		%>

		<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
		</font> <br>
		<br> <input type="submit" name="operation"
			value="<%=ComplaintListCtl.OP_BACK%>">

		<%
			}
		%>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>