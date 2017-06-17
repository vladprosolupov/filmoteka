<%@ taglib prefix="t" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Rostyk
  Date: 17.06.2017
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<t:wrapper>
    <c:choose>
        <c:when test="${director.id != 0}">
            <form>
                <span>Name</span> <input type="text" value="${director.firstName}" name="name">
                <span>Surname</span> <input type="text" value="${director.lastName}" name="surname">
                <span>Country</span> <input type="text" value="
                    ${director.countryByIdCountry.name}" name="country"/>
            </form>
        </c:when>
        <c:otherwise>
            <form>
                <span>Name</span> <input type="text" name="name">
                <span>Surname</span> <input type="text" name="surname">
                <span>Country</span> <input type="text" name="country"/>
            </form>
        </c:otherwise>
    </c:choose>

</t:wrapper>
