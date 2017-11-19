<%@ taglib prefix="t" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Rostyk
  Date: 17.06.2017
  Time: 19:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<t:wrapper>
    <div id="loading">        <div class="spinner">
        <div class="double-bounce1"></div>
        <div class="double-bounce2"></div>
    </div></div>
    <c:choose>
        <c:when test="${actor.id != 0}">
            <form class="formForActor formStyling" data-actor="${actor.id}" style="display: none">
                <div class="field">
                    <label class="label">Name</label>
                    <p class="control">
                        <input class="input" type="text" value="${actor.firstName}" name="firstName"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Surname</label>
                    <p class="control">
                        <input class="input" type="text" value="${actor.lastName}" name="lastName"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Birthday</label>
                    <p class="control">
                        <input class="input" type="date" value="${actor.birthdate}" name="birthdate"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Country</label>
                    <div class="control">
                        <p class="control">
                            <span class="select">
                                <select name="country">
                                    <c:forEach items="${countries}" var="country">
                                        <option value="${country.id}" ${country.id == actor.countryByIdCountry.id ? 'selected="selected"' : ''}
                                        >${country.name}</option>
                                    </c:forEach>
                                </select>
                            </span>
                        </p>
                    </div>
                </div>
            </form>
            <div class="field saveButton">
                <p class="control">
                    <button type="button" class="save button is-primary">Save actor</button>
                </p>
            </div>
        </c:when>
        <c:otherwise>
            <form class="formForActor formStyling" data-actor="0" style="display: none">
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
                    <label class="label">Birthday</label>
                    <p class="control">
                        <input class="input" type="date" name="birthdate"/>
                    </p>
                </div>
                <div class="field">
                    <label class="label">Country</label>
                    <div class="control">
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
                </div>
            </form>
            <div class="field saveButton">
                <p class="control">
                    <button type="button" class="save button is-primary">Save actor</button>
                </p>
            </div>
        </c:otherwise>
    </c:choose>

</t:wrapper>
