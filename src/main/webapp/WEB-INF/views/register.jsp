<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="t" tagdir="/WEB-INF/tags/main" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    <link rel="stylesheet" href="<c:url value="/resources/styles/main.css"/>">
    <script src="<c:url value="/resources/js/jquery-3.2.1.js"/>"></script>
    <script src="<c:url value="/resources/js/registration.js"/>"></script>
    <script src="<c:url value="/resources/js/vue.js"/>"></script>

    <!-- Including CSRF token to the header -->
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <title>Sign up</title>
</head>
<t:header>
    <div id="register-box">
        <section class="hero is-fullheight">
            <div class="hero-body">
                <div class="container has-text-centered">
                    <div class="column is-4 is-offset-4">
                        <h3 class="title has-text-white">Registration</h3>
                        <p class="subtitle has-text-white">Please register to proceed.</p>
                        <div class="box">
                            <form:form action="/client/save" method="post" class="clientForm" modelAttribute="client"
                                       onsubmit="return false">
                                <div class="field">
                                    <div class="control has-icons-left">
                                        <form:input class="input is-medium" type="text" name="firstName"
                                                    placeholder="First Name" path="firstName"/>
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
                                                    placeholder="Last Name" path="lastName"/>
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
                                                    placeholder="Email" path="email"/>
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
                                                    placeholder="Username" path="login"/>
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
                                                    placeholder="Password" path="password"/>
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
                                    <div class="control">
                                        <a class="button chooseImg">
                                            <i class="fa fa-user-circle-o" style="margin-right: 5px" aria-hidden="true"></i>
                                            Choose profile image
                                        </a>
                                    </div>
                                </div>
                                <div class="field" id="radioImgs" style="display: none;">
                                    <div class="control is-grouped is-grouped-multiline">
                                        <label class="radio" style="margin-left: 0.5em">
                                            <input type="radio" name="avatar" checked="true" value="icon1">
                                            <figure class="image is-64x64">
                                                <img src="/resources/images/avatar/icon1.png"/>
                                            </figure>
                                        </label>
                                        <label class="radio">
                                            <input type="radio" name="avatar" value="icon2">
                                            <figure class="image is-64x64">
                                                <img src="/resources/images/avatar/icon2.png"/>
                                            </figure>
                                        </label>
                                        <label class="radio">
                                            <input type="radio" name="avatar" value="icon3">
                                            <figure class="image is-64x64">
                                                <img src="/resources/images/avatar/icon3.png"/>
                                            </figure>
                                        </label>
                                        <label class="radio">
                                            <input type="radio" name="avatar" value="icon4">
                                            <figure class="image is-64x64">
                                                <img src="/resources/images/avatar/icon4.png"/>
                                            </figure>
                                        </label>
                                        <label class="radio">
                                            <input type="radio" name="avatar" value="icon5">
                                            <figure class="image is-64x64">
                                                <img src="/resources/images/avatar/icon5.png"/>
                                            </figure>
                                        </label>
                                        <label class="radio">
                                            <input type="radio" name="avatar" value="icon6">
                                            <figure class="image is-64x64">
                                                <img src="/resources/images/avatar/icon6.png"/>
                                            </figure>
                                        </label>
                                        <label class="radio">
                                            <input type="radio" name="avatar" value="icon7">
                                            <figure class="image is-64x64">
                                                <img src="/resources/images/avatar/icon7.png"/>
                                            </figure>
                                        </label>
                                        <label class="radio">
                                            <input type="radio" name="avatar" value="icon8">
                                            <figure class="image is-64x64">
                                                <img src="/resources/images/avatar/icon8.png"/>
                                            </figure>
                                        </label>
                                        <label class="radio">
                                            <input type="radio" name="avatar" value="icon9">
                                            <figure class="image is-64x64">
                                                <img src="/resources/images/avatar/icon9.png"/>
                                            </figure>
                                        </label>
                                        <label class="radio">
                                            <input type="radio" name="avatar" value="icon10">
                                            <figure class="image is-64x64">
                                                <img src="/resources/images/avatar/icon10.png"/>
                                            </figure>
                                        </label>
                                        <label class="radio">
                                            <input type="radio" name="avatar" value="icon11">
                                            <figure class="image is-64x64">
                                                <img src="/resources/images/avatar/icon11.png"/>
                                            </figure>
                                        </label>
                                        <label class="radio">
                                            <input type="radio" name="avatar" value="icon12">
                                            <figure class="image is-64x64">
                                                <img src="/resources/images/avatar/icon12.png"/>
                                            </figure>
                                        </label>
                                        <label class="radio">
                                            <input type="radio" name="avatar" value="icon13">
                                            <figure class="image is-64x64">
                                                <img src="/resources/images/avatar/icon13.png"/>
                                            </figure>
                                        </label>
                                        <label class="radio">
                                            <input type="radio" name="avatar" value="icon14">
                                            <figure class="image is-64x64">
                                                <img src="/resources/images/avatar/icon14.png"/>
                                            </figure>
                                        </label>
                                        <label class="radio">
                                            <input type="radio" name="avatar" value="icon15">
                                            <figure class="image is-64x64">
                                                <img src="/resources/images/avatar/icon15.png"/>
                                            </figure>
                                        </label>
                                        <label class="radio">
                                            <input type="radio" name="avatar" value="icon16">
                                            <figure class="image is-64x64">
                                                <img src="/resources/images/avatar/icon16.png"/>
                                            </figure>
                                        </label>
                                    </div>
                                </div>
                                <div class="field">
                                    <div class="control has-icons-left">
                                        <form:input class="input is-medium" type="tel" name="phoneNumber"
                                                    placeholder="Phone Number" path="phoneNumber"/>
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
                                <form:button id="submitButton" type="submit"
                                             class="save button is-info is-large">Register</form:button>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>

</t:header>
</html>
