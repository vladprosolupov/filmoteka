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


    <div id="loading" class="is-centered">Loading...</div>


    <table class="films table" style="display: none">
        <thead>
        <tr>
            <th>Title</th>
            <th>Release date</th>
            <th>Rating</th>
            <th>Language</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="film in films" :id="film.id">
            <td>{{film.title}}</td>
            <td>{{film.releaseDate}}</td>
            <td>{{film.rating}}</td>
            <td>{{film.languageByIdLanguage.name}}</td>
            <td>
                <button class="button is-primary" v-on:click="editFilm(film.id)">Edit</button>
            </td>
            <td>
                <button class="button is-danger" v-on:click="deleteFilm(film.id)">Delete</button>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="films" style="display: none">
        <button class="addFilm button is-primary ">Add</button>
    </div>

</t:wrapper>
