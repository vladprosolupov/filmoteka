package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import web.dao.AwardDb;
import web.dao.FilmDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.exceptions.ParsingJsonToDaoException;
import web.model.FilmJSON;
import web.model.FilmJSONAdmin;
import web.model.FilmJSONIndex;
import web.services.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Rostyk on 12.06.2017.
 */

@Controller
@RequestMapping(value = "/film")
public class FilmController {

    @Autowired
    FilmService filmService;

    @Autowired
    AwardService awardService;

    @Autowired
    FilmActorService filmActorService;

    @Autowired
    TrailerService trailerService;

    @Autowired
    ScreenshotService screenshotService;

    private static final Logger log = LogManager.getLogger(FilmController.class);

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONAdmin> getAllFilms() {
        log.info("getAllFilms()");

        List<FilmJSONAdmin> allFilms = filmService.getAllFilms();

        log.info("getAllFilms() returns : allFilms.size()=" + allFilms.size());
        return allFilms;
    }

    @RequestMapping(value = "/filmsForIndexPage/{page}", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONIndex> getFilmsForIndexPage(@PathVariable("page") String page) throws NumberFormatException {
        log.info("getFilmsForIndexPage(page: " + page + " )");

        int pageNum = Integer.parseInt(page);
        List<FilmJSONIndex> filmsForIndexPage = filmService.getFilmsForIndexPage(pageNum);

        log.info("getFilmsForIndexPage(page: " + page + " ) returns : filmsForIndexPage.size()=" + filmsForIndexPage.size());
        return filmsForIndexPage;
    }

    @RequestMapping(value = "/filmsForBestPage/{page}", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONIndex> getFilmsForNewPage(@PathVariable("page") String page) throws NumberFormatException {
        log.info("getFilmsForBestPage(page: " + page + " )");

        int pageNum = Integer.parseInt(page);
        List<FilmJSONIndex> filmsForBestPage = filmService.getFilmsForNewPage(pageNum);

        log.info("getFilmsForBestPage(page: " + page + " ) returns : filmsForBestPage.size()=" + filmsForBestPage.size());
        return filmsForBestPage;
    }

    @RequestMapping(value = "/numberOfFilms", method = RequestMethod.GET)
    public @ResponseBody
    long getNumberOfFilms() {
        log.info("getNumberOfFilms()");

        long numberOfFilms = filmService.getNumberOfFilms();

        log.info("getNumberOfFilms() returns : numberOfFilms=" + numberOfFilms);
        return numberOfFilms;
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    String saveOrUpdate(@RequestBody @Valid FilmJSON filmToSave, BindingResult bindingResult) throws ParseException, ParsingJsonToDaoException {
        log.info("saveOrUpdate(filmsToSave=" + filmToSave + ")");

        if(bindingResult.hasErrors()) {
            log.error("Film does not pass validation");

            return "Error";
        }

        FilmDb filmDb = filmService.convert(filmToSave);

        filmService.saveOrUpdate(filmDb);
        awardService.checkForAwards(filmDb.getId(), filmDb.getAwardsById());
        filmActorService.checkForFilmActors(filmDb.getId(), filmDb.getFilmActorsById());
        screenshotService.checkForSceens(filmDb.getId(), filmDb.getScreenshotsById());
        trailerService.checkForTrailers(filmDb.getId(), filmDb.getTrailersById());

        log.info("saveOrUpdate() returns : OK");
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        log.info("delete(id=" + id + ")");

        filmService.delete(id);

        log.info("delete() returns : OK");
        return "OK";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getFilm(@PathVariable("id") String id, Model model) {
        log.info("getFilm(id=" + id + ", model=" + model + ")");

        FilmDb film = filmService.getFilmWithId(id);
        model.addAttribute("title", film.getTitle());

        log.info("getFilm() returns : film" +
                ", film.getTitle()=" + film.getTitle());
        return "film";
    }


    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public @ResponseBody
    FilmDb getFilmInfo(@PathVariable("id") String id) {
        log.info("getFilmInfo(id=" + id + ")");

        FilmDb film = filmService.getFilmWithId(id);

        log.info("getFilmInfo() returns : film=" + film.getTitle());
        return film;

    }
}
