package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.validation.BindingResult;
import web.dao.FilmDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.exceptions.ParsingJsonToDaoException;
import web.exceptions.ValidationError;
import web.model.FilmJSON;
import web.model.FilmJSONAdmin;
import web.model.FilmJSONIndex;
import web.services.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Rostyk on 12.06.2017.
 */

@Controller
@RequestMapping(value = "/film")
public class FilmController {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger log = LogManager.getLogger(FilmController.class);

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONAdmin> getAllFilms() {
        log.info("getAllFilms()");

        FilmService filmService = new FilmService(sessionFactory);
        List<FilmJSONAdmin> allFilms = filmService.getAllFilms();

        log.info("getAllFilms() returns : allFilms.size()=" + allFilms.size());
        return allFilms;
    }

    @RequestMapping(value = "/filmsForIndexPage/{page}", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONIndex> getFilmsForIndexPage(@PathVariable("page") String page) throws NumberFormatException {
        log.info("getFilmsForIndexPage(page: " + page + " )");

        FilmService filmService = new FilmService(sessionFactory);
        int pageNum = Integer.parseInt(page);
        List<FilmJSONIndex> filmsForIndexPage = filmService.getFilmsForIndexPage(pageNum);

        log.info("getFilmsForIndexPage(page: " + page + " ) returns : filmsForIndexPage.size()=" + filmsForIndexPage.size());
        return filmsForIndexPage;
    }

    @RequestMapping(value = "/filmsForBestPage/{page}", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONIndex> getFilmsForNewPage(@PathVariable("page") String page) throws NumberFormatException, InterruptedException, ParseException, IOException, ParsingJsonToDaoException {
        log.info("getFilmsForBestPage(page: " + page + " )");

        FilmService filmService = new FilmService(sessionFactory);
        int pageNum = Integer.parseInt(page);
        List<FilmJSONIndex> filmsForBestPage = filmService.getFilmsForBestPage(pageNum);

        log.info("getFilmsForBestPage(page: " + page + " ) returns : filmsForBestPage.size()=" + filmsForBestPage.size());
        return filmsForBestPage;
    }

    @RequestMapping(value = "/numberOfFilms", method = RequestMethod.GET)
    public @ResponseBody
    long getNumberOfFilms() {
        log.info("getNumberOfFilms()");

        FilmService filmService = new FilmService(sessionFactory);
        long numberOfFilms = filmService.getNumberOfFilms();

        log.info("getNumberOfFilms() returns : numberOfFilms=" + numberOfFilms);
        return numberOfFilms;
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    String saveOrUpdate(@RequestBody @Valid FilmJSON filmToSave, BindingResult bindingResult) throws ParseException, ParsingJsonToDaoException, ValidationError {
        log.info("saveOrUpdate(filmsToSave=" + filmToSave + ")");

        if(bindingResult.hasErrors()) {
            log.error("Film does not pass validation");

            throw new ValidationError("Validation is incorrect");
        }

        FilmService filmService = new FilmService(sessionFactory);

        FilmDb filmDb = filmService.convert(filmToSave);

        AwardService awardService = new AwardService(sessionFactory);
        FilmActorService filmActorService = new FilmActorService(sessionFactory);
        ScreenshotService screenshotService = new ScreenshotService(sessionFactory);
        TrailerService trailerService = new TrailerService(sessionFactory);

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

        FilmService filmService = new FilmService(sessionFactory);
        filmService.delete(id);

        log.info("delete() returns : OK");
        return "OK";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getFilm(@PathVariable("id") String id) {
        log.info("getFilm(id=" + id);

        log.info("getFilm() returns : film");
        return "film";
    }


    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public @ResponseBody
    FilmDb getFilmInfo(@PathVariable("id") String id) {
        log.info("getFilmInfo(id=" + id + ")");

        FilmService filmService = new FilmService(sessionFactory);
        FilmDb film = filmService.getFilmWithId(id);

        log.info("getFilmInfo() returns : film=" + film.getTitle());
        return film;

    }
}
