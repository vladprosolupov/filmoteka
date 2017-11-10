$(function () {
    $('.addClient').click(function () {
        $('.addClient').addClass("is-loading");
        var clients = new Vue({
            el: '.newClient',
            data: {
                clients: []
            },
            beforeCompile: function () {
                var self = this;
                $.getJSON('/client', function (data) {
                    $('.addClient').removeClass("is-loading");
                    $('.newClient').show();
                    self.clients = data;
                });
            },
            methods: {
                saveClient: function () {
                    $('.newClient').hide();
                    var selected = $('.clientOptions').find(":selected");
                    var role = $('.clientRole').val();
                    var row = "<tr class='client'>" +
                        "<td data-client='" + selected.val() + "'>" +
                        selected.text() +
                        "</td>" +
                        "<td data-clientRole>" +
                        role +
                        "</td>" +
                        "<td>" +
                        "</td>" +
                        "</tr>";
                    $(row).insertBefore(".clients tr[class='newClient']");
                }
            }
        });

    });
});