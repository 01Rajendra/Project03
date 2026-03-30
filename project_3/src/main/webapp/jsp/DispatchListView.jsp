```jsp
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.project_3.controller.DispatchListCtl"%>
<%@page import="in.co.rays.project_3.dto.DispatchDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>

<head>

<title>Dispatch List View</title>

<style>

.p4{
background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
background-repeat: no-repeat;
background-attachment: fixed;
background-size: cover;
padding-top: 85px;
}

.text{
text-align:center;
}

</style>

</head>

<body class="p4">

<%@include file="Header.jsp"%>

<form action="<%=ORSView.DISPATCH_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.DispatchDTO" scope="request"></jsp:useBean>

<%
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;

int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

List list = ServletUtility.getList(request);

Iterator<DispatchDTO> it = list.iterator();

SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>

<center>

<h1>Dispatch List</h1>

</center>

<br>

<table border="1" width="100%">

<tr>

<th>Select</th>
<th>S.NO</th>
<th>Dispatch Id</th>
<th>Dispatch Date</th>
<th>Status</th>
<th>Courier Name</th>
<th>Edit</th>

</tr>

<%
while(it.hasNext()){

dto = it.next();
%>

<tr>

<td align="center">

<input type="checkbox" name="ids" value="<%=dto.getId()%>">

</td>

<td align="center"><%=index++%></td>

<td align="center"><%=dto.getDispatchId()%></td>

<td align="center">


<%= dto.getDispatchDate() != null ? sdf.format(dto.getDispatchDate()) : "" %>


</td>

<td align="center"><%=dto.getStatus()%></td>

<td align="center"><%=dto.getCourierName()%></td>

<td align="center">

<a href="DispatchCtl?id=<%=dto.getId()%>">Edit</a>

</td>

</tr>

<%
}
%>

</table>

<br>

<table width="100%">

<tr>

<td>

<input type="submit" name="operation"
value="<%=DispatchListCtl.OP_PREVIOUS%>"
<%=pageNo > 1 ? "" : "disabled"%>>

</td>

<td>

<input type="submit" name="operation"
value="<%=DispatchListCtl.OP_NEW%>">

</td>

<td>

<input type="submit" name="operation"
value="<%=DispatchListCtl.OP_DELETE%>">

</td>

<td align="right">

<input type="submit" name="operation"
value="<%=DispatchListCtl.OP_NEXT%>"
<%=(nextPageSize != 0) ? "" : "disabled"%>>

</td>

</tr>

</table>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>

<%@include file="FooterView.jsp"%>

</body>

</html>
```
