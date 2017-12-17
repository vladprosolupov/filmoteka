package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.dao.ClientDb;
import web.exceptions.NoSuchClientException;
import web.exceptions.ValidationError;
import web.model.BookmarkJSON;
import web.model.FilmJSONIndex;
import web.services.BookmarkService;
import web.services.ClientService;
import web.tasks.AddBookmarkTask;
import web.tasks.RemoveBookmarkTask;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/bookmark")
public class BookmarkController {

    private static final Logger log = LogManager.getLogger(BookmarkController.class);

    @Autowired
    private SessionFactory sessionFactory;

//    @Autowired
//    private BookmarkService bookmarkService;
//
//    @Autowired
//    private ClientService clientService;
//
//    @Autowired
//    private AddBookmarkTask addBookmarkTask;
//
//    @Autowired
//    private RemoveBookmarkTask removeBookmarkTask;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String addBookmark(@RequestBody @Valid BookmarkJSON bookmarkJSON, BindingResult bindingResult) throws ValidationError {
        log.info("addBookmark(bookmarkJSON=" + bookmarkJSON + ")");

        if (bindingResult.hasErrors()) {
            log.error("bookmark does not pass validation");

            throw new ValidationError("Validation is incorrect");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("Error : user is not logged in");

            throw new IllegalArgumentException("User is not logged in");
        }

        ClientService clientService = new ClientService(sessionFactory);
        AddBookmarkTask addBookmarkTask = new AddBookmarkTask(sessionFactory);

        ClientDb clientDb = clientService.getClientByLogin(authentication.getName());

        addBookmarkTask.setBookmarkJSON(bookmarkJSON);
        addBookmarkTask.setClientDb(clientDb);
        executorService.execute(addBookmarkTask);

        log.info("addBookmark() returns : OK");
        return "OK";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    String deleteBookmark(@RequestBody @Valid BookmarkJSON bookmarkJSON, BindingResult bindingResult) throws ValidationError {
        log.info("deleteBookmark(bookmarkJSON=" + bookmarkJSON + ")");

        if (bindingResult.hasErrors()) {
            log.error("bookmark does not pass validation");

            throw new ValidationError("Validation is incorrect");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("Error : user is not logged in");

            throw new IllegalArgumentException("User is not logged in");
        }
        ClientService clientService = new ClientService(sessionFactory);
        RemoveBookmarkTask removeBookmarkTask = new RemoveBookmarkTask(sessionFactory);

        ClientDb clientDb = clientService.getClientByLogin(authentication.getName());

        removeBookmarkTask.setClientDb(clientDb);
        removeBookmarkTask.setBookmarkJSON(bookmarkJSON);
        executorService.execute(removeBookmarkTask);

        log.info("deleteBookmark() returns : OK");
        return "OK";
    }

    @RequestMapping(value = "/checkBookmarkFilm/{id}", method = RequestMethod.GET)
    public @ResponseBody
    boolean checkBookmarkFilm(@PathVariable("id") String id) {
        log.info("checkBookmarkFilm(idFilm=" + id + ")");
        BookmarkService bookmarkService = new BookmarkService(sessionFactory);

        boolean result = bookmarkService.checkBookmarkFilm(id);
        log.info("checkBookmarkFilm() returns : " + result);
        return result;
    }

    @RequestMapping(value = "/getBookmarks/{page}", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONIndex> getBookmarkedFilms(@PathVariable("page") String page) {
        log.info("getBookmarkedFilms(page=" + page + ")");


        BookmarkService bookmarkService = new BookmarkService(sessionFactory);
        List<FilmJSONIndex> list = bookmarkService.getBookmarkedFilms(page);

        log.info("getBookmarkedFilms() returns : list.size()=" + list.size() + ")");
        return list;
    }

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @RequestMapping(value = "/numberOfBookmarks", method = RequestMethod.GET)
    public @ResponseBody long getNumberOfBookmarkedFilms() throws NoSuchClientException {
        log.info("getNumberOfBookmarkedFilms()");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ClientService clientService = new ClientService(sessionFactory);
        BookmarkService bookmarkService = new BookmarkService(sessionFactory);

        int clientId = clientService.getClientIdByLogin(authentication.getName());

        long result = bookmarkService.getNumbersOfBookmarkByUserId(clientId);

        log.info("getNumberOfBookmarkedFilms() returns : result=" + result);
        return result;
    }

}
