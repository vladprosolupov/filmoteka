package web.services;

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

    public DirectorDb getDirectorWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        Session session = sessionFactory.getCurrentSession();
        DirectorDb directorDb = (DirectorDb) session.createQuery("from DirectorDb d where d.id=" + id).list().get(0);
        return directorDb;
    }

    public void saveOrUpdate(DirectorDb directorDb) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(directorDb);
    }

    public void delete(String id) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from DirectorDb d where d.id=" + id).executeUpdate();
    }

    public List<DirectorDb> getAll() throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        List<DirectorDb> result = session.createQuery("from DirectorDb ").list();
        return result;
    }

    public DirectorDb convertToDirectorDb(DirectorJSON directorJSON) throws ParsingJsonToDaoException {
        DirectorDb directorDb = new DirectorDb();
        directorDb.setFirstName(directorJSON.getFirstName());
        directorDb.setId(directorJSON.getId());
        directorDb.setLastName(directorJSON.getLastName());
        directorDb.setCountryByIdCountry(
                countryService.getCountryWithId(directorJSON.getCountry()));
        return directorDb;
    }
}
