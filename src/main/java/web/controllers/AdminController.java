package web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import web.dao.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import web.services.*;

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

    @Autowired
    CountryService countryService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    DirectorService directorService;

    @Autowired
    NetworkService networkService;

    @Autowired
    StudioService studioService;

    @Autowired
    ActorService actorService;

    @RequestMapping(value = "/index")
    public String admin_index() {
        return "admin/index";
    }

    @RequestMapping(value = "/actors")
    public String admin_actors() {
        return "admin/actors/index";
    }

    @RequestMapping(value = "/actors/addOrUpdate/{id}")
    public String adminActorsAddOrUpdate(@PathVariable("id") String id, Model model) {
        ActorDb actorDb = new ActorDb();
        List<CountryDb> counties = countryService.getAll();
        if (!id.equals("0")) {
            actorDb = actorService.getActorWithId(Integer.parseInt(id));
        }
        model.addAttribute("actor", actorDb);
        model.addAttribute("countries", counties);
        return "admin/actors/addOrUpdate";
    }

    @RequestMapping(value = "/categories")
    public String admin_categories() {
        return "admin/categories/index";
    }

    @RequestMapping(value = "/categories/addOrUpdate/{id}")
    public String adminCategoriesAddOrUpdate(@PathVariable("id") String id, Model model) {
        CategoryDb categoryDb = new CategoryDb();
        if (!id.equals("0")) {
            categoryDb = categoryService.getCategoryWithId(id);
        }
        model.addAttribute("category", categoryDb);
        return "admin/categories/addOrUpdate";
    }

    @RequestMapping(value = "/directors")
    public String admin_directors() {
        return "admin/directors/index";
    }

    @RequestMapping(value = "/directors/addOrUpdate/{id}")
    public String adminDirectorsAddOrUpdate(@PathVariable("id") String id, Model model) {
        DirectorDb directorDb = new DirectorDb();
        List<CountryDb> counties = countryService.getAll();
        if (!id.equals("0")) {
            directorDb = directorService.getDirectorWithId(id);
        }
        model.addAttribute("director", directorDb);
        model.addAttribute("countries", counties);
        return "admin/directors/addOrUpdate";
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

    @RequestMapping(value = "/networks/addOrUpdate/{id}")
    public String adminNetworksAddOrUpdate(@PathVariable("id") String id, Model model) {
        NetworkDb networkDb = new NetworkDb();
        if (!id.equals("0")) {
            networkDb = networkService.getNetworkWithId(id);
        }
        model.addAttribute("network", networkDb);
        return "admin/networks/addOrUpdate";
    }

    @RequestMapping(value = "/studios")
    public String admin_studios() {
        return "admin/studios/index";
    }

    @RequestMapping(value = "/studios/addOrUpdate/{id}")
    public String adminStudiosAddOrUpdate(@PathVariable("id") String id, Model model) {
        StudioDb studioDb = new StudioDb();
        if (!id.equals("0")) {
            studioDb = studioService.getStudioWithId(id);
        }
        model.addAttribute("studio", studioDb);
        return "admin/studios/addOrUpdate";
    }

}
