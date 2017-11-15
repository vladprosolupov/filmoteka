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
    <div class="container" style="display: none">
        <div class="columns">
            <div class="column is-2">
                <aside class="menu ">
                    <p class="menu-label">
                        Categories
                    </p>
                    <ul class="menu-list categories">
                        <li v-for="category in categories"><a>{{category.name}}</a></li>
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
                <section class="hero is-white">
                    <div class="hero-body">
                    </div>
                </section>



                <div class="columns">
                </div>
            </div>
        </div>
    </div>
</t:header>
</html>

