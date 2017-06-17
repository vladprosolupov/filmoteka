package web.controllers;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.dao.StudioDb;
import web.model.StudioJSON;
import web.services.StudioService;

import java.util.List;

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
    public @ResponseBody String addOrUpdateStudio(@RequestBody StudioJSON studioJSON){
        studioService.saveOrUpdateStudio(studioService.convertToStudioDb(studioJSON));
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String deleteStudio(@PathVariable("id") String id){
        studioService.deleteStudio(id);
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    List<StudioDb> getAll(){
        return studioService.getAll();
    }


}
