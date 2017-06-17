<%@ taglib prefix="t" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Rostyk
  Date: 17.06.2017
  Time: 19:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<t:wrapper>
    <c:choose>
        <c:when test="${actor.id != 0}">
            <form>
                <span>Name</span> <input type="text" value="${actor.firstName}" name="name">
                <span>Surname</span> <input type="text" value="${actor.lastName}" name="surname">
                <span>Birthday</span> <input type="text" value="${actor.birthdate}" name="birthdate">
                <span>Country</span> <input type="text" value="${actor.countryByIdCountry.name}" name="country">
            </form>
        </c:when>
        <c:otherwise>
            <form>
                <span>Name</span> <input type="text" name="name">
                <span>Surname</span> <input type="text" name="surname">
                <span>Birthday</span> <input type="text" name="birthdate">
                <span>Country</span> <input type="text" name="country">
            </form>
        </c:otherwise>
    </c:choose>

</t:wrapper>
