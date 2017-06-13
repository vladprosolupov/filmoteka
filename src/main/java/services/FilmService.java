package services;

import dao.FilmDb;
import hibernate.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Rostyk on 12.06.2017.
 */
public class FilmService {

    public static List<FilmDb> getAllFilms(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<FilmDb> listOfFilms = session.createQuery("FROM FilmDb").list();
        return listOfFilms;
    }

    public static void saveOrUpdate(FilmDb filmToSave){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(filmToSave);
        session.getTransaction().commit();
        session.close();
    }
}
