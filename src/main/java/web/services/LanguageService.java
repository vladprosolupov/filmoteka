package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.LanguageDb;

import java.util.List;

/**
 * Created by Rostyk on 15.06.2017.
 */
@Service("LanguageService")
@Transactional
public class LanguageService {

//    @Autowired(required = true)
    private SessionFactory sessionFactory;

    private static final Logger log = LogManager.getLogger(LanguageService.class);

    public LanguageService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<LanguageDb> getAllLanguages() throws HibernateException {
        log.info("getAllLanguages()");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<LanguageDb> result = session.createQuery("FROM LanguageDb").list();
        session.getTransaction().commit();
        session.close();

        log.info("getAllLanguages() returns : result.size()=" + result.size());
        return result;
    }

    public LanguageDb getLanguageWithISO(String iso) throws HibernateException, IndexOutOfBoundsException {
        log.info("getLanguageWithISO(iso=" + iso + ")");

        if (iso == null || iso.isEmpty()) {
            log.error("Error : iso is incorrect");

            throw new IllegalArgumentException("iso should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        LanguageDb languageDb = (LanguageDb) session.createQuery("from  LanguageDb l where l.iso6391 =?").setParameter(0, iso).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getLanguageWithISO() returns : languageDb=" + languageDb);
        return languageDb;
    }

    public LanguageDb getLanguageWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getLanguageWithId(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        LanguageDb languageDb = (LanguageDb) session.createQuery("from  LanguageDb l where l.id =" + id).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getLanguageWithId() returns : languageDb=" + languageDb);
        return languageDb;
    }
}
