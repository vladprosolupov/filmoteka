/**
 * Created by vladyslavprosolupov on 13.06.17.
 */
var flag = false;
function hideLoading(){
    if(flag == false){
        flag = true;
    }else {
        $('#loading').hide();
        $('.container').show();
    }
}

$(function () {
    var films = new Vue({
        el: '.films',
        data: {
            films: []
        },
        beforeCompile: function () {
            var self = this;
            $.getJSON('/film/allForIndex', function (data) {
               hideLoading();
               self.films = data;
            });
        },
        methods:{
            getYear: function (val) {
                var date = new Date(Date.parse(val));
                return date.getFullYear();
            }
        }
    });

    var categories = new Vue({
       el: '.categories',
       data: {
           categories: []
       } ,
        beforeCompile: function() {
           var self = this;
           $.getJSON('/category/all', function (data) {
               hideLoading();
               self.categories = data;
           });
        }
    });

});