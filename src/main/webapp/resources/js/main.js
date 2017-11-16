/**
 * Created by vladyslavprosolupov on 13.06.17.
 */
function hideLoading() {
    $('#loading').hide();
    $('.container').show();
}

function showLoading() {
    $('#loading').show();
    $('.container').hide();
}

$(function () {

    if (window.location.pathname === "" || window.location.pathname === "/" || window.location.pathname === "/index" || window.location.pathname === null) {
        var vue = new Vue({
            el: '.vue',
            data: {
                films: [],
                categories: [],
                link: "/film/"
            },
            beforeCompile: function () {
                var self = this;
                $.when($.getJSON('/film/allForIndex', function (films) {
                        self.films = films;
                    }),
                    $.getJSON('/category/all', function (categories) {
                        self.categories = categories;
                    })).done(function () {
                    hideLoading();
                });
            },
            methods: {
                getYear: function (val) {
                    var date = new Date(Date.parse(val));
                    return date.getFullYear();
                },
                openCategory: function (id) {
                    var self = this;
                    showLoading();
                    $.getJSON('/category/' + id + '/films', function (data) {
                        self.films = data;
                        hideLoading();
                    });
                }
            }
        });
    } else if (window.location.pathname.includes("/film/")) {
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
            },
            methods: {
                openCategory: function (id) {
                    showLoading();
                    $.getJSON('/category/' + id + '/films', function (data) {
                        hideLoading(); //todo films loading after clicking on the category inside of the film
                    });
                }
            }
        });


        var url = '/film/info/' + window.location.href.slice(-1);
        var film = new Vue({
            el: '.info',
            data: {
                film: []
            },
            beforeCompile: function () {
                var self = this;
                $.getJSON(url, function (data) {
                    hideLoading();
                    console.log(data);
                    self.film = data;
                });
            },
            methods: {
                calculateTime: function (val) {
                    var result = "";
                    if (val / 60 < 1) {
                        result += val + " minutes";
                    } else if (val / 60 === 1) {
                        if (val % 60 === 0)
                            result += "1 hour";
                        else
                            result += "1 hour " + (val - 60) + " minutes";
                    } else {
                        var minutes = (val - (parseInt((val / 60).toString().split('.')[0]) * 60));
                        if (minutes === 0)
                            result += (val / 60) + " hours";
                        else
                            result += parseInt((val / 60).toString().split('.')[0]) + " hours " + minutes + " minutes";
                    }
                    return result;
                }
            }
        });

    } else {
        hideLoading();
    }
});