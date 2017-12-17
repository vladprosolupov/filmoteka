package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
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
    private SessionFactory sessionFactory;

    private static final Logger log = LogManager.getLogger(SearchController.class);

    @RequestMapping(value = "/film/quick/{title}", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONSearch> searchQuick(@PathVariable("title") String title) {
        log.info("searchQuick(title=" + title + ")");

        FilmService filmService = new FilmService(sessionFactory);
        SearchService searchService = new SearchService();
        List<FilmJSONSearch> filmsWithTitleForQuick = filmService.getFilmsWithTitleForQuick(searchService.titleToTitleSearch(title));

        log.info("searchQuick() returns : filmsWithTitleForQuick.size()=" + filmsWithTitleForQuick.size());
        return filmsWithTitleForQuick;
    }

    @RequestMapping(value = "/film/count/{title}", method = RequestMethod.GET)
    public @ResponseBody
    long numberOfFilmsWithTitle(@PathVariable("title") String title) {
        log.info("numberOfFilmsWithTitle(title=" + title + ")");

        FilmService filmService = new FilmService(sessionFactory);
        SearchService searchService = new SearchService();
        long result = filmService.getNumberOfFilmsWithTitle(searchService.titleToTitleSearch(title));

        log.info("numberOfFilmsWithTitle() returns : result=" + result);
        return result;
    }

    @RequestMapping(value = "/film/{title}/{page}", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONIndex> search(@PathVariable("title") String title, @PathVariable("page") String page) {
        log.info("search(title=" + title + ", page=" + page + ")");

        FilmService filmService = new FilmService(sessionFactory);
        SearchService searchService = new SearchService();
        List<FilmJSONIndex> filmsWithTitle = filmService.getFilmsWithTitle(searchService.titleToTitleSearch(title), page);

        log.info("search() returns : filmsWithTitle.size()=" + filmsWithTitle.size());
        return filmsWithTitle;
    }

}
