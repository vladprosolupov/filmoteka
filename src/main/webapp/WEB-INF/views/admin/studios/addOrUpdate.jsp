<%@ taglib prefix="t" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Rostyk
  Date: 17.06.2017
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<t:wrapper>
    <c:choose>
        <c:when test="${studio.id != 0}">
            <form>
                <span>Name</span> <input type="text" value="${studio.studioName}" name="name">
            </form>
        </c:when>
        <c:otherwise>
            <form>
                <span>Name</span> <input type="text" name="name">
            </form>
        </c:otherwise>
    </c:choose>

</t:wrapper>
