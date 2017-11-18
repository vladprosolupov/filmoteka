<%--
  Created by IntelliJ IDEA.
  User: GermanV
  Date: 15.11.17
  Time: 14:29
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
    <title>${title}</title>
    <link rel="stylesheet" href="<c:url value="/resources/styles/main.css"/>">
    <script src="<c:url value="/resources/js/jquery-3.2.1.js"/>"></script>
    <script src="<c:url value="/resources/js/vue.js"/>"></script>
    <script src="<c:url value="/resources/js/main.js"/>"></script>
</head>
<t:header>
    <div id="loading" class="is-centered">
        <div class="spinner">
            <div class="double-bounce1"></div>
            <div class="double-bounce2"></div>
        </div>
    </div>
    <div class="container vue" style="display: none">

                        <section class="hero is-medium is-dark is-bold has-text-centered">
                            <div class="hero-body">
                                <div class="container">
                                    <h1 class="title">
                                        Profile
                                    </h1>
                                    <br>
                                    <h2 class="subtitle">
                                        <p>Name:</p>
                                        <p>Surname:</p>
                                        <p>E-mail:</p>
                                        <p>Login:</p>
                                        <p>Phone number:</p>
                                    </h2>
                                    <button class="button is-primary is-medium">Edit</button>
                                </div>
                            </div>
                        </section>

                    </div>
                </section>

    </div>
</t:header>
</html>

