package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.ClientJSON;
import web.services.FilmService;
import web.services.PasswordGenerator;

/**
 * Created by Rostyk on 12.06.2017.
 */
@Controller
public class RootWebController {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/403")
    public String error() {
        return "403";
    }


}
