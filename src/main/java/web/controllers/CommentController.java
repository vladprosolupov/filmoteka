package web.controllers;


import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.exceptions.ParsingJsonToDaoException;
import web.model.CommentJSON;
import web.services.CommentService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String addOrUpdateCategory(@RequestBody CommentJSON commentJSON) {
        try {
            commentService.saveOrUpdate(commentService.convertToCommentRatingDB(commentJSON));
        } catch (ParsingJsonToDaoException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String deleteCategory(@PathVariable("id") String id) {
        commentService.deleteComment(id);
        return "OK";
    }

    @RequestMapping(value = "/getFilmComments/{id}", method = RequestMethod.POST)
    public List<CommentJSON> getAllCommentsForFilm(@PathVariable("id") String id) {
        List<CommentJSON> result;
        try {
            result = commentService.getAllCommentsForFilm(id);
        } catch (IllegalArgumentException e) {
            return null;
        } catch (HibernateException e) {
            return null;
        }
        return result;
    }
}
