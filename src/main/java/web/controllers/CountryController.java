package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.dao.CountryDb;
import web.services.CountryService;

import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Controller
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private SessionFactory sessionFactory;
//    private CountryService countryService;

    private static final Logger log = LogManager.getLogger(CountryController.class);

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    String addOrUpdate(@RequestBody CountryDb countryDb) {
        log.info("addOrUpdate(countryDb=" + countryDb + ")");

        CountryService countryService = new CountryService(sessionFactory);
        countryService.saveOrUpdateCountry(countryDb);

        log.info("addOrUpdate() returns : OK");
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String deleteCountry(@PathVariable("id") String id) {
        log.info("deleteCountry(id=" + id + ")");

        CountryService countryService = new CountryService(sessionFactory);
        countryService.deleteCountry(id);

        log.info("deleteCountry() returns : OK");
        return "OK";
    }

//    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    List<CountryDb> getAll() {
        log.info("getAll()");

        CountryService countryService = new CountryService(sessionFactory);
        List<CountryDb> countryDbs = countryService.getAll();

        log.info("getAll() returns : countryDbs.size()=" + countryDbs.size());
        return countryDbs;
    }
}
