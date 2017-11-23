package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.BookmarkJSON;
import web.model.FilmJSONIndex;
import web.services.BookmarkService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/bookmark")
public class BookmarkController {

    private static final Logger log = LogManager.getLogger(BookmarkController.class);

    @Autowired
    private BookmarkService bookmarkService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String addBookmark(@RequestBody @Valid BookmarkJSON bookmarkJSON, BindingResult bindingResult) {
        log.info("addBookmark(bookmarkJSON=" + bookmarkJSON + ")");

        if (bindingResult.hasErrors()) {
            log.error("bookmark does not pass validation");

            return "Error";
        }
        bookmarkService.saveOrUpdate(bookmarkService.convertToBookmarkDbFromBookmark(bookmarkService.convertToBookmarkFromBookmarkJSON(bookmarkJSON)));

        log.info("addBookmark() returns : OK");
        return "OK";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    String deleteBookmark(@RequestBody @Valid BookmarkJSON bookmarkJSON, BindingResult bindingResult) {
        log.info("deleteBookmark(bookmarkJSON=" + bookmarkJSON + ")");

        if (bindingResult.hasErrors()) {
            log.error("bookmark does not pass validation");

            return "Error";
        }
        bookmarkService.delete(bookmarkService.convertToBookmarkDbFromBookmark(bookmarkService.convertToBookmarkFromBookmarkJSON(bookmarkJSON)));

        log.info("deleteBookmark() returns : OK");
        return "OK";
    }

    @RequestMapping(value = "/checkBookmarkFilm/{id}", method = RequestMethod.GET)
    public @ResponseBody
    boolean checkBookmarkFilm(@PathVariable("id") String id) {
        log.info("checkBookmarkFilm(idFilm=" + id + ")");
        boolean result = bookmarkService.checkBookmarkFilm(id);
        log.info("checkBookmarkFilm() returns : " + result);
        return result;
    }

    @RequestMapping(value = "/getBookmarks/{page}", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONIndex> getBookmarkedFilms(@PathVariable("page") String page) {
        log.info("getBookmarkedFilms(page=" + page + ")");

        List<FilmJSONIndex> list = bookmarkService.getBookmarkedFilms(page);

        log.info("getBookmarkedFilms() returns : list.size()=" + list.size() + ")");
        return list;
    }

}
