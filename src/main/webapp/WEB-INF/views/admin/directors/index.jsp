<%@ taglib prefix="t" tagdir="/WEB-INF/tags/admin" %>
<%--
  Created by IntelliJ IDEA.
  User: vladyslavprosolupov
  Date: 13.06.17
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<t:wrapper>
    <div id="loading">        <div class="spinner">
        <div class="double-bounce1"></div>
        <div class="double-bounce2"></div>
    </div></div>
    <table class="directors table" style="display: none">
        <thead>
            <tr>
                <th>Firs name</th>
                <th>Last name</th>
                <th>Country</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="director in directors" :id="director.id">
                <td>{{director.firstName}}</td>
                <td>{{director.lastName}}</td>
                <td>{{director.countryByIdCountry.name}}</td>
                <td>
                    <button class="button is-primary" v-on:click="editDirector(director.id)">Edit</button>
                </td>
                <td>
                    <button class="button is-danger" v-on:click="deleteDirector(director.id)">Delete</button>
                </td>
            </tr>
        </tbody>
    </table>
    <div class="directors" style="display: none">
        <button class="addDirector button is-primary">Add</button>
    </div>
</t:wrapper>
