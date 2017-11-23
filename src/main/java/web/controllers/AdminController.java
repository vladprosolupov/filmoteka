package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import web.dao.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import web.services.*;

import java.util.ArrayList;
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

    private static final Logger log = LogManager.getLogger(AdminController.class);

    @RequestMapping(value = "/")
    public String adminIndex() {
        log.info("adminIndex()");

        return "admin/index";
    }

    @RequestMapping(value = "/actors")
    public String adminActors() {
        log.info("adminActors()");

        return "admin/actors/index";
    }

    @RequestMapping(value = "/actors/addOrUpdate/{id}")
    public String adminActorsAddOrUpdate(@PathVariable("id") String id, Model model) {
        log.info("adminActorsAddOrUpdate(id=" + id + ", model=" + model + ")");

        ActorDb actorDb = new ActorDb();
        List<CountryDb> counties = countryService.getAll();

        if (!id.equals("0")) {
            log.info("id statement, id is not 0");

            actorDb = actorService.getActorWithId(Integer.parseInt(id));
        }
        model.addAttribute("actor", actorDb);
        model.addAttribute("countries", counties);
        log.info("adminActorsAddOrUpdate() returns : admin/actors/addOrUpdate" +
                ", actor.getFirstName()=" + actorDb.getFirstName()
                + ", countries.size()=" + counties.size());
        return "admin/actors/addOrUpdate";
    }

    @RequestMapping(value = "/categories")
    public String adminCategories() {
        log.info("adminCategories");

        log.info("adminCategories() returns : admin/categories/index");
        return "admin/categories/index";
    }

    @RequestMapping(value = "/categories/addOrUpdate/{id}")
    public String adminCategoriesAddOrUpdate(@PathVariable("id") String id, Model model) {
        log.info("adminCategoriesAddOrUpdate(id=" + id + ", model=" + model + ")");

        CategoryDb categoryDb = new CategoryDb();
        if (!id.equals("0")) {
            log.info("if statement, id is not 0");

            categoryDb = categoryService.getCategoryWithId(id);

        }
        model.addAttribute("category", categoryDb);
        log.info("adminCategoriesAddOrUpdate() returns : admin/categories/addOrUpdate" +
                ", categoryDb.getName()=" + categoryDb.getName());
        return "admin/categories/addOrUpdate";
    }

    @RequestMapping(value = "/directors")
    public String adminDirectors() {
        log.info("adminDirectors()");

        log.info("adminDirectors() returns : admin/directors/index");
        return "admin/directors/index";
    }

    @RequestMapping(value = "/directors/addOrUpdate/{id}")
    public String adminDirectorsAddOrUpdate(@PathVariable("id") String id, Model model) {
        log.info("adminDirectorsAddOrUpdate(id=" + id + ", model=" + model + ")");

        DirectorDb directorDb = new DirectorDb();
        List<CountryDb> counties = countryService.getAll();
        if (!id.equals("0")) {
            log.info("if statement, id is not 0");

            directorDb = directorService.getDirectorWithId(id);
        }
        model.addAttribute("director", directorDb);
        model.addAttribute("countries", counties);

        log.info("adminDirectorsAddOrUpdate() returns : admin/directors/addOrUpdate" +
                ", director.getFirstName()=" + directorDb.getFirstName()
                + ", countries.size()=" + counties.size());
        return "admin/directors/addOrUpdate";
    }

    @RequestMapping(value = "/films")
    public String adminFilms() {
        log.info("adminFilms()");

        log.info("adminFilms() returns : admin/films/index");
        return "admin/films/index";
    }

    @RequestMapping(value = "/films/addOrUpdate/{id}")
    public String adminFilmsAddOrUpdate(@PathVariable("id") String id, Model model) {
        log.info("adminFilmsAddOrUpdate(id=" + id + ", model=" + model + ")");

        FilmDb filmDb = new FilmDb();
        if (!id.equals("0")) {
            log.info("if statement, id is not 0");

            filmDb = filmService.getFilmWithId(id);
        }

        if(id.equals("undefined")) {
            log.error("id film is undefined");

            throw new IllegalArgumentException("Film id is undefined");
        }

        List<LanguageDb> listOfLanguageDbs = languageService.getAllLanguages();
        model.addAttribute("languages", listOfLanguageDbs);
        model.addAttribute("film", filmDb);

        log.info("adminFilmsAddOrUpdate() returns : admin/films/addOrUpdate" +
                ", listOfLanguage.size()=" + listOfLanguageDbs.size()
                + ", filmDb=" + filmDb.getTitle());
        return "admin/films/addOrUpdate";
    }

    @RequestMapping(value = "/networks")
    public String admin_networks() {
        return "admin/networks/index";
    }

    @RequestMapping(value = "/networks/addOrUpdate/{id}")
    public String adminNetworksAddOrUpdate(@PathVariable("id") String id, Model model) {
        log.info("adminNetworksAddOrUpdate(id=" + id + ", model=" + model + ")");

        NetworkDb networkDb = new NetworkDb();
        if (!id.equals("0")) {
            log.info("if statement, id is not 0");

            networkDb = networkService.getNetworkWithId(id);
        }
        model.addAttribute("network", networkDb);

        log.info("adminNetworksAddOrUpdate() returns : admin/networks/addOrUpdate" +
                ", networkDb.getNetworkName()=" + networkDb.getNetworkName());
        return "admin/networks/addOrUpdate";
    }

    @RequestMapping(value = "/studios")
    public String adminStudios() {
        log.info("adminStudios()");

        log.info("adminStudios() returns : admin/studios/index");
        return "admin/studios/index";
    }

    @RequestMapping(value = "/studios/addOrUpdate/{id}")
    public String adminStudiosAddOrUpdate(@PathVariable("id") String id, Model model) {
        log.info("adminStudiosAddOrUpdate(id=" + id + ", model=" + model + ")");

        StudioDb studioDb = new StudioDb();
        if (!id.equals("0")) {
            log.info("if statement, id is not 0");

            studioDb = studioService.getStudioWithId(id);
        }
        model.addAttribute("studio", studioDb);

        log.info("adminStudiosAddOrUpdate() returns : admin/studios/addOrUpdate" +
                ", studioDb.getStudioName()=" + studioDb.getStudioName());
        return "admin/studios/addOrUpdate";
    }

}
