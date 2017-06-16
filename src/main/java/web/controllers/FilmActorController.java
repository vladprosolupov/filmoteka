package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
        FilmActorDb filmActorDb = filmActorJSON.convert();
        int id = filmActorService.saveFilmActor(filmActorDb);
        return Integer.toString(id);
    }
}
