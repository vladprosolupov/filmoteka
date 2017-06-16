/**
 * Created by vladyslavprosolupov on 13.06.17.
 */
$(function () {
    if (window.location.href.substr(28) == 'films') {
        var films = new Vue({
            el: '.films',
            data: {
                films: []
            },
            beforeCompile: function () {
                var self = this;
                $.getJSON('/film/all', function (data) {
                    $('#loading').css('display', 'none');
                    $('.films').css('display', 'block');
                    self.films = data;
                });
            },
            methods: {
                editFilm: function (id) {
                    window.location.href += '/addOrUpdate/' + id;
                },
                deleteFilm: function (id) {
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    $(document).ajaxSend(function (e, xhr, options) {
                        xhr.setRequestHeader(header, token);
                    });

                    $.ajax({
                        url: '/film/delete/' + id,
                        type: 'POST',
                        contentType: 'application/json',
                        success: function (data) {
                            console.log(data);
                            location.reload();
                        },
                        error: function (xhr, textStatus, errorThrown) {
                            console.log('Error in Operation');
                            console.log('Text status: ' + textStatus);
                            console.log('XHR: ' + xhr);
                            console.log('Error thrown: ' + errorThrown);
                        }
                    });
                }
            }
        });

        $('.addFilm').click(function () {
            window.location.href += '/addOrUpdate/0'
        });

    } else if (window.location.href.substr(28, 17) == 'films/addOrUpdate') {
        $('#loading').css('display', 'none');
        $('.formForFilm').css('display', 'block');

        $('.addCategory').click(function () {
            $('.categoryLoading').css('display', 'block');
            var categories = new Vue({
                el: '.newCategory',
                data: {
                    categories: []
                },
                beforeCompile: function () {
                    var self = this;
                    $.getJSON('/category/all', function (data) {
                        $('.categoryLoading').css('display', 'none');
                        $('.newCategory').css('display', 'block');
                        self.categories = data;
                    });
                },
                methods: {
                    saveCategory: function(){
                        $('.newCategory').css('display','none');
                        var selected = $('.categoryOptions').find(":selected");
                        var row = "<tr data-category='"+ selected.val() +"'>" +
                            "<td>" +
                            selected.text() +
                            "</td>" +
                            "<td>" +
                            "<button type='button'>Delete</button>" +
                            "</td>" +
                            "</tr>";
                        $(row).insertBefore(".categories tr[class='categoryLoading']");
                    }
                }
            });
        });

        $('.addDirector').click(function () {
            $('.directorLoading').css('display', 'block');
            var directors = new Vue({
                el: '.newDirector',
                data: {
                    directors: []
                },
                beforeCompile: function () {
                    var self = this;
                    $.getJSON('/director/all', function (data) {
                        $('.directorLoading').css('display', 'none');
                        $('.newDirector').css('display', 'block');
                        self.directors = data;
                    });
                },
                methods: {
                    saveDirector: function(){
                        $('.newDirector').css('display','none');
                        var selected = $('.directorOptions').find(":selected");
                        var row = "<tr data-director='"+ selected.val() +"'>" +
                            "<td>" +
                            selected.text() +
                            "</td>" +
                            "<td>" +
                            "<button type='button'>Delete</button>" +
                            "</td>" +
                            "</tr>";
                        $(row).insertBefore(".directors tr[class='directorLoading']");
                    }
                }
            });
        });

        $('.addStudio').click(function () {
            $('.studioLoading').css('display', 'block');
            var studios = new Vue({
                el: '.newStudio',
                data: {
                    studios: []
                },
                beforeCompile: function () {
                    var self = this;
                    $.getJSON('/studio/all', function (data) {
                        $('.studioLoading').css('display', 'none');
                        $('.newStudio').css('display', 'block');
                        self.studios = data;
                    });
                },
                methods: {
                    saveStudio: function(){
                        $('.newStudio').css('display','none');
                        var selected = $('.studioOptions').find(":selected");
                        var row = "<tr data-studio='"+ selected.val() +"'>" +
                            "<td>" +
                            selected.text() +
                            "</td>" +
                            "<td>" +
                            "<button type='button'>Delete</button>" +
                            "</td>" +
                            "</tr>";
                        $(row).insertBefore(".studios tr[class='studioLoading']");
                    }
                }
            });
        });


        $('.addCountry').click(function () {
            $('.countryLoading').css('display', 'block');
            var countries = new Vue({
                el: '.newCountry',
                data: {
                    countries: []
                },
                beforeCompile: function () {
                    var self = this;
                    $.getJSON('/country/all', function (data) {
                        $('.countryLoading').css('display', 'none');
                        $('.newCountry').css('display', 'block');
                        self.countries = data;
                    });
                },
                methods: {
                    saveCountry: function(){
                        $('.newCountry').css('display','none');
                        var selected = $('.countryOptions').find(":selected");
                        var row = "<tr data-country='"+ selected.val() +"'>" +
                            "<td>" +
                            selected.text() +
                            "</td>" +
                            "<td>" +
                            "<button type='button'>Delete</button>" +
                            "</td>" +
                            "</tr>";
                        $(row).insertBefore(".countries tr[class='countryLoading']");
                    }
                }
            });
        });




        $('.save').click(function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });

            var elements = document.getElementsByClassName("formForFilm")[0].elements;
            var filmToSave = {};
            for (var i = 0; i < elements.length; i++) {
                var item = elements.item(i);
                if (item.name !== 'ignore') {
                    filmToSave[item.name] = item.value;
                }
            }
            filmToSave['id'] = $('form[data-film]').attr("data-film");
            filmToSave['categories'] = [];
            filmToSave['actors'] = [];
            filmToSave['directors'] = [];
            filmToSave['studios'] = [];
            filmToSave['countries'] = [];
            filmToSave['networks'] = [];
            filmToSave['awards'] = [];
            filmToSave['screenshots'] = [];
            filmToSave['trailers'] = [];

            var categories = $('tr[data-category]');
            for (i = 0; i < categories.length; i++) {
                filmToSave['categories'].push($(categories[i]).attr("data-category"));
            }

            var actors = $('tr[data-actor]');
            for (i = 0; i < actors.length; i++) {
                filmToSave['actors'].push($(actors[i]).attr("data-actor"));
            }

            var directors = $('tr[data-director]');
            for (i = 0; i < directors.length; i++) {
                filmToSave['directors'].push($(directors[i]).attr("data-director"));
            }

            var studios = $('tr[data-studio]');
            for (i = 0; i < studios.length; i++) {
                filmToSave['studios'].push($(studios[i]).attr("data-studio"));
            }

            var countries = $('tr[data-country]');
            for (i = 0; i < countries.length; i++) {
                filmToSave['countries'].push($(countries[i]).attr("data-country"));
            }

            var networks = $('tr[data-network]');
            for (i = 0; i < networks.length; i++) {
                filmToSave['networks'].push($(networks[i]).attr("data-network"));
            }

            var awards = $('tr[data-award]');
            for (i = 0; i < awards.length; i++) {
                filmToSave['awards'].push($(awards[i]).attr("data-award"));
            }

            var screenshots = $('tr[data-screenshot]');
            for (i = 0; i < screenshots.length; i++) {
                filmToSave['screenshots'].push($(screenshots[i]).attr("data-screenshot"));
            }

            var trailers = $('tr[data-trailer]');
            for (i = 0; i < trailers.length; i++) {
                filmToSave['trailers'].push($(trailers[i]).attr("data-trailer"));
            }

            $.ajax({
                url: '/film/save',
                type: 'POST',
                data: JSON.stringify(filmToSave),
                contentType: 'application/json',
                success: function (data) {
                    console.log(data);
                    window.location.href = "http://localhost:8080/admin/films";
                },
                error: function (xhr, textStatus, errorThrown) {
                    console.log('Error in Operation');
                    console.log('Text status: ' + textStatus);
                    console.log('XHR: ' + xhr);
                    console.log('Error thrown: ' + errorThrown);
                }
            });
        });
    }
});