<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<img src="images/presets/${preset.getId()}.jpg" alt="${preset.getName()}">
<h2><c:out value="${preset.getName()}"/></h2>
<p><c:out value="${preset.getDescription()}"/></p>
<p class="price">$<c:out value="${preset.getPrice()}"/></p>
<form action="cart" method="post">
    <input type="hidden" name="topping" value="${preset.getTopping().getId()}">
    <input type="hidden" name="bottom" value="${preset.getBottom().getId()}">
    <div class="row">
        <div class="col s6 input-field">
            <input id="quantity-id" type="number" minlength="1" value="5" name="quantity" required>
            <label for="quantity-id">Amount</label>
        </div>
        <div class="col s6 input-field">
            <input class="button-submit btn-large red" type="submit" value="ADD TO CART">
        </div>
    </div>
</form>
<%@ include file="includes/footer.jspf" %>