<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<body>
<%boolean hasAccess = false; %>
<security:authorize access="hasAuthority('admin')">
    <% hasAccess = true; %>
</security:authorize>
<h2>
    <% if (hasAccess) {%>
        <a href="/admin/index">Admin Panel</a>
    <% } else { %>
        <a href="/index">Profile</a>
    <% } %>
</h2>
</body>
</html>
