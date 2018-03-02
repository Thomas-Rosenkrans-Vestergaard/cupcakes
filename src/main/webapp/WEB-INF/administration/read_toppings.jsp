<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/header.jspf" %>
<table class="striped">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${not empty toppings}">
            <c:forEach items="${toppings}" var="topping">
                <tr>
                    <td><a href="toppings?action=update&id=${topping.getId()}">${topping.getId()}</a></td>
                    <td><c:out value="${topping.getName()}"/></td>
                    <td><c:out value="${topping.getDescription()}"/></td>
                    <td><c:out value="${topping.getFormattedPrice()}"/></td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <p>There are currently no toppings.</p>
            </tr>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>
<a class="btn-floating btn-large waves-effect waves-light red right add-record" href="toppings?action=create"><i
        class="material-icons">add</i></a>
<%@ include file="includes/footer.jspf" %>