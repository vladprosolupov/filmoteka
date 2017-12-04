<%--
  Created by IntelliJ IDEA.
  User: vladprosolupov
  Date: 29.11.2017
  Time: 23:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/admin" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<t:wrapper>


    <div id="loading" class="is-centered" style="position: inherit">
        <div class="spinner">
            <div class="double-bounce1"></div>
            <div class="double-bounce2"></div>
        </div>
    </div>

    <table class="users table" style="display: none">
        <thead>
        <tr>
            <th>Login</th>
            <th>Email</th>
            <th>Name</th>
            <th>Surname</th>
            <th colspan="2"></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="user in users">
            <td>{{user[2]}}</td>
            <td>{{user[3]}}</td>
            <td>{{user[4]}}</td>
            <td>{{user[5]}}</td>
            <td>
                <button class="button is-primary" v-on:click="more(user[0])">More</button>
            </td>
            <td v-if="user[1] == 1 && user[2] != 'admin'">
                <button class="button is-danger" v-on:click="blockUser(user[2], $event)">Block</button>
            </td>
            <td v-if="user[1] == 0 && user[2] != 'admin'">
                <button class="button is-info" v-on:click="unblockUser(user[2], $event)">Unblock</button>
            </td>
            <td v-if="user[2] == 'admin'">
                <button class="button is-black">ADMIN</button>
            </td>
        </tr>
        </tbody>
    </table>

</t:wrapper>

