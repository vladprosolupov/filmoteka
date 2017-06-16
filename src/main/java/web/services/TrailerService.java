package web.services;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.TrailerDb;

import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("TrailerService")
@Transactional
public class TrailerService {

    @Autowired
    private SessionFactory sessionFactory;

    public TrailerDb getTrailerWithId(String id){
        Session session = sessionFactory.getCurrentSession();
        TrailerDb trailerDb =
                (TrailerDb) session.createQuery("from TrailerDb t where t.id=" + id).list().get(0);
        return trailerDb;
    }

    public void saveOrUpdate(TrailerDb trailerDb){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(trailerDb);
    }

    public void delete(String id){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from TrailerDb t where t.id=" + id).executeUpdate();
    }

    public List<TrailerDb> getAll(){
        Session session = sessionFactory.getCurrentSession();
        List<TrailerDb> result = session.createQuery("from TrailerDb ").list();
        return result;
    }
}
