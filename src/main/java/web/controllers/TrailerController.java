package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.dao.TrailerDb;
import web.services.TrailerService;

import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Controller
@RequestMapping("/trailer")
public class TrailerController {

    @Autowired
    private TrailerService trailerService;

    private static final Logger log = LogManager.getLogger(TrailerController.class);

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    String addOrUpdate(TrailerDb trailerDb) {
        log.info("addOrUpdate(trailerDb=" + trailerDb + ")");

        trailerService.saveOrUpdate(trailerDb);

        log.info("addORUpdate() returns : OK");
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        log.info("delete(id=" + id + ")");

        trailerService.delete(id);

        log.info("delete() returns : OK");
        return "OK";
    }

    //    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    List<TrailerDb> getAll() {
        log.info("getAll()");

        List<TrailerDb> all = trailerService.getAll();

        log.info("getAll() returns : all.size()=" + all.size());
        return all;
    }
}