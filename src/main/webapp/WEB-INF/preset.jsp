<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<img src="images/presets/${preset.getId()}.jpg" alt="${preset.getName()}">
<h2><c:out value="${bottom.getName()}"/></h2>
<p><c:out value="${bottom.getDescription()}"/></p>
<p>$<c:out value="${bottom.getPrice()}"/></p>
<%@ include file="includes/footer.jspf" %>