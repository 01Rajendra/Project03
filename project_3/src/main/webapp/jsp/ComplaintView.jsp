<%@page import="in.co.rays.project_3.controller.ComplaintCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Complaint View</title>

<style type="text/css">
.log1 {
	padding-top: 3%;
}
.input-group-addon{
	box-shadow: 9px 8px 7px #001a33;
}
.p4{
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

<div>
<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ComplaintDTO"
	scope="request"></jsp:useBean>

<form action="<%=ORSView.COMPLAINT_CTL%>" method="post">

<div class="row pt-3">
<div class="col-md-4"></div>

<div class="col-md-4">
<div class="card input-group-addon">
<div class="card-body">

<%
	long id = DataUtility.getLong(request.getParameter("id"));

	if (dto.getComplaintId() != null) {
%>
<h3 class="text-center text-primary">Update Complaint</h3>
<% } else { %>
<h3 class="text-center text-primary">Add Complaint</h3>
<% } %>

<!-- Messages -->

<% if (!ServletUtility.getSuccessMessage(request).equals("")) { %>
<div class="alert alert-success">
<%=ServletUtility.getSuccessMessage(request)%>
</div>
<% } %>

<% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
<div class="alert alert-danger">
<%=ServletUtility.getErrorMessage(request)%>
</div>
<% } %>

<!-- Hidden fields -->

<input type="hidden" name="complaintId" value="<%=dto.getComplaintId()%>">

<!-- Complaint Code -->
<b>Complaint Code</b>
<input type="text" name="complaintCode"
	value="<%=DataUtility.getStringData(dto.getComplaintCode())%>">
<font color="red"><%=ServletUtility.getErrorMessage("complaintCode", request)%></font>

<br><br>

<!-- Customer Name -->
<b>Customer Name</b>
<input type="text" name="customerName"
	value="<%=DataUtility.getStringData(dto.getCustomerName())%>">
<font color="red"><%=ServletUtility.getErrorMessage("customerName", request)%></font>

<br><br>

<!-- Complaint Type -->
<b>Complaint Type</b>
<input type="text" name="complaintType"
	value="<%=DataUtility.getStringData(dto.getComplaintType())%>">
<font color="red"><%=ServletUtility.getErrorMessage("complaintType", request)%></font>

<br><br>

<!-- Complaint Status -->
<b>Complaint Status</b>
<input type="text" name="complaintStatus"
	value="<%=DataUtility.getStringData(dto.getComplaintStatus())%>">
<font color="red"><%=ServletUtility.getErrorMessage("complaintStatus", request)%></font>

<br><br>

<%
	if (id > 0) {
%>
<input type="submit" name="operation" value="<%=ComplaintCtl.OP_UPDATE%>">
<input type="submit" name="operation" value="<%=ComplaintCtl.OP_CANCEL%>">
<% } else { %>
<input type="submit" name="operation" value="<%=ComplaintCtl.OP_SAVE%>">
<input type="submit" name="operation" value="<%=ComplaintCtl.OP_RESET%>">
<% } %>

</div>
</div>
</div>

<div class="col-md-4"></div>
</div>

</form>
</div>

</body>

<%@include file="FooterView.jsp"%>
</html>
