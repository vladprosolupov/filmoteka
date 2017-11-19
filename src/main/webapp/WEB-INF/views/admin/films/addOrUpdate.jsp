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
    <div id="loading">
        <div class="spinner">
            <div class="double-bounce1"></div>
            <div class="double-bounce2"></div>
        </div>
    </div>
    <c:choose>
        <c:when test="${film.id != 0}">
            <form class="formForFilm formStyling" data-film="${film.id}" style="display: none">
                <div class="field">
                    <label class="label">Title </label>
                    <p class="control">
                        <input class="input" type="text" value="${film.title}" name="title"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Release Date </label>
                    <p class="control">
                        <input class="input" type="date" value="${film.releaseDate}" name="releaseDate">
                    </p>
                </div>
                <div class="field">
                    <label class="label">Language </label>
                    <p class="control">
                        <span class="select">
                        <select name="language">
                            <c:forEach items="${languages}" var="language">
                                <option value="${language.id}" ${language.id == film.languageByIdLanguage.id ? 'selected="selected"' : ''}
                                >${language.name}</option>
                            </c:forEach>
                        </select>
                        </span>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Length </label>
                    <p class="control">
                        <input class="input" type="number" value="${film.lenght}" name="lenght"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Description</label>
                    <p class="control">
                        <textarea class="textarea" type="text" name="description">${film.description}</textarea>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Slogan </label>
                    <p class="control">
                        <input class="input" type="text" value="${film.slogan}" name="slogan"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Rating </label>
                    <p class="control">
                        <input class="input" type="number" value="${film.rating}" name="rating"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Budget </label>
                    <p class="control">
                        <input class="input" type="text" value="${film.budget}" name="budget"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Cover </label>
                    <p class="control">
                        <input class="input" type="text" value="${film.cover}" name="cover"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Age </label>
                    <p class="control">
                        <input class="input" type="number" value="${film.age}" name="age">
                    </p>
                </div>
                <div class="field">
                    <label class="label">Film Category </label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Category name</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="categories">
                            <c:forEach items="${film.filmCategories}" var="category">
                                <tr data-category="${category.id}">
                                    <td>${category.name}</td>
                                    <td>
                                        <button type="button" class="deleteButton  button is-danger">Delete</button>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr class="newCategory" style="display: none">
                                <td>
                                    <span class="select">
                                        <select class="categoryOptions">
                                            <option v-for="category in categories" :value="category.id">{{category.name}}
                                            </option>
                                        </select>
                                    </span>
                                </td>
                                <td>
                                    <button type="button" v-on:click="saveCategory" class="button is-primary">Save
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    <button class="addCategory button is-primary" type="button">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Actors in film </label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Actor name and surname</th>
                                <th>Actor role</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="actors">
                            <c:forEach items="${film.filmActorsById}" var="actor">
                                <tr data-actor="${actor.id}">
                                    <td>${actor.actorByIdActor.firstName} ${actor.actorByIdActor.lastName}</td>
                                    <td>${actor.role}</td>
                                    <td>
                                        <button type="button" class="deleteButton  button is-danger">Delete</button>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr class="newActor" style="display: none">
                                <td>
                                    <span class="select">
                                        <select class="actorOptions">
                                            <option v-for="actor in actors" :value="actor.id">{{actor.firstName}}
                                                {{actor.lastName}}
                                            </option>
                                        </select>
                                    </span>
                                </td>
                                <td>
                                    <input type="text" class="actorRole input">
                                </td>
                                <td>
                                    <button type="button" v-on:click="saveActor" class="button is-primary">Save</button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" style="text-align: center">
                                    <button type="button" class="addActor button is-primary">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Film director</label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Director name and surname</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="directors">
                            <c:forEach items="${film.filmDirectors}" var="director">
                                <tr data-director="${director.id}">
                                    <td>${director.firstName} ${director.lastName}</td>
                                    <td>
                                        <button type="button" class="deleteButton  button is-danger">Delete</button>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr class="newDirector" style="display: none">
                                <td>
                                    <span class="select">
                                        <select class="directorOptions">
                                            <option v-for="director in directors" :value="director.id">
                                                {{director.firstName}}
                                                {{director.lastName}}
                                            </option>
                                        </select>
                                    </span>
                                </td>
                                <td>
                                    <button type="button" v-on:click="saveDirector" class="button is-primary">Save
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    <button class="addDirector button is-primary" type="button">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Film studio</label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Studio name</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="studios">
                            <c:forEach items="${film.filmStudios}" var="studio">
                                <tr data-studio="${studio.id}">
                                    <td>${studio.studioName}</td>
                                    <td>
                                        <button type="button" class="deleteButton button is-danger">Delete</button>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr class="newStudio" style="display: none">
                                <td>
                                    <span class="select">
                                        <select class="studioOptions">
                                            <option v-for="studio in studios" :value="studio.id">{{studio.studioName}}
                                            </option>
                                        </select>
                                    </span>
                                </td>
                                <td>
                                    <button type="button" v-on:click="saveStudio" class="button is-primary">Save
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    <button class="addStudio button is-primary" type="button">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Film countries</label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Country name</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="countries">
                            <c:forEach items="${film.filmCountries}" var="country">
                                <tr data-country="${country.id}">
                                    <td>${country.name}</td>
                                    <td>
                                        <button type="button" class="deleteButton button is-danger">Delete</button>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr class="newCountry" style="display: none">
                                <td>
                                    <span class="select">
                                        <select class="countryOptions">
                                            <option v-for="country in countries" :value="country.id">{{country.name}}
                                            </option>
                                        </select>
                                    </span>
                                </td>
                                <td>
                                    <button type="button" v-on:click="saveCountry" class="button is-primary">Save
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    <button class="addCountry button is-primary" type="button">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Film networks</label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Network name</th>
                                <th>Link to network</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="networks">
                            <c:forEach items="${film.filmNetworks}" var="network">
                                <tr class="network">
                                    <td data-network="${network.networkByIdNetwork.id}">${network.networkByIdNetwork.networkName}</td>
                                    <td data-networkLink>${network.link}</td>
                                    <td>
                                        <button type="button" class="deleteButton button is-danger">Delete</button>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr class="newNetwork" style="display: none">
                                <td>
                                    <span class="select">
                                        <select class="networkOptions">
                                            <option v-for="network in networks" :value="network.id">
                                                {{network.networkName}}
                                            </option>
                                        </select>
                                    </span>
                                </td>
                                <td>
                                    <input type="text" class="linkToNetwork input"/>
                                </td>
                                <td>
                                    <button type="button" v-on:click="saveNetwork" class="button is-primary">Save
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" style="text-align: center">
                                    <button type="button" class="addNetwork button is-primary">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Film awards</label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Award name</th>
                                <th>Year</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="awards">
                            <c:forEach items="${film.awardsById}" var="award">
                                <tr class="award">
                                    <td data-awardName>${award.awardName}</td>
                                    <td data-awardYear>${award.awardYear}</td>
                                    <td>
                                        <button type="button" class="deleteButton button is-danger">Delete</button>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr class="newAward" style="display: none">
                                <td><input type="text" class="awardName input"/></td>
                                <td><input type="number" class="awardYear input"/></td>
                                <td>
                                    <button type="button" v-on:click="saveAward" class="button is-primary">Save</button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" style="text-align: center">
                                    <button class="addAward button is-primary" type="button">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Screenshots</label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Link</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="screenshots">
                            <c:forEach items="${film.screenshotsById}" var="screenshot">
                                <tr class="screenshot">
                                    <td data-screenshot>${screenshot.link}</td>
                                    <td>
                                        <button type="button" class="deleteButton button is-danger">Delete</button>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr class="newScreenshot" style="display: none">
                                <td><input type="text" class="screenshotLink input"/></td>
                                <td>
                                    <button type="button" v-on:click="saveScreenshot" class="button is-primary">Save
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    <button class="addScreenshot button is-primary" type="button">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Trailers</label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Link</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="trailers">
                            <c:forEach items="${film.trailersById}" var="trailer">
                                <tr class="trailer">
                                    <td data-trailer>${trailer.link}</td>
                                    <td>
                                        <button type="button" class="deleteButton button is-danger">Delete</button>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr class="newTrailer" style="display: none">
                                <td><input type="text" class="trailerLink input"/></td>
                                <td>
                                    <button type="button" v-on:click="saveTrailer" class="button is-primary">Save
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    <button class="addTrailer button is-primary" type="button">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </form>
            <div class="field saveButton">
                <p class="control">
                    <button type="button" class="save button is-primary">Save film</button>
                </p>
            </div>
        </c:when>
        <c:otherwise>
            <form class="formForFilm formStyling" data-film="${film.id}" style="display: none">
                <div class="field">
                    <label class="label">Title </label>
                    <p class="control">
                        <input class="input" type="text" name="title"/>
                    </p>
                </div>

                <div class="field">
                    <label class="label">Release Date </label>
                    <p class="control">
                        <input class="input" type="date" name="releaseDate">
                    </p>
                </div>
                <div class="field">
                    <label class="label">Language </label>
                    <p class="control">
                        <span class="select">
                            <select name="language">
                                <c:forEach items="${languages}" var="language">
                                    <option value="${language.id}">${language.name}</option>
                                </c:forEach>
                            </select>
                        </span>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Length </label>
                    <p class="control">
                        <input class="input" type="number" name="lenght"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Description </label>
                    <p class="control">
                        <textarea class="textarea" name="description"></textarea>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Slogan </label>
                    <p class="control">
                        <input class="input" type="text" name="slogan"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Rating </label>
                    <p class="control">
                        <input class="input" type="number" name="rating"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Budget </label>
                    <p class="control">
                        <input class="input" type="text" name="budget"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Cover </label>
                    <p class="control">
                        <input class="input" type="text" name="cover"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Age </label>
                    <p class="control">
                        <input class="input" type="number" name="age">
                    </p>
                </div>
                <div class="field">
                    <label class="label">Film Category </label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Category name</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="categories">
                            <tr class="newCategory" style="display: none">
                                <td>
                                    <span class="select">
                                        <select class="categoryOptions">
                                            <option v-for="category in categories" :value="category.id">{{category.name}}
                                            </option>
                                        </select>
                                    </span>
                                </td>
                                <td>
                                    <button type="button" v-on:click="saveCategory" class="button is-primary">Save
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    <button class="addCategory button is-primary" type="button">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Actors in film </label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Actor name and surname</th>
                                <th>Actor role</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="actors">
                            <tr class="newActor" style="display: none">
                                <td>
                                    <span class="select">
                                        <select class="actorOptions">
                                            <option v-for="actor in actors" :value="actor.id">{{actor.firstName}}
                                                {{actor.lastName}}
                                            </option>
                                        </select>
                                    </span>
                                </td>
                                <td>
                                    <input type="text" class="actorRole input" required>
                                </td>
                                <td>
                                    <button type="button" v-on:click="saveActor" class="button is-primary">Save</button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" style="text-align: center">
                                    <button type="button" class="addActor button is-primary">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Film director</label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Director name and surname</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="directors">
                            <tr class="newDirector" style="display: none">
                                <td>
                                    <span class="select">
                                        <select class="directorOptions">
                                            <option v-for="director in directors" :value="director.id">
                                                {{director.firstName}}
                                                {{director.lastName}}
                                            </option>
                                        </select>
                                    </span>
                                </td>
                                <td>
                                    <button type="button" v-on:click="saveDirector" class="button is-primary">Save
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    <button class="addDirector button is-primary" type="button">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Film studio</label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Studio name</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="studios">
                            <tr class="newStudio" style="display: none">
                                <td>
                                    <span class="select">
                                        <select class="studioOptions">
                                            <option v-for="studio in studios" :value="studio.id">{{studio.studioName}}
                                            </option>
                                        </select>
                                    </span>
                                </td>
                                <td>
                                    <button type="button" v-on:click="saveStudio" class="button is-primary">Save
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    <button class="addStudio button is-primary" type="button">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Film countries</label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Country name</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="countries">
                            <tr class="newCountry" style="display: none">
                                <td>
                                    <span class="select">
                                        <select class="countryOptions">
                                            <option v-for="country in countries" :value="country.id">{{country.name}}
                                            </option>
                                        </select>
                                    </span>
                                </td>
                                <td>
                                    <button type="button" v-on:click="saveCountry" class="button is-primary">Save
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    <button class="addCountry button is-primary" type="button">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Film networks</label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Network name</th>
                                <th>Link to network</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="networks">
                            <tr class="newNetwork" style="display: none">
                                <td>
                                    <span class="select">
                                        <select class="networkOptions">
                                            <option v-for="network in networks" :value="network.id">
                                                {{network.networkName}}
                                            </option>
                                        </select>
                                    </span>
                                </td>
                                <td>
                                    <input type="text" class="linkToNetwork input"/>
                                </td>
                                <td>
                                    <button type="button" v-on:click="saveNetwork" class="button is-primary">Save
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" style="text-align: center">
                                    <button type="button" class="addNetwork button is-primary">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Film awards</label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Award name</th>
                                <th>Year</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="awards">
                            <tr class="newAward" style="display: none">
                                <td><input type="text" class="awardName input"/></td>
                                <td><input type="number" class="awardYear input"/></td>
                                <td>
                                    <button type="button" v-on:click="saveAward" class="button is-primary">Save</button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" style="text-align: center">
                                    <button class="addAward button is-primary" type="button">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Screenshots</label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Link</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="screenshots">
                            <tr class="newScreenshot" style="display: none">
                                <td><input type="text" class="screenshotLink input"/></td>
                                <td>
                                    <button type="button" v-on:click="saveScreenshot" class="button is-primary">Save
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    <button class="addScreenshot button is-primary" type="button">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="field">
                    <label class="label">Trailers</label>
                    <div class="control">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Link</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="trailers">
                            <tr class="newTrailer" style="display: none">
                                <td><input type="text" class="trailerLink input"/></td>
                                <td>
                                    <button type="button" v-on:click="saveTrailer" class="button is-primary">Save
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    <button class="addTrailer button is-primary" type="button">Add</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </form>
            <div class="field saveButton">
                <p class="control">
                    <button type="button" class="save button is-primary">Save film</button>
                </p>
            </div>
        </c:otherwise>
    </c:choose>
</t:wrapper>
