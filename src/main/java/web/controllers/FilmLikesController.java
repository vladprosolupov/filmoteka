package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.dao.ClientDb;
import web.exceptions.NoSuchClientException;
import web.model.FilmJSONIndex;
import web.model.FilmLikesJSON;
import web.services.*;
import web.tasks.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping(value = "/likes")
public class FilmLikesController {

    @Autowired
    private FilmLikesService likesService;

    @Autowired
    private FilmDislikesService dislikesService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private AddClientLikeTask addClientLikeTask;

    @Autowired
    private AddClientDislikeTask addClientDislikeTask;

    @Autowired
    private RemoveClientLikeTask removeClientLikeTask;

    @Autowired
    private RemoveClientDislikeTask removeClientDislikeTask;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    AiService aiService;

    private static final Logger log = LogManager.getLogger(FilmLikesController.class);

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @RequestMapping(value = "/addLike", method = RequestMethod.POST)
    public @ResponseBody String addLikeForFilm(@RequestBody FilmLikesJSON filmLikesJSON) throws NoSuchClientException {
        log.info("addLikeForFilm(filmLikesJSON=" + filmLikesJSON + ")");

        if(filmLikesJSON == null) {
            log.error("filmLikesJSON is null");

            throw new IllegalArgumentException("FilmLikesJSON is null");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("If statement, user is not logged in, throwing exception");

            throw new IllegalArgumentException("User is not logged in");
        }

        ClientDb clientDb = clientService.getClientByLogin(authentication.getName());

        if(clientDb == null) {
            log.error("There is no such client");

            throw new NoSuchClientException("There is no such client");
        }

        addClientLikeTask.setClientDb(clientDb);
        addClientLikeTask.setFilmLikesJSON(filmLikesJSON);
        executorService.execute(addClientLikeTask);


        log.info("succ. added like for film");
        return "OK";
    }

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @RequestMapping(value = "/addDislike", method = RequestMethod.POST)
    public @ResponseBody String addDislikeForFilm(@RequestBody FilmLikesJSON filmLikesJSON) throws NoSuchClientException {
        log.info("addDislikeForFilm(filmLikesJSON=" + filmLikesJSON + ")");

        if(filmLikesJSON == null) {
            log.error("filmLikesJSON is null");

            throw new IllegalArgumentException("FilmLikesJSON is null");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("If statement, user is not logged in, throwing exception");

            throw new IllegalArgumentException("User is not logged in");
        }

        ClientDb clientDb = clientService.getClientByLogin(authentication.getName());

        if(clientDb == null) {
            log.error("There is no such client");

            throw new NoSuchClientException("There is no such client");
        }

        addClientDislikeTask.setClientDb(clientDb);
        addClientDislikeTask.setFilmLikesJSON(filmLikesJSON);
        executorService.execute(addClientDislikeTask);

        log.info("succ. added dislike for film");
        return "OK";
    }

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @RequestMapping(value = "/removeLike", method = RequestMethod.POST)
    public @ResponseBody String deleteLike(@RequestBody FilmLikesJSON filmLikesJSON) throws NoSuchClientException {
        log.info("deleteLike(filmLikesJSON=" + filmLikesJSON + ")");

        if(filmLikesJSON == null) {
            log.error("FilmLikesJSON is null");

            throw new IllegalArgumentException("FilmLikesJSON is null");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("If statement, user is not logged in, throwing exception");

            throw new IllegalArgumentException("User is not logged in");
        }

        int clientId = clientService.getClientIdByLogin(authentication.getName());

        removeClientLikeTask.setClientId(clientId);
        removeClientLikeTask.setFilmLikesJSON(filmLikesJSON);
        executorService.execute(removeClientLikeTask);

        return "OK";
    }

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @RequestMapping(value = "/removeDislike", method = RequestMethod.POST)
    public @ResponseBody String deleteDislike(@RequestBody FilmLikesJSON filmLikesJSON) throws NoSuchClientException {
        log.info("deleteDislike(filmLikesJSON=" + filmLikesJSON + ")");

        if(filmLikesJSON == null) {
            log.error("FilmLikesJSON is null");

            throw new IllegalArgumentException("FilmLikesJSON is null");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("If statement, user is not logged in, throwing exception");

            throw new IllegalArgumentException("User is not logged in");
        }

        int clientId = clientService.getClientIdByLogin(authentication.getName());

        removeClientDislikeTask.setClientId(clientId);
        removeClientDislikeTask.setFilmLikesJSON(filmLikesJSON);
        executorService.execute(removeClientDislikeTask);

        return "OK";
    }

    @RequestMapping(value = "/getLikesAndDislikes", method = RequestMethod.POST)
    public @ResponseBody FilmLikesJSON getLikesAndDislikesForFilm(@RequestBody FilmLikesJSON filmLikesJSON) {
        log.info("getLikesAndDislikesForFilm(filmLikesJSON=" + filmLikesJSON + ")");

        filmLikesJSON.setLikes(likesService.getLikesForFilm(Integer.toString(filmLikesJSON.getFilmId())));
        filmLikesJSON.setDislikes(dislikesService.getDislikesForFilm(Integer.toString(filmLikesJSON.getFilmId())));

        log.info("getLikesAndDislikesForFilm() returns : filmLikesJSON=" + filmLikesJSON);
        return filmLikesJSON;
    }

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @RequestMapping(value = "/getLiked/{page}", method = RequestMethod.GET)
    public @ResponseBody List<FilmJSONIndex> getLikedFilms(@PathVariable("page") String page) throws NoSuchClientException {
        log.info("getLikedFilms(page=" + page + ")");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int clientId = clientService.getClientIdByLogin(authentication.getName());

        List<FilmJSONIndex> likedFilmsByUser = likesService.getLikedFilmsByUserId(clientId, page);

        log.info("getLikedFilms() returns : likedFilmsByUser.size()=" + likedFilmsByUser.size());
        return likedFilmsByUser;
    }

    @RequestMapping(value = "/checkLikeFilm/{id}", method = RequestMethod.GET)
    public @ResponseBody boolean checkLikeFilm(@PathVariable("id") String id) throws NoSuchClientException {
        log.info("checkLikeFilm(id=" + id + ")");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("Error : user is not logged in");

            throw new IllegalArgumentException("User is not logged in");
        }
        int clientId = clientService.getClientIdByLogin(authentication.getName());

        boolean result = likesService.checkLikeFilmByUserId(id, clientId);

        log.info("checkLikeFilm() returns : result=" + result);
        return result;
    }

    @RequestMapping(value = "/checkDislikeFilm/{id}", method = RequestMethod.GET)
    public @ResponseBody boolean checkDislikeFilm(@PathVariable("id") String id) throws NoSuchClientException {
        log.info("checkDislikeFilm(id=" + id + ")");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("Error : user is not logged in");

            throw new IllegalArgumentException("User is not logged in");
        }
        int clientId = clientService.getClientIdByLogin(authentication.getName());

        boolean result = dislikesService.checkDislikeFilmWithClientId(id, clientId);

        log.info("checkDislikeFilm() returns : result=" + result);
        return result;
    }

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @RequestMapping(value = "/numberOfLiked", method = RequestMethod.GET)
    public @ResponseBody long getNumberOfLikedFilms() throws NoSuchClientException {
        log.info("getNumberOfLikedFilms()");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int clientId = clientService.getClientIdByLogin(authentication.getName());

        long result = likesService.getNumbersOfLikeByUserId(clientId);

        log.info("getNumberOfLikedFilms() returns : result=" + result);
        return result;
    }




}
