<%@page import="in.co.rays.project_3.controller.HostelCtl"%>
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
<title>Hostel View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
.i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}

.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/addPage.png');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;
}

.grad {
	background-image: linear-gradient(to bottom right, #ffd3ac, #f79d65);
	background-repeat: no-repeat;
	background-size: 100%;
}
</style>
</head>

<body class="p4">

	<div class="header">
		<%@include file="Header.jsp"%>
	</div>

	<main>
	<form action="<%=ORSView.HOSTEL_CTL%>" method="post">

		<div class="row pt-3 pb-4">

			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.HostelDTO"
				scope="request"></jsp:useBean>

			<div class="col-md-4 mb-4"></div>

			<div class="col-md-4 mb-4">
				<div class="card input-group-addon grad">
					<div class="card-body">

						<%
							long id = DataUtility.getLong(request.getParameter("id"));
							if (dto.getId() != null && dto.getId() > 0) {
						%>
						<h3 class="text-center text-primary">Update Hostel</h3>
						<%
							} else {
						%>
						<h3 class="text-center text-primary">Add Hostel</h3>
						<%
							}
						%>

						<!-- Messages -->

						<h4 align="center">
							<%
								if (!ServletUtility.getSuccessMessage(request).equals("")) {
							%>
							<div class="alert alert-success alert-dismissible">
								<button type="button" class="close" data-dismiss="alert">&times;</button>
								<%=ServletUtility.getSuccessMessage(request)%>
							</div>
							<%
								}
							%>
						</h4>

						<h4 align="center">
							<%
								if (!ServletUtility.getErrorMessage(request).equals("")) {
							%>
							<div class="alert alert-danger alert-dismissible">
								<button type="button" class="close" data-dismiss="alert">&times;</button>
								<%=ServletUtility.getErrorMessage(request)%>
							</div>
							<%
								}
							%>
						</h4>

						<!-- Hidden Fields -->

						<input type="hidden" name="id" value="<%=dto.getId()%>">
						<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
						<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
						<input type="hidden" name="createdDatetime"
							value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
						<input type="hidden" name="modifiedDatetime"
							value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

						<!-- Hostel Name -->

						<span class="pl-sm-5"><b>Hostel Name</b><span style="color: red;">*</span></span>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-building grey-text"></i>
									</div>
								</div>
								<input type="text" name="hostelName" class="form-control"
									placeholder="Enter Hostel Name"
									value="<%=DataUtility.getStringData(dto.getHostelName())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5">
							<%=ServletUtility.getErrorMessage("hostelName", request)%></font><br>

						<!-- Capacity -->

						<span class="pl-sm-5"><b>Capacity</b><span style="color: red;">*</span></span>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-users grey-text"></i>
									</div>
								</div>
								<input type="text" name="capacity" class="form-control"
									placeholder="Enter Capacity"
									value="<%=DataUtility.getStringData(dto.getCapacity())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5">
							<%=ServletUtility.getErrorMessage("capacity", request)%></font><br>

						<!-- Number of Rooms -->

						<span class="pl-sm-5"><b>No of Rooms</b><span style="color: red;">*</span></span>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-bed grey-text"></i>
									</div>
								</div>
								<input type="text" name="numberOfRooms" class="form-control"
									placeholder="Enter No of Rooms"
									value="<%=DataUtility.getStringData(dto.getNumberOfRooms())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5">
							<%=ServletUtility.getErrorMessage("numberOfRooms", request)%></font><br>

						<!-- Occupancy -->

						<span class="pl-sm-5"><b>Occupancy</b></span>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-user-friends grey-text"></i>
									</div>
								</div>
								<select name="occupancy" class="form-control">
									<option value="">--Select--</option>
									<option value="Single" <%= "Single".equals(dto.getOccupancy())?"selected":"" %>>Single</option>
									<option value="Double" <%= "Double".equals(dto.getOccupancy())?"selected":"" %>>Double</option>
									<option value="Triple" <%= "Triple".equals(dto.getOccupancy())?"selected":"" %>>Triple</option>
									<option value="Dormitory" <%= "Dormitory".equals(dto.getOccupancy())?"selected":"" %>>Dormitory</option>
								</select>
							</div>
						</div><br>

						<!-- Room Type -->

						<span class="pl-sm-5"><b>Room Type</b></span>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-snowflake grey-text"></i>
									</div>
								</div>
								<select name="roomType" class="form-control">
									<option value="">--Select--</option>
									<option value="AC" <%= "AC".equals(dto.getRoomType())?"selected":"" %>>AC</option>
									<option value="Non-AC" <%= "Non-AC".equals(dto.getRoomType())?"selected":"" %>>Non-AC</option>
								</select>
							</div>
						</div><br>

						<!-- Washroom -->

						<span class="pl-sm-5"><b>Washroom</b></span>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-bath grey-text"></i>
									</div>
								</div>
								<select name="washroomStatus" class="form-control">
									<option value="">--Select--</option>
									<option value="Attached" <%= "Attached".equals(dto.getWashroomStatus())?"selected":"" %>>Attached</option>
									<option value="Common" <%= "Common".equals(dto.getWashroomStatus())?"selected":"" %>>Common</option>
								</select>
							</div>
						</div><br>

						<!-- Status -->

						<span class="pl-sm-5"><b>Status</b></span>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-info-circle grey-text"></i>
									</div>
								</div>
								<select name="status" class="form-control">
									<option value="">--Select--</option>
									<option value="Vacant" <%= "Vacant".equals(dto.getStatus())?"selected":"" %>>Vacant</option>
									<option value="Full" <%= "Full".equals(dto.getStatus())?"selected":"" %>>Full</option>
								</select>
							</div>
						</div><br>

						<!-- Fees -->

						<span class="pl-sm-5"><b>Fees</b></span>
						<div class="col-sm-12">
							<div class="input-group">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<i class="fa fa-rupee-sign grey-text"></i>
									</div>
								</div>
								<input type="text" name="fees" class="form-control"
									placeholder="Enter Fees"
									value="<%=DataUtility.getStringData(dto.getFees())%>">
							</div>
						</div>
						<font color="red" class="pl-sm-5">
							<%=ServletUtility.getErrorMessage("fees", request)%></font><br><br>

						<!-- Buttons -->

						<%
							if (id > 0) {
						%>
						<div class="text-center">
							<input type="submit" name="operation" class="btn btn-success btn-md"
								value="<%=HostelCtl.OP_UPDATE%>">
							<input type="submit" name="operation" class="btn btn-warning btn-md"
								value="<%=HostelCtl.OP_CANCEL%>">
						</div>
						<%
							} else {
						%>
						<div class="text-center">
							<input type="submit" name="operation" class="btn btn-success btn-md"
								value="<%=HostelCtl.OP_SAVE%>">
							<input type="submit" name="operation" class="btn btn-warning btn-md"
								value="<%=HostelCtl.OP_RESET%>">
						</div>
						<%
							}
						%>

					</div>
				</div>
			</div>

			<div class="col-md-4 mb-4"></div>
		</div>

	</form>
	</main>

</body>

<%@include file="FooterView.jsp"%>
</html>
