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
    <div id="loading">        <div class="spinner">
        <div class="double-bounce1"></div>
        <div class="double-bounce2"></div>
    </div></div>
    <table class="categories table" style="display: none">
        <thead>
            <tr>
                <th>Name</th>
                <th colspan="2"></th>
            </tr>
        </thead>
        <tbody>
        <tr v-for="category in categories" :id="category.id">
            <td>{{category.name}}</td>
            <td>
                <button class="button is-primary" v-on:click="editCategory(category.id)">Edit</button>
            </td>
            <td>
                <button class="button is-danger" v-on:click="deleteCategory(category.id)">Delete</button>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="categories" style="display: none">
        <button class="addCategory button is-primary">Add</button>
    </div>
</t:wrapper>