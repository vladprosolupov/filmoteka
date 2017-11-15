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


@Service("CommentService")
@Transactional
public class CommentService {
    @Autowired(required = true)
    private SessionFactory sessionFactory;


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

    public CommentRatingDb convertToCommentRatingDB(CommentJSON commentJSON) throws ParsingJsonToDaoException {
        if (commentJSON == null) {
            throw new IllegalArgumentException("CommentJSON should not be null");
        }
        CommentRatingDb commentRatingDb = new CommentRatingDb();
        return commentRatingDb;
    }

}
