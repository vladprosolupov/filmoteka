<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/main" %>

<%--
  Created by IntelliJ IDEA.
  User: vladyslavprosolupov
  Date: 06.12.2017
  Time: 23:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search</title>
</head>
<t:header>
    <div class="container vue">
        <div class="columns">
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
            </div>
        </div>
    </div>
</t:header>
</html>
