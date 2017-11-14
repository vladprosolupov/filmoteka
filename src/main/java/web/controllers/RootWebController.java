package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Rostyk on 12.06.2017.
 */
@Controller
public class RootWebController {

    private static final Logger log = LogManager.getLogger(RootWebController.class);

    @RequestMapping(value = "/")
    public String index() {
        log.info("index()");

        return "index";
    }

    @RequestMapping(value = "/login")
    public String login() {
        log.info("login()");

        return "login";
    }

    @RequestMapping(value = "/403")
    public String error() {
        log.info("error()");

        return "403";
    }


}
