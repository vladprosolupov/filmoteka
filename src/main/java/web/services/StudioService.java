package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.StudioDb;

import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("StudioService")
@Transactional
public class StudioService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public StudioDb getStudioWithId(String id){
        Session session = sessionFactory.getCurrentSession();
        StudioDb studioDb = (StudioDb) session.createQuery("from StudioDb s where s.id=" + id).list().get(0);
        return studioDb;
    }

    public void saveOrUpdateStudio(StudioDb studioDb){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(studioDb);
    }

    public void deleteStudio(String id){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from StudioDb s where s.id=" + id).executeUpdate();
    }

    public List<StudioDb> getAll(){
        Session session = sessionFactory.getCurrentSession();
        List<StudioDb> result = session.createQuery("from StudioDb ").list();
        return result;
    }
}
