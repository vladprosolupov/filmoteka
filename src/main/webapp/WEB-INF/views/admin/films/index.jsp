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
    <table id="films" style="display: none">
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
            <td><a href="/admin/films/addOrUpdate?id={{film.id}}">Edit</a></td>
            <td><a>Delete</a></td>
        </tr>
        </tbody>
    </table>
    <div>
        <a href="/admin/films/addOrUpdate">Add</a>
    </div>
</t:wrapper>
