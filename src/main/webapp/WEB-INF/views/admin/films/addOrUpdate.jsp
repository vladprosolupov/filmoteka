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
                <span>Age </span> <input type="number" value="${film.age}" name="age"> <br/>
                <span>Film Category </span>
                <table>
                    <thead>
                    <tr>
                        <th>Category name</th>
                    </tr>
                    </thead>
                    <tbody class="categories">
                    <c:forEach items="${film.filmCategories}" var="category">
                        <tr data-category="${category.id}">
                            <td>${category.name}</td>
                            <td>
                                <button type="button">Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr class="categoryLoading" style="display: none">
                        <td colspan="2" style="text-align: center">Loading...</td>
                    </tr>
                    <tr class="newCategory" style="display: none">
                        <td>
                            <select class="categoryOptions">
                                <option v-for="category in categories" :value="category.id">{{category.name}}</option>
                            </select>
                        </td>
                        <td>
                            <button type="button" v-on:click="saveCategory">Save</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center">
                            <button class="addCategory" type="button">Add</button>
                        </td>
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
                    <tbody class="actors">
                    <c:forEach items="${film.filmActorsById}" var="actor">
                        <tr data-actor="${actor.id}">
                            <td>${actor.actorByIdActor.firstName} ${actor.actorByIdActor.lastName}</td>
                            <td>${actor.role}</td>
                            <td>
                                <button type="button">Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr class="actorLoading" style="display: none">
                        <td colspan="3" style="text-align: center">Loading...</td>
                    </tr>
                    <tr class="newActor" style="display: none">
                        <td>
                            <select class="actorOptions">
                                <option v-for="actor in actors" :value="actor.id">{{actor.firstName}}
                                    {{actor.lastName}}
                                </option>
                            </select>
                        </td>
                        <td>
                            <input type="text" class="actorRole" required>
                        </td>
                        <td>
                            <button type="button" v-on:click="saveActor">Save</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" style="text-align: center">
                            <button type="button" class="addActor">Add</button>
                        </td>
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
                    <tbody class="directors">
                    <c:forEach items="${film.filmDirectors}" var="director">
                        <tr data-director="${director.id}">
                            <td>${director.firstName} ${director.lastName}</td>
                            <td>
                                <button type="button">Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr class="directorLoading" style="display: none">
                        <td colspan="2" style="text-align: center">Loading...</td>
                    </tr>
                    <tr class="newDirector" style="display: none">
                        <td>
                            <select class="directorOptions">
                                <option v-for="director in directors" :value="director.id">{{director.firstName}}
                                    {{director.lastName}}
                                </option>
                            </select>
                        </td>
                        <td>
                            <button type="button" v-on:click="saveDirector">Save</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center">
                            <button class="addDirector" type="button">Add</button>
                        </td>
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
                    <tbody class="studios">
                    <c:forEach items="${film.filmStudios}" var="studio">
                        <tr data-studio="${studio.id}">
                            <td>${studio.studioName}</td>
                            <td>
                                <button type="button">Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr class="studioLoading" style="display: none">
                        <td colspan="2" style="text-align: center">Loading...</td>
                    </tr>
                    <tr class="newStudio" style="display: none">
                        <td>
                            <select class="studioOptions">
                                <option v-for="studio in studios" :value="studio.id">{{studio.studioName}}</option>
                            </select>
                        </td>
                        <td>
                            <button type="button" v-on:click="saveStudio">Save</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center">
                            <button class="addStudio" type="button">Add</button>
                        </td>
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
                    <tbody class="countries">
                    <c:forEach items="${film.filmCountries}" var="country">
                        <tr data-country="${country.id}">
                            <td>${country.name}</td>
                            <td>
                                <button type="button">Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr class="countryLoading" style="display: none">
                        <td colspan="2" style="text-align: center">Loading...</td>
                    </tr>
                    <tr class="newCountry" style="display: none">
                        <td>
                            <select class="countryOptions">
                                <option v-for="country in countries" :value="country.id">{{country.name}}</option>
                            </select>
                        </td>
                        <td>
                            <button type="button" v-on:click="saveCountry">Save</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center">
                            <button class="addCountry" type="button">Add</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <span>Film networks</span>
                <table>
                    <thead>
                    <tr>
                        <th>Network name</th>
                        <th>Link to network</th>
                    </tr>
                    </thead>
                    <tbody class="networks">
                    <c:forEach items="${film.filmNetworks}" var="network">
                        <tr data-network="${network.id}">
                            <td>${network.networkByIdNetwork.networkName}</td>
                            <td>${network.link}</td>
                            <td>
                                <button type="button">Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr class="networkLoading" style="display: none">
                        <td colspan="3" style="text-align: center">Loading...</td>
                    </tr>
                    <tr class="newNetwork" style="display: none">
                        <td>
                            <select class="networkOptions">
                                <option v-for="network in networks" :value="network.id">{{network.networkName}}</option>
                            </select>
                        </td>
                        <td>
                            <input type="text" class="linkToNetwork" required/>
                        </td>
                        <td>
                            <button type="button" v-on:click="saveNetwork">Save</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" style="text-align: center">
                            <button type="button" class="addNetwork">Add</button>
                        </td>
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
                    <tbody class="awards">
                    <c:forEach items="${film.awardsById}" var="award">
                        <tr class="award">
                            <td data-awardName>${award.awardName}</td>
                            <td data-awardYear>${award.awardYear}</td>
                            <td>
                                <button type="button">Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr class="newAward" style="display: none">
                        <td><input type="text" class="awardName"/></td>
                        <td><input type="number" class="awardYear"/></td>
                        <td>
                            <button type="button" v-on:click="saveAward">Save</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" style="text-align: center">
                            <button class="addAward" type="button">Add</button>
                        </td>
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
                    <tbody class="screenshots">
                    <c:forEach items="${film.screenshotsById}" var="screenshot">
                        <tr class="screenshot">
                            <td data-screenshot>${screenshot.link}</td>
                            <td>
                                <button type="button">Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr class="newScreenshot" style="display: none">
                        <td><input type="text" class="screenshotLink"/></td>
                        <td>
                            <button type="button" v-on:click="saveScreenshot">Save</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center">
                            <button class="addScreenshot" type="button">Add</button>
                        </td>
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
                    <tbody class="trailers">
                    <c:forEach items="${film.trailersById}" var="trailer">
                        <tr class="trailer">
                            <td data-trailer>${trailer.link}</td>
                            <td>
                                <button type="button">Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr class="newTrailer" style="display: none">
                        <td><input type="text" class="trailerLink"/></td>
                        <td>
                            <button type="button" v-on:click="saveTrailer">Save</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center">
                            <button class="addTrailer" type="button">Add</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <br/>
            </form>
            <button type="button" class="save">Save film</button>
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
                <span>Age </span> <input type="number" name="age"> <br/>
                <span>Film Category </span>
                <table>
                    <thead>
                    <tr>
                        <th>Category name</th>
                    </tr>
                    </thead>
                    <tbody class="categories">
                    <tr class="categoryLoading" style="display: none">
                        <td colspan="2" style="text-align: center">Loading...</td>
                    </tr>
                    <tr class="newCategory" style="display: none">
                        <td>
                            <select class="categoryOptions">
                                <option v-for="category in categories" :value="category.id">{{category.name}}</option>
                            </select>
                        </td>
                        <td>
                            <button type="button" v-on:click="saveCategory">Save</button>
                        </td>
                    </tr>
                    <tr class="addButton">
                        <td colspan="2" style="text-align: center">
                            <button class="addCategory" type="button">Add</button>
                        </td>
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
                    <tbody class="actors">
                    <tr class="actorLoading" style="display: none">
                        <td colspan="3" style="text-align: center">Loading...</td>
                    </tr>
                    <tr class="newActor" style="display: none">
                        <td>
                            <select class="actorOptions">
                                <option v-for="actor in actors" :value="actor.id">{{actor.firstName}}
                                    {{actor.lastName}}
                                </option>
                            </select>
                        </td>
                        <td>
                            <input type="text" class="actorRole" required>
                        </td>
                        <td>
                            <button type="button" v-on:click="saveActor">Save</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" style="text-align: center">
                            <button type="button" class="addActor">Add</button>
                        </td>
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
                        <td colspan="2" style="text-align: center">
                            <button type="button">Add</button>
                        </td>
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
                        <td colspan="2" style="text-align: center">
                            <button type="button">Add</button>
                        </td>
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
                        <td colspan="2" style="text-align: center">
                            <button type="button">Add</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <br/>
                <span>Film networks</span>
                <table>
                    <thead>
                    <tr>
                        <th>Network name</th>
                        <th>Link to network</th>
                    </tr>
                    </thead>
                    <tbody class="networks">
                    <tr class="networkLoading" style="display: none">
                        <td colspan="3" style="text-align: center">Loading...</td>
                    </tr>
                    <tr class="newNetwork" style="display: none">
                        <td>
                            <select class="networkOptions">
                                <option v-for="network in networks" :value="network.id">{{network.networkName}}</option>
                            </select>
                        </td>
                        <td>
                            <input type="text" class="linkToNetwork" required/>
                        </td>
                        <td>
                            <button type="button" v-on:click="saveNetwork">Save</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" style="text-align: center">
                            <button type="button" class="addNetwork">Add</button>
                        </td>
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
                    <tbody class="awards">
                    <tr class="newAward" style="display: none">
                        <td><input type="text" class="awardName"/></td>
                        <td><input type="number" class="awardYear"/></td>
                        <td>
                            <button type="button" v-on:click="saveAward">Save</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" style="text-align: center">
                            <button class="addAward" type="button">Add</button>
                        </td>
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
                    <tbody class="screenshots">
                    <tr class="newScreenshot" style="display: none">
                        <td><input type="text" class="screenshotLink"/></td>
                        <td>
                            <button type="button" v-on:click="saveScreenshot">Save</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center">
                            <button class="addScreenshot" type="button">Add</button>
                        </td>
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
                    <tbody class="trailers">
                    <tr class="newTrailer" style="display: none">
                        <td><input type="text" class="trailerLink"/></td>
                        <td>
                            <button type="button" v-on:click="saveTrailer">Save</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center">
                            <button class="addTrailer" type="button">Add</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <br/>
            </form>
            <button type="button" class="save">Save film</button>
        </c:otherwise>
    </c:choose>
</t:wrapper>
