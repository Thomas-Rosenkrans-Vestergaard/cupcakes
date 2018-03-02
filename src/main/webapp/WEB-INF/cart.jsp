<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<h2>Shopping cart</h2>
<div class="row">
    <div class="col s12 no-padding">
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi tortor ante, rutrum nec lacus vel, condimentum
            tincidunt mi. Nullam eu velit eu diam tempor sollicitudin at et augue. Suspendisse eu accumsan nisi. Ut
            velitante,efficitur vitae dapibus et, pretium at nisl. Fusce fringilla ligula purus, nec semper mi maximus
            lacinia.</p>
    </div>
</div>
<c:choose>
    <c:when test="${!cart.isEmpty()}">
        <div class="row">
            <table class="col s12 no-paddin responsive-table bordered">
                <head>
                    <tr>
                        <th>Bottom</th>
                        <th>Topping</th>
                        <th>Amount</th>
                        <th>Price</th>
                    </tr>
                </head>
                <tbody>
                <c:forEach items="${cart.iterator()}" var="item">
                    <tr>
                        <td><c:out value="${item.getBottom().getName()}"/></td>
                        <td><c:out value="${item.getTopping().getName()}"/></td>
                        <td><c:out value="${item.getAmount()}"/></td>
                        <td class="price">$<c:out value="${item.getFormattedTotalPrice()}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="row">
            <div class="col s12 no-padding">
                <p class="col s12">
                    The total price of your cart is <span class="price">$${cart.getFormattedTotal()}</span>.
                </p>
            </div>
        </div>
        <div class="row">
            <form action="order" method="get">
                <input class="button-submit btn-large" type="submit" value="Place order">
            </form>
        </div>
    </c:when>
    <c:otherwise>
        <p>No items in your cart.</p>
    </c:otherwise>
</c:choose>
<%@ include file="includes/footer.jspf" %>