<%--
  Created by IntelliJ IDEA.
  User: vladprosolupov
  Date: 29.11.2017
  Time: 23:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<t:wrapper>
    <div id="loading">
        <div class="spinner">
            <div class="double-bounce1"></div>
            <div class="double-bounce2"></div>
        </div>
    </div>
    <div class="user" style="display: none">
        <button class="button is-dark is-pulled-left has-icons-left" onclick="goBack('users')">
            <i class="fa fa-chevron-left" style="margin-right: 5px;" aria-hidden="true"></i>
            Back
        </button>
    </div>
    <form class="userForm formStyling user" data-director="${client.id}" style="display: none">
        <div class="field">
            <label class="label">Name</label>
            <p class="control">
                <input class="input" type="text" readonly="readonly" value="${client.firstName}" name="firstName"/>
            </p>
        </div>
        <div class="field">
            <label class="label">Surname</label>
            <p class="control">
                <input class="input" type="text" readonly="readonly" value="${client.lastName}" name="lastName"/>
            </p>
        </div>
        <div class="field">
            <label class="label">Email</label>
            <p class="control">
                <input class="input" type="text" readonly="readonly" value="${client.email}" name="lastName"/>
            </p>
        </div>
        <div class="field">
            <label class="label">Login</label>
            <p class="control">
                <input class="input" type="text" readonly="readonly" value="${client.login}" name="lastName"/>
            </p>
        </div>
        <div class="field">
            <label class="label">Phone number</label>
            <p class="control">
                <input class="input" type="text" readonly="readonly" value="${client.phoneNumber}" name="lastName"/>
            </p>
        </div>
        <div class="field">
            <label class="label">Enabled</label>
            <p class="control">
                <input class="input" type="text" readonly="readonly" value="${client.enabled}" name="lastName"/>
            </p>
        </div>
        <div class="field">
            <label class="label">Role</label>
            <p class="control">
                <input class="input" type="text" readonly="readonly" value="${client.role}" name="lastName"/>
            </p>
        </div>
    </form>
</t:wrapper>

