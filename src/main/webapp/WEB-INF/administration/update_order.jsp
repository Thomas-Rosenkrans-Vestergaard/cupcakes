<%@ page import="tvestergaard.cupcakes.database.orders.Order" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/header.jspf" %>
<form class="main" method="post" enctype="multipart/form-data">
    <div class="row">
        <div class="input-field col s12">
            <input disabled type="number" name="id" value="${order.getId()}">
        </div>
    </div>
    <div class="row">
        <div class="col s12 input-field no-padding">
            <input disabled type="text" id="user" name="user" value="${order.getUser().getUsername()}">
            <label for="user">Username</label>
        </div>
    </div>
    <div class="row">
        <div class="col s12 input-field no-padding">
            <textarea class="materialize-textarea" name="comment" id="comment" cols="30" rows="10" minlength="1"
                      required>${order.getComment()}</textarea>
            <label for="comment">Comment</label>
        </div>
    </div>
    <div class="row">
        <div class="col s12 no-padding">
            <c:set var="statuses" value="<%= Order.Status.values() %>"/>
            <select name="status" id="status-select" required>
                <c:forEach items="${statuses}" var="status">
                    <option value="${status.getCode()}"
                        ${order.getStatus().equals(status) ? 'selected' : ''}>
                        <c:out value="${status.toString()}"/>
                    </option>
                </c:forEach>
            </select>
        </div>
    </div>
    <script>
        $("#status-select").material_select();
    </script>
    <div class="row">
        <input class="button-submit btn-large" type="submit" value="Update">
    </div>
</form>
<%@ include file="includes/footer.jspf" %>