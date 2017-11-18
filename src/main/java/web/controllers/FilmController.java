package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import web.dao.AwardDb;
import web.dao.FilmDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.exceptions.ParsingJsonToDaoException;
import web.model.FilmJSON;
import web.model.FilmJSONIndex;
import web.services.*;

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
    List<FilmDb> getAllFilms() {
        log.info("getAllFilms()");

        List<FilmDb> allFilms = filmService.getAllFilms();

        log.info("getAllFilms() returns : allFilms.size()=" + allFilms.size());
        return allFilms;
    }

    @RequestMapping(value = "/allForIndex", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONIndex> getAllFilmsForIndex() {
        log.info("getAllFilmsForIndex()");

        List<FilmJSONIndex> allFilmsForIndex = filmService.getAllFilmsForIndex();

        log.info("getAllFilmsForIndex() returns : allFilmsForIndex.size()=" + allFilmsForIndex.size());
        return allFilmsForIndex;
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    String saveOrUpdate(@RequestBody FilmJSON filmToSave) throws ParseException, ParsingJsonToDaoException {
        log.info("saveOrUpdate(filmsToSave=" + filmToSave + ")");

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
