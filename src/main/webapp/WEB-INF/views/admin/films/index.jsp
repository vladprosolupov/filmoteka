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
    <div id="loading">Loading...</div>
    <table class="films" style="display: none">
        <thead>
        <tr>
            <th>Title</th>
            <th>Release date</th>
            <th>Length</th>
            <th>Description</th>
            <th>Slogan</th>
            <th>Rating</th>
            <th>Age</th>
            <th>Budget</th>
            <th>Language</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="film in films" :id="film.id">
            <td>{{film.title}}</td>
            <td>{{film.releaseDate}}</td>
            <td>{{film.lenght}}</td>
            <td>{{film.description}}</td>
            <td>{{film.slogan}}</td>
            <td>{{film.rating}}</td>
            <td>{{film.age}}</td>
            <td>{{film.budget}}</td>
            <td>{{film.languageByIdLanguage.name}}</td>
            <td><button <%--href="/admin/films/addOrUpdate?id={{film.id}}"--%> v-on:click="editFilm(film.id)">Edit</button></td>
            <td><button v-on:click="deleteFilm(film.id)">Delete</button></td>
        </tr>
        </tbody>
    </table>
    <div class="films" style="display: none">
        <button class="addFilm">Add</button>
    </div>
</t:wrapper>
