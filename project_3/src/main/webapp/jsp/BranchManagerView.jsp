<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.BranchManagerCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Branch Manager view</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.input-group-addon {
	box-shadow: 9px 8px 7px #001a33;
}

.hm {
	background-image:
		url('<%=ORSView.APP_CONTEXT%>/img/userRegistration.png');
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
<body class="hm">
	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>
	<div>

		<main>
		<form action="<%=ORSView.BRANCH_MANAGER_CTL%>" method="post">
			<jsp:useBean id="dto"
				class="in.co.rays.project_3.dto.BranchManagerDTO" scope="request"></jsp:useBean>

			<div class="row pt-3">
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card input-group-addon grad">
						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("id"));

								if (dto.getManagerName() != null && dto.getManagerId() > 0) {
							%>
							<h3 class="text-center default-text text-primary">Update
								Branch Manager</h3>
							<%
								} else {
							%>
							<h3 class="text-center default-text text-primary">Add Branch
								Manager</h3>
							<%
								}
							%>

							<div>

								<H4 align="center">
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
								</H4>

								<H4 align="center">
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
								</H4>

								<input type="hidden" name="id" value="<%=dto.getManagerId()%>">
							</div>

							<div class="md-form">

								<!-- Manager Name -->
								<span class="pl-sm-5"><b>Manager Name</b> <span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-user grey-text"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="managerName"
											placeholder="Manager Name"
											value="<%=DataUtility.getStringData(dto.getManagerName())%>">
									</div>
								</div>

								<!-- Branch Name -->
								<span class="pl-sm-5"><b>Branch Name</b> <span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-building"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="branchName"
											placeholder="Branch Name"
											value="<%=DataUtility.getStringData(dto.getBranchName())%>">
									</div>
								</div>

								<!-- Contact Number -->
								<span class="pl-sm-5"><b>Contact Number</b> <span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-phone"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="contactNumber"
											placeholder="Contact Number"
											value="<%=DataUtility.getStringData(dto.getContactNumber())%>">
									</div>
								</div>

								<div class="text-center">
									<input type="submit" name="operation"
										class="btn btn-success btn-md" value="Save"> <input
										type="submit" name="operation" class="btn btn-warning btn-md"
										value="Reset">
								</div>

							</div>

						</div>
					</div>
		</form>
		</main>
		<div class="col-md-4 mb-4"></div>

	</div>

</body>
<%@include file="FooterView.jsp"%>
</html>