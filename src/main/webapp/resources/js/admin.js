/**
 * Created by vladyslavprosolupov on 13.06.17.
 */
function succ(link){
    $('html').addClass("is-clipped");
    $('.success').addClass("is-active");

    setTimeout(function(){
        $('.success').removeClass("is-active");
        $('html').removeClass("is-clipped");
        window.location.replace(link);
    }, 2000);
}
function fail(link) {
    $('html').addClass("is-clipped");
    $('.fail').addClass("is-active");

    setTimeout(function(){
        $('.fail').removeClass("is-active");
        $('html').removeClass("is-clipped");
        window.location.replace(link);
    }, 2000);
}

$(function () {
    if (window.location.pathname === "/admin/films") {
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
                            succ("http://localhost:8080/admin/films");
                        },
                        error: function (xhr, textStatus, errorThrown) {
                            console.log('Error in Operation');
                            console.log('Text status: ' + textStatus);
                            console.log('XHR: ' + xhr);
                            console.log('Error thrown: ' + errorThrown);
                            fail("http://localhost:8080/admin/films");
                        }
                    });
                }
            }
        });

        $('.addFilm').click(function () {
            location.href += '/addOrUpdate/0'
        });

    } else if (window.location.pathname.includes("/admin/films/addOrUpdate")) {
        $('#loading').hide();
        $('.formForFilm').show();

        $('.addCategory').click(function () {
            $('.addCategory').addClass("is-loading");
            var categories = new Vue({
                el: '.newCategory',
                data: {
                    categories: []
                },
                beforeCompile: function () {
                    var self = this;
                    $.getJSON('/category/all', function (data) {
                        $('.addCategory').removeClass("is-loading");
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
                            "<button type='button' class='deleteButton button is-danger'>Delete</button>" +
                            "</td>" +
                            "</tr>";
                        $(row).insertBefore(".categories tr[class='newCategory']");
                    }
                }
            });
        });

        $('.addDirector').click(function () {
            $('.addDirector').addClass("is-loading");
            var directors = new Vue({
                el: '.newDirector',
                data: {
                    directors: []
                },
                beforeCompile: function () {
                    var self = this;
                    $.getJSON('/director/all', function (data) {
                        $('.addDirector').removeClass("is-loading");
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
                            "<button type='button' class='deleteButton button is-danger'>Delete</button>" +
                            "</td>" +
                            "</tr>";
                        $(row).insertBefore(".directors tr[class='newDirector']");
                    }
                }
            });
        });

        $('.addStudio').click(function () {
            $('.addStudio').addClass("is-loading");
            var studios = new Vue({
                el: '.newStudio',
                data: {
                    studios: []
                },
                beforeCompile: function () {
                    var self = this;
                    $.getJSON('/studio/all', function (data) {
                        $('.addStudio').removeClass("is-loading");
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
                            "<button type='button' class='deleteButton button is-danger'>Delete</button>" +
                            "</td>" +
                            "</tr>";
                        $(row).insertBefore(".studios tr[class='newStudio']");
                    }
                }
            });
        });


        $('.addCountry').click(function () {
            $('.addCountry').addClass("is-loading");
            var countries = new Vue({
                el: '.newCountry',
                data: {
                    countries: []
                },
                beforeCompile: function () {
                    var self = this;
                    $.getJSON('/country/all', function (data) {
                        $('.addCountry').removeClass("is-loading");
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
                            "<button type='button' class='deleteButton button is-danger'>Delete</button>" +
                            "</td>" +
                            "</tr>";
                        $(row).insertBefore(".countries tr[class='newCountry']");
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
                            "<button type='button' class='deleteButton button is-danger'>Delete</button>" +
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
                            "<button type='button' class='deleteButton button is-danger'>Delete</button>" +
                            "</td>" +
                            "</tr>";
                        $(row).insertBefore(".screenshots tr[class='newScreenshot']");
                    }
                }
            });
        });

        $('.addActor').click(function () {
            $('.addActor').addClass("is-loading");
            var actors = new Vue({
                el: '.newActor',
                data: {
                    actors: []
                },
                beforeCompile: function () {
                    var self = this;
                    $.getJSON('/actor/all', function (data) {
                        $('.addActor').removeClass("is-loading");
                        $('.newActor').show();
                        self.actors = data;
                    });
                },
                methods: {
                    saveActor: function () {
                        $('.newActor').hide();
                        var selected = $('.actorOptions').find(":selected");
                        var role = $('.actorRole').val();
                        var row = "<tr class='actor'>" +
                            "<td data-actor='" + selected.val() + "'>" +
                            selected.text() +
                            "</td>" +
                            "<td data-actorRole>" +
                            role +
                            "</td>" +
                            "<td>" +
                            "<button type='button' class='deleteButton button is-danger'>Delete</button>" +
                            "</td>" +
                            "</tr>";
                        $(row).insertBefore(".actors tr[class='newActor']");
                    }
                }
            });

        });


        $('.addAward').click(function () {
            $('.newAward').show();
            var award = new Vue({
                el: '.newAward',
                methods: {
                    saveAward: function () {
                        $('.newAward').hide();
                        var awardName = $('.awardName').val();
                        var awardYear = $('.awardYear').val();
                        var row = "<tr class='award'>" +
                            "<td data-awardName>" +
                            awardName +
                            "</td>" +
                            "<td data-awardYear>" +
                            awardYear +
                            "</td>" +
                            "<td>" +
                            "<button type='button' class='deleteButton button is-danger'>Delete</button>" +
                            "</td>" +
                            "</tr>";
                        $(row).insertBefore(".awards tr[class='newAward']");
                    }
                }
            });
        });


        $('.addNetwork').click(function () {
            $('.addNetwork').addClass("is-loading");
            var networks = new Vue({
                el: '.newNetwork',
                data: {
                    networks: []
                },
                beforeCompile: function () {
                    var self = this;
                    $.getJSON('/network/all', function (data) {
                        $('.addNetwork').removeClass("is-loading");
                        $('.newNetwork').show();
                        self.networks = data;
                    });
                },
                methods: {
                    saveNetwork: function () {
                        $('.newNetwork').hide();
                        var selected = $('.networkOptions').find(":selected");
                        var link = $('.linkToNetwork').val();
                        var row = "<tr class='network'>" +
                            "<td data-network='" + selected.val() + "'>" +
                            selected.text() +
                            "</td>" +
                            "<td data-networkLink>" +
                            link +
                            "</td>" +
                            "<td>" +
                            "<button type='button' class='deleteButton button is-danger'>Delete</button>" +
                            "</td>" +
                            "</tr>";
                        $(row).insertBefore(".networks tr[class='newNetwork']");
                    }
                }
            });

        });

        $('.formForFilm').on('click', '.deleteButton', function () {
            $(this).closest('tr').remove();
        });


        $('.save').click(function () {
            $('.save').addClass("is-loading");
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
            filmToSave['actors'] = {};
            filmToSave['directors'] = [];
            filmToSave['studios'] = [];
            filmToSave['countries'] = [];
            filmToSave['networks'] = {};
            filmToSave['awards'] = {};
            filmToSave['screenshots'] = [];
            filmToSave['trailers'] = [];

            var categories = $('tr[data-category]');
            for (i = 0; i < categories.length; i++) {
                filmToSave['categories'].push($(categories[i]).attr("data-category"));
            }

            var actors = $('tr[class="actor"]');
            for (i = 0; i < actors.length; i++) {
                filmToSave['actors'][$(actors[i]).find('td[data-actorRole]').text()] = $(actors[i]).find('td[data-actor]').attr("data-actor");
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


            var networks = $('tr[class="network"]');
            for (i = 0; i < networks.length; i++) {
                filmToSave['networks'][$(networks[i]).find('td[data-networkLink]').text()] = $(networks[i]).find('td[data-network]').attr("data-network");
            }


            var awards = $('tr[class="award"]');
            for (i = 0; i < awards.length; i++) {
                filmToSave['awards'][$(awards[i]).find('td[data-awardName]').text()] = $(awards[i]).find('td[data-awardYear]').text();
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

            $.ajax({
                url: '/film/save',
                type: 'POST',
                data: JSON.stringify(filmToSave),
                contentType: 'application/json',
                success: function (data) {
                    console.log(data);
                    succ("http://localhost:8080/admin/films");
                },
                error: function (xhr, textStatus, errorThrown) {
                    console.log('Error in Operation');
                    console.log('Text status: ' + textStatus);
                    console.log('XHR: ' + xhr);
                    console.log('Error thrown: ' + errorThrown);
                    fail("http://localhost:8080/admin/films");
                }
            });
        });
    } else if (window.location.pathname === "/admin/actors") {
        var actors = new Vue({
            el: '.actors',
            data: {
                actors: []
            },
            beforeCompile: function () {
                var self = this;
                $.getJSON('/actor/all', function (data) {
                    $('#loading').hide();
                    $('.actors').show();
                    self.actors = data;
                });
            },
            methods: {
                editActor: function (id) {
                    location.href += '/addOrUpdate/' + id;
                },
                deleteActor: function (id) {
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    $(document).ajaxSend(function (e, xhr, options) {
                        xhr.setRequestHeader(header, token);
                    });

                    $.ajax({
                        url: '/actor/delete/' + id,
                        type: 'POST',
                        contentType: 'application/json',
                        success: function (data) {
                            console.log(data);
                            succ("http://localhost:8080/admin/actors");
                        },
                        error: function (xhr, textStatus, errorThrown) {
                            console.log('Error in Operation');
                            console.log('Text status: ' + textStatus);
                            console.log('XHR: ' + xhr);
                            console.log('Error thrown: ' + errorThrown);
                            fail("http://localhost:8080/admin/actors");
                        }
                    });
                }
            }
        });

        $('.addActor').click(function () {
            location.href += '/addOrUpdate/0';
        });
    } else if (window.location.pathname.includes("/admin/actors/addOrUpdate")) {
        $('#loading').hide();
        $('.formForActor').show();

        $('.save').click(function () {
            $('.save').addClass("is-loading");
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });

            var elements = document.getElementsByClassName("formForActor")[0].elements;
            var actorToSave = {};
            for (var i = 0; i < elements.length; i++) {
                var item = elements.item(i);
                if (item.name !== 'ignore') {
                    actorToSave[item.name] = item.value;
                }
            }
            actorToSave['id'] = $('form[data-actor]').attr("data-actor");

            $.ajax({
                url: '/actor/save',
                type: 'POST',
                data: JSON.stringify(actorToSave),
                contentType: 'application/json',
                success: function (data) {
                    console.log(data);
                    succ("http://localhost:8080/admin/actors");
                },
                error: function (xhr, textStatus, errorThrown) {
                    console.log('Error in Operation');
                    console.log('Text status: ' + textStatus);
                    console.log('XHR: ' + xhr);
                    console.log('Error thrown: ' + errorThrown);
                    fail("http://localhost:8080/admin/actors");
                }
            });
        });
    } else if (window.location.pathname === "/admin/categories") {
        var categories = new Vue({
            el: '.categories',
            data: {
                categories: []
            },
            beforeCompile: function () {
                var self = this;
                $.getJSON('/category/all', function (data) {
                    $('#loading').hide();
                    $('.categories').show();
                    self.categories = data;
                });
            },
            methods: {
                editCategory: function (id) {
                    location.href += '/addOrUpdate/' + id;
                },
                deleteCategory: function (id) {
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    $(document).ajaxSend(function (e, xhr, options) {
                        xhr.setRequestHeader(header, token);
                    });

                    $.ajax({
                        url: '/category/delete/' + id,
                        type: 'POST',
                        contentType: 'application/json',
                        success: function (data) {
                            console.log(data);
                            succ("http://localhost:8080/admin/categories");
                        },
                        error: function (xhr, textStatus, errorThrown) {
                            console.log('Error in Operation');
                            console.log('Text status: ' + textStatus);
                            console.log('XHR: ' + xhr);
                            console.log('Error thrown: ' + errorThrown);
                            fail("http://localhost:8080/admin/categories");
                        }
                    });
                }
            }
        });

        $('.addCategory').click(function () {
            location.href += '/addOrUpdate/0';
        });
    } else if (window.location.pathname.includes("/admin/categories/addOrUpdate")) {
        $('#loading').hide();
        $('.formForCategory').show();

        $('.save').click(function () {
            $('.save').addClass("is-loading");
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });

            var elements = document.getElementsByClassName("formForCategory")[0].elements;
            var categoryToSave = {};
            for (var i = 0; i < elements.length; i++) {
                var item = elements.item(i);
                if (item.name !== 'ignore') {
                    categoryToSave[item.name] = item.value;
                }
            }
            categoryToSave['id'] = $('form[data-category]').attr("data-category");

            $.ajax({
                url: '/category/save',
                type: 'POST',
                data: JSON.stringify(categoryToSave),
                contentType: 'application/json',
                success: function (data) {
                    console.log(data);
                    succ("http://localhost:8080/admin/categories");
                },
                error: function (xhr, textStatus, errorThrown) {
                    console.log('Error in Operation');
                    console.log('Text status: ' + textStatus);
                    console.log('XHR: ' + xhr);
                    console.log('Error thrown: ' + errorThrown);
                    fail("http://localhost:8080/admin/categories");
                }
            });
        });
    } else if (window.location.pathname === "/admin/directors") {
        var directors = new Vue({
            el: '.directors',
            data: {
                directors: []
            },
            beforeCompile: function(){
                var self = this;
                $.getJSON('/director/all', function (data) {
                   $('#loading').hide();
                   $('.directors').show();
                   self.directors = data;
                });
            },
            methods: {
                editDirector: function (id) {
                    location.href += '/addOrUpdate/' + id;
                },
                deleteDirector: function (id) {
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    $(document).ajaxSend(function (e, xhr, options) {
                        xhr.setRequestHeader(header, token);
                    });

                    $.ajax({
                        url: '/director/delete/' + id,
                        type: 'POST',
                        contentType: 'application/json',
                        success: function (data) {
                            console.log(data);
                            succ("http://localhost:8080/admin/directors");
                        },
                        error: function (xhr, textStatus, errorThrown) {
                            console.log('Error in Operation');
                            console.log('Text status: ' + textStatus);
                            console.log('XHR: ' + xhr);
                            console.log('Error thrown: ' + errorThrown);
                            fail("http://localhost:8080/admin/directors");
                        }
                    });
                }
            }
        });

        $('.addDirector').click(function () {
            location.href += '/addOrUpdate/0';
        });

    } else if (window.location.pathname.includes("/admin/directors/addOrUpdate")) {
        $('#loading').hide();
        $('.formForDirector').show();
        $('.save').click(function () {
            $('.save').addClass("is-loading");
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });

            var elements = document.getElementsByClassName("formForDirector")[0].elements;
            var directorToSave = {};
            for (var i = 0; i < elements.length; i++) {
                var item = elements.item(i);
                if (item.name !== 'ignore') {
                    directorToSave[item.name] = item.value;
                }
            }
            directorToSave['id'] = $('form[data-director]').attr("data-director");

            $.ajax({
                url: '/director/save',
                type: 'POST',
                data: JSON.stringify(directorToSave),
                contentType: 'application/json',
                success: function (data) {
                    console.log(data);
                    succ("http://localhost:8080/admin/directors");
                },
                error: function (xhr, textStatus, errorThrown) {
                    console.log('Error in Operation');
                    console.log('Text status: ' + textStatus);
                    console.log('XHR: ' + xhr);
                    console.log('Error thrown: ' + errorThrown);
                    fail("http://localhost:8080/admin/directors");
                }
            });
        });
    }else if(window.location.pathname === "/admin/networks"){

        var networks = new Vue({
            el: '.networks',
            data: {
                networks: []
            },
            beforeCompile: function(){
                var self = this;
                $.getJSON('/network/all', function (data) {
                    $('#loading').hide();
                    $('.networks').show();
                    self.networks = data;
                });
            },
            methods: {
                editNetwork: function (id) {
                    location.href += '/addOrUpdate/' + id;
                },
                deleteNetwork: function (id) {
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    $(document).ajaxSend(function (e, xhr, options) {
                        xhr.setRequestHeader(header, token);
                    });

                    $.ajax({
                        url: '/network/delete/' + id,
                        type: 'POST',
                        contentType: 'application/json',
                        success: function (data) {
                            console.log(data);
                            succ("http://localhost:8080/admin/networks");
                        },
                        error: function (xhr, textStatus, errorThrown) {
                            console.log('Error in Operation');
                            console.log('Text status: ' + textStatus);
                            console.log('XHR: ' + xhr);
                            console.log('Error thrown: ' + errorThrown);
                            fail("http://localhost:8080/admin/networks");
                        }
                    });
                }
            }
        });

        $('.addNetwork').click(function () {
            location.href += '/addOrUpdate/0';
        });

    }else if(window.location.pathname.includes("/admin/networks/addOrUpdate")){
        $('#loading').hide();
        $('.formForNetwork').show();
        $('.save').click(function () {
            $('.save').addClass("is-loading");
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });

            var elements = document.getElementsByClassName("formForNetwork")[0].elements;
            var networkToSave = {};
            for (var i = 0; i < elements.length; i++) {
                var item = elements.item(i);
                if (item.name !== 'ignore') {
                    networkToSave[item.name] = item.value;
                }
            }
            networkToSave['id'] = $('form[data-network]').attr("data-network");

            $.ajax({
                url: '/network/save',
                type: 'POST',
                data: JSON.stringify(networkToSave),
                contentType: 'application/json',
                success: function (data) {
                    console.log(data);
                    succ("http://localhost:8080/admin/networks");
                },
                error: function (xhr, textStatus, errorThrown) {
                    console.log('Error in Operation');
                    console.log('Text status: ' + textStatus);
                    console.log('XHR: ' + xhr);
                    console.log('Error thrown: ' + errorThrown);
                    fail("http://localhost:8080/admin/networks");
                }
            });
        });
    } else if(window.location.pathname === "/admin/studios"){
        var studios = new Vue({
            el: '.studios',
            data: {
                studios: []
            },
            beforeCompile: function(){
                var self = this;
                $.getJSON('/studio/all', function (data) {
                    $('#loading').hide();
                    $('.studios').show();
                    self.studios = data;
                });
            },
            methods: {
                editStudio: function (id) {
                    location.href += '/addOrUpdate/' + id;
                },
                deleteStudio: function (id) {
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    $(document).ajaxSend(function (e, xhr, options) {
                        xhr.setRequestHeader(header, token);
                    });

                    $.ajax({
                        url: '/studio/delete/' + id,
                        type: 'POST',
                        contentType: 'application/json',
                        success: function (data) {
                            console.log(data);
                            succ("http://localhost:8080/admin/studios");
                        },
                        error: function (xhr, textStatus, errorThrown) {
                            console.log('Error in Operation');
                            console.log('Text status: ' + textStatus);
                            console.log('XHR: ' + xhr);
                            console.log('Error thrown: ' + errorThrown);
                            fail("http://localhost:8080/admin/studios");
                        }
                    });
                }
            }
        });

        $('.addStudio').click(function () {
            location.href += '/addOrUpdate/0';
        });
    } else if(window.location.pathname.includes("/admin/studios/addOrUpdate")){
        $('#loading').hide();
        $('.formForStudio').show();

        $('.save').click(function () {
            $('.save').addClass("is-loading");
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });

            var elements = document.getElementsByClassName("formForStudio")[0].elements;
            var studioToSave = {};
            for (var i = 0; i < elements.length; i++) {
                var item = elements.item(i);
                if (item.name !== 'ignore') {
                    studioToSave[item.name] = item.value;
                }
            }
            studioToSave['id'] = $('form[data-studio]').attr("data-studio");

            $.ajax({
                url: '/studio/save',
                type: 'POST',
                data: JSON.stringify(studioToSave),
                contentType: 'application/json',
                success: function (data) {
                    console.log(data);
                    succ("http://localhost:8080/admin/studios");
                },
                error: function (xhr, textStatus, errorThrown) {
                    console.log('Error in Operation');
                    console.log('Text status: ' + textStatus);
                    console.log('XHR: ' + xhr);
                    console.log('Error thrown: ' + errorThrown);
                    fail("http://localhost:8080/admin/studios");
                }
            });
        });
    }
});