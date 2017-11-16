package web.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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


    public void saveOrUpdate(CommentRatingDb commentRatingDb) throws HibernateException {
        if (commentRatingDb == null) {
            throw new IllegalArgumentException("CommentRatingDB should not be null");
        }
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(commentRatingDb);
    }

    public void deleteComment(String id) throws HibernateException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from CommentRatingDb c where c.id=" + id).executeUpdate();
    }

    public CommentRatingDb convertToCommentRatingDB(CommentJSON commentJSON) throws ParsingJsonToDaoException, ParseException {
        if (commentJSON == null) {
            throw new IllegalArgumentException("CommentJSON should not be null");
        }

        CommentRatingDb commentRatingDb = new CommentRatingDb();

        commentRatingDb.setId(commentJSON.getId());
        commentRatingDb.setRating(commentJSON.getRating());
        commentRatingDb.setFilmByIdFilm(filmService.getFilmWithId(Integer.toString(commentJSON.getIdFilm())));
        commentRatingDb.setClientByIdClient(clientService.getClientById(Integer.toString(commentJSON.getIdFilm())));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = format.parse(commentJSON.getCommentDate());

        commentRatingDb.setCommentDate(new java.sql.Timestamp(parsed.getTime()));
        commentRatingDb.setReferencedComment(commentJSON.getReferencedComment());
        commentRatingDb.setCommentText(commentJSON.getCommentText());

        return commentRatingDb;
    }

    public List<CommentJSON> getAllCommentsForFilm(String id) throws HibernateException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.getCurrentSession();
        List<CommentJSON> allComments = session.createQuery("from CommentRatingDb where filmByIdFilm=" + id).list();
        return allComments;
    }

}
