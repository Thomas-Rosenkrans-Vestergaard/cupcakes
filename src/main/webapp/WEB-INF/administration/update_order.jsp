<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/header.jspf" %>
<form class="main" method="post" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${bottom.getId()}">
    <div class="row">
        <div class="col s12 input-field no-padding">
            <input disabled type="text" id="user" name="user" value="${order.getUser().getUsername()}">
            <label for="user"></label>
        </div>
    </div>
    <div class="row">
        <div class="col s12 input-field no-padding">
            <textarea class="materialize-textarea" name="comment" id="comment" cols="30" rows="10" minlength="1"
                      required>${preset.getComment()}</textarea>
            <label for="comment">Comment</label>
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