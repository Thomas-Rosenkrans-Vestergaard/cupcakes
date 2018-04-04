<%@ page import="tvestergaard.cupcakes.data.users.User" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/header.jspf" %>
<form class="main" method="post">
    <div class="row">
        <div class="input-field col s12">
            <input name="username" id="username" type="text" class="validate" required>
            <label for="username">Username</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12">
            <input name="email" id="email" type="text" class="validate" required>
            <label for="email">Email</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12">
            <input name="password" id="password" type="password" pattern="{4,}" class="validate" required>
            <label for="password">Password</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12">
            <input name="balance" id="balance" type="number" class="validate" required>
            <label for="balance">Balance</label>
        </div>
    </div>
    <div class="row">
        <select name="role" required>
            <option value="" disabled selected>Role</option>
            <c:set var="roles" value="<%=User.Role.values()%>"/>
            <c:forEach items="${roles}" var="role">
                <option value="${role.code}">${role}</option>
            </c:forEach>
        </select>
        <label>Role</label>
        <script>
            $(document).ready(function () {
                $('select').material_select();
            });
        </script>
    </div>
    <div class="row">
        <input class="button-submit btn-large" type="submit" value="Create">
    </div>
</form>
<%@ include file="includes/footer.jspf" %>