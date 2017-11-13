package web.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.ScreenshotDb;
import web.exceptions.ParsingJsonToDaoException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("ScreenshotService")
@Transactional
public class ScreenshotService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private FilmService filmService;

    public ScreenshotDb getScreenshotWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        Session session = sessionFactory.getCurrentSession();
        ScreenshotDb screenshotDb =
                (ScreenshotDb) session.createQuery("from ScreenshotDb s where s.id=" + id).list().get(0);
        return screenshotDb;
    }

    public void saveOrUpdate(ScreenshotDb screenshotDb) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(screenshotDb);
    }

    public void delete(String id) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from ScreenshotDb s where s.id=" + id).executeUpdate();
    }

    public List<ScreenshotDb> getAll() throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        List<ScreenshotDb> result = session.createQuery("from ScreenshotDb ").list();
        return result;
    }

    public Set<ScreenshotDb> createScreenshotSet(List<String> links) throws ParsingJsonToDaoException {
        Set<ScreenshotDb> screenshotDbSet = new HashSet<>();
        for (String s : links) {
            ScreenshotDb screenshotDb = new ScreenshotDb();
            screenshotDb.setLink(s);
            screenshotDbSet.add(screenshotDb);
        }
        return screenshotDbSet;
    }

    public void checkForSceens(int idFilm, Set<ScreenshotDb> screenshotDbSet) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from ScreenshotDb s where s.filmByIdFilm=" + idFilm).executeUpdate();
        for (ScreenshotDb screenshotDb : screenshotDbSet) {
            screenshotDb.setFilmByIdFilm(filmService.getFilmWithId(Integer.toString(idFilm)));
            session.saveOrUpdate(screenshotDb);
        }
    }
}
