<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/main" %>
<%@page session="true" %>

<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="<c:url value="/resources/styles/main.css"/>">
</head>
<t:header>

    <div id="login-box" onload='document.loginForm.username.focus();'>
        <section class="hero is-fullheight is-bold">
            <div class="hero-body">
                <div class="container has-text-centered">
                    <c:if test="${param.confirmed == true}">
                        <article class="message is-success" v-if="notFound">
                            <div class="message-body">
                                Your email was successfully <strong>confirmed</strong>! Now you can
                                <strong>Login</strong>!
                            </div>
                        </article>
                    </c:if>

                    <c:if test="${param.confirmed == false}">
                        <article class="message is-info" v-if="notFound">
                            <div class="message-body">
                                We have sent a confirmation to your <strong>email</strong>. Please confirm it in order
                                to finish the registration.
                            </div>
                        </article>
                    </c:if>

                    <c:if test="${param.reset == false}">
                        <article class="message is-info" v-if="notFound">
                            <div class="message-body">
                                We have sent you a <strong>confirmation</strong> of password reset to your email!
                            </div>
                        </article>
                    </c:if>

                    <c:if test="${param.reset == true}">
                        <article class="message is-success" v-if="notFound">
                            <div class="message-body">
                                You have successfully <strong>reset</strong> your password! Now you can
                                <strong>Login</strong> using it!
                            </div>
                        </article>
                    </c:if>

                    <div class="column is-4 is-offset-4">
                        <h3 class="title has-text-white">Login</h3>
                        <form name='loginForm'
                              action="/j_spring_security_check" method='POST'>
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
                                    <div class="msg has-text-centered has-text-danger">Your login or password is
                                        incorrect!
                                        <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
                                    </div>
                                </c:if>
                                <div class="columns">
                                    <div class="column has-text-centered has-text-info is-half ">
                                        <a href="/forgotPassword" style="text-decoration: none">Forgot password?</a>
                                    </div>

                                    <div class="column has-text-centered has-text-info is-half">
                                        <a href="/register" style="text-decoration: none">Sign up!</a>
                                    </div>
                                </div>

                                <hr>
                                <input name="submit" class="button is-info is-medium" type="submit" value="Log In"/>
                            </div>

                            <sec:csrfInput/>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </div>

</t:header>
<script type="application/javascript">
    window.history.pushState({}, document.title, "/login");
</script>
</html>