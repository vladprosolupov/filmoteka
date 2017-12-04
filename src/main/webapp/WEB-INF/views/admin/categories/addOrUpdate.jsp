<%@ taglib prefix="t" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Rostyk
  Date: 17.06.2017
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<t:wrapper>
    <div id="loading">
        <div class="spinner">
            <div class="double-bounce1"></div>
            <div class="double-bounce2"></div>
        </div>
    </div>
    <div class="back" style="display: none;">
        <button class="button is-dark is-pulled-left has-icons-left" onclick="goBack('categories')">
            <i class="fa fa-chevron-left" style="margin-right: 5px;" aria-hidden="true"></i>
            Back
        </button>
    </div>
    <c:choose>
        <c:when test="${category.id != 0}">
            <form class="formForCategory formStyling" data-category="${category.id}" style="display: none">
                <div class="field">
                    <label class="label">Name</label>
                    <p class="control">
                        <input class="input" type="text" value="${category.name}" name="name"/>
                    </p>
                </div>
            </form>
            <div class="field saveButton">
                <p class="control">
                    <button type="button" class="save button is-primary">Save category</button>
                </p>
            </div>
        </c:when>
        <c:otherwise>
            <form class="formForCategory formStyling" data-category="0" style="display: none">
                <div class="field">
                    <label class="label">Name</label>
                    <p class="control">
                        <input class="input" type="text" name="name"/>
                    </p>
                </div>
            </form>
            <div class="field saveButton">
                <p class="control">
                    <button type="button" class="save button is-primary">Save category</button>
                </p>
            </div>
        </c:otherwise>
    </c:choose>

</t:wrapper>
