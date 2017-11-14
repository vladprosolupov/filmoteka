package web.controllers;

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

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String addOrUpdate(@RequestBody NetworkJSON networkJSON){
        try {
            networkService.saveOrUpdate(networkService.convertToNetworkDb(networkJSON));
        } catch (ParsingJsonToDaoException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable("id") String id){
        networkService.delete(id);
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    List<NetworkDb> getAll(){
        return networkService.getAll();
    }
}
