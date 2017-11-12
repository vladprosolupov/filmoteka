<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@tag description="Main header tag" pageEncoding="UTF-8" %>
<%int hasAccess = 0; %>
<security:authorize access="hasAnyAuthority('user')">
    <% hasAccess = 1; %>
</security:authorize>
<security:authorize access="hasAuthority('admin')">
    <% hasAccess = 2; %>
</security:authorize>
<body>
    <section class="hero background_for_client is-fullheight is-bold white-text">
        <div class="hero-head">
            <div class="container">
                <nav class="breadcrumb is-centered">
                    <div class="navbar">
                        <% if (hasAccess == 2) {%>
                        <div class="navbar" id="logout_id" action="${logoutUrl}"
                              method="post">

                            <a class="navbar-item" href="/">Home</a>
                            <a class="navbar-item" href="/">New</a>
                            <a class="navbar-item" href="/">You Should Watch</a>
                            <a class="navbar-item" href="/">About</a>
                            <a class="navbar-item" href="/admin/">Admin Panel</a>

                        </div>
                        <% } else if(hasAccess == 0) { %>
                        <form class="navbar" id="logout_id" action="${logoutUrl}"
                              method="post">

                            <a class="navbar-item" href="/">Home</a>
                            <a class="navbar-item" href="/">New</a>
                            <a class="navbar-item" href="/">You Should Watch</a>
                            <a class="navbar-item" href="/">About</a>
                            <a class="navbar-item" href="/login">Log in</a>

                        </form>
                        <% } else { %>
                        <form class="navbar" id="logout_id" action="${logoutUrl}"
                              method="post">

                            <a class="navbar-item" href="/">Home</a>
                            <a class="navbar-item" href="/">New</a>
                            <a class="navbar-item" href="/">You Should Watch</a>
                            <a class="navbar-item" href="/">About</a>
                            <a class="navbar-item" href="/">Profile</a>

                        </form>
                        <% } %>

                    </div>
                </nav>
            </div>
        </div>
        <jsp:doBody/>
    </section>
</body>