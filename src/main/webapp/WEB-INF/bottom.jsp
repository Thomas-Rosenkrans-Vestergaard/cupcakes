<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<img src="images/bottoms/${bottom.getId()}.jpg" alt="${bottom.getName()}">
<h2><c:out value="${bottom.getName()}"/></h2>
<p><c:out value="${bottom.getDescription()}"/></p>
<p class="price">$<c:out value="${bottom.getPrice()}"/></p>
<%@ include file="includes/footer.jspf" %>