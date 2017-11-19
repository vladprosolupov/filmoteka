package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.dao.NetworkDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.NetworkJSON;
import web.services.NetworkService;

import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Controller
@RequestMapping("/network")
public class NetworkController {

    @Autowired
    private NetworkService networkService;

    private static final Logger log = LogManager.getLogger(NetworkController.class);

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String addOrUpdate(@RequestBody NetworkJSON networkJSON) throws ParsingJsonToDaoException {
        log.info("addOrUpdate(networkJSON=" + networkJSON + ")");

        networkService.saveOrUpdate(networkService.convertToNetworkDb(networkJSON));

        log.info("addOrUpdate() returns : OK");
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        log.info("delete(id=" + id + ")");

        networkService.delete(id);

        log.info("delete() returns : OK");
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    List<NetworkDb> getAll() {
        log.info("getAll()");

        List<NetworkDb> networkDbs = networkService.getAll();

        log.info("getAll() returns : networkDbs.size()=" + networkDbs.size());
        return networkDbs;
    }
}
