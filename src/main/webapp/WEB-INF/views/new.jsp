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
    <title>New</title>
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
                <aside class="menu">
                    <p class="menu-label">
                        Categories
                    </p>
                    <ul class="menu-list categories">
                        <li v-for="category in categories"><a v-on:click="openCategory(category[0])" v-bind:data-category="category[0]">{{category[1]}}</a>
                        </li>
                    </ul>
                </aside>
            </div>
            <div class="column is-9">
                <article class="message is-danger" v-if="notFound">
                    <div class="message-body">
                        Sorry, but the film you are looking for is <strong>not found</strong>. Please try with other keyword or choose one film from our library!
                    </div>
                </article>
                <section class="info-tiles">
                    <div class="films">
                        <div v-for="film in films" class="card effect-ruby grow film">
                            <div class="card-image ">
                                <figure class="image is-3by4">
                                    <img v-bind:src="film[2]" alt="Cover">
                                    <a v-bind:href="link+film[3]"><span>More <i class="fa fa-info-circle"
                                                                                aria-hidden="true"></i></span></a>
                                </figure>
                            </div>
                            <div class="card-content">
                                <div class="media">
                                    <div class="media-content">
                                        <p class="title is-6">{{film[0]}}</p>
                                        <hr>
                                        <p class="subtitle is-6 is-pulled-right">{{getYear(film[1])}}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                <div class="columns">
                </div>
                <nav class="pagination is-centered" role="navigation" aria-label="pagination">
                    <a class="pagination-previous">Previous</a>
                    <a class="pagination-next">Next page</a>
                    <ul class="pagination-list">
                        <li><a class="pagination-link is-current" aria-label="Goto page 1" aria-current="page">1</a>
                        </li>
                        <li><a class="pagination-link" aria-label="Goto page 2">2</a></li>
                        <li><a class="pagination-link" aria-label="Goto page 3">3</a></li>
                        <li><span class="pagination-ellipsis">&hellip;</span></li>
                            <%--<li><a class="pagination-link" aria-label="Goto page 45">45</a></li>--%>
                            <%--<li><a class="pagination-link" aria-label="Goto page 46">46</a></li>--%>
                        <li><a class="pagination-link" aria-label="Goto page 47">47</a></li>
                            <%--<li><span class="pagination-ellipsis">&hellip;</span></li>--%>
                            <%--<li><a class="pagination-link" aria-label="Goto page 86">86</a></li>--%>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</t:header>
</html>
