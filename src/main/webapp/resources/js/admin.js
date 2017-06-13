/**
 * Created by vladyslavprosolupov on 13.06.17.
 */
$(function () {
    window.bus = new Vue();

    if (window.location.href.startsWith('/films',21)) {
        $.getJSON('/film/all', function (data) {
            bus.$emit('filmsLoaded', data);
        });

        bus.$on('filmsLoaded', function (data) {
            var films = new Vue({
                el: '#films',
                data: {
                    films: data
                }
            });
        });
    }
});