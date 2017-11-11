<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
<body>

<section class="hero background_for_login is-success is-fullheight">
    <div class="hero-body">
        <div class="container has-text-centered">
            <div class="column is-4 is-offset-4">
                <h3 class="title has-text-white">Registration</h3>
                <p class="subtitle has-text-white">Please register to proceed.</p>
                <div class="box">
                    <form action="/client/save" method="post" class="clientForm">
                        <div class="field">
                            <div class="control has-icons-left">
                                <input class="input is-medium" type="text" name="firstName"
                                       placeholder="First Name">
                                <span class="icon is-small is-left">
                                    <i class="fa fa-address-card"></i>
                                </span>
                            </div>
                        </div>

                        <div class="field">
                            <div class="control has-icons-left">
                                <input class="input is-medium" type="text" name="lastName"
                                       placeholder="Last Name">
                                <span class="icon is-small is-left">
                                    <i class="fa fa-address-card-o"></i>
                                </span>
                            </div>
                        </div>

                        <div class="field">
                            <div class="control has-icons-left">
                                <input class="input is-medium" type="email" name="email"
                                       placeholder="Email">
                                <span class="icon is-small is-left">
                                    <i class="fa fa-envelope"></i>
                                </span>
                            </div>
                        </div>

                        <div class="field">
                            <div class="control has-icons-left">
                                <input class="input is-medium" type="text" name="login"
                                       placeholder="Username">
                                <span class="icon is-small is-left">
                                  <i class="fa fa-user"></i>
                                </span>
                            </div>
                        </div>

                        <div class="field">
                            <div class="control has-icons-left has-icon-right">
                                <input class="input is-medium" type="password" name="password"
                                       placeholder="Password">
                                <span class="icon is-small is-left">
                                    <i class="fa fa-lock"></i>
                                </span>
                            </div>
                        </div>

                        <div class="field">
                            <div class="control has-icons-left has-icon-right">
                                <input class="input is-medium" type="password" name="ignore"
                                       placeholder="Confirm password">
                                <span class="icon is-small is-left">
                                    <i class="fa fa-check"></i>
                                </span>
                            </div>
                        </div>

                        <div class="field">
                            <div class="control has-icons-left">
                                <input class="input is-medium" type="tel" name="phoneNumber"
                                       placeholder="Phone Number">
                                <span class="icon is-small is-left">
                                    <i class="fa fa-phone"></i>
                                </span>
                            </div>
                        </div>
                        <button type="button" class="save button is-primary is-large">Register</button>

                        <%--<sec:csrfInput/>--%>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

</body>
</html>
