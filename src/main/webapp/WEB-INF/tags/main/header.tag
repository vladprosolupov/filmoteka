<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@tag description="Main header tag" pageEncoding="UTF-8" %>
<%int hasAccess = 0; %>
<security:authorize access="hasAuthority('user')">
    <% hasAccess = 1; %>
</security:authorize>
<security:authorize access="hasAuthority('admin')">
    <% hasAccess = 2; %>
</security:authorize>
<c:url var="logoutUrl" value="/do_logout"/>
<c:set var="logo"><img src="<c:url value="/resources/images/logo(Biger-Size).png"/>"/></c:set>
<html class="has-navbar-fixed-top">
<head>
    <link rel="shortcut icon"
          href="<c:url value="/resources/images/icon_sizes/icon48.ico"/>"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Including CSRF token to the header -->
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <script type="application/javascript">
        document.addEventListener('DOMContentLoaded', function () {

            var $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);

            if ($navbarBurgers.length > 0) {

                $navbarBurgers.forEach(function ($el) {
                    $el.addEventListener('click', function () {

                        var target = $el.dataset.target;
                        var $target = document.getElementById(target);

                        $el.classList.toggle('is-active');
                        $target.classList.toggle('is-active');

                    });
                });
            }

        });
    </script>
</head>
<body class="layout-default">
<%--<nav class="is-centered">--%>
    <% if (hasAccess == 2) { %>
    <form class="navbar is-transparent is-fixed-top" id="logout_id" action="${logoutUrl}"
          method="post" onsubmit="return false">

        <div class="navbar-burger burger" data-target="navburger-admin">
            <span></span>
            <span></span>
            <span></span>
        </div>

        <div class="navbar-menu" id="navburger-admin">

            <div class="navbar-start">
                <a class="navbar-item home" href="/">${logo}</a>

                <a class="navbar-item" href="/best"><i class="fa fa-star" style="margin-right: 5px"
                                                       aria-hidden="true"></i>Best Rated</a>
                <a class="navbar-item" href="/"><i class="fa fa-caret-square-o-right"
                                                   style="margin-right: 5px"
                                                   aria-hidden="true"></i>You Should Watch</a>

                <a class="navbar-item" href="/about"><i class="fa fa-question" style="margin-right: 5px"
                                                        aria-hidden="true"></i>About</a>

                <div class="navbar-item has-dropdown is-hoverable">
                    <a class="navbar-link" href="/admin/"><i class="fa fa-university"
                                                             style="margin-right: 5px"
                                                             aria-hidden="true"></i>Admin Panel</a>

                    <div class="navbar-dropdown is-boxed is-desktop">
                        <a class="navbar-item " style="margin-top: 6px;" href="/profile"><i
                                class="fa fa-user-circle-o" style="margin-right:5px;"
                                aria-hidden="true"></i>Profile</a>
                        <hr class="navbar-divider"/>
                        <a class="navbar-item" href="/admin/films">Films</a>
                        <a class="navbar-item" href="/admin/actors">Actors</a>
                        <a class="navbar-item" href="/admin/directors">Directors</a>
                        <a class="navbar-item" href="/admin/categories">Categories</a>
                        <a class="navbar-item" href="/admin/networks">Networks</a>
                        <a class="navbar-item" href="/admin/studios">Studios</a>
                        <a class="navbar-item" href="/admin/users">Users</a>

                        <hr class="navbar-divider"/>
                        <input type="hidden"
                               name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>
                        <a class="navbar-item droppeddown" style="color: #ff5257" href="javascript:{}"
                           onclick="document.getElementById('logout_id').submit();"><i
                                class="fa fa-sign-out"
                                style="margin-right: 5px"
                                aria-hidden="true"></i>
                            Log out</a>
                    </div>
                </div>
            </div>

            <div class="navbar-end VueSearch">
                <div class="navbar-item has-dropdown centered">
                    <div class="field has-addons" style="margin: 0">
                        <div class="control has-icons-left">
                            <input id="searchInput" autocomplete="off" v-model="searchInput"
                                   v-on:focusin="showDropdown" v-on:keyup.13="doSearch"
                                   v-on:keyup.down="moveFocusToDropdown"
                                   v-on:mouseover="removeFocusFromOthers"
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
                    <div class="navbar-dropdown is-boxed dropdown-menu is-right is-desktop"
                         style="margin: 0 !important;">
                        <a class="navbar-item searchDropdown" v-on:keyup.down="moveFocusDown"
                           v-on:keyup.up="moveFocusUp" v-for="result in searchResult"
                           v-bind:href="link+result[0]">{{result[1]}}</a>
                        <div class="navbar-item" v-if="searchResult.length == 0" style="cursor: default">Not
                            found
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <% } else if (hasAccess == 0) { %>
    <div class="navbar is-transparent is-fixed-top">

        <div class="navbar-burger burger" data-target="navburger-unreg">
            <span></span>
            <span></span>
            <span></span>
        </div>

        <div class="navbar-menu" id="navburger-unreg">

            <a class="navbar-item home" href="/">${logo}</a>

            <a class="navbar-item" href="/best"><i class="fa fa-star" style="margin-right: 5px"
                                                   aria-hidden="true"></i>Best Rated</a>
            <a class="navbar-item" href="/"><i class="fa fa-caret-square-o-right" style="margin-right: 5px"
                                               aria-hidden="true"></i>You Should Watch</a>

            <a class="navbar-item" href="/about"><i class="fa fa-question" style="margin-right: 5px"
                                                    aria-hidden="true"></i>About</a>

            <div class="is-divider-vertical" style="opacity: 0">bumbum</div>

            <a class="navbar-item" href="/login"><i class="fa fa-sign-in" style="margin-right: 5px"
                                                    aria-hidden="true"></i>Log in</a>

            <div class="is-divider-vertical"></div>

            <a class="navbar-item" href="/register"><i class="fa fa-check-square-o"
                                                       style="margin-right: 5px"
                                                       aria-hidden="true"></i>Sign up</a>

            <div class="navbar-end VueSearch">
                <div class="navbar-item has-dropdown centered">
                    <div class="field has-addons" style="margin: 0">
                        <div class="control has-icons-left">
                            <input id="searchInput" autocomplete="off" v-model="searchInput"
                                   v-on:focusin="showDropdown" v-on:keyup.13="doSearch"
                                   v-on:keyup.down="moveFocusToDropdown" class="input" type="text"
                                   placeholder="Search...">
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
                    <div class="navbar-dropdown is-boxed dropdown-menu is-right is-desktop"
                         style="margin: 0 !important;">
                        <a class="navbar-item searchDropdown" v-on:keyup.down="moveFocusDown"
                           v-on:keyup.up="moveFocusUp" v-on:mouseover="removeFocusFromOthers"
                           v-for="result in searchResult" v-bind:href="link+result[0]">{{result[1]}}</a>
                        <div class="navbar-item" v-if="searchResult.length == 0" style="cursor: default">Not
                            found
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <% } else { %>
    <form class="navbar is-transparent is-fixed-top" id="logout_id" action="${logoutUrl}"
          method="post" onsubmit="return false">

        <div class="navbar-burger burger" data-target="navburger-user">
            <span></span>
            <span></span>
            <span></span>
        </div>

        <div class="navbar-menu" id="navburger-user">

            <a class="navbar-item home" href="/">${logo}</a>

            <a class="navbar-item" href="/best"><i class="fa fa-star" style="margin-right: 5px"
                                                   aria-hidden="true"></i> Best Rated</a>

            <a class="navbar-item" href="/"><i class="fa fa-caret-square-o-right" style="margin-right: 5px"
                                               aria-hidden="true"></i>You Should Watch</a>

            <a class="navbar-item" href="/about"><i class="fa fa-question" style="margin-right: 5px"
                                                    aria-hidden="true"></i>About</a>

            <div class="navbar-item has-dropdown is-hoverable">

                <a class="navbar-link" href="/profile"><i class="fa fa-user-circle-o"
                                                          style="margin-right:5px;"
                                                          aria-hidden="true"></i>Profile</a>

                <div class="navbar-dropdown is-boxed is-desktop">

                    <a class="navbar-item" href="/profile?s=like"><i class="fa fa-heart"
                                                                     style="margin-right: 5px;"
                                                                     aria-hidden="true"></i>Films you've
                        liked</a>

                    <a class="navbar-item" href="/profile?s=book"><i class="fa fa-bookmark"
                                                                     style="margin-right: 5px;"
                                                                     aria-hidden="true"></i> Bookmarks</a>


                    <hr class="navbar-divider"/>

                    <input type="hidden"
                           name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>
                    <a style="color: #ff5257" class="navbar-item droppeddown" href="javascript:{}"
                       onclick="document.getElementById('logout_id').submit();"><i class="fa fa-sign-out"
                                                                                   style="margin-right: 5px"
                                                                                   aria-hidden="true"></i>Log
                        out</a>
                </div>
            </div>
            <div class="navbar-end VueSearch">
                <div class="navbar-item has-dropdown centered">
                    <div class="field has-addons" style="margin: 0">
                        <div class="control has-icons-left">
                            <input id="searchInput" autocomplete="off" v-model="searchInput"
                                   v-on:focusin="showDropdown" v-on:keyup.13="doSearch"
                                   v-on:keyup.down="moveFocusToDropdown"
                                   v-on:mouseover="removeFocusFromOthers"
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
                    <div class="navbar-dropdown is-boxed dropdown-menu is-right is-desktop"
                         style="margin: 0 !important;">
                        <a class="navbar-item searchDropdown" v-on:keyup.down="moveFocusDown"
                           v-on:keyup.up="moveFocusUp" v-for="result in searchResult"
                           v-bind:href="link+result[0]">{{result[1]}}</a>
                        <div class="navbar-item" v-if="searchResult.length == 0" style="cursor: default">Not
                            found
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </form>
    <% } %>
<%--</nav>--%>
<section class="hero background_for_client is-fullheight is-bold white-text">
    <%--div class="hero-head">--%>
        <div class="container">
            <jsp:doBody/>
        </div>
    <%--</div>--%>
    <%--<div class="hero-body body background_for_client">--%>

    <%--</div>--%>
</section>

<footer class="footer">
    <div class="container">
        <div class="content has-text-centered">
            <p>
                <strong>Filmoteka</strong> by Vladyslav Prosolupov, Rostyslav Tryndyak, German Varanytsya.
                The website content
                is licensed.
            </p>
        </div>
    </div>
</footer>

</body>

</html>