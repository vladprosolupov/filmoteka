package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger log = LogManager.getLogger(ScreenshotService.class);

    public ScreenshotDb getScreenshotWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getScreenshotWithId(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is null");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        ScreenshotDb screenshotDb =
                (ScreenshotDb) session.createQuery("from ScreenshotDb s where s.id=" + id).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getScreenshotWithId() returns : screenshotDb=" + screenshotDb);
        return screenshotDb;
    }

    public void saveOrUpdate(ScreenshotDb screenshotDb) throws HibernateException {
        log.info("saveOrUpdate(screenshoutDb=" + screenshotDb + ")");

        if (screenshotDb == null) {
            log.error("Error : screenshotDb is null");

            throw new IllegalArgumentException("ScreenshotDb should not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(screenshotDb);
        session.getTransaction().commit();
        session.close();

        log.info("succ. saved or updated screen");
    }

    public void delete(String id) throws HibernateException {
        log.info("delete(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from ScreenshotDb s where s.id=" + id).executeUpdate();
        session.getTransaction().commit();
        session.close();

        log.info("succ. deleted screen");
    }

    public List<ScreenshotDb> getAll() throws HibernateException {
        log.info("getAll()");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ScreenshotDb> result = session.createQuery("from ScreenshotDb ").list();
        session.getTransaction().commit();
        session.close();

        log.info("getAll() returns : result.size()" + result.size());
        return result;
    }

    public Set<ScreenshotDb> createScreenshotSet(List<String> links) throws ParsingJsonToDaoException {
        log.info("createScreenshotSet(links=" + links + ")");

        if (links == null) {
            log.error("Error : links is null");

            throw new IllegalArgumentException("Links should not be null");
        }

        Set<ScreenshotDb> screenshotDbSet = new HashSet<>();
        for (String s : links) {
            log.info("for loop");

            ScreenshotDb screenshotDb = new ScreenshotDb();
            screenshotDb.setLink(s);
            screenshotDbSet.add(screenshotDb);
        }

        log.info("createScreenshotSet() returns : screenshotDbSet.size()=" + screenshotDbSet.size());
        return screenshotDbSet;
    }

    public void checkForSceens(int idFilm, Set<ScreenshotDb> screenshotDbSet) throws HibernateException {
        log.info("checkForSceens(idFilm=" + idFilm + ", screenshotDbSet=" + screenshotDbSet + ")");

        if (idFilm < 0) {
            log.error("Error : idFilm is incorrect");

            throw new IllegalArgumentException("IdFilm should not be null");
        }
        if (screenshotDbSet == null) {
            log.error("Error : screenshotDbSet is null");

            throw new IllegalArgumentException("ScreenshotDbSet should not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from ScreenshotDb s where s.filmByIdFilm=" + idFilm).executeUpdate();

        for (ScreenshotDb screenshotDb : screenshotDbSet) {
            log.info("for loop");

            screenshotDb.setFilmByIdFilm(filmService.getFilmWithId(Integer.toString(idFilm)));
            session.saveOrUpdate(screenshotDb);
        }
        session.getTransaction().commit();
        session.close();
    }
}
