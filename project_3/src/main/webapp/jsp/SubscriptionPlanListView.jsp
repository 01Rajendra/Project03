<%@page import="in.co.rays.project_3.controller.SubscriptionPlanListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Subscription Plan List</title>

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

	<form action="<%=ORSView.SUBSCRIPTION_PLAN_LIST_CTL%>" method="post">

		<jsp:useBean id="dto"
			class="in.co.rays.project_3.dto.SubscriptionPlanDTO" scope="request"></jsp:useBean>

		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List list = ServletUtility.getList(request);
			Iterator it = list.iterator();
		%>

		<center>
			<h1>
				<u>Subscription Plan List</u>
			</h1>
		</center>

		<div class="row">
			<div class="col-md-4"></div>

			<%
				if (!ServletUtility.getSuccessMessage(request).equals("")) {
			%>
			<div class="col-md-4 alert alert-success">
				<%=ServletUtility.getSuccessMessage(request)%>
			</div>
			<%
				}
			%>

			<div class="col-md-4"></div>
		</div>

		<div class="row">
			<div class="col-md-4"></div>

			<%
				if (!ServletUtility.getErrorMessage(request).equals("")) {
			%>
			<div class="col-md-4 alert alert-danger">
				<%=ServletUtility.getErrorMessage(request)%>
			</div>
			<%
				}
			%>

			<div class="col-md-4"></div>
		</div>


		<div class="row">

			<div class="col-sm-3">
				<input type="text" name="planName" placeholder="Enter Plan Name"
					class="form-control"
					value="<%=ServletUtility.getParameter("planName", request)%>">
			</div>

			<div class="col-sm-2">
				<input type="submit" name="operation" class="btn btn-primary"
					value="<%=SubscriptionPlanListCtl.OP_SEARCH%>">
			</div>

			<div class="col-sm-2">
				<input type="submit" name="operation" class="btn btn-dark"
					value="<%=SubscriptionPlanListCtl.OP_RESET%>">
			</div>

		</div>

		<br>


		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th><input type="checkbox" id="select_all"> Select</th>
					<th>S.No</th>
					<th>Plan Name</th>
					<th>Price</th>
					<th>Validity Days</th>
					<th>Edit</th>
				</tr>
			</thead>

			<tbody>
				<%
					while (it.hasNext()) {
						dto = (in.co.rays.project_3.dto.SubscriptionPlanDTO) it.next();
				%>
				<tr>
					<td align="center"><input type="checkbox" name="ids"
						value="<%=dto.getPlanId()%>"></td>

					<td><%=index++%></td>
					<td><%=dto.getPlanName()%></td>
					<td><%=dto.getPrice()%></td>
					<td><%=dto.getValidityDays()%></td>

					<td><a href="SubscriptionPlanCtl?planId=<%=dto.getPlanId()%>">Edit</a>
					</td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>


		<table width="100%">
			<tr>
				<td><input type="submit" name="operation"
					value="<%=SubscriptionPlanListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>></td>

				<td><input type="submit" name="operation"
					value="<%=SubscriptionPlanListCtl.OP_NEW%>"></td>

				<td><input type="submit" name="operation"
					value="<%=SubscriptionPlanListCtl.OP_DELETE%>"></td>

				<td align="right"><input type="submit" name="operation"
					value="<%=SubscriptionPlanListCtl.OP_NEXT%>"
					<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
			</tr>
		</table>

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

	</form>

</body>

<%@include file="FooterView.jsp"%>
</html>