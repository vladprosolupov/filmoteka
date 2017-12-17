package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.FilmJSONIndex;

import java.util.List;

@Service("FilmCategoryService")
@Transactional
public class FilmCategoryService {

    private SessionFactory sessionFactory;

    private static final Logger log = LogManager.getLogger(FilmCategoryService.class);

    public FilmCategoryService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<FilmJSONIndex> getFilmsForCategory(String id, String page) throws HibernateException, NumberFormatException {
        log.info("getFilmsForCategory(id=" + id + ", page=" + page + ")");

        int limit = 10;
        int start = (Integer.parseInt(page) - 1) * limit;

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<FilmJSONIndex> list = session.createQuery("select f.title, f.releaseDate, f.cover, f.id, f.rating from FilmDb f INNER JOIN f.filmCategories fc WHERE fc.id =? order by f.rating desc").setParameter(0, Integer.parseInt(id)).setFirstResult(start).setMaxResults(limit).list();
        session.getTransaction().commit();
        session.close();

        log.info("getFilmsForCategory() returns : list.size()" + list.size());
        return list;
    }

    public long getNumberOfFilmsForCategory(String id) throws HibernateException {
        log.info("getnumberOfFilmsForCategory(id=" + id + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        long result = (long) session.createQuery("select count(f.id) from FilmDb f INNER JOIN f.filmCategories fc WHERE fc.id = " + id).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getNumberOfFilmsForCategory() returns : result=" + result);
        return result;
    }

}
