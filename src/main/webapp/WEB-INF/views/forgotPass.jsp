<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/main" %>
<%--
  Created by IntelliJ IDEA.
  User: vladprosolupov
  Date: 27.11.2017
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forgot password</title>
    <link rel="stylesheet" href="<c:url value="/resources/styles/main.css"/>">

    <!-- Including CSRF token to the header -->
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<t:header>
    <div id="login-box" onload='document.forgotPasswordForm.clientEmail.focus();'>
        <section class="hero is-fullheight is-bold">
            <div class="hero-body">
                <div class="container has-text-centered">
                    <div class="column is-4 is-offset-4">
                        <h3 class="title has-text-white">Forgot password</h3>
                        <article class="message is-danger" style="display: none;">
                            <div class="message-body">
                                Sorry, but user with such <strong>email</strong> is not found.
                            </div>
                        </article>
                        <form name='forgotPasswordForm'
                              action="/client/forgotPassword" method='POST' onsubmit="return false">
                            <div class="box">
                                <div class="field">
                                    <p class="control has-icons-left">
                                        <input class="input" type='text' name='clientEmail' placeholder="Email" autocomplete="off">
                                        <span class="icon is-small is-left">
                                          <i class="fa fa-envelope"></i>
                                        </span>
                                    </p>
                                    <p class="help is-danger" style="display: none">
                                        Please enter valid email!
                                    </p>
                                </div>
                                <div class="columns">
                                    <div class="column has-text-centered has-text-info is-half ">
                                        <a href="/login" style="text-decoration: none">Login</a>
                                    </div>

                                    <div class="column has-text-centered has-text-info is-half">
                                        <a href="/register" style="text-decoration: none">Sign up!</a>
                                    </div>
                                </div>

                                <hr>
                                <button class="button is-primary is-medium" type="submit">Reset password</button>
                            </div>

                            <%--<sec:csrfInput/>--%>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </div>
</t:header>
<script src="<c:url value="/resources/js/jquery-3.2.1.js"/>"></script>
<script>
    var domain = window.location.origin;
    var flag = false;
    $(function () {
        $('input[name="clientEmail"]').keyup(function () {
            if(this.value.match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)){
                if(this.classList.contains("is-danger")) {
                    if($('.help')[0].style.display == "block") $('.help')[0].style.display = "none";
                    $(this).removeClass("is-danger");
                }
                flag = true;
                $(this).addClass("is-success");
            }else {
                if(this.classList.contains("is-success")) $(this).removeClass("is-success");
                $('.help')[0].style.display = "block";
                $(this).addClass("is-danger");
                flag = false;
            }
        });

        $('input[name="clientEmail"]').change(function () {
            if(this.value.match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)){
                if(this.classList.contains("is-danger")) {
                    if($('.help')[0].style.display == "block") $('.help')[0].style.display = "none";
                    $(this).removeClass("is-danger");
                }
                flag = true;
                $(this).addClass("is-success");
            }else {
                if(this.classList.contains("is-success")) $(this).removeClass("is-success");
                $('.help')[0].style.display = "block";
                $(this).addClass("is-danger");
                flag = false;
            }
        });

        $('button[class*="button"]').click(function () {
            var self = this;
            if($('.message')[0].style.display !== "none"){
                $('.message').slideUp();
            }
            if(flag){
                var email = $('input[name="clientEmail"]').val();
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                $(document).ajaxSend(function (e, xhr, options) {
                    xhr.setRequestHeader(header, token);
                });

                $(self).addClass("is-loading");
                $.ajax({
                    url: $('form[name="forgotPasswordForm"]').attr('action'),
                    type: 'POST',
                    contentType: 'application/json; utf-8',
                    data: email,
                    success: function (data) {
                        if(data.name != "error")
                            window.location.replace(domain + '/login?reset=false');
                        else {
                            $('.message').slideDown();
                            $(self).removeClass("is-loading");
                            $('input[name="clientEmail"]').addClass("is-danger");
                        }
                    },
                    fail: function () {
                        $('.message').slideDown();
                    }
                });
            }
        });
    });
</script>

</html>
