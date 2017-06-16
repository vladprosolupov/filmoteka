package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.AwardDb;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("AwardService")
@Transactional
public class AwardService {

    @Autowired
    private SessionFactory sessionFactory;

    public AwardDb getAwardWithId(String id){
        Session session = sessionFactory.getCurrentSession();
        AwardDb awardDb = (AwardDb) session.createQuery("from AwardDb a where a.id=" + id).list().get(0);
        return awardDb;
    }

    public void saveOrUpdateAward(AwardDb awardDb){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(awardDb);
    }

    public void deleteAward(String id){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from AwardDb a where a.id=" + id).executeUpdate();
    }
}

