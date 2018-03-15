<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<div class="row">
    <h2>Confirm your order</h2>
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac tincidunt sapien, id lobortis nisi. Maecenas
        viverra lorem ac lacus tincidunt eleifend. Proin sodales viverra felis ut pellentesque. Vivamus sit amet ipsum a
        metus imperdiet euismod vitae vel nibh. Integer ultricies sit amet mauris sed tincidunt. Suspendisse euismod
        risus ut neque sodales, et pretium mi congue.</p>
</div>
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
                <td><c:out value="${item.getTotalPrice()}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="row">
    <form method="post" class="col s12 no-padding">
        <input type="hidden" name="bottom" value="${bottom.getId()}">
        <input type="hidden" name="topping" valeu="${topping.getId()}">
        <div class="row">
            <div class="col s12 input-field no-padding">
                <textarea class="materialize-textarea" name="comment" id="comment-input" cols="30" rows="10"></textarea>
                <label for="comment-input">Comments for your order</label>
            </div>
        </div>
        <div class="row">
            <div class="col s12 no-padding">
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac tincidunt sapien, id lobortis
                    nisi. Maecenas viverra lorem ac lacus tincidunt eleifend. Proin sodales viverra felis ut
                    pellentesque. Vivamus sit amet ipsum a metus imperdiet euismod vitae vel nibh. Integer ultricies sit
                    amet mauris sed tincidunt. Suspendisse euismod risus ut neque sodales, et pretium mi congue.</p>
            </div>
        </div>
        <div class="row">
            <div class="col s12 no-padding">
                <p class="col s12">
                    The total cost of the order is <span class="price">$${cart.getTotal()}</span>.
                </p>
            </div>
        </div>
        <div class="row">
            <div class="col s12 no-padding">
                <input class="button-submit btn-large" type="submit" name="submit" value="Confirm order">
            </div>
        </div>
    </form>
</div>
<%@ include file="includes/footer.jspf" %>