package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.DirectorDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.DirectorJSON;

import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("DirectorService")
@Transactional
public class DirectorService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Autowired
    private CountryService countryService;

    private static final Logger log = LogManager.getLogger(DirectorService.class);

    public DirectorDb getDirectorWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getDirectorWithId(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        DirectorDb directorDb = (DirectorDb) session.createQuery("from DirectorDb d where d.id=" + id).list().get(0);
        session.close();

        log.info("getDirectorWithId() returns : directorDb=" + directorDb);
        return directorDb;
    }

    public void saveOrUpdate(DirectorDb directorDb) throws HibernateException {
        log.info("saveOrUpdate(directorDb=" + directorDb + ")");

        if (directorDb == null) {
            log.error("Error : directorDb is null");

            throw new IllegalArgumentException("DirectorDb should not be null");
        }
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(directorDb);
        session.close();

        log.info("succ. saved or updated director");
    }

    public void delete(String id) throws HibernateException {
        log.info("delete(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.createQuery("delete from DirectorDb d where d.id=" + id).executeUpdate();
        session.close();

        log.info("succ. deleted director");
    }

    public List<DirectorDb> getAll() throws HibernateException {
        log.info("getAll()");

        Session session = sessionFactory.openSession();
        List<DirectorDb> result = session.createQuery("from DirectorDb ").list();
        session.close();

        log.info("getAll() returns : resut.size()=" + result.size());
        return result;
    }

    public DirectorDb convertToDirectorDb(DirectorJSON directorJSON) throws ParsingJsonToDaoException {
        log.info("convertToDirectorDb(directorJSON=" + directorJSON + ")");

        if (directorJSON == null) {
            log.error("Error : directorJSON is null");

            throw new IllegalArgumentException("DirectorJSON should not be null");
        }
        DirectorDb directorDb = new DirectorDb();
        directorDb.setFirstName(directorJSON.getFirstName());
        directorDb.setId(directorJSON.getId());
        directorDb.setLastName(directorJSON.getLastName());
        directorDb.setCountryByIdCountry(
                countryService.getCountryWithId(directorJSON.getCountry()));

        log.info("convertToDirectorDb() returns : directorDb=" + directorDb);
        return directorDb;
    }
}
