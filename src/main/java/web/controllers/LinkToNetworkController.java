package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.model.LinkToNetworkJSON;
import web.services.LinkToNetworkService;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Controller
@RequestMapping("/linktonetwork")
public class LinkToNetworkController {

    @Autowired
    private LinkToNetworkService linkToNetworkService;

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody String save(LinkToNetworkJSON linkToNetworkJSON){
        int id = linkToNetworkService.save(linkToNetworkService.convert(linkToNetworkJSON));
        return Integer.toString(id);
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody String update(LinkToNetworkJSON linkToNetworkJSON){
        int id = linkToNetworkService.update(linkToNetworkService.convert(linkToNetworkJSON));
        return Integer.toString(id);
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String delete(@PathVariable("id") String id){
        linkToNetworkService.delete(id);
        return "OK";
    }
}
