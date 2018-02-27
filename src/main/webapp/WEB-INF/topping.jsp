<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<img src="images/presets/${topping.getId()}.jpg" alt="${topping.getName()}">
<h2>${topping.getName()}</h2>
<p>${topping.getDescription()}</p>
<p>$${topping.getPrice()}</p>
<%@ include file="includes/footer.jspf" %>