package web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.exceptions.ParsingJsonToDaoException;
import web.model.CommentJSON;
import web.services.CommentService;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String addOrUpdateCategory(@RequestBody CommentJSON commentJSON){
        try {
            commentService.saveOrUpdate(commentService.convertToCommentRatingDB(commentJSON));
        } catch (ParsingJsonToDaoException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody String deleteCategory(@PathVariable("id") String id){
        commentService.deleteComment(id);
        return "OK";
    }
}
