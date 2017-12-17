package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.dao.StudioDb;
import web.exceptions.ParsingJsonToDaoException;
import web.exceptions.ValidationError;
import web.model.StudioJSON;
import web.services.StudioService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Controller
@RequestMapping("/studio")
public class StudioController {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger log = LogManager.getLogger(StudioController.class);

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String addOrUpdateStudio(@RequestBody @Valid StudioJSON studioJSON, BindingResult bindingResult) throws ParsingJsonToDaoException, ValidationError {
        log.info("addOrUpdate(studioJSON=" + studioJSON + ")");

        if(bindingResult.hasErrors()) {
            log.error("Studio does not pass validation");

            throw new ValidationError("Validation is incorrect");
        }

        StudioService studioService = new StudioService(sessionFactory);
        studioService.saveOrUpdateStudio(studioService.convertToStudioDb(studioJSON));

        log.info("addOrUpdate() returns : OK");
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String deleteStudio(@PathVariable("id") String id) {
        log.info("delete(id=" + id + ")");

        StudioService studioService = new StudioService(sessionFactory);
        studioService.deleteStudio(id);

        log.info("delete() returns : OK");
        return "OK";
    }

    //    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    List<StudioDb> getAll() {
        log.info("getAll()");

        StudioService studioService = new StudioService(sessionFactory);
        List<StudioDb> studioDbs = studioService.getAll();

        log.info("getAll() returns : studioDbs.size()=" + studioDbs.size());
        return studioDbs;
    }


}
