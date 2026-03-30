<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.dto.UploadHistoryDTO"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.UploadHistoryListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Upload History List</title>
</head>

<%@include file="Header.jsp"%>

<body>

	<form action="<%=ORSView.UPLOADHISTORY_LIST_CTL%>" method="post">

		<jsp:useBean id="dto"
			class="in.co.rays.project_3.dto.UploadHistoryDTO" scope="request"></jsp:useBean>

		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;

			int nextPageSize = 0;
			if (request.getAttribute("nextListSize") != null) {
				nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
			}

			List list = ServletUtility.getList(request);
			if (list == null) {
				list = new java.util.ArrayList();
			}

			Iterator<UploadHistoryDTO> it = list.iterator();
		%>

		<center>
			<h1>Upload History List</h1>
		</center>

		<h3 style="color: green;" align="center">
			<%=ServletUtility.getSuccessMessage(request)%>
		</h3>

		<h3 style="color: red;" align="center">
			<%=ServletUtility.getErrorMessage(request)%>
		</h3>

		<table align="center">

			<tr>
				<td><input type="text" name="uploadCode"
					placeholder="Upload Code"
					value="<%=ServletUtility.getParameter("uploadCode", request)%>">
				</td>

				<td><input type="text" name="fileName" placeholder="File Name"
					value="<%=ServletUtility.getParameter("fileName", request)%>">
				</td>

				<td><input type="submit" name="operation"
					value="<%=UploadHistoryListCtl.OP_SEARCH%>"> <input
					type="submit" name="operation"
					value="<%=UploadHistoryListCtl.OP_RESET%>"></td>
			</tr>

		</table>

		<br>

		<table border="1" width="80%" align="center">

			<tr>
				<th>Select</th>
				<th>S.NO</th>
				<th>Upload Code</th>
				<th>File Name</th>
				<th>Upload Time</th>
				<th>Upload Status</th>
				<th>Edit</th>
			</tr>

			<%
				while (it.hasNext()) {
					dto = it.next();
			%>

			<tr>
				<td align="center"><input type="checkbox" name="ids"
					value="<%=dto.getUploadId() != null ? dto.getUploadId() : 0%>">
				</td>

				<td><%=index++%></td>
				<td><%=DataUtility.getStringData(dto.getUploadCode())%></td>
				<td><%=DataUtility.getStringData(dto.getFileName())%></td>
				<td><%=dto.getUploadTime() != null ? dto.getUploadTime() : ""%></td>
				<td><%=DataUtility.getStringData(dto.getUploadStatus())%></td>

				<td><a
					href="UploadHistoryCtl?id=<%=dto.getUploadId() != null ? dto.getUploadId() : 0%>">
						Edit </a></td>
			</tr>

			<%
				}
			%>

		</table>

		<br>

		<table width="80%" align="center">

			<tr>

				<td><input type="submit" name="operation"
					value="<%=UploadHistoryListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" name="operation"
					value="<%=UploadHistoryListCtl.OP_NEW%>"></td>

				<td><input type="submit" name="operation"
					value="<%=UploadHistoryListCtl.OP_DELETE%>"></td>

				<td align="right"><input type="submit" name="operation"
					value="<%=UploadHistoryListCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>></td>

			</tr>

		</table>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

</body>

<%@include file="FooterView.jsp"%>
</html>