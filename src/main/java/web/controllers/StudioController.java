package web.controllers;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.dao.StudioDb;
import web.services.StudioService;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Controller
@RequestMapping("/studio")
public class StudioController {

    @Autowired
    private StudioService studioService;

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody String addOrUpdateStudio(@RequestBody StudioDb studioDb){
        studioService.saveOrUpdateStudio(studioDb);
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String deleteStudio(@PathVariable("id") String id){
        studioService.deleteStudio(id);
        return "OK";
    }
}
