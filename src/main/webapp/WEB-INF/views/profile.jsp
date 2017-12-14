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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Profile</title>
    <!-- Including CSRF token to the header -->
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

</head>
<t:header>
    <div class="container vue">
        <div class="hero-head2">
            <div class="navbar">
                <div class="navbar-item">
                    <a id="profile" class="panel-block is-active" v-on:click="goToProfile">
                        <span class="panel-icon">
                            <i class="fa fa-user-circle-o"></i>
                        </span>
                        Profile
                    </a>
                </div>

                <div class="navbar-item">
                    <a id="liked" class="panel-block" v-on:click="goToLiked">
                        <span class="panel-icon">
                            <i class="fa fa-heart"></i>
                        </span>
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

        <section class="hero is-white is-small is-bold has-text-centered" style="margin-top: 2ch">
            <div id="profileHero" class="hero-body is-paddingless">

                <div id="loading" style="position: relative; left: 50%">
                    <div class="spinner">
                        <div class="double-bounce1" style="background-color: #4e4e4e;"></div>
                        <div class="double-bounce2" style="background-color: #4e4e4e;"></div>
                    </div>
                </div>
                <div class="container column" v-if="currentTab == 'profile'"
                     style="display: none; margin-bottom: 10px; max-width: 400px; padding: 0.75rem">

                    <figure id="profileImg" style="margin-left: auto;margin-right: auto" class="image is-128x128">
                        <img id="avatar" v-bind:src="domain + info.avatar">
                        <a style="display: none;" v-on:click="chooseImg">Change profile image</a>
                    </figure>
                    <div class="field" id="radioImgs" style="display: none;">
                        <div class="control is-grouped is-grouped-multiline" style="left: 5%">
                            <label class="radio" style="margin-left: 0.5em">
                                <input type="radio" name="avatar" value="icon1" v-on:click="changeImg($event)" :checked = "info.avatar === '/resources/images/avatar/icon1.png'">
                                <figure class="image is-64x64">
                                    <img src="/resources/images/avatar/icon1.png"/>
                                </figure>
                            </label>
                            <label class="radio">
                                <input type="radio" name="avatar" value="icon2" v-on:click="changeImg($event)" :checked = "info.avatar === '/resources/images/avatar/icon2.png'">
                                <figure class="image is-64x64">
                                    <img src="/resources/images/avatar/icon2.png"/>
                                </figure>
                            </label>
                            <label class="radio">
                                <input type="radio" name="avatar" value="icon3" v-on:click="changeImg($event)" :checked = "info.avatar === '/resources/images/avatar/icon3.png'">
                                <figure class="image is-64x64">
                                    <img src="/resources/images/avatar/icon3.png"/>
                                </figure>
                            </label>
                            <label class="radio">
                                <input type="radio" name="avatar" value="icon4" v-on:click="changeImg($event)" :checked = "info.avatar === '/resources/images/avatar/icon4.png'">
                                <figure class="image is-64x64">
                                    <img src="/resources/images/avatar/icon4.png"/>
                                </figure>
                            </label>
                            <label class="radio">
                                <input type="radio" name="avatar" value="icon5" v-on:click="changeImg($event)" :checked = "info.avatar === '/resources/images/avatar/icon5.png'">
                                <figure class="image is-64x64">
                                    <img src="/resources/images/avatar/icon5.png"/>
                                </figure>
                            </label>
                            <label class="radio">
                                <input type="radio" name="avatar" value="icon6" v-on:click="changeImg($event)" :checked = "info.avatar === '/resources/images/avatar/icon6.png'">
                                <figure class="image is-64x64">
                                    <img src="/resources/images/avatar/icon6.png"/>
                                </figure>
                            </label>
                            <label class="radio">
                                <input type="radio" name="avatar" value="icon7" v-on:click="changeImg($event)" :checked = "info.avatar === '/resources/images/avatar/icon7.png'">
                                <figure class="image is-64x64">
                                    <img src="/resources/images/avatar/icon7.png"/>
                                </figure>
                            </label>
                            <label class="radio">
                                <input type="radio" name="avatar" value="icon8" v-on:click="changeImg($event)" :checked = "info.avatar === '/resources/images/avatar/icon8.png'">
                                <figure class="image is-64x64">
                                    <img src="/resources/images/avatar/icon8.png"/>
                                </figure>
                            </label>
                            <label class="radio">
                                <input type="radio" name="avatar" value="icon9" v-on:click="changeImg($event)" :checked = "info.avatar === '/resources/images/avatar/icon9.png'">
                                <figure class="image is-64x64">
                                    <img src="/resources/images/avatar/icon9.png"/>
                                </figure>
                            </label>
                            <label class="radio">
                                <input type="radio" name="avatar" value="icon10" v-on:click="changeImg($event)" :checked = "info.avatar === '/resources/images/avatar/icon10.png'">
                                <figure class="image is-64x64">
                                    <img src="/resources/images/avatar/icon10.png"/>
                                </figure>
                            </label>
                            <label class="radio">
                                <input type="radio" name="avatar" value="icon11" v-on:click="changeImg($event)" :checked = "info.avatar === '/resources/images/avatar/icon11.png'">
                                <figure class="image is-64x64">
                                    <img src="/resources/images/avatar/icon11.png"/>
                                </figure>
                            </label>
                            <label class="radio">
                                <input type="radio" name="avatar" value="icon12" v-on:click="changeImg($event)" :checked = "info.avatar === '/resources/images/avatar/icon12.png'">
                                <figure class="image is-64x64">
                                    <img src="/resources/images/avatar/icon12.png"/>
                                </figure>
                            </label>
                            <label class="radio">
                                <input type="radio" name="avatar" value="icon13" v-on:click="changeImg($event)" :checked = "info.avatar === '/resources/images/avatar/icon13.png'">
                                <figure class="image is-64x64">
                                    <img src="/resources/images/avatar/icon13.png"/>
                                </figure>
                            </label>
                            <label class="radio">
                                <input type="radio" name="avatar" value="icon14" v-on:click="changeImg($event)" :checked = "info.avatar === '/resources/images/avatar/icon14.png'">
                                <figure class="image is-64x64">
                                    <img src="/resources/images/avatar/icon14.png"/>
                                </figure>
                            </label>
                            <label class="radio">
                                <input type="radio" name="avatar" value="icon15" v-on:click="changeImg($event)" :checked = "info.avatar === '/resources/images/avatar/icon15.png'">
                                <figure class="image is-64x64">
                                    <img src="/resources/images/avatar/icon15.png"/>
                                </figure>
                            </label>
                            <label class="radio">
                                <input type="radio" name="avatar" value="icon16" v-on:click="changeImg($event)" :checked = "info.avatar === '/resources/images/avatar/icon16.png'">
                                <figure class="image is-64x64">
                                    <img src="/resources/images/avatar/icon16.png"/>
                                </figure>
                            </label>
                        </div>
                    </div>
                    <h1 class="title">
                        Profile
                        <br>
                        {{info.firstName}} {{info.lastName}}
                    </h1>
                    <hr>

                    <form class="subtitle profile" v-bind:data-id="info.id">
                        <div class="box">
                            <div class="field">
                                First name:
                                <input class="input is-3" type="text" name="firstName" value={{info.firstName}}>
                                <p class="help is-danger" style="display: none">
                                    First name cannot be shorter than 2 symbols and contain numeric!
                                </p>
                            </div>
                            <div class="field">
                                Last name
                                <input class="input" type="text" name="lastName" value={{info.lastName}}>
                                <p class="help is-danger" style="display: none">
                                    Last name cannot be shorter than 2 symbols and contain numeric!
                                </p>
                            </div>
                            <div class="field">
                                E-mail:
                                <input class="input is-static" autocomplete="off" type="email" name="email"
                                       value={{info.email}}>
                                <p class="help is-danger" style="display: none">
                                    Please enter valid email!
                                </p>
                            </div>
                            <div class="field">
                                Username:
                                <input class="input is-static" autocomplete="off" type="text" name="login"
                                       readonly="readonly"
                                       value={{info.login}}>
                            </div>
                            <div class="field">
                                Phone number:
                                <input class="input" type="tel" name="phoneNumber" value={{info.phoneNumber}}>
                                <p class="help is-danger" style="display: none">
                                    Phone number cannot be shorter than 6 symbols!
                                </p>
                            </div>
                            <hr>
                            <button type="button" class="button is-info is-medium is-static save" style="margin: 5px;">Save changes</button>

                            <button type="button" class="button is-dark is-medium pass" style="margin: 5px;">Change password</button>

                            <article class="message is-danger messageError" style="display: none;">
                                <div class="message-body">
                                    Sorry, an <strong>error</strong> occurred during execution.
                                </div>
                            </article>
                            <article class="message is-info messageSuccessEdit" style="display: none;">
                                <div class="message-body">
                                    You have successfully <strong>changed</strong> your profile information.
                                </div>
                            </article>
                            <article class="message is-info messageSuccess" style="display: none;">
                                <div class="message-body">
                                    We have sent you a <strong>letter</strong> to your email with password changing
                                    confirmation.
                                </div>
                            </article>
                        </div>
                    </form>
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
                                    <a v-bind:href="link+film[3]" v-on:click="filmClicked($event)"></a>
                                    <div class="card-image">
                                        <figure class="image is-3by4">
                                            <img v-bind:src="film[2]" alt="Cover">
                                        </figure>
                                    </div>
                                    <div class="card-content">
                                        <div class="media">
                                            <div class="media-content">

                                                <p class="title is-6">{{film[0]}}</p>
                                                <hr>
                                                <p class="subtitle is-6 is-pulled-right"><i class="fa fa-star"
                                                                                            style="color: #e09952;"
                                                                                            aria-hidden="true"></i>
                                                    {{film[4]}}</p>
                                                <p class="subtitle is-6 is-pulled-left">{{getYear(film[1])}}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>

                        <nav class="pagination is-centered" role="navigation" aria-label="pagination"
                             v-if="pagesNumberLiked != 0">
                            <a class="pagination-previous" v-bind:disabled="currentPage == 1"
                               v-on:click="goToPrevious($event)">Previous</a>
                            <ul class="pagination-list">
                                <li>
                                    <a class="pagination-link is-current" v-bind:data-pageNum="1"
                                       aria-label="Goto page 1"
                                       aria-current="page" v-on:click="goToPage(1)">1</a>
                                </li>
                                <li v-if="currentPage > 3"><span class="pagination-ellipsis">&hellip;</span></li>

                                <li v-if="currentPage - 2 > 1">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage-2"
                                       aria-label="Goto page {{currentPage-2}}"
                                       aria-current="page" v-on:click="goToPage(currentPage-2)">{{currentPage-2}}</a>
                                </li>
                                <li v-if="currentPage - 1 > 1">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage-1"
                                       aria-label="Goto page {{currentPage-1}}"
                                       aria-current="page" v-on:click="goToPage(currentPage-1)">{{currentPage-1}}</a>
                                </li>
                                <li v-if="currentPage != pagesNumberLiked && currentPage != 1">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage"
                                       aria-label="Goto page {{currentPage}}"
                                       aria-current="page" v-on:click="goToPage(currentPage)">{{currentPage}}</a>
                                </li>
                                <li v-if="currentPage + 1 < pagesNumberLiked">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage+1"
                                       aria-label="Goto page {{currentPage+1}}"
                                       aria-current="page" v-on:click="goToPage(currentPage+1)">{{currentPage+1}}</a>
                                </li>
                                <li v-if="currentPage + 2 < pagesNumberLiked">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage+2"
                                       aria-label="Goto page {{currentPage+2}}"
                                       aria-current="page" v-on:click="goToPage(currentPage+2)">{{currentPage+2}}</a>
                                </li>

                                <li v-if="currentPage <= (pagesNumberLiked-3)"><span class="pagination-ellipsis">&hellip;</span>
                                </li>
                                <li v-if="pagesNumberLiked != 1">
                                    <a class="pagination-link" v-bind:data-pageNum="pagesNumberLiked"
                                       aria-label="Goto page {{pagesNumberLiked}}"
                                       aria-current="page"
                                       v-on:click="goToPage(pagesNumberLiked)">{{pagesNumberLiked}}</a>
                                </li>
                            </ul>
                            <a class="pagination-next" v-bind:disabled="currentPage == pagesNumberLiked" v-on:click="goToNext($event)">Next
                                page</a>
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
                                    <a v-bind:href="link+film[3]" v-on:click="filmClicked($event)"></a>
                                    <div class="card-image">
                                        <figure class="image is-3by4">
                                            <img v-bind:src="film[2]" alt="Cover">
                                        </figure>
                                    </div>
                                    <div class="card-content">
                                        <div class="media">
                                            <div class="media-content">

                                                <p class="title is-6">{{film[0]}}</p>
                                                <hr>
                                                <p class="subtitle is-6 is-pulled-right"><i class="fa fa-star"
                                                                                            style="color: #e09952;"
                                                                                            aria-hidden="true"></i>
                                                    {{film[4]}}</p>
                                                <p class="subtitle is-6 is-pulled-left">{{getYear(film[1])}}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>

                        <nav class="pagination is-centered" role="navigation" aria-label="pagination"
                             v-if="pagesNumberBookmarked != 0">
                            <a class="pagination-previous" v-bind:disabled="currentPage == 1"
                               v-on:click="goToPrevious($event)">Previous</a>
                            <a class="pagination-next" v-bind:disabled="currentPage == pagesNumberBookmarked"
                               v-on:click="goToNext($event)">Next page</a>
                            <ul class="pagination-list">
                                <li>
                                    <a class="pagination-link is-current" v-bind:data-pageNum="1"
                                       aria-label="Goto page {{1}}"
                                       aria-current="page" v-on:click="goToPage(1)">1</a>
                                </li>
                                <li v-if="currentPage > 3"><span class="pagination-ellipsis">&hellip;</span></li>

                                <li v-if="currentPage - 2 > 1">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage-2"
                                       aria-label="Goto page {{currentPage-2}}"
                                       aria-current="page" v-on:click="goToPage(currentPage-2)">{{currentPage-2}}</a>
                                </li>
                                <li v-if="currentPage - 1 > 1">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage-1"
                                       aria-label="Goto page {{currentPage-1}}"
                                       aria-current="page" v-on:click="goToPage(currentPage-1)">{{currentPage-1}}</a>
                                </li>
                                <li v-if="currentPage != pagesNumberBookmarked && currentPage != 1">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage"
                                       aria-label="Goto page {{currentPage}}"
                                       aria-current="page" v-on:click="goToPage(currentPage)">{{currentPage}}</a>
                                </li>
                                <li v-if="currentPage + 1 < pagesNumberBookmarked">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage+1"
                                       aria-label="Goto page {{currentPage+1}}"
                                       aria-current="page" v-on:click="goToPage(currentPage+1)">{{currentPage+1}}</a>
                                </li>
                                <li v-if="currentPage + 2 < pagesNumberBookmarked">
                                    <a class="pagination-link" v-bind:data-pageNum="currentPage+2"
                                       aria-label="Goto page {{currentPage+2}}"
                                       aria-current="page" v-on:click="goToPage(currentPage+2)">{{currentPage+2}}</a>
                                </li>

                                <li v-if="currentPage <= (pagesNumberBookmarked-3)"><span class="pagination-ellipsis">&hellip;</span>
                                </li>
                                <li v-if="pagesNumberBookmarked != 1">
                                    <a class="pagination-link" v-bind:data-pageNum="pagesNumberBookmarked"
                                       aria-label="Goto page {{pagesNumberBookmarked}}"
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

