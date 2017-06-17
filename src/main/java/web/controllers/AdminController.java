package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import web.dao.FilmDb;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import web.dao.LanguageDb;
import web.services.FilmService;
import web.services.LanguageService;

import java.util.List;

/**
 * Created by vladyslavprosolupov on 13.06.17.
 */
@Controller
@PreAuthorize("hasAuthority('admin')")
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    FilmService filmService;

    @Autowired
    LanguageService languageService;

    @RequestMapping(value = "/index")
    public String admin_index() {
        return "admin/index";
    }

    @RequestMapping(value = "/actors")
    public String admin_actors() {
        return "admin/actors/index";
    }

    @RequestMapping(value = "/categories")
    public String admin_categories() {
        return "admin/categories/index";
    }

    @RequestMapping(value = "/directors")
    public String admin_directors() {
        return "admin/directors/index";
    }

    @RequestMapping(value = "/films")
    public String admin_films() {
        return "admin/films/index";
    }

    @RequestMapping(value = "/films/addOrUpdate/{id}")
    public String admin_films_addOrUpdate(@PathVariable("id") String id, Model model) {
        FilmDb filmDb = new FilmDb();
        if (!id.equals("0")) {
            filmDb = filmService.getFilmWithId(id);
        }
        List<LanguageDb> listOfLanguageDbs = languageService.getAllLanguages();
        model.addAttribute("languages", listOfLanguageDbs);
        model.addAttribute("film", filmDb);
        return "admin/films/addOrUpdate";
    }

    @RequestMapping(value = "/networks")
    public String admin_networks() {
        return "admin/networks/index";
    }

    @RequestMapping(value = "/studios")
    public String admin_studios() {
        return "admin/studios/index";
    }

}
