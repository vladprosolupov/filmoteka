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
    <div id="loading">Loading...</div>
    <c:choose>
        <c:when test="${film.id != 0}">
            <form class="formForFilm" data-film="${film.id}" style="display: none">
                <span>Title </span> <input type="text" value="${film.title}" name="title"/><br/>
                <span>Release Date </span> <input type="date" value="${film.releaseDate}" name="releaseDate"><br/>
                <span>Language </span>
                <select name="language">
                    <c:forEach items="${languages}" var="language">
                        <option value="${language.id}" ${language.id == film.languageByIdLanguage.id ? 'selected="selected"' : ''}
                        >${language.name}</option>
                    </c:forEach>
                </select> <br/>
                <span>Length </span> <input type="number" value="${film.lenght}" name="lenght"/> <br/>
                <span>Description </span> <input type="text" value="${film.description}" name="description"/> <br/>
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
                        <tr data-category="${category.id}">
                            <td>${category.name}</td>
                            <td>Delete</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
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
                        <tr data-actor="${actor.id}">
                            <td>${actor.actorByIdActor.firstName} ${actor.actorByIdActor.lastName}</td>
                            <td>${actor.role}</td>
                            <td>Delete</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="3" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <span>Film director</span>
                <table>
                    <thead>
                    <tr>
                        <th>Director name and surname</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.filmDirectors}" var="director">
                        <tr data-director="${director.id}">
                            <td>${director.firstName} ${director.lastName}</td>
                            <td>Delete</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <span>Film studio</span>
                <table>
                    <thead>
                    <tr>
                        <th>Studio name</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.filmStudios}" var="studio">
                        <tr data-studio="${studio.id}">
                            <td>${studio.studioName}</td>
                            <td>Delete</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <span>Film countries</span>
                <table>
                    <thead>
                    <tr>
                        <th>Country name</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.filmCountries}" var="country">
                        <tr data-country="${country.id}">
                            <td>${country.name}</td>
                            <td>Delete</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
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
                        <tr data-network="${network.id}">
                            <td>${network.networkByIdNetwork.networkLogo}</td>
                            <td>${network.networkByIdNetwork.networkName}</td>
                            <td>${network.link}</td>
                            <td>Delete</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="4" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <span>Film awards</span>
                <table>
                    <thead>
                    <tr>
                        <th>Award name</th>
                        <th>Year</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.awardsById}" var="award">
                        <tr data-award="${award.id}">
                            <td>${award.awardName}</td>
                            <td>${award.awardYear}</td>
                            <td>Delete</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="3" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <span>Screenshots</span>
                <table>
                    <thead>
                    <tr>
                        <th>Link</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.screenshotsById}" var="screenshot">
                        <tr data-screenshot="${screenshot.id}">
                            <td>${screenshot.link}</td>
                            <td>Delete</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <span>Trailers</span>
                <table>
                    <thead>
                    <tr>
                        <th>Link</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${film.trailersById}" var="trailer">
                        <tr data-trailer="${trailer.id}">
                            <td>${trailer.link}</td>
                            <td>Delete</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <input type="button" class="save" name="ignore" value="Save film"/>
            </form>
        </c:when>
        <c:otherwise>
            <form class="formForFilm" data-film="${film.id}" style="display: none">
                <span>Title </span> <input type="text" name="title"/><br/>
                <span>Release Date </span> <input type="date" name="releaseDate"><br/>
                <span>Language </span>
                <select name="language">
                    <c:forEach items="${languages}" var="language">
                        <option value="${language.id}">${language.name}</option>
                    </c:forEach>
                </select> <br/>
                <span>Length </span> <input type="number" name="lenght"/> <br/>
                <span>Description </span> <input type="text" name="description"/> <br/>
                <span>Slogan </span> <input type="text" name="slogan"/> <br/>
                <span>Rating </span> <input type="number" name="rating"/> <br/>
                <span>Budget </span> <input type="text" name="budget"/> <br/>
                <span>Cover </span> <input type="text" name="cover"/> <br/>
                <span>Film Category </span>
                <table>
                    <thead>
                    <tr>
                        <th>Category name</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td colspan="2" style="text-align: center" class="addCategory">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <span>Actors in film </span>
                <table>
                    <thead>
                    <tr>
                        <th>Actor name and surname</th>
                        <th>Actor role</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td colspan="3" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <span>Film director</span>
                <table>
                    <thead>
                    <tr>
                        <th>Director name and surname</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <span>Film studio</span>
                <table>
                    <thead>
                    <tr>
                        <th>Studio name</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <span>Film countries</span>
                <table>
                    <thead>
                    <tr>
                        <th>Country name</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
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
                    <tr>
                        <td colspan="4" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <span>Film awards</span>
                <table>
                    <thead>
                    <tr>
                        <th>Award name</th>
                        <th>Year</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td colspan="3" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <span>Screenshots</span>
                <table>
                    <thead>
                    <tr>
                        <th>Link</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <span>Trailers</span>
                <table>
                    <thead>
                    <tr>
                        <th>Link</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td colspan="2" style="text-align: center">Add</td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <input type="button" class="save" name="ignore" value="Save film"/>
            </form>
        </c:otherwise>
    </c:choose>
</t:wrapper>
