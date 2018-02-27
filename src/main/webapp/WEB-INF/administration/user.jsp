<%@ page import="tvestergaard.cupcakes.database.users.User" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="includes/header.jspf" %>
<form class="main" method="post">
    <div class="row">
        <div class="input-field col s12">
            <input id="id" type="number" class="validate" value="${user.getId()}" disabled>
            <label for="id">ID</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12">
            <input name="username" id="username" type="text" value="${user.getUsername()}" class="validate" required>
            <label for="username">Username</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12">
            <input name="email" id="email" type="text" value="${user.getEmail()}" class="validate" required>
            <label for="email">Email</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12">
            <input name="password" id="password" type="password" pattern="{8,}" class="validate">
            <label for="password">Password</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12">
            <input name="repeat-password" id="repeat-password" type="password" pattern="{8,}" class="validate">
            <label for="repeat-password">Repeat password</label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12">
            <input name="balance" id="balance" type="number" value="${user.getBalance()}" class="validate" required>
            <label for="balance">Balance</label>
        </div>
    </div>
    <div class="row">
        <select name="role" required>
            <option value="" disabled selected>Administrator</option>
            <c:set var="roles" value="<%=User.Role.values()%>"/>
            <c:forEach items="${roles}" var="role">
                <option value="${role.code}" ${role.code == user.getRole().code ? 'selected' : ''}>${role}</option>
            </c:forEach>
        </select>
        <label>Administrator</label>
        <script>
            $(document).ready(function () {
                $('select').material_select();
            });
        </script>
    </div>
    <div class="row">
        <input class="button-submit btn-large" type="submit" value="Update">
    </div>
</form>
<form class="main" method="post" action="users?action=delete">
    <input name="id" type="hidden" value="${user.getId()}">
    <input class="button-submit btn-large" type="submit" value="Delete">
</form>
<%@ include file="includes/footer.jspf" %>