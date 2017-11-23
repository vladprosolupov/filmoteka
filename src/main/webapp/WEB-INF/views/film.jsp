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

<c:set var="bookmark">
    <% if (hasAccess != 0) { %>
    <div class="is-pulled-right">
        <a id="bookmark" v-if="!bookmarked" class="is-size-3" v-on:click="addRemoveBookMark($event)">
            <i class="fa fa-bookmark-o" aria-hidden="true"></i>
            <i class="fa fa-bookmark" aria-hidden="true"></i>
        </a>
        <a id="bookmark" v-if="bookmarked" class="is-size-3 is-bookmarked" v-on:click="addRemoveBookMark($event)">
            <i class="fa fa-bookmark-o" aria-hidden="true"></i>
            <i class="fa fa-bookmark" aria-hidden="true"></i>
        </a>
    </div>
    <% } %>
</c:set>

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
                    <textarea id="newComment" v-on:keydown.13="submitCommentOrMoveLine($event)" class="textarea"
                              placeholder="Add a comment..."></textarea>
                </p>
            </div>
            <nav class="level">
                <div class="level-left">
                    <div class="level-item">
                        <a class="button is-dark" id="submitButton" v-on:click="submitComment">Submit</a>
                    </div>
                </div>
                <div class="level-right">
                    <div class="level-item">
                        <label class="checkbox">
                            <input v-model="check" type="checkbox"> Press enter to submit
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
                        <li v-for="category in categories">
                            <a v-on:click="openCategory(category[0])">{{category[1]}}</a>
                        </li>
                    </ul>
                </aside>
            </div>
            <div class="column is-9 info" style="background-color: rgb(250,250,250); color: black;">
                <section class="hero">
                    <article id="bookmarkInfo" class="message is-info" style="display: none">
                        <div class="message-header">
                            <p>Bookmarks</p>
                            <button class="delete" aria-label="delete" v-on:click="hideBookmarksInfo($event)"></button>
                        </div>
                        <div class="message-body">
                            You have successfully added that film to <strong>your bookmarks</strong>, now you can find it in your <strong>profile</strong>, or <strong>navigation bar dropdown menu</strong>.
                        </div>
                    </article>
                    <div class="hero-body">
                        <article id="filmInfo" class="media">
                            <figure class="media-left">
                                <p class="image is-percentage">
                                    <img v-bind:src='film.cover'>
                                </p>
                                <p style="display: flex; margin-top: 5px;" class="centered">
                                    <a class="button is-success is-outlined" style="width: 70px; margin: 5px;" v-on:click="like"><i class="fa fa-thumbs-o-up" aria-hidden="true"></i>500</a>
                                    <a class="button is-danger is-outlined" style="width: 70px; margin: 5px;" v-on:click="dislike"><i class="fa fa-thumbs-o-down" aria-hidden="true"></i>20</a>
                                    <div id="likeInfoMessage" class="message is-info" style="display: none;" v-if="currUser.login == null">
                                        <div class="message-body">
                                            Please <strong><a href="/login">Log in</a></strong> to like or dislike a film.
                                        </div>
                                    </div>
                                </p>
                            </figure>
                            <div class="media-content">
                                <div class="content">
                                    <h3><b>{{film.title}}</b></h3>
                                    <hr>
                                    <h6 class="rating" style="display: inline-flex">Rating: <i v-if="film.rating == 0" class="fa fa-star-o"
                                                                     aria-hidden="true"></i> <i
                                        v-if="film.rating > 0 && film.rating < 1" class="fa fa-star-half-o"
                                        aria-hidden="true"></i> <i v-if="film.rating >= 1" class="fa fa-star"
                                                                   aria-hidden="true"></i> <i v-if="film.rating <= 1"
                                                                                              class="fa fa-star-o"
                                                                                              aria-hidden="true"></i> <i
                                        v-if="film.rating > 1 && film.rating < 2" class="fa fa-star-half-o"
                                        aria-hidden="true"></i> <i v-if="film.rating >= 2" class="fa fa-star"
                                                                   aria-hidden="true"></i> <i v-if="film.rating <= 2"
                                                                                              class="fa fa-star-o"
                                                                                              aria-hidden="true"></i> <i
                                        v-if="film.rating > 2 && film.rating < 3" class="fa fa-star-half-o"
                                        aria-hidden="true"></i> <i v-if="film.rating >= 3" class="fa fa-star"
                                                                   aria-hidden="true"></i> <i v-if="film.rating <= 3"
                                                                                              class="fa fa-star-o"
                                                                                              aria-hidden="true"></i> <i
                                        v-if="film.rating > 3 && film.rating < 4" class="fa fa-star-half-o"
                                        aria-hidden="true"></i> <i v-if="film.rating >= 4" class="fa fa-star"
                                                                   aria-hidden="true"></i> <i v-if="film.rating <= 4"
                                                                                              class="fa fa-star-o"
                                                                                              aria-hidden="true"></i> <i
                                        v-if="film.rating > 4 && film.rating < 5" class="fa fa-star-half-o"
                                        aria-hidden="true"></i> <i v-if="film.rating >= 5" class="fa fa-star"
                                                                   aria-hidden="true"></i> <i v-if="film.rating <= 5"
                                                                                              class="fa fa-star-o"
                                                                                              aria-hidden="true"></i> <i
                                        v-if="film.rating > 5 && film.rating < 6" class="fa fa-star-half-o"
                                        aria-hidden="true"></i> <i v-if="film.rating >= 6" class="fa fa-star"
                                                                   aria-hidden="true"></i> <i v-if="film.rating <= 6"
                                                                                              class="fa fa-star-o"
                                                                                              aria-hidden="true"></i> <i
                                        v-if="film.rating > 6 && film.rating < 7" class="fa fa-star-half-o"
                                        aria-hidden="true"></i> <i v-if="film.rating >= 7" class="fa fa-star"
                                                                   aria-hidden="true"></i> <i v-if="film.rating <= 7"
                                                                                              class="fa fa-star-o"
                                                                                              aria-hidden="true"></i> <i
                                        v-if="film.rating > 7 && film.rating < 8" class="fa fa-star-half-o"
                                        aria-hidden="true"></i> <i v-if="film.rating >= 8" class="fa fa-star"
                                                                   aria-hidden="true"></i> <i v-if="film.rating <= 8"
                                                                                              class="fa fa-star-o"
                                                                                              aria-hidden="true"></i> <i
                                        v-if="film.rating > 8 && film.rating < 9" class="fa fa-star-half-o"
                                        aria-hidden="true"></i> <i v-if="film.rating >= 9" class="fa fa-star"
                                                                   aria-hidden="true"></i> <i v-if="film.rating <= 9"
                                                                                              class="fa fa-star-o"
                                                                                              aria-hidden="true"></i> <i
                                        v-if="film.rating > 9 && film.rating < 10" class="fa fa-star-half-o"
                                        aria-hidden="true"></i> <i v-if="film.rating == 10" class="fa fa-star"
                                                                   aria-hidden="true"></i> <span
                                        style="margin-left: 1%">{{film.rating}}</span></h6>
                                    <h6>Length: {{calculateTime(film.lenght)}}</h6>
                                    <h6>Release date: {{formatReleaseDate(film.releaseDate)}}</h6>
                                    <h6>Countries: <span v-for="country in film.filmCountries">{{country.name}}<span
                                        v-if="country != film.filmCountries[film.filmCountries.length - 1]">, </span></span>
                                </h6>
                                    <h6>Genre: <span v-for="category in film.filmCategories">{{category.name}}<span
                                        v-if="category != film.filmCategories[film.filmCategories.length - 1]">, </span></span>
                                </h6>
                                    <h6>Budget: {{film.budget}}</h6>
                                    <h6>Awards: <span v-for="award in film.awardsById">{{award.awardName}}({{award.awardYear}})<span
                                        v-if="award != film.awardsById[film.awardsById.length - 1]">, </span></span>
                                </h6>
                                    <h6>Actors: <span v-for="filmActor in film.filmActorsById">{{filmActor.actorByIdActor.firstName}} {{filmActor.actorByIdActor.lastName}}<span
                                        v-if="filmActor != film.filmActorsById[film.filmActorsById.length -1]">, </span></span>
                                </h6>
                                    <h6>Directors: <span v-for="director in film.filmDirectors">{{director.firstName}} {{director.lastName}}<span
                                        v-if="director != film.filmDirectors[film.filmDirectors.length -1]">, </span></span>
                                </h6>
                                    <h6>Studios: <span v-for="studio in film.filmStudios">{{studio.studioName}}<span
                                        v-if="studio != film.filmStudios[film.filmStudios.length -1]">, </span></span>
                                </h6>
                                    <p><h6>Language: {{film.languageByIdLanguage.name}}</h6>
                                </div>
                            </div>

                            ${bookmark}

                        </article>
                    </div>
                </section>
                <div class="info desc">
                    <h2 class="subtitle">Description:</h2>
                    <h5>{{film.description}}</h5>
                </div>
                <br>
                <hr>

                ${comment}

                <article class="media" v-for="comment in comments">
                    <figure class="media-left">
                        <p class="image is-64x64">
                            <img src="https://bulma.io/images/placeholders/128x128.png">
                        </p>
                    </figure>
                    <div class="media-content">
                        <div class="content">
                            <p>
                                <strong>{{comment.clientFirstName}} {{comment.clientLastName}}</strong>
                                <small>@{{comment.clientLogin}}</small>
                                <small>{{getPostTime(comment.commentDate)}}</small>
                                <br>
                                {{comment.commentText}}
                            </p>
                        </div>
                        <%--<nav class="level is-mobile">--%>
                            <%--<div class="level-left">--%>
                                <%--<a class="level-item" v-on:click="commentDown(comment)">--%>
                                    <%--<span class="icon is-small dislike"><i class="fa fa-thumbs-o-down"></i></span>--%>
                                <%--</a>--%>
                                <%--<a class="level-item counter">--%>
                                    <%--{{comment.rating}}--%>
                                <%--</a>--%>
                                <%--<a class="level-item" v-on:click="commentUp(comment)">--%>
                                    <%--<span class="icon is-small like"><i class="fa fa-thumbs-o-up"></i></span>--%>
                                <%--</a>--%>
                            <%--</div>--%>
                        <%--</nav>--%>
                    </div>
                    <div class="media-right" v-if="currUser.login == comment.clientLogin || currUser.login == 'admin'">
                        <button class="delete" v-on:click="deleteComment(comment.id, $event)"></button>
                    </div>
                    <br>
                </article>
            </div>
        </div>
    </div>
</t:header>
</html>

