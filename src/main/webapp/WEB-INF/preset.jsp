<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<img src="images/presets/${preset.getId()}.jpg" alt="${preset.getName()}">
<h2><c:out value="${preset.getName()}"/></h2>
<p><c:out value="${preset.getDescription()}"/></p>
<p class="price">$<c:out value="${preset.getFormattedPrice()}"/></p>
<%@ include file="includes/footer.jspf" %>