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
import web.dao.AwardDb;
import web.services.AwardService;

import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Controller
@RequestMapping("/award")
public class AwardController {

    @Autowired
    private AwardService awardService;

    private static final Logger log = LogManager.getLogger(AwardController.class);

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    String addOrUpdate(AwardDb awardDb) {
        log.info("addOrUpdate(award=" + awardDb + ")");

        awardService.saveOrUpdateAward(awardDb);

        log.info("addOrUpdate() returns : OK");
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        log.info("delete(id=" + id + ")");

        awardService.deleteAward(id);

        log.info("delete() returns : OK");
        return "OK";
    }

//    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    List<AwardDb> getAll() {
        log.info("getAll()");

        List<AwardDb> awardDbs = awardService.getAll();

        log.info("getAll() returns awardDbs.size()=" + awardDbs.size());
        return awardDbs;
    }
}
