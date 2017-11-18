/**
 * Created by vladyslavprosolupov on 13.06.17.
 */

window.addEventListener("keydown", function(e) {
    if($('#searchInput').is(':focus') || $('.searchDropdown').is(':focus')) {
        if ([38, 40, 13].indexOf(e.keyCode) > -1) {
            e.preventDefault();
        }
    }
}, false);

function hideLoading() {
    $('#loading').hide();
    $('.vue').show();
}

function showLoading() {
    $('#loading').show();
    $('.vue').hide();
}

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

var removeGetParametersFromPage = function (pageURL) {
    window.history.pushState({}, document.title, pageURL);
};

var normilizeSearch = function (title) {
    var titleSearch = title;
    titleSearch = titleSearch.toLowerCase(); //Making it lowercase
    titleSearch = titleSearch.normalize('NFKD');                       //
    var pattern = new RegExp("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+"); //Replacing accented letters with latin analogs
    titleSearch = titleSearch.replace(/\s/g,''); // Removing whitespaces
    return titleSearch;
};


$(function () {
    $(document).click(function () {
        $('.centered').removeClass("is-active");
    });

    $(".VueSearch").click(function (event) {
        event.stopPropagation();
    });
    var search = new Vue({
        el: '.VueSearch',
        data: {
            searchResult: [],
            link: "/film/",
            searchInput: ''
        },
        watch: {
            searchInput: function (input) {
                if (input) {
                    var self = this;
                    console.log(input);
                    console.log(normilizeSearch(input));
                    $.getJSON('/search/film/quick/' + input, function (data) {
                        self.searchResult = data;
                        console.log(data);
                        $('.centered').addClass("is-active");
                    });
                } else {
                    $('.centered').removeClass("is-active");
                }
            }
        },
        methods: {
            doSearch: function () {
                var self = this;
                window.location.replace('http://localhost:8080/?s=' + self.searchInput);
            },
            showDropdown: function () {
                var self = this;
                if (self.searchInput) {
                    $('.centered').addClass("is-active");
                }
            },
            moveFocusToDropdown: function () {
                $($('.searchDropdown')[0]).focus();
            },
            moveFocusDown: function () {
                var currentIndex = $('.searchDropdown').index(document.activeElement);
                $($('.searchDropdown')[currentIndex+1]).focus();
            },
            moveFocusUp: function () {
                var currentIndex = $('.searchDropdown').index(document.activeElement);
                $($('.searchDropdown')[currentIndex-1]).focus();
            },
            removeFocusFromOthers: function () {
                var currentIndex = $('.searchDropdown').index(document.activeElement);
                $($('.searchDropdown')[currentIndex]).blur();
            }
        }
    });
    if (window.location.pathname === "" || window.location.pathname === "/" || window.location.pathname === "/index" || window.location.pathname === null) {
        var category = getUrlParameter("c");
        var searchInput = getUrlParameter("s");
        removeGetParametersFromPage("/");

        if(searchInput){
            $('#searchInput').val(searchInput);
        }

        var vue = new Vue({
            el: '.vue',
            data: {
                films: [],
                categories: [],
                link: "/film/",
                notFound: false
            },
            beforeCompile: function () {
                var self = this;
                $.when($.getJSON('/film/allForIndex', function (films) {
                        self.films = films;
                    }),
                    $.getJSON('/category/all', function (categories) {
                        self.categories = categories;
                        if (category) {
                            $.getJSON('/category/' + category + '/films', function (data) {
                                $('a[data-category]').removeClass('is-current');
                                $('a[data-category = ' + category + ']').addClass('is-current');
                                self.notFound = false;
                                self.films = data;
                                hideLoading();
                            });
                        } else if (searchInput) {
                            $.getJSON('/search/film/' + searchInput, function (data) {
                                self.films = data;
                                if(self.films.length === 0){
                                    self.notFound = true;
                                }
                                hideLoading();
                            });
                        }
                    })).done(function () {
                    if (!category && !searchInput) {
                        hideLoading();
                    }
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
                        $('a[data-category]').removeClass('is-current');
                        $('a[data-category = ' + id + ']').addClass('is-current');
                        self.notFound = false;
                        self.films = data;
                        hideLoading();
                    });
                }
            }
        });
    } else if (window.location.pathname.includes("/film/")) {
        var url = '/film/info/' + window.location.href.slice(-1);
        var film = new Vue({
            el: '.vue',
            data: {
                film: [],
                categories: [],
                check: ''
            },
            beforeCompile: function () {
                var self = this;
                $.when($.getJSON(url, function (data) {
                        self.film = data;
                    }),
                    $.getJSON('/category/all', function (categories) {
                        self.categories = categories;
                    })).done(function () {
                    hideLoading();
                });
            },
            watch: {
                check: function (property) {
                    this.check = property;
                }
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
                },
                openCategory: function (id) {
                    window.location.replace('http://localhost:8080/?c=' + id);
                },
                submitCommentOrMoveLine: function () {
                    if (this.check) {
                        alert("submit");
                    }
                }
            }
        });

    } else if(window.location.pathname.includes("/profile")) {
        var profile = new Vue({
            el : '.vue',
            data : {info : []},
            beforeCompile : function(){
                $.getJSON("/client/info", function (data) {
                    var self = this;
                    self.info = data;
                    console.log(self.info);
                    hideLoading();

                })
            }
        });

    }



});