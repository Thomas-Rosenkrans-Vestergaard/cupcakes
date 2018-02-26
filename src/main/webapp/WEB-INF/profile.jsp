<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<h2>You are logged in as <%= session.getAttribute("user")%>.</h2>
<%@ include file="includes/footer.jspf" %>