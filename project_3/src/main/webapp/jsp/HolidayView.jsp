<%@page import="in.co.rays.project_3.controller.HolidayCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Holiday View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>

.log1{
padding-top:3%;
}

i.css{
border:2px solid #8080803b;
padding-left:10px;
padding-bottom:11px;
background-color:#ebebe0;
}

.input-group-addon{
box-shadow:9px 8px 7px #001a33;
}

.p4{
background-image:url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
background-repeat:no-repeat;
background-attachment:fixed;
background-size:cover;
padding-top:85px;
}

</style>

</head>

<body class="p4">

<div class="header">
<%@include file="Header.jsp"%>
</div>

<div>

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.HolidayDTO" scope="request"></jsp:useBean>

<main>

<form action="<%=ORSView.HOLIDAY_CTL%>" method="post">

<div class="row pt-3">

<div class="col-md-4"></div>

<div class="col-md-4">

<div class="card input-group-addon">

<div class="card-body">

<%
long id = DataUtility.getLong(request.getParameter("id"));

if(dto.getHolidayId()!=null){
%>

<h3 class="text-center text-primary font-weight-bold">
Update Holiday
</h3>

<%
}else{
%>

<h3 class="text-center text-primary font-weight-bold">
Add Holiday
</h3>

<%
}
%>

<H4 align="center">

<%
if(!ServletUtility.getSuccessMessage(request).equals("")){
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
if(!ServletUtility.getErrorMessage(request).equals("")){
%>

<div class="alert alert-danger alert-dismissible">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<%=ServletUtility.getErrorMessage(request)%>
</div>

<%
}
%>

</H4>

<input type="hidden" name="holidayId" value="<%=dto.getHolidayId()%>">

<div class="md-form input-group-addon">

<span class="pl-sm-5"><b>Holiday Code</b></span></br>

<div class="col-sm-12">

<div class="input-group">

<div class="input-group-prepend">
<div class="input-group-text">
<i class="fa fa-code"></i>
</div>
</div>

<input type="text" name="holidayCode" class="form-control"
placeholder="Enter Holiday Code"
value="<%=DataUtility.getStringData(dto.getHolidayCode())%>">

</div>
</div>

<br>

<span class="pl-sm-5"><b>Holiday Name</b></span></br>

<div class="col-sm-12">

<div class="input-group">

<div class="input-group-prepend">
<div class="input-group-text">
<i class="fa fa-calendar"></i>
</div>
</div>

<input type="text" name="holidayName" class="form-control"
placeholder="Enter Holiday Name"
value="<%=DataUtility.getStringData(dto.getHolidayName())%>">

</div>
</div>

<br>

<span class="pl-sm-5"><b>Holiday Date</b></span></br>

<div class="col-sm-12">

<div class="input-group">

<div class="input-group-prepend">
<div class="input-group-text">
<i class="fa fa-calendar-day"></i>
</div>
</div>

<input type="date" name="holidayDate" class="form-control"
value="<%=DataUtility.getStringData(dto.getHolidayDate())%>">

</div>
</div>

<br>

<span class="pl-sm-5"><b>Holiday Type</b></span></br>

<div class="col-sm-12">

<div class="input-group">

<div class="input-group-prepend">
<div class="input-group-text">
<i class="fa fa-list"></i>
</div>
</div>

<input type="text" name="holidayType" class="form-control"
placeholder="Enter Holiday Type"
value="<%=DataUtility.getStringData(dto.getHolidayType())%>">

</div>
</div>

</div>

<br><br>

<%
if(id>0){
%>

<div class="text-center">

<input type="submit" name="operation"
class="btn btn-success btn-md"
value="<%=HolidayCtl.OP_UPDATE%>">

<input type="submit" name="operation"
class="btn btn-warning btn-md"
value="<%=HolidayCtl.OP_CANCEL%>">

</div>

<%
}else{
%>

<div class="text-center">

<input type="submit" name="operation"
class="btn btn-success btn-md"
value="<%=HolidayCtl.OP_SAVE%>">

<input type="submit" name="operation"
class="btn btn-warning btn-md"
value="<%=HolidayCtl.OP_RESET%>">

</div>

<%
}
%>

</div>

</div>

</div>

<div class="col-md-4"></div>

</div>

</form>

</main>

</div>

</body>

<%@include file="FooterView.jsp"%>

</html>
