package web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Controller
@RequestMapping(value = "/actor")
public class ActorController {

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody String addActor(){

        return "OK";
    }
}
