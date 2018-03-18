<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/header.jspf" %>
<form class="main" method="post" enctype="multipart/form-data">
    <div class="row">
        <div class="input-field col s12">
            <input readonly type="number" name="id" value="${bottom.getId()}">
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12">
            <input name="name" id="name" type="text" class="validate" value="${bottom.getName()}" required>
            <label for="name">Name</label>
        </div>
    </div>
    <div class="row">
        <div class="col s12 input-field no-padding">
            <textarea class="materialize-textarea" name="description" id="description" cols="30" rows="10" minlength="1"
                      required>${bottom.getDescription()}</textarea>
            <label for="description">Description</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12">
            <input name="price" id="price" type="number" class="validate" value="${bottom.getPrice()}" required>
            <label for="price">Price</label>
        </div>
    </div>
    <div class="row">
        <p>
            <input name="active" type="radio" value="true" id="active-true" ${bottom.isActive() ? 'checked' : ''}/>
            <label for="active-true">Active</label>
        </p>
        <p>
            <input name="active" type="radio" value="false" id="active-false" ${bottom.isActive() ? '' : 'checked'}/>
            <label for="active-false">Inactive</label>
        </p>
    </div>
    <img src="../images/bottoms/${bottom.getId()}.jpg" alt="">
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