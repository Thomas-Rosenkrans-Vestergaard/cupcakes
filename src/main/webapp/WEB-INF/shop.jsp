<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<div class="row">
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam mattis, mauris eu malesuada vehicula, justo diam
        malesuada arcu, tincidunt eleifend ligula metus eget nisl. Donec in augue ac tellus congue consectetur quis eget
        nisl. Duis tincidunt vehicula arcu aliquet imperdiet. Sed sed nisi et nulla convallis fringilla maximus a erat.
        Aliquam varius nisl ac quam lobortis, non pellentesque leo efficitur.
    </p>
    <p>If you wish to, you can create you own cupcake <a href="custom">here</a>.</p>
</div>
<div id="shop" class="row">
    <div class="col s12">
        <ul id="shop-tabs" class="tabs tabs-fixed-width">
            <li class="tab col s3 tab-presets"><a class="active" href="#presets">Presets</a></li>
            <li class="tab col s3 tab-bottoms"><a href="#bottoms">Bottoms</a></li>
            <li class="tab col s3 tab-toppings"><a href="#toppings">Toppings</a></li>
        </ul>
    </div>
    <script>

        $('.tab-presets').on('click', function () {
            location.href = "#presets";
        });

        $('.tab-bottoms').on('click', function () {
            location.href = "#bottoms";
        });

        $('.tab-toppings').on('click', function () {
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
        <ul class="preset-list row">
            <div class="col s4">
                <c:choose>
                    <c:when test="${not empty presets}">
                        <c:forEach items="${presets}" var="preset">
                            <div class="preset-content">
                                <a href="preset?id=${preset.getId()}">
                                    <img src="images/presets/${preset.getId()}.jpg" alt="${preset.getName()}">
                                </a>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>There are currently no presets available.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </ul>
    </div>
    <div id="bottoms" class="col s12">
        <h2>Bottoms</h2>
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi tortor ante, rutrum nec lacus vel, condimentum
            tincidunt mi. Nullam eu velit eu diam tempor sollicitudin at et augue. Suspendisse eu accumsan nisi. Ut
            velit ante, efficitur vitae dapibus et, pretium at nisl. Fusce fringilla ligula purus, nec semper mi maximus
            lacinia. </p>
        <ul class="preset-list row">
            <div class="col s4">
                <c:choose>
                    <c:when test="${not empty bottoms}">
                        <c:forEach items="${bottoms}" var="bottom">
                            <div class="preset-content">
                                <a href="bottom?id=${bottom.getId()}">
                                    <img src="images/bottoms/${bottom.getId()}.jpg" alt="${bottom.getName()}">
                                </a>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>There are currently no bottoms available.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </ul>
    </div>
    <div id="toppings" class="col s12">
        <h2>Toppings</h2>
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi tortor ante, rutrum nec lacus vel, condimentum
            tincidunt mi. Nullam eu velit eu diam tempor sollicitudin at et augue. Suspendisse eu accumsan nisi. Ut
            velit ante, efficitur vitae dapibus et, pretium at nisl. Fusce fringilla ligula purus, nec semper mi maximus
            lacinia. </p>
        <ul class="preset-list row">
            <div class="col s4">
                <c:choose>
                    <c:when test="${not empty toppings}">
                        <c:forEach items="${toppings}" var="topping">
                            <div class="preset-content">
                                <a href="topping?id=${topping.getId()}">
                                    <img src="images/toppings/${topping.getId()}.jpg" alt="${topping.getName()}">
                                </a>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>There are currently no toppings available.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </ul>
    </div>
</div>
<%@ include file="includes/footer.jspf" %>