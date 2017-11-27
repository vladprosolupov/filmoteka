<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/main" %>
<%@page session="true" %>
<html>
<head>
    <title>Reset password</title>
    <link rel="stylesheet" href="<c:url value="/resources/styles/main.css"/>">

    <!-- Including CSRF token to the header -->
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<t:header>

    <div id="login-box" onload='document.updatePasswordForm.password.focus();'>
        <section class="hero is-fullheight is-bold">
            <div class="hero-body">
                <div class="container has-text-centered">
                    <div class="column is-4 is-offset-4">
                        <h3 class="title has-text-white">New password</h3>
                        <form name='updatePasswordForm'
                              action="/client/savePassword" method='POST' onsubmit="return false">
                            <div class="box">
                                <div class="field">
                                    <div class="control has-icons-left has-icon-right">
                                        <input class="input is-medium" type="password" name="password"
                                                    placeholder="Password" path="password"/>
                                        <span class="icon is-small is-left">
                                    <i class="fa fa-lock"></i>
                                </span>
                                    </div>
                                    <p class="help is-danger" style="display:none">
                                        Password cannot be shorter than 8 symbols!
                                    </p>
                                </div>

                                <div class="field">
                                    <div class="control has-icons-left has-icons-right">
                                        <input class="input is-medium" type="password" name="ignore"
                                               placeholder="Confirm password">
                                        <span class="icon is-small is-left">
                                    <i class="fa fa-check"></i>
                                </span>
                                        <span class="icon is-small is-right" style="display: none">
                                    <i class="fa fa-warning"></i>
                                </span>
                                    </div>
                                    <p class="help is-danger" style="display:none">
                                        Password cannot be shorter than 8 symbols!
                                    </p>
                                    <p class="help is-danger" style="display: none">
                                        Passwords are not same!
                                    </p>
                                </div>

                                <hr>
                                <button class="button is-primary is-medium" type="submit">Set new password</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </div>

</t:header>
<script src="<c:url value="/resources/js/jquery-3.2.1.js"/>"></script>
<script>
    var lastslashindex = window.location.pathname.lastIndexOf('/');
    var URLtoken = window.location.pathname.substring(lastslashindex + 1);
    var domain = window.location.origin;
    var flags = [false,false];
    $(function () {
        $('input[name="password"]').keyup(function () {
            if (this.value.length >= 8) {
                if (this.classList.contains("is-danger")) {
                    if ($('.help')[0].style.display == "block") $('.help')[0].style.display = "none";
                    $(this).removeClass("is-danger");
                }
                flags[0] = true;
                $(this).addClass("is-success");
            } else {
                if (this.classList.contains("is-success")) {
                    $(this).removeClass("is-success");
                }
                flags[0] = false;
                $('.help')[0].style.display = "block";
                $(this).addClass("is-danger");
            }
        });
        $('input[name="password"]').change(function () {
            if (this.value.length >= 8) {
                if (this.classList.contains("is-danger")) {
                    if ($('.help')[0].style.display == "block") $('.help')[0].style.display = "none";
                    $(this).removeClass("is-danger");
                }
                flags[0] = true;
                $(this).addClass("is-success");
            } else {
                if (this.classList.contains("is-success")) {
                    $(this).removeClass("is-success");
                }
                flags[0] = false;
                $('.help')[0].style.display = "block";
                $(this).addClass("is-danger");
            }
        });

        $('input[name="ignore"]').keyup(function () {
            if (this.value.length >= 8) {
                if ($('.help')[1].style.display == "block") $('.help')[1].style.display = "none";
                if ($('.help')[2].style.display == "block") $('.help')[2].style.display = "none";
                if (this.value !== $('input[name="password"]').val()) {
                    if (this.classList.contains("is-success")) {
                        $(this).removeClass("is-success");
                    }
                    flags[1] = false;
                    $('.help')[2].style.display = "block";
                    $(this).addClass("is-danger");
                } else {
                    if (this.classList.contains("is-danger")) {
                        if ($('.help')[1].style.display == "block") $('.help')[1].style.display = "none";
                        if ($('.help')[2].style.display == "block") $('.help')[2].style.display = "none";
                        $(this).removeClass("is-danger");
                    }
                    flags[1] = true;
                    $(this).addClass("is-success");
                }
            } else {
                if (this.classList.contains("is-success")) {
                    $(this).removeClass("is-success");
                }
                flags[1] = false;
                $('.help')[1].style.display = "block";
                $(this).addClass("is-danger");
            }
        });
        $('input[name="ignore"]').change(function () {
            if (this.value.length >= 8) {
                if ($('.help')[1].style.display == "block") $('.help')[1].style.display = "none";
                if ($('.help')[2].style.display == "block") $('.help')[2].style.display = "none";
                if (this.value !== $('input[name="password"]').val()) {
                    if (this.classList.contains("is-success")) {
                        $(this).removeClass("is-success");
                    }
                    flags[1] = false;
                    $('.help')[2].style.display = "block";
                    $(this).addClass("is-danger");
                } else {
                    if (this.classList.contains("is-danger")) {
                        if ($('.help')[1].style.display == "block") $('.help')[1].style.display = "none";
                        if ($('.help')[2].style.display == "block") $('.help')[2].style.display = "none";
                        $(this).removeClass("is-danger");
                    }
                    flags[1] = true;
                    $(this).addClass("is-success");
                }
            } else {
                if (this.classList.contains("is-success")) {
                    $(this).removeClass("is-success");
                }
                flags[1] = false;
                $('.help')[1].style.display = "block";
                $(this).addClass("is-danger");
            }
        });
        $('button[class*="button"]').click(function () {
            var $password = $('input[name="password"]').val();
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });

            var clientPassword = {};
            clientPassword['password'] = $password;
            if(!flags.includes(false)) {
                $(this).addClass("is-loading");
                $.ajax({
                    url: $('form[name="updatePasswordForm"]').attr('action') + "/" + URLtoken,
                    type: 'POST',
                    contentType: 'application/json; utf-8',
                    data: JSON.stringify(clientPassword),
                    success: function () {
                        window.location.replace(domain + '/login?reset=true');
                    }
                });
            }
        });
    });
</script>
</html>