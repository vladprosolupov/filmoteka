package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import web.dao.CommentRatingDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.CommentJSON;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("CommentService")
@Transactional
public class CommentService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Autowired
    private FilmService filmService;

    @Autowired
    private ClientService clientService;

    private static final Logger log = LogManager.getLogger(CommentService.class);

    public void saveOrUpdate(CommentRatingDb commentRatingDb) throws HibernateException {
        log.info("saveOrUpdate(commentRatingDb=" + commentRatingDb + ")");

        if (commentRatingDb == null) {
            log.error("Error : commentRatingDb is null");

            throw new IllegalArgumentException("CommentRatingDB should not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(commentRatingDb);
        session.getTransaction().commit();
        session.close();

        log.info("succ. saved or updated comment");
    }

    public void deleteComment(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("deleteComment(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("Error : user is not logged in");

            throw new IllegalArgumentException("User is not logged in");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        CommentRatingDb commentToDelete = (CommentRatingDb) session.createQuery("from CommentRatingDb c where c.id=" + id).list().get(0);
        if (commentToDelete.getClientByIdClient().getLogin().equals(authentication.getName())) {
            log.info("if statement");
            session.createQuery("delete from CommentRatingDb c where c.id=" + id).executeUpdate();

            log.info("succ. deleted comment");
        } else if (authentication.getName().equals("admin")) {
            log.info("else if statement, admin is deleting comment");

            session.createQuery("delete from CommentRatingDb c where c.id=" + id).executeUpdate();

            log.info("admin succ. deleted comment");
        }
        session.getTransaction().commit();
        session.close();
    }

    public CommentRatingDb convertToCommentRatingDB(CommentJSON commentJSON) throws ParsingJsonToDaoException, ParseException {
        log.info("convertToCommentRatingDB(commentJSON=" + commentJSON + ")");

        if (commentJSON == null) {
            log.error("Error : commentJSON is null");

            throw new IllegalArgumentException("CommentJSON should not be null");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("Error : user is not logged in");

            throw new IllegalArgumentException("User is not logged in");
        }

        CommentRatingDb commentRatingDb = new CommentRatingDb();

        commentRatingDb.setId(commentJSON.getId());
        commentRatingDb.setRating(0.0);
        commentRatingDb.setFilmByIdFilm(filmService.getFilmWithId(Integer.toString(commentJSON.getIdFilm())));
        commentRatingDb.setClientByIdClient(clientService.getClientByLogin(authentication.getName()));

        commentRatingDb.setCommentDate(new java.sql.Timestamp(new Date().getTime()));
        commentRatingDb.setReferencedComment(commentJSON.getReferencedComment());
        commentRatingDb.setCommentText(commentJSON.getCommentText());

        log.info("convertToCommentRatingDB() returns : commentRatingDb=" + commentRatingDb);
        return commentRatingDb;
    }

    public List<CommentRatingDb> getAllCommentsForFilm(String id) throws HibernateException {
        log.info("getAllCommentsForFilm(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<CommentRatingDb> allComments = session.createQuery("from CommentRatingDb c where c.filmByIdFilm=" + id + " order by c.commentDate desc").list();
        session.getTransaction().commit();
        session.close();


        log.info("getAllCommentsForFilm() returns : allComents.size()=" + allComments.size());
        return allComments;
    }

}
