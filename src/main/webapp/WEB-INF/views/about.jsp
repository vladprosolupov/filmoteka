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
    <title>About</title>
    <!-- Including CSRF token to the header -->
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

</head>
<t:header>
    <section class="hero is-white is-small is-bold has-text-centered">
        <div class="hero-body columns">
            <div class="container columns">
                <div class="column"></div>
                <div class="column">
                    <br>
                <h1 class="title">
                    Filmoteka
                </h1>
                <br>
                <h2 class="subtitle">
                    <p>This is our diploma project</p>
                    <br>
                    <p>This is a site where user can find popular films,films which he like,find films which he should like</p>
                    <br>

                    <b><p></p>Filmoteka</b> - biggest european web-site for watching information about films.Every month we have more then 100 million users which wants to be navigated in cinema industry and add to bookmarks films which they want to see in their future. Our service gives you possibility to be modern person, to know about new films and to know about films which will be in our hearts forever! You can open that site via every device because it is optimized! <b>Filmoteka</b> is a really friendly web-site which wants you to feel comfortable and find films which you will like, out artificial intelligence will help you with that!</p>
                    <br>
                    <br>
                    <p>Our group:</p>
                    <br>
                    <p>Vladyslav Prosolupov</p>
                    <p>Rostyslav Tryndyak</p>
                    <p>German Varanytsya</p>
                </h2>
                </div>
                <div class="column"></div>
            </div>
        </div>
    </section>
</t:header>
</html>

