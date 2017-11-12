<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    <%@ taglib prefix="t" tagdir="/WEB-INF/tags/main" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    <link rel="stylesheet" href="<c:url value="/resources/styles/main.css"/>">
    <script src="<c:url value="/resources/js/jquery-3.2.1.js"/>"></script>
    <script src="<c:url value="/resources/js/user.js"/>"></script>
    <script src="<c:url value="/resources/js/vue.js"/>"></script>

    <!-- Including CSRF token to the header -->
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <title>Sign up</title>
</head>
<t:header>

<section class="hero background_for_client is-success is-fullheight">
    <div class="hero-body">
        <div class="container has-text-centered">
            <div class="column is-4 is-offset-4">
                <h3 class="title has-text-white">Registration</h3>
                <p class="subtitle has-text-white">Please register to proceed.</p>
                <div class="box">
                    <%--<form action="/client/save" method="post" class="clientForm">--%>
                        <%--<div class="field">--%>
                            <%--<div class="control has-icons-left">--%>
                                <%--<input class="input is-medium" type="text" name="firstName"--%>
                                       <%--placeholder="First Name">--%>
                                <%--<span class="icon is-small is-left">--%>
                                    <%--<i class="fa fa-address-card"></i>--%>
                                <%--</span>--%>
                            <%--</div>--%>
                            <%--<p class="help is-danger" style="display: none">--%>
                                <%--First name cannot be shorter than 2 symbols and contain numeric!--%>
                            <%--</p>--%>
                        <%--</div>--%>

                        <%--<div class="field">--%>
                            <%--<div class="control has-icons-left">--%>
                                <%--<input class="input is-medium" type="text" name="lastName"--%>
                                       <%--placeholder="Last Name">--%>
                                <%--<span class="icon is-small is-left">--%>
                                    <%--<i class="fa fa-address-card-o"></i>--%>
                                <%--</span>--%>
                            <%--</div>--%>
                            <%--<p class="help is-danger" style="display: none">--%>
                                <%--Last name cannot be shorter than 2 symbols and contain numeric!--%>
                            <%--</p>--%>
                        <%--</div>--%>

                        <%--<div class="field">--%>
                            <%--<div class="control has-icons-left">--%>
                                <%--<input class="input is-medium" type="email" name="email"--%>
                                       <%--placeholder="Email">--%>
                                <%--<span class="icon is-small is-left">--%>
                                    <%--<i class="fa fa-envelope"></i>--%>
                                <%--</span>--%>
                            <%--</div>--%>
                            <%--<p class="help is-danger" style="display: none">--%>
                                <%--Please enter valid email!--%>
                            <%--</p>--%>
                        <%--</div>--%>

                        <%--<div class="field">--%>
                            <%--<div class="control has-icons-left">--%>
                                <%--<input class="input is-medium" type="text" name="login"--%>
                                       <%--placeholder="Username">--%>
                                <%--<span class="icon is-small is-left">--%>
                                  <%--<i class="fa fa-user"></i>--%>
                                <%--</span>--%>
                            <%--</div>--%>
                            <%--<p class="help is-danger" style="display:none">--%>
                                <%--Username cannot be shorter than 2 symbols!--%>
                            <%--</p>--%>
                            <%--<p class="help is-danger" style="display: none">--%>
                                <%--This Username is already taken, please pick another one!--%>
                            <%--</p>--%>
                        <%--</div>--%>

                        <%--<div class="field">--%>
                            <%--<div class="control has-icons-left has-icon-right">--%>
                                <%--<input class="input is-medium" type="password" name="password"--%>
                                       <%--placeholder="Password">--%>
                                <%--<span class="icon is-small is-left">--%>
                                    <%--<i class="fa fa-lock"></i>--%>
                                <%--</span>--%>
                            <%--</div>--%>
                            <%--<p class="help is-danger" style="display:none">--%>
                                <%--Password cannot be shorter than 8 symbols!--%>
                            <%--</p>--%>
                        <%--</div>--%>

                        <%--<div class="field">--%>
                            <%--<div class="control has-icons-left has-icons-right">--%>
                                <%--<input class="input is-medium" type="password" name="ignore"--%>
                                       <%--placeholder="Confirm password">--%>
                                <%--<span class="icon is-small is-left">--%>
                                    <%--<i class="fa fa-check"></i>--%>
                                <%--</span>--%>
                                <%--<span class="icon is-small is-right" style="display: none">--%>
                                    <%--<i class="fa fa-warning"></i>--%>
                                <%--</span>--%>
                            <%--</div>--%>
                            <%--<p class="help is-danger" style="display:none">--%>
                                <%--Password cannot be shorter than 8 symbols!--%>
                            <%--</p>--%>
                            <%--<p class="help is-danger" style="display: none">--%>
                                <%--Passwords are not same!--%>
                            <%--</p>--%>
                        <%--</div>--%>

                        <%--<div class="field">--%>
                            <%--<div class="control has-icons-left">--%>
                                <%--<input class="input is-medium" type="tel" name="phoneNumber"--%>
                                       <%--placeholder="Phone Number">--%>
                                <%--<span class="icon is-small is-left">--%>
                                    <%--<i class="fa fa-phone"></i>--%>
                                <%--</span>--%>
                            <%--</div>--%>
                            <%--<p class="help is-danger" style="display: none">--%>
                                <%--Phone number cannot be shorter than 6 symbols!--%>
                            <%--</p>--%>
                        <%--</div>--%>
                        <%--<button type="button" class="save button is-primary is-large">Register</button>--%>

                        <%--&lt;%&ndash;<sec:csrfInput/>&ndash;%&gt;--%>
                    <%--</form>--%>

                        <form:form action="save" method="post" class="clientForm" modelAttribute="client">
                            <div class="field">
                                <div class="control has-icons-left">
                                    <form:input class="input is-medium" type="text" name="firstName"
                                           placeholder="First Name" path="firstName" />
                                    <span class="icon is-small is-left">
                                    <i class="fa fa-address-card"></i>
                                </span>
                                </div>
                                <p class="help is-danger" style="display: none">
                                    First name cannot be shorter than 2 symbols and contain numeric!
                                </p>
                            </div>

                            <div class="field">
                                <div class="control has-icons-left">
                                    <form:input class="input is-medium" type="text" name="lastName"
                                           placeholder="Last Name" path="lastName" />
                                    <span class="icon is-small is-left">
                                    <i class="fa fa-address-card-o"></i>
                                </span>
                                </div>
                                <p class="help is-danger" style="display: none">
                                    Last name cannot be shorter than 2 symbols and contain numeric!
                                </p>
                            </div>

                            <div class="field">
                                <div class="control has-icons-left">
                                    <form:input class="input is-medium" type="email" name="email"
                                           placeholder="Email" path="email" />
                                    <span class="icon is-small is-left">
                                    <i class="fa fa-envelope"></i>
                                </span>
                                </div>
                                <p class="help is-danger" style="display: none">
                                    Please enter valid email!
                                </p>
                            </div>

                            <div class="field">
                                <div class="control has-icons-left">
                                    <form:input class="input is-medium" type="text" name="login"
                                           placeholder="Username" path="login" />
                                    <span class="icon is-small is-left">
                                  <i class="fa fa-user"></i>
                                </span>
                                </div>
                                <p class="help is-danger" style="display:none">
                                    Username cannot be shorter than 2 symbols!
                                </p>
                                <p class="help is-danger" style="display: none">
                                    This Username is already taken, please pick another one!
                                </p>
                            </div>

                            <div class="field">
                                <div class="control has-icons-left has-icon-right">
                                    <form:input class="input is-medium" type="password" name="password"
                                           placeholder="Password" path="password" />
                                    <span class="icon is-small is-left">
                                    <i class="fa fa-lock"></i>
                                </span>
                                </div>
                                <p class="help is-danger" style="display:none">
                                    Password cannot be shorter than 8 symbols!
                                </p>
                            </div>

                            <div class="field">
                                <div class="control has-icons-left has-icons-right">
                                    <input class="input is-medium" type="password" name="ignore"
                                           placeholder="Confirm password">
                                    <span class="icon is-small is-left">
                                    <i class="fa fa-check"></i>
                                </span>
                                    <span class="icon is-small is-right" style="display: none">
                                    <i class="fa fa-warning"></i>
                                </span>
                                </div>
                                <p class="help is-danger" style="display:none">
                                    Password cannot be shorter than 8 symbols!
                                </p>
                                <p class="help is-danger" style="display: none">
                                    Passwords are not same!
                                </p>
                            </div>

                            <div class="field">
                                <div class="control has-icons-left">
                                    <form:input class="input is-medium" type="tel" name="phoneNumber"
                                           placeholder="Phone Number" path="phoneNumber" />
                                    <span class="icon is-small is-left">
                                    <i class="fa fa-phone"></i>
                                </span>
                                </div>
                                <p class="help is-danger" style="display: none">
                                    Phone number cannot be shorter than 6 symbols!
                                </p>
                            </div>
                            <input type="hidden"
                                   name="${_csrf.parameterName}"
                                   value="${_csrf.token}"/>
                            <form:button type="submit" class="save button is-primary is-large">Register</form:button>

                                <%--<sec:csrfInput/>--%>
                        </form:form>

                </div>
            </div>
        </div>
    </div>
</section>

</t:header>
</html>
