<%--
  Created by IntelliJ IDEA.
  User: GermanV
  Date: 22.11.17
  Time: 23:09
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/main" %>

<%int hasAccess = 0; %>
<security:authorize access="hasAnyAuthority('user')">
    <% hasAccess = 1; %>
</security:authorize>
<security:authorize access="hasAuthority('admin')">
    <% hasAccess = 2; %>
</security:authorize>


<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="<c:url value="/resources/styles/main.css"/>">
    <script src="<c:url value="/resources/js/jquery-3.2.1.js"/>"></script>
    <script src="<c:url value="/resources/js/vue.js"/>"></script>
    <script src="<c:url value="/resources/js/main.js"/>"></script>

    <!-- Including CSRF token to the header -->
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

</head>
<t:header>
    <section class="hero is-white is-small is-bold has-text-centered">
        <div class="hero-body">
            <div class="container">
                <h1 class="title">
                    Filmoteka

                </h1>
                <br>
                <h2 class="subtitle">
                    <p>This is our diploma project</p>
                    <br>
                    <p>This is a cite where user can find popular films,films which he like,find films which he should like</p>
                    <br>
                    <p>Our group:</p>
                    <br>
                    <p>Vlasyslav Prosolupov</p>
                    <p>Rostyslav Tryndiak</p>
                    <p>German Varanytsya</p>
                </h2>
            </div>
        </div>
    </section>
</t:header>
</html>

