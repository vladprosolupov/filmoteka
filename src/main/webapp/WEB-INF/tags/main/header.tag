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
                        <form class="navbar" id="logout_id" action="${logoutUrl}"
                              method="post">

                            <a class="navbar-item" href="/">Home</a>
                            <a class="navbar-item" href="/">New</a>
                            <a class="navbar-item" href="/">You Should Watch</a>
                            <a class="navbar-item" href="/">About</a>
                            <div class="navbar-item has-dropdown is-hoverable">
                                <a class="navbar-link" href="/admin/">Admin Panel</a>
                                <div class="navbar-dropdown is-boxed">
                                    <a class="navbar-item" href="/admin/films">Films</a>
                                    <a class="navbar-item" href="/admin/actors">Actors</a>
                                    <a class="navbar-item" href="/admin/directors">Directors</a>
                                    <a class="navbar-item" href="/admin/categories">Categories</a>
                                    <a class="navbar-item" href="/admin/networks">Networks</a>
                                    <a class="navbar-item" href="/admin/studios">Studios</a>

                                    <hr class="navbar-divider"/>

                                    <input type="hidden"
                                           name="${_csrf.parameterName}"
                                           value="${_csrf.token}"/>
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
                            <div class="navbar-item has-dropdown is-hoverable">
                                <a class="navbar-link" href="/">Profile</a>
                                <div class="navbar-dropdown is-boxed">
                                    <a class="navbar-item" href="/">Films you've liked</a>
                                    <a class="navbar-item" href="/">Bookmarks</a>

                                    <hr class="navbar-divider"/>

                                    <input type="hidden"
                                           name="${_csrf.parameterName}"
                                           value="${_csrf.token}"/>
                                    <a class="navbar-item droppeddown" href="javascript:{}" onclick="document.getElementById('logout_id').submit();">Log
                                        out</a>
                                </div>
                            </div>

                        </form>
                        <% } %>
                    </div>
                </nav>
            </div>
        </div>
        <jsp:doBody/>
    </section>
</body>