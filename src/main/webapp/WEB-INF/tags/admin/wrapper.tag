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
                    <a class="nav-item" href="/admin/">Home</a>
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
                    <div class="success modal">
                        <div class="modal-background"></div>
                        <div class="modal-content">
                            <div class="box">
                                <article class="media">
                                    <div class="media-left">
                                        <figure class="image is-64x64">
                                            <img src="/resources/images/success.png" alt="success"/>
                                        </figure>
                                    </div>
                                    <div class="media-content">
                                        <div class="content is-vcentered" style="text-align: center">
                                               <h1 class="is-centered">Success!</h1>
                                        </div>
                                    </div>
                                </article>
                            </div>
                        </div>
                    </div>
                    <div class="fail modal">
                        <div class="modal-background"></div>
                        <div class="modal-content">
                            <div class="box">
                                <article class="media">
                                    <div class="media-left">
                                        <figure class="image is-64x64">
                                            <img src="/resources/images/fail.png" alt="fail"/>
                                        </figure>
                                    </div>
                                    <div class="media-content">
                                        <div class="content" style="text-align: center">
                                            <h1 class="is-centered">Error during processing. Please try later!</h1>
                                        </div>
                                    </div>
                                </article>
                            </div>
                        </div>
                    </div>
                    <jsp:doBody/>
                </div>
            </div>
        </div>
    </div>
</section>

</body>
</html>