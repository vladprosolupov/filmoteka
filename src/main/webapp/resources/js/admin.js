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
    var elements = document.getElementsByClassName("formForFilm")[0].elements;
    var obj ={};
    for(var i = 0 ; i < elements.length ; i++){
        var item = elements.item(i);
        obj[item.name] = item.value;
    }
    obj['categories'] = [];
    obj['actors'] = {};
    obj['directors'] = [];
    obj['studios'] = [];
    obj['countries'] = [];

    var categories = $('.filmCategory');
    for (i = 0; i < categories.length; i++){
        obj['categories'].push(categories.toArray()[i].innerHTML);
    }

    var actors = $('.filmActor');
    var actorsRole = $('.filmActorRole');
    for (i = 0; i < actors.length; i++){
        obj['actors'][actors.toArray()[i].innerHTML] = actorsRole.toArray()[i].innerHTML;
    }

    var directors = $('.filmDirector');
    for(i = 0; i < directors.length; i++){
        obj['directors'].push(directors.toArray()[i].innerHTML);
    }

    var studios = $('.filmStudios');
    for(i = 0; i < directors.length; i++){
        obj['studios'].push(studios.toArray()[i].innerHTML);
    }

    var countries = $('.filmCountries');
    for(i = 0; i < countries.length; i++){
        obj['countries'].push(studios.toArray()[i].innerHTML);
    }

    var networks = $('.filmNetworks');




    console.log(obj);
}