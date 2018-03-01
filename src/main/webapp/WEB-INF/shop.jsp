<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<div class="row">
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam mattis, mauris eu malesuada vehicula, justo diam
        malesuada arcu, tincidunt eleifend ligula metus eget nisl. Donec in augue ac tellus congue consectetur quis eget
        nisl. Duis tincidunt vehicula arcu aliquet imperdiet. Sed sed nisi et nulla convallis fringilla maximus a erat.
        Aliquam varius nisl ac quam lobortis, non pellentesque leo efficitur.
    </p>
</div>
<div id="shop" class="row">
    <div class="col s12">
        <ul id="shop-tabs" class="tabs tabs-fixed-width">
            <li class="tab col s3" id="tab-presets"><a class="active" href="#presets">Presets</a></li>
            <li class="tab col s3" id="tab-bottoms"><a href="#bottoms">Bottoms</a></li>
            <li class="tab col s3" id="tab-toppings"><a href="#toppings">Toppings</a></li>
        </ul>
    </div>
    <script>

        $('#tab-presets').on('click', function () {
            location.href = "#presets";
        });

        $('#tab-bottoms').on('click', function () {
            location.href = "#bottoms";
        });

        $('#tab-toppings').on('click', function () {
            location.href = "#toppings";
        });

        $("#navigation-presets").on('click', function (e) {
            $('#shop-tabs').tabs('select_tab', 'presets');
        });

        $("#navigation-bottoms").on('click', function (e) {
            location.href = "#bottoms";
            $('#shop-tabs').tabs('select_tab', 'bottoms');
        });

        $("#navigation-toppings").on('click', function (e) {
            location.href = "#toppings";
            $('#shop-tabs').tabs('select_tab', 'toppings');
        });
    </script>
    <div id="presets" class="col s12">
        <h2>Presets</h2>
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi tortor ante, rutrum nec lacus vel, condimentum
            tincidunt mi. Nullam eu velit eu diam tempor sollicitudin at et augue. Suspendisse eu accumsan nisi. Ut
            velit ante, efficitur vitae dapibus et, pretium at nisl. Fusce fringilla ligula purus, nec semper mi maximus
            lacinia. </p>
        <ul class="shop-item-list row">
            <c:choose>
                <c:when test="${not empty presets}">
                    <c:forEach items="${presets}" var="preset">
                        <div class="col s4 shop-item">
                                <div class="shop-item-details">
                                    <h2 class="shop-item-name"><c:out value="${preset.getName()}"/></h2>
                                    <p class="shop-item-description"><c:out value="${preset.getDescription()}"/></p>
                                    <p class="shop-item-price price">$<c:out value="${preset.getFormattedPrice()}"/></p>
                                </div>
                            <a href="preset?id=${preset.getId()}" class="btn red">MORE</a>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>There are currently no presets available.</p>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
<div id="bottoms" class="col s12">
    <h2>Bottoms</h2>
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi tortor ante, rutrum nec lacus vel, condimentum
        tincidunt mi. Nullam eu velit eu diam tempor sollicitudin at et augue. Suspendisse eu accumsan nisi. Ut
        velit ante, efficitur vitae dapibus et, pretium at nisl. Fusce fringilla ligula purus, nec semper mi maximus
        lacinia. </p>
    <ul class="shop-item-list row">
        <c:choose>
            <c:when test="${not empty bottoms}">
                <c:forEach items="${bottoms}" var="bottom">
                    <div class="col s4 shop-item">
                            <div class="shop-item-details">
                                <h2 class="shop-item-name"><c:out value="${bottom.getName()}"/></h2>
                                <p class="shop-item-description"><c:out value="${bottom.getDescription()}"/></p>
                                <p class="shop-item-price price">$<c:out value="${bottom.getFormattedPrice()}"/></p>
                            </div>
                        <a href="bottom?id=${bottom.getId()}" class="btn red">MORE</a>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>There are currently no bottoms available.</p>
            </c:otherwise>
        </c:choose>
    </ul>
</div>
</ul>
</div>
<div id="toppings" class="col s12">
    <h2>Toppings</h2>
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi tortor ante, rutrum nec lacus vel, condimentum
        tincidunt mi. Nullam eu velit eu diam tempor sollicitudin at et augue. Suspendisse eu accumsan nisi. Ut
        velit ante, efficitur vitae dapibus et, pretium at nisl. Fusce fringilla ligula purus, nec semper mi maximus
        lacinia. </p>
    <ul class="shop-item-list row">
        <c:choose>
            <c:when test="${not empty toppings}">
                <c:forEach items="${toppings}" var="topping">

                    <div class="col s4 shop-item">
                            <div class="shop-item-details">
                                <h2 class="shop-item-name"><c:out value="${topping.getName()}"/></h2>
                                <p class="shop-item-description"><c:out value="${topping.getDescription()}"/></p>
                                <p class="shop-item-price price">$<c:out value="${topping.getFormattedPrice()}"/></p>
                            </div>
                        <a href="topping?id=${topping.getId()}" class="btn red">MORE</a>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>There are currently no toppings available.</p>
            </c:otherwise>
        </c:choose>
    </ul>
</div>
<%@ include file="includes/footer.jspf" %>