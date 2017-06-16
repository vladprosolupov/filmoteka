package web.controllers;

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

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody String addOrUpdate(AwardDb awardDb){
        awardService.saveOrUpdateAward(awardDb);
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable("id") String id){
        awardService.deleteAward(id);
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public @ResponseBody
    List<AwardDb> getAll(){
        return awardService.getAll();
    }
}
