<%@ taglib prefix="t" tagdir="/WEB-INF/tags/admin" %>

<%--
  Created by IntelliJ IDEA.
  User: vladyslavprosolupov
  Date: 13.06.17
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<t:wrapper>


    <div id="loading" class="is-centered" style="position: inherit">
        <div class="spinner">
            <div class="double-bounce1"></div>
            <div class="double-bounce2"></div>
        </div>
    </div>

    <div class="films" style="display: none">
        <button class="addFilm button is-primary ">Add</button>
    </div>

    <table class="films table" style="display: none">
        <thead>
        <tr>
            <th>Title</th>
            <th>Release date</th>
            <th>Rating</th>
            <th>Length</th>
            <th colspan="2"></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="film in films" :id="film[4]">
            <td>{{film[0]}}</td>
            <td>{{film[1]}}</td>
            <td>{{film[2]}}</td>
            <td>{{film[3]}}</td>
            <td>
                <button class="button is-primary" v-on:click="editFilm(film[4])">Edit</button>
            </td>
            <td>
                <button class="button is-danger" v-on:click="deleteFilm(film[4])">Delete</button>
            </td>
        </tr>
        </tbody>
    </table>

</t:wrapper>
