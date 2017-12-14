package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger log = LogManager.getLogger(FilmActorController.class);

//    @PreAuthorize("hasAuthority('admin')")
//    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
//    public @ResponseBody String save(@RequestBody FilmActorJSON filmActorJSON){
//        FilmActorDb filmActorDb = filmActorService.convert(filmActorJSON);
//        int id = filmActorService.saveOrUpdate(filmActorDb);
//        return Integer.toString(id);
//    }
//
//    @PreAuthorize("hasAuthority('admin')")
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public @ResponseBody String update(@RequestBody FilmActorJSON filmActorJSON){
//        FilmActorDb filmActorDb = filmActorService.convert(filmActorJSON);
//        int id = filmActorService.updateFilmActor(filmActorDb);
//        return Integer.toString(id);
//    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable("id") String id){
        log.info("delete(id=" + id + ")");

        filmActorService.deleteFilmActor(id);

        log.info("delete() returns : OK");
        return "OK";
    }
}
