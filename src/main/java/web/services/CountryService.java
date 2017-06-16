package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import web.dao.CountryDb;

/**
 * Created by Rostyk on 16.06.2017.
 */
public class CountryService {

    @Autowired(required = true)
    SessionFactory sessionFactory;

    public CountryDb getCountryWithId(String id){
        Session session = sessionFactory.getCurrentSession();
        CountryDb countryDb = (CountryDb) session.createQuery("from CountryDb  c where c.id=" + id).list().get(0);
        return countryDb;
    }
}
