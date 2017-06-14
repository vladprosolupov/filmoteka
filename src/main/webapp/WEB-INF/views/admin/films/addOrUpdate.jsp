<%@ taglib prefix="t" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: vladyslavprosolupov
  Date: 14.06.17
  Time: 0:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<t:wrapper>
    <c:choose>
        <c:when test="${film.id != 0}">
            <h2>${film.title}</h2>
        </c:when>
        <c:otherwise>
            <h2>new film</h2>
        </c:otherwise>
    </c:choose>
</t:wrapper>
