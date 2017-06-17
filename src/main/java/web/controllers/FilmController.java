package web.controllers;

import web.dao.AwardDb;
import web.dao.FilmDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.model.FilmJSON;
import web.services.*;

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

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
     String saveOrUpdate(@RequestBody FilmJSON filmToSave) {
        FilmDb filmDb = filmService.convert(filmToSave);
        filmService.saveOrUpdate(filmDb);
        awardService.checkForAwards(filmDb.getId(), filmDb.getAwardsById());
        filmActorService.checkForFilmActors(filmDb.getId(), filmDb.getFilmActorsById());
        screenshotService.checkForSceens(filmDb.getId(), filmDb.getScreenshotsById());
        trailerService.checkForTrailers(filmDb.getId(), filmDb.getTrailersById());
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable("id") String id){
        filmService.delete(id);
        return "OK";
    }
}
