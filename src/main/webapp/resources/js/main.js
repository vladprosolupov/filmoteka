/**
 * Created by vladyslavprosolupov on 13.06.17.
 */
$(function () {
    var films = new Vue({
        el: '.films',
        data: {
            films: []
        },
        beforeCompile: function () {
            var self = this;
            $.getJSON('/film/allForIndex', function (data) {
               $('#loading').hide();
               $('.container').show();
               self.films = data;
               console.log(data);
            });
        }
    });

});