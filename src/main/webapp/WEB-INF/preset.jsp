<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<img src="images/presets/${preset.getId()}.jpg" alt="${preset.getName()}">
<h2>${preset.getName()}</h2>
<p>${preset.getDescription()}</p>
<p>$${preset.getPrice()}</p>
<%@ include file="includes/footer.jspf" %>