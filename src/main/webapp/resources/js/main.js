/**
 * Created by vladyslavprosolupov on 13.06.17.
 */
var domain = window.location.origin;

window.addEventListener("keydown", function (e) {
    if ($('#searchInput').is(':focus') || $('.searchDropdown').is(':focus')) {
        if ([38, 40].indexOf(e.keyCode) > -1) {
            e.preventDefault();
        } else if ($(window).width() <= 1023) {
            $(window).scrollTop(0);
        }
    }
}, false);

function removeWhiteSpaceAndNewLine(input) {
    var result = input.replace(/(\r\n|\n|\r|\s)/gm, "");
    return result;
}

function hideLoading() {
    $('#loading').hide();
    $('.vue').show();
}

function showLoading() {
    $('#loading').show();
    $('.vue').hide();
}

function showLoadingProfile() {
    $('#loading').show();
    $('div[class*="container column"]').hide();
}

function hideLoadingProfile() {
    $('#loading').hide();
    $('div[class*="container column"]').show();
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


jQuery(document).on('touchmove', function (ev) {
    if (jQuery(ev.target).parents().hasClass('bd-is-clipped-touch')) {
        ev.preventDefault();
    }
});

document.addEventListener('DOMContentLoaded', function () {

    var $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);

    if ($navbarBurgers.length > 0) {

        $navbarBurgers.forEach(function ($el) {
            $el.addEventListener('click', function () {
                var target = $el.dataset.target;
                var $target = document.getElementById(target);

                $('html').toggleClass('bd-is-clipped-touch');
                $('body').toggleClass('bd-is-clipped-touch');
                $('section[class*="background_for_client"]').toggleClass('is-blurred');

                $($target).toggleClass('is-active');
                $($el).toggleClass('is-active');
            });
        });
    }

});


