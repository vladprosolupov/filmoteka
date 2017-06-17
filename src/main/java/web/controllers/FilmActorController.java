package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.dao.FilmActorDb;
import web.model.FilmActorJSON;
import web.services.FilmActorService;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Controller
@RequestMapping("/filmactor")
public class FilmActorController {

    @Autowired
    private FilmActorService filmActorService;

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String save(@RequestBody FilmActorJSON filmActorJSON){
        FilmActorDb filmActorDb = filmActorService.convert(filmActorJSON);
        int id = filmActorService.saveFilmActor(filmActorDb);
        return Integer.toString(id);
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody String update(@RequestBody FilmActorJSON filmActorJSON){
        FilmActorDb filmActorDb = filmActorService.convert(filmActorJSON);
        int id = filmActorService.updateFilmActor(filmActorDb);
        return Integer.toString(id);
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable("id") String id){
        filmActorService.deleteFilmActor(id);
        return "OK";
    }
}
