/**
 * Created by vladyslavprosolupov on 13.06.17.
 */
var domain = window.location.origin;

window.addEventListener("keydown", function (e) {
    if ($('#searchInput').is(':focus') || $('.searchDropdown').is(':focus')) {
        if ([38, 40].indexOf(e.keyCode) > -1) {
            e.preventDefault();
        }
    }
}, false);

function removeWhiteSpaceAndNewLine(input) {
    var result = input.replace(/(\r\n|\n|\r|\s)/gm, "");
}

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

var normalizeSearch = function (title) {
    var titleSearch = title;
    titleSearch = titleSearch.toLowerCase(); //Making it lowercase
    titleSearch = titleSearch.normalize('NFKD');                       //
    var pattern = new RegExp("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+"); //Replacing accented letters with latin analogs
    titleSearch = titleSearch.replace(/\s/g, ''); // Removing whitespaces
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
                window.location.replace(domain + '/?s=' + self.searchInput);
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
                $($('.searchDropdown')[currentIndex + 1]).focus();
            },
            moveFocusUp: function () {
                var currentIndex = $('.searchDropdown').index(document.activeElement);
                $($('.searchDropdown')[currentIndex - 1]).focus();
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

        if (searchInput) {
            $('#searchInput').val(searchInput);
        }

        var vue = new Vue({
            el: '.vue',
            data: {
                films: [],
                categories: [],
                link: '/film/',
                notFound: false,
                pagesNumber: 0,
                currentPage: 1,
                pageLink: '/film/filmsForIndexPage/'
            },
            beforeCompile: function () {
                var self = this;
                if (category) {
                    self.notFound = false;
                    $.when($.getJSON('/category/forNav', function (categories) {
                            self.categories = categories;
                        }),
                        $.getJSON('/category/' + category + '/films/1', function (data) {
                            $('a[data-category]').removeClass('is-current');
                            $('a[data-category = ' + category + ']').addClass('is-current');
                            self.films = data;
                            self.currentPage = 1;

                            $.getJSON('category/' + category + ' /count', function (filmsNumber) {
                                if (filmsNumber / 10 !== parseInt(filmsNumber / 10, 10))
                                    self.pagesNumber = parseInt(filmsNumber / 10, 10) + 1;
                                else
                                    self.pagesNumber = parseInt(filmsNumber / 10, 10);
                            });
                            self.pageLink = '/category/' + category + '/films/';
                        })).done(function () {
                        // $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                        hideLoading();
                    });
                } else if (searchInput) {
                    $.when($.getJSON('/category/forNav', function (categories) {
                            self.categories = categories;
                        }),
                        $.getJSON('/search/film/' + searchInput + '/1', function (data) {
                            self.films = data;
                            if (self.films.length === 0) {
                                self.pagesNumber = 0;
                                self.notFound = true;
                            } else {
                                self.currentPage = 1;
                                $.getJSON('search/film/count/' + searchInput, function (filmsNumber) {
                                    if (filmsNumber / 10 !== parseInt(filmsNumber / 10, 10))
                                        self.pagesNumber = parseInt(filmsNumber / 10, 10) + 1;
                                    else
                                        self.pagesNumber = parseInt(filmsNumber / 10, 10);
                                });
                                self.pageLink = '/search/film/' + searchInput + '/';
                            }
                        })).done(function () {
                        // $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                        hideLoading();
                    });
                } else {
                    self.notFound = false;
                    $.when($.getJSON('/film/filmsForIndexPage/1', function (films) {
                            self.films = films;
                        }),
                        $.getJSON('/category/forNav', function (categories) {
                            self.categories = categories;
                        }),
                        $.getJSON('/film/numberOfFilms', function (filmsNumber) {
                            if (filmsNumber / 10 !== parseInt(filmsNumber / 10, 10))
                                self.pagesNumber = parseInt(filmsNumber / 10, 10) + 1;
                            else
                                self.pagesNumber = parseInt(filmsNumber / 10, 10);
                        })).done(function () {
                        // $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                        hideLoading();
                    });
                }
                $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
            },
            methods: {
                getYear: function (val) {
                    var date = new Date(Date.parse(val));
                    return date.getFullYear();
                },
                openCategory: function (id) {
                    var self = this;
                    showLoading();
                    $.getJSON('/category/' + id + '/films/1', function (data) {
                        $('a[data-category]').removeClass('is-current');
                        $('a[data-category = ' + id + ']').addClass('is-current');
                        self.notFound = false;
                        self.films = data;
                        $.when($.getJSON('category/' + id + ' /count', function (filmsNumber) {
                            if (filmsNumber / 10 !== parseInt(filmsNumber / 10, 10))
                                self.pagesNumber = parseInt(filmsNumber / 10, 10) + 1;
                            else
                                self.pagesNumber = parseInt(filmsNumber / 10, 10);
                        })).done(function () {
                            hideLoading();
                        });
                        self.pageLink = '/category/' + id + '/films/'
                    });
                },
                goToPage: function (pageNum) {
                    var self = this;
                    showLoading();
                    $.getJSON(self.pageLink + pageNum, function (data) {
                        self.currentPage = parseInt(pageNum);
                        $('a[data-pagenum]').removeClass("is-current");
                        $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                        self.films = data;
                        hideLoading();
                    });
                },
                goToPrevious: function () {
                    var self = this;
                    showLoading();
                    $.getJSON(self.pageLink + (parseInt(self.currentPage) - 1), function (data) {
                        self.currentPage = parseInt(self.currentPage) - 1;
                        $('a[data-pagenum]').removeClass("is-current");
                        $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                        self.films = data;
                        hideLoading();
                    });
                },
                goToNext: function () {
                    var self = this;
                    showLoading();
                    $.getJSON(self.pageLink + (parseInt(self.currentPage) + 1), function (data) {
                        self.currentPage = parseInt(self.currentPage) + 1;
                        $('a[data-pagenum]').removeClass("is-current");
                        $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                        self.films = data;
                        hideLoading();
                    });
                }
            }
        });
    } else if (window.location.pathname.includes("/film/")) {
        var lastslashindex = window.location.pathname.lastIndexOf('/');
        var id = window.location.pathname.substring(lastslashindex + 1);
        var url = '/film/info/' + id;
        var film = new Vue({
            el: '.vue',
            data: {
                film: [],
                categories: [],
                check: '',
                comments: []
            },
            beforeCompile: function () {
                var self = this;
                $.when($.getJSON(url, function (data) {
                        self.film = data;
                    }),
                    $.getJSON('/category/forNav', function (categories) {
                        self.categories = categories;
                    }),
                    $.getJSON('/comment/getFilmComments/' + id, function (comments) {
                        self.comments = comments;
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
                formatReleaseDate: function (val) {
                    var date = new Date(Date.parse(val));
                    var monthNames = [
                        "January", "February", "March",
                        "April", "May", "June", "July",
                        "August", "September", "October",
                        "November", "December"
                    ];

                    var day = date.getDate();
                    var monthIndex = date.getMonth();
                    var year = date.getFullYear();

                    return day + ' ' + monthNames[monthIndex] + ' ' + year;
                },
                openCategory: function (id) {
                    window.location.replace(domain + '/?c=' + id);
                },
                submitCommentOrMoveLine: function () {
                    if (this.check) {
                        $('#newComment').prop('disabled', true);
                        var self = this;
                        var comment = {};
                        comment['idFilm'] = id;
                        comment['commentText'] = $('#newComment').val();

                        var token = $("meta[name='_csrf']").attr("content");
                        var header = $("meta[name='_csrf_header']").attr("content");
                        $(document).ajaxSend(function (e, xhr, options) {
                            xhr.setRequestHeader(header, token);
                        });
                        $('#submitButton').addClass("is-loading");
                        $.ajax({
                            url: domain + '/comment/save',
                            type: 'POST',
                            contentType: 'application/json;charset=utf-8',
                            data: JSON.stringify(comment),
                            success: function () {
                                $('#newComment').val("");
                                $.when($.getJSON('/comment/getFilmComments/' + id, function (comments) {
                                    self.comments = comments;
                                })).done(function () {
                                    $('#newComment').prop('disabled', false);
                                    $('#submitButton').removeClass("is-loading");
                                });
                            }
                        });
                    }
                },
                submitComment: function () {
                    $('#newComment').prop('disabled', true);
                    var self = this;
                    var comment = {};
                    comment['idFilm'] = id;
                    comment['commentText'] = $('#newComment').val();

                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    $(document).ajaxSend(function (e, xhr, options) {
                        xhr.setRequestHeader(header, token);
                    });
                    $('#submitButton').addClass("is-loading");
                    $.ajax({
                        url: domain + '/comment/save',
                        type: 'POST',
                        contentType: 'application/json;charset=utf-8',
                        data: JSON.stringify(comment),
                        success: function () {
                            $('#newComment').val("");
                            $.when($.getJSON('/comment/getFilmComments/' + id, function (comments) {
                                self.comments = comments;
                            })).done(function () {
                                $('#newComment').prop('disabled', false);
                                $('#submitButton').removeClass("is-loading");
                            });
                        }
                    });
                },
                deleteComment: function () {
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    $(document).ajaxSend(function (e, xhr, options) {
                        xhr.setRequestHeader(header, token);
                    });
                    $.ajax({
                        url: domain + '/comment/delete/' + "3",
                        type: 'POST',
                        success: function () {
                            $.getJSON('/comment/getFilmComments/' + id, function (comments) {
                                self.comments = comments;
                            });
                        }
                    });
                },
                getPostTime: function (date) {
                    var result = "";
                    var commentDate = new Date(Date.parse(date));
                    var now = new Date();
                    var timeDiff = Math.abs(now.getTime() - commentDate.getTime());
                    var diffMinutes = Math.floor(timeDiff / 1000 / 60);
                    var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));

                    if (diffDays > 1) {
                        if (diffDays < 30) {
                            result += (diffDays - 1) + "d";
                        } else {
                            if (diffDays < 365) {
                                result += (Math.floor(diffDays / 30)) + "month";
                            } else {
                                result += (Math.floor(diffDays / 365)) + "year";
                            }
                        }
                    } else {
                        if (diffMinutes < 60) {
                            if ((Math.floor(timeDiff / 1000)) >= 60) {
                                result += diffMinutes + "min";
                            } else {
                                result += (Math.floor(timeDiff / 1000)) + "sec";
                            }
                        } else {
                            result += Math.floor(diffMinutes / 60) + "h";
                        }
                    }
                    return result;
                }
            }
        });

    } else if (window.location.pathname.includes("/profile")) {
        var profile = new Vue({
            el: '.vue',
            data: {
                info: []
            },
            beforeCompile: function () {
                var self = this;

                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                $(document).ajaxSend(function (e, xhr, options) {
                    xhr.setRequestHeader(header, token);
                });

                $.getJSON("/client/getCurrentUser", function (data) {
                    self.info = data;
                    hideLoading();
                }).fail(function () {
                    window.location.replace(domain + "/login");
                });
            }
        });

    } else if (window.location.pathname === "/best") {
        var pageNew = new Vue({
            el: '.vue',
            data: {
                films: [],
                categories: [],
                link: "/film/",
                pagesNumber: 0,
                currentPage: 1
            },
            beforeCompile: function () {
                var self = this;
                $.when($.getJSON('/film/filmsForBestPage/1', function (films) {
                        self.films = films;
                    }),
                    $.getJSON('/category/forNav', function (categories) {
                        self.categories = categories;
                    }),
                    $.getJSON('/film/numberOfFilms', function (filmsNumber) {
                        if (filmsNumber / 10 !== parseInt(filmsNumber / 10, 10))
                            self.pagesNumber = parseInt(filmsNumber / 10, 10) + 1;
                        else
                            self.pagesNumber = parseInt(filmsNumber / 10, 10);
                    })).done(function () {
                    $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                    hideLoading();
                });
            },
            methods: {
                getYear: function (val) {
                    var date = new Date(Date.parse(val));
                    return date.getFullYear();
                },
                openCategory: function (id) {
                    window.location.replace(domain + '/?c=' + id);
                },
                goToPage: function (pageNum) {
                    var self = this;
                    showLoading();
                    $.getJSON('/film/filmsForBestPage/' + pageNum, function (data) {
                        self.currentPage = parseInt(pageNum);
                        $('a[data-pagenum]').removeClass("is-current");
                        $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                        self.films = data;
                        hideLoading();
                    });
                },
                goToPrevious: function () {
                    var self = this;
                    showLoading();
                    $.getJSON('/film/filmsForBestPage/' + (parseInt(self.currentPage) - 1), function (data) {
                        self.currentPage = parseInt(self.currentPage) - 1;
                        $('a[data-pagenum]').removeClass("is-current");
                        $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                        self.films = data;
                        hideLoading();
                    });
                },
                goToNext: function () {
                    var self = this;
                    showLoading();
                    $.getJSON('/film/filmsForBestPage/' + (parseInt(self.currentPage) + 1), function (data) {
                        self.currentPage = parseInt(self.currentPage) + 1;
                        $('a[data-pagenum]').removeClass("is-current");
                        $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                        self.films = data;
                        hideLoading();
                    });
                }
            }
        });
    }


});