<%@ taglib prefix="t" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Rostyk
  Date: 17.06.2017
  Time: 19:05
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
        <button class="button is-dark is-pulled-left has-icons-left" onclick="goBack('directors')">
            <i class="fa fa-chevron-left" style="margin-right: 5px;" aria-hidden="true"></i>
            Back
        </button>
    </div>
    <c:choose>
        <c:when test="${director.id != 0}">
            <form class="formForDirector formStyling" data-director="${director.id}" style="display: none">
                <div class="field">
                    <label class="label">Name</label>
                    <p class="control">
                        <input class="input" type="text" value="${director.firstName}" name="firstName"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Surname</label>
                    <p class="control">
                        <input class="input" type="text" value="${director.lastName}" name="lastName"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Country</label>
                    <p class="control">
                        <span class="select">
                             <select name="country">
                                 <c:forEach items="${countries}" var="country">
                                     <option value="${country.id}" ${country.id == director.countryByIdCountry.id ? 'selected="selected"' : ''}>${country.name}</option>
                                 </c:forEach>
                             </select>
                         </span>
                    </p>
                </div>
            </form>
            <div class="field saveButton">
                <p class="control">
                    <button type="button" class="save button is-primary">Save director</button>
                </p>
            </div>
        </c:when>
        <c:otherwise>
            <form class="formForDirector formStyling" data-director="0" style="display: none">
                <div class="field">
                    <label class="label">Name</label>
                    <p class="control">
                        <input class="input" type="text" name="firstName"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Surname</label>
                    <p class="control">
                        <input class="input" type="text" name="lastName"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Country</label>
                    <p class="control">
                         <span class="select">
                             <select name="country">
                                 <c:forEach items="${countries}" var="country">
                                     <option value="${country.id}">${country.name}</option>
                                 </c:forEach>
                             </select>
                         </span>
                    </p>
                </div>
            </form>
            <div class="field saveButton">
                <p class="control">
                    <button type="button" class="save button is-primary">Save director</button>
                </p>
            </div>
        </c:otherwise>
    </c:choose>

</t:wrapper>
