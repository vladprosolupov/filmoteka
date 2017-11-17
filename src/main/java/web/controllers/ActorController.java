package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.dao.ActorDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.ActorJSON;
import web.services.ActorService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Controller
@RequestMapping(value = "/actor")
public class ActorController {

    @Autowired
    private ActorService actorService;

    private static final Logger log = LogManager.getLogger(ActorController.class);

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String addActor(@RequestBody ActorJSON actorJSON) throws ParseException, ParsingJsonToDaoException {
        log.info("addActor(actorJSON=" + actorJSON + ")");

        actorService.saveOrUpdate(actorService.convertToActorDb(actorJSON));

        log.info("addActor() returns : OK");
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String deleteActor(@PathVariable("id") String id) {
        log.info("deleteActor(id=" + id + ")");

        actorService.delete(id);

        log.info("deleteActor() returns : OK");
        return "OK";
    }

//    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    List<ActorDb> getAll() {
        log.info("getAll()");

        List<ActorDb> all = actorService.getAll();

        log.info("getAll() returns list.size() : " + all.size());
        return all;
    }
}
