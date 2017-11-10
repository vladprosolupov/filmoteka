<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page session="true" %>
<html>
<head>

    <title>Login Page</title>
    <link rel="stylesheet" href="<c:url value="/resources/styles/main.css"/>">
</head>
<body onload='document.loginForm.username.focus();'>

<div id="login-box">
    <section class="hero background_for_login is-fullheight is-bold">
        <div class="hero-body">
            <div class="container">
                <div class="columns is-vcentered">
                    <div class="column is-4 is-offset-4">
                        <form name='loginForm'
                              action="/j_spring_security_check" method='POST'>
                            <h1 class="title white-text">Login</h1>
                            <div class="box">
                                <div class="field">
                                    <p class="control has-icons-left">
                                        <input class="input" type='text' name='username' placeholder="Username">
                                        <span class="icon is-small is-left">
                                          <i class="fa fa-user"></i>
                                        </span>
                                    </p>
                                </div>
                                <div class="field">
                                    <p class="control has-icons-left">
                                        <input class="input" type='password' name='password' placeholder="Password"/>
                                        <span class="icon is-small is-left">
                                          <i class="fa fa-lock"></i>
                                        </span>
                                    </p>
                                </div>

                                <c:if test="${param.success == false}">
                                    <div class="msg has-text-centered has-text-danger">Your login or password is incorrect!</div>
                                </c:if>

                                <hr>

                                <input name="submit" class="button is-primary" type="submit" value="Log In"/>
                            </div>

                            <sec:csrfInput/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

</body>
</html>