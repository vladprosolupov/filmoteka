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
    <table id="films">
        <thead>
            <th>Name</th>
        </thead>
        <tbody v-on="filmsLoaded">
            <tr v-for="film in films">
                <td>{{film.title}}</td>
            </tr>
        </tbody>
    </table>
</t:wrapper>
