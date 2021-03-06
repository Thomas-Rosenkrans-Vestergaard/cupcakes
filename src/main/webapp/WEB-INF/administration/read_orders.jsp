<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/header.jspf" %>
<table class="striped">
    <thead>
    <tr>
        <th>ID</th>
        <th>User</th>
        <th>Comment</th>
        <th>Total</th>
        <th>Status</th>
        <th>Created at</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${not empty orders}">
            <c:forEach items="${orders}" var="order">
                <tr>
                    <td><a href="orders?action=update&id=${order.getId()}">${order.getId()}</a></td>
                    <td><c:out value="${order.getUser().getUsername()}"/></td>
                    <td><c:out value="${order.getComment()}"/></td>
                    <td class="price">$<c:out value="${f:formatPrice(order.getTotal())}"/></td>
                    <td><c:out value="${order.getStatus()}"/></td>
                    <td><c:out value="${f:formatTimestamp(order.getCreatedAt())}"/></td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="5">There are currently no orders.</td>
            </tr>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>
<%@ include file="includes/footer.jspf" %>