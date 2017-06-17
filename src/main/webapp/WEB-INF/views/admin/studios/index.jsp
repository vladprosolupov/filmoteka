
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
    <div id="loading">        <div class="spinner">
        <div class="double-bounce1"></div>
        <div class="double-bounce2"></div>
    </div></div>
    <table class="studios table" style="display: none">
        <thead>
        <tr>
            <th>Name</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="studio in studios" :id="studio.id">
            <td>{{studio.studioName}}</td>
            <td>
                <button class="button is-primary" v-on:click="editStudio(studio.id)">Edit</button>
            </td>
            <td>
                <button class="button is-danger" v-on:click="deleteStudio(studio.id)">Delete</button>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="studios" style="display: none">
        <button class="addStudio button is-primary">Add</button>
    </div>
</t:wrapper>