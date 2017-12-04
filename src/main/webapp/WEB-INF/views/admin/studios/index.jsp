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

    <div class="show" style="display: none">
        <button class="addStudio button is-primary">Add</button>
    </div>

    <table class="studios table show" style="display: none">
        <thead>
        <tr>
            <th>Name</th>
            <th colspan="2"></th>
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
</t:wrapper>