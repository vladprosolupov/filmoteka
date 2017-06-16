package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import web.dao.DirectorDb;

/**
 * Created by Rostyk on 16.06.2017.
 */
public class DirectorService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public DirectorDb getDirectorWithId(String id){
        Session session = sessionFactory.getCurrentSession();
        DirectorDb directorDb = (DirectorDb) session.createQuery("from DirectorDb d where d.id=" + id).list().get(0);
        return directorDb;
    }
}
