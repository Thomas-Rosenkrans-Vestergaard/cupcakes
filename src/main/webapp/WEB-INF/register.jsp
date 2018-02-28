<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="includes/header.jspf" %>
<div class="row">
    <h2>Register</h2>
    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi tortor ante, rutrum nec lacus vel, condimentum
        tincidunt mi. Nullam eu velit eu diam tempor sollicitudin at et augue. Suspendisse eu accumsan nisi. Ut velit
        ante,
        efficitur vitae dapibus et, pretium at nisl. Fusce fringilla ligula purus, nec semper mi maximus lacinia.</p>
</div>
<div class="row">
    <form method="POST" class="col s12">
        <div class="row">
            <div class="input-field col s12">
                <input type="text" id="username-input" name="username" class="validate" required>
                <label for="username-input">Username</label>
            </div>
            <div class="input-field col s12">
                <input type="email" id="email-input" class="validate" name="email" required>
                <label for="email-input">Email</label>
            </div>
            <div class="input-field col s12">
                <input type="password" id="password-input" name="password" class="validate" pattern="{8,}" required>
                <label for="password-input">Password</label>
            </div>
            <div class="input-field col s12">
                <input type="password" id="password-repeat-input" name="repeat-password" class="validate" pattern="{8,}"
                       required>
                <label for="password-repeat-input">Repeat password</label>
            </div>
            <input class="button-submit btn-large" type="submit" value="Register">
        </div>
    </form>
</div>
<%@ include file="includes/footer.jspf" %>