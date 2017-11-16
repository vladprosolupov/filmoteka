<%--
  Created by IntelliJ IDEA.
  User: GermanV
  Date: 15.11.17
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/main" %>

<%int hasAccess = 0; %>
<security:authorize access="hasAnyAuthority('user')">
    <% hasAccess = 1; %>
</security:authorize>
<security:authorize access="hasAuthority('admin')">
    <% hasAccess = 2; %>
</security:authorize>

<c:set var="comment">
    <% if (hasAccess != 0) { %>
    <article class="media">
        <figure class="media-left">
            <p class="image is-64x64">
                <img src="https://bulma.io/images/placeholders/128x128.png">
            </p>
        </figure>
        <div class="media-content">
            <div class="field">
                <p class="control">
                    <textarea class="textarea" placeholder="Add a comment..."></textarea>
                </p>
            </div>
            <nav class="level">
                <div class="level-left">
                    <div class="level-item">
                        <a class="button is-dark">Submit</a>
                    </div>
                </div>
                <div class="level-right">
                    <div class="level-item">
                        <label class="checkbox">
                            <input type="checkbox"> Press enter to submit
                        </label>
                    </div>
                </div>
            </nav>
        </div>
    </article>
    <%} else {%>
    <article class="media">
        <div class="media-content">
            <div class="field">
                <p class="control">
                    <span>Please login to write a comment</span>
                    <a href="/login">Login</a>
                </p>
            </div>
        </div>
    </article>
    <% } %>
</c:set>

<html>
<head>
    <title>${title}</title>
    <link rel="stylesheet" href="<c:url value="/resources/styles/main.css"/>">
    <script src="<c:url value="/resources/js/jquery-3.2.1.js"/>"></script>
    <script src="<c:url value="/resources/js/vue.js"/>"></script>
    <script src="<c:url value="/resources/js/main.js"/>"></script>
</head>
<t:header>
    <div id="loading" class="is-centered">
        <div class="spinner">
            <div class="double-bounce1"></div>
            <div class="double-bounce2"></div>
        </div>
    </div>
    <div class="container vue" style="display: none">
        <div class="columns">
            <div class="column is-2">
                <aside class="menu ">
                    <p class="menu-label">
                        Categories
                    </p>
                    <ul class="menu-list categories">
                        <li v-for="category in categories"><a v-on:click="openCategory(category.id)">{{category.name}}</a></li>
                    </ul>
                    <p class="menu-label">
                        Administration
                    </p>
                    <ul class="menu-list">
                        <li><a>Team Settings</a></li>
                        <li>
                            <a>Manage Your Team</a>
                            <ul>
                                <li><a>Members</a></li>
                                <li><a>Plugins</a></li>
                                <li><a>Add a member</a></li>
                            </ul>
                        </li>
                        <li><a>Invitations</a></li>
                        <li><a>Cloud Storage Environment Settings</a></li>
                        <li><a>Authentication</a></li>
                    </ul>
                    <p class="menu-label">
                        Transactions
                    </p>
                    <ul class="menu-list">
                        <li><a>Payments</a></li>
                        <li><a>Transfers</a></li>
                        <li><a>Balance</a></li>
                    </ul>
                </aside>
            </div>
            <div class="column is-9 info">
                <section class="hero">
                    <div class="hero-body">


                        <article class="media">
                            <figure class="media-left">
                                <p class="image is-percentage">
                                    <img v-bind:src='film.cover'>
                                </p>
                            </figure>
                            <div class="media-content">
                                <div class="content">
                                    <h3><b>{{film.title}}</b></h3>
                                    <hr>
                                    <p><h6>Rating: {{film.rating}}</h6></p>
                                    <p><h6>Length: {{calculateTime(film.lenght)}}</h6></p>
                                    <p><h6>Date: {{film.releaseDate}}</h6></p>
                                    <p><h6>Countries: <span v-for="country in film.filmCountries">{{country.name}}<span v-if="country != film.filmCountries[film.filmCountries.length - 1]">, </span></span></h6></p>
                                    <p><h6>Genre: <span v-for="category in film.filmCategories">{{category.name}}<span v-if="category != film.filmCategories[film.filmCategories.length - 1]">, </span></span></h6></p>
                                    <p><h6>Budget: {{film.budget}}</h6></p>
                                    <p><h6>Awards: <span v-for="award in film.awardsById">{{award.awardName}}({{award.awardYear}})<span v-if="award != film.awardsById[film.awardsById.length - 1]">, </span></span></h6></p>
                                    <p><h6>Actors: <span v-for="filmActor in film.filmActorsById">{{filmActor.actorByIdActor.firstName}} {{filmActor.actorByIdActor.lastName}}<span v-if="filmActor != film.filmActorsById[film.filmActorsById.length -1]">, </span></span></h6></p>
                                    <p><h6>Directors: <span v-for="director in film.filmDirectors">{{director.firstName}} {{director.lastName}}<span v-if="director != film.filmDirectors[film.filmDirectors.length -1]">, </span></span></h6></p>
                                    <p><h6>Studios: <span v-for="studio in film.filmStudios">{{studio.studioName}}<span v-if="studio != film.filmStudios[film.filmStudios.length -1]">, </span></span></h6></p>
                                    <p><h6>Language: {{film.languageByIdLanguage.name}}</h6></p>
                                </div>
                            </div>
                        </article>
                    </div>
                </section>
                <div class="info">
                    <br>
                    <br>
                    <h3 class="subtitle">Description:</h3>
                    <p><h5>{{film.description}}</h5></p>
                </div>
                <hr>
                ${comment}

            </div>
        </div>
    </div>
</t:header>
</html>

