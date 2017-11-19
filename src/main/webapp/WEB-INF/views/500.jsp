<%@ taglib prefix="t" tagdir="/WEB-INF/tags/main" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: vladprosolupov
  Date: 15.11.2017
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Server error</title>
    <link rel="stylesheet" href="<c:url value="/resources/styles/main.css"/>">
</head>
<t:header>
    <article id="errorBox" class="message is-danger">
        <div class="message-body">
            Sorry, an <strong>error</strong> occurred processing this request!
        </div>
    </article>
</t:header>
</html>
