package controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Rostyk on 12.06.2017.
 */
@Controller
public class RootWebController {

    @RequestMapping(value = "/index")
    public String index() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails);
        return "index";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/kek")
    public String kekTEST() {
        return "kekTEST";
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
