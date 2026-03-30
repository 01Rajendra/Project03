<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.SubscriptionPlanCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<html>
<head>
<title>Subscription Plan</title>
</head>

<body>

	<%@include file="Header.jsp"%>

	<form action="<%=ORSView.SUBSCRIPTION_PLAN_CTL%>" method="post">

		<jsp:useBean id="dto"
			class="in.co.rays.project_3.dto.SubscriptionPlanDTO" scope="request"></jsp:useBean>

		<%
			long id = DataUtility.getLong(request.getParameter("id"));
		%>

		<center>
			<%
				if (dto.getPlanId() > 0) {
			%>
			<h2>Update Subscription Plan</h2>
			<%
				} else {
			%>
			<h2>Add Subscription Plan</h2>
			<%
				}
			%>
		</center>

		<%=ServletUtility.getErrorMessage(request)%>
		<%=ServletUtility.getSuccessMessage(request)%>

		<input type="hidden" name="id" value="<%=dto.getPlanId()%>">

		<table align="center">

			<tr>
				<th>Plan Name*</th>
				<td><input type="text" name="planName"
					value="<%=DataUtility.getStringData(dto.getPlanName())%>">
				</td>
			</tr>

			<tr>
				<th>Price*</th>
				<td><input type="text" name="price"
					value="<%=DataUtility.getStringData(dto.getPrice())%>"></td>
			</tr>

			<tr>
				<th>Validity Days*</th>
				<td><input type="text" name="validityDays"
					value="<%=DataUtility.getStringData(dto.getValidityDays())%>">
				</td>
			</tr>

			<tr>
				<td colspan="2" align="center">
					<%
						if (dto.getPlanId() > 0) {
					%> <input type="submit" name="operation"
					value="<%=SubscriptionPlanCtl.OP_UPDATE%>"> <input
					type="submit" name="operation"
					value="<%=SubscriptionPlanCtl.OP_CANCEL%>"> <%
 	} else {
 %> <input type="submit" name="operation"
					value="<%=SubscriptionPlanCtl.OP_SAVE%>"> <input
					type="submit" name="operation"
					value="<%=SubscriptionPlanCtl.OP_RESET%>"> <%
 	}
 %>

				</td>
			</tr>

		</table>

	</form>

	<%@include file="FooterView.jsp"%>

</body>
</html>
