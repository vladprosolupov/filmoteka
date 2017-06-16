package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.dao.CountryDb;
import web.services.CountryService;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Controller
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody String addOrUpdate(@RequestBody CountryDb countryDb){
        countryService.saveOrUpdateCountry(countryDb);
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String deleteCountry(@PathVariable("id") String id){
        countryService.deleteCountry(id);
        return "OK";
    }
}
