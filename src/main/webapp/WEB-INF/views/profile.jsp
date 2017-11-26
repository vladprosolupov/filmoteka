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


<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="<c:url value="/resources/styles/main.css"/>">
    <script src="<c:url value="/resources/js/jquery-3.2.1.js"/>"></script>
    <script src="<c:url value="/resources/js/vue.js"/>"></script>
    <script src="<c:url value="/resources/js/main.js"/>"></script>

    <!-- Including CSRF token to the header -->
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

</head>
<t:header>

    <div class="container vue" >

        <div class="hero-head2">
            <div class="navbar">
                <div class="navbar-item">
                    <a id="profile" class="panel-block is-active" v-on:click="goToProfile">
                        <i class="fa fa-user-circle-o"></i>
                        Profile
                    </a>
                </div>

                <div class="navbar-item">
                    <a id="liked" class="panel-block" v-on:click="goToLiked">
                        <i class="fa fa-heart"></i>
                        Films you've liked
                    </a>
                </div>

                <div class="navbar-item">
                    <a id="bookmarks" class="panel-block" v-on:click="goToBookmarks">
            <span class="panel-icon">
              <i class="fa fa-bookmark"></i>
            </span>
                        Bookmarks
                    </a>
                </div>
            </div>
        </div>

        <section class="hero is-white is-small is-bold has-text-centered">
            <div class="hero-body is-paddingless" style="margin-top: 100px" >

                <div id="loading" style="position: relative; left: 50%">
                    <div class="spinner">
                        <div class="double-bounce1" style="background-color: #4e4e4e;"></div>
                        <div class="double-bounce2" style="background-color: #4e4e4e;"></div>
                    </div>
                </div>
                <div class="container column" v-if="currentTab == 'profile'"
                     style="display: none; margin-bottom: 10px; max-width: 400px; height: 100%;">
                    <%--<div style="position: absolute; top: 50%;transform: translate(0, -50%);">--%>


                        <h1 class="title">
                            Profile
                            <br>
                            {{info.firstName}} {{info.lastName}}
                        </h1>
                        <br>
                        <form class="subtitle">
                            First name:<br>
                            <input class="input is-3" type="text" name="firstname" value={{info.firstName}}>
                            <br><br>
                            Last name:<br>
                            <input class="input" type="text" name="lastname" value={{info.lastName}}>
                            <br><br>
                            E-mail:<br>
                            <input class="input" type="email" name="lastname" value={{info.email}}>
                            <br><br>
                            Username:<br>
                            <input class="input" type="text" name="login" value={{info.login}}>
                            <br><br>
                            Password:<br>
                            <input class="input" type="password" name="password" value={{info.password}}>
                            <br><br>
                            Phone number:<br>
                            <input class="input" type="tel" name="phoneNumber" value={{info.phoneNumber}}>
                            <br><br>
                            <button class="button is-info is-medium">Save Changes</button>

                        </form>


                    <%--</div>--%>
                </div>
                <div class="container column" v-if="currentTab == 'like'"
                     style="display: none; position: relative; overflow: scroll; margin: 0 0.7rem 0.7rem 0; height: 100%;">
                    <div style="position: absolute; display: flex; width: -webkit-fill-available; width: -moz-available; justify-content: center;">
                        <article class="message is-info" style="margin-top: 20px;" v-if="likedFilms.length == 0">
                            <div class="message-body">
                                You haven't liked any <strong>films</strong> yet.
                            </div>
                        </article>

                        <section class="info-tiles">
                            <div class="filmsProf">
                        <div v-for="film in likedFilms" class="card effect-ruby grow film">
                            <div class="card-image">
                                <figure class="image is-3by4">
                                    <img v-bind:src="film[2]" alt="Cover">
                                    <a v-bind:href="link+film[3]"></a>
                                </figure>
                            </div>
                            <div class="card-content">
                                <div class="media">
                                    <div class="media-content">

                                        <p class="title is-6">{{film[0]}}</p>
                                        <hr>
                                        <p class="subtitle is-6 is-pulled-right"><i class="fa fa-star" style="color: #e09952;"
                                                                                    aria-hidden="true"></i> {{film[4]}}</p>
                                        <p class="subtitle is-6 is-pulled-left">{{getYear(film[1])}}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                            </div>
                            </section>

                        <nav class="pagination is-centered" role="navigation" aria-label="pagination" v-if="pagesNumberLiked != 0">
                            <a class="pagination-previous" v-if="currentPage != 1" v-on:click="goToPrevious">Previous</a>
                            <a class="pagination-next" v-if="currentPage != pagesNumberLiked" v-on:click="goToNext">Next page</a>
                            <ul class="pagination-list">
                                <li>
                                    <a class="pagination-link is-current" v-bind:data-pageNum="1" aria-label="Goto page 1"
                                       aria-current="page" v-on:click="goToPage(1)">1</a>
                                </li>
                                <li v-if="currentPage > 3"><span class="pagination-ellipsis">&hellip;</span></li>

                                <li v-if="currentPage - 2 > 1">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage-2" aria-label="Goto page {{currentPage-2}}"
                                       aria-current="page" v-on:click="goToPage(currentPage-2)">{{currentPage-2}}</a>
                                </li>
                                <li v-if="currentPage - 1 > 1">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage-1" aria-label="Goto page {{currentPage-1}}"
                                       aria-current="page" v-on:click="goToPage(currentPage-1)">{{currentPage-1}}</a>
                                </li>
                                <li v-if="currentPage != pagesNumberLiked && currentPage != 1">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage" aria-label="Goto page {{currentPage}}"
                                       aria-current="page" v-on:click="goToPage(currentPage)">{{currentPage}}</a>
                                </li>
                                <li v-if="currentPage + 1 < pagesNumberLiked">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage+1" aria-label="Goto page {{currentPage+1}}"
                                       aria-current="page" v-on:click="goToPage(currentPage+1)">{{currentPage+1}}</a>
                                </li>
                                <li v-if="currentPage + 2 < pagesNumberLiked">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage+2" aria-label="Goto page {{currentPage+2}}"
                                       aria-current="page" v-on:click="goToPage(currentPage+2)">{{currentPage+2}}</a>
                                </li>

                                <li v-if="currentPage <= (pagesNumberLiked-5)"><span class="pagination-ellipsis">&hellip;</span></li>
                                <li v-if="pagesNumberLiked != 1">
                                    <a class="pagination-link" v-bind:data-pageNum="pagesNumberLiked" aria-label="Goto page {{pagesNumberLiked}}"
                                       aria-current="page" v-on:click="goToPage(pagesNumberLiked)">{{pagesNumberLiked}}</a>
                                </li>
                            </ul>
                        </nav>

                    </div>
                </div>
                <div class="container column" v-if="currentTab == 'book'"
                     style="display: none; position: relative; overflow: scroll; margin: 0 0.7rem 0.7rem 0; height: 100%;">
                    <div style="position: absolute; display: flex; width: -webkit-fill-available; width: -moz-available; justify-content: center;">
                        <article class="message is-info" style="margin-top: 20px;" v-if="bookmarkedFilms.length == 0">
                            <div class="message-body">
                                You don't have any <strong>films</strong> in bookmarks yet.
                            </div>
                        </article>


                    <section class="info-tiles">
                        <div class="filmsProf">
                        <div v-for="film in bookmarkedFilms" class="card effect-ruby grow film">
                            <div class="card-image">
                                <figure class="image is-3by4">
                                    <img v-bind:src="film[2]" alt="Cover">
                                    <a v-bind:href="link+film[3]"></a>
                                </figure>
                            </div>
                            <div class="card-content">
                                <div class="media">
                                    <div class="media-content">

                                        <p class="title is-6">{{film[0]}}</p>
                                        <hr>
                                        <p class="subtitle is-6 is-pulled-right"><i class="fa fa-star" style="color: #e09952;"
                                                                                    aria-hidden="true"></i> {{film[4]}}</p>
                                        <p class="subtitle is-6 is-pulled-left">{{getYear(film[1])}}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    </section>

                        <nav class="pagination is-centered" role="navigation" aria-label="pagination" v-if="pagesNumberBookmarked != 0">
                            <a class="pagination-previous" v-if="currentPage != 1" v-on:click="goToPrevious">Previous</a>
                            <a class="pagination-next" v-if="currentPage != pagesNumberBookmarked" v-on:click="goToNext">Next page</a>
                            <ul class="pagination-list">
                                <li>
                                    <a class="pagination-link is-current" v-bind:data-pageNum="1" aria-label="Goto page {{1}}"
                                       aria-current="page" v-on:click="goToPage(1)">1</a>
                                </li>
                                <li v-if="currentPage > 3"><span class="pagination-ellipsis">&hellip;</span></li>

                                <li v-if="currentPage - 2 > 1">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage-2" aria-label="Goto page {{currentPage-2}}"
                                       aria-current="page" v-on:click="goToPage(currentPage-2)">{{currentPage-2}}</a>
                                </li>
                                <li v-if="currentPage - 1 > 1">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage-1" aria-label="Goto page {{currentPage-1}}"
                                       aria-current="page" v-on:click="goToPage(currentPage-1)">{{currentPage-1}}</a>
                                </li>
                                <li v-if="currentPage != pagesNumberBookmarked && currentPage != 1">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage" aria-label="Goto page {{currentPage}}"
                                       aria-current="page" v-on:click="goToPage(currentPage)">{{currentPage}}</a>
                                </li>
                                <li v-if="currentPage + 1 < pagesNumberBookmarked">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage+1" aria-label="Goto page {{currentPage+1}}"
                                       aria-current="page" v-on:click="goToPage(currentPage+1)">{{currentPage+1}}</a>
                                </li>
                                <li v-if="currentPage + 2 < pagesNumberBookmarked">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage+2" aria-label="Goto page {{currentPage+2}}"
                                       aria-current="page" v-on:click="goToPage(currentPage+2)">{{currentPage+2}}</a>
                                </li>

                                <li v-if="currentPage <= (pagesNumberBookmarked-5)"><span class="pagination-ellipsis">&hellip;</span></li>
                                <li v-if="pagesNumberBookmarked != 1">
                                    <a class="pagination-link" v-bind:data-pageNum="pagesNumberBookmarked" aria-label="Goto page {{pagesNumberBookmarked}}"
                                       aria-current="page" v-on:click="goToPage(pagesNumberBookmarked)">{{pagesNumberBookmarked}}</a>
                                </li>
                            </ul>
                        </nav>

                    </div>
                </div>
            </div>
        </section>
    </div>
</t:header>
</html>

