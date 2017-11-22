package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.ActorDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.ActorJSON;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("ActorService")
@Transactional
public class ActorService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Autowired
    private CountryService countryService;

    private static final Logger log = LogManager.getLogger(ActorService.class);

    public ActorDb getActorWithId(int id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getActorWithId(id=" + id + ")");

        if (id < 0) {
            log.error("Error : Id is not correct");

            throw new IllegalArgumentException("Id should not be smaller than 0");
        }

        Session session = sessionFactory.openSession();
        ActorDb actorDb = (ActorDb) session.createQuery("from ActorDb a where a.id=" + id).list().get(0);
        session.close();

        log.info("getActorWithId() returns : actorDb.getFirstName()=" + actorDb.getFirstName());
        return actorDb;
    }

    public void saveOrUpdate(ActorDb actorDb) throws HibernateException {
        log.info("saveOrUpdate(actorDb=" + actorDb + ")");

        if (actorDb == null) {
            log.error("Error : actorDb is null");

            throw new IllegalArgumentException("ActorDb should not be null");
        }

        Session session = sessionFactory.openSession();
        session.saveOrUpdate(actorDb);
        session.close();

        log.info("succ. saved or updated actor");
    }

    public void delete(String id) throws HibernateException {
        log.info("delete(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.createQuery("delete from ActorDb a where a.id=" + id).executeUpdate();
        session.close();

        log.info("succ. deleted actor");
    }

    public List<ActorDb> getAll() throws HibernateException {
        log.info("getAll()");

        Session session = sessionFactory.openSession();
        List<ActorDb> result = session.createQuery("from ActorDb ").list();
        session.close();

        log.info("getAll() returns : result.size()=" + result.size());
        return result;
    }

    public ActorDb convertToActorDb(ActorJSON actorJSON) throws ParsingJsonToDaoException, ParseException {
        log.info("convertToActorDb(actorJSON=" + actorJSON);

        if (actorJSON == null) {
            log.error("Error : actorJSON is null");

            throw new IllegalArgumentException("ActorJSON should not be null");
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = format.parse(actorJSON.getBirthdate());

        ActorDb actorDb = new ActorDb();
        actorDb.setFirstName(actorJSON.getFirstName());
        actorDb.setLastName(actorJSON.getLastName());
        actorDb.setId(actorJSON.getId());
        actorDb.setBirthdate(new java.sql.Date(parsed.getTime()));
        actorDb.setCountryByIdCountry(
                countryService.getCountryWithId(actorJSON.getCountry()));

        log.info("convertToActorDb() returns : actorDb.getFirstName()=" + actorDb.getFirstName());
        return actorDb;
    }
}
