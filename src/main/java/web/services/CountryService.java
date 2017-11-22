package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.CountryDb;

import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("CountryService")
@Transactional
public class CountryService {

    @Autowired(required = true)
    SessionFactory sessionFactory;

    private static final Logger log = LogManager.getLogger(CountryService.class);

    public CountryDb getCountryWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getCountryWithId(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        CountryDb countryDb = (CountryDb) session.createQuery("from CountryDb  c where c.id=" + id).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getCountryWithId() returns : countryDb=" + countryDb);
        return countryDb;
    }

    public void saveOrUpdateCountry(CountryDb countryDb) throws HibernateException {
        log.info("saveOrUpdateCountry(countryDb=" + countryDb + ")");

        if (countryDb == null) {
            log.error("Error : coutry is null");

            throw new IllegalArgumentException("CountryDb should not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(countryDb);
        session.getTransaction().commit();
        session.close();

        log.info("succ. saved or updated country");
    }

    public void deleteCountry(String id) throws HibernateException {
        log.info("deleteCountry(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from CountryDb c where c.id=" + id).executeUpdate();
        session.getTransaction().commit();
        session.close();

        log.info("succ. deleted country");
    }

    public List<CountryDb> getAll() throws HibernateException {
        log.info("getAll()");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<CountryDb> result = session.createQuery("from CountryDb ").list();
        session.getTransaction().commit();
        session.close();

        log.info("getAll() returns : result.size()=" + result.size());
        return result;
    }
}
