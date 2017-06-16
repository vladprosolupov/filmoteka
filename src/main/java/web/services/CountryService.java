package web.services;

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

    public CountryDb getCountryWithId(String id){
        Session session = sessionFactory.getCurrentSession();
        CountryDb countryDb = (CountryDb) session.createQuery("from CountryDb  c where c.id=" + id).list().get(0);
        return countryDb;
    }

    public void saveOrUpdateCountry(CountryDb countryDb){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(countryDb);
    }

    public void deleteCountry(String id){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from CountryDb c where c.id=" + id).executeUpdate();
    }

    public List<CountryDb> getAll(){
        Session session = sessionFactory.getCurrentSession();
        List<CountryDb> result = session.createQuery("from CountryDb ").list();
        return result;
    }
}
