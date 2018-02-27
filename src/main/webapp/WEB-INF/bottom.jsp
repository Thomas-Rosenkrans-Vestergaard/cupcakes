<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<img src="images/presets/${bottom.getId()}.jpg" alt="${bottom.getName()}">
<h2>${bottom.getName()}</h2>
<p>${bottom.getDescription()}</p>
<p>$${bottom.getPrice()}</p>
<%@ include file="includes/footer.jspf" %>