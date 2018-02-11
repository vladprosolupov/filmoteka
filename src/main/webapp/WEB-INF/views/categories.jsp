<%--
  Created by IntelliJ IDEA.
  User: vladyslavprosolupov
  Date: 13.12.2017
  Time: 1:13
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/main" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Categories</title>
</head>
<t:header>
    <div id="loading" class="is-centered">
        <div class="spinner">
            <div class="double-bounce1"></div>
            <div class="double-bounce2"></div>
        </div>
    </div>
    <div class="container vue" style="display: none">
        <div class="columns centered" style="margin-top: 2ch">
            <div class="films categories">
                <div v-for="category in categories" class="card effect-ruby grow film">
                    <a v-on:click="goToCategory(category.id)"></a>
                    <div class="card-content">
                        <div class="media">
                            <div class="media-content">
                                <p class="title is-6" style="text-align: center; white-space: nowrap;">{{category.name}}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:header>
</html>
