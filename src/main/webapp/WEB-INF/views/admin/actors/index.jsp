<%@ taglib prefix="t" tagdir="/WEB-INF/tags/admin" %>
<%--
  Created by IntelliJ IDEA.
  User: vladyslavprosolupov
  Date: 13.06.17
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<t:wrapper>
    <div id="loading" style="position: inherit">
        <div class="spinner">
            <div class="double-bounce1"></div>
            <div class="double-bounce2"></div>
        </div>
    </div>
    <table class="actors table" style="display: none">
        <thead>
        <tr>
            <th>First name</th>
            <th>Last name</th>
            <th>Country</th>
            <th colspan="2"></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="actor in actors" :id="actor.id">
            <td>{{actor.firstName}}</td>
            <td>{{actor.lastName}}</td>
            <td>{{actor.countryByIdCountry.name}}</td>
            <td>
                <button class="button is-primary" v-on:click="editActor(actor.id)">Edit</button>
            </td>
            <td>
                <button class="button is-danger" v-on:click="deleteActor(actor.id)">Delete</button>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="actors" style="display: none">
        <button class="addActor button is-primary">Add</button>
    </div>
</t:wrapper>
