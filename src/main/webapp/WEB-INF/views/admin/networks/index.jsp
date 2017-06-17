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
    <div id="loading">Loading...</div>
    <table class="networks table" style="display: none">
        <thead>
            <tr>
                <th>Name</th>
                <th>Logo</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="network in networks" :id="network.id">
                <td>{{network.networkName}}</td>
                <td>{{network.networkLogo}}</td>
                <td>
                    <button class="button is-primary" v-on:click="editNetwork(network.id)">Edit</button>
                </td>
                <td>
                    <button class="button is-danger" v-on:click="deleteNetwork(network.id)">Delete</button>
                </td>
            </tr>
        </tbody>
    </table>
    <div class="networks" style="display: none">
        <button class="addNetwork button is-primary">Add</button>
    </div>
</t:wrapper>