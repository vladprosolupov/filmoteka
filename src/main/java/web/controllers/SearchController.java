package web.controllers;

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

    @RequestMapping(value = "/film/quick/{title}", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONSearch> searchQuick(@PathVariable("title") String title){
        return filmService.getFilmsWithTitleForQuick(searchService.titleToTitleSearch(title));
    }

    @RequestMapping(value = "/film/{title}", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONIndex> search(@PathVariable("title") String title){
        return filmService.getFilmsWithTitle(searchService.titleToTitleSearch(title));
    }

}
