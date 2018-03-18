<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<div id="profile-page">
    <div class="row">
        <div class="col s12 no-padding">
            <h2>Profile</h2>
        </div>
    </div>
    <div class="row">
        <div class="col s12 no-padding">
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac tincidunt
                sapien, id lobortis
                nisi. Maecenas viverra lorem ac lacus tincidunt eleifend. Proin sodales viverra felis ut
                pellentesque. Vivamus sit amet ipsum a metus imperdiet euismod vitae vel nibh. Integer ultricies sit
                amet mauris sed tincidunt. Suspendisse euismod risus ut neque sodales, et pretium mi congue.</p>
        </div>
    </div>
    <div class="row">
        <div class="col s12 no-padding">
            <ul class="collection with-header user-information">
                <li class="collection-header"><h3>User information</h3></li>
                <li class="collection-item row">
                    <div class="col s6 no-padding">
                        <h4>Username</h4>
                    </div>
                    <div class="col s6 no-padding">
                        <p><c:out value="${user.getUsername()}"/></p>
                    </div>
                </li>
                <li class="collection-item row">
                    <div class="col s6 no-padding">
                        <h4>Email</h4>
                    </div>
                    <div class="col s6 no-padding">
                        <p><c:out value="${user.getEmail()}"/></p>
                    </div>
                </li>
                <li class="collection-item row">
                    <div class="col s6 no-padding">
                        <h4>Balance</h4>
                    </div>
                    <div class="col s6 no-padding">
                        <p class="price">$<c:out value="${f:formatPrice(user.getBalance())}"/></p>
                    </div>
                </li>
                <li class="collection-item row">
                    <div class="col s6 no-padding">
                        <h4>User role</h4>
                    </div>
                    <div class="col s6 no-padding">
                        <p><c:out value="${user.getRole()}"/></p>
                    </div>
                </li>
            </ul>
        </div>
    </div>

    <div class="row">
        <div class="col s12 no-padding">
            <h2>Your orders</h2>
        </div>
    </div>
    <div class="row">
        <div class="col s12 no-padding">
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac tincidunt
                sapien, id lobortis
                nisi. Maecenas viverra lorem ac lacus tincidunt eleifend. Proin sodales viverra felis ut
                pellentesque. Vivamus sit amet ipsum a metus imperdiet euismod vitae vel nibh. Integer ultricies sit
                amet mauris sed tincidunt. Suspendisse euismod risus ut neque sodales, et pretium mi congue.</p>
        </div>
    </div>
    <div class="row">
        <div class="col s12 no-padding">
            <c:choose>
                <c:when test="${not empty orders}">
                    <table class="highlight order-table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Comment</th>
                            <th>Total</th>
                            <th>Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${orders}" var="order">
                            <tr class="expandable">
                                <td><c:out value="${order.getId()}"/></td>
                                <td><c:out value="${order.getComment()}"/></td>
                                <td class="price">$<c:out value="${f:formatPrice(order.getTotal())}"/></td>
                                <td><c:out value="${order.getStatus()}"/></td>
                            </tr>
                            <tr class="order-items-row">
                                <td colspan="4" class="no-padding">
                                    <table class="order-items-table z-depth-0">
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
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p>You have placed no orders.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<script>
    $(".expandable").on('click', function () {
        var items_table = $(this).next();

        if (!items_table.is(":visible")) {
            items_table.show(500);
        } else {
            items_table.hide(500);
        }
    });
</script>
<%@ include file="includes/footer.jspf" %>