$(function () {
    $(document).click(function (event) {
        $('.centered').removeClass("is-active");
        if ($(event.target.closest("#userDropDown")).length <= 0) {
            try {
                if ($('#userDropDown')[0].classList.contains("is-active"))
                    $('#userDropDown').removeClass("is-active");
            } catch (e) {
            }
        }
        if (($(event.target).parents().hasClass('hero background_for_client') && $(event.target).parents().hasClass('bd-is-clipped-touch')) || ($(event.target).hasClass('hero background_for_client') && $(event.target).parents().hasClass('bd-is-clipped-touch'))) {
            var $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);

            if ($navbarBurgers.length > 0) {

                $navbarBurgers.forEach(function ($el) {
                    var target = $el.dataset.target;
                    var $target = document.getElementById(target);

                    $('html').toggleClass('bd-is-clipped-touch');
                    $('body').toggleClass('bd-is-clipped-touch');
                    $('section[class*="background_for_client"]').toggleClass('is-blurred');
                    $($target).toggleClass('is-active');
                    $($el).toggleClass('is-active');
                });
            }
        }
    });

    $(".VueSearch").click(function (event) {
        event.stopPropagation();
    });

    $(".VueSearchOnPage").click(function (event) {
        event.stopPropagation();
    });

    var search = new Vue({
        el: '.VueSearch',
        data: {
            searchResult: [],
            link: "/film/",
            searchInput: '',
            width: 0
        },
        created: function () {
            var self = this;
            window.addEventListener('resize', function (ev) {
                self.width = $(window).width();
            });
        },
        beforeCompile: function () {
            var self = this;
            self.width = $(window).width();
        },
        watch: {
            searchInput: function (input) {
                if (input) {
                    var self = this;
                    if ($(window).width() >= 1024) {
                        $.getJSON('/search/film/quick/' + input, function (data) {
                            self.searchResult = data;
                            $('.centered').addClass("is-active");
                        });
                    }
                } else {
                    $('.centered').removeClass("is-active");
                }
            }
        },
        computed: {
            scroll: function () {
                if ($(window).width() <= 1023)
                    return "$(window).scrollTop(0);";
                else
                    return '';
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
                    if ($(window).width() >= 1024) {
                        $('.centered').addClass("is-active");
                    }
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

    var searchOnPage = new Vue({
        el: '.VueSearchOnPage',
        data: {
            searchResult: [],
            link: "/film/",
            searchInput: '',
            width: 0
        },
        created: function () {
            var self = this;
            window.addEventListener('resize', function (ev) {
                self.width = $(window).width();
            });
        },
        beforeCompile: function () {
            var self = this;
            self.width = $(window).width();
        },
        methods: {
            doSearch: function () {
                var self = this;
                window.location.replace(domain + '/?s=' + self.searchInput);
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
                        })
                    ).done(function () {
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
                filmClicked: function (ev) {
                    if ($(ev.target).parents().hasClass('layout-default bd-is-clipped-touch') && $(ev.target).parents().hasClass('hero background_for_client')) {
                        ev.preventDefault();
                    }
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
                        self.films = data;
                        $(window).scrollTop(0);
                        $(function () {
                            $('a[data-pagenum]').removeClass("is-current");
                            $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                        });
                        hideLoading();
                    });
                },
                goToPrevious: function (event) {
                    if (!$(event.currentTarget).attr('disabled')) {
                        var self = this;
                        showLoading();
                        $.getJSON(self.pageLink + (parseInt(self.currentPage) - 1), function (data) {
                            self.currentPage = parseInt(self.currentPage) - 1;
                            self.films = data;
                            $(window).scrollTop(0);
                            $(function () {
                                $('a[data-pagenum]').removeClass("is-current");
                                $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                            });
                            hideLoading();
                        });
                    }
                },
                goToNext: function (event) {
                    if (!$(event.currentTarget).attr('disabled')) {
                        var self = this;
                        showLoading();
                        $.getJSON(self.pageLink + (parseInt(self.currentPage) + 1), function (data) {
                            self.currentPage = parseInt(self.currentPage) + 1;
                            self.films = data;
                            $(window).scrollTop(0);
                            $(function () {
                                $('a[data-pagenum]').removeClass("is-current");
                                $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                            });
                            hideLoading();
                        });
                    }
                }
            }
        });
    } else if (window.location.pathname.indexOf("/film/") > -1) {
        var lastslashindex = window.location.pathname.lastIndexOf('/');
        var id = window.location.pathname.substring(lastslashindex + 1);
        var url = '/film/info/' + id;
        var film = new Vue({
            el: '.vue',
            data: {
                film: [],
                categories: [],
                check: '',
                comments: [],
                currUser: [],
                bookmarked: false,
                liked: false,
                disliked: false,
                likes: 0,
                dislikes: 0,
                domain: window.location.origin
            },
            beforeCompile: function () {
                var self = this;

                var filmLikesJSON = {};
                filmLikesJSON['filmId'] = id;
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                $(document).ajaxSend(function (e, xhr, options) {
                    xhr.setRequestHeader(header, token);
                });

                $.when($.getJSON(url, function (data) {
                        self.film = data;
                        document.title = self.film.title;
                    }),
                    $.getJSON('/category/forNav', function (categories) {
                        self.categories = categories;
                    }),
                    $.getJSON('/comment/getFilmComments/' + id, function (comments) {
                        self.comments = comments;
                    }),
                    $.getJSON('/client/getCurrentUser', function (user) {
                        self.currUser = user;
                        if (self.currUser.login != null) {
                            $.getJSON('/bookmark/checkBookmarkFilm/' + id, function (flag) {
                                self.bookmarked = flag;
                            });
                            $.getJSON('/likes/checkLikeFilm/' + id, function (liked) {
                                if (liked.name !== 'error') {
                                    self.liked = liked;

                                    if (liked) {
                                        self.disliked = false;
                                    } else {
                                        $.getJSON('/likes/checkDislikeFilm/' + id, function (disliked) {
                                            self.disliked = disliked;
                                        });
                                    }
                                } else {
                                    self.liked = false;
                                    self.disliked = false;
                                }

                            });
                        }
                    }),
                    $.ajax({
                        url: domain + '/likes/getLikesAndDislikes',
                        type: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify(filmLikesJSON),
                        success: function (response) {
                            self.likes = response.likes;
                            self.dislikes = response.dislikes;
                        }
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
                goToEdit: function (val) {
                    window.location.replace(domain + "/admin/films/addOrUpdate/" + id);
                },
                blockUser: function (login) {
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    $(document).ajaxSend(function (e, xhr, options) {
                        xhr.setRequestHeader(header, token);
                    });
                    var clientLoginJSON = {};
                    clientLoginJSON['login'] = login;
                    $.ajax({
                        url: '/client/blockClient',
                        type: 'POST',
                        data: JSON.stringify(clientLoginJSON),
                        contentType: 'application/json',
                        success: function (data) {
                            alert("user successfully blocked");
                        },
                        error: function (xhr, textStatus, errorThrown) {
                            console.log('Error in Operation');
                            console.log('Text status: ' + textStatus);
                            console.log('XHR: ' + xhr);
                            console.log('Error thrown: ' + errorThrown);
                            fail(domain + "/admin/users");
                        }
                    });
                },
                dropDownUserMenu: function () {
                    if ($('#userDropDown')[0].classList.contains("is-active"))
                        $('#userDropDown').removeClass("is-active");
                    else
                        $('#userDropDown').addClass("is-active");
                },
                moreInfo: function (id) {
                    window.location.replace(domain + "/admin/users/more/" + id);
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
                submitCommentOrMoveLine: function (event) {
                    if (this.check) {
                        event.preventDefault();
                        if (removeWhiteSpaceAndNewLine($('#newComment').val())) {
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
                    }
                },
                submitComment: function () {
                    if (removeWhiteSpaceAndNewLine($('#newComment').val())) {
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
                deleteComment: function (idComment) {
                    $("article[data-comment-id=" + idComment + "]").addClass("is-deleting");
                    var self = this;
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    $(document).ajaxSend(function (e, xhr, options) {
                        xhr.setRequestHeader(header, token);
                    });
                    $.ajax({
                        url: domain + '/comment/delete/' + idComment,
                        type: 'POST',
                        success: function () {
                            $.getJSON('/comment/getFilmComments/' + id, function (comments) {
                                self.comments = comments;
                            });
                        }
                    });
                },
                hideBookmarksInfo: function (event) {
                    $(event.currentTarget.parentElement.parentElement).slideUp();
                },
                addRemoveBookMark: function (event) {
                    var self = this;
                    var $bookmarkInfo = $('#bookmarkInfo');
                    var $bookmark = $('#bookmark');
                    if (event.currentTarget.classList.contains("is-bookmarked")) {
                        self.bookmarked = false;
                        $bookmark.removeClass("is-bookmarked");
                        if ($bookmarkInfo.is(":visible")) {
                            $bookmarkInfo.slideUp();
                        }

                        var token = $("meta[name='_csrf']").attr("content");
                        var header = $("meta[name='_csrf_header']").attr("content");
                        $(document).ajaxSend(function (e, xhr, options) {
                            xhr.setRequestHeader(header, token);
                        });

                        var bookmark = {};
                        bookmark['idFilm'] = id;

                        $.ajax({
                            url: domain + '/bookmark/delete',
                            type: 'POST',
                            contentType: 'application/json',
                            data: JSON.stringify(bookmark)
                        });
                    } else {
                        self.bookmarked = true;
                        $bookmark.addClass("is-bookmarked");
                        if ($bookmarkInfo.is(":visible")) {
                            return;
                        }

                        var token = $("meta[name='_csrf']").attr("content");
                        var header = $("meta[name='_csrf_header']").attr("content");
                        $(document).ajaxSend(function (e, xhr, options) {
                            xhr.setRequestHeader(header, token);
                        });

                        var bookmark = {};
                        bookmark['idFilm'] = id;

                        $.ajax({
                            url: domain + '/bookmark/save',
                            type: 'POST',
                            contentType: 'application/json',
                            data: JSON.stringify(bookmark)
                        });
                        $bookmarkInfo.slideDown();
                    }
                },
                like: function () {
                    var self = this;

                    var filmLikesJSON = {};
                    filmLikesJSON['filmId'] = id;
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    $(document).ajaxSend(function (e, xhr, options) {
                        xhr.setRequestHeader(header, token);
                    });

                    if (self.currUser.login != null) {
                        if (self.disliked) {
                            self.disliked = false;
                            self.liked = true;
                            self.likes += 1;
                            self.dislikes -= 1;
                            $.ajax({
                                url: domain + '/likes/addLike',
                                type: 'POST',
                                contentType: 'application/json',
                                data: JSON.stringify(filmLikesJSON),
                                success: function (response) {
                                    $.ajax({
                                        url: domain + '/likes/removeDislike',
                                        type: 'POST',
                                        contentType: 'application/json',
                                        data: JSON.stringify(filmLikesJSON)
                                    });
                                }
                            });
                        } else if (!self.liked) {
                            $.ajax({
                                url: domain + '/likes/addLike',
                                type: 'POST',
                                contentType: 'application/json',
                                data: JSON.stringify(filmLikesJSON)
                            });
                            self.liked = true;
                            self.likes += 1;
                        } else if (self.liked) {
                            self.liked = false;
                            self.likes -= 1;
                            $.ajax({
                                url: domain + '/likes/removeLike',
                                type: 'POST',
                                contentType: 'application/json',
                                data: JSON.stringify(filmLikesJSON)
                            });
                        }
                    } else {
                        $('#likeInfoMessage').slideDown();
                    }
                },
                dislike: function () {
                    var self = this;

                    var filmLikesJSON = {};
                    filmLikesJSON['filmId'] = id;
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    $(document).ajaxSend(function (e, xhr, options) {
                        xhr.setRequestHeader(header, token);
                    });
                    if (self.currUser.login != null) {
                        if (self.liked) {
                            $.ajax({
                                url: domain + '/likes/removeLike',
                                type: 'POST',
                                contentType: 'application/json',
                                data: JSON.stringify(filmLikesJSON),
                                success: function (response) {
                                    $.ajax({
                                        url: domain + '/likes/addDislike',
                                        type: 'POST',
                                        contentType: 'application/json',
                                        data: JSON.stringify(filmLikesJSON)
                                    });
                                }
                            });
                            self.liked = false;
                            self.disliked = true;
                            self.dislikes += 1;
                            self.likes -= 1;
                        } else if (!self.disliked) {
                            $.ajax({
                                url: domain + '/likes/addDislike',
                                type: 'POST',
                                contentType: 'application/json',
                                data: JSON.stringify(filmLikesJSON)
                            });
                            self.disliked = true;
                            self.dislikes += 1;
                        } else if (self.disliked) {
                            $.ajax({
                                url: domain + '/likes/removeDislike',
                                type: 'POST',
                                contentType: 'application/json',
                                data: JSON.stringify(filmLikesJSON)
                            });
                            self.disliked = false;
                            self.dislikes -= 1;
                        }
                    } else {
                        $('#likeInfoMessage').slideDown();
                    }

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

    } else if (window.location.pathname.indexOf("/profile") > -1) {
        var section = getUrlParameter("s");
        removeGetParametersFromPage("/profile");

        var profile = new Vue({
            el: '.vue',
            data: {
                info: [],
                currentTab: "profile",
                bookmarkedFilms: [],
                likedFilms: [],
                currentPage: 1,
                link: domain + '/film/',
                pagesNumberLiked: 1,
                pagesNumberBookmarked: 1,
                domain: window.location.origin
            },
            beforeCompile: function () {
                var self = this;

                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                $(document).ajaxSend(function (e, xhr, options) {
                    xhr.setRequestHeader(header, token);
                });

                if (section) {
                    if (section === "book") {
                        $('#profile').removeClass("is-active");
                        $('#bookmarks').addClass("is-active");
                        self.currentTab = "book";
                        self.currentPage = 1;
                        $.when($.getJSON(domain + '/bookmark/getBookmarks/' + self.currentPage, function (films) {
                                self.bookmarkedFilms = films;
                            }),
                            $.getJSON(domain + '/bookmark/numberOfBookmarks', function (filmsNumber) {
                                if (filmsNumber / 6 !== parseInt(filmsNumber / 6, 10))
                                    self.pagesNumberBookmarked = parseInt(filmsNumber / 6, 10) + 1;
                                else
                                    self.pagesNumberBookmarked = parseInt(filmsNumber / 6, 10);
                            })).done(function () {
                            hideLoadingProfile();
                        });
                    } else if (section === "like") {
                        $('#profile').removeClass("is-active");
                        $('#liked').addClass("is-active");
                        self.currentTab = "like";
                        self.currentPage = 1;
                        $.when($.getJSON(domain + '/likes/getLiked/' + self.currentPage, function (films) {
                                self.likedFilms = films;
                            }),
                            $.getJSON(domain + '/likes/numberOfLiked', function (filmsNumber) {
                                if (filmsNumber / 6 !== parseInt(filmsNumber / 6, 10))
                                    self.pagesNumberLiked = parseInt(filmsNumber / 6, 10) + 1;
                                else
                                    self.pagesNumberLiked = parseInt(filmsNumber / 6, 10);
                            })).done(function () {
                            hideLoadingProfile();
                        });
                    } else {
                        $.getJSON("/client/getCurrentUser", function (data) {
                            self.info = data;
                            hideLoadingProfile();
                        }).fail(function () {
                            window.location.replace(domain + "/login");
                        });
                    }
                } else {
                    $.getJSON("/client/getCurrentUser", function (data) {
                        self.info = data;
                        hideLoadingProfile();
                    }).fail(function () {
                        window.location.replace(domain + "/login");
                    });
                }
            },
            methods: {
                goToProfile: function () {
                    var self = this;
                    self.currentTab = "profile";
                    showLoadingProfile();
                    $('a[class*="is-active"]').removeClass("is-active");
                    $('#profile').addClass("is-active");
                    $.getJSON("/client/getCurrentUser", function (data) {
                        self.info = data;
                        hideLoadingProfile();
                    }).fail(function () {
                        window.location.replace(domain + "/login");
                    });
                },
                goToLiked: function () {
                    var self = this;
                    self.currentTab = "like";
                    self.currentPage = 1;
                    showLoadingProfile();
                    $('a[class*="is-active"]').removeClass("is-active");
                    $('#liked').addClass("is-active");
                    $.when($.getJSON(domain + '/likes/getLiked/' + self.currentPage, function (films) {
                            self.likedFilms = films;
                        }),
                        $.getJSON(domain + '/likes/numberOfLiked', function (filmsNumber) {
                            if (filmsNumber / 6 !== parseInt(filmsNumber / 6, 10))
                                self.pagesNumberLiked = parseInt(filmsNumber / 6, 10) + 1;
                            else
                                self.pagesNumberLiked = parseInt(filmsNumber / 6, 10);
                        })).done(function () {
                        hideLoadingProfile();
                    });
                },
                goToBookmarks: function () {
                    var self = this;
                    self.currentTab = "book";
                    self.currentPage = 1;
                    showLoadingProfile();
                    $('a[class*="is-active"]').removeClass("is-active");
                    $('#bookmarks').addClass("is-active");
                    $.when($.getJSON(domain + '/bookmark/getBookmarks/' + self.currentPage, function (films) {
                            self.bookmarkedFilms = films;
                        }),
                        $.getJSON(domain + '/bookmark/numberOfBookmarks', function (filmsNumber) {
                            if (filmsNumber / 6 !== parseInt(filmsNumber / 6, 10))
                                self.pagesNumberBookmarked = parseInt(filmsNumber / 6, 10) + 1;
                            else
                                self.pagesNumberBookmarked = parseInt(filmsNumber / 6, 10);
                        })).done(function () {
                        hideLoadingProfile();
                    });
                },
                getYear: function (val) {
                    var date = new Date(Date.parse(val));
                    return date.getFullYear();
                },
                filmClicked: function (ev) {
                    if ($(ev.target).parents().hasClass('layout-default bd-is-clipped-touch') && $(ev.target).parents().hasClass('hero background_for_client')) {
                        ev.preventDefault();
                    }
                },
                goToPage: function (pageNum) {
                    var self = this;
                    showLoadingProfile();
                    self.currentPage = parseInt(pageNum);
                    if (self.currentTab === "book") {
                        showLoadingProfile();
                        $('a[class*="is-active"]').removeClass("is-active");
                        $('#bookmarks').addClass("is-active");
                        $.getJSON(domain + '/bookmark/getBookmarks/' + pageNum, function (films) {
                            self.bookmarkedFilms = films;
                            $(window).scrollTop(0);
                            $(function () {
                                $('a[data-pagenum]').removeClass("is-current");
                                $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                            });
                            hideLoadingProfile();
                        });
                    } else {
                        showLoadingProfile();
                        $('a[class*="is-active"]').removeClass("is-active");
                        $('#liked').addClass("is-active");
                        $.getJSON(domain + '/likes/getLiked/' + pageNum, function (films) {
                            self.likedFilms = films;
                            $(window).scrollTop(0);
                            $(function () {
                                $('a[data-pagenum]').removeClass("is-current");
                                $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                            });
                            hideLoadingProfile();
                        });
                    }
                },
                goToPrevious: function (event) {
                    if (!$(event.currentTarget).attr('disabled')) {
                        var self = this;
                        showLoadingProfile();
                        if (self.currentTab === "book") {
                            showLoadingProfile();
                            $('a[class*="is-active"]').removeClass("is-active");
                            $('#bookmarks').addClass("is-active");
                            $.getJSON(domain + '/bookmark/getBookmarks/' + (parseInt(self.currentPage) - 1), function (films) {
                                self.currentPage = parseInt(self.currentPage) - 1;
                                self.bookmarkedFilms = films;
                                $(window).scrollTop(0);
                                $(function () {
                                    $('a[data-pagenum]').removeClass("is-current");
                                    $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                                });
                                hideLoadingProfile();
                            });
                        } else {
                            showLoadingProfile();
                            $('a[class*="is-active"]').removeClass("is-active");
                            $('#liked').addClass("is-active");
                            $.getJSON(domain + '/likes/getLiked/' + (parseInt(self.currentPage) - 1), function (films) {
                                self.currentPage = parseInt(self.currentPage) - 1;
                                self.likedFilms = films;
                                $(window).scrollTop(0);
                                $(function () {
                                    $('a[data-pagenum]').removeClass("is-current");
                                    $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                                });
                                hideLoadingProfile();
                            });
                        }
                    }
                },
                goToNext: function (event) {
                    if (!$(event.currentTarget).attr('disabled')) {
                        var self = this;
                        showLoadingProfile();
                        if (self.currentTab === "book") {
                            showLoadingProfile();
                            $('a[class*="is-active"]').removeClass("is-active");
                            $('#bookmarks').addClass("is-active");
                            $.getJSON(domain + '/bookmark/getBookmarks/' + (parseInt(self.currentPage) + 1), function (films) {
                                self.currentPage = parseInt(self.currentPage) + 1;
                                self.bookmarkedFilms = films;
                                $(window).scrollTop(0);
                                $(function () {
                                    $('a[data-pagenum]').removeClass("is-current");
                                    $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                                });
                                hideLoadingProfile();
                            });
                        } else {
                            showLoadingProfile();
                            $('a[class*="is-active"]').removeClass("is-active");
                            $('#liked').addClass("is-active");
                            $.getJSON(domain + '/likes/getLiked/' + (parseInt(self.currentPage) + 1), function (films) {
                                self.currentPage = parseInt(self.currentPage) + 1;
                                self.likedFilms = films;
                                $(window).scrollTop(0);
                                $(function () {
                                    $('a[data-pagenum]').removeClass("is-current");
                                    $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                                });
                                hideLoadingProfile();
                            });
                        }
                    }
                },
                chooseImg: function () {
                    var $radioImgs = $('#radioImgs');
                    if ($radioImgs.css('display') === 'block')
                        $radioImgs.slideUp();
                    else
                        $radioImgs.slideDown();
                }
                ,
                changeImg: function (event) {
                    var self = this;
                    self.info.avatar = $($(event.currentTarget).next().children()[0]).attr('src');
                    $('button[class*="is-info"]').removeClass("is-static");
                }
            }
        });
        var flags = [true, true, true, true];
        var button = $('button[class*="is-info"]');
        $('input[name="firstName"]').keyup(function () {
            if (this.value.length >= 2 && !this.value.match(/\d+/g) && this.value.length <= 64) {
                if (this.classList.contains("is-danger")) {
                    if ($('.help')[0].style.display == "block") $('.help')[0].style.display = "none";
                    $(this).removeClass("is-danger");
                }
                flags[0] = true;
                if (!flags.includes(false)) {
                    button.removeClass("is-static");
                }
                profile.info.firstName = this.value;
                $(this).addClass("is-success");
            } else {
                if (this.classList.contains("is-success")) $(this).removeClass("is-success");
                $('.help')[0].style.display = "block";
                button.addClass("is-static");
                flags[0] = false;
                $(this).addClass("is-danger");
            }
        });
        $('input[name="firstName"]').change(function () {
            if (this.value.length >= 2 && !this.value.match(/\d+/g) && this.value.length <= 64) {
                if (this.classList.contains("is-danger")) {
                    if ($('.help')[0].style.display == "block") $('.help')[0].style.display = "none";
                    $(this).removeClass("is-danger");
                }
                flags[0] = true;
                if (!flags.includes(false)) {
                    button.removeClass("is-static");
                }
                profile.info.firstName = this.value;
                $(this).addClass("is-success");
            } else {
                if (this.classList.contains("is-success")) $(this).removeClass("is-success");
                $('.help')[0].style.display = "block";
                button.addClass("is-static");
                flags[0] = false;
                $(this).addClass("is-danger");
            }
        });
        $('input[name="lastName"]').keyup(function () {
            if (this.value.length >= 2 && !this.value.match(/\d+/g) && this.value.length <= 64) {
                if (this.classList.contains("is-danger")) {
                    if ($('.help')[1].style.display == "block") $('.help')[1].style.display = "none";
                    $(this).removeClass("is-danger");
                }
                flags[1] = true;
                if (!flags.includes(false)) {
                    button.removeClass("is-static");
                }
                profile.info.lastName = this.value;
                $(this).addClass("is-success");
            } else {
                if (this.classList.contains("is-success")) $(this).removeClass("is-success");
                $('.help')[1].style.display = "block";
                button.addClass("is-static");
                flags[1] = false;
                $(this).addClass("is-danger");
            }
        });
        $('input[name="lastName"]').change(function () {
            if (this.value.length >= 2 && !this.value.match(/\d+/g) && this.value.length <= 64) {
                if (this.classList.contains("is-danger")) {
                    if ($('.help')[1].style.display == "block") $('.help')[1].style.display = "none";
                    $(this).removeClass("is-danger");
                }
                flags[1] = true;
                if (!flags.includes(false)) {
                    button.removeClass("is-static");
                }
                profile.info.lastName = this.value;
                $(this).addClass("is-success");
            } else {
                if (this.classList.contains("is-success")) $(this).removeClass("is-success");
                $('.help')[1].style.display = "block";
                button.addClass("is-static");
                flags[1] = false;
                $(this).addClass("is-danger");
            }
        });
        $('input[name="phoneNumber"]').keyup(function (event) {
            if (this.value.length > 6) {
                $('.help')[3].style.display = "none";
                if (this.classList.contains("is-danger")) {
                    $(this).removeClass("is-danger");
                }
                flags[3] = true;
                if (!flags.includes(false)) {
                    button.removeClass("is-static");
                }
                profile.info.phoneNumber = this.value;
                $(this).addClass("is-success");
            } else {
                $('.help')[3].style.display = "block";
                if (this.classList.contains("is-success")) {
                    $(this).removeClass("is-success");
                }
                button.addClass("is-static");
                flags[3] = false;
                $(this).addClass("is-danger");
            }
        });
        $('input[name="phoneNumber"]').change(function (event) {
            if (this.value.length > 6) {
                $('.help')[3].style.display = "none";
                if (this.classList.contains("is-danger")) {
                    $(this).removeClass("is-danger");
                }
                flags[3] = true;
                if (!flags.includes(false)) {
                    button.removeClass("is-static");
                }
                profile.info.phoneNumber = this.value;
                $(this).addClass("is-success");
            } else {
                $('.help')[3].style.display = "block";
                if (this.classList.contains("is-success")) {
                    $(this).removeClass("is-success");
                }
                button.addClass("is-static");
                flags[3] = false;
                $(this).addClass("is-danger");
            }
        });
        $('input[name="phoneNumber"]').keypress(function () {
            if (event.keyCode < 43 || event.keyCode > 57 || event.keyCode === 44 || event.keyCode === 45 || event.keyCode === 46 || event.keyCode === 47 || (event.keyCode === 43 && this.value.length !== 0))
                return false;
        });
        $('input').keypress(function () {
            if (event.keyCode === 32)
                return false;
        });
        $('button[class*="save"]').click(function () {
            var self = this;
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });

            $(self).addClass("is-loading");
            $.ajax({
                url: domain + "/client/edit",
                type: 'POST',
                contentType: 'application/json; utf-8',
                data: JSON.stringify(profile.info),
                success: function (data) {
                    if (data.name != "error") {
                        $(self).removeClass("is-loading");
                        $('.messageSuccessEdit').slideDown();
                    } else {
                        $('.messageError').slideDown();
                        $(self).removeClass("is-loading");
                    }
                },
                fail: function () {
                    $('.messageError').slideDown();
                }
            });

        });
        $('button[class*="pass"]').click(function () {
            var self = this;
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function (e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });

            var clientEmail = profile.info.email;
            $(self).addClass("is-loading");
            $.ajax({
                url: domain + "/client/forgotPassword",
                type: 'POST',
                contentType: 'application/json; utf-8',
                data: clientEmail,
                success: function (data) {
                    if (data.name != "error") {
                        $(self).removeClass("is-loading");
                        $('.messageSuccess').slideDown();
                    } else {
                        $('.messageError').slideDown();
                        $(self).removeClass("is-loading");
                    }
                },
                fail: function () {
                    $('.messageError').slideDown();
                }
            });
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
                filmClicked: function (ev) {
                    if ($(ev.target).parents().hasClass('layout-default bd-is-clipped-touch') && $(ev.target).parents().hasClass('hero background_for_client')) {
                        ev.preventDefault();
                    }
                },
                openCategory: function (id) {
                    window.location.replace(domain + '/?c=' + id);
                },
                goToPage: function (pageNum) {
                    var self = this;
                    showLoading();
                    $.getJSON('/film/filmsForBestPage/' + pageNum, function (data) {
                        self.currentPage = parseInt(pageNum);
                        self.films = data;
                        $(window).scrollTop(0);
                        $(function () {
                            $('a[data-pagenum]').removeClass("is-current");
                            $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                        });
                        hideLoading();
                    });
                },
                goToPrevious: function (event) {
                    if (!$(event.currentTarget).attr('disabled')) {
                        var self = this;
                        showLoading();
                        $.getJSON('/film/filmsForBestPage/' + (parseInt(self.currentPage) - 1), function (data) {
                            self.currentPage = parseInt(self.currentPage) - 1;
                            self.films = data;
                            $(window).scrollTop(0);
                            $(function () {
                                $('a[data-pagenum]').removeClass("is-current");
                                $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                            });
                            hideLoading();
                        });
                    }
                },
                goToNext: function (event) {
                    if (!$(event.currentTarget).attr('disabled')) {
                        var self = this;
                        showLoading();
                        $.getJSON('/film/filmsForBestPage/' + (parseInt(self.currentPage) + 1), function (data) {
                            self.currentPage = parseInt(self.currentPage) + 1;
                            self.films = data;
                            $(window).scrollTop(0);
                            $(function () {
                                $('a[data-pagenum]').removeClass("is-current");
                                $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                            });
                            hideLoading();
                        });
                    }
                }
            }
        });
    } else if (window.location.pathname.indexOf("/suggestion") > -1) {
        var pageNew = new Vue({
            el: '.vue',
            data: {
                films: [],
                categories: [],
                link: "/film/",
                pagesNumber: 0,
                currentPage: 1,
                logged: false
            },
            beforeCompile: function () {
                var self = this;
                $.when($.getJSON('/client/filmsForSuggestion/1', function (films) {
                        if (films.name != 'error') {
                            self.logged = true;
                            self.films = films;
                        }
                    }),
                    $.getJSON('/category/forNav', function (categories) {
                        self.categories = categories;
                    }),
                    $.getJSON('/client/numberOfSuggested', function (filmsNumber) {
                        if (filmsNumber / 10 !== parseInt(filmsNumber / 10, 10))
                            self.pagesNumber = parseInt(filmsNumber / 10, 10) + 1;
                        else
                            self.pagesNumber = parseInt(filmsNumber / 10, 10);
                    })).done(function () {
                    if (!self.logged)
                        self.pagesNumber = 0;
                    $(function () {
                        $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                    });
                    hideLoading();
                });
            },
            methods: {
                getYear: function (val) {
                    var date = new Date(Date.parse(val));
                    return date.getFullYear();
                },
                filmClicked: function (ev) {
                    if ($(ev.target).parents().hasClass('layout-default bd-is-clipped-touch') && $(ev.target).parents().hasClass('hero background_for_client')) {
                        ev.preventDefault();
                    }
                },
                openCategory: function (id) {
                    window.location.replace(domain + '/?c=' + id);
                },
                goToPage: function (pageNum) {
                    var self = this;
                    showLoading();
                    $.getJSON('/client/filmsForSuggestion/' + pageNum, function (data) {
                        self.currentPage = parseInt(pageNum);
                        self.films = data;
                        $(window).scrollTop(0);
                        $(function () {
                            $('a[data-pagenum]').removeClass("is-current");
                            $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                        });
                        hideLoading();
                    });
                },
                goToPrevious: function (event) {
                    if (!$(event.currentTarget).attr('disabled')) {
                        var self = this;
                        showLoading();
                        $.getJSON('/client/filmsForSuggestion/' + (parseInt(self.currentPage) - 1), function (data) {
                            self.currentPage = parseInt(self.currentPage) - 1;
                            self.films = data;
                            $(window).scrollTop(0);
                            $(function () {
                                $('a[data-pagenum]').removeClass("is-current");
                                $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                            });
                            hideLoading();
                        });
                    }
                },
                goToNext: function (event) {
                    if (!$(event.currentTarget).attr('disabled')) {
                        var self = this;
                        showLoading();
                        $.getJSON('/client/filmsForSuggestion/' + (parseInt(self.currentPage) + 1), function (data) {
                            self.currentPage = parseInt(self.currentPage) + 1;
                            self.films = data;
                            $(window).scrollTop(0);
                            $(function () {
                                $('a[data-pagenum]').removeClass("is-current");
                                $('a[data-pagenum=' + self.currentPage + ']').addClass("is-current");
                            });
                            hideLoading();
                        });
                    }
                }
            }
        });
    } else if (window.location.pathname === "/search") {
        if ($(window).width() >= 1024) {
            window.location.replace(domain);
        } else {
            window.addEventListener('resize', function () {
                if ($(window).width() >= 1024) {
                    window.location.replace(domain);
                }
            });
        }
    }


});