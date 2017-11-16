package web.controllers;

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

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmDb> getAllFilms() {
        return filmService.getAllFilms();
    }

    @RequestMapping(value = "/allForIndex", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONIndex> getAllFilmsForIndex() {
        return filmService.getAllFilmsForIndex();
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    String saveOrUpdate(@RequestBody FilmJSON filmToSave) {
        FilmDb filmDb = null;
        try {
            filmDb = filmService.convert(filmToSave);
        } catch (ParsingJsonToDaoException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        filmService.saveOrUpdate(filmDb);
        awardService.checkForAwards(filmDb.getId(), filmDb.getAwardsById());
        filmActorService.checkForFilmActors(filmDb.getId(), filmDb.getFilmActorsById());
        screenshotService.checkForSceens(filmDb.getId(), filmDb.getScreenshotsById());
        trailerService.checkForTrailers(filmDb.getId(), filmDb.getTrailersById());
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        filmService.delete(id);
        return "OK";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getFilm(@PathVariable("id") String id, Model model) {
        FilmDb film = new FilmDb();
        try {
            film = filmService.getFilmWithId(id);
        } catch (IndexOutOfBoundsException ex) {
            return "404";
        }
        model.addAttribute("title", film.getTitle());
        return "film";
    }


    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public @ResponseBody
    FilmDb getFilmInfo(@PathVariable("id") String id) {
        FilmDb film = new FilmDb();
        try {
            film = filmService.getFilmWithId(id);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
        return film;

    }
}
