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
        <c:when test="${not empty bottoms}">
            <c:forEach items="${bottoms}" var="bottom">
                <tr>
                    <td><a href="bottoms?action=update&id=${bottom.getId()}">${bottom.getId()}</a></td>
                    <td><c:out value="${bottom.getName()}"/></td>
                    <td><c:out value="${bottom.getDescription()}"/></td>
                    <td><c:out value="${bottom.getFormattedPrice()}"/></td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <p>There are currently no bottoms.</p>
            </tr>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>
<a class="btn-floating btn-large waves-effect waves-light red right add-record" href="bottoms?action=create"><i
        class="material-icons">add</i></a>
<%@ include file="includes/footer.jspf" %>