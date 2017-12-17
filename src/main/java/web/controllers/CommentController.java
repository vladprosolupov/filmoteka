package web.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.dao.CommentDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.CommentJSON;
import web.services.CommentService;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private SessionFactory sessionFactory;
//    private CommentService commentService;

    private static final Logger log = LogManager.getLogger(CommentController.class);

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String addOrUpdateComment(@RequestBody @Valid CommentJSON commentJSON, BindingResult bindingResult) throws ParseException, ParsingJsonToDaoException, UnsupportedEncodingException {
        log.info("addOrUpdateComment(commentJSON=" + commentJSON + ")");

        String text = URLDecoder.decode(commentJSON.getCommentText(), "UTF-8");

        log.info("text = " + text);

        if(bindingResult.hasErrors()) {
            log.error("Comment does not pass validation");

            return "Error";
        }
        CommentService commentService = new CommentService(sessionFactory);
        commentService.saveOrUpdate(commentService.convertToCommentRatingDB(commentJSON));

        log.info("addOrUpdateComment() returns : OK");
        return "OK";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String deleteComment(@PathVariable("id") String id) {
        log.info("deleteComment(id=" + id + ")");

        CommentService commentService = new CommentService(sessionFactory);
        commentService.deleteComment(id);

        log.info("deleteComment() returns : OK");
        return "OK";
    }

    @RequestMapping(value = "/getFilmComments/{id}", method = RequestMethod.GET)
    public @ResponseBody List<CommentJSON> getAllCommentsForFilm(@PathVariable("id") String id) {
        log.info("getAllCommentsForFilm(id=" + id + ")");

        List<CommentJSON> result = new ArrayList<>();

        CommentService commentService = new CommentService(sessionFactory);
        List<CommentDb> commentDbList = commentService.getAllCommentsForFilm(id);
        for (CommentDb comment : commentDbList) {
            log.info("for loop");

            CommentJSON json = new CommentJSON();

            json.setId(comment.getId());

            Date date = new Date();
            date.setTime(comment.getCommentDate().getTime());

            json.setCommentDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date));
            json.setCommentText(comment.getCommentText());
            json.setIdFilm(comment.getFilmByIdFilm().getId());
            json.setIdClient(comment.getClientByIdClient().getId());
            json.setClientLogin(comment.getClientByIdClient().getLogin());
            json.setClientFirstName(comment.getClientByIdClient().getFirstName());
            json.setClientLastName(comment.getClientByIdClient().getLastName());
            json.setClientAvatar(comment.getClientByIdClient().getAvatarByAvatar().getPath());
            result.add(json);
        }

        log.info("getAllCommentsForFilm() returns : result.size()=" + result.size());
        return result;
    }
}
