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
    } else if (window.location.href.substr(28, 17) == 'films/addOrUpdate') {
        $('#loading').css('display','none');
        $('.formForFilm').css('display','block');

        $('.addCategory').click(function () {

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