package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.StudioDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.StudioJSON;

import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("StudioService")
@Transactional
public class StudioService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    private static final Logger log = LogManager.getLogger(StudioService.class);

    public StudioDb getStudioWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getStudioWithId(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        StudioDb studioDb = (StudioDb) session.createQuery("from StudioDb s where s.id=" + id).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getStudioWithId() returns : studioDb=" + studioDb);
        return studioDb;
    }

    public void saveOrUpdateStudio(StudioDb studioDb) throws HibernateException {
        log.info("saveOrUpdateStudio(studioDb=" + studioDb + ")");

        if (studioDb == null) {
            log.error("Error : studioDb is null");

            throw new IllegalArgumentException("StudioDb should not be null");
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(studioDb);
        session.getTransaction().commit();
        session.close();

        log.info("succ. saved or updated studio");
    }

    public void deleteStudio(String id) throws HibernateException {
        log.info("deleteStudio(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from StudioDb s where s.id=" + id).executeUpdate();
        session.getTransaction().commit();
        session.close();

        log.info("succ. deleted studio");
    }

    public List<StudioDb> getAll() throws HibernateException {
        log.info("getAll()");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<StudioDb> result = session.createQuery("from StudioDb s order by s.studioName").list();
        session.getTransaction().commit();
        session.close();

        log.info("getAll() returns : result.size()" + result.size());
        return result;
    }

    public StudioDb convertToStudioDb(StudioJSON studioJSON) throws ParsingJsonToDaoException {
        log.info("convertToStudioDb(studioJSON=" + studioJSON + ")");

        if (studioJSON == null) {
            log.error("Error : studioJSON is null");

            throw new IllegalArgumentException("StudioJSON should not be null");
        }

        StudioDb studioDb = new StudioDb();
        studioDb.setId(studioJSON.getId());
        studioDb.setStudioName(studioJSON.getStudioName());

        log.info("convertToStudioDb() returns : studioDb=" + studioDb);
        return studioDb;
    }
}
