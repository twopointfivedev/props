<%@page import="net.property.data.LocationDB"%>
<%@page import="net.property.data.Location"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<% request.setCharacterEncoding("UTF-8");%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	String data = request.getParameter("data");
%>
<html>
<body> 
<%
	if(data!=null && !data.isEmpty()) {
		//data = StringEscapeUtils.unescapeHtml4(data);
		Location loc = LocationDB.getInstance().normalizeLocation(data);
		out.write("<span style=\"color:red\">"+loc.getLocation(1)+"</span><br/>");	
		out.write("<span style=\"color:red\">"+loc.getLocation(2)+"</span><br/>");	
		out.write("<span style=\"color:red\">"+loc.getLocation(3)+"</span><br/>");	
		out.write("<span style=\"color:red\">"+loc.getLocation(4)+"</span><br/>");		
	}
%>
<form action="" method="get">
	<label>Normalize:</label>
	<input type="text" name="data"/>
	<input type="submit"/>
</form>

</body>
</html>