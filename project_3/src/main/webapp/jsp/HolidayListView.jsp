<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.HolidayListCtl"%>
<%@page import="in.co.rays.project_3.dto.HolidayDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Holiday List View</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>

<script type="text/javascript"
src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>

.p4{
background-image:url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
background-repeat:no-repeat;
background-attachment:fixed;
background-size:cover;
padding-top:85px;
}

.text{
text-align:center;
}

.table-hover tbody tr:hover td{
background-color:#0064ff36;
}

</style>

</head>

<body class="p4">

<div>
<%@include file="Header.jsp"%>
</div>

<div>

<form action="<%=ORSView.HOLIDAY_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.HolidayDTO"
scope="request"></jsp:useBean>

<%

int pageNo = ServletUtility.getPageNo(request);

int pageSize = ServletUtility.getPageSize(request);

int index = ((pageNo - 1) * pageSize) + 1;

int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

List list = ServletUtility.getList(request);

Iterator<HolidayDTO> it = list.iterator();

if(list.size()!=0){

%>

<center>

<h1 class="text-primary font-weight-bold pt-3">
<font color="black">Holiday List</font>
</h1>

</center>

<br>

<div class="table-responsive">

<table class="table table-bordered table-dark table-hover">

<thead>

<tr style="background-color:#8C8C8C;">

<th width="10%">
<input type="checkbox" id="select_all"> Select All
</th>

<th class="text">S.NO</th>

<th class="text">Holiday Code</th>

<th class="text">Holiday Name</th>

<th class="text">Holiday Date</th>

<th class="text">Holiday Type</th>

<th class="text">Edit</th>

</tr>

</thead>

<%

while(it.hasNext()){

dto = it.next();

%>

<tbody>

<tr>

<td align="center">
<input type="checkbox" class="checkbox"
name="ids" value="<%=dto.getHolidayId()%>">
</td>

<td align="center"><%=index++%></td>

<td align="center"><%=dto.getHolidayCode()%></td>

<td align="center"><%=dto.getHolidayName()%></td>

<td align="center"><%=dto.getHolidayDate()%></td>

<td align="center"><%=dto.getHolidayType()%></td>

<td align="center">
<a href="HolidayCtl?id=<%=dto.getHolidayId()%>">Edit</a>
</td>

</tr>

</tbody>

<%
}
%>

</table>

</div>

<table width="100%">

<tr>

<td>

<input type="submit" name="operation"
class="btn btn-secondary btn-md"
value="<%=HolidayListCtl.OP_PREVIOUS%>"
<%=pageNo>1?"":"disabled"%>>

</td>

<td>

<input type="submit" name="operation"
class="btn btn-primary btn-md"
value="<%=HolidayListCtl.OP_NEW%>">

</td>

<td>

<input type="submit" name="operation"
class="btn btn-danger btn-md"
value="<%=HolidayListCtl.OP_DELETE%>">

</td>

<td align="right">

<input type="submit" name="operation"
class="btn btn-secondary btn-md"
value="<%=HolidayListCtl.OP_NEXT%>"
<%=(nextPageSize!=0)?"":"disabled"%>>

</td>

</tr>

</table>

<%
}

if(list.size()==0){
%>

<center>

<h1 style="font-size:40px;padding-top:24px;color:#162390;">
Holiday List
</h1>

</center>

<br>

<div style="padding-left:48%;">

<input type="submit" name="operation"
class="btn btn-primary btn-md"
value="<%=HolidayListCtl.OP_BACK%>">

</div>

<%
}
%>

<input type="hidden" name="pageNo" value="<%=pageNo%>">

<input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>

</div>

<br><br>

<%@include file="FooterView.jsp"%>

</body>

</html>
