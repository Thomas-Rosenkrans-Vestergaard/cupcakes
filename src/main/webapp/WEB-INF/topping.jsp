<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<img src="images/toppings/${topping.getId()}.jpg" alt="${topping.getName()}">
<h2><c:out value="${topping.getName()}"/></h2>
<p><c:out value="${topping.getDescription()}"/></p>
<p class="price">$<c:out value="${topping.getPrice()}"/></p>
<%@ include file="includes/footer.jspf" %>