package web.services;

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

    public ActorDb getActorWithId(int id) throws HibernateException, IndexOutOfBoundsException {
        Session session = sessionFactory.getCurrentSession();
        ActorDb actorDb = (ActorDb) session.createQuery("from ActorDb a where a.id=" + id).list().get(0);
        return actorDb;
    }

    public void saveOrUpdate(ActorDb actorDb) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(actorDb);
    }

    public void delete(String id) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from ActorDb a where a.id=" + id).executeUpdate();
    }

    public List<ActorDb> getAll() throws HibernateException {
        List<ActorDb> result = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        result = session.createQuery("from ActorDb ").list();
        return result;
    }

    public ActorDb convertToActorDb(ActorJSON actorJSON) throws ParsingJsonToDaoException, ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = format.parse(actorJSON.getBirthdate());

        ActorDb actorDb = new ActorDb();
        actorDb.setFirstName(actorJSON.getFirstName());
        actorDb.setLastName(actorJSON.getLastName());
        actorDb.setId(actorJSON.getId());
        actorDb.setBirthdate(new java.sql.Date(parsed.getTime()));
        actorDb.setCountryByIdCountry(
                countryService.getCountryWithId(actorJSON.getCountry()));

        return actorDb;
    }
}
