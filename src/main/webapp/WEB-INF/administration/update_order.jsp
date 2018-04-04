<%@ page import="tvestergaard.cupcakes.data.orders.Order" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/header.jspf" %>
<form class="main" method="post">
    <div class="row">
        <div class="input-field col s12">
            <input readonly type="number" name="id" value="${order.getId()}">
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
    <div class="row">
        <div class="col s12 no-padding">
            <table class="order-items-table">
                <caption>Items</caption>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Bottom</th>
                    <th>Topping</th>
                    <th>Quantity</th>
                    <th>Unit price</th>
                    <th>Total price</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${order.getItems()}" var="item">
                    <tr>
                        <td><c:out value="${item.getId()}"/></td>
                        <td><c:out value="${item.getBottom().getName()}"/></td>
                        <td><c:out value="${item.getTopping().getName()}"/></td>
                        <td><c:out value="${item.getQuantity()}"/></td>
                        <td class="price">$<c:out value="${f:formatPrice(item.getUnitPrice())}"/></td>
                        <td class="price">$<c:out value="${f:formatPrice(item.getTotalPrice())}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
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