<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<h2>Profile</h2>
<ul>
    <li><c:out value="${user.getName()}"/></li>
    <li><c:out value="${user.getEmail()}"/></li>
    <li><c:out value="${user.getBalance()}"/></li>
    <li><c:out value="${user.getRole()}"/></li>
</ul>
<%@ include file="includes/footer.jspf" %>