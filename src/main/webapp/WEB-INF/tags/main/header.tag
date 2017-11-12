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
<c:url var="logoutUrl" value="/do_logout"/>
<body>
    <section class="hero background_for_client is-fullheight is-bold white-text">
        <div class="hero-head">
            <div class="container">
                <nav class="is-centered">
                        <% if (hasAccess == 2) {%>
                        <form class="navbar-menu" id="logout_id" action="${logoutUrl}"
                              method="post">

                            <a class="navbar-item" href="/">Home</a>
                            <a class="navbar-item" href="/">New</a>
                            <a class="navbar-item" href="/">You Should Watch</a>
                            <a class="navbar-item" href="/">About</a>
                            <div class="navbar-item has-dropdown is-hoverable">
                                <a class="navbar-link">Admin Panel</a>
                                <div class="navbar-dropdown is-boxed">
                                    <input type="hidden"
                                           name="${_csrf.parameterName}"
                                           value="${_csrf.token}"/>
                                    <a class="navbar-item droppeddown" href="/admin/films">Films</a>
                                    <a class="navbar-item droppeddown" href="/admin/actors">Actors</a>
                                    <a class="navbar-item droppeddown" href="/admin/directors">Directors</a>
                                    <a class="navbar-item droppeddown" href="/admin/categories">Categories</a>
                                    <a class="navbar-item droppeddown" href="/admin/networks">Networks</a>
                                    <a class="navbar-item droppeddown" href="/admin/studios">Studios</a>
                                    <hr class="navbar-divider"/>
                                    <a class="navbar-item droppeddown" href="javascript:{}" onclick="document.getElementById('logout_id').submit();">Log
                                        out</a>
                                </div>
                            </div>


                        </form>
                        <% } else if(hasAccess == 0) { %>
                        <div class="navbar">

                            <a class="navbar-item" href="/">Home</a>
                            <a class="navbar-item" href="/">New</a>
                            <a class="navbar-item" href="/">You Should Watch</a>
                            <a class="navbar-item" href="/">About</a>
                            <a class="navbar-item" href="/login">Log in</a>

                        </div>
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