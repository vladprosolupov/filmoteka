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

function save() {

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
    filmToSave['categories'] = [];
    filmToSave['actors'] = {};
    filmToSave['directors'] = [];
    filmToSave['studios'] = [];
    filmToSave['countries'] = [];
    filmToSave['networks'] = [];

    var categories = $('.filmCategory');
    for (i = 0; i < categories.length; i++) {
        filmToSave['categories'].push(categories.toArray()[i].innerHTML);
    }

    var actors = $('.filmActor');
    var actorsRole = $('.filmActorRole');
    for (i = 0; i < actors.length; i++) {
        filmToSave['actors'][actorsRole.toArray()[i].innerHTML] = actors.toArray()[i].innerHTML;
    }

    var directors = $('.filmDirector');
    for (i = 0; i < directors.length; i++) {
        filmToSave['directors'].push(directors.toArray()[i].innerHTML);
    }

    var studios = $('.filmStudio');
    for (i = 0; i < studios.length; i++) {
        filmToSave['studios'].push(studios.toArray()[i].innerHTML);
    }

    var countries = $('.filmCountries');
    for (i = 0; i < countries.length; i++) {
        filmToSave['countries'].push(countries.toArray()[i].innerHTML);
    }

    var networks = $('.filmNetworks');
    for (i = 0; i < networks.length; i++) {
        filmToSave['networks'].push(networks.toArray()[i].innerHTML);
    }

    console.log('No json: ');
    console.log(filmToSave);
    console.log('With: ' + JSON.stringify(filmToSave));


    $.ajax({
        url: '/film/save',
        type: 'POST',
        // dataType: 'JSON',
        data: JSON.stringify(filmToSave),
        contentType: 'application/json',
        success: function (data) {
            console.log('Yes, success');
            console.log(data);
        },
        error: function (xhr, textStatus, errorThrown) {
            // console.log('Arr at beg ' + filmToSave);
            // console.log(('After ' + JSON.stringify(filmToSave)));
            console.log('Error in Operation');
            console.log('Text status: ' + textStatus);
            console.log('XHR: ' + xhr);
            console.log('Error thrown: ' + errorThrown);
        }
    });


    // console.log(filmToSave);
}