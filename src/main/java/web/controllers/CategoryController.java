package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.dao.CategoryDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.CategoryJSON;
import web.model.FilmJSONIndex;
import web.services.CategoryService;
import web.services.FilmCategoryService;

import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    FilmCategoryService filmCategoryService;

    private static final Logger log = LogManager.getLogger(CategoryController.class);

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String addOrUpdateCategory(@RequestBody CategoryJSON categoryJSON) throws ParsingJsonToDaoException {
        log.info("addOrUpdateCategory(categoryJSON=" + categoryJSON + ")");

        categoryService.saveOrUpdate(categoryService.convertToCategoryDb(categoryJSON));

        log.info("addOrUpdateCategory() returns : OK");
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String deleteCategory(@PathVariable("id") String id) {
        log.info("deleteCategory(id=" + id + ")");

        categoryService.deleteCategory(id);

        log.info("deleteCategory() returns : OK");
        return "OK";
    }

    @RequestMapping(value = "/{id}/films", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONIndex> getFilmsForCategory(@PathVariable("id") String id) {
        log.info("getFilmsForCategory(id" + id + ")");

        List<FilmJSONIndex> allFilmsForCategory = filmCategoryService.getAllFilmsForCategory(id);

        log.info("getFilmsCategory() returns allFilmsForCategory.size() : " + allFilmsForCategory.size());
        return allFilmsForCategory;
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    List<CategoryDb> getAll() {
        log.info("getAll()");

        List<CategoryDb> allCategories = categoryService.getAllCategories();

        log.info("getAll() returns : allCategories.size()=" + allCategories.size());
        return allCategories;
    }

    @RequestMapping(value = "/forNav", method = RequestMethod.GET)
    public @ResponseBody
    List<CategoryJSON> getForNav(){
        log.info("getForNav()");

        List<CategoryJSON> allForNav = categoryService.getForNav();
        log.info("getForNav() returns : allCategories.size()=" + allForNav.size());
        return allForNav;
    }

}
