<%@page import="in.co.rays.project_3.controller.PhotographerCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<title>Photographer View</title>

<style>
.hm {
	background-image:
		url('<%=ORSView.APP_CONTEXT%>/img/userRegistration.png');
	background-repeat: no-repeat;
	background-size: cover;
	padding-top: 75px;
}

.grad {
	background-image: linear-gradient(to bottom right, #ffd3ac, #f79d65);
}
</style>

</head>

<body class="hm">

	<%@include file="Header.jsp"%>

	<form action="<%=ORSView.PHOTOGRAPHER_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.PhotographerDTO"
			scope="request"></jsp:useBean>

		<div align="center">

			<h3>
				<%
					if (dto.getId() > 0) {
				%>
				Update Photographer
				<%
					} else {
				%>
				Add Photographer
				<%
					}
				%>
			</h3>

			<h4 style="color: green;">
				<%=ServletUtility.getSuccessMessage(request)%>
			</h4>

			<h4 style="color: red;">
				<%=ServletUtility.getErrorMessage(request)%>
			</h4>

			<input type="hidden" name="id" value="<%=dto.getId()%>">

			<!-- Photographer ID -->
			<b>Photographer ID:</b><br> <input type="text"
				name="photographerId"
				value="<%=DataUtility.getStringData(dto.getPhotographerId())%>">
			<font color="red"> <%=ServletUtility.getErrorMessage("photographerId", request)%>
			</font> <br> <br>

			<!-- Photographer Name -->
			<b>Photographer Name:</b><br> <input type="text"
				name="photographerName"
				value="<%=DataUtility.getStringData(dto.getPhotographerName())%>">
			<font color="red"> <%=ServletUtility.getErrorMessage("photographerName", request)%>
			</font> <br> <br>

			<!-- Event Type -->
			<b>Event Type:</b><br> <input type="text" name="eventType"
				value="<%=DataUtility.getStringData(dto.getEventType())%>">
			<font color="red"> <%=ServletUtility.getErrorMessage("eventType", request)%>
			</font> <br> <br>

			<!-- Charges -->
			<b>Charges:</b><br> <input type="text" name="charges"
				value="<%=DataUtility.getStringData(dto.getCharges())%>"> <font
				color="red"> <%=ServletUtility.getErrorMessage("charges", request)%>
			</font> <br> <br>

			<!-- Buttons -->
			<%
				if (dto.getId() > 0) {
			%>

			<input type="submit" name="operation"
				value="<%=PhotographerCtl.OP_UPDATE%>"> <input type="submit"
				name="operation" value="<%=PhotographerCtl.OP_CANCEL%>">

			<%
				} else {
			%>

			<input type="submit" name="operation"
				value="<%=PhotographerCtl.OP_SAVE%>"> <input type="submit"
				name="operation" value="<%=PhotographerCtl.OP_RESET%>">

			<%
				}
			%>

		</div>

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>