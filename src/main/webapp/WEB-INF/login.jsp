<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<h2>Log in</h2>
<form method="POST" class="col s12">
    <div class="input-field col s12">
        <input type="text" id="username-input" name="username" required>
        <label for="username-input">Username</label>
    </div>
    <div class="input-field col s12">
        <input type="password" id="password-input" name="password" required>
        <label for="password-input">Password</label>
    </div>
    <input class="button-submit btn-large" type="submit" value="Login">
</form>
<%@ include file="includes/footer.jspf" %>