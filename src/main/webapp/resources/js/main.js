/**
 * Created by vladyslavprosolupov on 13.06.17.
 */
var flag = false;

function hideLoading() {
    if (flag == false) {
        flag = true;
    } else {
        $('#loading').hide();
        $('.container').show();
    }
}

$(function () {
    var categories = new Vue({
        el: '.categories',
        data: {
            categories: []
        },
        beforeCompile: function () {
            var self = this;
            $.getJSON('/category/all', function (data) {
                hideLoading();
                self.categories = data;
            });
        }
    });

    if (window.location.pathname === "" || window.location.pathname === "/" || window.location.pathname === "/index" || window.location.pathname === null) {
        var films = new Vue({
            el: '.films',
            data: {
                films: [],
                link: "/film/"
            },
            beforeCompile: function () {
                var self = this;
                $.getJSON('/film/allForIndex', function (data) {
                    hideLoading();
                    self.films = data;
                });
            },
            methods: {
                getYear: function (val) {
                    var date = new Date(Date.parse(val));
                    return date.getFullYear();
                }
            }
        });
    }else if(window.location.pathname.includes("/film/")){
        var url = '/film/info/'+ window.location.href.slice(-1);
        var film = new Vue({
            el: '.info',
            data:{
                film: []
            },
            beforeCompile: function () {
                var self = this;
                $.getJSON(url, function (data) {
                    hideLoading();
                    console.log(data);
                    self.film = data;
                });
            }
        });

    }else {
        hideLoading();
    }
});