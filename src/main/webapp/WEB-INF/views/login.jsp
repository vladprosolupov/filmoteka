<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page session="true" %>
<html>
<head>

    <title>Login Page</title>
    <link rel="stylesheet" href="<c:url value="/resources/styles/bulma.css"/>">
</head>
<body onload='document.loginForm.username.focus();'>

<div id="login-box">



    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
    </c:if>
    <section class="hero is-fullheight is-dark is-bold">
        <div class="hero-body">
            <div class="container">
                <div class="columns is-vcentered">
                    <div class="column is-4 is-offset-4">
    <form name='loginForm'
          action="/j_spring_security_check" method='POST'>
        <h1 class="title">Login</h1>
        <div class="box">

                <div class="label">User:</div>
                <input class="input" type='text' name='username'>

                <div class="label">Password:</div>
                <input class="input" type='password' name='password'/>
            <hr>
                <input name="submit" class="button is-primary" type="submit" value="submit"/>
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