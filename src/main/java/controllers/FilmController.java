package controllers;

import dao.FilmDb;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.FilmService;

import java.util.List;

/**
 * Created by Rostyk on 12.06.2017.
 */

@Controller
@RequestMapping(value = "/film")
public class FilmController {

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public @ResponseBody
    List<FilmDb> getAllFilms() {
        return FilmService.getAllFilms();
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String saveOrUpdate(@RequestParam FilmDb filmToSave) {
        FilmService.saveOrUpdate(filmToSave);
        return "OK";
    }
}
