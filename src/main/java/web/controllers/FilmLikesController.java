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
import web.model.FilmJSONIndex;
import web.model.FilmLikesJSON;
import web.services.ClientService;
import web.services.FilmDislikesService;
import web.services.FilmLikesService;
import web.services.FilmService;

import java.util.List;

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

    private static final Logger log = LogManager.getLogger(FilmLikesController.class);

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @RequestMapping(value = "/addLike", method = RequestMethod.POST)
    public @ResponseBody String addLikeForFilm(@RequestBody FilmLikesJSON filmLikesJSON) {
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

            throw new IllegalArgumentException("There is no such client");
        }

        likesService.addLike(likesService.convertToFilmLikeDbFromFilmLike(likesService.convertToFilmLikeFromFilmLikesJSON(filmLikesJSON, clientDb)));

        log.info("succ. added like for film");
        return "OK";
    }

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @RequestMapping(value = "/addDislike", method = RequestMethod.POST)
    public @ResponseBody String addDislikeForFilm(@RequestBody FilmLikesJSON filmLikesJSON) {
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

            throw new IllegalArgumentException("There is no such client");
        }

        dislikesService.addLike(dislikesService.convertToFilmLikeDbFromFilmLike(dislikesService.convertToFilmDislikeFromFilmLikesJSON(filmLikesJSON, clientDb)));

        log.info("succ. added dislike for film");
        return "OK";
    }

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @RequestMapping(value = "/removeLike", method = RequestMethod.POST)
    public @ResponseBody String deleteLike(@RequestBody FilmLikesJSON filmLikesJSON) {
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

        ClientDb clientDb = clientService.getClientByLogin(authentication.getName());

        if(clientDb == null) {
            log.error("There is no such client");

            throw new IllegalArgumentException("There is no such client");
        }

        likesService.deleteLike(Integer.toString(filmLikesJSON.getFilmId()), Integer.toString(clientDb.getId()));

        return "OK";
    }

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @RequestMapping(value = "/removeDislike", method = RequestMethod.POST)
    public @ResponseBody String deleteDislike(@RequestBody FilmLikesJSON filmLikesJSON) {
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

        ClientDb clientDb = clientService.getClientByLogin(authentication.getName());

        if(clientDb == null) {
            log.error("There is no such client");

            throw new IllegalArgumentException("There is no such client");
        }

        dislikesService.deleteDislike(Integer.toString(filmLikesJSON.getFilmId()), Integer.toString(clientDb.getId()));

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
    public List<FilmJSONIndex> getLikedFilms(@PathVariable("page") String page) {
        log.info("getLikedFilms(page=" + page + ")");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ClientDb clientDb = clientService.getClientByLogin(authentication.getName());

        if(clientDb == null) {
            log.error("There is no such client");

            throw new IllegalArgumentException("There is no such client");
        }

        List<FilmJSONIndex> likedFilmsByUser = likesService.getLikedFilmsByUser(clientDb, page);

        log.info("getLikedFilms() returns : likedFilmsByUser.size()=" + likedFilmsByUser.size());
        return likedFilmsByUser;
    }





}
