$(function () {
    $('input[name="firstName"]').keyup(function () {
        if(this.value.length >= 2 && !this.value.match(/\d+/g) && this.value.length <= 64){
            if(this.classList.contains("is-danger")) {
                if($('.help')[0].style.display == "block") $('.help')[0].style.display = "none";
                $(this).removeClass("is-danger");
            }
            $(this).addClass("is-success");
        }else {
            if(this.classList.contains("is-success")) $(this).removeClass("is-success");
            $('.help')[0].style.display = "block";
            $(this).addClass("is-danger");
        }
    });
    $('input[name="lastName"]').keyup(function () {
        if(this.value.length >= 2 && !this.value.match(/\d+/g) && this.value.length <= 64){
            if(this.classList.contains("is-danger")) {
                if($('.help')[1].style.display == "block") $('.help')[1].style.display = "none";
                $(this).removeClass("is-danger");
            }
            $(this).addClass("is-success");
        }else {
            if(this.classList.contains("is-success")) $(this).removeClass("is-success");
            $('.help')[1].style.display = "block";
            $(this).addClass("is-danger");
        }
    });
    $('input[name="email"]').keyup(function () {
        if(this.value.match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)){
            if(this.classList.contains("is-danger")) {
                if($('.help')[2].style.display == "block") $('.help')[2].style.display = "none";
                $(this).removeClass("is-danger");
            }
            $(this).addClass("is-success");
        }else {
            if(this.classList.contains("is-success")) $(this).removeClass("is-success");
            $('.help')[2].style.display = "block";
            $(this).addClass("is-danger");
        }
    });
    $('input[name="login"]').keyup(function () {
        if(this.value.length >= 2 && this.value.length <= 64){
            if(this.classList.contains("is-danger")) {
                if($('.help')[3].style.display == "block") $('.help')[3].style.display = "none";
                if($('.help')[4].style.display == "block") $('.help')[4].style.display = "none";
                $(this).removeClass("is-danger");
            }
            $(this).addClass("is-success");
        }else {
            if(this.classList.contains("is-success")){
                $(this).removeClass("is-success");
            }
            $(this).addClass("is-danger");
            $('.help')[3].style.display = "block";
        }
    });

    $('input[name="password"]').keyup(function () {
        if(this.value.length >= 8){
            if(this.classList.contains("is-danger")){
                if($('.help')[5].style.display == "block") $('.help')[5].style.display = "none";
                $(this).removeClass("is-danger");
            }
            $(this).addClass("is-success");
        }else {
            if(this.classList.contains("is-success")){
                $(this).removeClass("is-success");
            }
            $('.help')[5].style.display = "block";
            $(this).addClass("is-danger");
        }
    });

    $('input[name="ignore"]').keyup(function () {
        if(this.value.length >= 8){
            if($('.help')[6].style.display == "block") $('.help')[6].style.display = "none";
            if($('.help')[7].style.display == "block") $('.help')[7].style.display = "none";
            if(this.value !== $('input[name="password"]').val()){
                if(this.classList.contains("is-success")){
                    $(this).removeClass("is-success");
                }
                $('.help')[7].style.display = "block";
                $(this).addClass("is-danger");
            }else {
                if(this.classList.contains("is-danger")){
                    if($('.help')[6].style.display == "block") $('.help')[6].style.display = "none";
                    if($('.help')[7].style.display == "block") $('.help')[7].style.display = "none";
                    $(this).removeClass("is-danger");
                }
                $(this).addClass("is-success");
            }
        }else {
            if(this.classList.contains("is-success")){
                $(this).removeClass("is-success");
            }
            $('.help')[6].style.display = "block";
            $(this).addClass("is-danger");
        }
    });
    $('input[name="phoneNumber"]').keyup(function(event){
        if(this.value.length > 6){
            $('.help')[8].style.display = "none";
            if(this.classList.contains("is-danger")){
                $(this).removeClass("is-danger");
            }
            $(this).addClass("is-success");
        }else {
            $('.help')[8].style.display = "block";
            if(this.classList.contains("is-success")){
                $(this).removeClass("is-success");
            }
            $(this).addClass("is-danger");
        }
    });
    $('input[name="phoneNumber"]').keypress(function () {
        if (event.keyCode < 43 || event.keyCode > 57 || event.keyCode === 44 || event.keyCode === 45 || event.keyCode === 46 || event.keyCode === 47 || (event.keyCode === 43 && this.value.length !== 0))
            return false;
    });






    // $('.save').click(function () {
    //     $('.save').addClass("is-loading");
    //     var token = $("meta[name='_csrf']").attr("content");
    //     var header = $("meta[name='_csrf_header']").attr("content");
    //     $(document).ajaxSend(function (e, xhr, options) {
    //         xhr.setRequestHeader(header, token);
    //     });
    //
    //     var elements = document.getElementsByClassName("clientForm")[0].elements;
    //     var client = {};
    //     for (var i = 0; i < elements.length; i++) {
    //         var item = elements.item(i);
    //         if (item.name !== 'ignore') {
    //             client[item.name] = item.value;
    //         }
    //     }
    //
    //     $.ajax({
    //         url: '/client/save',
    //         type: 'POST',
    //         data: JSON.stringify(client),
    //         contentType: 'application/json',
    //         success: function (data) {
    //             console.log(data);
    //             window.location.replace("http://localhost:8080/login");
    //         },
    //         error: function (xhr, textStatus, errorThrown) {
    //             console.log('Error in Operation');
    //             console.log('Text status: ' + textStatus);
    //             console.log('XHR: ' + xhr);
    //             console.log('Error thrown: ' + errorThrown);
    //         }
    //     });
    // });
});