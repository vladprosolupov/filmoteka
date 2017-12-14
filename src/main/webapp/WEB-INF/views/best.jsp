<%--
  Created by IntelliJ IDEA.
  User: vladyslavprosolupov
  Date: 19.11.2017
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/main" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Best Rated</title>
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
            <div class="column is-2 is-desktop">
                <aside class="menu">
                    <p class="menu-label">
                        Categories
                    </p>
                    <ul class="menu-list categories">
                        <li v-for="category in categories"><a v-on:click="openCategory(category[0])"
                                                              v-bind:data-category="category[0]">{{category[1]}}</a>
                        </li>
                        <li><a href="/categories">More...</a></li>
                    </ul>
                </aside>
            </div>
            <div class="column is-9">

                <div class="navbar-end VueSearchOnPage">
                    <div class="navbar-item has-dropdown centered" v-if="width <= 1023">
                        <div class="field has-addons" style="margin: 0">
                            <div class="control has-icons-left">
                                <input id="searchInput" autocomplete="off" v-model="searchInput"
                                       v-on:keyup.13="doSearch"
                                       class="input" type="text" placeholder="Search...">
                                <span class="icon is-small is-left">
                                        <i class="fa fa-search"></i>
                                        </span>
                            </div>
                            <div class="control">
                                <a class="button is-dark" v-on:click="doSearch">
                                    Search
                                </a>
                            </div>
                        </div>
                    </div>
                </div>

                <section class="info-tiles">
                    <div class="films">
                        <div v-for="film in films" class="card effect-ruby grow film">
                            <a v-bind:href="link+film[3]" v-on:click="filmClicked($event)"></a>
                            <div class="card-image ">
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
                                                                                    aria-hidden="true"></i> {{film[4]}}
                                        </p>
                                        <p class="subtitle is-6 is-pulled-left">{{getYear(film[1])}}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                <nav class="pagination is-centered" role="navigation" aria-label="pagination" v-if="pagesNumber != 0">
                    <a class="pagination-previous" v-bind:disabled="currentPage == 1" v-on:click="goToPrevious($event)">Previous</a>
                    <ul class="pagination-list">
                        <li>
                            <a class="pagination-link" v-bind:data-pageNum="1" aria-label="Goto page {{1}}"
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
                        <li v-if="currentPage != pagesNumber && currentPage != 1">
                            <a class="pagination-link" v-bind:data-pageNum="currentPage" aria-label="Goto page {{currentPage}}"
                               aria-current="page" v-on:click="goToPage(currentPage)">{{currentPage}}</a>
                        </li>
                        <li v-if="currentPage + 1 < pagesNumber">
                            <a class="pagination-link" v-bind:data-pageNum="currentPage+1" aria-label="Goto page {{currentPage+1}}"
                               aria-current="page" v-on:click="goToPage(currentPage+1)">{{currentPage+1}}</a>
                        </li>
                        <li v-if="currentPage + 2 < pagesNumber">
                            <a class="pagination-link" v-bind:data-pageNum="currentPage+2" aria-label="Goto page {{currentPage+2}}"
                               aria-current="page" v-on:click="goToPage(currentPage+2)">{{currentPage+2}}</a>
                        </li>

                        <li v-if="currentPage <= (pagesNumber-3)"><span class="pagination-ellipsis">&hellip;</span></li>
                        <li v-if="pagesNumber != 1">
                            <a class="pagination-link" v-bind:data-pageNum="pagesNumber" aria-label="Goto page {{pagesNumber}}"
                               aria-current="page" v-on:click="goToPage(pagesNumber)">{{pagesNumber}}</a>
                        </li>
                    </ul>
                    <a class="pagination-next" v-bind:disabled="currentPage == pagesNumber" v-on:click="goToNext($event)">Next page</a>
                </nav>
            </div>
        </div>
    </div>
</t:header>
</html>
