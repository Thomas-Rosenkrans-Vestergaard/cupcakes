<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<h2>Log in</h2>
<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi tortor ante, rutrum nec lacus vel, condimentum
    tincidunt mi. Nullam eu velit eu diam tempor sollicitudin at et augue. Suspendisse eu accumsan nisi. Ut velit ante,
    efficitur vitae dapibus et, pretium at nisl. Fusce fringilla ligula purus, nec semper mi maximus lacinia.</p>
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