<%@ taglib prefix="t" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
            <form class="formForFilm" name="filmToSave" action="${pageContext.request.contextPath}/film/save" method="post">
                <span>Title </span> <input type="text" value="${film.title}" name="title"/><br/>
                <span>Release Date </span> <input type="date" value="${film.releaseDate}" name="releaseDate"><br/>
                <span>Language </span>
                <select name="languageByIdLanguage">
                    <c:forEach items="${languages}" var="language">
                       <option value="${language}" ${language.id == film.languageByIdLanguage.id ? 'selected="selected"' : ''}
                       >${language.name}</option>
                    </c:forEach>
                </select> <br />
                <span>Length </span> <input type="number" value="${film.lenght}" name="lenght"/> <br/>
                <span>Description </span> <input type="text" value="${film.description}" name="description"/> <br />
                <span>Slogan </span> <input type="text" value="${film.slogan}" name="slogan"/> <br/>
                <span>Rating </span> <input type="number" value="${film.rating}" name="rating"/> <br/>
                <span>Budget </span> <input type="text" value="${film.budget}" name="budget"/> <br/>
                <span>Cover </span> <input type="text" value="${film.cover}" name="cover"/> <br/>
                <span>Film Category </span>
                <table>
                    <thead>
                        <tr>
                            <th>Category name</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.filmCategories}" var="category">
                        <tr>
                            <td>${category.name}</td>
                            <td>Delete</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table> <br/>
                <span>Actors in film </span>
                <table>
                    <thead>
                    <tr>
                        <th>Actor name and surname</th>
                        <th>Actor role</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.filmActorsById}" var="actor">
                        <tr>
                            <td>${actor.actorByIdActor.firstName} ${actor.actorByIdActor.lastName}</td>
                            <td>${actor.role}</td>
                            <td>Delete</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table> <br/>
                <span>Film director</span>
                <table>
                    <thead>
                    <tr>
                        <th>Director name and surname</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.filmDirectors}" var="director">
                        <tr>
                            <td>${director.firstName} ${director.lastName}</td>
                            <td>Delete</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table> <br/>
                <span>Film studio</span>
                <table>
                    <thead>
                    <tr>
                        <th>Studio name</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.filmStudios}" var="studio">
                        <tr>
                            <td>${studio.studioName}</td>
                            <td>Delete</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table> <br/>
                <span>Film countries</span>
                <table>
                    <thead>
                    <tr>
                        <th>Country name</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.filmCountries}" var="country">
                        <tr>
                            <td>${country.name}</td>
                            <td>Delete</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table> <br/>
                <span>Film networks</span>
                <table>
                    <thead>
                    <tr>
                        <th>Network logo</th>
                        <th>Network name</th>
                        <th>Link to network</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.filmNetworks}" var="network">
                        <tr>
                            <td>${network.networkByIdNetwork.networkLogo}</td>
                            <td>${network.networkByIdNetwork.networkName}</td>
                            <td>${network.link}</td>
                            <td>Delete</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table> <br/>
                <input type="submit" value="Save film" />
                <sec:csrfInput/>
            </form>

        </c:when>
        <c:otherwise>
            <h2>new film</h2>
        </c:otherwise>
    </c:choose>
</t:wrapper>
