package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.dao.ActorDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.ActorJSON;
import web.services.ActorService;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Controller
@RequestMapping(value = "/actor")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody String addActor(@RequestBody ActorJSON actorJSON){
        try {
            actorService.saveOrUpdate(actorService.convertToActorDb(actorJSON));
        } catch (ParsingJsonToDaoException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String deleteActor(@PathVariable("id") String id){
        actorService.delete(id);
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    List<ActorDb> getAll(){
        return actorService.getAll();
    }
}
