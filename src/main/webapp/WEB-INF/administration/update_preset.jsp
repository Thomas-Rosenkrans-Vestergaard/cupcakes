<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/header.jspf" %>
<form class="main" method="post" enctype="multipart/form-data">
    <div class="row">
        <div class="input-field col s12">
            <input readonly type="number" name="id" value="${preset.getId()}">
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12">
            <input name="name" id="name" type="text" class="validate" value="${preset.getName()}" required>
            <label for="name">Name</label>
        </div>
    </div>
    <div class="row">
        <div class="col s12 input-field no-padding">
            <textarea class="materialize-textarea" name="description" id="description" cols="30" rows="10" minlength="1"
                      required>${preset.getDescription()}</textarea>
            <label for="description">Description</label>
        </div>
    </div>
    <div class="row">
        <div class="col s12 no-padding">
            <select name="bottom" id="bottom-select" required>
                <option value="" disabled selected>Choose bottom</option>
                <c:forEach items="${bottoms}" var="bottom">
                    <option value="${bottom.getId()}"
                        ${bottom.equals(preset.getBottom()) ? 'selected' : ''}>
                        <c:out value="${bottom.getName()}"/>
                    </option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="row">
        <div class="col s12 no-padding">
            <select name="topping" id="topping-select" required>
                <option value="" disabled selected>Choose topping</option>
                <c:forEach items="${toppings}" var="topping">
                    <option value="${topping.getId()}"
                        ${topping.equals(preset.getTopping()) ? 'selected' : ''}>
                        <c:out value="${topping.getName()}"/>
                    </option>
                </c:forEach>
            </select>
        </div>
    </div>
    <script>
        $("#bottom-select").material_select();
        $("#topping-select").material_select();
    </script>
    <img src="../images/presets/${preset.getId()}.jpg" alt="">
    <div class="row">
        <div class="file-field input-field">
            <div class="btn">
                <span>Image</span>
                <input name="image" id="image" type="file" accept="image/jpg," class="validate">
            </div>
            <div class="file-path-wrapper">
                <input class="file-path validate" type="text">
            </div>
        </div>
    </div>
    <div class="row">
        <input class="button-submit btn-large" type="submit" value="Update">
    </div>
</form>
<%@ include file="includes/footer.jspf" %>