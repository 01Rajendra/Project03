<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.project_3.controller.BankListCtl"%>
<%@page import="in.co.rays.project_3.dto.BankDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bank List View</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.p1 {
	padding-top: 200px;
	font-size: 20px;
	color: #b62f2f;
}

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

.table-hover tbody tr:hover td {
	background-color: #0064ff36;
}
</style>
</head>

<body class="p4">

	<div>
		<%@include file="Header.jsp"%>
	</div>

	<div>

		<form action="<%=ORSView.BANK_LIST_CTL%>" method="post">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.BankDTO"
				scope="request"></jsp:useBean>

			<%
				List list1 = (List) request.getAttribute("bankList");
			%>

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				List list = ServletUtility.getList(request);
				Iterator<BankDTO> it = list.iterator();

				if (list.size() != 0) {
			%>

			<center>
				<h1 class="text-primary font-weight-bold pt-3">
					<font color="black">Bank List</font>
				</h1>
			</center>
			<br>

			<div style="margin-bottom: 20px;" class="table-responsive">
				<table class="table table-bordered table-dark table-hover">

					<thead>
						<tr style="background-color: #8C8C8C;">
							<th width="10%"><input type="checkbox" id="select_all"
								name="Select" class="text"> Select All</th>
							<th class="text">S.NO</th>
							<th class="text">Bank Name</th>
							<th class="text">Account No</th>
							<th class="text">Customer Name</th>
							<th class="text">DOB</th>
							<th class="text">Address</th>
							<th class="text">Edit</th>
						</tr>
					</thead>

					<%
						while (it.hasNext()) {
								dto = it.next();
					%>

					<tbody>
						<tr>
							<td align="center"><input type="checkbox" class="checkbox"
								name="ids" value="<%=dto.getId()%>"></td>

							<td align="center"><%=index++%></td>
							<td align="center"><%=dto.getBank_Name()%></td>
							<td align="center"><%=dto.getAccount_NO()%></td>
							<td align="center"><%=dto.getCustomer_Name()%></td>
							<td align="center"><%=new SimpleDateFormat("dd-MM-yyyy").format(dto.getDob())%>
							</td>
							<td align="center"><%=dto.getAddress()%></td>

							<td align="center"><a href="BankCtl?id=<%=dto.getId()%>">Edit</a>
							</td>

						</tr>
					</tbody>

					<%
						}
					%>

				</table>
			</div>

			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						class="btn btn-secondary btn-md"
						value="<%=BankListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td><input type="submit" name="operation"
						class="btn btn-primary btn-md" value="<%=BankListCtl.OP_NEW%>">
					</td>

					<td><input type="submit" name="operation"
						class="btn btn-danger btn-md" value="<%=BankListCtl.OP_DELETE%>">
					</td>

					<td align="right"><input type="submit" name="operation"
						class="btn btn-secondary btn-md" value="<%=BankListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
				}
				if (list.size() == 0) {
			%>

			<center>
				<h1 style="font-size: 40px; padding-top: 24px; color: #162390;">
					Bank List</h1>
			</center>

			<br>

			<div style="padding-left: 48%;">
				<input type="submit" name="operation" class="btn btn-primary btn-md"
					value="<%=BankListCtl.OP_BACK%>">
			</div>

			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

		</form>
	</div>

	<br>
	<br>

	<%@include file="FooterView.jsp"%>

</body>
</html>