/**
 * Created by vladyslavprosolupov on 13.06.17.
 */
$(function () {
    window.bus = new Vue();

    if (window.location.href.substr(28) == 'films') {
        var films = new Vue({
            el: '#films',
            data: {
                films: []
            },
            beforeCompile: function () {
                var self = this;
                $.getJSON('/film/all', function (data) {
                    $('#loading').css('display', 'none');
                    $('#films').css('display', 'block');
                    self.films = data;
                });
            }
        });
    }
});