<%@page import="in.co.rays.project_3.controller.BookCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Book View</title>
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

	<jsp:useBean id="dto" class="in.co.rays.project_3.dto.BookDTO"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.BOOK_CTL%>" method="post">

		<div class="row pt-3">
			<div class="col-md-4"></div>

			<div class="col-md-4">
				<div class="card">
					<div class="card-body">

						<%
							long id = DataUtility.getLong(request.getParameter("id"));
							if (dto.getId() != null) {
						%>

						<h3 class="text-center text-primary font-weight-bold">
							Update Book
						</h3>

						<%
							} else {
						%>

						<h3 class="text-center text-primary font-weight-bold">
							Add Book
						</h3>

						<%
							}
						%>

						<input type="hidden" name="id" value="<%=dto.getId()%>">

						<!-- Book Title -->
						<label><b>Book Title</b> *</label>
						<input type="text" name="bookTitle" class="form-control"
							value="<%=DataUtility.getStringData(dto.getBookTitle())%>">

						<font color="red">
							<%=ServletUtility.getErrorMessage("bookTitle", request)%>
						</font>

						<br>

						<!-- Renter Name -->
						<label><b>Renter Name</b> *</label>
						<input type="text" name="renterName" class="form-control"
							value="<%=DataUtility.getStringData(dto.getRenterName())%>">

						<font color="red">
							<%=ServletUtility.getErrorMessage("renterName", request)%>
						</font>

						<br>

						<!-- Rent Date -->
						<label><b>Rent Date</b> *</label>
						<input type="date" name="rentDate" class="form-control"
							value="<%=DataUtility.getDateString(dto.getRentDate())%>">

						<font color="red">
							<%=ServletUtility.getErrorMessage("rentDate", request)%>
						</font>

						<br>

						<!-- Return Date -->
						<label><b>Return Date</b> *</label>
						<input type="date" name="returnDate" class="form-control"
							value="<%=DataUtility.getDateString(dto.getReturnDate())%>">

						<font color="red">
							<%=ServletUtility.getErrorMessage("returnDate", request)%>
						</font>

						<br><br>

						<%
							if (id > 0) {
						%>

						<input type="submit" name="operation" class="btn btn-success"
							value="<%=BookCtl.OP_UPDATE%>">

						<input type="submit" name="operation" class="btn btn-warning"
							value="<%=BookCtl.OP_CANCEL%>">

						<%
							} else {
						%>

						<input type="submit" name="operation" class="btn btn-success"
							value="<%=BookCtl.OP_SAVE%>">

						<input type="submit" name="operation" class="btn btn-warning"
							value="<%=BookCtl.OP_RESET%>">

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