

<%--
  Created by IntelliJ IDEA.
  User: GermanV
  Date: 10.11.17
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <link rel="stylesheet" href="<c:url value="/resources/styles/main.css"/>">

    <title>Title</title>
</head>
<body>

<section class="hero background_for_login is-success is-fullheight">
    <div class="hero-body">
        <div class="container has-text-centered">
            <div class="column is-4 is-offset-4">
                <h3 class="title has-text-grey">Registration</h3>
                <p class="subtitle has-text-grey">Please register to proceed.</p>
                <div class="box">
                    <form>
                        <div class="field">
                            <div class="control has-icons-left">
                                <input class="input is-medium" type="text" value="${client.firstName}" placeholder="Your First Name" autofocus="">
                            </div>
                        </div>

                        <div class="field">
                            <div class="control has-icons-left">
                                <input class="input is-medium" type="text" value="${client.lastName}" placeholder="Your Last Name" autofocus="">
                            </div>
                        </div>

                        <div class="field">
                            <div class="control has-icons-left">
                                <input class="input is-medium" type="text" value="${client.login}" placeholder="Your Username" autofocus="">
                                <span class="icon is-small is-left">
      <i class="fa fa-user"></i>
    </span>
                            </div>
                        </div>

                        <div class="field">
                            <div class="control has-icons-left">
                                <input class="input is-medium" type="email" value="${client.email}" placeholder="Your Email" autofocus="">

                            </div>
                        </div>

                        <div class="field">
                            <div class="control has-icons-left">
                                <input class="input is-medium" type="password" value="${client.password}" placeholder="Your Password">
                            </div>
                        </div>

                        <div class="field">
                            <div class="control has-icons-left">
                                <input class="input is-medium" type="number" value="${client.phoneNumber}" placeholder="Your Phone Number">
                            </div>
                        </div>
                        <input name="submit" class="button is-primary is-large" type="submit" value="Register"/>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<%--<script async type="text/javascript" src="../js/vue.js"></script>--%>
<script src="<c:url value="/resources/js/vue.js"/>"></script>

</body>
</html>
