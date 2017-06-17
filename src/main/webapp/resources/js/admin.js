/**
 * Created by vladyslavprosolupov on 13.06.17.
 */
$(function () {
    if (location.href.substr(28) === 'films') {
        var films = new Vue({
            el: '.films',
            data: {
                films: []
            },
            beforeCompile: function () {
                var self = this;
                $.getJSON('/film/all', function (data) {
                    $('#loading').hide();
                    $('.films').show();
                    self.films = data;
                });
            },
            methods: {
                editFilm: function (id) {
                    location.href += '/addOrUpdate/' + id;
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
            location.href += '/addOrUpdate/0'
        });

    } else if (location.href.substr(28, 17) === 'films/addOrUpdate') {
        $('#loading').hide();
        $('.formForFilm').show();

        $('.addCategory').click(function () {
            $('.categoryLoading').show();
            var categories = new Vue({
                el: '.newCategory',
                data: {
                    categories: []
                },
                beforeCompile: function () {
                    var self = this;
                    $.getJSON('/category/all', function (data) {
                        $('.categoryLoading').hide();
                        $('.newCategory').show();
                        self.categories = data;
                    });
                },
                methods: {
                    saveCategory: function () {
                        $('.newCategory').hide();
                        var selected = $('.categoryOptions').find(":selected");
                        var row = "<tr data-category='" + selected.val() + "'>" +
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
            $('.directorLoading').show();
            var directors = new Vue({
                el: '.newDirector',
                data: {
                    directors: []
                },
                beforeCompile: function () {
                    var self = this;
                    $.getJSON('/director/all', function (data) {
                        $('.directorLoading').hide();
                        $('.newDirector').show();
                        self.directors = data;
                    });
                },
                methods: {
                    saveDirector: function () {
                        $('.newDirector').hide();
                        var selected = $('.directorOptions').find(":selected");
                        var row = "<tr data-director='" + selected.val() + "'>" +
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
            $('.studioLoading').show();
            var studios = new Vue({
                el: '.newStudio',
                data: {
                    studios: []
                },
                beforeCompile: function () {
                    var self = this;
                    $.getJSON('/studio/all', function (data) {
                        $('.studioLoading').hide();
                        $('.newStudio').show();
                        self.studios = data;
                    });
                },
                methods: {
                    saveStudio: function () {
                        $('.newStudio').hide();
                        var selected = $('.studioOptions').find(":selected");
                        var row = "<tr data-studio='" + selected.val() + "'>" +
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
            $('.countryLoading').show();
            var countries = new Vue({
                el: '.newCountry',
                data: {
                    countries: []
                },
                beforeCompile: function () {
                    var self = this;
                    $.getJSON('/country/all', function (data) {
                        $('.countryLoading').hide();
                        $('.newCountry').show();
                        self.countries = data;
                    });
                },
                methods: {
                    saveCountry: function () {
                        $('.newCountry').hide();
                        var selected = $('.countryOptions').find(":selected");
                        var row = "<tr data-country='" + selected.val() + "'>" +
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


        $('.addTrailer').click(function () {
            $('.newTrailer').show();
            var trailer = new Vue({
                el: '.newTrailer',
                methods: {
                    saveTrailer: function () {
                        $('.newTrailer').hide();
                        var link = $('.trailerLink').val();
                        var row = "<tr class='trailer'>" +
                            "<td data-trailer>" +
                            link +
                            "</td>" +
                            "<td>" +
                            "<button type='button'>Delete</button>" +
                            "</td>" +
                            "</tr>";
                        $(row).insertBefore(".trailers tr[class='newTrailer']");
                    }
                }
            });
        });

        $('.addScreenshot').click(function () {
            $('.newScreenshot').show();
            var trailer = new Vue({
                el: '.newScreenshot',
                methods: {
                    saveScreenshot: function () {
                        $('.newScreenshot').hide();
                        var link = $('.screenshotLink').val();
                        var row = "<tr class='screenshot'>" +
                            "<td data-screenshot>" +
                            link +
                            "</td>" +
                            "<td>" +
                            "<button type='button'>Delete</button>" +
                            "</td>" +
                            "</tr>";
                        $(row).insertBefore(".screenshots tr[class='newScreenshot']");
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
            filmToSave['awards'] = {};
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

            var awards = $('tr[class="award"]');
            for (i = 0; i < awards.length; i++) {
                filmToSave['awards'][$(awards[i]).find('td[data-awardYear]').text()] = $(awards[i]).find('td[data-awardName]').text();
            }

            var screenshots = $('tr[class="screenshot"]');
            for (i = 0; i < screenshots.length; i++) {
                filmToSave['screenshots'].push($(screenshots[i]).find('td[data-screenshot]').text());
            }

            var trailers = $('tr[class="trailer"]');
            for (i = 0; i < trailers.length; i++) {
                filmToSave['trailers'].push($(trailers[i]).find('td[data-trailer]').text());
            }
            console.log(filmToSave);

            // $.ajax({
            //     url: '/film/save',
            //     type: 'POST',
            //     data: JSON.stringify(filmToSave),
            //     contentType: 'application/json',
            //     success: function (data) {
            //         console.log(data);
            //         location.href = "http://localhost:8080/admin/films";
            //     },
            //     error: function (xhr, textStatus, errorThrown) {
            //         console.log('Error in Operation');
            //         console.log('Text status: ' + textStatus);
            //         console.log('XHR: ' + xhr);
            //         console.log('Error thrown: ' + errorThrown);
            //     }
            // });
        });
    }
});