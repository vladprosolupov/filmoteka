package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.StudioDb;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("NetworkService")
@Transactional
public class StudioService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public StudioDb getStudioWithId(String id){
        Session session = sessionFactory.getCurrentSession();
        StudioDb studioDb = (StudioDb) session.createQuery("from StudioDb s where s.id=" + id).list().get(0);
        return studioDb;
    }
}
