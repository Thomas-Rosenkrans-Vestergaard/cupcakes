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
        <th>Active</th>
        <th>Image</th>
        <th>Delete</th>
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
                    <td><c:out value="${bottom.getPrice()}"/></td>
                    <td>${bottom.isActive() ? "true" : "false"}</td>
                    <td class="table-image"><img src="../images/bottoms/${bottom.getId()}.jpg" alt=""></td>
                    <td>
                        <form class="main" method="post" action="bottoms?action=delete">
                            <input name="id" type="hidden" value="${bottom.getId()}">
                            <input class="button-submit btn" type="submit" value="Delete">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="5">There are currently no bottoms.</td>
            </tr>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>
<a class="btn-floating btn-large waves-effect waves-light red right add-record" href="bottoms?action=create"><i
        class="material-icons">add</i></a>
<%@ include file="includes/footer.jspf" %>