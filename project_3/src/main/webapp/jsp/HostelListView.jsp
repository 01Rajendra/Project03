<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.controller.HostelListCtl"%>
<%@page import="in.co.rays.project_3.dto.HostelDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Hostel List View</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.p1 { padding: 8px; }

.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list.png');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 85px;
}
</style>
</head>

<body class="p4">

<div>
	<%@include file="Header.jsp"%>
</div>

<div>
<form action="<%=ORSView.HOSTEL_LIST_CTL%>" method="post">

	<jsp:useBean id="dto" class="in.co.rays.project_3.dto.HostelDTO"
		scope="request"></jsp:useBean>

	<%
		List list1 = (List) request.getAttribute("hostelList");
		int pageNo = ServletUtility.getPageNo(request);
		int pageSize = ServletUtility.getPageSize(request);
		int index = ((pageNo - 1) * pageSize) + 1;
		int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
		List list = ServletUtility.getList(request);
		Iterator<HostelDTO> it = list.iterator();
	%>

	<% if (list.size() != 0) { %>

	<center>
		<h1 class="text-light font-weight-bold pt-3">
			<font color="black"><u>Hostel List</u></font>
		</h1>
	</center>

	<!-- Success Message -->
	<div class="row">
		<div class="col-md-4"></div>
		<% if (!ServletUtility.getSuccessMessage(request).equals("")) { %>
		<div class="col-md-4 alert alert-success alert-dismissible"
			style="background-color: #80ff80">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<h4>
				<font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h4>
		</div>
		<% } %>
		<div class="col-md-4"></div>
	</div>

	<!-- Error Message -->
	<div class="row">
		<div class="col-md-4"></div>
		<% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
		<div class="col-md-4 alert alert-danger alert-dismissible">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<h4><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h4>
		</div>
		<% } %>
		<div class="col-md-4"></div>
	</div>
	<br>

	<!-- Search Panel -->
	<div class="row">
		<div class="col-sm-1"></div>

		<div class="col-sm-3">
			<input type="text" class="form-control" name="hostelName"
				placeholder="Enter Hostel Name"
				value="<%=ServletUtility.getParameter("hostelName", request)%>">
		</div>

		<div class="col-sm-2">
			<select name="status" class="form-control">
				<option value="">--Status--</option>
				<option value="Vacant">Vacant</option>
				<option value="Full">Full</option>
			</select>
		</div>

		<div class="col-sm-2">
			<select name="roomType" class="form-control">
				<option value="">--Room Type--</option>
				<option value="AC">AC</option>
				<option value="Non-AC">Non-AC</option>
			</select>
		</div>

		<div class="col-sm-3">
			<input type="submit" class="btn btn-primary btn-md" name="operation"
				value="<%=HostelListCtl.OP_SEARCH%>">
			<input type="submit" class="btn btn-dark btn-md" name="operation"
				value="<%=HostelListCtl.OP_RESET%>">
		</div>

		<div class="col-sm-1"></div>
	</div>

	<br>

	<!-- Table -->
	<div class="table-responsive">
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr style="background-color: #f79d65; font-size: 18px;">
				<th width="10%"><input type="checkbox" id="select_all"> Select All</th>
				<th>S.NO</th>
				<th>Hostel Name</th>
				<th>Capacity</th>
				<th>Rooms</th>
				<th>Occupancy</th>
				<th>Room Type</th>
				<th>Status</th>
				<th>Fees</th>
				<th>Edit</th>
			</tr>
		</thead>

		<tbody>
		<% while (it.hasNext()) { dto = it.next(); %>
			<tr style="font-weight: bold;">
				<td align="center"><input type="checkbox" class="checkbox" name="ids" value="<%=dto.getId()%>"></td>
				<td align="center"><%=index++%></td>
				<td align="center"><%=dto.getHostelName()%></td>
				<td align="center"><%=dto.getCapacity()%></td>
				<td align="center"><%=dto.getNumberOfRooms()%></td>
				<td align="center"><%=dto.getOccupancy()%></td>
				<td align="center"><%=dto.getRoomType()%></td>
				<td align="center"><%=dto.getStatus()%></td>
				<td align="center"><%=dto.getFees()%></td>
				<td align="center"><a href="HostelCtl?id=<%=dto.getId()%>">Edit</a></td>
			</tr>
		<% } %>
		</tbody>
	</table>
	</div>

	<!-- Buttons -->
	<table width="100%">
	<tr>
		<td>
			<input type="submit" name="operation" class="btn btn-secondary btn-md"
				value="<%=HostelListCtl.OP_PREVIOUS%>" <%=pageNo > 1 ? "" : "disabled"%>>
		</td>

		<td>
			<input type="submit" name="operation" class="btn btn-primary btn-md"
				value="<%=HostelListCtl.OP_NEW%>">
		</td>

		<td>
			<input type="submit" name="operation" class="btn btn-danger btn-md"
				value="<%=HostelListCtl.OP_DELETE%>">
		</td>

		<td align="right">
			<input type="submit" name="operation" class="btn btn-secondary btn-md"
				value="<%=HostelListCtl.OP_NEXT%>" <%= (nextPageSize != 0) ? "" : "disabled" %>>
		</td>
	</tr>
	</table>

	<% } else { %>

	<!-- No Record Found -->
	<center>
		<h1 class="text-primary font-weight-bold pt-3">Hostel List</h1>
	</center>
	<br>

	<div class="row">
		<div class="col-md-4"></div>
		<% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
		<div class="col-md-4 alert alert-danger alert-dismissible">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<h4><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h4>
		</div>
		<% } %>
		<div class="col-md-4"></div>
	</div>

	<br>
	<div style="padding-left: 48%;">
		<input type="submit" name="operation" class="btn btn-primary btn-md"
			value="<%=HostelListCtl.OP_BACK%>">
	</div>

	<% } %>

	<input type="hidden" name="pageNo" value="<%=pageNo%>">
	<input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>
</div>

</body>
<%@include file="FooterView.jsp"%>
</html>
