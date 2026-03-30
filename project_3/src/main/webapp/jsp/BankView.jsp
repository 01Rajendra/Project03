<%@page import="in.co.rays.project_3.controller.BankCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Bank View</title>
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

	<jsp:useBean id="dto" class="in.co.rays.project_3.dto.BankDTO"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.BANK_CTL%>" method="post">

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
							Bank</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary font-weight-bold">Add
							Bank</h3>
						<%
							}
						%>

						<input type="hidden" name="id" value="<%=dto.getId()%>">

						<!-- Bank Name -->
						<label><b>Bank Name</b> *</label> <input type="text"
							name="bank_Name" class="form-control"
							value="<%=DataUtility.getStringData(dto.getBank_Name())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("bank_Name", request)%></font>

						<br>

						<!-- Account No -->
						<label><b>Account No</b> *</label> <input type="text"
							name="account_NO" class="form-control"
							value="<%=DataUtility.getStringData(dto.getAccount_NO())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("account_NO", request)%></font>

						<br>

						<!-- Customer Name -->
						<label><b>Customer Name</b> *</label> <input type="text"
							name="customer_Name" class="form-control"
							value="<%=DataUtility.getStringData(dto.getCustomer_Name())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("customer_Name", request)%></font>

						<br>

						<!-- DOB -->
						<label><b>Date Of Birth</b> *</label> <input type="date"
							name="dob" class="form-control"
							value="<%=DataUtility.getDateString(dto.getDob())%>"> <font
							color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font>

						<br>

						<!-- Address -->
						<label><b>Address</b> *</label>
						<textarea name="address" class="form-control"><%=DataUtility.getStringData(dto.getAddress())%></textarea>
						<font color="red"><%=ServletUtility.getErrorMessage("address", request)%></font>

						<br> <br>

						<%
							if (id > 0) {
						%>
						<input type="submit" name="operation" class="btn btn-success"
							value="<%=BankCtl.OP_UPDATE%>"> <input type="submit"
							name="operation" class="btn btn-warning"
							value="<%=BankCtl.OP_CANCEL%>">
						<%
							} else {
						%>
						<input type="submit" name="operation" class="btn btn-success"
							value="<%=BankCtl.OP_SAVE%>"> <input type="submit"
							name="operation" class="btn btn-warning"
							value="<%=BankCtl.OP_RESET%>">
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