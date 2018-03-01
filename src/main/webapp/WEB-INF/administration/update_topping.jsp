<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/header.jspf" %>
<form class="main" method="post">
    <div class="row">
        <div class="input-field col s12">
            <input name="name" id="name" type="text" class="validate" value="${topping.getName()}" required>
            <label for="name">Name</label>
        </div>
    </div>
    <div class="row">
        <div class="col s12 input-field no-padding">
            <textarea class="materialize-textarea" name="description" id="description" cols="30" rows="10" minlength="1"
                      required>${topping.getDescription()}</textarea>
            <label for="description">Description</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12">
            <input name="price" id="price" type="number" class="validate" value="${topping.getPrice()}" required>
            <label for="price">Price</label>
        </div>
    </div>
    <div class="row">
        <input class="button-submit btn-large" type="submit" value="Update">
    </div>
</form>
<form class="main" method="post" action="toppings?action=delete">
    <input name="id" type="hidden" value="${topping.getId()}">
    <input class="button-submit btn-large" type="submit" value="Delete">
</form>
<%@ include file="includes/footer.jspf" %>