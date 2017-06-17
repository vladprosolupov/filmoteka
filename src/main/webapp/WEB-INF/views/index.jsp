<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Index</title>
    <link rel="stylesheet" href="<c:url value="/resources/styles/bulma.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/styles/main.css"/>">
</head>
<body>
<%boolean hasAccess = false; %>
<security:authorize access="hasAuthority('admin')">
    <% hasAccess = true; %>
</security:authorize>
<section class="hero background_for_login is-fullheight is-bold">
    <div class="hero-body">
        <div class="container has-text-centered">
            <h1 class="title">
                <% if (hasAccess) {%>
                <a href="/admin/index" class="white-text">Admin Panel</a>
                <% } else { %>
                <a href="/index">Profile</a>
                <% } %>
            </h1>
        </div>
    </div>
</section>
</body>
</html>
