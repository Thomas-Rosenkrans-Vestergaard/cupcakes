<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/header.jspf" %>
<table class="striped">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Bottom</th>
        <th>Topping</th>
        <th>Price</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${not empty presets}">
            <c:forEach items="${presets}" var="preset">
                <tr>
                    <td><a href="presets?action=update&id=${preset.getId()}">${preset.getId()}</a></td>
                    <td><c:out value="${preset.getName()}"/></td>
                    <td><c:out value="${preset.getDescription()}"/></td>
                    <td><c:out value="${preset.getBottom().getName()}"/></td>
                    <td><c:out value="${preset.getTopping().getName()}"/></td>
                    <td><c:out value="${preset.getFormattedPrice()}"/></td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <p>There are currently no presets.</p>
            </tr>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>
<a class="btn-floating btn-large waves-effect waves-light red right add-record" href="presets?action=create"><i
        class="material-icons">add</i></a>
<%@ include file="includes/footer.jspf" %>