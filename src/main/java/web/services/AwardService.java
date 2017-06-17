package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.AwardDb;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public List<AwardDb> getAll(){
        Session session = sessionFactory.getCurrentSession();
        List<AwardDb> result = session.createQuery("from AwardDb ").list();
        return result;
    }

    public Set<AwardDb> createSetOfAwards(List<Map.Entry<Integer, String>> awards){
        Set<AwardDb> awardDbSet = new HashSet<>();
        for(Map.Entry<Integer, String> m : awards){
            AwardDb awardDb = new AwardDb();
            awardDb.setAwardName(m.getValue());
            awardDb.setAwardYear(m.getKey());
            awardDbSet.add(awardDb);
        }
        return awardDbSet;
    }
}

