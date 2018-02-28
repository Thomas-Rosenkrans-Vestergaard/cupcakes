<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<h2>Profile</h2>
<c:choose>
    <c:when test="${not empty cart}">
        <c:forEach items="${cart}" var="item">
            <p>${item.getBottom().getName()} + ${item.getTopping().getName()}</p>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p>No items in your cart.</p>
    </c:otherwise>
</c:choose>
<%@ include file="includes/footer.jspf" %>