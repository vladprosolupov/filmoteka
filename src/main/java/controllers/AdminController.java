package controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by vladyslavprosolupov on 13.06.17.
 */
@Controller
@PreAuthorize("hasAuthority('admin')")
@RequestMapping(value = "/admin")
public class AdminController {

    @RequestMapping(value = "/index")
    public String admin_index() {
        return "admin/index";
    }

    @RequestMapping(value = "/actors")
    public String admin_actors(){
        return "admin/actors";
    }

    @RequestMapping(value = "/categories")
    public String admin_categories(){
        return "admin/categories";
    }

    @RequestMapping(value = "/directors")
    public String admin_directors(){
        return "admin/directors";
    }

    @RequestMapping(value = "/films")
    public String admin_films(){
        return "admin/films";
    }

    @RequestMapping(value = "/networks")
    public String admin_networks(){
        return "admin/networks";
    }

    @RequestMapping(value = "/studios")
    public String admin_studios(){
        return "admin/studios";
    }

}
