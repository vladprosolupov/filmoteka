package web.controllers;

import web.dao.FilmDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.services.FilmService;

import java.util.List;

/**
 * Created by Rostyk on 12.06.2017.
 */

@Controller
@RequestMapping(value = "/film")
public class FilmController {

    @Autowired
    FilmService filmService;

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmDb> getAllFilms() {
        return filmService.getAllFilms();
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String saveOrUpdate(@RequestParam String filmToSave) {
        System.out.println(filmToSave);
        //filmService.saveOrUpdate(filmToSave);
        return "OK";
    }
}
