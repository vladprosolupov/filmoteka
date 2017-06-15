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
    // var table = document.getElementById('filmCategories'),
    //     cells = table.getElementsByTagName('td');

    // for (var i=0,len=cells.length; i<len; i++){
    //     // cells[i].onclick = function(){
    //     //     console.log(this.innerHTML);
    //     //     /* if you know it's going to be numeric:
    //     //      console.log(parseInt(this.innerHTML),10);
    //     //      */
    //     // }
    //     if(cells[i].innerHTML != 'Delete' || cells[i]) {
    //         console.log(cells[i].innerHTML);
    //     }
    // }

    var categories;
    for (i = 0; i < $('.filmCategory').length; i++){
        console.log($('.filmCategory').toArray()[i].innerHTML);
        categories = $('.filmCategory').toArray()[i].innerHTML;
    }

    console.log(obj);
}