package web.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.dao.CommentRatingDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.CommentJSON;
import web.services.CommentService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    private static final Logger log = LogManager.getLogger(CommentController.class);

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String addOrUpdateComment(@RequestBody CommentJSON commentJSON) throws ParseException, ParsingJsonToDaoException {
        log.info("addOrUpdateComment(commentJSON=" + commentJSON + ")");

        commentService.saveOrUpdate(commentService.convertToCommentRatingDB(commentJSON));

        log.info("addOrUpdateComment() returns : OK");
        return "OK";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String deleteComment(@PathVariable("id") String id) {
        log.info("deleteComment(id=" + id + ")");

        commentService.deleteComment(id);

        log.info("deleteComment() returns : OK");
        return "OK";
    }

    @RequestMapping(value = "/getFilmComments/{id}", method = RequestMethod.POST)
    public List<CommentJSON> getAllCommentsForFilm(@PathVariable("id") String id) {
        log.info("getAllCommentsForFilm(id=" + id + ")");

        List<CommentJSON> result = new ArrayList<>();
        List<CommentRatingDb> commentRatingDbList = commentService.getAllCommentsForFilm(id);
        for (CommentRatingDb comment : commentRatingDbList) {
            log.info("for loop");

            CommentJSON json = new CommentJSON();

            json.setId(comment.getId());

            Date date = new Date();
            date.setTime(comment.getCommentDate().getTime());

            json.setCommentDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
            json.setCommentText(comment.getCommentText());
            json.setRating(comment.getRating());
            json.setIdFilm(comment.getFilmByIdFilm().getId());
            json.setIdClient(comment.getClientByIdClient().getId());
            json.setClientLogin(comment.getClientByIdClient().getLogin());

            result.add(json);
        }

        log.info("getAllCommentsForFilm() returns : result.size()=" + result.size());
        return result;
    }
}
