<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Admin Panel wrapper tag" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Admin Panel - Filmoteka</title>
    <script src="<c:url value="/resources/js/jquery-3.2.1.js"/>"></script>
    <script src="<c:url value="/resources/js/vue.js"/>"></script>
    <script src="<c:url value="/resources/js/admin.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/styles/bulma.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/styles/admin.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/styles/main.css"/>">

    <!-- Including CSRF token to the header -->
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

</head>
<body>
<section class="hero background_for_login is-fullheight is-bold white-text">
    <div class="hero-head">
        <div class="container">
            <nav class="nav">
                <div class="nav-center nav-menu">
                    <a class="nav-item" href="/admin/films">Films</a>
                    <a class="nav-item" href="/admin/actors">Actors</a>
                    <a class="nav-item" href="/admin/directors">Directors</a>
                    <a class="nav-item" href="/admin/categories">Categories</a>
                    <a class="nav-item" href="/admin/networks">Networks</a>
                    <a class="nav-item" href="/admin/studios">Studios</a>
                </div>
            </nav>
        </div>
    </div>
    <div class="hero-body">
        <div class="container has-text-centered">
            <div class="columns is-vcentered">
                <div class="column is-6 is-offset-3">
                    <jsp:doBody/>
                </div>
            </div>
        </div>
    </div>
</section>

</body>
</html>