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
            <form class="formForFilm">
                <span>Title </span> <input type="text" value="${film.title}" name="title"/><br/>
                <span>Release Date </span> <input type="date" value="${film.releaseDate}" name="releaseDate"><br/>
                <span>Language </span>
                <select name="language">
                    <c:forEach items="${languages}" var="language">
                       <option value="${language.id}" ${language.id == film.languageByIdLanguage.id ? 'selected="selected"' : ''}
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
                <table id="filmCategories">
                    <thead>
                        <tr>
                            <th>Category name</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.filmCategories}" var="category">
                        <tr>
                            <td hidden class="filmCategory">${category.id}</td>
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
                <table id="filmActors">
                    <thead>
                    <tr>
                        <th>Actor name and surname</th>
                        <th>Actor role</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.filmActorsById}" var="actor">
                        <tr>
                            <td hidden class="filmActor">${actor.actorByIdActor.id}</td>
                            <td>${actor.actorByIdActor.firstName} ${actor.actorByIdActor.lastName}</td>
                            <td class="filmActorRole">${actor.role}</td>
                            <td>Delete</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table> <br/>
                <span>Film director</span>
                <table id="filmDirectors">
                    <thead>
                    <tr>
                        <th>Director name and surname</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.filmDirectors}" var="director">
                        <tr>
                            <td hidden class="filmDirector">${director.id}</td>
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
                <table id="filmStudious">
                    <thead>
                    <tr>
                        <th>Studio name</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.filmStudios}" var="studio">
                        <tr>
                            <td hidden class="filmStudio">${studio.id}</td>
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
                <table id="filmCountries">
                    <thead>
                    <tr>
                        <th>Country name</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.filmCountries}" var="country">
                        <tr>
                            <td hidden class="filmCountries">${country.id}</td>
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
                <table id="filmNetworks">
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
                            <td hidden class="filmNetworks">${network.id}</td>
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
                <input type="button" name="ignore" value="Save film" onclick="save()" />

            </form>

        </c:when>
        <c:otherwise>
            <h2>new film</h2>
        </c:otherwise>
    </c:choose>
</t:wrapper>
