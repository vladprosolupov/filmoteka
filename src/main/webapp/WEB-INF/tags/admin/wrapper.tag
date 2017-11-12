<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Admin Panel wrapper tag" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Admin Panel - Filmoteka</title>
    <script src="<c:url value="/resources/js/jquery-3.2.1.js"/>"></script>
    <script src="<c:url value="/resources/js/vue.js"/>"></script>
    <script src="<c:url value="/resources/js/admin.js"/>"></script>
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
            <nav class="breadcrumb is-centered">

                <c:url var="logoutUrl" value="/do_logout"/>
                <form class="navbar" id="logout_id" action="${logoutUrl}"
                      method="post">

                    <a class="navbar-item" href="/index">Home</a>
                    <a class="navbar-item" href="/admin/films">Films</a>
                    <a class="navbar-item" href="/admin/actors">Actors</a>
                    <a class="navbar-item" href="/admin/directors">Directors</a>
                    <a class="navbar-item" href="/admin/categories">Categories</a>
                    <a class="navbar-item" href="/admin/networks">Networks</a>
                    <a class="navbar-item" href="/admin/studios">Studios</a>

                    <%--
                        This form is used for log out
                        Because Spring Security by default is using csrf token,
                        So to send request to the server to log out
                        We have submit it using post method and adding additional parameters
                    --%>


                    <input type="hidden"
                           name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>
                    <a class="navbar-item" href="javascript:{}" onclick="document.getElementById('logout_id').submit();">Log
                        out</a>

                </form>

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