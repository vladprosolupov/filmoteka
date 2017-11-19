package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.model.FilmJSONIndex;
import web.model.FilmJSONSearch;
import web.services.FilmService;
import web.services.SearchService;

import java.util.List;

@Controller
@RequestMapping(value = "/search")
public class SearchController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private SearchService searchService;

    private static final Logger log = LogManager.getLogger(SearchController.class);

    @RequestMapping(value = "/film/quick/{title}", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONSearch> searchQuick(@PathVariable("title") String title) {
        log.info("searchQuick(title=" + title + ")");

        List<FilmJSONSearch> filmsWithTitleForQuick = filmService.getFilmsWithTitleForQuick(searchService.titleToTitleSearch(title));

        log.info("searchQuick() returns : filmsWithTitleForQuick.size()=" + filmsWithTitleForQuick.size());
        return filmsWithTitleForQuick;
    }

    @RequestMapping(value = "/film/{title}", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONIndex> search(@PathVariable("title") String title) {
        log.info("search(title=" + title + ")");

        List<FilmJSONIndex> filmsWithTitle = filmService.getFilmsWithTitle(searchService.titleToTitleSearch(title));

        log.info("search() returns : filmsWithTitle.size()=" + filmsWithTitle.size());
        return filmsWithTitle;
    }

}
