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
    <div id="loading" style="position: inherit">
        <div class="spinner">
            <div class="double-bounce1"></div>
            <div class="double-bounce2"></div>
        </div>
    </div>

    <div class="networks" style="display: none">
        <button class="addNetwork button is-primary">Add</button>
    </div>

    <table class="networks table" style="display: none">
        <thead>
        <tr>
            <th>Name</th>
            <th>Logo</th>
            <th colspan="2"></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="network in networks" :id="network.id">
            <td>{{network.networkName}}</td>
            <td>
                <figure class="image is-48x48"><img :src="network.networkLogo"/></figure>
                    <%--{{network.networkLogo}}--%></td>
            <td>
                <button class="button is-primary" v-on:click="editNetwork(network.id)">Edit</button>
            </td>
            <td>
                <button class="button is-danger" v-on:click="deleteNetwork(network.id)">Delete</button>
            </td>
        </tr>
        </tbody>
    </table>
</t:wrapper>