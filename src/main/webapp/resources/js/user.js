$(function () {
    $('.save').click(function () {
        $('.save').addClass("is-loading");
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });

        var elements = document.getElementsByClassName("clientForm")[0].elements;
        var client = {};
        for (var i = 0; i < elements.length; i++) {
            var item = elements.item(i);
            if (item.name !== 'ignore') {
                client[item.name] = item.value;
            }
        }

        $.ajax({
            url: '/client/save',
            type: 'POST',
            data: JSON.stringify(client),
            contentType: 'application/json',
            success: function (data) {
                console.log(data);
                window.location.replace("http://localhost:8080/login");
            },
            error: function (xhr, textStatus, errorThrown) {
                console.log('Error in Operation');
                console.log('Text status: ' + textStatus);
                console.log('XHR: ' + xhr);
                console.log('Error thrown: ' + errorThrown);
            }
        });
    });
});