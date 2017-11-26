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
import web.dao.CommentDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.CommentJSON;

import java.text.ParseException;
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

    public void saveOrUpdate(CommentDb commentDb) throws HibernateException {
        log.info("saveOrUpdate(commentDb=" + commentDb + ")");

        if (commentDb == null) {
            log.error("Error : commentDb is null");

            throw new IllegalArgumentException("CommentRatingDB should not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(commentDb);
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

        CommentDb commentToDelete = (CommentDb) session.createQuery("from CommentDb c where c.id=" + id).list().get(0);
        if (commentToDelete.getClientByIdClient().getLogin().equals(authentication.getName())) {
            log.info("if statement");
            session.createQuery("delete from CommentDb c where c.id=" + id).executeUpdate();

            log.info("succ. deleted comment");
        } else if (authentication.getName().equals("admin")) {
            log.info("else if statement, admin is deleting comment");

            session.createQuery("delete from CommentDb c where c.id=" + id).executeUpdate();

            log.info("admin succ. deleted comment");
        }
        session.getTransaction().commit();
        session.close();
    }

    public CommentDb convertToCommentRatingDB(CommentJSON commentJSON) throws ParsingJsonToDaoException, ParseException {
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

        CommentDb commentDb = new CommentDb();

        commentDb.setId(commentJSON.getId());
        commentDb.setFilmByIdFilm(filmService.getFilmWithId(Integer.toString(commentJSON.getIdFilm())));
        commentDb.setClientByIdClient(clientService.getClientByLogin(authentication.getName()));

        commentDb.setCommentDate(new java.sql.Timestamp(new Date().getTime()));
        commentDb.setCommentText(commentJSON.getCommentText());

        log.info("convertToCommentRatingDB() returns : commentDb=" + commentDb);
        return commentDb;
    }

    public List<CommentDb> getAllCommentsForFilm(String id) throws HibernateException {
        log.info("getAllCommentsForFilm(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<CommentDb> allComments = session.createQuery("from CommentDb c where c.filmByIdFilm.id=" + id + " order by c.commentDate desc").list();
        session.getTransaction().commit();
        session.close();


        log.info("getAllCommentsForFilm() returns : allComents.size()=" + allComments.size());
        return allComments;
    }

}
