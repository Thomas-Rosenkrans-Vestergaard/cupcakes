<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/header.jspf" %>
<table class="striped">
    <thead>
    <tr>
        <th>ID</th>
        <th>Username</th>
        <th>Email</th>
        <th>Balance</th>
        <th>Role</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${not empty users}">
            <c:forEach items="${users}" var="user">
                <tr>
                    <td><a href="users?action=update&id=${user.getId()}">${user.getId()}</a></td>
                    <td><c:out value="${user.getUsername()}"/></td>
                    <td><c:out value="${user.getEmail()}"/></td>
                    <td class="price">$<c:out value="${f:formatPrice(user.getBalance())}"/></td>
                    <td><c:out value="${user.getRole()}"/></td>
                    <td>
                        <form class="main" method="post" action="users?action=delete">
                            <input name="id" type="hidden" value="${user.getId()}">
                            <input class="button-submit btn" type="submit" value="Delete">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="5">There are currently no users.</td>
            </tr>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>
<a class="btn-floating btn-large waves-effect waves-light red right add-record" href="users?action=create"><i
        class="material-icons">add</i></a>
<%@ include file="includes/footer.jspf" %>