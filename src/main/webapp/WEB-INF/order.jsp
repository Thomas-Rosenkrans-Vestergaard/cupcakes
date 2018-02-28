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
    <div class="col s12 custom-preview-container order-preview-container" id="bottom-preview-container">
        <div class="row">
            <div class="col s4">
                <img class="custom-image-preview" id="bottom-preview-image" src="images/bottoms/${bottom.getId()}.jpg"
                     alt="<c:out value="${bottom.getName()}"/>">
            </div>
            <div class="col s8">
                <h3 class="custom-preview-name" id="bottom-preview-name"><c:out value="${bottom.getName()}"/></h3>
                <p class="custom-preview-description" id="bottom-preview-description"><c:out
                        value="${bottom.getDescription()}"/></p>
                <p class="custom-preview-price price" id="bottom-preview-price">$<c:out
                        value="${bottom.getPrice()}"/></p>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col s12 custom-preview-container order-preview-container" id="topping-preview-container"
         style="display: block">
        <div class="row">
            <div class="col s4">
                <img class="custom-image-preview" id="topping-preview-image"
                     src="images/toppings/${topping.getId()}.jpg"
                     alt="<c:out value="${topping.getName()}"/>">
            </div>
            <div class="col s8">
                <h3 class="custom-preview-name" id="topping-preview-name"><c:out value="${topping.getName()}"/></h3>
                <p class="custom-preview-description" id="topping-preview-description"><c:out
                        value="${topping.getDescription()}"/></p>
                <p class="custom-preview-price price" id="topping-preview-price">$<c:out
                        value="${topping.getPrice()}"/></p>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <form method="post" class="col s12">
        <input type="hidden" name="bottom" value="${bottom.getId()}">
        <input type="hidden" name="topping" valeu="${topping.getId()}">
        <input class="button-submit btn-large" type="submit" name="submit" value="Confirm order">
    </form>
</div>
<%@ include file="includes/footer.jspf" %>