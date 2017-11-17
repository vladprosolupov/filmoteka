<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/main" %>

<html>
<head>
    <title>Filmoteka</title>
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
                        <li v-for="category in categories"><a v-on:click="openCategory(category.id)" v-bind:data-category="category.id">{{category.name}}</a>
                        </li>
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
            <div class="column is-9">
                    <%--<section class="hero">--%>
                    <%--<div class="hero-body">--%>


                    <%--<div class="container">--%>
                    <%--<h1 class="title">--%>
                    <%--You're Welcome--%>
                    <%--</h1>--%>
                    <%--<h2 class="subtitle">--%>
                    <%--I hope you are having a great day!--%>
                    <%--</h2>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--</section>--%>

                    <%--<div class="card">--%>
                    <%--<div class="card-image">--%>
                    <%--<figure class="image is-4by3">--%>
                    <%--<img src="https://bulma.io/images/placeholders/1280x960.png" alt="Placeholder image">--%>
                    <%--</figure>--%>
                    <%--</div>--%>
                    <%--<div class="card-content">--%>
                    <%--<div class="media">--%>
                    <%--<div class="media-left">--%>
                    <%--<figure class="image is-48x48">--%>
                    <%--<img src="https://bulma.io/images/placeholders/96x96.png" alt="Placeholder image">--%>
                    <%--</figure>--%>
                    <%--</div>--%>
                    <%--<div class="media-content">--%>
                    <%--<p class="title is-4">John Smith</p>--%>
                    <%--<p class="subtitle is-6">@johnsmith</p>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--TODO add a div which is showing that no films were found with such title--%>

                <section class="info-tiles">
                    <div class="films">
                        <div v-for="film in films" class="card film">
                            <div class="card-image">
                                <figure class="image is-3by4 effect-ruby">
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
